package lost.tok.opTable;

import lost.tok.excerptionsView.ExcerptionView;

import org.eclipse.jface.action.IAction;

public class UnMarkAllAction extends AbstractEditorAction {

	public void run(IAction action) {
		((OperationTable) activeEditor).clearMarked();
		ExcerptionView.getView().clear();
	}

}
