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
