package lost.tok.opTable.actions;

import lost.tok.opTable.OperationTable;

import org.eclipse.jface.action.IAction;

public class UnMarkAllAction extends AbstractEditorAction {

	public void run(IAction action) {
		((OperationTable) activeEditor).clearMarked();
	}

}
