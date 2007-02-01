package lost.tok.sourceParser.wizards;

//import lost.tok.ToK;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class UnparsedDocWizard extends Wizard implements INewWizard {
	private UnparsedDocWizardPage page;

	private ISelection selection;

	//private ToK tok;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public UnparsedDocWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new UnparsedDocWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {
		// Shay(TODO)
/*		tok = new ToK();
		try {
			tok.createToKProject(page.getProjectName(), page.getCreatorName(),
					page.getRootName());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}
