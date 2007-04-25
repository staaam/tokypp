package lost.tok.sourceParser.wizards;

import java.io.File;

import lost.tok.Messages;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
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
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * The (only) page of the wizard of the source parser
 * 
 * Supports choosing the project of the file, the file to parse
 * and filling details regarding the new source.
 * 
 * Performs basic checks regarding the validity of the data
 */
public class UnparsedDocWizardPage extends WizardPage implements ModifyListener {
	/** The full name of the source */
	private Text sourceTitleText;

	/** The name of the source's Author */
	private Text authorNameText;

	/** The path of the specific source (ie bible\genesis\) */
	private Text sourcePathText;

	/** The path of the file to be parsed */
	private Text inputFilePath;

	/** The destanation project */
	private Text targetProject;

	/** Checked if this should be root, unchecked otherwise */
	private Button isRootButton;

	private boolean isRootField;

	private ISelection selection;

	/**
	 * Constructor for UnparsedDocWizardPage.
	 */
	public UnparsedDocWizardPage(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.getString("SPWizardP.DocumentParserWizardTitle")); //$NON-NLS-1$
		setDescription(Messages
				.getString("SPWizardP.DocumentParserWizardDescription")); //$NON-NLS-1$
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
		label.setText(Messages.getString("SPWizardP.FieldTitle")); //$NON-NLS-1$

		sourceTitleText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		sourceTitleText.setLayoutData(gd);
		sourceTitleText.addModifyListener(this);

		label = new Label(container, SWT.NULL);

