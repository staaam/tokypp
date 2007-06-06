package lost.tok.sourceParser;

import lost.tok.opTable.actions.AbstractEditorAction;

import org.eclipse.jface.action.IAction;

/**
 * The action that ends a chapter at the current location of the cursor
 */
public class EndHereAction extends AbstractEditorAction {

	/**
	 * Gets the current location of the cursor and calls to the method which
	 * opens a new dialog
	 */
	public void run(IAction action) {
		assert (activeEditor != null);

		SourceParser sp = (SourceParser) activeEditor;
		int offset = sp.getCaretLocation();
		sp.openNewChapterDialog(offset);
	}

}
