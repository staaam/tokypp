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
import lost.tok.opTable.OperationTable;

import org.eclipse.jface.action.IAction;

public class MarkAction extends AbstractEditorAction {

	public static final String ACTION_ID = "lost.tok.opTable.MarkPopUpMenu.Action"; //$NON-NLS-1$

	public void run(IAction action) {
		assert (activeEditor != null);
		if (currentSelection == null || currentSelection.isEmpty()
				|| currentSelection.getLength() == 0) {
			return;
		}

		OperationTable operationTable = (OperationTable) activeEditor;
		
		if (operationTable.isRootDiscussionsView()) {
			messageBox(
					Messages.getString("AddQuoteAction.Error"), //$NON-NLS-1$
					Messages.getString("Action.Disabled")); //$NON-NLS-1$
			return;
		}
			
		operationTable.mark(currentSelection,
				action.getId().indexOf("Unmark") == -1); //$NON-NLS-1$
	}
}
