package lost.tok.html;

import java.util.HashMap;

import lost.tok.GeneralFunctions;
import lost.tok.Link;
import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.sorter.Sorter;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

/**
 * Creates and handles the navigation menu at the side of pages
 * @author Team Lost
 *
 */
public class Menu {
	
	/** The ToK of the project */
	private ToK tok;
	/** The next available id of the tree */
	private int freeId;
	/** The page we are generating a menu for */
	private HTMLPage menuOwner;
	/** The id of the menuOwner in the tree, if there is one.
	 *  Initialized when its node is generated, 0 if not found */
	private int ownerId;
	
	/** A map from the path of a source file to its HTMLPage */
	private HashMap<String, SourcePage> srcPathToPage;
	/** A map from the name of a discussion file to its HTMLPage */
	private HashMap<String, DiscussionPage> discNameToPage;
	/** The path to the icons directory for the dTree */
	private String imgBase;
	
	/**
	 * Creates a new side menu item
	 * @param tok the tok related to this menu
	 * @param page the page this menu is generated for
	 * @param srcPathToPage A map from the path of the project's sources to their HTMLPage
	 * @param discNameToPage A map from the name of the project's discussion's names to thier HTMLPage
	 */
	public Menu(ToK tok, HTMLPage page, HashMap<String, SourcePage> srcPathToPage, HashMap<String, DiscussionPage> discNameToPage)
	{
		this.tok = tok;
		this.freeId = 0;
		this.menuOwner = page;
		this.ownerId = 0;
		this.srcPathToPage = srcPathToPage;
		this.discNameToPage = discNameToPage;
		this.imgBase = GeneralFunctions.isLTR() ? ToK.HTML_FOLDER + "/other/menu/img/" : ToK.HTML_FOLDER + "/other/menu/imgRTL/"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/** Returns the line importing the menu javascript (should be in the html title) */
	public String getScriptLine()
	{
		String pathToJS = menuOwner.getPathTo(ToK.HTML_FOLDER + "/other/menu/dtree.js"); //$NON-NLS-1$
		return "<script type=\"text/javascript\" src=\"" + pathToJS + "\"></script>"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/** Returns the html's div element of the menu */
	public Element getMenuDiv()
	{
		//Element div = DocumentHelper.createElement("div");
		Element script = DocumentHelper.createElement("script"); //$NON-NLS-1$
		script.addAttribute("type", "text/javascript"); //$NON-NLS-1$ //$NON-NLS-2$
		
		script.addComment(getDTreeCode());
		return script;
	}
	
	/** Returns the code for creating the menu tree (dtree js) */
	private String getDTreeCode()
	{
		StringBuffer sb = new StringBuffer(1000);
		
		sb.append("\n"); //$NON-NLS-1$
		sb.append("d = new dTree('d');\n"); //$NON-NLS-1$
		sb.append("d.config.folderLinks = true;\n"); //$NON-NLS-1$
		sb.append("d.config.inOrder = true;\n"); //$NON-NLS-1$
		
		sb.append("d.icon.root = '" + menuOwner.getPathTo( imgBase + "tree1.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.folder = '" + menuOwner.getPathTo(imgBase + "folder.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.folderOpen = '" + menuOwner.getPathTo(imgBase + "folderopen.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.node = '" + menuOwner.getPathTo(imgBase + "page.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.empty = '" + menuOwner.getPathTo(imgBase + "empty.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.line = '" + menuOwner.getPathTo(imgBase + "line.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.join = '" + menuOwner.getPathTo(imgBase + "join.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.joinBottom = '" + menuOwner.getPathTo(imgBase + "joinbottom.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.plus = '" + menuOwner.getPathTo(imgBase + "plus.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.plusBottom = '" + menuOwner.getPathTo(imgBase + "plusbottom.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.minus = '" + menuOwner.getPathTo(imgBase + "minus.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.minusBottom = '" + menuOwner.getPathTo(imgBase + "minusbottom.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.nlPlus = '" + menuOwner.getPathTo(imgBase + "nolines_plus.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sb.append("d.icon.nlMinus = '" + menuOwner.getPathTo(imgBase + "nolines_minus.gif") + "';\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		sb.append(getAddCode(0, -1, Messages.getString("Menu.ToK"), menuOwner.getPathTo(ToK.HTML_FOLDER + "/index.html"), Messages.getString("Menu.BackToMain")));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		freeId = 1;
			
		sb.append(getAddSourcesCode(tok.getRootsFolder(), 0));
		sb.append(getAddSourcesCode(tok.getSourcesFolder(), 0));
		
		sb.append(getAddDiscsCode(0));
		
		sb.append("document.write(d);\n"); //$NON-NLS-1$
		sb.append("d.openTo(" + ownerId + ",true);\n"); //$NON-NLS-1$ //$NON-NLS-2$
		sb.append("d.openAll();\n"); //$NON-NLS-1$
		
		return sb.toString();
	}
	
	/**
	 * Returns the dtree js code for adding the sources into the tree
	 * @param srcFolder The folder which we want to add
	 * @param parentID the id of the parent element
	 * @return js code which adds the element in the source dir to the dtree
	 */
	private String getAddSourcesCode(IFolder srcFolder, int parentID)
	{
		StringBuffer sb = new StringBuffer();
		int myId = freeId;
		freeId++;
		
		String iconPath = menuOwner.getPathTo(imgBase + "folder.gif"); //$NON-NLS-1$
		String iconOpenPath = menuOwner.getPathTo(imgBase + "folderopen.gif");  //$NON-NLS-1$
		
		sb.append( getAddCode(myId, parentID, srcFolder.getName(), null, null, null, iconPath, iconOpenPath, null) );
		
		Sorter sorter = new Sorter(srcFolder);
		IResource[] resourses = sorter.getSorted();
		
		for (IResource res : resourses)
		{
			if ((res instanceof IFile) && (((IFile)res).getName().equals("order.xml")))
				continue;
			
			if ((res instanceof IFile) && (((IFile)res).getFileExtension().equals("src")))
				sb.append(getAddSourceCode((IFile)res, myId));
			
			else if (res instanceof IFolder)
				sb.append(getAddSourcesCode((IFolder)res, myId));
			
			else
				System.err.println("Unexpected resource: " + res);		 //$NON-NLS-1$
		}
		
		return sb.toString();
	}
	
	/** 
	 * Returns the js code for adding a link to source to the dtree
	 * @param srcFile the file to add
	 * @param parentID the id of its parent (dtree node id)
	 * @return the js code for adding a link to source to the dtree
	 */
	private String getAddSourceCode(IFile srcFile, int parentID)
	{
		String srcPath = srcFile.getProjectRelativePath().toString();
		
		SourcePage sPage = srcPathToPage.get(srcPath);
		
		// TODO(Shay, medium-low): different icons for roots
				
		int myId = freeId;
		freeId++;
		
		if (sPage == menuOwner)
			ownerId = myId;
		
		// TODO(Shay): JS escape the strings
		String title = sPage.getSourcDoc().getTitle();
		String link = menuOwner.getPathTo(sPage);
		String tooltip = title + Messages.getString("Menu.by") + sPage.getSourcDoc().getAuthor(); //$NON-NLS-1$
		
		String iconPath = menuOwner.getPathTo(imgBase + ( sPage.isSrcRoot() ? "root.png" : "source.png" )); //$NON-NLS-1$ //$NON-NLS-2$
		
		return getAddCode(myId, parentID, title, link, tooltip, null, iconPath, null, null);	
	}
	
	/**
	 * Returns the dtree js code for adding the discussions into the tree
	 * @param parentID the id of the parent element
	 * @return js code which adds the discussions dir to the dtree
	 */
	private String getAddDiscsCode(int parentId)
	{
		StringBuffer sb = new StringBuffer();
		
		int myId = freeId;
		freeId++;
		
		String iconPath = menuOwner.getPathTo(imgBase + "folder.gif"); //$NON-NLS-1$
		String iconOpenPath = menuOwner.getPathTo(imgBase + "folderopen.gif");  //$NON-NLS-1$
		
		sb.append( getAddCode(myId, parentId, ToK.DISCUSSION_FOLDER, null, null, null, iconPath, iconOpenPath, null) );
		
		for (String discName : discNameToPage.keySet())
		{
			sb.append( getAddDiscussionCode(discName, myId) );
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns the js code for adding the selected discussion to the tree
	 * @param discussionName the name of the discussion to add
	 * @param parentId the parent of the said discussion
	 * @return js code which adds the selected discussion to the menu
	 */
	private String getAddDiscussionCode(String discussionName, int parentId)
	{
		int myId = freeId;
		freeId++;
		
		DiscussionPage dPage = discNameToPage.get(discussionName);
		
		if (dPage == menuOwner)
			ownerId = myId;
		
		String urlLink = menuOwner.getPathTo(dPage);
		
		Link dLink = dPage.getDiscussion().getLink();
		String tooltip = discussionName + Messages.getString("Menu.by") + dPage.getDiscussion().getCreatorName() + ", ";  //$NON-NLS-1$//$NON-NLS-2$
		
		if (dLink != null)
			tooltip += dLink.getDisplayLinkType();
		else
			tooltip += Messages.getString("Menu.unlinked"); //$NON-NLS-1$
		
		String iconPath = menuOwner.getPathTo(imgBase + "disc.png"); //$NON-NLS-1$
		
		return getAddCode(myId, parentId, discussionName, urlLink, tooltip, null, iconPath, null, null);
	}
	
	/**
	 * Returns the code for a dtree js add command
	 * @param id Unique identity number.
	 * @param pid Number refering to the parent node. The value for the root node has to be -1.
	 * @param name Text label for the node.
	 * @param url Url for the node.
	 * @param title	Title for the node.
	 */
	private String getAddCode(int id, int pid, String name, String url, String title)
	{
		return getAddCode(id, pid, name, url, title, null, null, null, null);
	}
	
	
	/**
	 * Returns the code for a dtree js add command
	 * @param id Unique identity number.
	 * @param pid Number refering to the parent node. The value for the root node has to be -1.
	 * @param name Text label for the node.
	 * @param url Url for the node.
	 * @param title	Title for the node.
	 * @param target Target for the node.
	 * @param icon Image file to use as the icon. Uses default if not specified.
	 * @param iconOpen Image file to use as the open icon. Uses default if not specified.
	 * @param open Is the node open.
	 */
	private String getAddCode(int id, int pid, String name, String url, String title, 
								String target, String icon, String iconOpen, Boolean open )
	{
		StringBuffer sb = new StringBuffer(100);
		
		sb.append("d.add("); //$NON-NLS-1$
		sb.append(id);
		sb.append(',');
		sb.append(pid);
		sb.append(',');
		sb.append(jsEscape(name));
		sb.append(',');
		sb.append(jsEscape(url));
		sb.append(',');
		sb.append(jsEscape(title));
		sb.append(',');
		sb.append(jsEscape(target));
		sb.append(',');
		sb.append(jsEscape(icon));
		sb.append(',');
		sb.append(jsEscape(iconOpen));
		sb.append(',');
		sb.append(open == null ? "''" : open.toString()); //$NON-NLS-1$
		sb.append(");\n"); //$NON-NLS-1$
		
		return sb.toString();
	}
	
	/** Escapes a string so it could be embedded in js code */
	private String jsEscape(String str)
	{
		if (str != null)
			return "'" + str.replaceAll("'", "\\\\'") + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		else
			return "''"; //$NON-NLS-1$
	}
	
	
	
	
	

}
