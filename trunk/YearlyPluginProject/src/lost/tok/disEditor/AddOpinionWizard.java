package lost.tok.disEditor;

import lost.tok.Discussion;
import lost.tok.Messages;

import org.eclipse.jface.wizard.Wizard;

public class AddOpinionWizard extends Wizard {
	private AddOpinionWizardPage page;

	private DiscussionEditor discussionEditor = null;

	private Discussion discussion;

	private String opinionName = null;

	public AddOpinionWizard(DiscussionEditor discussionEditor) {
		super();

		this.discussionEditor = discussionEditor;
		discussion = discussionEditor.getDiscussion();

		setWindowTitle(Messages.getString("AddOpinionWizard.AddOpinion")); //$NON-NLS-1$
	}

	public AddOpinionWizard(Discussion discussion) {
		super();

		this.discussion = discussion;

		setWindowTitle(Messages.getString("AddOpinionWizard.AddOpinion")); //$NON-NLS-1$
	}

	@Override
	public void addPages() {
		page = new AddOpinionWizardPage(discussion);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		opinionName = page.getOpinion();
		if (discussionEditor == null) {
			discussion.addOpinion(opinionName);
		} else {
			discussionEditor.addOpinion(opinionName);
		}
		return true;
	}

	public String getOpinionName() {
		return opinionName;
	}
}
