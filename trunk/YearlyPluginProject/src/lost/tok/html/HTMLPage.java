package lost.tok.html;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
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
	/** The path underwhich the file is to be saved. Path is relative to the project's root dir, / seperated */
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
		this.exportPath = exportedFilePath;
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
		String body = getBody();
		
 		StringBuffer htmlPage = new StringBuffer(body.length() + 2000);
		
		htmlPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"");
		htmlPage.append(" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
		htmlPage.append("\n");
		htmlPage.append("<html xml:lang=\"" + lang + "\" lang=\"" + lang + "\" dir=\"" + (ltr ? "ltr" : "rtl") + "\">\n");
		htmlPage.append("\n");
		htmlPage.append("<head>\n");
		htmlPage.append("\t<title>" + GeneralFunctions.xmlEscape(title) + "</title>\n");
		htmlPage.append("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssPath + "\" />\n"); 
		htmlPage.append("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"); 
		htmlPage.append("\t<meta name=\"author\" content=\"Tree of Knowledge Site Exporter\" />\n");
		htmlPage.append("\n");
		htmlPage.append("</head>\n");
		htmlPage.append("<body>\n");
		htmlPage.append("\t" + body.replaceAll("\n", "\n\t"));
		//htmlPage.append("\t" + getBody(); // TODO(Shay, low): Make sure the final xml looks good
		htmlPage.append("</body>\n");
		htmlPage.append("</html>\n");
		
		return htmlPage.toString();
	}
	
	/**
	 * Creates the file on the disk
	 * 
	 * May create more than one file, if there are other files connected to this page
	 */
	public void generatePage() throws CoreException
	{
		// 1. Create the entire path leading to that file, if it doesn't exist
		String [] pathSegs = exportPath.split("/");
		
		IPath path = tok.getProject().getProjectRelativePath();
		for (int i=0; i < pathSegs.length-1; i++)
		{
			path = path.append( pathSegs[i] + "/" );
			IFolder f = tok.getProject().getFolder(path);
			if (!f.exists())
				f.create(true, true, null);
		}
		
		IFile f = tok.getProject().getFile(exportPath);
		
		// Note(Shay): we should delete the old html dir before creating it anew
		assert(f.exists() == false);
		
		// 2. Create an input stream from the page's text
		String pageString = getPage();
		
		byte[] pageBytes;
		try {
			pageBytes = pageString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			pageBytes = pageString.getBytes();
		}		
		
		ByteArrayInputStream bais = new ByteArrayInputStream(pageBytes);
		
		// 3. Puke the input stream into the file
		f.create(bais, true, null);
		
		// TODO(Shay): Generate the CSS file, here or in another place
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
