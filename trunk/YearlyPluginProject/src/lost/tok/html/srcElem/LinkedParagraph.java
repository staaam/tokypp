package lost.tok.html.srcElem;

import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import lost.tok.GeneralFunctions;
import lost.tok.html.DiscConflictPage;
import lost.tok.html.DiscussionPage;
import lost.tok.html.HTMLPage;
import lost.tok.html.SourcePage;

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
	
	public void addLink(int start, int end, DiscussionPage disc)
	{
		SortedMap<Integer, LinkInfo> lower = links.subMap(0, start);
		
		// there's a link that started before us :( 
		if (!lower.isEmpty() && links.get(lower.lastKey()).end > start)
		{
			// break it to two parts. We will later add ourselves to the second part
			splitLink(lower.lastKey(), start);
		}
		
		// there's a link that starts right on our spot
		if (links.containsKey(start))
		{
			// the link ends after us
			if (links.get(start).end > end)
			{
				splitLink(start, end+1);
				// we don't stop here
			}
			
			// the link ends before us
			if (links.get(start).end < end)
			{
				// add the start of the link to the existing link info
				links.get(start).discs.add(disc);
				// add the rest of the link
				addLink(lower.get(start).end + 1, end, disc);
				return;
			}
			
			// the link ends exactly with us
			if (links.get(start).end == end)
			{
				// add the link to the existing link info
				links.get(start).discs.add(disc);
				return;
			}
		}
		
		int currStart = start;
		SortedMap<Integer, LinkInfo> higher = links.subMap(currStart+1, end);
		
		// there is one or more links starting in our area
		while (!higher.isEmpty() && higher.firstKey() < end)
		{
			// add ourselves before that link
			LinkInfo info = new LinkInfo(higher.firstKey(), disc);
			links.put(currStart, info);
			
			// if we do not contain that evil link, break it
			if (links.get(higher.firstKey()).end > end)
				splitLink(higher.firstKey(), end);
			
			// add ourselves to that link
			links.get(higher.firstKey()).discs.add(disc);
			
			// repeat the process from the existing link's end
			currStart = links.get(higher.firstKey()).end;
			higher = links.subMap(currStart+1, end);
		}
		
		assert(currStart <= end);
		
		// in normal cases, this is the code that gets executed eventaully
		if (currStart != end)
		{
			LinkInfo info = new LinkInfo(end, disc);
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
		assert(links.get(start).end > end);
		
		LinkInfo oldInfo = links.get(start);
		LinkInfo newInfo = oldInfo.clone();
		
		newInfo.end = oldInfo.end;
		oldInfo.end = end;
		
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
				s.append( "\t" + GeneralFunctions.xmlEscape(unlinkedText) + "\n");
			}
				
			LinkInfo info = e.getValue();
			HTMLPage targetPage = null;
			// TODO(Shay): add tooltips. Do not forget to escape them!
			String tooltip = "";
			// Add a conflict page if needed
			if (info.discs.size() > 1)
			{
				DiscConflictPage confPage = new DiscConflictPage(info.discs);
				discConfs.add( confPage );
				targetPage = confPage;
			}
			else
			{
				targetPage = info.discs.getFirst();
			}
			
			// TODO(Shay): Add styles to the links using span
			s.append( "\t<a href=\"");
			
			s.append( source.getPathTo(targetPage) );
			s.append( "\" title=\"" + tooltip + "\" >");
			
			String linkedText = text.substring(e.getKey(), info.end);
			s.append( GeneralFunctions.xmlEscape(linkedText) );
			
			s.append( "</a>\n" );			
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
	
	
	/* ************************************************************ */
	/*                        Private Classes                       */
	/* ************************************************************ */
	
	
	/**
	 * A small struct defining where a link ends and what discussions it includes
	 * @author Team Lost
	 */
	private class LinkInfo 
	{
		/** The offset of the link's end (exclusive) */
		public int end;
		/** The linked discussions, 1 or more */
		public LinkedList<DiscussionPage> discs;
		
		public LinkInfo(int end)
		{
			this.end = end;
			this.discs = new LinkedList<DiscussionPage>();
		}
		
		public LinkInfo(int end, DiscussionPage disc)
		{
			this(end);
			this.discs.add(disc);
		}
		
		/** Retruns a 2-lvl copy of this (Discussions are not cloned) */
		public LinkInfo clone()
		{
			LinkInfo l = new LinkInfo(end);
			for (DiscussionPage d : discs)
				l.discs.add(d);
			return l;
		}
	}

	/** Returns pages related to this element which should be generated upon export */
	public LinkedList<DiscConflictPage> getUngeneratedPages() {
		return discConfs;
	}
	

}