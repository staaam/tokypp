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

package lost.tok.opTable.wizards;

import lost.tok.Discussion;
import lost.tok.Messages;
import lost.tok.Opinion;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddQuoteWizardPage extends WizardPage {
	private ToK tok;

	private String quoteText;

	protected AddQuoteWizardPage(ToK tok, String text) {
		super(Messages.getString("AddQuoteWizardPage.AddQuote")); //$NON-NLS-1$

		setTitle(Messages.getString("AddQuoteWizardPage.AddQuote")); //$NON-NLS-1$
		updateStatus(Messages.getString("AddQuoteWizardPage.SelectDisc")); //$NON-NLS-1$

		this.tok = tok;
		quoteText = text;
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getComment() {
		return commentArea.getText();
	}

	/**
	 * Returns the selected discussion name
	 * @return the selected discussion name
	 */
	private String getDiscussionName() {
		return discCombo.getText();
	}
	
	/**
	 * Returns the selected discussion
	 * @return the selected discussion
	 */
	public Discussion getDiscussion() {
		try {
			return tok.getDiscussion(getDiscussionName());
		} catch (CoreException e) {
		}
		return null;
	}

	/**
	 * Returns the selected opinion name
	 * @return the selected opinion
	 */
	public String getOpinion() {
		return oc.getText();
	}

	private Text quoteArea = null;

	private Text commentArea = null;

	private DiscCombo discCombo = null;

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
		quoteArea.setText(quoteText);

		new Label(composite, SWT.NONE).setText(Messages
				.getString("AddQuoteWizard.QuoteComment")); //$NON-NLS-1$
		commentArea = new Text(composite, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER);
		commentArea.setLayoutData(gridData);
		
		init();
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
		discCombo = new DiscCombo(composite, SWT.READ_ONLY, tok);
		discCombo.setLayoutData(gridData);

		new Label(composite, SWT.NONE).setText(Messages
				.getString("AddQuoteWizard.Opinion")); //$NON-NLS-1$
		oc = new OpinionCombo(composite, SWT.READ_ONLY, tok);
		oc.setLayoutData(gridData);
	}
	
	private void init() {
		discCombo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			
			public void widgetSelected(SelectionEvent e) {
				oc.setDiscussion(getDiscussion());
				if (tok.getPersistentProperty(Discussion.LATEST_QNAME)
						.equals(getDiscussionName())) {
					oc.setText(tok.getPersistentProperty(Opinion.LATEST_QNAME));
				} else {
					oc.setText(null);
				}
				updateStatus(null);
			}
		});
		
		discCombo.init();
	}
}
