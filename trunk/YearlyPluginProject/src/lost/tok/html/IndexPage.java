package lost.tok.html;

import org.eclipse.core.runtime.CoreException;

import lost.tok.Discussion;
import lost.tok.Source;
import lost.tok.ToK;

/**
 * The main page of the ToK site
 * 
 * Includes links to all the Dicussions and Sources
 * Responsible for generating all the html files
 * Responsible for parsing the links.xml files
 * @author Team Lost
 */
public class IndexPage extends HTMLPage {
	
	static final public String INDEX_CSS = DEFAULT_CSS;
	
	/** The discussions of the ToK, during it's creation */
	@SuppressWarnings("unused")
	private Discussion[] discussions;
	/** The sources (and roots) of the ToK, during it's creation */
	@SuppressWarnings("unused")
	private Source[] sources;
	
	public IndexPage(ToK tok)
	{
		super(tok, tok.getProject().getName() + " Index Page", "html/index.html", "html/" + INDEX_CSS);
		
		discussions = tok.getDiscussions().toArray( new Discussion[0] );
		// Note(Shay): Disabled until I make a getSource method: sources = tok.getSources();
	}

	@Override
	protected String getBody() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void generatePage() throws CoreException
	{
		super.generatePage();
		
		// TODO(Shay): Iterate over the source and discussions and generate them
		// TODO(Shay): Parse the links file and connect it with the sources
		
	}

}
