package lost.tok.opTable.actions;

import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.opTable.OperationTable;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Action for 'Show root discussions' button
 * 
 * @author Michael Gelfand
 *
 */
public class SwitchOpTableViewAction extends AbstractEditorAction {

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		super.setActiveEditor(action, targetEditor);
		OperationTable ot = (OperationTable) activeEditor;
		action.setChecked(ot.isRootDiscussionsView());
	}
	
	public void run(IAction action) {
		assert (activeEditor != null);
		
		//return an error messege if called from a source that is not a root
		if (((FileEditorInput)activeEditor.getEditorInput()).getFile().getProjectRelativePath().toPortableString().startsWith(ToK.SOURCES_FOLDER)){
			messageBox(
					Messages.getString("AddQuoteAction.Error"), Messages.getString("SwitchOpTableViewAction.0")); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		
		OperationTable ot = (OperationTable) activeEditor;
		
//		activeEditor.getAction("lost.tok.opTable.PopUpMenu.switchView").setChecked(action.isChecked());
//		activeEditor.getAction("lost.tok.opTable.switchView").setChecked(action.isChecked());
		//IContributionItem a = activeEditor.getEditorSite().getActionBars().getToolBarManager().find("lost.tok.opTable.switchView");
		//((PluginActionContributionItem)a).getAction().setChecked(action.isChecked());
		if (action.isChecked()) {
			System.out.println("checked"); //$NON-NLS-1$
			ot.showDiscussions();
		} else {
			System.out.println("not checked"); //$NON-NLS-1$
			ot.hideDisucssions();
		}
		
	}
	
	void messageBox(String title, String message) {
		MessageBox mb = new MessageBox(activeEditor.getSite().getShell());
		mb.setText(title);
		mb.setMessage(message);
		mb.open();
	}

}
