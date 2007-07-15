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
import lost.tok.ToK;

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
public class DiscCombo extends Composite implements SelectionListener {
	ToK tok;

	private Combo discCombo;

	Button newDisButton;

	public DiscCombo(Composite parent, int style, ToK tok) {
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

		discCombo = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		discCombo.setLayoutData(gd);

		newDisButton = new Button(this, SWT.NONE);
		newDisButton.setText(Messages.getString("DiscCombo.new")); //$NON-NLS-1$

		newDisButton.addSelectionListener(this);
	}

	/**
	 * Updates the list of discussions presented in the combo box.
	 * Sets the current discussion to latest used
	 * 
	 * This method should be called after all listeners have been added 
	 *
	 */
	public void init() {
		updateDiscussions();

		String latestDiscussionName = tok.getPersistentProperty(Discussion.LATEST_QNAME);
		if (latestDiscussionName != null) { 
			if (discCombo.indexOf(latestDiscussionName) == -1) {
				tok.setLatestDiscussionOpinion(null, null);
			}
			else {
				setTextNotify(latestDiscussionName);
			}
		}
	}

	/**
	 * Sets the contents of the receiver's text field to the
	 * given string and notifies all of the receiver's listeners
	 * with the <code>SWT.Selection</code> event
	 * 
	 * @param discussionName new text field content
	 */
	public void setTextNotify(String discussionName) {
		discCombo.setText(discussionName);
		discCombo.notifyListeners(SWT.Selection, new Event());
	}
	
	private void updateDiscussions() {
		discCombo.removeAll();
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
		setTextNotify(w.getDiscussionName());
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
		return discCombo.getText();
	}

	/**
	 * Adds the listener to the collection of listeners who will
	 * be notified when the receiver's selection changes, by sending
	 * it one of the messages defined in the <code>SelectionListener</code>
	 * interface.
	 * <p>
	 * <code>widgetSelected</code> is called when the combo's list selection changes.
	 * <code>widgetDefaultSelected</code> is typically called when ENTER is pressed the combo's text area.
	 * </p>
	 *
	 * @param listener the listener which should be notified
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 *
	 * @see SelectionListener
	 * @see #removeSelectionListener
	 * @see SelectionEvent
	 */
	public void addSelectionListener(SelectionListener listener) {
		discCombo.addSelectionListener(listener);
	}

}
