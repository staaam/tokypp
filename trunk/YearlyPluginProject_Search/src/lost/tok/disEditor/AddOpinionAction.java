package lost.tok.disEditor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public class AddOpinionAction implements IEditorActionDelegate {

	private DiscussionEditor targetEditor;

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof DiscussionEditor) {
			this.targetEditor = (DiscussionEditor) targetEditor;
		}
	}

	public void run(IAction action) {
		AddOpinionWizard w = new AddOpinionWizard(targetEditor);

		WizardDialog wd = new WizardDialog(new Shell(), w);
		wd.setBlockOnOpen(true);

		wd.open();

		// targetEditor.updatePartControl(targetEditor.getEditorInput());
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
