package lost.tok.opTable.wizards;

import java.util.List;

import lost.tok.DiscussionLink;
import lost.tok.Excerption;
import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.excerptionsView.ExcerptionView;
import lost.tok.wizards.DiscCombo;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * A page in the link root - discussion wizard
 * 
 * @author Team Lost
 * 
 */
public class NewLinkWizardPage extends WizardPage implements SelectionListener,
		ModifyListener {
	private DiscCombo discussionCombo;

	private Tree excerptions;

	private Combo linkType;

	private Text subject;

	private ToK tok;

	/**
	 * Constructor for NewLinkWizardPage.
	 * 
	 * @param pageName
	 */
	public NewLinkWizardPage() {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.getString("NewLinkWizardPage.0")); //$NON-NLS-1$
		setDescription(Messages.getString("NewLinkWizardPage.12")); //$NON-NLS-1$
		tok = ToK.getProjectToK(ExcerptionView.getView().getProject());
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridData gd;
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;

		// discussion
		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewLinkWizardPage.14")); //$NON-NLS-1$

		discussionCombo = new DiscCombo(container, SWT.NONE, tok);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		discussionCombo.setLayoutData(gd);
		discussionCombo.addSelectionListener(this);
		discussionCombo.init();

		// subject
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewLinkWizardPage.1")); //$NON-NLS-1$

		subject = new Text(container, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		subject.setLayoutData(gd);
		subject.addModifyListener(this);

		// link type
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewLinkWizardPage.2")); //$NON-NLS-1$

		linkType = new Combo(container, SWT.READ_ONLY | SWT.DROP_DOWN);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		linkType.setLayoutData(gd);
		linkType.setItems(DiscussionLink.linkDisplayNames);
		linkType.addSelectionListener(this);

		// roots
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewLinkWizardPage.15")); //$NON-NLS-1$
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		label.setLayoutData(gd);

		excerptions = new Tree(container, SWT.BORDER | SWT.MULTI);
		excerptions.setSize(600, 300);

		for (String root : ExcerptionView.getView().getRoots()) {
			TreeItem file = new TreeItem(excerptions, 0);
			file.setText(root);
		}
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		excerptions.addSelectionListener(this);
		excerptions.selectAll();
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
		if (discussionCombo.getText().length() == 0) { //$NON-NLS-1$
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

	public String getDiscussionName() {
		return discussionCombo.getText();
	}

	public Excerption[] getExcerptions(String fileName) {
		List<Excerption> excerptions = ExcerptionView.getView().getExcerptions(
				fileName);
		Excerption[] exs = new Excerption[excerptions.size()];
		excerptions.toArray(exs);
		return exs;
	}

	/**
	 * Returns the type of the link chosen
	 * 
	 * @return the String for link type which should be written in the xml (and
	 *         not the display string)
	 */
	public String getLinkType() {
		int indexChosen = linkType.getSelectionIndex();
		if (indexChosen == -1)
			return ""; //$NON-NLS-1$
		return DiscussionLink.linkXMLTypes[indexChosen];
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

		// if (selection != null && selection.isEmpty() == false
		// && selection instanceof IStructuredSelection) {
		// IStructuredSelection ssel = (IStructuredSelection) selection;
		// if (ssel.size() > 1) {
		// return;
		// }
		// Object obj = ssel.getFirstElement();
		// if (obj instanceof IResource) {
		// IResource resource = (IResource) obj;
		// String projectName = resource.getProject().getName();
		// int chosenProjIndex = -1;
		// String[] projectComboNames = projectCombo.getItems();
		// for (int i = 0; i < projectComboNames.length; i++) {
		// if (projectComboNames[i].compareTo(projectName) == 0) {
		// chosenProjIndex = i;
		// break;
		// }
		// }
		// projectCombo.select(chosenProjIndex);
		// projectSelected();
		//
		// String discName = resource.getName().split(".dis")[0]; //$NON-NLS-1$
		// int chosenDiscIndex = -1;
		// String[] discComboNames = discussionCombo.getItems();
		// for (int i = 0; i < discComboNames.length; i++) {
		// if (discComboNames[i].compareTo(discName) == 0) {
		// chosenDiscIndex = i;
		// break;
		// }
		// }
		// discussionCombo.select(chosenDiscIndex);
		// discussionCombo.redraw();
		// }
		// }
	}

	/**
	 * Sets the project in which context the linkage occurs
	 * 
	 * @param projectName
	 */
	// public void setProjectName(String projectName) {
	// int chosenProjIndex = -1;
	// String[] projectComboNames = projectCombo.getItems();
	// for (int i = 0; i < projectComboNames.length; i++) {
	// if (projectComboNames[i].compareTo(this.projectName) == 0) {
	// chosenProjIndex = i;
	// break;
	// }
	// }
	// projectCombo.select(chosenProjIndex);
	// projectSelected();
	// }
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void widgetSelected(SelectionEvent arg0) {
		dialogChanged();

	}

	public void modifyText(ModifyEvent arg0) {
		dialogChanged();

	}

	public ToK getTok() {
		return tok;
	}

}