package lost.tok.html;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import lost.tok.Excerption;
import lost.tok.Link;
import lost.tok.Source;
import lost.tok.ToK;
import lost.tok.html.srcElem.Heading;
import lost.tok.html.srcElem.LinkedParagraph;
import lost.tok.html.srcElem.SrcElem;
import lost.tok.sourceDocument.Chapter;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

/**
 * An HTML exported version of a source file
 * 
 * Displays the source text, as well as links to linked Discussions
 * @author Team Lost
 */
public class SourcePage extends HTMLPage {
	
	/** The path of the CSS used by Source Pages */
	public static String SOURCE_CSS = DEFAULT_CSS;
	/** The Source displayed by this page */
	private SourceDocument srcDoc;
	/** The elements displayed on this page (either headings or paragraphs) */
	private List<SrcElem> elements;
	/** True iff the source is a root file */
	private boolean isRoot;
	/** A mapping from xPath in the source to paragraph elements */
	private HashMap<String, LinkedParagraph> xPathToParagraph;

	/**
	 * Create a source page for the given source
	 * Links to discussions can be added later
	 * @param src the source for which this page is built
	 */
	public SourcePage(Source src)
	{
		super(src.getTok(),
				src.getFile().getFullPath().lastSegment(),
				getExportPath(src),
				getCSSPath(src));
		
		srcDoc = new SourceDocument();
		srcDoc.set( src );
		
		isRoot = src.isRoot();
		
		buildElements();
	}
	
	/**
	 * Returns the path of the css file for the given source
	 * @param src the source for which a SourcePage is generated
	 * @return the path from the source page to the css file
	 */
	static protected String getCSSPath(Source src)
	{
		IProject proj = src.getTok().getProject();
		IPath path = src.getFile().getProjectRelativePath();
		
		return HTMLPage.getHTMLRelPath(proj, path) + SOURCE_CSS;
	}
	
	/**
	 * Splits the SourceDocument and creates the elements attribute from it
	 */
	private void buildElements()
	{
		elements = new LinkedList<SrcElem>();	
		xPathToParagraph = new HashMap<String, LinkedParagraph>();
		for (Chapter c : srcDoc.getAllChapters())
		{
			if (c.getDepth() == 0)
				continue; // the root chapter contains general information and is not part of the source
			
			if ( c instanceof ChapterText )
			{
				ChapterText cText = (ChapterText) c;
				LinkedParagraph paragraph = new LinkedParagraph(this, cText.getText());
				xPathToParagraph.put( c.getXPath(), paragraph );
				elements.add( paragraph );
			}
			else
			{
				// just a normal chapter
				Heading h = new Heading(c.getName(), c.getDepth(), c.getXPath());
				elements.add(h);
			}
		}
	}
	
	/**
	 * Creates a link from the source to the selected document page
	 * @param e the specific excerption to add
	 * @param l the link itself
	 * @param disc the target discussion
	 */
	public void addLink(Excerption e, Link l, DiscussionPage disc)
	{
		xPathToParagraph.get(e.getXPath()).addLink(e, l, disc);
	}
	
	/**
	 * Generate the source page and all the discussion conflict pages connected to it
	 */
	public void generatePage() throws CoreException
	{
		// generate this
		super.generatePage();
		
		// generate all the discConflictPages
		for (SrcElem e : elements)
		{
			if (! (e instanceof LinkedParagraph) )
				continue;
			
			LinkedParagraph p = (LinkedParagraph)e;
			
			for ( HTMLPage page : p.getUngeneratedPages() )
				page.generatePage();
		}
		
	}
	
	/**
	 * Returns the path to which the html file of the source should be saved
	 * @param src the source file
	 * @return the path of the new .html file
	 */
	static protected String getExportPath(Source src)
	{
		IPath emptyPath = src.getTok().getProject().getProjectRelativePath();
		IPath htmlDirPath = emptyPath.append(ToK.HTML_FOLDER + "/");
		IPath srcPath = htmlDirPath.append(src.getFile().getProjectRelativePath());
		
		IPath fixedExtension = srcPath.removeFileExtension().addFileExtension("html"); 
		
		return fixedExtension.removeTrailingSeparator().toString();
	}
	
	@Override
	protected String getBody() 
	{
		StringBuffer s = new StringBuffer();
		
		String srcType = isRoot ? "root" : "source";
		
		s.append( "<div class=\"main_content\" id=\"" + srcType + "\">" );
		s.append( "<h1>" + srcDoc.getTitle() + "</h1>\n" );
		s.append( "<p>Written by: <em>" + srcDoc.getAuthor() + "</em></p>\n" );
		
		// TODO(Shay, low): Add a list of discussions using this source
		// TODO(Shay, low): Add a table of contents
		
		for (SrcElem e : elements)
		{
			s.append ( "\t" + e.getHTMLText().replaceAll("\n", "\t\n") );
		}
		
		s.append( "</div>\n" );
	
		return s.toString();
	}

}
