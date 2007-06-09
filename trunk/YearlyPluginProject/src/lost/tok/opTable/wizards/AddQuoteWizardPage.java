package lost.tok.opTable.wizards;

import lost.tok.Discussion;
import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.wizards.DiscCombo;
import lost.tok.wizards.OpinionCombo;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddQuoteWizardPage extends WizardPage {
	private ToK tok;

	private String quoteText;

	protected AddQuoteWizardPage(ToK tok, String text) {
		super(Messages.getString("AddQuoteWizardPage.AddQuote")); //$NON-NLS-1$

		setTitle(Messages.getString("AddQuoteWizardPage.AddQuote")); //$NON-NLS-1$

		this.tok = tok;
		quoteText = text;
	}

	public void createAddQuoteWizard(String comment) {
		discussionCombo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				oc.setDiscussion(getDiscussion());
				oc.opinCombo.select(0);
				updateStatus(null);
			}
		});

		quoteArea.setText(comment);

		updateStatus(Messages.getString("AddQuoteWizardPage.SelectDisc")); //$NON-NLS-1$
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getComment() {
		return commentArea.getText();
	}

	public Discussion getDiscussion() {
		try {
			return tok.getDiscussion(discussionCombo.getText());
		} catch (CoreException e) {
		}
		return null;
	}

	public String getOpinion() {
		return oc.opinCombo.getText();
	}

	private Text quoteArea = null;

	private Text commentArea = null;

	private Combo discussionCombo = null;

	private OpinionCombo oc = null;

	public void createControl(Composite parent) {
		GridData gridData = new GridData(GridData.FILL_BOTH);
		GridLayout gridLayout = new GridLayout();

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(gridLayout);
		createCombos(composite);

		new Label(composite, SWT.NONE).setText(Messages
				.getString("AddQuoteWizard.QuoteText")); //$NON-NLS-1$
		quoteArea = new Text(composite, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER | SWT.READ_ONLY);
		quoteArea.setLayoutData(gridData);

		new Label(composite, SWT.NONE).setText(Messages
				.getString("AddQuoteWizard.QuoteComment")); //$NON-NLS-1$
		commentArea = new Text(composite, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER);
		commentArea.setLayoutData(gridData);

		createAddQuoteWizard(quoteText);
		setControl(parent);
	}

	private void createCombos(Composite parent) {
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);

		GridLayout gridLayout = new GridLayout(2, false);
		Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(gridLayout);
		composite.setLayoutData(gridData);

		new Label(composite, SWT.NONE).setText(Messages
				.getString("AddQuoteWizard.Discussion")); //$NON-NLS-1$
		DiscCombo dc = new DiscCombo(composite, SWT.READ_ONLY, tok);
		dc.setLayoutData(gridData);
		discussionCombo = dc.getDiscCombo();

		new Label(composite, SWT.NONE).setText(Messages
				.getString("AddQuoteWizard.Opinion")); //$NON-NLS-1$
		oc = new OpinionCombo(composite, SWT.READ_ONLY, tok);
		oc.setLayoutData(gridData);
	}
}
