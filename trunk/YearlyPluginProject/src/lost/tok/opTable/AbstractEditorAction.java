package lost.tok.opTable;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;

public abstract class AbstractEditorAction implements IEditorActionDelegate {

	protected TextEditor activeEditor;

	protected TextSelection currentSelection;

	public AbstractEditorAction() {
		activeEditor = null;
		currentSelection = null;
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		assert (targetEditor instanceof TextEditor);
		activeEditor = (TextEditor) targetEditor;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		assert (selection instanceof TextSelection);
		currentSelection = (TextSelection) selection;
	}

}