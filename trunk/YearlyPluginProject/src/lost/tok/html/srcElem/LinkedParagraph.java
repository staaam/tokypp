package lost.tok.html.srcElem;

import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.Link;
import lost.tok.html.DiscConflictPage;
import lost.tok.html.DiscussionPage;
import lost.tok.html.HTMLPage;
import lost.tok.html.SourcePage;
import lost.tok.html.srcElem.LinkInfo;

public class LinkedParagraph implements SrcElem
{
	/** The source page this paragraph is related to */
	private SourcePage source;
	
	/** 
	 * a map from a link start offset (inclusive)
	 * to the linked discussions and end offset of the link 
	 * The links in the map are not overlapping
	 */
	private TreeMap<Integer, LinkInfo> links;
	/** The entire text of the parahraph, unescaped */
	private String text;
	/** Pages containing conflicts which should be created upon export */
	private LinkedList<DiscConflictPage> discConfs; 
	
	/**
	 * Given the text of the paragraph, creates a LinkedParahraph
	 * @param text the text of the paragraph
	 */
	public LinkedParagraph(SourcePage src, String text)
	{
		this.source = src;
		this.links = new TreeMap<Integer, LinkInfo>();
		this.text = text;
		this.discConfs = new LinkedList<DiscConflictPage>();
	}
	
	/**
	 * Adds a link to this Paragraph
	 * @param e the excerption to link
	 * @param l the whole link (used for metadata)
	 * @param disc the target discussion page
	 */
	public void addLink(Excerption e, Link l, DiscussionPage disc)
	{
		int start = e.getStartPos();
		int end = e.getEndPos();
		
		SortedMap<Integer, LinkInfo> lower = links.subMap(0, start);
		
		// there's a link that started before us :( 
		if (!lower.isEmpty() && links.get(lower.lastKey()).getEnd() > start)
		{
			// break it to two parts. We will later add ourselves to the second part
			splitLink(lower.lastKey(), start);
		}
		
		// there's a link that starts right on our spot
		if (links.containsKey(start))
		{
			// the link ends after us
			if (links.get(start).getEnd() > end)
			{
				splitLink(start, end+1);
				// we don't stop here
			}
			
			// the link ends before us
			if (links.get(start).getEnd() < end)
			{
				// add the start of the link to the existing link info
				links.get(start).add(disc, e.getText(), l);
				// add the rest of the link
				int newStart = links.get(start).getEnd() + 1;
				Excerption eNew = new Excerption(e.getXPath(), e.getText(), newStart, end);
				addLink(eNew, l, disc);
				return;
			}
			
			// the link ends exactly with us
			if (links.get(start).getEnd() == end)
			{
				// add the link to the existing link info
				links.get(start).add(disc, e.getText(), l);
				return;
			}
		}
		
		int currStart = start;
		SortedMap<Integer, LinkInfo> higher = links.subMap(currStart+1, end);
		
		// there is one or more links starting in our area
		while (!higher.isEmpty() && higher.firstKey() < end)
		{
			// add ourselves before that link
			LinkInfo info = new LinkInfo(higher.firstKey(), disc, e.getText(), l);
			links.put(currStart, info);
			
			// if we do not contain that evil link, break it
			if (links.get(higher.firstKey()).getEnd() > end)
				splitLink(higher.firstKey(), end);
			
			// add ourselves to that link
			links.get(higher.firstKey()).add(disc, e.getText(), l);
			
			// repeat the process from the existing link's end
			currStart = links.get(higher.firstKey()).getEnd();
			if (currStart + 1 > end)
				break;
			
			higher = links.subMap(currStart+1, end);
		}
		
		assert(currStart <= end);
		
		// in normal cases, this is the code that gets executed eventaully
		if (currStart != end)
		{
			LinkInfo info = new LinkInfo(end, disc, e.getText(), l);
			links.put(currStart, info);
		}
	}
	
	/**
	 * Splits a link in the links TreeMap to two parts
	 * 
	 * @param start the offset in which the link currently starts
	 * @param end the split point. The first point of the second link after the split
	 */
	private void splitLink(int start, int end)
	{
		assert(links.get(start).getEnd() > end);
		
		LinkInfo oldInfo = links.get(start);
		LinkInfo newInfo = oldInfo.clone();
		
		newInfo.setEnd( oldInfo.getEnd() );
		oldInfo.setEnd( end );

		links.put(end, newInfo);
	}
	
	public String getHTMLText() {
		StringBuffer s = new StringBuffer( text.length() * 2 );
		
		s.append("<p>");
		
		int currOffset = 0;
		
		for ( Entry<Integer, LinkInfo> e : links.entrySet() )
		{
			// there is some unlinked text before the link
			if (e.getKey() != currOffset)
			{
				String unlinkedText = text.substring(currOffset, e.getKey());
				s.append( GeneralFunctions.xmlEscape(unlinkedText) );
			}
				
			LinkInfo info = e.getValue();
			HTMLPage targetPage = null;

			// Add a conflict page if needed
			if (info.size() > 1)
			{
				DiscConflictPage confPage = new DiscConflictPage(info);
				discConfs.add( confPage );
				targetPage = confPage;
			}
			else
			{
				targetPage = info.getOnlyPage();
			}
			
			String tooltip = GeneralFunctions.xmlEscape(info.getNames());
			String type = info.getType(); // the class (type) of the link
			
			s.append( "<a href=\"");
			
			s.append( source.getPathTo(targetPage) );
			s.append( "\" title=\"" + tooltip + "\" ");
			s.append( " class=\"" + type + "\" >");
			
			String linkedText = text.substring(e.getKey(), info.getEnd());
			s.append( GeneralFunctions.xmlEscape(linkedText) );
			
			s.append( "</a>" );
			
			currOffset = info.getEnd();
		}
		
		// add the rest of the text
		if (currOffset != text.length())
		{
			String unlinkedText = text.substring(currOffset, text.length());
			s.append( "\t" + GeneralFunctions.xmlEscape(unlinkedText) );
		}
		s.append("</p>\n");
		
		return s.toString();
	}

	/**
	 * Returns null (no id)
	 */
	public String getID() {
		return null;
	}
	
	/** Returns pages related to this element which should be generated upon export */
	public LinkedList<DiscConflictPage> getUngeneratedPages() {
		return discConfs;
	}

}