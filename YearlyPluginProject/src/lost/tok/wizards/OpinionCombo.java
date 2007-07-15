/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

package lost.tok.wizards;

import lost.tok.Discussion;
import lost.tok.Messages;
import lost.tok.Opinion;
import lost.tok.ToK;
import lost.tok.disEditor.AddOpinionWizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
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

	private Combo opinCombo;

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

	/**
	 * Returns a string containing a copy of the contents of the
	 * receiver's text field, or an empty string if there are no
	 * contents.
	 *
	 * @return the receiver's text
	 *
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 */
	public String getText() {
		return opinCombo.getText();
	}

	/**
	 * Sets the contents of the receiver's text field to the
	 * given string.
	 * <p>
	 * Note: The text field in a <code>Combo</code> is typically
	 * only capable of displaying a single line of text. Thus,
	 * setting the text to a string containing line breaks or
	 * other special characters will probably cause it to 
	 * display incorrectly.
	 * </p>
	 *
	 * @param opinion the new opinion name. If the opinion is <code>null</code>
	 * or not exists - sets the <code>default opinion</code>
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the string is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 */
	public void setText(String opinion) {
		opinCombo.setText(
				(opinion == null || opinCombo.indexOf(opinion) == -1) ? Discussion.DEFAULT_OPINION_DISPLAY : opinion);
	}

}
