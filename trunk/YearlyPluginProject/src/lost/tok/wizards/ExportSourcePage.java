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
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.export.SourceExportOperation;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.internal.wizards.datatransfer.IDataTransferHelpContextIds;
import org.eclipse.ui.internal.wizards.datatransfer.WizardFileSystemResourceExportPage1;

/**
 * Page 1 of export sources Wizard.
 */
public class ExportSourcePage extends WizardFileSystemResourceExportPage1 {

	/** The Constant STORE_COMPRESS_CONTENTS_ID. */
	private final static String STORE_COMPRESS_CONTENTS_ID = "ExportSourcePage.STORE_COMPRESS_CONTENTS_ID"; //$NON-NLS-1$

	/** The Constant STORE_CREATE_STRUCTURE_ID. */
	private final static String STORE_CREATE_STRUCTURE_ID = "ExportSourcePage.STORE_CREATE_STRUCTURE_ID"; //$NON-NLS-1$

	// dialog store id constants
	/** The Constant STORE_DESTINATION_NAMES_ID. */
	private final static String STORE_DESTINATION_NAMES_ID = "ExportSourcePage.STORE_DESTINATION_NAMES_ID"; //$NON-NLS-1$

	// widgets
	/** The compress contents checkbox. */
	protected Button compressContentsCheckbox;

	/** The selection. */
	private IStructuredSelection selection;

	/** The tok. */
	private ToK tok;

	/**
	 * Create an instance of this class.
	 * 
	 * @param selection
	 *            the selection
	 */
	public ExportSourcePage(IStructuredSelection selection) {
		this("zipFileExportPage1", selection); //$NON-NLS-1$
		setTitle(Messages.getString("ExportSourcePage.0")); //$NON-NLS-1$
		setDescription(Messages.getString("ExportSourcePage.1")); //$NON-NLS-1$
	}

	/**
	 * Create an instance of this class.
	 * 
	 * @param name
	 *            java.lang.String
	 * @param selection
	 *            the selection
	 */
	protected ExportSourcePage(String name, IStructuredSelection selection) {
		super(name, selection);
		this.selection = selection;
	}

	/**
	 * Check source selection.
	 * 
	 * @param resourcesToExport
	 *            the resources to export
	 * 
	 * @return true, if check source selection
	 */
	private boolean checkSourceSelection(List resourcesToExport) {
		Iterator iter = resourcesToExport.iterator();

		while (iter.hasNext()) {
			IResource resource = (IResource) iter.next();
			IResource[] children = null;
			if (resource.getType() == IResource.PROJECT) {
				try {
					children = ((IContainer) resource).members();
				} catch (CoreException e) {
					e.printStackTrace();
				}
				for (IResource file : children) {
					String fileName = file.getName();
					String[] name = fileName.split("\\."); //$NON-NLS-1$
					if (name[1].compareTo("src") != 0) { //$NON-NLS-1$
						setMessage(Messages.getString("ExportSourcePage.5"), WARNING);
//						MessageDialog.openWarning(null, Messages
//								.getString("ExportSourcePage.4"), //$NON-NLS-1$
//								Messages.getString("ExportSourcePage.5")); //$NON-NLS-1$
						return false;
					}
				}
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardExportResourcesPage#createButton(org.eclipse.swt.widgets.Composite,
	 *      int, java.lang.String, boolean)
	 */
	@Override
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		// increment the number of columns in the button bar
		((GridLayout) parent.getLayout()).numColumns++;

		Button button = new Button(parent, SWT.PUSH);

		GridData buttonData = new GridData(GridData.FILL_HORIZONTAL);
		button.setLayoutData(buttonData);

		button.setData(new Integer(id));
		if (id != 20) {
			button.setText(label);
		} else {
			button.setText(Messages.getString("ExportSourcePage.6")); //$NON-NLS-1$
		}

		button.setFont(parent.getFont());

		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
			button.setFocus();
		}
		button.setFont(parent.getFont());
		setButtonLayoutData(button);
		return button;

	}

