package lost.tok.opTable.actions;

import lost.tok.opTable.OperationTable;

import org.eclipse.jface.action.IAction;

public class SwitchOpTableViewAction extends AbstractEditorAction {

	public void run(IAction action) {
		assert (activeEditor != null);
		
		OperationTable ot = (OperationTable) activeEditor;
		
		if (action.isChecked()) {
			System.out.println("checked");
			ot.showDiscussions();
		} else {
			System.out.println("not checked");
			ot.hideDisucssions();
		}
		
	}

}
