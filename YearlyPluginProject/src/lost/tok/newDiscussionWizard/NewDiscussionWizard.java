package lost.tok.newDiscussionWizard;

import lost.tok.Messages;
import lost.tok.ToK;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewDiscussionWizard extends Wizard implements INewWizard {
	private NewDiscussionWizardPage page;

	private ISelection selection;

	private ToK tok;

	private IProject project = null;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public NewDiscussionWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("NewDiscussionWizard.0")); //$NON-NLS-1$
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewDiscussionWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {
		tok = getToK(selection);
		tok.addDiscussion(page.getDiscussionName());
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	private ToK getToK(ISelection selection) {
		if (project == null) {
			if (selection != null && selection.isEmpty() == false
					&& selection instanceof IStructuredSelection) {
				IStructuredSelection ssel = (IStructuredSelection) selection;
				if (ssel.size() > 1) {
					return null;
				}
				Object obj = ssel.getFirstElement();
				if (obj instanceof IResource) {
					IResource resource = (IResource) obj;
					project = resource.getProject();
				}
			}
		}
		return ToK.getProjectToK(project);
	}

}
