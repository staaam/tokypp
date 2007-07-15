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

import lost.tok.Discussion;
import lost.tok.GeneralFunctions;
import lost.tok.Link;
import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.html.srcElem.LinkInfo;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * In case two discussions are linked to the same text
 * Clicking on that text in the source opens a window
 * in which we choose the correct discussion
 * @author Team Lost
 *
 */
public class DiscConflictPage extends HTMLPage {
	
	static private int counter = 0;
	static private LinkInfo info;
	
	
	public DiscConflictPage(LinkInfo li, CSSManager cssMan)
	{
		super( li.getTok(), 
				Messages.getString("DiscConflictPage.SelectDiscussion"),  //$NON-NLS-1$
				ToK.HTML_FOLDER + "/other/conf" + counter + ".html",  //$NON-NLS-1$ //$NON-NLS-2$
				cssMan.addDiscConflict(counter) ); //$NON-NLS-1$
		
		info = li;
		counter ++;
		
		// make sure there really is a conflict
		assert(info.size() >= 2); 
	}

	@Override
	protected String getBody() {
		Element body = DocumentHelper.createElement("div"); //$NON-NLS-1$
		body.addAttribute("id", "conflict"); //$NON-NLS-1$ //$NON-NLS-2$
		body.addAttribute("class", "main_content"); //$NON-NLS-1$ //$NON-NLS-2$
		
		// main title of Discussion conflict page
		body.addElement("h1").addText(Messages.getString("DiscConflictPage.ChooseDiscussion")); //$NON-NLS-1$ //$NON-NLS-2$
		
		
		for (int i =0; i < info.size(); i++)
		{
			// 1. extract information from the info object
			DiscussionPage dPage = info.getDPage(i);
			Link link = info.getLink(i);
			Discussion disc = link.getLinkedDiscussion();
			String text = info.getLinkText(i);
		
			// 2. Add the i'th discussion title
			Element div = body.addElement("div"); //$NON-NLS-1$
			div.addElement("h2").addText(link.getDisplayLinkType() + ": " + link.getSubject()); //$NON-NLS-1$ //$NON-NLS-2$
			
			// 3. Add the i'th excerption's text
			Element quoteElm = div.addElement("blockquote"); //$NON-NLS-1$
			quoteElm.addAttribute("class", "sublink"); //$NON-NLS-1$ //$NON-NLS-2$
			quoteElm.addAttribute("cite", getPathTo(dPage)); //$NON-NLS-1$
			quoteElm.addElement("p").addText(text); //$NON-NLS-1$
			
			// 4. Add the link to the discussion + creator's name
			Element p = div.addElement("p"); //$NON-NLS-1$
			Element linkElm = p.addElement("a"); //$NON-NLS-1$
			linkElm.addAttribute("href", getPathTo(dPage)); //$NON-NLS-1$
			linkElm.addText(disc.getDiscName());
			p.addText(Messages.getString("DiscConflictPage.CreatedBy")); //$NON-NLS-1$
			p.addElement("em").addText(disc.getCreatorName()); //$NON-NLS-1$
		}
		
		return GeneralFunctions.elementToString(body);
	}

}
