/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

package lost.tok.search;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lost.tok.Messages;
import lost.tok.ToK;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.search.internal.ui.SearchPlugin;
import org.eclipse.search.internal.ui.SearchPreferencePage;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;

/**
 * Class that creates and manages ToK Search dialog page
 * 
 * @author Michael Gelfand
 * 
 */
public class ToKSearchPage extends DialogPage implements ISearchPage {

	// For dialog settings
	private static final String PAGE_NAME = "LostTokSearchPage"; //$NON-NLS-1$
	private static final String STORE_HISTORY = "HISTORY"; //$NON-NLS-1$
	private static final String STORE_HISTORY_SIZE = "HISTORY_SIZE"; //$NON-NLS-1$
	private static final int HISTORY_SIZE = 20;
	private LinkedList<SearchPatternData> history = new LinkedList<SearchPatternData>();
	private boolean fFirstTime = true;

	// By default - all options are on, case sensivity is off
	protected EnumSet<SearchOption> searchOptions = EnumSet
			.complementOf(EnumSet.of(SearchOption.CASE_SENSITIVE));
	private EnumMap<SearchOption, Button> checkboxes = new EnumMap<SearchOption, Button>(
			SearchOption.class);

	/**
	 * Class for storing/restoring search history data
	 * 
	 * @author Michael Gelfand
	 * 
	 */
	private static class SearchPatternData {
		private static final String STORE_WORKING_SETS = "WORKING_SETS"; //$NON-NLS-1$
		private static final String STORE_TEXT_PATTERN = "TEXT_PATTERN"; //$NON-NLS-1$
		private static final String STORE_SCOPE = "SCOPE"; //$NON-NLS-1$

		public final String textPattern;
		public final EnumSet<SearchOption> searchOptions;
		public final int scope;
		public final IWorkingSet[] workingSets;

		public SearchPatternData(String textPattern,
				EnumSet<SearchOption> searchOptions, int scope,
				IWorkingSet[] workingSets) {
			this.textPattern = textPattern;
			this.searchOptions = searchOptions;
			this.scope = scope;
			this.workingSets = workingSets;
		}

		public void store(IDialogSettings settings) {
			settings.put(STORE_TEXT_PATTERN, textPattern); //$NON-NLS-1$
			settings.put(STORE_SCOPE, scope); //$NON-NLS-1$
			if (workingSets != null) {
				String[] wsIds = new String[workingSets.length];
				for (int i = 0; i < workingSets.length; i++) {
					wsIds[i] = workingSets[i].getLabel();
				}
				settings.put(STORE_WORKING_SETS, wsIds); //$NON-NLS-1$
			} else {
				settings.put(STORE_WORKING_SETS, new String[0]); //$NON-NLS-1$
			}

			for (SearchOption so : SearchOption.values())
				settings.put(so.toString(), searchOptions.contains(so));
		}

		public static SearchPatternData create(IDialogSettings settings) {
			String textPattern = settings.get(STORE_TEXT_PATTERN); //$NON-NLS-1$

			String[] wsIds = settings.getArray(STORE_WORKING_SETS); //$NON-NLS-1$
			IWorkingSet[] workingSets = null;
			if (wsIds != null && wsIds.length > 0) {
				IWorkingSetManager workingSetManager = PlatformUI
						.getWorkbench().getWorkingSetManager();
				workingSets = new IWorkingSet[wsIds.length];
				for (int i = 0; workingSets != null && i < wsIds.length; i++) {
					workingSets[i] = workingSetManager.getWorkingSet(wsIds[i]);
					if (workingSets[i] == null) {
						workingSets = null;
					}
				}
			}

			EnumSet<SearchOption> searchOptions = EnumSet
					.noneOf(SearchOption.class);
			for (SearchOption so : SearchOption.values()) {
				if (settings.getBoolean(so.toString()))
					searchOptions.add(so);
				else
					searchOptions.remove(so);
			}

			try {
				int scope = settings.getInt(STORE_SCOPE); //$NON-NLS-1$

				return new SearchPatternData(textPattern, searchOptions, scope,
						workingSets);
			} catch (NumberFormatException e) {
				return null;
			}
		}
	}

	private Combo fPattern;
	private ISearchPageContainer container;

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		readConfiguration();

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 10;
		composite.setLayout(layout);

