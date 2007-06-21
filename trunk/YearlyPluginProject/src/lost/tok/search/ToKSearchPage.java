package lost.tok.search;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lost.tok.ToK;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.resource.ImageDescriptor;
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

public class ToKSearchPage extends DialogPage implements ISearchPage {

	EnumSet<SearchOption> searchOptions = EnumSet.noneOf(SearchOption.class);
	
	private Combo fPattern;
	private ISearchPageContainer container;

	public ToKSearchPage() {
	}

	public ToKSearchPage(String title) {
		super(title);
	}

	public ToKSearchPage(String title, ImageDescriptor image) {
		super(title, image);
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
        layout.horizontalSpacing = 10;
        composite.setLayout(layout);

		Control expression = createExpression(composite);
		expression.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false, 2, 1));

		Label separator = new Label(composite, SWT.NONE);
        separator.setVisible(false);
        GridData data = new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1);
        data.heightHint = convertHeightInCharsToPixels(1) / 3;
        separator.setLayoutData(data);
        
        Control sourceSrch = createSourceSrch(composite);
        sourceSrch.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        		
        Control discussionSrch = createDiscussionSrch(composite);
        discussionSrch.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        
		setControl(composite);
		Dialog.applyDialogFont(composite);
	}

    private Control createSourceSrch(Composite parent) {
        Group result = new Group(parent, SWT.NONE);
        result.setText("Sources");
        result.setLayout(new GridLayout());

        newButton(result, SWT.CHECK, "Title", true, SearchOption.SRC_TITLE);
        
        newButton(result, SWT.CHECK, "Author", true, SearchOption.SRC_AUTHOR);
        
        newButton(result, SWT.CHECK, "Content", true, SearchOption.SRC_CONTENT);
               
        return result;
	}

	private Control createDiscussionSrch(Composite parent) {
        Group result = new Group(parent, SWT.NONE);
        result.setText("Discussions");
        //result.setLayout(new GridLayout());
        result.setLayout(new GridLayout(2, true));
        
        newButton(result, SWT.CHECK, "Name", true, SearchOption.DSC_NAME);
        
        //newButton(result, SWT.CHECK, "Link Subject", true, SearchOption.DSC_LINK_SUBJ);
        
        newButton(result, SWT.CHECK, "Quote Text", true, SearchOption.DSC_QUOTES);
        
        newButton(result, SWT.CHECK, "Creator", true, SearchOption.DSC_CREATOR);
        
        newButton(result, SWT.CHECK, "Quote Comment", true, SearchOption.DSC_QUOTE_COMMENTS);
        
        newButton(result, SWT.CHECK, "Opinion Name", true, SearchOption.DSC_OPINIONS);
        
        return result;
	}

	SelectionListener selectionListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (e.widget.getData() instanceof SearchOption) {
				SearchOption so = (SearchOption) e.widget.getData();
				if (((Button)e.widget).getSelection()) {
					searchOptions.add(so);
				} else {
					searchOptions.remove(so);
				}
			}
		}
	};
	
	private Button newButton(Composite parent, int style, String title, boolean selected, SearchOption opt) {
		Button b = new Button(parent, style);

		if (opt != null) {
			b.setData(opt);
			if (selected) searchOptions.add(opt);
			b.addSelectionListener(selectionListener);
		}
		b.setText(title);
		b.setSelection(selected);
		return b;
	}
	
	private Control createExpression(Composite parent) {
        Composite result = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        result.setLayout(layout);
        
        Label label = new Label(result, SWT.LEFT);
        label.setText("Search string (* = any string, ? = any character):"); //$NON-NLS-1$
        label.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1));

        fPattern = new Combo(result, SWT.SINGLE | SWT.BORDER);
//        fPattern.addSelectionListener(new SelectionAdapter() {
//            public void widgetSelected(SelectionEvent e) {
//            }
//        });
//        fPattern.addModifyListener(new ModifyListener() {
//            public void modifyText(ModifyEvent e) {
//            }
//        });
        GridData data = new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1);
        data.widthHint = convertWidthInCharsToPixels(50);
        fPattern.setLayoutData(data);

        // Ignore case checkbox
        newButton(result, SWT.CHECK, "Case sensitive", false, SearchOption.CASE_SENSITIVE)
        	.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 1, 1));

        return result;
    }
    
    public boolean performAction() {
    	SearchPlugin.getDefault().getPreferenceStore().setValue(SearchPreferencePage.REUSE_EDITOR, false);
		NewSearchUI.runQueryInBackground(getSearchQuery());
		return true;
	}

	private ISearchQuery getSearchQuery() {
        // Setup search scope
        // SearchScope scope = null;
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		List<IResource> scope = new LinkedList<IResource>();
		switch (getContainer().getSelectedScope()) {
	        case ISearchPageContainer.WORKSPACE_SCOPE:
	            for (IProject p : wsRoot.getProjects()) {
	            	getProjectElements(scope, p);
	            }
	            break;
	        case ISearchPageContainer.SELECTION_SCOPE: 
	        	ISelection s = getContainer().getSelection();
	        	if (s==null || !(s instanceof IStructuredSelection)) return null;
				
	        	IStructuredSelection ss = (IStructuredSelection) s;
				for (Iterator iter = ss.iterator(); iter.hasNext();) {
					IResource r = (IResource) iter.next();
					scope.add(r);
				}
				
	        	break;
	        case ISearchPageContainer.SELECTED_PROJECTS_SCOPE:
	        	for (String pn : getContainer().getSelectedProjectNames()) {
	        		getProjectElements(scope, wsRoot.getProject(pn));
	        	}
	        	break;
	        case ISearchPageContainer.WORKING_SET_SCOPE:
	            IWorkingSet[] workingSets = getContainer().getSelectedWorkingSets();
	            for (IAdaptable a : workingSets[0].getElements()) {
	            	if (a instanceof IProject) {
	            		getProjectElements(scope, (IProject)a);
	            	}
	            	else if (a instanceof IResource) {
						IResource r = (IResource) a;
						scope.add(r);
					}
	            }
	            break;
        }
		if (scope.isEmpty()) return null;
        NewSearchUI.activateSearchResultView();

        return new ToKSearchQuery(
        		fPattern.getText(),
        		searchOptions,
        		scope
        		);
	}

	private void getProjectElements(List<IResource> scope, IProject p) {
		ToK tok = ToK.getProjectToK(p);
		if (tok == null) return;
		
		System.out.println("Add " + p.getName());
		
		scope.add(tok.getDiscussionFolder());
		scope.add(tok.getSourcesFolder());
		scope.add(tok.getRootsFolder());
	}

	private ISearchPageContainer getContainer() {
		return container;
	}

	public void setContainer(ISearchPageContainer container) {
		this.container = container;
	}

}
