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