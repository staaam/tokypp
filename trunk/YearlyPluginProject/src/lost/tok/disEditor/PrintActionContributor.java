package lost.tok.disEditor;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

public class PrintActionContributor extends EditorActionBarContributor{

	private PrintAction printAction; 
	
	public PrintActionContributor() {
		printAction = new PrintAction();
	}
	
	@Override
	public void setActiveEditor(IEditorPart targetEditor) {
		super.setActiveEditor(targetEditor);
		IActionBars actionBars= getActionBars();
	    if (actionBars == null)
	    	return;
	    printAction.setActiveEditor((DiscussionEditor)targetEditor);
	    actionBars.setGlobalActionHandler(ActionFactory.PRINT.getId(), printAction);
	    actionBars.updateActionBars();

	}
}
