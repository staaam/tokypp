package lost.tok.html.srcElem;

import java.util.LinkedList;

import lost.tok.Link;
import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.html.DiscussionPage;

/**
 * A small struct defining where a link ends and what discussions it includes
 * @author Team Lost
 */
public class LinkInfo 
{
	public static String TYPE_COMBINED = "combined"; //$NON-NLS-1$
	
	/** The offset of the link's end (exclusive) */
	private int end;
	/** The linked discussions, 1 or more */
	private LinkedList<DiscussionPage> discs;
	/** The text of the linked discussions */
	private LinkedList<String> linksText;
	/** The links of the linked discussions (used for meta data) */
	private LinkedList<Link> links;
	
	/** Creates a link info without any discussions */
	public LinkInfo(int end)
	{
		this.end = end;
		this.discs = new LinkedList<DiscussionPage>();
		this.linksText = new LinkedList<String>();
		this.links = new LinkedList<Link>();
	}
	
	/**
	 * Creates a link info with a single discussion
	 * @param end the end offset (exclusive) of the link 
	 * @param disc the discussion linked too
	 * @param linkText the full text of the linked excerption
	 * @param link the link of the added discussion
	 */
	public LinkInfo(int end, DiscussionPage disc, String linkText, Link link)
	{
		this(end);
		add(disc, linkText, link);
	}
	
	/** Adds a discussion to this info, with the given linkedText and link data */
	public void add(DiscussionPage disc, String linkedText, Link link)
	{
		discs.add(disc);
		linksText.add(linkedText);
		links.add(link);
	}
	
	/** Retruns a shallow copy of this (discussions and links are not cloned) */
	public LinkInfo clone()
	{
		LinkInfo l = new LinkInfo(end);
		for (DiscussionPage d : discs)
			l.discs.add(d);
		for (String lt : linksText)
			l.linksText.add(lt);
		for (Link link : links)
			l.links.add(link);
		return l;
	}
	
	/** Returns the ToK used by the link */
	public ToK getTok()
	{
		assert( discs.size() > 0 );
		return discs.getFirst().getTok();
	}
	
	/** Returns the only discussion in this link, if there is only one */
	public DiscussionPage getOnlyPage()
	{
		assert( discs.size() == 1 );
		return discs.getFirst();
	}
	
	/** Returns the number of linked discussions */
	public int size()
	{
		assert( linksText.size() == discs.size() );
		assert( discs.size() == links.size() );
		return discs.size();
	}

	/** Returns the end offset of the link (exclusive) */
	public int getEnd() {
		return end;
	}
	
	/** Sets the offset of the link's end */
	public void setEnd(int end) {
		this.end = end;
	}
	
	/** Returns the type of the link, or "combined" if there is more than one */
	public String getType() {
		if (size() == 1)
			return links.getFirst().getLinkType();
		// else
		return TYPE_COMBINED;
	}
	
	/**
	 *  Returns a comma seperated list of the linked discussions names,
	 *  If there is only one link, returns the subject of the link too
	 */
	public String getNames() {
		if (size() == 1)
		{
			String discName = links.getFirst().getLinkedDiscussion().getDiscName();
			String linkName = links.getFirst().getSubject();
			
			return Messages.getString("LinkInfo.Discussion") + discName + ", " + linkName; //$NON-NLS-1$ //$NON-NLS-2$
		}
		// else
		String retVal = Messages.getString("LinkInfo.Combined"); //$NON-NLS-1$
		for (int i=0; i < links.size() - 1; i++)
		{
			Link l = links.get(i);
			retVal += l.getLinkedDiscussion().getDiscName() + " ,"; //$NON-NLS-1$
		}
		retVal += links.getLast().getLinkedDiscussion().getDiscName();
		
		return retVal;
	}
	
	/** Returns the i'th linked discussion page */
	public DiscussionPage getDPage(int i)
	{
		return discs.get(i);
	}
	
	/** Returns the excerption's text for the i'th link */
	public String getLinkText(int i)
	{
		return linksText.get(i);
	}
	
	/** Returns the i'th link */
	public Link getLink(int i)
	{
		return links.get(i);
	}
}