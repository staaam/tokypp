package lost.tok.opTable.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;

/**
 * A basic actions on an editor Keeps track of the active editor and current
 * selection
 */
public abstract class AbstractEditorAction implements IEditorActionDelegate {

	protected TextEditor activeEditor;

	protected TextSelection currentSelection;

	/** C'tor */
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

	void messageBox(String title, String message) {
		MessageBox mb = new MessageBox(activeEditor.getSite().getShell());
		mb.setText(title);
		mb.setMessage(message);
		mb.open();
	}

}