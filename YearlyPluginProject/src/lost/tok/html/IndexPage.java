package lost.tok.html;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
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
	private Discussion[] discussions;
	/** The sources (and roots) of the ToK, during it's creation */
	private Source[] sources;
	
	public IndexPage(ToK tok)
	{
		super(tok, tok.getProject().getName() + " Tree of Knowledge", "html/index.html", "html/" + INDEX_CSS);
		
		discussions = tok.getDiscussions().toArray( new Discussion[0] );
		sources = tok.getSources();
	}

	@Override
	protected String getBody() {
		// Document doc = DocumentHelper.createDocument();


		Element body = DocumentHelper.createElement("body");
		
		body.addElement("h1").addText( tok.getProject().getName() );
		
		Element author = body.addElement("p").addText("Created By: ");
		author.addElement("em").addText(tok.getProjectCreator());
		
		
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding("UTF-8"); //$NON-NLS-1$
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String retVal;
		try {
			XMLWriter writer = new XMLWriter(baos, outformat);
			writer.write(body);
			writer.flush();
			retVal = baos.toString("UTF-8");
		} catch (IOException e) {
			retVal = "Error " + e.getMessage();
			e.printStackTrace();
		}

		// TODO(Shay): Add the sources and discussions to the file (a href)
		// TODO(Shay): Make sure the xml looks nice :P
		return retVal;
	}
	
	@Override
	public void generatePage() throws CoreException
	{
		super.generatePage();
		
		// 1. Find all the sub pages
		HashMap<String, SourcePage> srcPathToPage = new HashMap<String, SourcePage>();
		for (Source source : sources)
		{
			SourcePage sPage = new SourcePage(source);
			String srcPath = source.getFile().getProjectRelativePath().toString();
			srcPathToPage.put( srcPath, sPage );
		}
		
		HashMap<String, DiscussionPage> discNameToPage = new HashMap<String, DiscussionPage>();
		for (Discussion disc : discussions)
		{
			DiscussionPage dPage = new DiscussionPage(disc);
			String discName = disc.getDiscName();
			discNameToPage.put(discName, dPage);
		}
		
		// TODO(Shay): 2. Parse the links file and connect it with the sources
		
		// 3. Generate all the sub pages
		for (SourcePage sPage : srcPathToPage.values())
			sPage.generatePage();
		
		for (DiscussionPage dPage : discNameToPage.values())
			dPage.generatePage();		
	}

}
