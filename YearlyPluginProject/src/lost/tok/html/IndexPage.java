/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

package lost.tok.html;

import java.util.HashMap;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.Messages;
import lost.tok.Source;
import lost.tok.SubLink;
import lost.tok.ToK;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.core.runtime.CoreException;

/**
 * The main page of the ToK site
 * 
 * Includes links to all the Dicussions and Sources
 * Responsible for generating all the html files
 * Responsible for parsing the links.xml files
 * @author Team Lost
 */
public class IndexPage extends HTMLPage {

	/** The discussions of the ToK, during it's creation */
	private Discussion[] discussions;
	/** The sources (and roots) of the ToK, during it's creation */
	private Source[] sources;
	
	/** A map from the path of a source file to its HTMLPage */
	private HashMap<String, SourcePage> srcPathToPage;
	/** A map from the name of a discussion file to its HTMLPage */
	private HashMap<String, DiscussionPage> discNameToPage;
	
	/** The manager of all our css files */
	private CSSManager cssManager;
	
	public IndexPage(ToK tok)
	{
		super(tok,
				tok.getProject().getName() + Messages.getString("IndexPage.ToK"), //$NON-NLS-1$
				ToK.HTML_FOLDER + "/index.html", //$NON-NLS-1$
				null);
		
		
		cssManager = new CSSManager(tok);
		this.cssPath = cssManager.addIndexPage();
		discussions = tok.getDiscussions().toArray( new Discussion[0] );
		sources = tok.getSources();
		
		this.cssManager = new CSSManager(tok);
		
		// 1. Find all the sub pages
		srcPathToPage = new HashMap<String, SourcePage>();
		for (Source source : sources)
		{
			SourcePage sPage = new SourcePage(source, cssManager, this);
			String srcPath = source.getFile().getProjectRelativePath().toString();
			srcPathToPage.put( srcPath, sPage );
		}
		
		discNameToPage = new HashMap<String, DiscussionPage>();
		for (Discussion disc : discussions)
		{
			DiscussionPage dPage = new DiscussionPage(disc, srcPathToPage, cssManager);
			String discName = disc.getDiscName();
			discNameToPage.put(discName, dPage);
		}
		
		// 2. Link the Roots to the discussions
		for (Discussion disc : discussions)
		{
			if (disc.getLink() == null)
				continue;
			
			DiscussionPage dPage = discNameToPage.get( disc.getDiscName() );
			
			for (SubLink sublink : disc.getLink().getSubLinkList())
			{
				Source src = sublink.getLinkedSource();
				String srcPath = src.getFile().getProjectRelativePath().toString();
				SourcePage sPage = srcPathToPage.get ( srcPath );
				
				for (Excerption e : sublink.getExcerption())
					sPage.addLink( e, disc.getLink(), dPage);
			}
		}
		
		setMenuPages();
	}

	@Override
	protected String getBody() {
		Element body = DocumentHelper.createElement("div"); //$NON-NLS-1$
		body.addAttribute("id", "index"); //$NON-NLS-1$ //$NON-NLS-2$
		body.addAttribute("class", "main_content"); //$NON-NLS-1$ //$NON-NLS-2$
		
		body.addElement("h1").addText( tok.getProject().getName() ); //$NON-NLS-1$
		
		Element author = body.addElement("p").addText(Messages.getString("IndexPage.CreatedBy")); //$NON-NLS-1$ //$NON-NLS-2$
		author.addElement("em").addText(tok.getProjectCreator()); //$NON-NLS-1$
		
		body.add( getSourcesElement(true) );
		body.add( getSourcesElement(false) );
		body.add( getDiscussionElement() );
		
		return GeneralFunctions.elementToString(body);
	}
	