		Control expression = createExpression(composite);
		expression.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,
				true, false, 2, 1));

		Label separator = new Label(composite, SWT.NONE);
		separator.setVisible(false);
		GridData data = new GridData(GridData.FILL, GridData.FILL, false,
				false, 2, 1);
		data.heightHint = convertHeightInCharsToPixels(1) / 3;
		separator.setLayoutData(data);

		Control sourceSrch = createSourceSrch(composite);
		sourceSrch.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, false, 1, 1));

		Control discussionSrch = createDiscussionSrch(composite);
		discussionSrch.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, false, 1, 1));

		setControl(composite);
		Dialog.applyDialogFont(composite);
	}

	/**
	 * Creates checkboxes for source search options
	 * 
	 * @param parent
	 *            the parent composite
	 * @return created composite
	 */
	private Control createSourceSrch(Composite parent) {
		Group result = new Group(parent, SWT.NONE);
		result.setText(Messages.getString("ToKSearchPage.Sources")); //$NON-NLS-1$
		result.setLayout(new GridLayout());

		newButton(result, Messages.getString("ToKSearchPage.Title"), SearchOption.SRC_TITLE); //$NON-NLS-1$

		newButton(result, Messages.getString("ToKSearchPage.Author"), SearchOption.SRC_AUTHOR); //$NON-NLS-1$

		newButton(result, Messages.getString("ToKSearchPage.Content"), SearchOption.SRC_CONTENT); //$NON-NLS-1$

		return result;
	}

	/**
	 * Creates checkboxes for discussion search options
	 * 
	 * @param parent
	 *            the parent composite
	 * @return created composite
	 */
	private Control createDiscussionSrch(Composite parent) {
		Group result = new Group(parent, SWT.NONE);
		result.setText(Messages.getString("ToKSearchPage.Discussions")); //$NON-NLS-1$
		// result.setLayout(new GridLayout());
		result.setLayout(new GridLayout(2, true));

		newButton(result, Messages.getString("ToKSearchPage.Name"), SearchOption.DSC_NAME); //$NON-NLS-1$

		// newButton(result, "Link Subject", SearchOption.DSC_LINK_SUBJ);

		newButton(result, Messages.getString("ToKSearchPage.QuoteText"), SearchOption.DSC_QUOTES); //$NON-NLS-1$

		newButton(result, Messages.getString("ToKSearchPage.Creator"), SearchOption.DSC_CREATOR); //$NON-NLS-1$

		newButton(result, Messages.getString("ToKSearchPage.QuoteComment"), SearchOption.DSC_QUOTE_COMMENTS); //$NON-NLS-1$

		newButton(result, Messages.getString("ToKSearchPage.Opinion"), SearchOption.DSC_OPINIONS); //$NON-NLS-1$

		return result;
	}

	/**
	 * Listener used to handle changes in search options
	 */
	private SelectionListener selectionListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (e.widget.getData() instanceof SearchOption) {
				SearchOption so = (SearchOption) e.widget.getData();
				if (((Button) e.widget).getSelection())
					searchOptions.add(so);
				else
					searchOptions.remove(so);
			}
		}
	};

	/**
	 * Creates new checkbox assotiated with with given Search Option
	 * 
	 * @param parent
	 *            the parent composite
	 * @param title
	 *            the checkbox title
	 * @param opt
	 *            search option to assotiate with
	 * @return newly created button widget
	 */
	private Button newButton(Composite parent, String title, SearchOption opt) {
		Button b = new Button(parent, SWT.CHECK);

		if (opt != null) {
			b.setData(opt);
			b.setSelection(searchOptions.contains(opt));
			checkboxes.put(opt, b);
			b.addSelectionListener(selectionListener);
		}
		b.setText(title);
		return b;
	}

	/**
	 * Creates combobox for search pattern input
	 * 
	 * @param parent
	 *            the parent composite
	 * @return newly created composite
	 */
	private Composite createExpression(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		result.setLayout(layout);

		Label label = new Label(result, SWT.LEFT);
		label.setText(Messages.getString("ToKSearchPage.SearchHint")); //$NON-NLS-1$
		label.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false,
				false, 2, 1));

		fPattern = new Combo(result, SWT.SINGLE | SWT.BORDER);
		fPattern.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleWidgetSelected();
			}
		});

		GridData data = new GridData(GridData.FILL, GridData.FILL, true, false,
				1, 1);
		data.widthHint = convertWidthInCharsToPixels(50);
		fPattern.setLayoutData(data);

		// Ignore case checkbox
		newButton(result, Messages.getString("ToKSearchPage.CaseSensetive"), SearchOption.CASE_SENSITIVE) //$NON-NLS-1$
				.setLayoutData(
						new GridData(GridData.FILL, GridData.FILL, false,
								false, 1, 1));

		return result;
	}

	/**
	 * Loads saved search options for previously used search
	 */
	private void handleWidgetSelected() {
		int selectionIndex = fPattern.getSelectionIndex();
		if (selectionIndex < 0 || selectionIndex >= history.size())
			return;

		SearchPatternData patternData = history.get(selectionIndex);
		if (!fPattern.getText().equals(patternData.textPattern))
			return;

		searchOptions = EnumSet.copyOf(patternData.searchOptions);
		fPattern.setText(patternData.textPattern);
		container.setSelectedScope(patternData.scope);
		if (patternData.workingSets != null)
			container.setSelectedWorkingSets(patternData.workingSets);

		for (SearchOption so : SearchOption.values())
			checkboxes.get(so).setSelection(
					patternData.searchOptions.contains(so));
		
		container.setPerformActionEnabled(true);
	}

	private String[] getPreviousSearchPatterns() {
		int size = history.size();
		String[] patterns = new String[size];
		for (int i = 0; i < size; i++)
			patterns[i] = history.get(i).textPattern;

		return patterns;
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible && fPattern != null) {
			if (fFirstTime) {
				fFirstTime = false;
				fPattern.setItems(getPreviousSearchPatterns());
				fPattern.select(0);
				handleWidgetSelected();
			}
			fPattern.setFocus();
		}
		super.setVisible(visible);
	}

	public boolean performAction() {
		updateHistoryData();

		SearchPlugin.getDefault().getPreferenceStore().setValue(
				SearchPreferencePage.REUSE_EDITOR, false);
		NewSearchUI.runQueryInBackground(getSearchQuery());
		return true;
	}

	private void updateHistoryData() {
		for (int i = 0; i < history.size(); i++) {
			SearchPatternData d = history.get(i);
			if (!d.textPattern.equals(fPattern.getText()))
				continue;
			history.remove(i);
		}

		history.addFirst(new SearchPatternData(fPattern.getText(),
				searchOptions, container.getSelectedScope(), container
						.getSelectedWorkingSets()));
	}

	/**
	 * Returns search query for newly created search
	 * 
	 * @return SearchQuery for newly created search
	 */
	private ISearchQuery getSearchQuery() {
		// Setup search scope
		// SearchScope scope = null;
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		List<IResource> scope = new LinkedList<IResource>();
		switch (container.getSelectedScope()) {
		case ISearchPageContainer.WORKSPACE_SCOPE:
			for (IProject p : wsRoot.getProjects())
				getProjectElements(scope, p);
			break;
		case ISearchPageContainer.SELECTION_SCOPE:
			ISelection s = container.getSelection();
			if (s == null || !(s instanceof IStructuredSelection))
				return null;

			IStructuredSelection ss = (IStructuredSelection) s;
			for (Iterator iter = ss.iterator(); iter.hasNext();) {
				IResource r = (IResource) iter.next();
				scope.add(r);
			}

			break;
		case ISearchPageContainer.SELECTED_PROJECTS_SCOPE:
			for (String pn : container.getSelectedProjectNames())
				getProjectElements(scope, wsRoot.getProject(pn));
			break;
		case ISearchPageContainer.WORKING_SET_SCOPE:
			IWorkingSet[] workingSets = container.getSelectedWorkingSets();
			for (IAdaptable a : workingSets[0].getElements()) {
				if (a instanceof IProject)
					getProjectElements(scope, (IProject) a);
				else if (a instanceof IResource) {
					IResource r = (IResource) a;
					scope.add(r);
				}
			}
			break;
		}
		NewSearchUI.activateSearchResultView();

		return new ToKSearchQuery(fPattern.getText(), searchOptions, scope);
	}

	/**
	 * Given a project adds its sources, roots and discussions to search scope
	 * 
	 * @param scope
	 *            scope to add items to
	 * @param p
	 *            project to scan
	 */
	private void getProjectElements(List<IResource> scope, IProject p) {
		ToK tok = ToK.getProjectToK(p);
		if (tok == null)
			return;

		scope.add(tok.getDiscussionFolder());
		scope.add(tok.getSourcesFolder());
		scope.add(tok.getRootsFolder());
	}

	public void setContainer(ISearchPageContainer container) {
		this.container = container;
	}

	// --------------- Configuration handling --------------

	public void dispose() {
		writeConfiguration();
		super.dispose();
	}

	/**
	 * Returns the page settings for this Text search page.
	 * 
	 * @return the page settings to be used
	 */
	private IDialogSettings getDialogSettings() {
		return SearchPlugin.getDefault().getDialogSettingsSection(PAGE_NAME);
	}

	/**
	 * Initializes itself from the stored page settings.
	 */
	private void readConfiguration() {
		IDialogSettings s = getDialogSettings();
		for (SearchOption so : SearchOption.values()) {
			if (s.get(so.toString()) == null)
				continue;
			if (s.getBoolean(so.toString()))
				searchOptions.add(so);
			else
				searchOptions.remove(so);
		}

		try {
			int historySize = s.getInt(STORE_HISTORY_SIZE);
			for (int i = 0; i < historySize; i++) {
				IDialogSettings histSettings = s.getSection(STORE_HISTORY + i);
				history.addLast(SearchPatternData.create(histSettings));
			}
		} catch (NumberFormatException e) {
			// ignore
		}
	}

	/**
	 * Stores it current configuration in the dialog store.
	 */
	private void writeConfiguration() {
		IDialogSettings s = getDialogSettings();
		for (SearchOption so : SearchOption.values())
			s.put(so.toString(), searchOptions.contains(so));

		int historySize = Math.min(history.size(), HISTORY_SIZE);
		s.put(STORE_HISTORY_SIZE, historySize);
		for (int i = 0; i < historySize; i++) {
			IDialogSettings histSettings = s.addNewSection(STORE_HISTORY + i);
			history.get(i).store(histSettings);
		}
	}

}
