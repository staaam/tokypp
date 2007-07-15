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

package lost.tok.wizards;

import lost.tok.Messages;
import lost.tok.ToK;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewDiscussionPage extends WizardPage implements ModifyListener {
	/** The full name of the source */
	private Text name;
	private String discussionName;

	private Text description;
	private String discussionDescription;
	/**
	 * Constructor for UnparsedDocWizardPage.
	 */
	public NewDiscussionPage() {
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
		layout.numColumns = 2;
		layout.verticalSpacing = 9;

		// Title selection
		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewDiscussionWizardPage.3")); //$NON-NLS-1$

		name = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(gd);
		name.addModifyListener(this);

		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewDiscussionPage.Description") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		description = new Text(container, SWT.BORDER | SWT.MULTI| SWT.WRAP | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 100;
		description.setLayoutData(gd);
		description.addModifyListener(this);

		// Last:
		dialogChanged();
		setControl(container);
	}

	/**
	 * Ensures that thr text field is set.
	 */

	private void dialogChanged() {
		discussionName = name.getText();
		discussionDescription = description.getText();

		if (discussionName.length() == 0) {
			updateStatus(Messages.getString("NewDiscussionPage.ErrNoDiscName")); //$NON-NLS-1$
			return;
		}

		// TODO: Check whether the given discussion name is valid

		if (discussionExists(discussionName)) {
			updateStatus(Messages
					.getString("NewDiscussionPage.ErrDiscAlreadyExist")); //$NON-NLS-1$
			return;
		}

		updateStatus(null);
	}

	private boolean discussionExists(String name) {
		if (getWizard() != null && (getWizard() instanceof NewDiscussion)) {
			NewDiscussion wizard = (NewDiscussion) getWizard();
			try {
				return (null != ToK.getProjectToK(wizard.getProject())
						.getDiscussion(name));
			} catch (CoreException e) {
			}
		}
		return false;
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public void modifyText(ModifyEvent e) {
		dialogChanged();
	}

	public String getDiscussionName() {
		return discussionName;
	}

	public String getDiscussionDescription() {
		return discussionDescription;
	}
}