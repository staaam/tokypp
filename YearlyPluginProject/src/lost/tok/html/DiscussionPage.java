package lost.tok.html;

import java.util.HashMap;
import java.util.LinkedList;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.Link;
import lost.tok.Messages;
import lost.tok.Opinion;
import lost.tok.Quote;
import lost.tok.Source;
import lost.tok.SubLink;
import lost.tok.ToK;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

/**
 * An HTML exported version of a Discussion file
 * 
 * Displays the Quotes and Opinions, as well as links to the respective sources
 * @author Team Lost
 */
public class DiscussionPage extends HTMLPage {

	private Discussion disc;
	
	private HashMap<String, SourcePage> srcPathToPage;
	
	/** The length of quotes titles. The title will be longer than this, as it won't cut a word */
	static public int QUOTE_TITLE_LENGTH = 50;

	/**
	 * Creates a new discussion page
	 * @param disc the discussion this page is created for
	 * @param srcPathToPage a mapping from source paths to the respective source pages
	 */
	public DiscussionPage(Discussion disc, HashMap<String, SourcePage> srcPathToPage, CSSManager cssMan)
	{
		super(disc.getMyToK(),
				disc.getDiscFileName(),
				getExportPath(disc),
				cssMan.add(disc));
		
		this.disc = disc;
		this.srcPathToPage = srcPathToPage;
				
	}
	
	/** Returns the underlying discussion */
	public Discussion getDiscussion()
	{
		return disc;
	}
	
	/**
	 * Returns the relative path of the .html file in the project for the discussion
	 * @param disc the discussion file for which we want to find the html path
	 * @return a path to the html file matching the discussion
	 */
	static protected String getExportPath(Discussion disc)
	{
		IProject proj = disc.getMyToK().getProject();
		
		IPath path = proj.getProjectRelativePath().append(ToK.HTML_FOLDER + "/"); //$NON-NLS-1$
		path = path.append(disc.getFile().getProjectRelativePath());
		
		path = path.removeFileExtension().addFileExtension("html").removeTrailingSeparator(); //$NON-NLS-1$
		
		return path.toString();
	}
	
	@Override
	protected String getBody() 
	{
		Element body = DocumentHelper.createElement("div"); //$NON-NLS-1$
		body.addAttribute("id", "discussion"); //$NON-NLS-1$ //$NON-NLS-2$
		body.addAttribute("class", "main_content"); //$NON-NLS-1$ //$NON-NLS-2$
		
		body.addElement("h1").addText(disc.getDiscName()); //$NON-NLS-1$
		
		body.addText(Messages.getString("DiscussionPage.CreatedBy")); //$NON-NLS-1$
		body.addElement("em").addText(disc.getCreatorName()); //$NON-NLS-1$
		
		if (disc.getLink() != null)
			body.add(getLinkInfoElement());
		
		Element opinionList = body.addElement("ol"); //$NON-NLS-1$
		for (Opinion o : disc.getOpinions())
		{
			Element listItem = opinionList.addElement("li"); //$NON-NLS-1$
			listItem.add( getOpinionElement(o) );
		}
		
		// TODO(Shay, low): Consider adding related root and source files
		
		return GeneralFunctions.elementToString(body);
	}
	
	/**
	 * Returns a div element containing all the information related to the discussion's link
	 */
	private Element getLinkInfoElement()
	{
		Element div = DocumentHelper.createElement("div"); //$NON-NLS-1$
		
		Link l = disc.getLink();
		
		div.addAttribute("class", "linkInfo"); //$NON-NLS-1$ //$NON-NLS-2$
		div.addElement("h2").addText( l.getSubject() ); //$NON-NLS-1$
		Element typeElement = div.addElement("p"); //$NON-NLS-1$
		
		typeElement.addText(Messages.getString("DiscussionPage.linkType")); //$NON-NLS-1$
		typeElement.addElement("em").addText( l.getDisplayLinkType() ); //$NON-NLS-1$
		
		Element sublinksList = div.addElement("ul"); //$NON-NLS-1$
		for (SubLink sl : l.getSubLinkList())
		{
			LinkedList<Excerption> excerptions = new LinkedList<Excerption>();
			for (Excerption e : sl.getExcerption())
				excerptions.add(e);
			
			String eText = Excerption.concat(excerptions);
			Source src = sl.getLinkedSource();
			
			Element listItem = sublinksList.addElement("li"); //$NON-NLS-1$
			
			Element quoteElm = listItem.addElement("blockquote"); //$NON-NLS-1$
			quoteElm.addAttribute("class", "sublink"); //$NON-NLS-1$ //$NON-NLS-2$
			quoteElm.addAttribute("cite", getPathToSrc(src)); //$NON-NLS-1$
			quoteElm.addElement("p").addText(eText); //$NON-NLS-1$
			
			Element srcLinkParagraph = listItem.addElement("p"); //$NON-NLS-1$
			srcLinkParagraph.addText(Messages.getString("DiscussionPage.Source")).add( getLinkSrcElm(src) ); //$NON-NLS-1$
		}
		
		return div;		
	}
	
