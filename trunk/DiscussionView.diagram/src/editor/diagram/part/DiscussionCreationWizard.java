package editor.diagram.part;

import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.wizards.EditorCreationWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

/**
 * @generated
 */
public class DiscussionCreationWizard extends EditorCreationWizard {

	/**
	 * @generated
	 */
	public void addPages() {
		super.addPages();
		if (page == null) {
			page = new DiscussionCreationWizardPage(getWorkbench(),
					getSelection());
		}
		addPage(page);
	}

	/**
	 * @generated
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		setWindowTitle("New Discussion Diagram"); //$NON-NLS-1$
		setDefaultPageImageDescriptor(DiscussionDiagramEditorPlugin
				.getBundledImageDescriptor("icons/wizban/NewEditorWizard.gif")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}
}
