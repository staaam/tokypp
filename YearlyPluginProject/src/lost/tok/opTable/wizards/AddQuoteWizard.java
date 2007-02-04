package lost.tok.opTable.wizards;

import java.util.List;

import lost.tok.Discussion;
import lost.tok.Quote;
import lost.tok.opTable.Messages;

import org.dom4j.Comment;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.Wizard;

public class AddQuoteWizard extends Wizard {
	private AddQuoteWizardPage page;
	boolean finished = false;
	List<Discussion> disussions;
	Quote quote;

	public AddQuoteWizard(List<Discussion> disussions, Quote quote) {
		super();
		
		this.disussions = disussions;
		this.quote = quote;
		
		setWindowTitle("Add quote");		
		
		page = new AddQuoteWizardPage(disussions, quote.getText());
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





