package lost.tok.opTable;

import java.util.LinkedList;
import java.util.List;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.Quote;
import lost.tok.ToK;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.FileEditorInput;

public class AddQuoteAction extends AbstractOpTableAction {
	public void run(IAction action) {
		List<Excerption> excerptions = activeEditor.getMarked();
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

		Quote q = new Quote(file.getProjectRelativePath().toPortableString(),
				excerptions);

		System.out.println(q.getText());

		addQuote(ToK.getProjectToK(project), q);
		// d.
	}

	private void addQuote(ToK tok, Quote q) {
		List<Discussion> discussions = tok.getDiscussions(); // - all
		// discussions
		if (discussions.isEmpty()) {
			// messageBox("Error", "No discuttions found");
			// return;

			// TODO: First iteration only
			tok.addDiscussion("Test Discussion"); //$NON-NLS-1$
			discussions = tok.getDiscussions();
		}

		List<String> discStrings = new LinkedList<String>();
		for (Discussion discussion : discussions) {
			discStrings.add(discussion.getDiscName());
		}

		List<String> opinStrings = new LinkedList<String>();
		// tok.getOpinions(); - all opinions
		// TODO: First iteration only
		opinStrings.add(Discussion.DEFAULT_OPINION);

		AddQuoteWizard w = new AddQuoteWizard();
		w.createAddQuoteWizard(discStrings, opinStrings, q.getText());
		if (!w.run()) {
			return;
		}

		q.setComment(w.getComment());
		try {
			Discussion discussion = tok.getDiscussion(w.getDiscussion());
			discussion.addQuote(q, w.getOpinion());
		} catch (CoreException e) {
			messageBox(
					Messages.getString("AddQuoteAction.Error"), Messages.getString("AddQuoteAction.DiscussionNotExists")); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		activeEditor.clearMarked();
	}

	void messageBox(String title, String message) {
		MessageBox mb = new MessageBox(activeEditor.getSite().getShell());
		mb.setText(title);
		mb.setMessage(message);
		mb.open();
	}

}
