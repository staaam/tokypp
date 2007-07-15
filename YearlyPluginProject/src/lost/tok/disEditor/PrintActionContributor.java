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
