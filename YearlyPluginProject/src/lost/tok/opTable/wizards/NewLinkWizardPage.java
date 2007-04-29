package lost.tok.opTable.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.Link;
import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.excerptionsView.ExcerptionView;
import lost.tok.wizards.NewDiscussion;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
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

/**
 * A page in the link root - discussion wizard
 * 
 * @author Team Lost
 * 
 */
public class NewLinkWizardPage extends WizardPage {

	private HashMap<String, Discussion> discMap = new HashMap<String, Discussion>();

	private Combo discussionCombo;

	private Tree excerptions;

	private ExcerptionView expViewer;

	private Combo linkType;

	@SuppressWarnings("unused")
	private ISelection selection;

	private Text subject;

	private IProject project;

	/**
	 * Constructor for NewLinkWizardPage.
	 * 
	 * @param pageName
	 */
	public NewLinkWizardPage() {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.getString("NewLinkWizardPage.0")); //$NON-NLS-1$
		setDescription(Messages.getString("NewLinkWizardPage.12")); //$NON-NLS-1$
		project = ExcerptionView.getView().getProject();
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
		label.setText(Messages.getString("NewLinkWizardPage.14")); //$NON-NLS-1$
		
		Composite c = new Composite(container, SWT.NONE);
		GridLayout layout2 = new GridLayout();
		c.setLayout(layout2);
		layout2.numColumns = 2;
		layout2.marginWidth = 0;
		layout2.marginHeight = 0;
		c.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.FILL_HORIZONTAL));

		discussionCombo = new Combo(c, SWT.READ_ONLY | SWT.DROP_DOWN);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		discussionCombo.setLayoutData(gd);
		projectSelected();
		discussionCombo.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent arg0) {
				dialogChanged();
			}

		});

		Button newDisButton = new Button(c, SWT.NONE);
		newDisButton.setText("New...");
		
		newDisButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				NewDiscussion w = new NewDiscussion(project);

				WizardDialog wd = new WizardDialog(new Shell(), w);
				wd.setBlockOnOpen(true);

				wd.open();
				
				if (wd.getReturnCode() != WizardDialog.OK)
					return;

				String newDiscussion = w.getDiscussionName();
				projectSelected();
				for (int i=0; i<discussionCombo.getItemCount(); i++) {
					if (newDiscussion.compareTo(discussionCombo.getItem(i)) != 0)
						continue;
					
					discussionCombo.select(i);
					dialogChanged();
					break;
				}
			}
		});
		
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewLinkWizardPage.1")); //$NON-NLS-1$

		subject = new Text(container, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		subject.setLayoutData(gd);
		subject.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				dialogChanged();

			}

		});

		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewLinkWizardPage.2")); //$NON-NLS-1$

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
		label.setText(Messages.getString("NewLinkWizardPage.15")); //$NON-NLS-1$
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		label.setLayoutData(gd);

		expViewer = ExcerptionView.getView();
		Tree tree = expViewer.getTree();
		TreeItem[] files = tree.getItems();

		excerptions = new Tree(container, SWT.BORDER | SWT.MULTI);
		excerptions.setSize(600, 300);

		for (TreeItem element : files) {
			TreeItem file = new TreeItem(excerptions, 0);
			file.setText(element.getText());
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
	 * Ensures that all fields are set
	 * 
	 */
	private void dialogChanged() {
		if (discussionCombo.getText() == "") { //$NON-NLS-1$
			updateStatus(Messages.getString("NewLinkWizardPage.6")); //$NON-NLS-1$
			return;
		}

		if (subject.getText() == "") { //$NON-NLS-1$
			updateStatus(Messages.getString("NewLinkWizardPage.8")); //$NON-NLS-1$
			return;
		}

		if (linkType.getText() == "") { //$NON-NLS-1$
			updateStatus(Messages.getString("NewLinkWizardPage.10")); //$NON-NLS-1$
			return;
		}

		if (excerptions.getSelection().length == 0) {
			updateStatus(Messages.getString("NewLinkWizardPage.11")); //$NON-NLS-1$
			return;
		}

		updateStatus(null);
	}

	public String getDiscussion() {
		return discussionCombo.getText();
	}

	public Excerption[] getExcerptions(String fileName) {
		List<Excerption> excerptions = expViewer.getExcerptions(fileName);
		Excerption[] exs = new Excerption[excerptions.size()];
		excerptions.toArray(exs);
		return exs;
	}

	public String getLinkType() {
		return linkType.getText();
	}

	public String getProject() {
		return project.getName();
	}

	public String[] getSourceFiles() {
		TreeItem[] selected = excerptions.getSelection();
		String[] selectedNames = new String[selected.length];
		for (int i = 0; i < selected.length; i++) {
			selectedNames[i] = selected[i].getText();
		}
		return selectedNames;
	}

	public String getSubject() {
		return subject.getText();
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {

//		if (selection != null && selection.isEmpty() == false
//				&& selection instanceof IStructuredSelection) {
//			IStructuredSelection ssel = (IStructuredSelection) selection;
//			if (ssel.size() > 1) {
//				return;
//			}
//			Object obj = ssel.getFirstElement();
//			if (obj instanceof IResource) {
//				IResource resource = (IResource) obj;
//				String projectName = resource.getProject().getName();
//				int chosenProjIndex = -1;
//				String[] projectComboNames = projectCombo.getItems();
//				for (int i = 0; i < projectComboNames.length; i++) {
//					if (projectComboNames[i].compareTo(projectName) == 0) {
//						chosenProjIndex = i;
//						break;
//					}
//				}
//				projectCombo.select(chosenProjIndex);
//				projectSelected();
//
//				String discName = resource.getName().split(".dis")[0]; //$NON-NLS-1$
//				int chosenDiscIndex = -1;
//				String[] discComboNames = discussionCombo.getItems();
//				for (int i = 0; i < discComboNames.length; i++) {
//					if (discComboNames[i].compareTo(discName) == 0) {
//						chosenDiscIndex = i;
//						break;
//					}
//				}
//				discussionCombo.select(chosenDiscIndex);
//				discussionCombo.redraw();
//			}
//		}
	}

	private void projectSelected() {
		// //$NON-NLS-1$ //$NON-NLS-2$
		// TODO
		ToK tok = ToK.getProjectToK(project);
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

	/**
	 * Sets the project in which context the linkage occurs
	 * 
	 * @param projectName
	 */
//	public void setProjectName(String projectName) {
//		int chosenProjIndex = -1;
//		String[] projectComboNames = projectCombo.getItems();
//		for (int i = 0; i < projectComboNames.length; i++) {
//			if (projectComboNames[i].compareTo(this.projectName) == 0) {
//				chosenProjIndex = i;
//				break;
//			}
//		}
//		projectCombo.select(chosenProjIndex);
//		projectSelected();
//	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

}