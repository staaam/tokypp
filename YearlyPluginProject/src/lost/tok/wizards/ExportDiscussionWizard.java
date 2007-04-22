package lost.tok.wizards;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardExportResourcesPage;

public class ExportDiscussionWizard extends WizardExportResourcesPage implements IExportWizard 
 {

	protected ExportDiscussionWizard(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
		// TODO Auto-generated constructor stub
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	public void addPages() {
		// TODO Auto-generated method stub

	}

	public boolean canFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	public void createPageControls(Composite pageContainer) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public IWizardContainer getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	public Image getDefaultPageImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public IDialogSettings getDialogSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	public IWizardPage getNextPage(IWizardPage page) {
		// TODO Auto-generated method stub
		return null;
	}

	public IWizardPage getPage(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPageCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public IWizardPage[] getPages() {
		// TODO Auto-generated method stub
		return null;
	}

	public IWizardPage getPreviousPage(IWizardPage page) {
		// TODO Auto-generated method stub
		return null;
	}

	public IWizardPage getStartingPage() {
		// TODO Auto-generated method stub
		return null;
	}

	public RGB getTitleBarColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWindowTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isHelpAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean needsPreviousAndNextButtons() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean needsProgressMonitor() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean performCancel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setContainer(IWizardContainer wizardContainer) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createDestinationGroup(Composite parent) {
		// TODO Auto-generated method stub
		
	}

	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

}
