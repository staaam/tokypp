/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

package lost.tok.opTable.actions;

import lost.tok.Messages;
import lost.tok.Source;
import lost.tok.opTable.OperationTable;

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
		if (activeEditor instanceof OperationTable) {
			OperationTable ot = (OperationTable) activeEditor;

			if (new Source(((FileEditorInput) ot.getEditorInput()).getFile())
					.isRoot()) {
				action.setEnabled(true);
				action.setChecked(ot.isRootDiscussionsView());
			} else {
				action.setEnabled(false);
			}
		}
	}
	
	
	/**
	 * The actual run method
	 * Split from run in order to easily catch and print runtime exceptions
	 */
	private void actualRun(IAction action)
	{
		// return an error messege if called from a source that is not a root
		if (!new Source(((FileEditorInput) activeEditor.getEditorInput()).getFile()).isRoot()) {
			messageBox(
					Messages.getString("AddQuoteAction.Error"), //$NON-NLS-1$
					Messages.getString("SwitchOpTableViewAction.0")); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		OperationTable ot = (OperationTable) activeEditor;

		// activeEditor.getAction("lost.tok.opTable.PopUpMenu.switchView").setChecked(action.isChecked());
		// activeEditor.getAction("lost.tok.opTable.switchView").setChecked(action.isChecked());
		// IContributionItem a =
		// activeEditor.getEditorSite().getActionBars().getToolBarManager().find("lost.tok.opTable.switchView");
		// ((PluginActionContributionItem)a).getAction().setChecked(action.isChecked());
		if (action.isChecked()) {
			System.out.println("checked"); //$NON-NLS-1$
			ot.showDiscussions();
			if (AddQuoteAction.getQuoteAction() != null)
				AddQuoteAction.getQuoteAction().setEnabled(false);
		} else {
			System.out.println("not checked"); //$NON-NLS-1$
			ot.hideDisucssions();
			if (AddQuoteAction.getQuoteAction() != null)
				AddQuoteAction.getQuoteAction().setEnabled(true);
		}
	}

	public void run(IAction action) {	
		try {
			assert (activeEditor != null);
			actualRun(action);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
