package lost.tok.sourceParser.wizards;

import java.util.LinkedList;

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
		sourcePathText.setToolTipText("The path of this document in the full source text");
		
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
		label.setText("P&roject:");

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
				targetProject.setText(container.getFullPath().toString());
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
		
		// FIXME(Shay): Doesn't work :(
		
		IProject projects[] = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		String projNames[] = new String[projects.length];
		LinkedList<String> projNameList = new LinkedList<String>();
		for (int i = 0; i < projects.length; i++)
		{
			projNames[i] = projects[i].getName();
			projNameList.addLast(projects[i].getName());
		}
		
		dialog.setAddCancelButton(true);
		dialog.setBlockOnOpen(true);
		
		dialog.setLabelProvider(new LabelProvider() {
            public String getText(Object element) {
                // Return the resolution's label.
                return element == null ? "" : (String)element; //$NON-NLS-1$
            }
        });
		/*
		SimpleListContentProvider scp = new SimpleListContentProvider();
		scp.setElements(projNames);
		dialog.setContentProvider(scp);*/
		
		dialog.setInitialElementSelections(projNameList);
				
		// TODO(Shay): Filter the project and choose only those that are ToK
		dialog.setTitle("Select Target Project");
		dialog.setMessage("The document will be added to the selected project");
		//dialog.setAllowUserToToggleDerived(false);

		if (dialog.open() == ResourceSelectionDialog.OK)
		{
			Object[] result = dialog.getResult();
			if (result.length == 1)
				targetProject.setText(((Path) result[0]).toString());				
		}
		
		
/*		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				"Select target project");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				targetProject.setText(((Path) result[0]).toString());
			}
		}*/
	}


	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		// TODO(Shay)
/*		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getContainerName()));
		String fileName = getFileName();

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("mpe") == false) {
				updateStatus("File extension must be \"mpe\"");
				return;
			}
		}*/
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

	public String getInputFileNamw() {
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
}