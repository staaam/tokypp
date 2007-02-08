package lost.tok.newLinkWizard;

import lost.tok.Messages;
import lost.tok.ToK;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * This is a sample new wizard. Its role is to create a new file resource in the
 * provided container. If the container resource (a folder or a project) is
 * selected in the workspace when the wizard is opened, it will accept it as the
 * target container. The wizard creates one file with the extension "mpe". If a
 * sample multi-page editor (also available as a template) is registered for the
 * same extension, it will be able to open it.
 */

public class NewLinkWizard extends Wizard implements INewWizard {
	private NewLinkWizardPage page;

	private ISelection selection;

	private ToK tok = null;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public NewLinkWizard() {
		super();
		setWindowTitle(Messages.getString("NewLinkWizard.0")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
		setHelpAvailable(false);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewLinkWizardPage(selection);
		addPage(page);
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {

		IProject tokProject = ResourcesPlugin.getWorkspace().getRoot().getProject(page.getProject());
		tok = new ToK(tokProject);
		String[] roots = page.getSourceFiles();
		 //TODO
		 try {
			for (int i = 0; i < roots.length; i++) {
				tok.linkDiscussionRoot(tok.getDiscussion(page.getDiscussion()),roots[i],page.getExcerptions(roots[i]), page.getSubject(),page.getLinkType());
			} 
		 
		 } catch (CoreException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		return true;
	}

	@SuppressWarnings("unused") //$NON-NLS-1$
	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "Yearly_Plugin_Project", //$NON-NLS-1$
				IStatus.OK, message, null);
		throw new CoreException(status);
	}
	
	
	
}