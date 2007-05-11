package lost.tok.disEditor;

import java.util.HashSet;

import lost.tok.Messages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddOpinionWizardPage extends WizardPage implements ModifyListener {
	DiscussionEditor discussionEditor;

	HashSet<String> opinions = new HashSet<String>();

	public AddOpinionWizardPage(DiscussionEditor discussionEditor) {
		super(Messages.getString("AddOpinionWizardPage.AddOpinion")); //$NON-NLS-1$
		this.discussionEditor = discussionEditor;

		setTitle(Messages.getString("AddOpinionWizardPage.AddOpinion")); //$NON-NLS-1$

		for (String opinion : discussionEditor.getDiscussion()
				.getOpinionNames()) {
			opinions.add(opinion);
		}
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getOpinion() {
		return opinionArea.getText();
	}

	private Text opinionArea = null;

	public void createControl(Composite parent) {
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		GridLayout gridLayout = new GridLayout(2, false);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(gridLayout);

		new Label(composite, SWT.NONE).setText(Messages.getString("AddOpinionWizardPage.Opinion")); //$NON-NLS-1$
		opinionArea = new Text(composite, SWT.SINGLE | SWT.BORDER);
		opinionArea.setLayoutData(gridData);

		opinionArea.addModifyListener(this);

		dialogChanged();
		setControl(parent);
	}

	public void modifyText(ModifyEvent e) {
		dialogChanged();
	}

	private void dialogChanged() {
		if (opinionArea.getText().length() == 0) {
			updateStatus(Messages.getString("AddOpinionWizardPage.ErrOpinionMustNotBeEmpty")); //$NON-NLS-1$
		} else if (opinions.contains(opinionArea.getText())) {
			updateStatus(Messages.getString("AddOpinionWizardPage.ErrOpinionExists")); //$NON-NLS-1$
		} else {
			updateStatus(null);
		}
	}
}
