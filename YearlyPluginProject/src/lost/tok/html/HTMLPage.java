package lost.tok.html;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import lost.tok.GeneralFunctions;
import lost.tok.ToK;

/**
 * An abstract xhtml page
 * 
 * Includes all the xhtml title information
 * @author Team Lost
 */
abstract public class HTMLPage 
{
	/** The CSS file used by most of the html project */
	public static String DEFAULT_CSS = "other/nice.css";
	
	/** The ToK connected to this page */
	protected ToK tok;
	
	/** The page's title */
	protected String title;
	/** The path underwhich the file is to be saved. Path is relative to the project's root dir */
	protected String exportPath;	
	/** The path to the css file used by this page */
	private String cssPath;
	
	/** True if the page should be left to right */
	protected boolean ltr;
	/** The page's language (he for Hebrew, en for English) */
	protected String lang;
	
	/**
	 * Creates an abstract html page
	 * @param tok the ToK related to this page
	 * @param title the title of this page
	 * @param exportedFilePath the name and path of the file to create
	 * @param css the css page connected to this file
	 */
	protected HTMLPage(ToK tok, String title, String exportedFilePath, String css)
	{
		this.tok = tok;
		this.title = title;
		this.cssPath = css;
		
		this.ltr = GeneralFunctions.isLTR();
		this.lang = GeneralFunctions.langCode();
	}
	
	/**
	 * Returns the body of the html page
	 * Should return all the content between the \<body\> and \</body\> tages  
	 */
	protected abstract String getBody(); 
	
	/**
	 * Returns the full html page
	 */
	public String getPage()
	{
		String htmlPage = "";
		
		htmlPage += "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"" +
						" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n";
		htmlPage += "\n";
		htmlPage += "<html xml:lang=\"" + lang + "\" lang=\"" + lang + "\" dir=\"" + (ltr ? "ltr" : "rtl") + "\">\n";
		htmlPage += "\n";
		htmlPage += "<head>\n";
		htmlPage += "\t<title>" + GeneralFunctions.xmlEscape(title) + "</title>\n";
		htmlPage += "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssPath + "\" />\n"; 
		htmlPage += "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"; 
		htmlPage += "\t<meta name=\"author\" content=\"Tree of Knowledge Site Exporter\" />\n";
		htmlPage += "\n";
		htmlPage += "</head>\n";
		htmlPage += "<body>\n";
		htmlPage += "\t" + getBody().replaceAll("\n", "\n\t");
		htmlPage += "</body>\n";
		htmlPage += "</html>\n";
		
		return htmlPage;
	}
	
	/**
	 * Creates the file on the disk
	 * 
	 * May create more than one file, if there are other files connected to this page
	 */
	public void generatePage()
	{
		// TODO(Shay): Walk over the directories and generate them as needed
		
		// TODO(Shay): Create an input stream from the page's text
		// TODO(Shay): Puke the input stream into the file
	}
	
	/**
	 * Returns the tok related to this page
	 */
	public ToK getTok()
	{
		return tok;
	}
	
	/**
	 * Returns the relative path (to the project) in which the file will be saved
	 */
	protected String getPagePath()
	{
		return exportPath;
	}
	
	/**
	 * Returns a path from this page to another page
	 * @param otherPage the page we want to get to
	 * @return a path (with / as slashes) from this page to the other page
	 */
	public String getPathTo(HTMLPage otherPage)
	{
		// not handling this case
		assert(this != otherPage);
		
		String[] srcExportPath = this.exportPath.split("/");
		String[] targetExportPath = otherPage.exportPath.split("/");
		
		String path = "";
		int pathIdx = 0;
		
		while (pathIdx < srcExportPath.length - 1 && 
				pathIdx < targetExportPath.length - 1 &&
				targetExportPath[pathIdx].equals( srcExportPath[pathIdx]) )
		{
			// so far the directories are similar
			// so we do not include them in the relative path
			pathIdx ++;
		}
		
		while (pathIdx < srcExportPath.length - 1)
		{
			// now we descend directories back to the closest common ancestor
			path += "../";
			pathIdx ++;
		}
		
		while (pathIdx < targetExportPath.length)
		{
			// now we ascend directories to the target path
			path += targetExportPath[pathIdx] + "/";
			pathIdx ++;
		}
		
		return path;	
	}
	
	/**
	 * Given a path inside a project, returns a path from html/<that path> back to the html dir.
	 * 
	 * This means that for a the path "a/b/c/d.src" the path "../../.." will be returned
	 * 
	 * @param proj The project related to the path
	 * @param path The path of the resource
	 * @return "../" times the number of dirs in the path
	 */
	static protected String getHTMLRelPath(IProject proj, IPath path)
	{
		int segCount = path.segmentCount();
		
		// start with an empty path
		IPath currPath = proj.getProjectRelativePath();
		
		// descend all the directories
		// -1 for the filename segment (which is not a dir)
		for (int i=0; i < segCount - 1; i++)
			currPath.append("../");
		
		// TODO(Shay): Verify correctness
		return currPath.toString();
	}
	

}
