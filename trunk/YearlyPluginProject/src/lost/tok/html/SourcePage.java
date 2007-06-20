package lost.tok.html;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lost.tok.Excerption;
import lost.tok.Link;
import lost.tok.Messages;
import lost.tok.Source;
import lost.tok.ToK;
import lost.tok.html.srcElem.Heading;
import lost.tok.html.srcElem.LinkedParagraph;
import lost.tok.html.srcElem.SrcElem;
import lost.tok.sourceDocument.Chapter;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * An HTML exported version of a source file
 * 
 * Displays the source text, as well as links to linked Discussions
 * @author Team Lost
 */
public class SourcePage extends HTMLPage {
	
	/** The Source displayed by this page */
	private SourceDocument srcDoc;
	/** The elements displayed on this page (either headings or paragraphs) */
	private List<SrcElem> elements;
	/** True iff the source is a root file */
	private boolean isRoot;
	/** A mapping from xPath in the source to paragraph elements */
	private HashMap<String, LinkedParagraph> xPathToParagraph;
	/** The css manager of this class */
	private CSSManager cssMan;

	/**
	 * Create a source page for the given source
	 * Links to discussions can be added later
	 * @param src the source for which this page is built
	 */
	public SourcePage(Source src, CSSManager cssMan)
	{
		super(src.getTok(),
				src.getFile().getFullPath().lastSegment(),
				getExportPath(src),
				cssMan.add(src));
		
		srcDoc = new SourceDocument();
		srcDoc.set( src );
		
		isRoot = src.isRoot();
		this.cssMan = cssMan;
		
		buildElements();
	}
	
	public CSSManager getCSSMan()
	{
		return cssMan;
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
		IPath htmlDirPath = emptyPath.append(ToK.HTML_FOLDER + "/"); //$NON-NLS-1$
		IPath srcPath = htmlDirPath.append(src.getFile().getProjectRelativePath());
		
		IPath fixedExtension = srcPath.removeFileExtension().addFileExtension("html");  //$NON-NLS-1$
		
		return fixedExtension.removeTrailingSeparator().toString();
	}
	
	@Override
	protected String getBody() 
	{
		StringBuffer s = new StringBuffer();
		
		String srcType = isRoot ? "root" : "source"; //$NON-NLS-1$ //$NON-NLS-2$
		
		s.append( "<div class=\"main_content\" id=\"" + srcType + "\">" ); //$NON-NLS-1$ //$NON-NLS-2$
		s.append( "<h1>" + srcDoc.getTitle() + "</h1>\n" ); //$NON-NLS-1$ //$NON-NLS-2$
		s.append( Messages.getString("SourcePage.WrittenBy") + srcDoc.getAuthor() + "</em></p>\n" ); //$NON-NLS-1$ //$NON-NLS-2$
		
		// TODO(Shay, low): Add a list of discussions using this source
		// TODO(Shay, low): Add a table of contents
		
		for (SrcElem e : elements)
		{
			s.append ( "\t" + e.getHTMLText().replaceAll("\n", "\t\n") ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		
		s.append( "</div>\n" ); //$NON-NLS-1$
	
		return s.toString();
	}

}
