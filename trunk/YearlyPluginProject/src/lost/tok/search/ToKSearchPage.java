package lost.tok.search;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.internal.ui.util.ComboFieldEditor;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

	private Combo fPattern;
	private Button fCaseSensitive;
	private Button fSourceName;
	private Button fSourceAuthor;
	private Button fSourceContent;
	private Button fDiscussionName;
	private Button fDiscussionSources;
	private Button fDiscussionLinks;
	private Button fDiscussionQuotes;
	private Button fDiscussionQuoteComments;
	private ISearchPageContainer container;

	public ToKSearchPage() {
		// TODO Auto-generated constructor stub
	}

	public ToKSearchPage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public ToKSearchPage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
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

        fSourceName = newButton(result, SWT.CHECK, "Title", true);
        
        fSourceAuthor = newButton(result, SWT.CHECK, "Author", true);
        
        fSourceContent = newButton(result, SWT.CHECK, "Content", true);
               
        return result;
	}

	private Control createDiscussionSrch(Composite parent) {
        Group result = new Group(parent, SWT.NONE);
        result.setText("Discussions");
        //result.setLayout(new GridLayout());
        result.setLayout(new GridLayout(2, true));
        
        fDiscussionName = newButton(result, SWT.CHECK, "Name", true);

        fDiscussionQuotes = newButton(result, SWT.CHECK, "Quotes", true);

        fDiscussionSources = newButton(result, SWT.CHECK, "Sources Names", true);

        fDiscussionQuoteComments = newButton(result, SWT.CHECK, "Quote Comments", true);

        fDiscussionLinks = newButton(result, SWT.CHECK, "Links", true);

        return result;
	}

	private Button newButton(Composite parent, int style, String title, boolean selected) {
		Button b = new Button(parent, style);
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
        fCaseSensitive = new Button(result, SWT.CHECK);
        fCaseSensitive.setText("Case sensitive"); //$NON-NLS-1$
//        fCaseSensitive.addSelectionListener(new SelectionAdapter() {
//            public void widgetSelected(SelectionEvent e) {
//            }
//        });
        fCaseSensitive.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 1, 1));

        return result;
    }
    
    public boolean performAction() {
    	NewSearchUI.runQueryInBackground(getSearchQuery());
		return true;
	}

	private ISearchQuery getSearchQuery() {
        // Setup search scope
        // SearchScope scope = null;
        switch (getContainer().getSelectedScope()) {
        case ISearchPageContainer.WORKSPACE_SCOPE:
            // scope = SearchScope.newWorkspaceScope();
            break;
        case ISearchPageContainer.SELECTION_SCOPE: 
        	// scope = getSelectedResourcesScope(false);
        	break;
        case ISearchPageContainer.SELECTED_PROJECTS_SCOPE:
        	// scope = getSelectedResourcesScope(true);
        	break;
        case ISearchPageContainer.WORKING_SET_SCOPE:
            //IWorkingSet[] workingSets = getContainer().getSelectedWorkingSets();
            //String desc = Messages.format(SearchMessages.WorkingSetScope, ScopePart
            //        .toString(workingSets));
            // scope = SearchScope.newSearchScope(desc, workingSets);
        	break;
        }
        NewSearchUI.activateSearchResultView();

        return null;
	}

	private ISearchPageContainer getContainer() {
		return container;
	}

	public void setContainer(ISearchPageContainer container) {
		this.container = container;
	}

}
