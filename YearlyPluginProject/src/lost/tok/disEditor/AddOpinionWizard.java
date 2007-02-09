package lost.tok.disEditor;

import org.eclipse.jface.wizard.Wizard;

public class AddOpinionWizard extends Wizard {
	private AddOpinionWizardPage page;

	private DiscussionEditor discussionEditor;

	public AddOpinionWizard(DiscussionEditor discussionEditor) {
		super();

		this.discussionEditor = discussionEditor;

		setWindowTitle("Add Opinion");

		page = new AddOpinionWizardPage(discussionEditor);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		discussionEditor.addOpinion(page.getOpinion());
		return true;
	}
}
