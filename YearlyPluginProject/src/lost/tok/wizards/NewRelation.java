package lost.tok.wizards;

import lost.tok.Messages;

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

public class NewRelation extends Wizard implements INewWizard {
	private NewRelationPage page;

	private ISelection selection;

//	private ToK tok = null;

	/**
	 * Constructor for NewRelationWizard.
	 */
	public NewRelation() {
		super();
		setNeedsProgressMonitor(true);
		setHelpAvailable(false);
		setWindowTitle(Messages.getString("NewRelationWizard.0")); //$NON-NLS-1$
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewRelationPage(selection);
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

//		IStructuredSelection ssel = (IStructuredSelection) selection;
//		IResource resource = (IResource) ssel.getFirstElement();
//		IProject project = resource.getProject();
//		tok = ToK.getProjectToK(project);

		Integer[] ids = page.getSelectedQuotes();
		page.getDiscussion().createLink(
				ids[0], ids[1],
				page.getComment(),
				page.getRelationType());

		return true;
	}
}