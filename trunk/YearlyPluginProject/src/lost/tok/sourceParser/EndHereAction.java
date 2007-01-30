package lost.tok.sourceParser;

import lost.tok.opTable.AbstractEditorAction;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorActionDelegate;

public class EndHereAction extends AbstractEditorAction {

	public void run(IAction action) {
		assert (activeEditor != null);
		if (currentSelection == null)
			return;
		
		int offset = currentSelection.getLength() + currentSelection.getOffset();
		// TODO(Shay): Think about what to do if the selection is not empty;
		System.out.println("" + currentSelection.getOffset() + ":" + currentSelection.getText());
		((SourceParser)activeEditor).createNewChapter(offset);
	}

}
