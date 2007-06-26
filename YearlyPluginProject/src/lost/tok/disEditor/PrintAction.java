package lost.tok.disEditor;

import lost.tok.print.PrintDiscussion;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;

public class PrintAction extends Action {
	
	private IEditorPart editor;
	
	@Override
	public void run() {
		new PrintDiscussion(editor.getSite().getShell(), ((DiscussionEditor)editor).getDiscussion());
	}
	
	public void setActiveEditor(IEditorPart ep){
		editor=ep;
	}
};