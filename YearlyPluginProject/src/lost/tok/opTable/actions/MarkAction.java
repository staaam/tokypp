package lost.tok.opTable.actions;

import lost.tok.opTable.OperationTable;

import org.eclipse.jface.action.IAction;

public class MarkAction extends AbstractEditorAction {

	public static final String ACTION_ID = "lost.tok.opTable.MarkPopUpMenu.Action";

	public void run(IAction action) {
		assert (activeEditor != null);
		if (currentSelection == null || currentSelection.isEmpty()
				|| currentSelection.getLength() == 0) {
			return;
		}

		((OperationTable) activeEditor).mark(currentSelection);
	}
}
