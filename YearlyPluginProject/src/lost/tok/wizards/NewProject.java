package lost.tok.wizards;

import lost.tok.ToK;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

/**
 * This is a new project wizard. Its role is to create a new file resource in
 * the provided container. If the container resource (a folder or a project) is
 * selected in the workspace when the wizard is opened, it will accept it as the
 * target container.
 */

public class NewProject extends Wizard implements INewWizard,
		IExecutableExtension {
	private NewProjectPage page;

	private ISelection selection;

	private IConfigurationElement configElm;

	/**
	 * Constructor new project.
	 */
	public NewProject() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewProjectPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {
		new ToK(page.getProjectName(), page.getCreatorName(), page
				.getRootName());

		// We need this in order to prompt the perspective change
		// when the New Project Wizard is finished
		BasicNewProjectResourceWizard.updatePerspective(configElm);

		return true;
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
	 * This method is the method of the IExecutableExtension interface that this
	 * class implements. We need this in order to prompt the perspective change
	 * when the New Project Wizard is finished. (also prompts the Dialog asking
	 * if we want to switch perspective)
	 */
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		configElm = config;

	}
}