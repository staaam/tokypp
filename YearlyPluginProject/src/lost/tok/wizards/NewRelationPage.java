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

package lost.tok.wizards;

import lost.tok.Discussion;
import lost.tok.Messages;
import lost.tok.Quote;
import lost.tok.Relation;
import lost.tok.ToK;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * A page in the creation of relation wizard
 * 
 * @author Team Lost
 * 
 */
public class NewRelationPage extends WizardPage implements ModifyListener,
		SelectionListener {

	// private String projectName;
	private ToK tok;

	// private String discName;
	private Discussion discussion;

	private Combo relType;

	private Text comment;

	private Tree leftObjects;

	private Tree rightObjects;

	private DiscCombo discCombo;

	private boolean firstTime = true;

	/**
	 * Constructor for NewRelationWizardPage.
	 * 
	 */
	public NewRelationPage(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.getString("NewRelationWizardPage.0")); //$NON-NLS-1$
		setDescription(Messages.getString("NewRelationWizardPage.1")); //$NON-NLS-1$
		initialize(selection);
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;

		Label label;
		GridData gd;

		// Discussion line
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewRelationWizardPage.Discussion")); //$NON-NLS-1$

		discCombo = new DiscCombo(container, SWT.READ_ONLY
				| SWT.DROP_DOWN, tok);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		discCombo.setLayoutData(gd);
		discCombo.addSelectionListener(this);

		// Relation type line
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewRelationWizardPage.2")); //$NON-NLS-1$

		relType = new Combo(container, SWT.READ_ONLY | SWT.DROP_DOWN);
		for (String element : Relation.getRelTypes()) {
			relType.add(element);
		}

		relType.addSelectionListener(this);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		relType.setLayoutData(gd);

		// Comment line
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewRelationWizardPage.3")); //$NON-NLS-1$

		comment = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		comment.setLayoutData(gd);
		comment.addModifyListener(this);

		// last 'line'
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewRelationWizardPage.4")); //$NON-NLS-1$
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		gd = new GridData(GridData.FILL_BOTH);
		leftObjects = new Tree(container, SWT.BORDER);
		leftObjects.setLayoutData(gd);

		gd = new GridData(GridData.FILL_BOTH);
		rightObjects = new Tree(container, SWT.BORDER);
		rightObjects.setLayoutData(gd);

		leftObjects.addSelectionListener(this);

		rightObjects.addSelectionListener(this);

		String dname = (discussion == null) ? null : discussion.getDiscName();
		discCombo.init();

		if (dname != null) {
			discCombo.setTextNotify(dname);
		}

		dialogChanged();
		setControl(container);
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		if (discCombo.getText().length() == 0) {
			updateStatus(Messages
					.getString("NewRelationPage.ErrNoDiscSelected")); //$NON-NLS-1$
			return;
		}

		String relType = getRelationType();
		TreeItem[] leftSelected = leftObjects.getSelection();
		TreeItem[] rightSelected = rightObjects.getSelection();

		if (relType.length() == 0) {
			updateStatus(Messages.getString("NewRelationWizardPage.5")); //$NON-NLS-1$
			return;
		}
		updateStatus(null);
		if (leftSelected.length + rightSelected.length != 2) {
			updateStatus(Messages.getString("NewRelationWizardPage.6")); //$NON-NLS-1$
			return;
		}
	}

	public String getComment() {
		return comment.getText();
	}

	public Discussion getDiscussion() {
		return discussion;
	}

	public String getRelationType() {
		return relType.getText();
	}

	public Integer[] getSelectedQuotes() {
		TreeItem[] leftSelected = leftObjects.getSelection();
		TreeItem[] rightSelected = rightObjects.getSelection();
		Integer[] selectedText = new Integer[2];
		selectedText[0] = (Integer) leftSelected[0].getData();
		selectedText[1] = (Integer) rightSelected[0].getData();
		return selectedText;
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize(ISelection selection) {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1) {
				return;
			}
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IResource resource = (IResource) obj;
				tok = ToK.getProjectToK(resource.getProject());
				try {
					discussion = tok.getDiscussion(Discussion
							.getNameFromResource(resource));
				} catch (CoreException e) {
				}
			}
		}
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public void modifyText(ModifyEvent e) {
		dialogChanged();
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
		widgetSelected(arg0);
	}

	public void widgetSelected(SelectionEvent arg0) {
		if ((discussion != null && firstTime ) ||
				(discussion == null && discCombo.getText() != null && discCombo
				.getText().length() > 0)
				|| discussion.getDiscName().compareTo(discCombo.getText()) != 0) {
			try {
				discussion = tok.getDiscussion(discCombo.getText());
			} catch (CoreException e) {
				discussion = null;
			}

			discussionChanged();
			firstTime = false;
		}

		dialogChanged();
	}

	private void discussionChanged() {
		if (discussion == null)
			return;
		leftObjects.removeAll();
		rightObjects.removeAll();

		String[] opinionNames = discussion.getOpinionNames();
		Integer[] opinionIDs = discussion.getOpinionIDs();

		for (int i = 0; i < opinionNames.length; i++) {
			TreeItem leftOpinion = new TreeItem(leftObjects, 0);
			leftOpinion.setText(opinionNames[i]);
			leftOpinion.setData(opinionIDs[i]);
			leftOpinion.setExpanded(true);

			TreeItem rightOpinion = new TreeItem(rightObjects, 0);
			rightOpinion.setText(opinionNames[i]);
			rightOpinion.setData(opinionIDs[i]);
			rightOpinion.setExpanded(true);

			for (Quote quote : discussion.getQuotes(opinionNames[i])) {
				TreeItem leftQuote = new TreeItem(leftOpinion, 0);
				leftQuote.setText(quote.getPrefix(40));
				leftQuote.setData(quote.getID());

				TreeItem rightQuote = new TreeItem(rightOpinion, 0);
				rightQuote.setText(quote.getPrefix(40));
				rightQuote.setData(quote.getID());
			}
		}
	}
}