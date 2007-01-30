package lost.tok.opTable;

import org.eclipse.jface.action.IAction;

public class MarkAction extends AbstractEditorAction {

	public void run(IAction action) {
		assert (activeEditor != null);
		if (currentSelection == null || currentSelection.isEmpty()
				|| currentSelection.getLength() == 0)
			return;

		((OperationTable)activeEditor).mark(currentSelection);
	}

}
