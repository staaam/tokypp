package lost.tok.html;

import java.util.HashMap;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import lost.tok.Discussion;
import lost.tok.GeneralFunctions;
import lost.tok.Opinion;
import lost.tok.Quote;
import lost.tok.Source;
import lost.tok.ToK;

/**
 * An HTML exported version of a Discussion file
 * 
 * Displays the Quotes and Opinions, as well as links to the respective sources
 * @author Team Lost
 */
public class DiscussionPage extends HTMLPage {
	
	public static String DISCUSSION_CSS = DEFAULT_CSS;
	
	private Discussion disc;
	
	private HashMap<String, SourcePage> srcPathToPage;
	
	/** The length of quotes titles. The title will be longer than this, as it won't cut a word */
	static public int QUOTE_TITLE_LENGTH = 20;

	/**
	 * Creates a new discussion page
	 * @param disc the discussion this page is created for
	 * @param srcPathToPage a mapping from source paths to the respective source pages
	 */
	public DiscussionPage(Discussion disc, HashMap<String, SourcePage> srcPathToPage)
	{
		super(disc.getMyToK(),
				disc.getDiscFileName(),
				getExportPath(disc),
				getCSSPath(disc));
		
		this.disc = disc;
		this.srcPathToPage = srcPathToPage;
				
	}
	
	/**
	 * Returns the relative path of the .html file in the project for the discussion
	 * @param disc the discussion file for which we want to find the html path
	 * @return a path to the html file matching the discussion
	 */
	static protected String getExportPath(Discussion disc)
	{
		IProject proj = disc.getMyToK().getProject();
		
		IPath path = proj.getProjectRelativePath().append(ToK.HTML_FOLDER + "/");
		path = path.append(disc.getFile().getProjectRelativePath());
		
		path = path.removeFileExtension().addFileExtension("html").removeTrailingSeparator();
		
		return path.toString();
	}
	
	/**
	 * Returns the relative path of the css to this html file
	 * @param disc the discussion file for which we want to find the rel css path
	 * @return a path to the css file for this discussion
	 */
	static protected String getCSSPath(Discussion disc)
	{
		IProject proj = disc.getMyToK().getProject();
		IPath path = disc.getFile().getProjectRelativePath();
		
		return HTMLPage.getHTMLRelPath(proj, path) + DISCUSSION_CSS;		
	}
	
	@Override
	protected String getBody() 
	{
		Element body = DocumentHelper.createElement("body");
		
		body.addElement("h1").addText(disc.getDiscName());
		
		body.addText("Created By: ");
		body.addElement("em").addText(disc.getCreatorName());
		
		// TODO(Shay): Add the link and some info about it
		// TODO(Shay, low): Consider adding related root and source files
		
		for (Opinion o : disc.getOpinions())
			body.add( getOpinionElement(o) );
		
		
		// TODO(Shay) continue working on the getBody method
		return GeneralFunctions.elementToString(body);
	}
	
	/**
	 * Creates a div element describing the given opinion
	 * @param o the opinion to generate html div for
	 * @return a div element that contains all the information relating the opinion
	 */
	private Element getOpinionElement(Opinion o)
	{
		Element div = DocumentHelper.createElement("div");
		
		// 1. Create the title
		Element opTitle = div.addElement("h2");
		opTitle.addText( o.getName() );
		opTitle.addAttribute("id", "discItem" + o.getId());
		
		// 2. Add the quotes
		for ( Quote q : disc.getQuotes(o.getName()) )
		{
			// TODO(Shay, medium-low): Sort the quotes according to author importance
			div.add( getQuoteElement(q) );	
		}
		// TODO(Shay, low): Consider adding relation data
		
		return div;
	}
	
	/**
	 * Creates a div element describing the given quote
	 * @param q the quote to generate html div for
	 * @return a div element that contains all the information relating the quote
	 */
	private Element getQuoteElement(Quote q)
	{
		Element div = DocumentHelper.createElement("div");
		
		String qText = q.getText();
		int actualLen = qText.indexOf(' ', QUOTE_TITLE_LENGTH);
		actualLen = (actualLen != -1) ? actualLen : qText.length();
		String qTitle = qText.substring(0, actualLen);
		
		Element quoteTitle = div.addElement("h3");
		quoteTitle.addText( qTitle );
		quoteTitle.addAttribute("id", "discItem" + q.getID());
		
		div.addElement("p").addText(qText);
		
		if (!q.getComment().equals(""))
		{
			Element pComment = div.addElement("p");
			pComment.addText("Comment: ");
			pComment.addElement("em").addText(q.getComment());
		}
		
		Source src = q.getSource();
		String srcPath = src.getFile().getProjectRelativePath().toString();
		SourcePage sPage = srcPathToPage.get(srcPath);
		
		Element srcLinkParagraph = div.addElement("p");
		srcLinkParagraph.addText("From: ");
		
		Element srcLink = srcLinkParagraph.addElement("a");
		srcLink.addAttribute("href", getPathTo(sPage) );
		srcLink.addText( src.getTitle() );
		
		srcLinkParagraph.addText(" by: ");
		srcLinkParagraph.addElement("em").addText(src.getAuthor());
		
		// TODO(Shay, low): Consider adding relation data
		
		return div;
	}

}
