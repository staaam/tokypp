package lost.tok.opTable.actions;

import lost.tok.Messages;
import lost.tok.opTable.OperationTable;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;

public class SwitchOpTableViewAction extends AbstractEditorAction {

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		super.setActiveEditor(action, targetEditor);
		OperationTable ot = (OperationTable) activeEditor;
		action.setChecked(ot.isRootDiscussionsView());
	}
	
	public void run(IAction action) {
		assert (activeEditor != null);
		
		OperationTable ot = (OperationTable) activeEditor;
		
		if (action.isChecked()) {
			System.out.println("checked"); //$NON-NLS-1$
			ot.showDiscussions();
		} else {
			System.out.println("not checked"); //$NON-NLS-1$
			ot.hideDisucssions();
		}
		
	}

}
