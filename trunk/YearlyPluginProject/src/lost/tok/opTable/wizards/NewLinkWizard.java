package lost.tok.opTable.wizards;

import lost.tok.Messages;
import lost.tok.ToK;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * A wizard that creates link between discussion and roots
 * 
 * @author Team Lost
 * 
 */

public class NewLinkWizard extends Wizard implements INewWizard {
	private NewLinkWizardPage page;

	private ToK tok = null;

	/**
	 * Constructor for NewLinkWizard.
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
		page = new NewLinkWizardPage();
		addPage(page);
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {
		tok = page.getTok();
		String[] roots = page.getSourceFiles();
		// TODO
		try {
			for (String root : roots) {
				tok.linkDiscussionRoot(tok.getDiscussion(page.getDiscussion()),
						tok.getSource(root),
						page.getExcerptions(root), 
						page.getSubject(),
						page.getLinkType());
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