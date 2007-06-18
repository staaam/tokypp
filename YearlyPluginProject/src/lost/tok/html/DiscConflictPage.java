package lost.tok.html;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import lost.tok.Discussion;
import lost.tok.GeneralFunctions;
import lost.tok.Link;
import lost.tok.ToK;
import lost.tok.html.srcElem.LinkInfo;

/**
 * In case two discussions are linked to the same text
 * Clicking on that text in the source opens a window
 * in which we choose the correct discussion
 * @author Team Lost
 *
 */
public class DiscConflictPage extends HTMLPage {
	
	static private int counter = 0;
	@SuppressWarnings("unused")
	static private LinkInfo info;
	
	
	public DiscConflictPage(LinkInfo li)
	{
		super( li.getTok(), 
				"Select Discussion", 
				ToK.HTML_FOLDER + "/other/conf" + counter + ".html", 
				"../" + HTMLPage.DEFAULT_CSS );
		
		info = li;
		counter ++;
		
		// make sure there really is a conflict
		assert(info.size() >= 2); 
	}

	@Override
	protected String getBody() {
		Element body = DocumentHelper.createElement("body"); //$NON-NLS-1$
		
		// main title of Discussion conflict page
		body.addElement("h1").addText("Choose Discussion");
		
		
		for (int i =0; i < info.size(); i++)
		{
			// 1. extract information from the info object
			DiscussionPage dPage = info.getDPage(i);
			Link link = info.getLink(i);
			Discussion disc = link.getLinkedDiscussion();
			String text = info.getLinkText(i);
		
			// 2. Add the i'th discussion title
			Element div = body.addElement("div");
			div.addElement("h2").addText(link.getDisplayLinkType() + ": " + link.getSubject());
			
			// 3. Add the i'th excerption's text
			Element quoteElm = div.addElement("blockquote");
			quoteElm.addAttribute("class", "sublink");
			quoteElm.addAttribute("cite", getPathTo(dPage));
			quoteElm.addElement("p").addText(text);
			
			// 4. Add the link to the discussion + creator's name
			Element p = div.addElement("p");
			Element linkElm = p.addElement("a");
			linkElm.addAttribute("href", getPathTo(dPage));
			linkElm.addText(disc.getDiscName());
			p.addText(" Created By: ");
			p.addElement("em").addText(disc.getCreatorName());
		}
		
		return GeneralFunctions.elementToString(body);
	}

}
