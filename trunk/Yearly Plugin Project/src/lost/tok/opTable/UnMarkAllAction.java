package lost.tok.opTable;

import org.eclipse.jface.action.IAction;

public class UnMarkAllAction extends AbstractOpTableAction {

	public void run(IAction action) {
		activeEditor.clearMarked();
	}

}
