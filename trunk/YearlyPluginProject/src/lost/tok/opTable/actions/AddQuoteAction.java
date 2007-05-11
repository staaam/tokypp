package lost.tok.opTable.actions;

import java.util.List;

import lost.tok.Excerption;
import lost.tok.Quote;
import lost.tok.Source;
import lost.tok.ToK;
import lost.tok.opTable.Messages;
import lost.tok.opTable.OperationTable;
import lost.tok.opTable.wizards.AddQuoteWizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The action of adding a new quote from the chosen excerptions
 */
public class AddQuoteAction extends AbstractEditorAction {
	public void run(IAction action) {
		List<Excerption> excerptions = ((OperationTable) activeEditor)
				.getMarked();
		System.out.println("The are " + excerptions.size() + " Excerptions:"); //$NON-NLS-1$ //$NON-NLS-2$
		for (Excerption e : excerptions) {
			System.out.println(e);
		}

		// List<Discussion> l = tok.getDiscussions();
		if (excerptions.isEmpty()) {
			messageBox(
					Messages.getString("AddQuoteAction.Error"), Messages.getString("AddQuoteAction.NoMarkedText")); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		if (!(activeEditor.getEditorInput() instanceof FileEditorInput)) {
			messageBox(
					Messages.getString("AddQuoteAction.Error"), Messages.getString("AddQuoteAction.SourceNotLinkedToProject")); //$NON-NLS-1$ //$NON-NLS-2$
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
		if (tok.getDiscussions().isEmpty()) {
			messageBox("Error", "No discussions found");
			return;

			// TODO: First iteration only
			// tok.addDiscussion("Test Discussion"); //$NON-NLS-1$
		}

		AddQuoteWizard w = new AddQuoteWizard(tok, q);

		WizardDialog wd = new WizardDialog(new Shell(), w);
		wd.setBlockOnOpen(true);

		wd.open();

		if (w.finished()) {
			((OperationTable) activeEditor).clearMarked();
		}
	}

	void messageBox(String title, String message) {
		MessageBox mb = new MessageBox(activeEditor.getSite().getShell());
		mb.setText(title);
		mb.setMessage(message);
		mb.open();
	}

}
