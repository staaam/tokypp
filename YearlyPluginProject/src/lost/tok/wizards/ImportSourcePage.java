package lost.tok.wizards;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import lost.tok.Messages;
import lost.tok.Source;
import lost.tok.ToK;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FileSystemElement;
import org.eclipse.ui.internal.ide.DialogUtil;
import org.eclipse.ui.internal.ide.dialogs.ResourceTreeAndListGroup;
import org.eclipse.ui.internal.wizards.datatransfer.ArchiveFileManipulations;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.internal.wizards.datatransfer.IDataTransferHelpContextIds;
import org.eclipse.ui.internal.wizards.datatransfer.MinimizedFileSystemElement;
import org.eclipse.ui.internal.wizards.datatransfer.TarException;
import org.eclipse.ui.internal.wizards.datatransfer.TarFile;
import org.eclipse.ui.internal.wizards.datatransfer.TarLeveledStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.WizardFileSystemResourceImportPage1;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.model.WorkbenchViewerSorter;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;

/**
 * The Class ImportSourcePage.
 */
public class ImportSourcePage extends WizardFileSystemResourceImportPage1
		implements Listener {

	// constants
	/** The Constant FILE_IMPORT_MASK. */
	private static final String[] FILE_IMPORT_MASK = { "*.exs" }; //$NON-NLS-1$ 

	/** The Constant STORE_OVERWRITE_EXISTING_RESOURCES_ID. */
	private final static String STORE_OVERWRITE_EXISTING_RESOURCES_ID = "WizardZipFileResourceImportPage1.STORE_OVERWRITE_EXISTING_RESOURCES_ID"; //$NON-NLS-1$

	/** The Constant STORE_SELECTED_TYPES_ID. */
	private final static String STORE_SELECTED_TYPES_ID = "WizardZipFileResourceImportPage1.STORE_SELECTED_TYPES_ID"; //$NON-NLS-1$

	// dialog store id constants
	/** The Constant STORE_SOURCE_NAMES_ID. */
	private final static String STORE_SOURCE_NAMES_ID = "WizardZipFileResourceImportPage1.STORE_SOURCE_NAMES_ID"; //$NON-NLS-1$

	/** The selection. */
	private IStructuredSelection selection;

	/** The tar current provider. */
	TarLeveledStructureProvider tarCurrentProvider;

	/** The tok. */
	private ToK tok;

	/** The zip current provider. */
	ZipLeveledStructureProvider zipCurrentProvider;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param aWorkbench
	 *            IWorkbench
	 * @param selection
	 *            IStructuredSelection
	 */
	public ImportSourcePage(IWorkbench aWorkbench,
			IStructuredSelection selection) {
		super("zipFileImportPage1", aWorkbench, selection); //$NON-NLS-1$
		this.selection = selection;
		setTitle(Messages.getString("ImportSourcePage.0")); //$NON-NLS-1$
		setDescription(Messages.getString("ImportSourcePage.1")); //$NON-NLS-1$
	}

	/**
	 * Called when the user presses the Cancel button. Return a boolean
	 * indicating permission to close the wizard.
	 * 
	 * @return boolean
	 */
	public boolean cancel() {
		clearProviderCache();
		return true;
	}


	/**
	 * Clears the cached structure provider after first finalizing it properly.
	 */
	protected void clearProviderCache() {
		ArchiveFileManipulations.clearProviderCache(getContainer().getShell());
	}

	/**
	 * Attempts to close the passed zip file, and answers a boolean indicating
	 * success.
	 * 
	 * @param file
	 *            the file
	 * 
	 * @return true, if close zip file
	 */
	protected boolean closeZipFile(ZipFile file) {
		try {
			file.close();
		} catch (IOException e) {
			displayErrorDialog(NLS.bind(
					DataTransferMessages.ZipImport_couldNotClose, file
							.getName()));
			return false;
		}

		return true;
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
				IDataTransferHelpContextIds.ZIP_FILE_IMPORT_WIZARD_PAGE);
	}

	/**
	 * Create the options specification widgets. There is only one in this case
	 * so create no group.
	 * 
	 * @param parent
	 *            org.eclipse.swt.widgets.Composite
	 */
	@Override
	protected void createOptionsGroup(Composite parent) {

		// overwrite... checkbox
		overwriteExistingResourcesCheckbox = new Button(parent, SWT.CHECK);
		overwriteExistingResourcesCheckbox
				.setText(DataTransferMessages.FileImport_overwriteExisting);
		overwriteExistingResourcesCheckbox.setFont(parent.getFont());
		overwriteExistingResourcesCheckbox.setSelection(true);
		overwriteExistingResourcesCheckbox.setVisible(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.internal.wizards.datatransfer.WizardFileSystemResourceImportPage1#createSourceGroup(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createSourceGroup(Composite parent) {
		createRootDirectoryGroup(parent);
		createFileSelectionGroup(parent);
		createButtonsGroup(parent);
		selectTypesButton.setVisible(false);
		selectAllButton.setVisible(false);
		deselectAllButton.setVisible(false);
	}

	/**
	 * The Finish button was pressed. Try to do the required work now and answer
	 * a boolean indicating success. If <code>false</code> is returned then
	 * the wizard will not close.
	 * 
	 * @return boolean
	 */
	@Override
	public boolean finish() {
		if (!ensureSourceIsValid()) {
			return false;
		}

		saveWidgetValues();

		Iterator resourcesEnum = getSelectedResources().iterator();
		List<Object> fileSystemObjects = new ArrayList<Object>();
		while (resourcesEnum.hasNext()) {
			fileSystemObjects.add(((FileSystemElement) resourcesEnum.next())
					.getFileSystemObject());
		}

		if (fileSystemObjects.size() > 0) {
			return importResources(fileSystemObjects);
		}
		setMessage(DataTransferMessages.FileImport_noneSelected, INFORMATION);
		MessageDialog.openInformation(getContainer().getShell(),
				DataTransferMessages.DataTransfer_information,
				DataTransferMessages.FileImport_noneSelected);

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardResourceImportPage#getContainerFullPath()
	 */
	@Override
	protected IPath getContainerFullPath() {
		getTok();
		return tok.getSourcesFolder().getParent().getFullPath();
	}

	/**
	 * Returns a content provider for <code>FileSystemElement</code>s that
	 * returns only files as children.
	 * 
	 * @return the file provider
	 */
	@Override
	protected ITreeContentProvider getFileProvider() {
		return new WorkbenchContentProvider() {
			@Override
			public Object[] getChildren(Object o) {
				if (o instanceof MinimizedFileSystemElement) {
					MinimizedFileSystemElement element = (MinimizedFileSystemElement) o;
					AdaptableList l;
					if (zipCurrentProvider != null) {
						l = element.getFiles(zipCurrentProvider);
					} else {
						l = element.getFiles(tarCurrentProvider);
					}

					Object[] files = l.getChildren(element);
					
					return files;
				}
				return new Object[0];
			}
		};
	}

	/**
	 * Answer the root FileSystemElement that represents the contents of the
	 * currently-specified .zip file. If this FileSystemElement is not currently
	 * defined then create and return it.
	 * 
	 * @return the file system tree
	 */
	@Override
	protected MinimizedFileSystemElement getFileSystemTree() {
		if (ArchiveFileManipulations.isTarFile(sourceNameField.getText())) {
			TarFile sourceTarFile = getSpecifiedTarSourceFile();
			if (sourceTarFile == null) {
				// Clear out the provider as well
				this.zipCurrentProvider = null;
				this.tarCurrentProvider = null;
				return null;
			}

			TarLeveledStructureProvider provider = ArchiveFileManipulations
					.getTarStructureProvider(sourceTarFile, getContainer()
							.getShell());
			this.tarCurrentProvider = provider;
			this.zipCurrentProvider = null;
			return selectFiles(provider.getRoot(), provider);
		}

		ZipFile sourceFile = getSpecifiedZipSourceFile();
		if (sourceFile == null) {
			// Clear out the provider as well
			this.zipCurrentProvider = null;
			this.tarCurrentProvider = null;
			return null;
		}

		ZipLeveledStructureProvider provider = ArchiveFileManipulations
				.getZipStructureProvider(sourceFile, getContainer().getShell());
		this.zipCurrentProvider = provider;
		this.tarCurrentProvider = null;
		return selectFiles(provider.getRoot(), provider);
	}

	/**
	 * Returns a content provider for <code>FileSystemElement</code>s that
	 * returns only folders as children.
	 * 
	 * @return the folder provider
	 */
	@Override
	protected ITreeContentProvider getFolderProvider() {
		return new WorkbenchContentProvider() {
			@Override
			public Object[] getChildren(Object o) {
				if (o instanceof MinimizedFileSystemElement) {
					MinimizedFileSystemElement element = (MinimizedFileSystemElement) o;
					AdaptableList l;
					if (zipCurrentProvider != null) {
						l = element.getFolders(zipCurrentProvider);
					} else {
						l = element.getFolders(tarCurrentProvider);
					}
					return l.getChildren(element);
				}
				return new Object[0];
			}

			@Override
			public boolean hasChildren(Object o) {
				if (o instanceof MinimizedFileSystemElement) {
					MinimizedFileSystemElement element = (MinimizedFileSystemElement) o;
					return getChildren(element).length > 0;
				}
				return false;
			}
		};
	}

	/**
	 * Answer the string to display as the label for the source specification
	 * field.
	 * 
	 * @return the source label
	 */
	@Override
	protected String getSourceLabel() {
		return DataTransferMessages.ArchiveImport_fromFile;
	}

	/**
	 * Answer a handle to the zip file currently specified as being the source.
	 * Return null if this file does not exist or is not of valid format.
	 * 
	 * @return the specified tar source file
	 */
	protected TarFile getSpecifiedTarSourceFile() {
		return getSpecifiedTarSourceFile(sourceNameField.getText());
	}

	/**
	 * Answer a handle to the zip file currently specified as being the source.
	 * Return null if this file does not exist or is not of valid format.
	 * 
	 * @param fileName
	 *            the file name
	 * 
	 * @return the specified tar source file
	 */
	private TarFile getSpecifiedTarSourceFile(String fileName) {
		if (fileName.length() == 0) {
			return null;
		}

		try {
			return new TarFile(fileName);
		} catch (TarException e) {
			displayErrorDialog(DataTransferMessages.TarImport_badFormat);
		} catch (IOException e) {
			displayErrorDialog(DataTransferMessages.ZipImport_couldNotRead);
		}

		sourceNameField.setFocus();
		return null;
	}

	/**
	 * Answer a handle to the zip file currently specified as being the source.
	 * Return null if this file does not exist or is not of valid format.
	 * 
	 * @return the specified zip source file
	 */
	protected ZipFile getSpecifiedZipSourceFile() {
		return getSpecifiedZipSourceFile(sourceNameField.getText());
	}

	/**
	 * Answer a handle to the zip file currently specified as being the source.
	 * Return null if this file does not exist or is not of valid format.
	 * 
	 * @param fileName
	 *            the file name
	 * 
	 * @return the specified zip source file
	 */
	private ZipFile getSpecifiedZipSourceFile(String fileName) {
		if (fileName.length() == 0) {
			return null;
		}

		try {
			return new ZipFile(fileName);
		} catch (ZipException e) {
			displayErrorDialog(DataTransferMessages.ZipImport_badFormat);
		} catch (IOException e) {
			displayErrorDialog(DataTransferMessages.ZipImport_couldNotRead);
		}

		sourceNameField.setFocus();
		return null;
	}

	/**
	 * Gets the tok.
	 * 
	 * @return the tok
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
	 * Open a FileDialog so that the user can specify the source file to import
	 * from.
	 */
	@Override
	protected void handleSourceBrowseButtonPressed() {
		String selectedFile = queryZipFileToImport();

		if (selectedFile != null) {
			// Be sure it is valid before we go setting any names
			if (!selectedFile.equals(sourceNameField.getText())
					&& validateSourceFile(selectedFile)) {
				setSourceName(selectedFile);
				selectionGroup.setFocus();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.internal.wizards.datatransfer.WizardFileSystemResourceImportPage1#handleTypesEditButtonPressed()
	 */
	@SuppressWarnings("unchecked")//$NON-NLS-1$
	@Override
	protected void handleTypesEditButtonPressed() {
		selectedTypes.clear();
		selectedTypes.add("src"); //$NON-NLS-1$
		setAllSelections(true);
		setupSelectionsBasedOnSelectedTypes();
	}

	/**
	 * Import the resources with extensions as specified by the user.
	 * 
	 * @param fileSystemObjects
	 *            the file system objects
	 * 
	 * @return true, if import resources
	 */
	@Override
	protected boolean importResources(List fileSystemObjects) {
		boolean result = false;

		if (ArchiveFileManipulations.isTarFile(sourceNameField.getText())) {
			TarFile tarFile = getSpecifiedTarSourceFile();
			TarLeveledStructureProvider structureProvider = ArchiveFileManipulations
					.getTarStructureProvider(tarFile, getContainer()
							.getShell());
			ImportOperation operation = new ImportOperation(
					getContainerFullPath(), structureProvider.getRoot(),
					structureProvider, this, fileSystemObjects);
	
			operation.setContext(getShell());
			return executeImportOperation(operation);
		}

		ZipFile zipFile = getSpecifiedZipSourceFile();

		ZipLeveledStructureProvider structureProvider = ArchiveFileManipulations
				.getZipStructureProvider(zipFile, getContainer().getShell());
		ImportOperation operation = new ImportOperation(
				getContainerFullPath(), structureProvider.getRoot(),
				structureProvider, this, fileSystemObjects);

		operation.setContext(getShell());
		result = executeImportOperation(operation);

		closeZipFile(zipFile);
		
		return result;
	}

	/**
	 * Check existing sources.
	 * 
	 * @param zipFile
	 *            the zip file
	 * @param fileSystemObjects
	 * @param provider
	 * 
	 * @return true, if successful
	 */
	@SuppressWarnings("unchecked")//$NON-NLS-1$
	private boolean checkExistingSources(ZipFile zipFile,
			List fileSystemObjects, ZipLeveledStructureProvider provider) {
		for (Object object : fileSystemObjects) {
			IPath sourcePath = new Path(provider.getFullPath(object));
			String sourceName = sourcePath.removeFileExtension()
					.lastSegment();
			Source src = null;
			src = tok.getSource(sourceName);
			if (src != null) {
				if(getMessage() == null){
					setMessage(Messages.getString("ImportSourcePage.12"), ERROR);
				}
				setPageComplete(false);
				return false;
			}
		}
		return true;
	}

	/**
	 * Initializes the specified operation appropriately.
	 * 
	 * @param op
	 *            the op
	 */
	@Override
	protected void initializeOperation(ImportOperation op) {
		op.setOverwriteResources(overwriteExistingResourcesCheckbox
				.getSelection());
	}

	/**
	 * Opens a file selection dialog and returns a string representing the
	 * selected file, or <code>null</code> if the dialog was canceled.
	 * 
	 * @return the string
	 */
	protected String queryZipFileToImport() {
		FileDialog dialog = new FileDialog(sourceNameField.getShell(), SWT.OPEN);
		dialog.setFilterExtensions(FILE_IMPORT_MASK);
		dialog.setText(DataTransferMessages.ArchiveImportSource_title);

		String currentSourceString = sourceNameField.getText();
		int lastSeparatorIndex = currentSourceString
				.lastIndexOf(File.separator);
		if (lastSeparatorIndex != -1) {
			dialog.setFilterPath(currentSourceString.substring(0,
					lastSeparatorIndex));
		}

		return dialog.open();
	}

	/**
	 * Repopulate the view based on the currently entered directory.
	 */
	@Override
	protected void resetSelection() {

		super.resetSelection();
		setAllSelections(true);
	}

	/**
	 * Use the dialog store to restore widget values to the values that they
	 * held last time this wizard was used to completion.
	 */
	@Override
	protected void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] sourceNames = settings.getArray(STORE_SOURCE_NAMES_ID);
			if (sourceNames == null) {
				return; // ie.- no settings stored
			}

			// set filenames history
			for (int i = 0; i < sourceNames.length; i++) {
				sourceNameField.add(sourceNames[i]);
			}

			// radio buttons and checkboxes
			overwriteExistingResourcesCheckbox.setSelection(settings
					.getBoolean(STORE_OVERWRITE_EXISTING_RESOURCES_ID));
		}
	}

	/**
	 * Since Finish was pressed, write widget values to the dialog store so that
	 * they will persist into the next invocation of this wizard page.
	 * 
	 * Note that this method is identical to the one that appears in the
	 * superclass. This is necessary because proper overriding of instance
	 * variables is not occurring.
	 */
	@Override
	protected void saveWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			// update source names history
			String[] sourceNames = settings.getArray(STORE_SOURCE_NAMES_ID);
			if (sourceNames == null) {
				sourceNames = new String[0];
			}

			sourceNames = addToHistory(sourceNames, sourceNameField.getText());
			settings.put(STORE_SOURCE_NAMES_ID, sourceNames);

			// update specific types to import history
			String[] selectedTypesNames = settings
					.getArray(STORE_SELECTED_TYPES_ID);
			if (selectedTypesNames == null) {
				selectedTypesNames = new String[0];
			}

			settings.put(STORE_OVERWRITE_EXISTING_RESOURCES_ID,
					overwriteExistingResourcesCheckbox.getSelection());
		}
	}

	/**
	 * Validate source file.
	 * 
	 * @param fileName
	 *            the file name
	 * 
	 * @return true, if successful
	 */
	private boolean validateSourceFile(String fileName) {
		if (ArchiveFileManipulations.isTarFile(fileName)) {
			TarFile tarFile = getSpecifiedTarSourceFile(fileName);
			return (tarFile != null);
		}
		ZipFile zipFile = getSpecifiedZipSourceFile(fileName);
		if (zipFile != null) {
			ArchiveFileManipulations.closeZipFile(zipFile, getContainer()
					.getShell());
			return true;
		}
		return false;
	}

	/**
	 * Answer a boolean indicating whether self's source specification widgets
	 * currently all contain valid values.
	 * 
	 * @return true, if validate source group
	 */
	@Override
	protected boolean validateSourceGroup() {

		// If there is nothing being provided to the input then there is a
		// problem
		if (this.zipCurrentProvider == null && this.tarCurrentProvider == null) {
			setMessage(SOURCE_EMPTY_MESSAGE);
			enableButtonGroup(false);
			return false;
		}

		List resourcesToExport = selectionGroup.getAllWhiteCheckedItems();
		if (resourcesToExport.size() == 0) {
			setErrorMessage(DataTransferMessages.FileImport_noneSelected);
			return false;
		}

		enableButtonGroup(true);
		setErrorMessage(null);
		return true;
	}

	/**
	 * 
	 */
	private boolean checkSources() {
		boolean result = true;
		Iterator resourcesEnum = getSelectedResources().iterator();
		List<Object> fileSystemObjects = new ArrayList<Object>();
		while (resourcesEnum.hasNext()) {
			fileSystemObjects.add(((FileSystemElement) resourcesEnum.next())
					.getFileSystemObject());
		}
		{
			ZipFile zipFile = getSpecifiedZipSourceFile();

			// check not overwriting existing sourcens
			result = checkExistingSources(zipFile, fileSystemObjects,
					ArchiveFileManipulations.getZipStructureProvider(zipFile,
							getContainer().getShell()));

		}
		return result;
	}

	@Override
	protected void createFileSelectionGroup(Composite parent) {
		// Just create with a dummy root.
		this.selectionGroup = new ResourceTreeAndListGroup(
				parent,
				new FileSystemElement("Dummy", null, true),//$NON-NLS-1$
				getFolderProvider(), new WorkbenchLabelProvider(),
				getFileProvider(), new WorkbenchLabelProvider(), SWT.NONE,
				DialogUtil.inRegularFontMode(parent));

		ICheckStateListener listener = new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				updateWidgetEnablements();
				if (!checkSources())
					setPageComplete(false);
			}
		};

		WorkbenchViewerSorter sorter = new WorkbenchViewerSorter();
		this.selectionGroup.setTreeSorter(sorter);
		this.selectionGroup.setListSorter(sorter);
		this.selectionGroup.addCheckStateListener(listener);

	}
}