	/**
	 * Returns a list of either sources or roots as an html elements
	 * If the list is empty, returns an empty div element instead
	 * @param isRoot true if the list should be of roots
	 * @return an html div element containing the entire list
	 */
	public Element getSourcesElement(boolean genRoot)
	{
		Element main = DocumentHelper.createElement("div"); //$NON-NLS-1$
		String listTitle = genRoot ? Messages.getString("IndexPage.Roots") : Messages.getString("IndexPage.Sources"); //$NON-NLS-1$ //$NON-NLS-2$
		
		main.addElement("h2").addText(listTitle); //$NON-NLS-1$
		
		int numItems = 0; // the number of items in the list
		
		Element rootsList = main.addElement("ul"); //$NON-NLS-1$
		for (Source src : sources)
		{
			if (src.isRoot() != genRoot)
				continue;
			
			Element item = rootsList.addElement("li"); //$NON-NLS-1$
			
			String srcPath = src.getFile().getProjectRelativePath().toString();
			
			Element a = item.addElement("a"); //$NON-NLS-1$
			SourcePage sPage = srcPathToPage.get( srcPath );
			a.addAttribute("href", getPathTo(sPage) ); //$NON-NLS-1$
			// a.addAttribute("tooltip", value)
			a.addText( src.getTitle() );
			
			item.addText(Messages.getString("IndexPage.by")); //$NON-NLS-1$
			item.addElement("em").addText( src.getAuthor() ); //$NON-NLS-1$
			item.addText(" (" + srcPath + ")" ); //$NON-NLS-1$ //$NON-NLS-2$
			
			numItems ++;
		}
		
		if (numItems > 0)
			return main;
		// else, we return an empty div
		return DocumentHelper.createElement("div"); //$NON-NLS-1$
	}
	
	/**
	 * Returns a list of links to discussions, or empty div if no discussions exist
	 * @return an html div element containing the entire list
	 */
	public Element getDiscussionElement()
	{
		Element main = DocumentHelper.createElement("div"); //$NON-NLS-1$
		
		if (discussions.length == 0)
			return main; // empty div
		
		main.addElement("h2").addText(Messages.getString("IndexPage.Discussions")); //$NON-NLS-1$ //$NON-NLS-2$
			
		Element rootsList = main.addElement("ul"); //$NON-NLS-1$
		for (Discussion disc : discussions)
		{	
			Element item = rootsList.addElement("li"); //$NON-NLS-1$
			
			Element a = item.addElement("a"); //$NON-NLS-1$
			DiscussionPage dPage = discNameToPage.get( disc.getDiscName() );
			a.addAttribute("href", getPathTo(dPage) ); //$NON-NLS-1$
			// TODO(Shay, low): Add a normal tooltip in the index page
			//   a.addAttribute("tooltip", disc.getLinkType() );
			a.addText( disc.getDiscName() );
			
			item.addText(Messages.getString("IndexPage.by")); //$NON-NLS-1$
			item.addElement("em").addText( disc.getCreatorName() ); //$NON-NLS-1$
			item.addText(" (" + disc.getDiscFileName() + ")" ); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		return main;
	}
	
	/** Sets menu pages to all the sub pages */
	private void setMenuPages()
	{
		// First, set a menu for me
		addMenuToPage(this);
		
		// Then for my close friends
		for (DiscussionPage dPage : discNameToPage.values())
			addMenuToPage(dPage);
		
		// Then for more friends 
		for (SourcePage sPage : srcPathToPage.values())
			addMenuToPage(sPage);
	}
	
	/** Adds a menu page to the parameter */
	public void addMenuToPage(HTMLPage targetPage)
	{
		targetPage.setMenu( new Menu(tok, targetPage, srcPathToPage, discNameToPage) );	
	}
	
	
	@Override
	public void generatePage() throws CoreException
	{
		super.generatePage();
		
		// Generate all the sub pages
		for (SourcePage sPage : srcPathToPage.values())
			sPage.generatePage();
		
		for (DiscussionPage dPage : discNameToPage.values())
			dPage.generatePage();
		
		cssManager.generatePages();
	}

}
