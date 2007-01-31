package lost.tok.sourceParser;

import lost.tok.opTable.AbstractEditorAction;

import org.eclipse.jface.action.IAction;

public class EndHereAction extends AbstractEditorAction {

	public void run(IAction action) {
		assert (activeEditor != null);

		SourceParser sp = (SourceParser)activeEditor; 
		int offset = sp.getCaretLocation(); 
		sp.openNewChapterDialog(offset);	
	}

}
