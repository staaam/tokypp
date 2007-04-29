package lost.tok.wizards;

import lost.tok.Messages;
import lost.tok.ToK;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewDiscussion extends Wizard implements INewWizard {
	private NewDiscussionPage page;

	private IProject project = null;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public NewDiscussion() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("NewDiscussionWizard.0")); //$NON-NLS-1$
	}

	public NewDiscussion(IProject project) {
		this();
		setProject(project);
	}
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewDiscussionPage();
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {
		ToK.getProjectToK(project).addDiscussion(page.getDiscussionName());
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		if (project != null) return;
		
		if (selection == null || 
			selection.isEmpty() ||
			selection.size() > 1) return;

		Object obj = selection.getFirstElement();
		if (obj instanceof IResource) {
			IResource resource = (IResource) obj;
			project = resource.getProject();
		}
	}

	private void setProject(IProject project) {
		this.project = project;
	}
	
	public IProject getProject() {
		return project;
	}
	
	public String getDiscussionName() {
		return page.getDiscussionName();
	}
}
