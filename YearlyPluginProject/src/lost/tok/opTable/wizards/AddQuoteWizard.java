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

import lost.tok.Messages;
import lost.tok.Quote;
import lost.tok.ToK;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.Wizard;

public class AddQuoteWizard extends Wizard {
	private AddQuoteWizardPage page;

	boolean finished = false;

	Quote quote;

	public AddQuoteWizard(ToK tok, Quote quote) {
		super();

		this.quote = quote;

		setWindowTitle(Messages.getString("AddQuoteWizard.AddQuote0")); //$NON-NLS-1$

		page = new AddQuoteWizardPage(tok, quote.getText());
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		finished = true;

		quote.setComment(page.getComment());

		try {
			page.getDiscussion().addQuote(quote, page.getOpinion());
		} catch (CoreException e) {
		}

		return true;
	}

	public boolean finished() {
		return finished;
	}
}
