package lost.tok.html;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import lost.tok.Discussion;
import lost.tok.GeneralFunctions;
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

	public DiscussionPage(Discussion disc)
	{
		super(disc.getMyToK(),
				disc.getDiscFileName(),
				getExportPath(disc),
				getCSSPath(disc));
		
		this.disc = disc;
				
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
		
		
		// TODO(Shay) continue working on the getBody method
		return GeneralFunctions.elementToString(body);
	}

}
