package lost.tok.wizards;

import java.util.List;

import lost.tok.Messages;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.wizards.datatransfer.ZipFileExportWizard;


/**
 * The Class ExportDiscussion.
 */
public class ExportDiscussion extends ZipFileExportWizard {

	/** The selection. */
	private ISelection selection;

	/** The export page. */
	private ExportDiscussionPage exportPage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.wizards.datatransfer.ZipFileExportWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		this.selection = currentSelection;
		List selectedResources = IDE.computeSelectedResources(currentSelection);
		if (!selectedResources.isEmpty()) {
			this.selection = new StructuredSelection(selectedResources);
		}

		setDefaultPageImageDescriptor(IDEWorkbenchPlugin
				.getIDEImageDescriptor("wizban/exportzip_wiz.png"));//$NON-NLS-1$
		setNeedsProgressMonitor(true);

		setWindowTitle(Messages.getString("ExportDiscussion.0")); //$NON-NLS-1$
		setWindowTitle(Messages.getString("ExportDiscussion.exportDiscs")); //$NON-NLS-1$

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.wizards.datatransfer.ZipFileExportWizard#addPages()
	 */
	@Override
	public void addPages() {
		// super.addPages();
		exportPage = new ExportDiscussionPage((IStructuredSelection) selection);
		addPage(exportPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.wizards.datatransfer.ZipFileExportWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return exportPage.finish();
	}

}