	/**
	 * (non-Javadoc) Method declared on IDialogPage.
	 * 
	 * @param parent
	 *            the parent
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IDataTransferHelpContextIds.ZIP_FILE_EXPORT_WIZARD_PAGE);
		getTok();
		try {
			tok.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the export options specification widgets.
	 * 
	 * @param optionsGroup
	 *            the options group
	 */
	@Override
	protected void createOptionsGroupButtons(Group optionsGroup) {

		Font font = optionsGroup.getFont();
		// compress... checkbox
		compressContentsCheckbox = new Button(optionsGroup, SWT.CHECK
				| SWT.LEFT);
		// compressContentsCheckbox.setText(DataTransferMessages.ZipExport_compressContents);
		// compressContentsCheckbox.setFont(font);
		compressContentsCheckbox.setVisible(false);
		createDirectoryStructureOptions(optionsGroup, font);

		optionsGroup.setVisible(false);
		// initial setup
		createDirectoryStructureButton.setSelection(false);
		createSelectionOnlyButton.setSelection(false);
		createSelectionOnlyButton.setVisible(false);
		// compressContentsCheckbox.setSelection(true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.wizards.datatransfer.WizardFileSystemResourceExportPage1#destinationEmptyMessage()
	 */
	@Override
	protected String destinationEmptyMessage() {
		return DataTransferMessages.ZipExport_destinationEmpty;
	}

	/**
	 * Returns a boolean indicating whether the directory portion of the passed
	 * pathname is valid and available for use.
	 * 
	 * @param fullPathname
	 *            the full pathname
	 * 
	 * @return true, if ensure target directory is valid
	 */
	protected boolean ensureTargetDirectoryIsValid(String fullPathname) {
		int separatorIndex = fullPathname.lastIndexOf(File.separator);

		if (separatorIndex == -1) {
			return true;
		}

		return ensureTargetIsValid(new File(fullPathname.substring(0,
				separatorIndex)));
	}

	/**
	 * Returns a boolean indicating whether the passed File handle is is valid
	 * and available for use.
	 * 
	 * @param targetFile
	 *            the target file
	 * 
	 * @return true, if ensure target file is valid
	 */
	protected boolean ensureTargetFileIsValid(File targetFile) {
		if (targetFile.exists() && targetFile.isDirectory()) {
			displayErrorDialog(DataTransferMessages.ZipExport_mustBeFile);
			giveFocusToDestination();
			return false;
		}

		if (targetFile.exists()) {
			if (targetFile.canWrite()) {
				if (!queryYesNoQuestion(DataTransferMessages.ZipExport_alreadyExists)) {
					return false;
				}
			} else {
				displayErrorDialog(DataTransferMessages.ZipExport_alreadyExistsError);
				giveFocusToDestination();
				return false;
			}
		}

		return true;
	}

	/**
	 * Ensures that the target output file and its containing directory are both
	 * valid and able to be used. Answer a boolean indicating validity.
	 * 
	 * @return true, if ensure target is valid
	 */
	protected boolean ensureTargetIsValid() {
		String targetPath = getDestinationValue();

		if (!ensureTargetDirectoryIsValid(targetPath)) {
			return false;
		}

		if (!ensureTargetFileIsValid(new File(targetPath))) {
			return false;
		}

		return true;
	}

	/**
	 * Export the passed resource and recursively export all of its child
	 * resources (iff it's a container). Answer a boolean indicating success.
	 * 
	 * @param op
	 *            the op
	 * 
	 * @return true, if execute export operation
	 */
	protected boolean executeExportOperation(SourceExportOperation op) {

		op.setCreateLeadupStructure(false);
		op.setUseCompression(true);

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			displayErrorDialog(e.getTargetException());
			return false;
		}

		IStatus status = op.getStatus();
		if (!status.isOK()) {
			ErrorDialog.openError(getContainer().getShell(),
					DataTransferMessages.DataTransfer_exportProblems, null, // no
					// special
					// message
					status);
			return false;
		}

		return true;
	}

	/**
	 * The Finish button was pressed. Try to do the required work now and answer
	 * a boolean indicating success. If false is returned then the wizard will
	 * not close.
	 * 
	 * @return boolean
	 */
	@Override
	public boolean finish() {
		if (!ensureTargetIsValid()) {
			return false;
		}

		List resourcesToExport = getWhiteCheckedResources();

		if (!checkSourceSelection(resourcesToExport)) {
			handleTypesEditButtonPressed();

			resourcesToExport = getWhiteCheckedResources();
		}

		// Save dirty editors if possible but do not stop if not all are saved
		saveDirtyEditors();
		// about to invoke the operation so save our state
		saveWidgetValues();

		getTok();
		if (resourcesToExport.size() > 0) {
			return executeExportOperation(new SourceExportOperation(null,
					resourcesToExport, getDestinationValue(), tok));
		}
		setMessage(DataTransferMessages.FileExport_noneSelected, INFORMATION);
//		MessageDialog.openInformation(getContainer().getShell(),
//				DataTransferMessages.DataTransfer_information,
//				DataTransferMessages.FileExport_noneSelected);

		return false;
	}

