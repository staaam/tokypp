package lost.tok.wizards;

import lost.tok.Discussion;
import lost.tok.ToK;

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

public class DiscCombo extends Composite implements SelectionListener {
	ToK tok;
	public Combo discCombo;
	Button newDisButton;

	public DiscCombo(Composite parent, int style, ToK tok) {
		super(parent, style);
		this.tok = tok;
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.FILL_HORIZONTAL);
		setLayoutData(gridData);
		
		discCombo = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		discCombo.setLayoutData(gd);
		
		updateDiscussions();
		
		newDisButton = new Button(this, SWT.NONE);
		newDisButton.setText("New...");
		
		newDisButton.addSelectionListener(this);
	}

	private void updateDiscussions() {
		for (Discussion d : tok.getDiscussions()) {
			discCombo.add(d.getDiscName());
		}
	}
	
	public void widgetSelected(SelectionEvent e) {
		NewDiscussion w = new NewDiscussion(tok.getProject());

		WizardDialog wd = new WizardDialog(new Shell(), w);
		wd.setBlockOnOpen(true);

		wd.open();
		
		if (wd.getReturnCode() != WizardDialog.OK)
			return;

		updateDiscussions();
		discCombo.setText(w.getDiscussionName());
		discCombo.notifyListeners(SWT.Selection, new Event());
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}

}