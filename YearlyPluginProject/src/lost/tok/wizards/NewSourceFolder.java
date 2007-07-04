package lost.tok.wizards;

import lost.tok.GeneralFunctions;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewFolderMainPage;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.newresource.ResourceMessages;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;



public class NewSourceFolder extends BasicNewResourceWizard{

	private WizardNewFolderMainPage mainPage;

    /**
     * Creates a wizard for creating a new folder resource in the workspace.
     */
    public NewSourceFolder() {
        super();
    }

    /* (non-Javadoc)
     * Method declared on IWizard.
     */
    public void addPages() {
        super.addPages();
        mainPage = new WizardNewFolderMainPage(ResourceMessages.NewFolder_text, getSelection()); 
        addPage(mainPage);
    }

    /* (non-Javadoc)
     * Method declared on IWorkbenchWizard.
     */
    public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
        super.init(workbench, currentSelection);
        setWindowTitle(ResourceMessages.NewFolder_title);
        setNeedsProgressMonitor(true);
    }

    /* (non-Javadoc)
     * Method declared on BasicNewResourceWizard.
     */
    protected void initializeDefaultPageImageDescriptor() {
      ImageDescriptor desc = IDEWorkbenchPlugin.getIDEImageDescriptor("wizban/newfolder_wiz.png");//$NON-NLS-1$
      setDefaultPageImageDescriptor(desc);
       
    }

    /* (non-Javadoc)
     * Method declared on IWizard.
     */
    public boolean performFinish() {
        IFolder folder = mainPage.createNewFolder();
        if (folder == null) {
			return false;
		}
        
        IFile orderFile = folder.getFile("order.xml"); //$NON-NLS-1$
		if (!orderFile.exists()) {
			GeneralFunctions.writeToXml(orderFile, orderSkeleton(folder));
		}
		
		IFolder father = (IFolder) folder.getParent();
		IFile order = father.getFile("order.xml");
		Document d = GeneralFunctions.readFromXML(order);
		
		Element e = d.getRootElement().addElement("sub");
		e.addElement("name").addText(mainPage.getName());
		e.addElement("type").addText("dir");
		
		GeneralFunctions.writeToXml(order, d);

        selectAndReveal(folder);
        
        return true;
    }
		
	protected Document orderSkeleton(IFolder folder) {
		Document orderDoc = DocumentHelper.createDocument();

		// Create the Skeleton of the order file
		Element e = orderDoc.addElement("order"); //$NON-NLS-1$
		e.addElement("dir").addText(folder.getName());
		return orderDoc;
	}
}
