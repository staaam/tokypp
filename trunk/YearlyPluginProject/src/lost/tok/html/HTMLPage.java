package lost.tok.html;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import lost.tok.GeneralFunctions;
import lost.tok.ToK;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * An abstract xhtml page
 * 
 * Includes all the xhtml title information
 * @author Team Lost
 */
abstract public class HTMLPage 
{	
	/** The ToK connected to this page */
	protected ToK tok;
	
	/** The page's title */
	protected String title;
	/** The path underwhich the file is to be saved. Path is relative to the project's root dir, / seperated */
	protected String exportPath;	
	/** The path to the css file used by this page */
	protected String cssPath;
	/** The side menu of the page */
	protected Menu menu;
	
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
	 * Returns the main content of the html page
	 * Should return a &lt;div&gt; element with all its content as a string
	 */
	protected abstract String getBody();
	
	/**
	 * Sets the menu for this page
	 * @param menu the menu that will be used
	 */
	public void setMenu(Menu menu)
	{
		this.menu = menu;		
	}
	
	/**
	 * Returns the full html page
	 */
	public String getPage()
	{
		String body = getBody();
		
 		StringBuffer htmlPage = new StringBuffer(body.length() + 2000);
		
		htmlPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\""); //$NON-NLS-1$
		htmlPage.append(" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"); //$NON-NLS-1$
		htmlPage.append("\n"); //$NON-NLS-1$
		htmlPage.append("<html xml:lang=\"" + lang + "\" lang=\"" + lang + "\" dir=\"" + (ltr ? "ltr" : "rtl") + "\">\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		htmlPage.append("\n"); //$NON-NLS-1$
		htmlPage.append("<head>\n"); //$NON-NLS-1$
		htmlPage.append("\t<title>" + GeneralFunctions.xmlEscape(title) + "</title>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		htmlPage.append("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssPath + "\" />\n");  //$NON-NLS-1$ //$NON-NLS-2$
		htmlPage.append("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n");  //$NON-NLS-1$
		htmlPage.append("\t<meta name=\"author\" content=\"Tree of Knowledge Site Exporter\" />\n"); //$NON-NLS-1$
		
		if (menu != null)
			htmlPage.append("\t" + menu.getScriptLine() + "\n"); // adds the dTree js   //$NON-NLS-1$ //$NON-NLS-2$
		
		htmlPage.append("\n"); //$NON-NLS-1$
		htmlPage.append("</head>\n"); //$NON-NLS-1$
		htmlPage.append("<body>\n"); //$NON-NLS-1$
		
		// TODO(Shay, medium-low): add menu
		htmlPage.append("<div id=\"menu\">"); //$NON-NLS-1$
		if (menu != null)
			htmlPage.append(GeneralFunctions.elementToString(menu.getMenuDiv()));
		htmlPage.append("</div>\n");  //$NON-NLS-1$

		htmlPage.append("\t" + body.replaceAll("\n", "\n\t")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		htmlPage.append("</body>\n"); //$NON-NLS-1$
		htmlPage.append("</html>\n"); //$NON-NLS-1$
		
		return htmlPage.toString();
	}
	
	/**
	 * Generates the directories leading the the given path
	 * Works even if a certain directory on the path already exist
	 * 
	 * @param proj the project under which the directories should be created
	 * @param path a / seperated path, with / after the last directory
	 * @throws CoreException if cannot create folder
	 */
	static public void generatePath(IProject proj, String path) throws CoreException
	{
		String [] pathSegs = path.split("/"); //$NON-NLS-1$
		
		IPath ipath = proj.getProjectRelativePath();
		for (int i=0; i < pathSegs.length-1; i++)
		{
			ipath = ipath.append( pathSegs[i] + "/" ); //$NON-NLS-1$
			IFolder f = proj.getFolder(ipath);
			if (!f.exists())
				f.create(true, true, null);
		}
	}
	
	/**
	 * Creates the file on the disk
	 * 
	 * May create more than one file, if there are other files connected to this page
	 */
	public void generatePage() throws CoreException
	{
		// 1. Create the entire path leading to that file, if it doesn't exist
		generatePath(tok.getProject(), exportPath);
				
		IFile f = tok.getProject().getFile(exportPath);
		
		// Note(Shay): we delete the old html dir before creating it anew
		//assert(f.exists() == false);
		
		// 2. Create an input stream from the page's text
		String pageString = getPage();
		
		byte[] pageBytes;
		try {
			pageBytes = pageString.getBytes("UTF-8"); //$NON-NLS-1$
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
	 * Returns a path from this page to another url in the site
	 * @param otherPath the path of the other item, relative to the project
	 * @return a path (with / as slashes) from this page to the other page
	 */
	public String getPathTo(String otherPath)
	{
		String[] srcExportPath = this.exportPath.split("/"); //$NON-NLS-1$
		String[] dstExportPath = otherPath.split("/"); //$NON-NLS-1$
		
		String path = ""; //$NON-NLS-1$
		int srcPathIdx = 0;
		int dstPathIdx = 0;
		
		while (srcPathIdx < srcExportPath.length - 1 && 
				dstPathIdx < dstExportPath.length - 1 &&
				dstExportPath[dstPathIdx].equals( srcExportPath[srcPathIdx]) )
		{
			// so far the directories are similar
			// so we do not include them in the relative path
			srcPathIdx ++;
			dstPathIdx ++;
		}
		
		while (srcPathIdx < srcExportPath.length - 1)
		{
			// now we descend directories back to the closest common ancestor
			path += "../"; //$NON-NLS-1$
			srcPathIdx ++;
		}
		
		while (dstPathIdx < dstExportPath.length - 1)
		{
			// now we ascend directories to the target path
			path += dstExportPath[dstPathIdx] + "/"; //$NON-NLS-1$
			dstPathIdx ++;
		}
		
		// we want to add the last file without a trailing "/"
		path += dstExportPath[ dstExportPath.length - 1 ];
		
		// now we need to escape spaces in the path
		path = path.replace(" ", "%20"); //$NON-NLS-1$ //$NON-NLS-2$
		
		return path;
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
		return getPathTo(otherPage.exportPath);
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
		String currPath = ""; //$NON-NLS-1$
		
		// descend all the directories
		// -1 for the filename segment (which is not a dir)
		for (int i=0; i < segCount - 1; i++)
			currPath += "../"; //$NON-NLS-1$
		
		return currPath;
	}
	

}
