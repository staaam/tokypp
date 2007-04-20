package lost.tok.opTable;

import org.eclipse.jface.action.IAction;

public class SwitchOpTableView extends AbstractEditorAction {

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
