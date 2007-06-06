package lost.tok.html;

import java.util.List;

import org.eclipse.core.runtime.IPath;

import lost.tok.Source;
import lost.tok.html.srcElem.SrcElem;

/**
 * An HTML exported version of a source file
 * 
 * Displays the source text, as well as links to linked Discussions
 * @author Team Lost
 */
public class SourcePage extends HTMLPage {
	
	public static String SOURCE_CSS = DEFAULT_CSS;
	
	@SuppressWarnings("unused")
	private Source src;
	
	@SuppressWarnings("unused")
	private List<SrcElem> elements;

	public SourcePage(Source src)
	{
		super(src.getTok(),
				src.getFile().getFullPath().lastSegment(),
				getExportPath(src),
				HTMLPage.getHTMLRelPath(src.getTok().getProject(),src.getFile().getProjectRelativePath()) + SOURCE_CSS);
		
		this.src = src;
				
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
		return srcPath.removeFileExtension().addFileExtension(".html").toOSString();
	}
	
	
	
	@Override
	protected String getBody() {
		// TODO Auto-generated method stub
		return null;
	}

}