	/**
	 * Answer the string to display in the receiver as the destination type.
	 * 
	 * @return the destination label
	 */
	@Override
	protected String getDestinationLabel() {
		return DataTransferMessages.ZipExport_destinationLabel;
	}

	/**
	 * Answer the contents of self's destination specification widget. If this
	 * value does not have a suffix then add it first.
	 * 
	 * @return the destination value
	 */
	@Override
	protected String getDestinationValue() {
		String idealSuffix = getOutputSuffix();
		String destinationText = super.getDestinationValue();

		// only append a suffix if the destination doesn't already have a . in
		// its last path segment.
		// Also prevent the user from selecting a directory. Allowing this will
		// create a ".zip" file in the directory
		if (destinationText.length() != 0
				&& !destinationText.endsWith(File.separator)) {
			int dotIndex = destinationText.lastIndexOf('.');
			if (dotIndex != -1) {
				// the last path seperator index
				int pathSepIndex = destinationText.lastIndexOf(File.separator);
				if (pathSepIndex != -1 && dotIndex < pathSepIndex) {
					destinationText += idealSuffix;
				}
			} else {
				destinationText += idealSuffix;
			}
		}

		return destinationText;
	}

	/**
	 * Answer the suffix that files exported from this wizard should have. If
	 * this suffix is a file extension (which is typically the case) then it
	 * must include the leading period character.
	 * 
	 * @return the output suffix
	 */
	protected String getOutputSuffix() {
		return ".exs"; //$NON-NLS-1$
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void getTok() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = selection;
			if (ssel.size() > 1) {
				return;
			}
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IResource resource = (IResource) obj;
				tok = ToK.getProjectToK(resource.getProject());
			}
		}
	}

	/**
	 * Open an appropriate destination browser so that the user can specify a
	 * source to import from.
	 */
	@Override
	protected void handleDestinationBrowseButtonPressed() {
		FileDialog dialog = new FileDialog(getContainer().getShell(), SWT.SAVE);
		dialog.setFilterExtensions(new String[] { "*.exs" }); //$NON-NLS-1$ 
		dialog.setText(DataTransferMessages.ZipExport_selectDestinationTitle);
		String currentSourceString = getDestinationValue();
		int lastSeparatorIndex = currentSourceString
				.lastIndexOf(File.separator);
		if (lastSeparatorIndex != -1) {
			dialog.setFilterPath(currentSourceString.substring(0,
					lastSeparatorIndex));
		}
		String selectedFileName = dialog.open();

		if (selectedFileName != null) {
			setErrorMessage(null);
			setDestinationValue(selectedFileName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardExportResourcesPage#handleTypesEditButtonPressed()
	 */
	@Override
	protected void handleTypesEditButtonPressed() {
		super.handleTypesEditButtonPressed();
	}

	/**
	 * Hook method for saving widget values for restoration by the next instance
	 * of this class.
	 */
	@Override
	protected void internalSaveWidgetValues() {
		// update directory names history
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings
					.getArray(STORE_DESTINATION_NAMES_ID);
			if (directoryNames == null) {
				directoryNames = new String[0];
			}

			directoryNames = addToHistory(directoryNames, getDestinationValue());
			settings.put(STORE_DESTINATION_NAMES_ID, directoryNames);

			settings.put(STORE_CREATE_STRUCTURE_ID,
					createDirectoryStructureButton.getSelection());

			settings.put(STORE_COMPRESS_CONTENTS_ID, compressContentsCheckbox
					.getSelection());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardExportResourcesPage#queryResourceTypesToExport()
	 */
	@Override
	protected Object[] queryResourceTypesToExport() {
		String[] extension = new String[1];
		extension[0] = "src"; //$NON-NLS-1$
		return extension;
	}

	/**
	 * Hook method for restoring widget values to the values that they held last
	 * time this wizard was used to completion.
	 */
	@Override
	protected void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings
					.getArray(STORE_DESTINATION_NAMES_ID);
			if (directoryNames == null || directoryNames.length == 0) {
				return; // ie.- no settings stored
			}

			// destination
			setDestinationValue(directoryNames[0]);
			for (int i = 0; i < directoryNames.length; i++) {
				addDestinationItem(directoryNames[i]);
			}

			boolean setStructure = settings
					.getBoolean(STORE_CREATE_STRUCTURE_ID);

			createDirectoryStructureButton.setSelection(setStructure);
			createSelectionOnlyButton.setSelection(!setStructure);

			compressContentsCheckbox.setSelection(settings
					.getBoolean(STORE_COMPRESS_CONTENTS_ID));
		}
	}
}
