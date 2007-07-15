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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public class AddOpinionAction implements IEditorActionDelegate {

	private DiscussionEditor targetEditor;

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof DiscussionEditor) {
			this.targetEditor = (DiscussionEditor) targetEditor;
		}
	}

	public void run(IAction action) {
		AddOpinionWizard w = new AddOpinionWizard(targetEditor);

		WizardDialog wd = new WizardDialog(new Shell(), w);
		wd.setBlockOnOpen(true);

		wd.open();

		// targetEditor.updatePartControl(targetEditor.getEditorInput());
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
