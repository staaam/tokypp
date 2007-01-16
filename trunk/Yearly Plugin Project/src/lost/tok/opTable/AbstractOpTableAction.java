package lost.tok.opTable;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public abstract class AbstractOpTableAction implements IEditorActionDelegate {

	protected OperationTable activeEditor;

	protected TextSelection currentSelection;

	public AbstractOpTableAction() {
		activeEditor = null;
		currentSelection = null;
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		assert (targetEditor instanceof OperationTable);
		activeEditor = (OperationTable) targetEditor;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		assert (selection instanceof TextSelection);
		currentSelection = (TextSelection) selection;
	}

}