/**
 * 
 */
package lost.tok.perspective;
import lost.tok.excerptionsView.ExcerptionView;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * This class creates the perspective Tree of Knowledge
 * @author LOST
 *
 */
public class Perspective implements IPerspectiveFactory{
	
	public interface IPerspectivePlugin {
        /**
         * Plugin id.
         */
        public final static String PLUGIN_ID = "lost.tok.perspective"; //$NON-NLS-1$
        
        /**
         * Test perspective id.
         */
        public final static String TEST_PERSPECTIVE_ID = PLUGIN_ID + ".ToK"; //$NON-NLS-1$
	}

	/**
	 * The createInitialLayout method is called on the IPerspectiveFactory. 
	 *  This method defines the initial layout for a page 
	 */
	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
	    defineLayout(layout);

		
	}

	private void defineLayout(IPageLayout layout) {
		//Editors are placed for free.
        String editorArea = layout.getEditorArea();

        // Place package explorer to the left of
        // editor area.
        IFolderLayout left =
                layout.createFolder("left", IPageLayout.LEFT, (float) 0.26, editorArea); //$NON-NLS-1$
        left.addView("org.eclipse.jdt.ui.PackageExplorer"); //$NON-NLS-1$
   
        // Place the Excerption view underneath
        // editor area.
        IFolderLayout bottom = 
        	layout.createFolder("bottom", IPageLayout.BOTTOM, (float) 0.75, editorArea); //$NON-NLS-1$
        bottom.addView(ExcerptionView.ID);
	}

	private void defineActions(IPageLayout layout) {
		//Add "new wizards".
		//this Adds it to the File->new menu and the right click menu
		layout.addNewWizardShortcut("lost.tok.newTokWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("lost.tok.newDiscussionWizard.NewDiscussionWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("lost.tok.newRelationWizard.NewRelationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("lost.tok.newLinkWizard.NewLinkWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("lost.tok.unparsedDocWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder"); //$NON-NLS-1$
                
        
        // Add "show views". 
		//(both thtough the menues and the shortcut on the bottom left corner)
        layout.addShowViewShortcut(ExcerptionView.ID);
        layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
        layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
        layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
        layout.addShowViewShortcut(IPageLayout.ID_EDITOR_AREA);
        layout.addShowViewShortcut("org.eclipse.jdt.ui.PackageExplorer"); //$NON-NLS-1$
	
	}
	
	

}
