package lost.tok.wizards;

import lost.tok.Discussion;
import lost.tok.Messages;
import lost.tok.Opinion;
import lost.tok.ToK;
import lost.tok.disEditor.AddOpinionWizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

/**
 * Widget(composite) that contains combobox with list of disussions and button
 * to create new discussion
 * 
 * @author Michael Gelfand
 * 
 */
public class OpinionCombo extends Composite implements SelectionListener {
	ToK tok;

	public Combo opinCombo;

	Button newOpinButton;

	private Discussion discussion = null;

	public OpinionCombo(Composite parent, int style, ToK tok) {
		super(parent, style);
		this.tok = tok;

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.FILL_HORIZONTAL);
		setLayoutData(gridData);

		opinCombo = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		opinCombo.setLayoutData(gd);

		updateOpinions();

		newOpinButton = new Button(this, SWT.NONE);
		newOpinButton.setText(Messages.getString("DiscCombo.new")); //$NON-NLS-1$

		newOpinButton.addSelectionListener(this);
	}

	public void setDiscussion(Discussion d) {
		discussion = d;
		updateOpinions();
	}

	private void updateOpinions() {
		opinCombo.removeAll();
		if (discussion == null) {
			return;
		}
		for (Opinion o : discussion.getOpinions()) {
			opinCombo.add(o.getName());
		}
	}

	public void widgetSelected(SelectionEvent e) {
		AddOpinionWizard w = new AddOpinionWizard(discussion);

		WizardDialog wd = new WizardDialog(new Shell(), w);
		wd.setBlockOnOpen(true);

		wd.open();

		if (wd.getReturnCode() != WizardDialog.OK)
			return;

		updateOpinions();
		opinCombo.setText(w.getOpinionName());
		opinCombo.notifyListeners(SWT.Selection, new Event());
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}

}
