package lost.tok.newLinkWizard;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.swt.widgets.FileDialog;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.Link;
import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.excerptionsView.ExcerptionView;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class NewLinkWizardPage extends WizardPage {


	private ISelection selection;

	private Combo projectCombo;

	private Combo discussionCombo;
	
	private Combo linkType;
	
	private Text subject;
	
	private Tree excerptions;
	
	private HashMap<String, Discussion> discMap = new HashMap<String, Discussion>();
	
	private ExcerptionView expViewer;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewLinkWizardPage(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle("Link Discussion to Root"); //$NON-NLS-1$
		setDescription("Links a discussion to excerptions in a root file"); //$NON-NLS-1$
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

		Label	label = new Label(container, SWT.NULL);
		label.setText("Project:"); //$NON-NLS-1$
		
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		String[] projectNames = new String[projects.length];
		
		for (int i = 0; i < projects.length; i++) {
			projectNames[i] = projects[i].getName();
		}
		
		projectCombo = new Combo(container,SWT.NULL);
		projectCombo.setItems(projectNames);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		projectCombo.setLayoutData(gd);

		projectCombo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				//widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent arg0) {
				projectSelected(arg0);
				
			}

		});

		
		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$

		label = new Label(container, SWT.NULL);
		label.setText("Discussion"); //$NON-NLS-1$
		
		discussionCombo = new Combo(container,SWT.NULL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		discussionCombo.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$

		label = new Label(container,SWT.NULL);
		label.setText("Subject:");
		
		subject = new Text(container,SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		subject.setLayoutData(gd);
		
		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$
		

		label = new Label(container,SWT.NULL);
		label.setText("Link type:");
		
		linkType = new Combo(container,SWT.NULL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		linkType.setLayoutData(gd);
		linkType.setItems(Link.linkTypes);
		
		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$
		
		
		
		IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(ExcerptionView.ID);

		if (view == null) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().activate(new ExcerptionView());
			view = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().findView(ExcerptionView.ID);
		}

		expViewer = (ExcerptionView) view;
		Tree tree = expViewer.getTree();
		TreeItem[] files = tree.getItems();
		
		excerptions = new Tree(container,SWT.BORDER);
		excerptions.setSize(600, 300);
	
//		excerptions.setContentProvider(expViewer.getContentProvider());
//		excerptions.setLabelProvider(expViewer.getLabelProvider());
//		excerptions.setInput(expViewer.getInput());
//		
		for (int i = 0; i < files.length; i++) {
			TreeItem file = new TreeItem(excerptions,0);
			file.setText(files[i].getText());
			
//			for (int j = 0; j < files[i].getItems().length; j++) {
//				TreeItem exp = new TreeItem(file,0);
//				exp.setText(files[i].getItem(j).getText());
//			}
			//excerptions.add(files[i], files[i].getItems());
		}
		gd = new GridData(GridData.FILL_BOTH);
//		excerptions.getControl().setLayoutData(gd);
//		excerptions.expandAll();
//		excerptions.refresh();
//		
		excerptions.setLayoutData(gd);
		excerptions.redraw();
		initialize();
		dialogChanged();
		setControl(container);
	}
	
	
	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

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
			//	rootText.setText(container.getFullPath().toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		/*
		 * String projectName = getProjectName(); String creatorName =
		 * getCreatorName(); String rootName = getRootName();
		 * 
		 * if (projectName.length() == 0) {
		 * updateStatus(Messages.getString("NewToKWizErrSpecName"));
		 * //$NON-NLS-1$ return; } if (projectNameExists(projectName)) {
		 * updateStatus(Messages.getString("NewToKWizErrExist")); //$NON-NLS-1$
		 * return; } if (projectName.replace('\\', '/').indexOf('/', 1) > 0) {
		 * updateStatus(Messages.getString("NewToKWizErrNameInvalid"));
		 * //$NON-NLS-1$ return; }
		 * 
		 * if (creatorName.length() == 0) {
		 * updateStatus(Messages.getString("NewToKWizErrMissingCreator"));
		 * //$NON-NLS-1$ return; }
		 * 
		 * if (rootName.length() == 0) {
		 * updateStatus(Messages.getString("NewToKWizErrSelectRoot"));
		 * //$NON-NLS-1$ return; } if (!legalRootExtension(rootName)) {
		 * updateStatus(Messages.getString("NewToKWizErrRootNotSrc"));
		 * //$NON-NLS-1$ return; } if (!fileExists(rootName)) {
		 * updateStatus(Messages.getString("NewToKWizErrRootNotExist"));
		 * //$NON-NLS-1$ return; }
		 */

		updateStatus(null);
	}

	private void projectSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		String chosenProject = projectCombo.getText();
		
		
//		 FOR DEBUGGING ONLY!!!!!!!!
		ToK tok = new ToK(chosenProject, "Arie", "Babel_he.src");
		// @TODO
		// ToK tok = ToK.getProjectToK(project);
		ArrayList<Discussion> discussions = new ArrayList<Discussion>(tok.getDiscussions());
		String[] discs = new String[discussions.size()];
		int i = 0;
		for (Discussion discussion : discussions) {
			discs[i] = discussion.getDiscName();
			discMap.put(discs[i++], discussion);
		}
		discussionCombo.setItems(discs);
		discussionCombo.redraw();
	}
	
	

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	public String getDiscussion(){
		return discussionCombo.getText();
	}
	
	public String getProject(){
		return projectCombo.getText();
	}

	public Excerption[] getExcerptions(){
		int i = 0;
		ArrayList<Excerption> list = (ArrayList<Excerption>)expViewer.getExcerptions(excerptions.getSelection()[0].getText());
		Excerption[] array = new Excerption[list.size()];
		for (Excerption excerption : list) {
			array[i++] = excerption;
		}
		return array;
	}
	
	public String getSourceFile(){
		return excerptions.getSelection()[0].getText();
	}

	public String getLinkType() {
		return linkType.getText();
	}

	public String getSubject() {
		return subject.getText();
	}
	
	
	
}