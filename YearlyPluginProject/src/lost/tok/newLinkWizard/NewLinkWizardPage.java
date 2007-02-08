package lost.tok.newLinkWizard;

import java.util.ArrayList;
import java.util.HashMap;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.Link;
import lost.tok.ToK;
import lost.tok.excerptionsView.ExcerptionView;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class NewLinkWizardPage extends WizardPage {

	@SuppressWarnings("unused")
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

		Label label = new Label(container, SWT.NULL);
		label.setText("Project:"); //$NON-NLS-1$

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		String[] projectNames = new String[projects.length];

		for (int i = 0; i < projects.length; i++) {
			projectNames[i] = projects[i].getName();
		}

		projectCombo = new Combo(container, SWT.READ_ONLY | SWT.DROP_DOWN);
		projectCombo.setItems(projectNames);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		projectCombo.setLayoutData(gd);

		projectCombo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent arg0) {
				projectSelected();
				dialogChanged();
			}

		});

		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$

		label = new Label(container, SWT.NULL);
		label.setText("Discussion"); //$NON-NLS-1$
		

		discussionCombo = new Combo(container, SWT.READ_ONLY | SWT.DROP_DOWN);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		discussionCombo.setLayoutData(gd);
		discussionCombo.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent arg0) {
				dialogChanged();
			}

		});

		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$

		label = new Label(container, SWT.NULL);
		label.setText("Subject:");

		subject = new Text(container, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		subject.setLayoutData(gd);
		subject.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				dialogChanged();

			}

		});

		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$

		label = new Label(container, SWT.NULL);
		label.setText("Link type:");

		linkType = new Combo(container, SWT.READ_ONLY | SWT.DROP_DOWN);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		linkType.setLayoutData(gd);
		linkType.setItems(Link.linkTypes);
		linkType.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent arg0) {
				dialogChanged();

			}

		});

		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$
		
		label = new Label(container, SWT.NULL);
		label.setText("Root files:"); //$NON-NLS-1$
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		label.setLayoutData(gd);

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

		excerptions = new Tree(container, SWT.BORDER | SWT.MULTI);
		excerptions.setSize(600, 300);
		
		for (int i = 0; i < files.length; i++) {
			TreeItem file = new TreeItem(excerptions, 0);
			file.setText(files[i].getText());
		}
		gd = new GridData(GridData.FILL_BOTH);
		excerptions.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent arg0) {
				dialogChanged();

			}

		});

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
				IResource resource = (IResource) obj;
				String projectName = resource.getProject().getName();
				int chosenProjIndex = -1;
				String[] projectComboNames = projectCombo.getItems();
				for (int i = 0; i < projectComboNames.length; i++) {
					if (projectComboNames[i].compareTo(projectName) == 0) {
						chosenProjIndex = i;
						break;
					}
				}
				projectCombo.select(chosenProjIndex);
				projectSelected();
				
				String discName = resource.getName().split(".dis")[0];
				int chosenDiscIndex = -1;
				String[] discComboNames = discussionCombo.getItems();
				for (int i = 0; i < discComboNames.length; i++) {
					if (discComboNames[i].compareTo(discName) == 0) {
						chosenDiscIndex = i;
						break;
					}
				}
				discussionCombo.select(chosenDiscIndex);
				discussionCombo.redraw();
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		if (projectCombo.getText() == "") {
			updateStatus("Please select a project");
			return;
		}

		if (discussionCombo.getText() == "") {
			updateStatus("Please select a discussion");
			return;
		}

		if (subject.getText() == "") {
			updateStatus("Please fill-in a subject for the link");
			return;
		}

		if (linkType.getText() == "") {
			updateStatus("Please select the type of the link");
			return;
		}

		if (excerptions.getSelection().length == 0) {
			updateStatus("Please choose the root file to link to");
			return;
		}

		updateStatus(null);
	}

	private void projectSelected() {
		// TODO Auto-generated method stub
		String chosenProject = projectCombo.getText();

		ToK tok = new ToK(chosenProject, "Arie", "Babel_he.src");
		// TODO
		// ToK tok = ToK.getProjectToK(project);
		ArrayList<Discussion> discussions = new ArrayList<Discussion>(tok
				.getDiscussions());
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

	public String getDiscussion() {
		return discussionCombo.getText();
	}

	public String getProject() {
		return projectCombo.getText();
	}

	public Excerption[] getExcerptions(String fileName) {
		int i = 0;
		ArrayList<Excerption> list = (ArrayList<Excerption>) expViewer
				.getExcerptions(fileName);
		Excerption[] array = new Excerption[list.size()];
		for (Excerption excerption : list) {
			array[i++] = excerption;
		}
		return array;
	}

	public String[] getSourceFiles() {
		TreeItem[] selected = excerptions.getSelection();
		String[] selectedNames = new String[selected.length];
		for (int i = 0; i < selected.length; i++) {
			selectedNames[i] = selected[i].getText();
		}
		return selectedNames;
	}

	public String getLinkType() {
		return linkType.getText();
	}

	public String getSubject() {
		return subject.getText();
	}

}