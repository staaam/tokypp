package lost.tok.wizards;

import java.util.List;

import lost.tok.ToK;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.wizards.datatransfer.ZipFileExportWizard;

// TODO: Auto-generated Javadoc
/**
 * The Class ExportDiscussion.
 */
public class ExportDiscussion extends ZipFileExportWizard
 {

	//private ExportDiscussionPage page;

	/** The selection. */
	private ISelection selection;

	/** The tok. */
	private ToK tok;

	/** The project. */
	private IProject project = null;
	
	private ExportDiscussionPage exportPage;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		this.selection = currentSelection;
        List selectedResources = IDE.computeSelectedResources(currentSelection);
        if (!selectedResources.isEmpty()) {
            this.selection = new StructuredSelection(selectedResources);
        }

        setDefaultPageImageDescriptor(IDEWorkbenchPlugin.getIDEImageDescriptor("wizban/exportzip_wiz.png"));//$NON-NLS-1$
        setNeedsProgressMonitor(true);
		setWindowTitle("Export Discussions");
	}

	@Override
	public void addPages() {
		//super.addPages();
		exportPage = new ExportDiscussionPage((IStructuredSelection) selection);
		addPage(exportPage);
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return exportPage.finish();
	}
	
}
