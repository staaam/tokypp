package lost.tok.opTable;

import java.util.List;

import lost.tok.Discussion;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddQuoteWizard {
	String discussion;

	String opinion;

	String comment;

	public void createAddQuoteWizard(List<String> discStrings,
			List<String> opinStrings, String text) {
		createSShell();

		combo.setItems((String[]) discStrings.toArray(new String[discStrings
				.size()]));
		combo.select(0);

		combo1.setItems((String[]) opinStrings.toArray(new String[opinStrings
				.size()]));
		combo1.select(opinStrings.indexOf(Discussion.DEFAULT_OPINION));

		textArea.setText(text);
	}

	public boolean run() {
		// sShell.setVisible(true);
		sShell.open();
		Display display = sShell.getDisplay();
		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return okPressed;
	}

	public String getComment() {
		return comment;
	}

	public String getDiscussion() {
		return discussion;
	}

	public String getOpinion() {
		return opinion;
	}

	private Shell sShell = null;

	private Composite composite = null;

	private Text textArea = null;

	private Text textArea1 = null;

	private Composite composite1 = null;

	private Label label = null;

	private Combo combo = null;

	private Label label1 = null;

	private Combo combo1 = null;

	private Label label2 = null;

	private Label label3 = null;

	private Composite composite2 = null;

	private Button button = null;

	private Button button1 = null;

	protected boolean okPressed = false;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText(Messages.getString("AddQuoteWizard.AddQuote")); //$NON-NLS-1$
		sShell.setLayout(new FillLayout());
		sShell.setSize(new Point(400, 500));
		createComposite();
	}

	/**
	 * This method initializes composite
	 * 
	 */
	private void createComposite() {
		GridData gridData3 = new GridData(GridData.FILL_BOTH);
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.FILL;
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.makeColumnsEqualWidth = true;
		composite = new Composite(sShell, SWT.NONE);
		createComposite1();
		composite.setLayout(gridLayout);
		label2 = new Label(composite, SWT.NONE);
		label2.setText(Messages.getString("AddQuoteWizard.QuoteText")); //$NON-NLS-1$
		textArea = new Text(composite, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER);
		textArea.setLayoutData(gridData);
		label3 = new Label(composite, SWT.NONE);
		label3.setText(Messages.getString("AddQuoteWizard.QuoteComment")); //$NON-NLS-1$
		textArea1 = new Text(composite, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER);
		textArea1.setLayoutData(gridData3);
		createComposite2();
	}

	/**
	 * This method initializes composite1
	 * 
	 */
	private void createComposite1() {
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		composite1 = new Composite(composite, SWT.BORDER);
		composite1.setLayout(gridLayout1);
		composite1.setLayoutData(gridData5);
		label = new Label(composite1, SWT.NONE);
		label.setText(Messages.getString("AddQuoteWizard.Discussion")); //$NON-NLS-1$
		createCombo();
		label1 = new Label(composite1, SWT.NONE);
		label1.setText(Messages.getString("AddQuoteWizard.Opinion")); //$NON-NLS-1$
		createCombo1();
	}

	/**
	 * This method initializes combo
	 * 
	 */
	private void createCombo() {
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.verticalAlignment = GridData.CENTER;
		combo = new Combo(composite1, SWT.NONE);
		combo.setLayoutData(gridData4);
	}

	/**
	 * This method initializes combo1
	 * 
	 */
	private void createCombo1() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		combo1 = new Combo(composite1, SWT.NONE);
		combo1.setLayoutData(gridData1);
	}

	/**
	 * This method initializes composite2
	 * 
	 */
	private void createComposite2() {
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.END;
		gridData2.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		composite2 = new Composite(composite, SWT.NONE);
		composite2.setLayout(gridLayout2);
		composite2.setLayoutData(gridData2);
		button = new Button(composite2, SWT.NONE);
		button.setText(Messages.getString("AddQuoteWizard.Ok")); //$NON-NLS-1$
		button
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						okPressed = true;
						comment = textArea1.getText();
						discussion = combo.getText();
						opinion = combo1.getText();
						sShell.dispose();
					}
				});
		button1 = new Button(composite2, SWT.NONE);
		button1.setText(Messages.getString("AddQuoteWizard.Cancel")); //$NON-NLS-1$
		button1
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						okPressed = false;
						sShell.dispose();
					}
				});
	}
}
