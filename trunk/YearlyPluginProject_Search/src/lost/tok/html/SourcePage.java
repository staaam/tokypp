package lost.tok.html;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;

import lost.tok.Source;
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
	private Source src;
	
	/** The elements displayed on this page (either headings or paragraphs) */
	private List<SrcElem> elements;
	
	/** A mapping from xPath in the source to paragraph elements */
	private HashMap<String, LinkedParagraph> xPathToParagraph;

	public SourcePage(Source src)
	{
		super(src.getTok(),
				src.getFile().getFullPath().lastSegment(),
				getExportPath(src),
				HTMLPage.getHTMLRelPath(src.getTok().getProject(),src.getFile().getProjectRelativePath()) + SOURCE_CSS);
		
		this.src = src;
		
		SourceDocument srcDoc = new SourceDocument();
		srcDoc.set( this.src );
		this.elements = new LinkedList<SrcElem>();	
		buildElements( srcDoc );
	}
	
	/**
	 * Splits the SourceDocument and creates the elements attribute from it
	 * @param srcDoc the source document related to this Source
	 */
	private void buildElements( SourceDocument srcDoc )
	{
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
	 * Returns the path to which the html file of the source should be saved
	 * @param src the source file
	 * @return the path of the new .html file
	 */
	static protected String getExportPath(Source src)
	{
		IPath emptyPath = src.getTok().getProject().getProjectRelativePath();
		IPath htmlDirPath = emptyPath.append("html/");
		IPath srcPath = htmlDirPath.append(src.getFile().getProjectRelativePath());
		
		// FIXME(Shay): Verify this is correct
		return srcPath.removeFileExtension().addFileExtension(".html").toString();
	}
	
	@Override
	protected String getBody() 
	{
		
				
		return null;
	}

}
