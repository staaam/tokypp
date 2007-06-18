package lost.tok.html;

import java.util.HashMap;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.Source;
import lost.tok.SubLink;
import lost.tok.ToK;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.core.runtime.CoreException;

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
	private Discussion[] discussions;
	/** The sources (and roots) of the ToK, during it's creation */
	private Source[] sources;
	
	/** A map from the path of a source file to its HTMLPage */
	private HashMap<String, SourcePage> srcPathToPage;
	/** A map from the name of a discussion file to its HTMLPage */
	private HashMap<String, DiscussionPage> discNameToPage;
	
	public IndexPage(ToK tok)
	{
		super(tok,
				tok.getProject().getName() + ", Tree of Knowledge",
				ToK.HTML_FOLDER + "/index.html",
				ToK.HTML_FOLDER + "/" + INDEX_CSS);
		
		discussions = tok.getDiscussions().toArray( new Discussion[0] );
		sources = tok.getSources();
		
		// 1. Find all the sub pages
		srcPathToPage = new HashMap<String, SourcePage>();
		for (Source source : sources)
		{
			SourcePage sPage = new SourcePage(source);
			String srcPath = source.getFile().getProjectRelativePath().toString();
			srcPathToPage.put( srcPath, sPage );
		}
		
		discNameToPage = new HashMap<String, DiscussionPage>();
		for (Discussion disc : discussions)
		{
			DiscussionPage dPage = new DiscussionPage(disc, srcPathToPage);
			String discName = disc.getDiscName();
			discNameToPage.put(discName, dPage);
		}
		
		// 2. Link the Roots to the discussions
		for (Discussion disc : discussions)
		{
			if (disc.getLink() == null)
				continue;
			
			DiscussionPage dPage = discNameToPage.get( disc.getDiscName() );
			
			for (SubLink sublink : disc.getLink().getSubLinkList())
			{
				Source src = sublink.getLinkedSource();
				String srcPath = src.getFile().getProjectRelativePath().toString();
				SourcePage sPage = srcPathToPage.get ( srcPath );
				
				for (Excerption e : sublink.getExcerption())
					sPage.addLink( e, disc.getLink(), dPage);
			}

		}
	}

	@Override
	protected String getBody() {
		Element body = DocumentHelper.createElement("body");
		
		body.addElement("h1").addText( tok.getProject().getName() );
		
		Element author = body.addElement("p").addText("Created By: ");
		author.addElement("em").addText(tok.getProjectCreator());
		
		body.add( getSourcesElement(true) );
		body.add( getSourcesElement(false) );
		body.add( getDiscussionElement() );
		
		return GeneralFunctions.elementToString(body);
	}
	
	/**
	 * Returns a list of either sources or roots as an html elements
	 * If the list is empty, returns an empty div element instead
	 * @param isRoot true if the list should be of roots
	 * @return an html div element containing the entire list
	 */
	public Element getSourcesElement(boolean genRoot)
	{
		Element main = DocumentHelper.createElement("div");
		String listTitle = genRoot ? "Roots" : "Sources";
		
		main.addElement("h2").addText(listTitle);
		
		int numItems = 0; // the number of items in the list
		
		Element rootsList = main.addElement("ul");
		for (Source src : sources)
		{
			if (src.isRoot() != genRoot)
				continue;
			
			Element item = rootsList.addElement("li");
			
			String srcPath = src.getFile().getProjectRelativePath().toString();
			
			Element a = item.addElement("a");
			SourcePage sPage = srcPathToPage.get( srcPath );
			a.addAttribute("href", getPathTo(sPage) );
			// a.addAttribute("tooltip", value)
			a.addText( src.getTitle() );
			
			item.addText(", by ");
			item.addElement("em").addText( src.getAuthor() );
			item.addText(" (" + srcPath + ")" );
			
			numItems ++;
		}
		
		if (numItems > 0)
			return main;
		// else, we return an empty div
		return DocumentHelper.createElement("div");
	}
	
	/**
	 * Returns a list of links to discussions, or empty div if no discussions exist
	 * @return an html div element containing the entire list
	 */
	public Element getDiscussionElement()
	{
		Element main = DocumentHelper.createElement("div");
		
		if (discussions.length == 0)
			return main; // empty div
		
		main.addElement("h2").addText("Discussions");
			
		Element rootsList = main.addElement("ul");
		for (Discussion disc : discussions)
		{	
			Element item = rootsList.addElement("li");
			
			Element a = item.addElement("a");
			DiscussionPage dPage = discNameToPage.get( disc.getDiscName() );
			a.addAttribute("href", getPathTo(dPage) );
			// TODO(Shay, low): Add a normal tooltip in the index page
			//   a.addAttribute("tooltip", disc.getLinkType() );
			a.addText( disc.getDiscName() );
			
			item.addText(", by ");
			item.addElement("em").addText( disc.getCreatorName() );
			item.addText(" (" + disc.getDiscFileName() + ")" );
		}
		
		return main;
	}
	
	
	@Override
	public void generatePage() throws CoreException
	{
		super.generatePage();
		
		// Generate all the sub pages
		for (SourcePage sPage : srcPathToPage.values())
			sPage.generatePage();
		
		for (DiscussionPage dPage : discNameToPage.values())
			dPage.generatePage();		
	}

}
