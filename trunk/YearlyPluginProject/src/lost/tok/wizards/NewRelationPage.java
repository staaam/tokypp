package lost.tok.wizards;

import lost.tok.Discussion;
import lost.tok.Messages;
import lost.tok.Quote;
import lost.tok.ToK;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
 * A page in the creation of relation wizard
 * 
 * @author Team Lost
 * 
 */
public class NewRelationPage extends WizardPage {

	private Text comment;

//	private String discName;
	private Discussion discussion;

	private Tree leftObjects;

//	private String projectName;
	private IProject project; 

	private Combo relType;

	private Tree rightObjects;

	private ISelection selection;

	/**
	 * Constructor for NewRelationWizardPage.
	 * 
	 */
	public NewRelationPage(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.getString("NewRelationWizardPage.0")); //$NON-NLS-1$
		setDescription(Messages.getString("NewRelationWizardPage.1")); //$NON-NLS-1$
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		getShell().setSize(600, 400);
		initialize();
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;

		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewRelationWizardPage.2")); //$NON-NLS-1$

		relType = new Combo(container, SWT.READ_ONLY | SWT.DROP_DOWN);
		for (String element : Discussion.relTypes) {
			relType.add(element); // TODO(Shay): Make sure this is translated in Hebrew
		}

		relType.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				dialogChanged();
			}

		});

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		relType.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewRelationWizardPage.3")); //$NON-NLS-1$
		comment = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		comment.setLayoutData(gd);
		comment.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText(""); //$NON-NLS-1$		

		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("NewRelationWizardPage.4")); //$NON-NLS-1$
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		label.setLayoutData(gd);

		gd = new GridData(GridData.FILL_BOTH);
		leftObjects = new Tree(container, SWT.BORDER);
		leftObjects.setLayoutData(gd);
		leftObjects.setSize(100, 200);

		gd = new GridData(GridData.FILL_BOTH);
		rightObjects = new Tree(container, SWT.BORDER);
		rightObjects.setLayoutData(gd);
		rightObjects.setSize(100, 200);

		// FOR DEBUGGING ONLY!!!!!!!!
		// ToK tok = new ToK(projectName, "Arie", "Babel_he.src");
		// @TODO

		String[] opinionNames = discussion.getOpinionNames();
		Integer[] opinionIDs = discussion.getOpinionIDs();

		for (int i = 0; i < opinionNames.length; i++) {
			TreeItem leftOpinion = new TreeItem(leftObjects, 0);
			TreeItem rightOpinion = new TreeItem(rightObjects, 0);
			leftOpinion.setText(opinionNames[i]);
			leftOpinion.setData(opinionIDs[i]);
			rightOpinion.setText(opinionNames[i]);
			rightOpinion.setData(opinionIDs[i]);

			Quote[] quotes = null;
			quotes = discussion.getQuotes(opinionNames[i]);

			for (Quote element : quotes) {
				TreeItem leftQuote = new TreeItem(leftOpinion, 0);
				TreeItem rightQuote = new TreeItem(rightOpinion, 0);
				leftQuote.setText(element.getPrefix(40));
				leftQuote.setData(element.getID());
				rightQuote.setText(element.getPrefix(40));
				rightQuote.setData(element.getID());
			}
		}
		leftObjects.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent arg0) {
				dialogChanged();

			}

		});

		rightObjects.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent arg0) {
				dialogChanged();

			}

		});
		dialogChanged();
		setControl(container);
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {

		String relType = getRelationType();
		TreeItem[] leftSelected = leftObjects.getSelection();
		TreeItem[] rightSelected = rightObjects.getSelection();

		if (relType.length() == 0) {
			updateStatus(Messages.getString("NewRelationWizardPage.5")); //$NON-NLS-1$
			return;
		}
		updateStatus(null);
		if (leftSelected.length + rightSelected.length != 2) {
			updateStatus(Messages.getString("NewRelationWizardPage.6")); //$NON-NLS-1$
			return;
		}
	}

	public String getComment() {
		return comment.getText();
	}

	public Discussion getDiscussion() {
		return discussion;
	}
//	public String getDiscName() {
//		return discName;
//	}

	public String getRelationType() {
		return relType.getText();
	}

	public Integer[] getSelectedQuotes() {

		TreeItem[] leftSelected = leftObjects.getSelection();
		TreeItem[] rightSelected = rightObjects.getSelection();
		Integer[] selectedText = new Integer[2];
		selectedText[0] = (Integer) leftSelected[0].getData();
		selectedText[1] = (Integer) rightSelected[0].getData();
		return selectedText;
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1) {
				return;
			}
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IResource resource = (IResource) obj;
				project = resource.getProject();
				//discName = resource.getName().split(".dis")[0]; //$NON-NLS-1$
				try {
					discussion = ToK.getProjectToK(project).getDiscussion(Discussion.getNameFromResource(resource));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
}