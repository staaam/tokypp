package lost.tok.newDiscussionWizard;

import java.util.LinkedList;

import lost.tok.Messages;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.internal.ide.dialogs.SimpleListContentProvider;

public class NewDiscussionWizardPage extends WizardPage implements ModifyListener {
	/** The full name of the source */
	private Text name;

	private ISelection selection;

	/**
	 * Constructor for UnparsedDocWizardPage.
	 */
	public NewDiscussionWizardPage(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.getString("NewDiscussionWizardPage.1")); //$NON-NLS-1$
		setDescription(Messages.getString("NewDiscussionWizardPage.2")); //$NON-NLS-1$
		this.selection = selection;
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
		
		String discussionName = getDiscussionName();
		
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
	
	public String getDiscussionName() {
		return name.getText();
	}

}