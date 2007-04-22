package lost.tok.wizards;

import lost.tok.Messages;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewDiscussionPage extends WizardPage implements
		ModifyListener {
	/** The full name of the source */
	private Text name;
	private String discussionName;

	/**
	 * Constructor for UnparsedDocWizardPage.
	 */
	public NewDiscussionPage(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.getString("NewDiscussionWizardPage.1")); //$NON-NLS-1$
		setDescription(Messages.getString("NewDiscussionWizardPage.2")); //$NON-NLS-1$
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;

		// Title selection
		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewDiscussionWizardPage.3")); //$NON-NLS-1$

		name = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(gd);
		name.addModifyListener(this);

		// Last:
		dialogChanged();
		setControl(container);
	}

	/**
	 * Ensures that thr text field is set.
	 */

	private void dialogChanged() {

		discussionName = updateDiscussionName();
		if (discussionName.length() == 0) {
			updateStatus("Discussion name must be specified"); //$NON-NLS-1$
			return;
		}

		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public void modifyText(ModifyEvent e) {
		dialogChanged();
	}

	public String updateDiscussionName() {
		return name.getText();
	}
	
	public String getDiscussionName() {
		return discussionName;
	}

}