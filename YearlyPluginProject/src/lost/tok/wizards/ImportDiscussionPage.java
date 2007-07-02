package lost.tok.wizards;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import lost.tok.Discussion;
import lost.tok.GeneralFunctions;
import lost.tok.Messages;
import lost.tok.Source;
import lost.tok.ToK;
import lost.tok.activator.Activator;
import lost.tok.export.DiscussionExportOperation;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
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
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;

/**
 * The Class ImportDiscussionPage.
 */
public class ImportDiscussionPage extends WizardFileSystemResourceImportPage1
		implements Listener {

	/** The Constant BUFFER. */
	private static final int BUFFER = 2048;

	// constants
	/** The Constant FILE_IMPORT_MASK. */
	private static final String[] FILE_IMPORT_MASK = { "*.exd" }; //$NON-NLS-1$ 

	/** The Constant STORE_OVERWRITE_EXISTING_RESOURCES_ID. */
	private final static String STORE_OVERWRITE_EXISTING_RESOURCES_ID = "WizardZipFileResourceImportPage1.STORE_OVERWRITE_EXISTING_RESOURCES_ID"; //$NON-NLS-1$

	/** The Constant STORE_SELECTED_TYPES_ID. */
	private final static String STORE_SELECTED_TYPES_ID = "WizardZipFileResourceImportPage1.STORE_SELECTED_TYPES_ID"; //$NON-NLS-1$

	// dialog store id constants
	/** The Constant STORE_SOURCE_NAMES_ID. */
	private final static String STORE_SOURCE_NAMES_ID = "WizardZipFileResourceImportPage1.STORE_SOURCE_NAMES_ID"; //$NON-NLS-1$

	private boolean lostExport = false;

	/** The selection. */
	private IStructuredSelection selection;

	/** The tar current provider. */
	TarLeveledStructureProvider tarCurrentProvider;

	/** The temp links file. */
	private String tempLinksFile;

	/** The tok. */
	private ToK tok;

	/** The zip current provider. */
	ZipLeveledStructureProvider zipCurrentProvider;
	
	class DiscussionImportListener extends SelectionAdapter implements ICheckStateListener{

		public void checkStateChanged(CheckStateChangedEvent event) {
				updateWidgetEnablements();
				if (!checkDiscussions() || !checkSources()) {
					setPageComplete(false);
				}
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
            setAllSelections(true);
            updateWidgetEnablements();
			if (!checkDiscussions() || !checkSources()) {
				setPageComplete(false);
			}
        }
	}
	
	
	

	/**
	 * Creates an instance of this class.
	 * 
	 * @param aWorkbench
	 *            IWorkbench
	 * @param selection
	 *            IStructuredSelection
	 */
	public ImportDiscussionPage(IWorkbench aWorkbench,
			IStructuredSelection selection) {
		super("zipFileImportPage1", aWorkbench, selection); //$NON-NLS-1$
		this.selection = selection;
		setTitle(Messages.getString("ImportDiscussionPage.0")); //$NON-NLS-1$
		setDescription(Messages.getString("ImportDiscussionPage.1")); //$NON-NLS-1$
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
	 * 
	 */
	private boolean checkDiscussions() {
		boolean result = true;
		Iterator resourcesEnum = getSelectedResources().iterator();
		List<Object> fileSystemObjects = new ArrayList<Object>();
		while (resourcesEnum.hasNext()) {
			fileSystemObjects.add(((FileSystemElement) resourcesEnum.next())
					.getFileSystemObject());
		}
		if (ensureZipSourceIsValid() && fileSystemObjects.size() > 0) {
			ZipFile zipFile = getSpecifiedZipSourceFile();

			// check not overwriting existing discussions
			result = checkExistingDiscussions(zipFile, fileSystemObjects,
					ArchiveFileManipulations.getZipStructureProvider(zipFile,
							getContainer().getShell()));

		}
		return result;
	}

	/**
	 * Check existing discussions.
	 * 
	 * @param zipFile
	 *            the zip file
	 * @param fileSystemObjects
	 * @param provider
	 * 
	 * @return true, if successful
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	private boolean checkExistingDiscussions(ZipFile zipFile,
			List fileSystemObjects, ZipLeveledStructureProvider provider) {
		Enumeration entries = zipFile.entries();
		while(entries.hasMoreElements()){
			ZipEntry entry = (ZipEntry) entries.nextElement();
			if (entry.isDirectory()) {
				continue;
			}
			String discussionName = entry.getComment();
			Discussion disc = null;
			try {
				disc = tok.getDiscussion(discussionName);
			} catch (CoreException e) {
				// no such discussion exists
			}
			if (disc != null) {
				if (getMessage() == null) {
					setMessage(Messages.getString("ImportDiscussionPage.12"), //$NON-NLS-1$
							ERROR);
				}
				setPageComplete(false);
				return false;
			}
		}
		return true;
	}

	private boolean checkSources() {
		boolean result = true;
		Iterator resourcesEnum = getSelectedResources().iterator();
		List<Object> fileSystemObjects = new ArrayList<Object>();
		while (resourcesEnum.hasNext()) {
			fileSystemObjects.add(((FileSystemElement) resourcesEnum.next())
					.getFileSystemObject());
		}
		if (ensureZipSourceIsValid() && fileSystemObjects.size() > 0) {
			ZipFile zipFile = getSpecifiedZipSourceFile();
			createTempLinksFile(zipFile);
			result = checkSourcesExist(ArchiveFileManipulations
					.getZipStructureProvider(zipFile, getContainer().getShell()));
			deleteTempLinksFile();

		}
		return result;
	}

	/**
	 * Check sources exist.
	 * 
	 * @param provider
	 * @param zipFile
	 * 
	 * @return true, if successful
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	private boolean checkSourcesExist(IImportStructureProvider provider) {
		Iterator resourcesEnum = getSelectedResources().iterator();
		List<Object> fileSystemObjects = new ArrayList<Object>();
		Vector<String> selectedDiscussions = new Vector<String>();
		while (resourcesEnum.hasNext()) {
			fileSystemObjects.add(((FileSystemElement) resourcesEnum.next())
					.getFileSystemObject());
		}
		ZipFile zipFile = null;
		if (ensureZipSourceIsValid()) {
			zipFile = getSpecifiedZipSourceFile();
		}

		// collect the selected discussion names
		for (Object object : fileSystemObjects) {
			IPath sourcePath = new Path(provider.getFullPath(object));
			selectedDiscussions.add(sourcePath.lastSegment());
		}

		List<String> missingFiles = new ArrayList<String>();
		Document tempDoc = GeneralFunctions.readFromXML(tempLinksFile);
		IFolder sourceFolder = tok.getRootsFolder();
		for (String discussionName : selectedDiscussions) { 
			XPath sourcesXPath = DocumentHelper.createXPath("//link[discussionFile='" //$NON-NLS-1$
					+ discussionName + "']/sublink/sourceFile"); //$NON-NLS-1$
			List<Node> sourceNodes = sourcesXPath.selectNodes(tempDoc);
//			List<Node> sourceNodes = tempDoc
//					.selectNodes("//link/discussionFile[text()='"
//							+ discussionName + "']/sublink/sourceFile"); //$NON-NLS-1$

			// check for missing roots that the discussions are linked too
			for (Node sourceFile : sourceNodes) {
				String rawPathName = sourceFile.getText();
				IPath path = new Path(sourceFile.getText());
				if (rawPathName.contains(sourceFolder.getName())) {
					// check that
					// the path
					// doesn't
					// include the
					// source folder
					// Itself
					while (rawPathName.contains(sourceFolder.getName())) {
						path = path.removeFirstSegments(1);
						rawPathName = path.toOSString();
					}
				}
				if (!sourceFolder.exists(path)) {
					missingFiles.add(path.toOSString());
				}
			}
		}

		// check for missing source files that the discussion includes quotes
		// out of them
		Source[] sources = null;
		try {
			sources = tok.getSources();
		} catch (Exception e) {
			// no sources or roots
			if (getMessage() == null) {
				setErrorMessage(Messages.getString("ImportDiscussionPage.2")); //$NON-NLS-1$
			}
			return false;
		}
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile
				.entries();
		while (entries.hasMoreElements()) {
			ZipEntry element = entries.nextElement();
			if (selectedDiscussions.contains(element.getComment())) {
				File dest = unzipToTempLocation(zipFile, element);
				Document discussionDoc = GeneralFunctions.readFromXML(dest
						.getAbsolutePath());
				List result = discussionDoc.selectNodes("//quote/sourceFile"); //$NON-NLS-1$

				for (Object sourceNode : result) {
					boolean exists = false;
					String[] sourceName = ((Element) sourceNode).getText()
							.split("/"); //$NON-NLS-1$
					for (Source tokSource : sources) {
						if (tokSource.getFile().getFullPath().lastSegment()
								.compareTo(sourceName[sourceName.length - 1]) == 0) {// the
							// source
							// exists
							exists = true;
							break;
						}
					}
					if ((!exists)
							&& (!missingFiles
									.contains(sourceName[sourceName.length - 1]))) {
						missingFiles.add(sourceName[sourceName.length - 1]);
					}
				}
				dest.delete();
			}

		}

		if (missingFiles.size() != 0) {
			String error = new String();
			for (String file : missingFiles) {
				if (!error.contains(file)) {
					error += file + "\n"; //$NON-NLS-1$
				}
			}
			if (getMessage() == null) {
				setErrorMessage(Messages.getString("ImportDiscussionPage.6") //$NON-NLS-1$
						+ error);
			}
			return false;
		}
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

	

	@Override
	protected void createFileSelectionGroup(Composite parent) {
		// Just create with a dummy root.
		this.selectionGroup = new ResourceTreeAndListGroup(
				parent,
				new FileSystemElement("Dummy", null, true),//$NON-NLS-1$
				getFolderProvider(), new WorkbenchLabelProvider(),
				getFileProvider(), new WorkbenchLabelProvider(), SWT.NONE,
				DialogUtil.inRegularFontMode(parent));

		WorkbenchViewerSorter sorter = new WorkbenchViewerSorter();
		this.selectionGroup.setTreeSorter(sorter);
		this.selectionGroup.setListSorter(sorter);
		this.selectionGroup.addCheckStateListener(new DiscussionImportListener());

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
		selectAllButton.setVisible(true);
		selectAllButton.addSelectionListener(new DiscussionImportListener());
		deselectAllButton.setVisible(false);
	}

	/**
	 * Creates a temporary Links file.
	 * 
	 * @param zipFile
	 *            the zip file
	 */
	private void createTempLinksFile(ZipFile zipFile) {
		ZipEntry linksEntry = zipFile
				.getEntry(DiscussionExportOperation.TEMP_LINKS_XML);
		File destFile = null;
		destFile = unzipToTempLocation(zipFile, linksEntry);
		tempLinksFile = destFile.getAbsolutePath();
		transformLinksFile();
	}

	/**
	 * Delete temp links file.
	 */
	private void deleteTempLinksFile() {
		File tempLinkFile = new File(tempLinksFile);
		tempLinkFile.delete();
	}

	/**
	 * Answer a boolean indicating whether the specified source currently exists
	 * and is valid (ie.- proper format)
	 * 
	 * @return true, if ensure source is valid
	 */
	@Override
	protected boolean ensureSourceIsValid() {
		if (ArchiveFileManipulations.isTarFile(sourceNameField.getText())) {
			return ensureTarSourceIsValid();
		}
		return ensureZipSourceIsValid();
	}

	/**
	 * Ensure tar source is valid.
	 * 
	 * @return true, if successful
	 */
	private boolean ensureTarSourceIsValid() {
		TarFile specifiedFile = getSpecifiedTarSourceFile();
		if (specifiedFile == null) {
			return false;
		}
		return true;
	}

	/**
	 * Answer a boolean indicating whether the specified source currently exists
	 * and is valid (ie.- proper format)
	 * 
	 * @return true, if ensure zip source is valid
	 */
	private boolean ensureZipSourceIsValid() {
		ZipFile specifiedFile = getSpecifiedZipSourceFile();
		if (specifiedFile == null) {
			return false;
		}
		return ArchiveFileManipulations.closeZipFile(specifiedFile,
				getContainer().getShell());
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
		return tok.getDiscussionFolder().getFullPath();
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

					// filter the links.xml
					Object[] files = l.getChildren(element);
					Object[] newFiles = new Object[0];
					for (int i = 0; i < files.length; i++) {
						if (files[i] instanceof MinimizedFileSystemElement) {
							MinimizedFileSystemElement file = (MinimizedFileSystemElement) files[i];
							String name = ((ZipEntry) file
									.getFileSystemObject()).getName();
							if (name
									.compareTo(DiscussionExportOperation.TEMP_LINKS_XML) == 0) {
								List<Object> filesList = new ArrayList<Object>();
								for (int j = 0; j < files.length; j++) {
									if (i != j) {
										filesList.add(files[j]);
									}
								}
								newFiles = filesList.toArray();
							}
						}
					}

					return newFiles;
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
				setAllSelections(false);
			}
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.internal.wizards.datatransfer.WizardFileSystemResourceImportPage1#handleTypesEditButtonPressed()
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	@Override
	protected void handleTypesEditButtonPressed() {
		selectedTypes.clear();
		selectedTypes.add("dis"); //$NON-NLS-1$
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
			if (ensureTarSourceIsValid()) {
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
		}

		if (ensureZipSourceIsValid()) {
			ZipFile zipFile = getSpecifiedZipSourceFile();
			
			ZipLeveledStructureProvider structureProvider = ArchiveFileManipulations
					.getZipStructureProvider(zipFile, getContainer().getShell());
			ImportOperation operation = new ImportOperation(
					getContainerFullPath(), structureProvider.getRoot(),
					structureProvider, this, fileSystemObjects);

			
			
			operation.setContext(getShell());
			result = executeImportOperation(operation);

			if (result) {
				String comment = zipFile.getEntry(DiscussionExportOperation.TEMP_LINKS_XML).getComment();
				if (comment != null) {
					lostExport = comment.compareTo(Activator.PLUGIN_ID) == 0 ? true	: false;
				}					
				if(!lostExport) {
					transformDiscussionFiles(zipFile);
				}
				// Merge link files
				createTempLinksFile(zipFile);
				mergeLinkfiles();
				deleteTempLinksFile();
			}
			closeZipFile(zipFile);
		}
		return result;
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
	 * Merge linkfiles.
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	private void mergeLinkfiles() {
		Document tempDoc = GeneralFunctions.readFromXML(tempLinksFile);
		Document linksDoc = GeneralFunctions.readFromXML(tok.getLinkFile());
		Element rootElement = linksDoc.getRootElement();
		List<Node> linkNodes = tempDoc.selectNodes("//link"); //$NON-NLS-1$
		for (Node node : linkNodes) {
			node.detach();
			rootElement.add(node);
		}
		GeneralFunctions.writeToXml(tok.getLinkFile(), linksDoc);
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

			for (String element : sourceNames) {
				sourceNameField.add(element);
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

	private void transformDiscussionFiles(ZipFile zipFile) {
		Enumeration entries = zipFile.entries();
		while(entries.hasMoreElements()){
			ZipEntry entry = (ZipEntry) entries.nextElement();
			if (entry.isDirectory() || entry.getName().compareTo(DiscussionExportOperation.TEMP_LINKS_XML)==0){
				//if the entry is directory or if it's the links.xml file
				continue;
			}
				
			String discussionFileName = entry.getName();
			IFolder discussionFolder = tok.getDiscussionFolder();
			IFile discussionFile = discussionFolder.getFile(discussionFileName);
			Document discussionDocument = GeneralFunctions.readFromXML(discussionFile);
			
			
			XPath sourcesXPath = DocumentHelper.createXPath("//sourceFile"); //$NON-NLS-1$
			List sourceNodes = sourcesXPath.selectNodes(discussionDocument);
			for (Object object : sourceNodes) {
				Element sourceFile = (Element)object;
				sourceFile.setText(ToK.ROOTS_FOLDER + "/" + sourceFile.getText()); //$NON-NLS-1$
			}
			GeneralFunctions.writeToXml(discussionFile, discussionDocument);
		}
	}

	private void transformLinksFile() {
		Document linksDocument = GeneralFunctions.readFromXML(tempLinksFile);
		
		
		XPath linkXPath = DocumentHelper.createXPath("//sourceFile"); //$NON-NLS-1$
		List linkNodes = linkXPath.selectNodes(linksDocument);
		for (Object object : linkNodes) {
			Element link = (Element)object;
			link.setText(ToK.ROOTS_FOLDER + "/" + link.getText()); //$NON-NLS-1$
		}
		GeneralFunctions.writeToXml(tempLinksFile, linksDocument);
		
	}

	/**
	 * @param zipFile
	 * @param linksEntry
	 * @param destFile
	 * @return
	 */
	private File unzipToTempLocation(ZipFile zipFile, ZipEntry linksEntry) {
		File destFile_ = null;
		try {
			BufferedInputStream is = new BufferedInputStream(zipFile
					.getInputStream(linksEntry));
			String tmpFilePath = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$
			File unzipDestinationDirectory = new File(tmpFilePath);
			destFile_ = new File(unzipDestinationDirectory, linksEntry
					.getName());
			int currentByte;
			// establish buffer for writing file
			byte data[] = new byte[BUFFER];
			// write the current file to disk
			FileOutputStream fos = new FileOutputStream(destFile_);
			BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
			// read and write until last byte is encountered
			while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
				dest.write(data, 0, currentByte);
			}
			dest.flush();
			dest.close();
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile_;
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

}


