package lost.tok.newRelationWizard;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
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
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.swt.widgets.FileDialog;
import java.awt.*;
import java.io.File;

import javax.naming.NameParser;
import javax.swing.JFileChooser;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.Messages;
import lost.tok.Quote;
import lost.tok.ToK;
import lost.tok.sourceDocument.SourceDocument;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class NewRelationWizardPage extends WizardPage {

	private ISelection selection;

	private Combo relType;

	private Text comment;

	private Tree leftObjects;

	private Tree rightObjects;

	private String discName;
	
	private String projectName;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewRelationWizardPage(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle("Create a relation between opinions/quotes"); //$NON-NLS-1$
		setDescription("Creates a relation between opinions/quotes"); //$NON-NLS-1$
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		initialize();
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;

		Label label = new Label(container, SWT.NULL);
		label.setText("Relation type:"); //$NON-NLS-1$

		relType = new Combo(container, SWT.NULL);
		for (int i = 0; i < Discussion.relTypes.length; i++) {
			relType.add(Discussion.relTypes[i]);
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
		label.setText("Comment"); //$NON-NLS-1$
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
		gd = new GridData(GridData.FILL_BOTH);
		leftObjects = new Tree(container, SWT.BORDER);
		leftObjects.setLayoutData(gd);
		leftObjects.setSize(100, 200);

		gd = new GridData(GridData.FILL_BOTH);
		rightObjects = new Tree(container, SWT.BORDER);
		rightObjects.setLayoutData(gd);
		rightObjects.setSize(100, 200);

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject();

		// FOR DEBUGGING ONLY!!!!!!!!
		ToK tok = new ToK(projectName, "Arie", "Babel_he.src");
		// @TODO
		//ToK tok = ToK.getProjectToK(project);

		Discussion disc = null;
		try {
			disc = tok.getDiscussion(discName);
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] opinions = disc.getOpinions();

		for (int i = 0; i < opinions.length; i++) {
			TreeItem leftOpinion = new TreeItem(leftObjects, 0);
			TreeItem rightOpinion = new TreeItem(rightObjects, 0);
			leftOpinion.setText(opinions[i]);
			rightOpinion.setText(opinions[i]);

			Quote[] quotes = null;
			try {
				quotes = disc.getQuotes(opinions[i]);
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (int j = 0; j < quotes.length; j++) {
				TreeItem leftQuote = new TreeItem(leftOpinion, 0);
				TreeItem rightQuote = new TreeItem(rightOpinion, 0);
				leftQuote.setText(quotes[j].getPrefix(40));
				leftQuote.setData(quotes[j].getID());
				rightQuote.setText(quotes[j].getPrefix(40));
				rightQuote.setData(quotes[j].getID());
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
				discName = resource.getName().split(".dis")[0];
				projectName = resource.getProject().getName();
			}
		}
	}

	
	
	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {

		String relType = getRelationType();
		TreeItem[] leftSelected = leftObjects.getSelection();
		TreeItem[] rightSelected = rightObjects.getSelection();

		if (leftSelected.length + rightSelected.length != 2) {
			updateStatus("Please choose 2 objects to link between"); //$NON-NLS-1$
			return;
		}

		if (relType.length() == 0) {
			updateStatus("Please choose a type of relationship"); //$NON-NLS-1$
		}
		updateStatus(null);
	}
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getRelationType() {
		return relType.getText();
	}

	public String getComment() {
		return comment.getText();
	}

	public Integer[] getSelectedQuotes() {

		TreeItem[] leftSelected = leftObjects.getSelection();
		TreeItem[] rightSelected = rightObjects.getSelection();
		Integer[] selectedText = new Integer[2];
		selectedText[0] = (Integer)leftSelected[0].getData();
		selectedText[1] = (Integer)rightSelected[0].getData();
		return selectedText;
	}

	public String getDiscName() {
		return discName;
	}
}