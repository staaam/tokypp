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

import java.util.List;

import lost.tok.Excerption;
import lost.tok.Messages;
import lost.tok.Quote;
import lost.tok.Source;
import lost.tok.ToK;
import lost.tok.opTable.OperationTable;
import lost.tok.opTable.wizards.AddQuoteWizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The action of adding a new quote from the chosen excerptions
 */
public class AddQuoteAction extends AbstractEditorAction {

	private static IAction action;

	public void run(IAction action) {
		AddQuoteAction.action = action;

		OperationTable operationTable = (OperationTable) activeEditor;
		
		if (operationTable.isRootDiscussionsView()) {
			messageBox(
					Messages.getString("AddQuoteAction.Error"), //$NON-NLS-1$
					Messages.getString("Action.Disabled")); //$NON-NLS-1$
			return;
		}
			
		
		List<Excerption> excerptions = operationTable.getExcerptions();

		if (excerptions.isEmpty()) {
			messageBox(
					Messages.getString("AddQuoteAction.Error"), //$NON-NLS-1$
					Messages.getString("AddQuoteAction.NoMarkedText")); //$NON-NLS-1$
			return;
		}

		if (!(activeEditor.getEditorInput() instanceof FileEditorInput)) {
			messageBox(
					Messages.getString("AddQuoteAction.Error"), //$NON-NLS-1$
					Messages.getString("AddQuoteAction.SourceNotLinkedToProject")); //$NON-NLS-1$
			return;
		}

		FileEditorInput fileEditorInput = (FileEditorInput) activeEditor
				.getEditorInput();
		IFile file = fileEditorInput.getFile();
		IProject project = file.getProject();

		Quote q = new Quote(new Source(file), excerptions);

		System.out.println(q.getText());

		addQuote(ToK.getProjectToK(project), q);
	}

	private void addQuote(ToK tok, Quote q) {
		AddQuoteWizard w = new AddQuoteWizard(tok, q);

		WizardDialog wd = new WizardDialog(new Shell(), w);
		wd.setBlockOnOpen(true);

		wd.open();

		if (w.finished()) {
			((OperationTable) activeEditor).clearMarked();
		}
	}

	static public IAction getQuoteAction() {
		return action;
	}

}