		// Author selection
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("SPWizardP.FieldAuthor")); //$NON-NLS-1$

		authorNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		authorNameText.setLayoutData(gd);
		authorNameText.addModifyListener(this);

		label = new Label(container, SWT.NULL);

		// Source path selection
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("SPWizardP.FieldSrcPath")); //$NON-NLS-1$
		label.setToolTipText(Messages
				.getString("SPWizardP.FieldSrcPathDescription")); //$NON-NLS-1$

		sourcePathText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		sourcePathText.setLayoutData(gd);
		sourcePathText.addModifyListener(this);

		label = new Label(container, SWT.NULL);

		// Source file selection
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("SPWizardP.FieldSrcInputFile")); //$NON-NLS-1$

		inputFilePath = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		inputFilePath.setLayoutData(gd);
		inputFilePath.addModifyListener(this);

		Button button = new Button(container, SWT.PUSH);
		button.setText(Messages.getString("SPWizardP.BrowseButton")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseForInputFile();
			}
		});

		// Destenation Project Selection
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("SPWizardP.FieldProject")); //$NON-NLS-1$

		targetProject = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		targetProject.setLayoutData(gd);
		targetProject.addModifyListener(this);

		button = new Button(container, SWT.PUSH);
		button.setText(Messages.getString("SPWizardP.BrowseButton")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseForTargetProject();
			}
		});

		// IsProject CheckBox
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("SPWizardP.FieldIsRoot")); //$NON-NLS-1$
		label.setToolTipText(Messages
				.getString("SPWizardP.FieldIsRootDescription")); //$NON-NLS-1$

		isRootButton = new Button(container, SWT.CHECK);
		isRootButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				isRootField = !isRootField;
				dialogChanged();
			}
		});
		isRootField = false;

		label = new Label(container, SWT.NULL);

		// Last:
		initialize();
		dialogChanged();
		setControl(container);
	}

	/** Tries to initialize the targetProject field to the current project */
	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1) {
				return;
			}
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IProject proj = ((IResource)obj).getProject();
				targetProject.setText(proj.getName());
			}
		}
	}

	/** Opens a dialog for the user to pick his Unparsed File in the filesystem */
	private void handleBrowseForInputFile() {
		final String EXTENSIONS[] = { "*.txt", "*.*" }; //$NON-NLS-1$ //$NON-NLS-2$
		final String DESCRIPTIONS[] = {
				Messages.getString("SPWizardP.15"), Messages.getString("SPWizardP.16") }; //$NON-NLS-1$ //$NON-NLS-2$

		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		dialog.setFilterExtensions(EXTENSIONS);
		dialog.setFilterNames(DESCRIPTIONS);
		String result = dialog.open();
		inputFilePath.setText(result);
	}

	/** Opens a dialog for the user to pick his Target Project */
	private void handleBrowseForTargetProject() {

		ListDialog dialog = new ListDialog(getShell());

		dialog.setAddCancelButton(true);
		dialog.setBlockOnOpen(true);

		IWorkspaceRoot iwr = ResourcesPlugin.getWorkspace().getRoot();
		dialog.setContentProvider(new BaseWorkbenchContentProvider());
		dialog.setLabelProvider(new WorkbenchLabelProvider());
		dialog.setInput(iwr);

		// TODO(Shay): Filter the project and choose only those that are ToK
		dialog.setTitle(Messages.getString("SPWizardP.ProjectBrowse.title")); //$NON-NLS-1$
		dialog.setMessage(Messages
				.getString("SPWizardP.ProjectBrowse.Description")); //$NON-NLS-1$

		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				targetProject.setText(((IProject) result[0]).getName());
			}
		}

	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		/** The full name of the source: sourceTitleText */
		if (sourceTitleText.getText().length() == 0) {
			updateStatus(Messages.getString("SPWizardP.ErrorMissingTitle")); //$NON-NLS-1$
			return;
		}

		/** The name of the source's Author: authorNameText */
		if (authorNameText.getText().length() == 0) {
			updateStatus(Messages.getString("SPWizardP.ErrorMissingAuthor")); //$NON-NLS-1$
			return;
		}

		/** The path of the specific source: sourcePathText */
		String srcPath = sourcePathText.getText();
		if (srcPath.length() != 0
				&& srcPath.charAt(srcPath.length() - 1) == '\\') {
			updateStatus(Messages.getString("SPWizardP.ErrorSlash")); //$NON-NLS-1$
			return;
		}

		if (srcPath.indexOf('/') != -1) {
			updateStatus(Messages.getString("SPWizardP.ErrorBackslash")); //$NON-NLS-1$
			return;
		}

		/** The path of the file to be parsed: inputFilePath */
		if (inputFilePath.getText().length() == 0) {
			updateStatus(Messages.getString("SPWizardP.ErrorMissingInputFile")); //$NON-NLS-1$
			return;
		}

		File f = new File(inputFilePath.getText());
		if (!f.exists() || !f.isFile()) {
			updateStatus(Messages.getString("SPWizardP.ErrorIllegalFilename")); //$NON-NLS-1$
			return;
		}
		f = null;

		/** The destanation project: targetProject */
		Path path = new Path(""); //$NON-NLS-1$

		if (!path.isValidSegment(targetProject.getText())) {
			updateStatus(Messages.getString("SPWizardP.ErrorInvalidProject")); //$NON-NLS-1$
			return;
		}
		// TODO(Shay): Verify that the project is a ToK
		IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(
				targetProject.getText());
		if (!proj.exists()) {
			updateStatus(Messages.getString("SPWizardP.ErrorMissingProject")); //$NON-NLS-1$
			return;
		}

		// Verify that no source of the same name is currently being parsed
		IFile upFile = UnparsedDocWizard.getUnparsedTargetIFile(targetProject
				.getText(), inputFilePath.getText());
		if (upFile.exists()) {
			updateStatus(Messages.getString("SPWizardP.File") + upFile.getName() + Messages.getString("SPWizardP.ErrorBeingParsed")); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		// Verify that no source of the same name already exists
		IFile pFile = UnparsedDocWizard.getParsedTargetIFile(targetProject
				.getText(), inputFilePath.getText());
		if (pFile.exists()) {
			updateStatus(Messages.getString("SPWizardP.File") + pFile.getName() + Messages.getString("SPWizardP.ErrorAlreadyParsed")); //$NON-NLS-1$ //$NON-NLS-2$
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

	public String getAuthorName() {
		return authorNameText.getText();
	}

	public String getInputFileName() {
		return inputFilePath.getText();
	}

	public String getSourcePath() {
		return sourcePathText.getText();
	}

	public String getSourceTitle() {
		return sourceTitleText.getText();
	}

	public String getTargetProjectName() {
		return targetProject.getText();
	}

	public boolean getIsRoot() {
		return isRootField;
	}
}