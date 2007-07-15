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

package lost.tok.disEditor;

import java.util.HashSet;

import lost.tok.Discussion;
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
	Discussion discussion;

	HashSet<String> opinions = new HashSet<String>();

	public AddOpinionWizardPage(Discussion discussion) {
		super(Messages.getString("AddOpinionWizardPage.AddOpinion")); //$NON-NLS-1$
		this.discussion = discussion;

		setTitle(Messages.getString("AddOpinionWizardPage.AddOpinion")); //$NON-NLS-1$

		for (String opinion : discussion.getOpinionNames()) {
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

		new Label(composite, SWT.NONE).setText(Messages
				.getString("AddOpinionWizardPage.Opinion")); //$NON-NLS-1$
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
			updateStatus(Messages
					.getString("AddOpinionWizardPage.ErrOpinionMustNotBeEmpty")); //$NON-NLS-1$
		} else if (opinions.contains(opinionArea.getText())
				|| opinionArea.getText().equals(Discussion.DEFAULT_OPINION_XML)) {
			updateStatus(Messages
					.getString("AddOpinionWizardPage.ErrOpinionExists")); //$NON-NLS-1$
		} else {
			updateStatus(null);
		}
	}
}
