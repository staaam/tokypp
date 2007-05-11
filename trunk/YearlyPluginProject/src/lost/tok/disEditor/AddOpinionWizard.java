package lost.tok.disEditor;

import lost.tok.Messages;

import org.eclipse.jface.wizard.Wizard;

public class AddOpinionWizard extends Wizard {
	private AddOpinionWizardPage page;

	private DiscussionEditor discussionEditor;

	public AddOpinionWizard(DiscussionEditor discussionEditor) {
		super();

		this.discussionEditor = discussionEditor;

		setWindowTitle(Messages.getString("AddOpinionWizard.AddOpinion")); //$NON-NLS-1$

		page = new AddOpinionWizardPage(discussionEditor);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		discussionEditor.addOpinion(page.getOpinion());
		return true;
	}
}
