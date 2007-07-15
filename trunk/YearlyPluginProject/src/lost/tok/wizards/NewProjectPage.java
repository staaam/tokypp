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

import java.io.File;

import lost.tok.Messages;
import lost.tok.Source;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
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

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class NewProjectPage extends WizardPage {
	private Text rootText;

	private Text fileText;

	private Text creatorText;

	// private ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewProjectPage(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.getString("NewToKWizTitle")); //$NON-NLS-1$
		setDescription(Messages.getString("NewToKWizDescription")); //$NON-NLS-1$
		// this.selection = selection;
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

		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewToKWizReqProjName")); //$NON-NLS-1$

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewToKWizReqCreatorName")); //$NON-NLS-1$
		creatorText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		creatorText.setLayoutData(gd);
		creatorText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewToKWizReqRootFile")); //$NON-NLS-1$
		rootText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		rootText.setLayoutData(gd);
		rootText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$

		Button button = new Button(container, SWT.PUSH);
		button.setText(Messages.getString("NewToKWizBrowseCmd")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		// initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */
	private void handleBrowse() {
		FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
		fd.setText(Messages.getString("NewToKWizOpenCmd")); //$NON-NLS-1$
		fd.setFilterPath(null); //$NON-NLS-1$
		String[] filterExt = { "*." + Source.SOURCE_EXTENSION }; //$NON-NLS-1$
		// , "*.xml", "*.txt", "*.doc", ".rtf", "*.*" }; //$NON-NLS-1$
		// //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		rootText.setText(selected);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	// private void initialize() {
	// if (selection != null && selection.isEmpty() == false
	// && selection instanceof IStructuredSelection) {
	// IStructuredSelection ssel = (IStructuredSelection) selection;
	// if (ssel.size() > 1) {
	// return;
	// }
	// Object obj = ssel.getFirstElement();
	// if (obj instanceof IResource) {
	// IContainer container;
	// if (obj instanceof IContainer) {
	// container = (IContainer) obj;
	// } else {
	// container = ((IResource) obj).getParent();
	// }
	// rootText.setText(container.getFullPath().toString());
	// }
	// }
	// }
	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {

		String projectName = getProjectName();
		String creatorName = getCreatorName();
		String rootName = getRootName();

		if (projectName.length() == 0) {
			updateStatus(Messages.getString("NewToKWizErrSpecName")); //$NON-NLS-1$
			return;
		}
		if (projectNameExists(projectName)) {
			updateStatus(Messages.getString("NewToKWizErrExist")); //$NON-NLS-1$
			return;
		}
		if (projectName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus(Messages.getString("NewToKWizErrNameInvalid")); //$NON-NLS-1$
			return;
		}

		if (creatorName.length() == 0) {
			updateStatus(Messages.getString("NewToKWizErrMissingCreator")); //$NON-NLS-1$
			return;
		}

		if (rootName.length() == 0) {
			warningStatus(Messages.getString("NewProjectPage.WrnNoRoot")); //$NON-NLS-1$
			// updateStatus(Messages.getString("NewToKWizErrSelectRoot"));
			// //$NON-NLS-1$
			return;
		}

		if (!legalRootExtension(rootName)) {
			updateStatus(Messages.getString("NewToKWizErrRootNotSrc")); //$NON-NLS-1$
			return;
		}

		if (!fileExists(rootName)) {
			updateStatus(Messages.getString("NewToKWizErrRootNotExist")); //$NON-NLS-1$
			return;
		}

		updateStatus(null);
	}

	private boolean fileExists(String rootName) {
		// checking file exists
		File tempFile = new File(rootName);
		if (tempFile.exists()) {
			return true;
		} else {
			return false;
		}

	}

	private boolean legalRootExtension(String fileName) {
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc == -1) {
			return false;
		} else {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("src") == false) { //$NON-NLS-1$
				return false;
			}
		}
		return true;
	}

	private boolean projectNameExists(String projectName) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName)
				.exists();
	}

	private void warningStatus(String message) {
		setMessage(message, WARNING);
		setPageComplete(true);
	}

	private void updateStatus(String message) {
		setMessage(null);
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getProjectName() {
		return fileText.getText();
	}

	public String getCreatorName() {
		return creatorText.getText();
	}

	public String getRootName() {
		return rootText.getText();
	}
}