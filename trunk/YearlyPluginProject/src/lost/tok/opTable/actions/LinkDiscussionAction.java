package lost.tok.opTable.actions;

import lost.tok.Source;
import lost.tok.excerptionsView.ExcerptionView;
import lost.tok.opTable.OperationTable;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The action of linking a discussion to the currently marked text
 */
public class LinkDiscussionAction extends AbstractEditorAction {
	
	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		super.setActiveEditor(action, targetEditor);
		if (activeEditor instanceof OperationTable) {
			OperationTable ot = (OperationTable) activeEditor;
			
			action.setEnabled(new Source(((FileEditorInput)ot.getEditorInput()).getFile()).isRoot());
		}
	}

	public void run(IAction action) {
		assert (activeEditor != null);

		ExcerptionView.getView().linkDiscussion();
	}
}
