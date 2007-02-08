package lost.tok.sourceParser.wizards;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

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
		super("wizardPage");
		setTitle("Document Parser Wizard");
		setDescription("This wizard sets and opens a txt file for parsing");
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
		label.setText("&Title:");

		sourceTitleText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		sourceTitleText.setLayoutData(gd);
		sourceTitleText.addModifyListener(this);
		
		label = new Label(container, SWT.NULL);
		
		// Author selection
		label = new Label(container, SWT.NULL);
		label.setText("&Author:");

		authorNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		authorNameText.setLayoutData(gd);
		authorNameText.addModifyListener(this);
		
		label = new Label(container, SWT.NULL);
		
		// Source path selection
		label = new Label(container, SWT.NULL);
		label.setText("Source &Path:");
		label.setToolTipText("The path of this document in the full source text");

		sourcePathText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		sourcePathText.setLayoutData(gd);
		sourcePathText.addModifyListener(this);
		
		label = new Label(container, SWT.NULL);
		
		// Source file selection
		label = new Label(container, SWT.NULL);
		label.setText("Source &File:");

		inputFilePath = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		inputFilePath.setLayoutData(gd);
		inputFilePath.addModifyListener(this);

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseForInputFile();
			}
		});
		
		// Destenation Project Selection
		label = new Label(container, SWT.NULL);
		label.setText("Pro&ject:");

		targetProject = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		targetProject.setLayoutData(gd);
		targetProject.addModifyListener(this);
		
		button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseForTargetProject();
			}
		});
		
		// IsProject CheckBox
		label = new Label(container, SWT.NULL);
		label.setText("Is &Root:");
		label.setToolTipText("Is this document a root or a source");
		
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
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				targetProject.setText(container.getName());
			}
		}
	}

	/** Opens a dialog for the user to pick his Unparsed File in the filesystem */
	private void handleBrowseForInputFile() {
		final String EXTENSIONS[] = {"*.txt","*.*"};
		final String DESCRIPTIONS[] = {"Text Files (*.txt)","All Types (*.*)"};
		
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
		dialog.setTitle("Select Target Project");
		dialog.setMessage("The document will be added to the selected project");

		if (dialog.open() == ResourceSelectionDialog.OK)
		{
			Object[] result = dialog.getResult();
			if (result.length == 1)
				targetProject.setText( ((IProject)result[0]).getName() );				
		}

	}


	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		/** The full name of the source: sourceTitleText */		
		if (sourceTitleText.getText().length() == 0)
		{
			updateStatus("Title must be specified");
			return;
		}
		
		/** The name of the source's Author: authorNameText */
		if (authorNameText.getText().length() == 0)
		{
			updateStatus("Author name must be specified");
			return;
		}
		
		/** The path of the specific source: sourcePathText */
		String srcPath = sourcePathText.getText(); 
		if (srcPath.length() != 0 && srcPath.charAt(srcPath.length()-1)=='\\')
		{
			updateStatus("Source Path should not end with a slash");
			return;
		}
		
		if (srcPath.indexOf('/') != -1)
		{
			updateStatus("SrcPath should be seperated by slash and not backslash");
			return;		
		}
		
		/** The path of the file to be parsed: inputFilePath */
		if (inputFilePath.getText().length() == 0)
		{
			updateStatus("Input filename must be specified");
			return;
		}
		
		File f = new File(inputFilePath.getText());
		if (!f.exists() || !f.isFile())
		{
			updateStatus("Input filename is illegal");
			return;
		}
		f = null;
		
		/** The destanation project: targetProject */
		Path path = new Path("");
		
		if (!path.isValidSegment(targetProject.getText()))
		{
			updateStatus("Project Name is invalid");
			return;
		}
		// TODO(Shay): Verify that the project is a ToK
		IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(targetProject.getText());
		if ( !proj.exists() )
		{
			updateStatus("Project does not exist");
			return;
		}
		
		// Verify that no source of the same name is currently being parsed
		IFile upFile = 
			UnparsedDocWizard.getUnparsedTargetIFile(targetProject.getText(), inputFilePath.getText());
		if (upFile.exists())
		{
			updateStatus("File " + upFile.getName() + " is currently being parsed");
			return;
		}

		// Verify that no source of the same name already exists
		IFile pFile = 
			UnparsedDocWizard.getParsedTargetIFile(targetProject.getText(), inputFilePath.getText());
		if (pFile.exists())
		{
			updateStatus("File " + pFile.getName() + " already exists");
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