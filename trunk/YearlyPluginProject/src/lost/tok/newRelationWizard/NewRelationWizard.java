package lost.tok.newRelationWizard;

import lost.tok.Discussion;
import lost.tok.Messages;
import lost.tok.ToK;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * New relation creation wizard
 * 
 * @author Team Lost
 * 
 */

public class NewRelationWizard extends Wizard implements INewWizard {
	private NewRelationWizardPage page;

	private ISelection selection;

	private ToK tok = null;

	/**
	 * Constructor for NewRelationWizard.
	 */
	public NewRelationWizard() {
		super();
		setNeedsProgressMonitor(true);
		setHelpAvailable(false);
		setWindowTitle(Messages.getString("NewRelationWizard.0")); //$NON-NLS-1$
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewRelationWizardPage(selection);
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

		IStructuredSelection ssel = (IStructuredSelection) selection;
		IResource resource = (IResource) ssel.getFirstElement();
		IProject project = resource.getProject();
		tok = ToK.getProjectToK(project);

		try {
			Integer[] ids = page.getSelectedQuotes();
			Discussion disc = tok.getDiscussion(page.getDiscName());
			disc.createLink(ids[0], ids[1], page.getComment(), page
					.getRelationType());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}