	/**
	 * Creates a div element describing the given opinion
	 * @param o the opinion to generate html div for
	 * @return a div element that contains all the information relating the opinion
	 */
	private Element getOpinionElement(Opinion o)
	{
		Element div = DocumentHelper.createElement("div"); //$NON-NLS-1$
		
		// 1. Create the title
		Element opTitle = div.addElement("h2"); //$NON-NLS-1$
		opTitle.addText( o.getName() );
		opTitle.addAttribute("id", "discItem" + o.getId()); //$NON-NLS-1$ //$NON-NLS-2$
		
		Quote[] quotes = disc.getSortedQuotes(o.getName());
		
		// add quotes only if there are any
		if (quotes.length > 0)
		{
			Element quotesList = div.addElement("ul"); //$NON-NLS-1$
			
			// 2. Add the quotes
			for ( Quote q : quotes )
			{
				// TODO(Shay, medium-low): Sort the quotes according to author importance
				
				Element listItem = quotesList.addElement("li"); //$NON-NLS-1$
				listItem.add( getQuoteElement(q) );	
			}
		}
		// TODO(Shay, low): Consider adding relation data
		
		return div;
	}
	
	/**
	 * Returns a path to the src specified
	 * @param src a source existing in the system
	 * @return a String which can be used in &lta href&gt tags and links to the given source page
	 */
	private String getPathToSrc(Source src)
	{
		String srcPath = src.getFile().getProjectRelativePath().toString();
		SourcePage sPage = srcPathToPage.get(srcPath);
		return getPathTo(sPage);
	}
	
	/**
	 * Creates a div element describing the given quote
	 * @param q the quote to generate html div for
	 * @return a div element that contains all the information relating the quote
	 */
	private Element getQuoteElement(Quote q)
	{
		Element div = DocumentHelper.createElement("div"); //$NON-NLS-1$
		
		// 1. Get the quote's text and find a good title for it
		String qText = q.getText();
		int actualLen = qText.indexOf(' ', QUOTE_TITLE_LENGTH);
		actualLen = (actualLen != -1) ? actualLen : qText.length();
		String qTitle = qText.substring(0, actualLen);
		
		// 2. Get the src we should link to
		Source src = q.getSource();
		
		// 3. Add the title
		Element quoteTitle = div.addElement("h3"); //$NON-NLS-1$
		quoteTitle.addText( qTitle );
		quoteTitle.addAttribute("id", "discItem" + q.getID()); //$NON-NLS-1$ //$NON-NLS-2$
		
		// 4. Add the quote itself
		Element quoteElm = div.addElement("blockquote"); //$NON-NLS-1$
		quoteElm.addAttribute("class", "quote"); //$NON-NLS-1$ //$NON-NLS-2$
		quoteElm.addAttribute("cite", getPathToSrc(src)); //$NON-NLS-1$
		quoteElm.addElement("p").addText(qText); //$NON-NLS-1$
		
		// 5. Add a comment, if there is one
		if (!q.getComment().equals("")) //$NON-NLS-1$
		{
			Element pComment = div.addElement("p"); //$NON-NLS-1$
			pComment.addText(Messages.getString("DiscussionPage.Comment")); //$NON-NLS-1$
			pComment.addElement("em").addText(q.getComment()); //$NON-NLS-1$
			
			quoteElm.addAttribute("title", q.getComment()); //$NON-NLS-1$
		}
		
		// 6. Add a link to the related source
		Element srcLinkParagraph = div.addElement("p"); //$NON-NLS-1$
		srcLinkParagraph.addText(Messages.getString("DiscussionPage.From")).add( getLinkSrcElm(src) ); //$NON-NLS-1$
				
		// TODO(Shay, low): 7. Consider adding relation data
		
		return div;
	}
	
	/**
	 * Returns a paragraph Element linking to the selected source
	 * @param src the source to link to
	 * @return a p element which can be added to the page
	 */
	private Element getLinkSrcElm(Source src)
	{
		Element cite = DocumentHelper.createElement("cite"); //$NON-NLS-1$
		
		Element srcLink = cite.addElement("a"); //$NON-NLS-1$
		srcLink.addAttribute("href", getPathToSrc(src) ); //$NON-NLS-1$
		srcLink.addText( src.getTitle() );
		
		cite.addText(Messages.getString("DiscussionPage.by")); //$NON-NLS-1$
		cite.addElement("em").addText(src.getAuthor()); //$NON-NLS-1$
		
		return cite;
	}

}
