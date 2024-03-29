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

package lost.tok;

import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.core.resources.IFile;

//this class represents a link between the discussion and the source
public class Link {

	public static final String TYPE_INTERPRETATION = "interpretation"; //$NON-NLS-1$
	public static final String TYPE_DIFFICULTY = "difficulty"; //$NON-NLS-1$
	public static final String TYPE_GENERAL = "general"; //$NON-NLS-1$
	
	/**
	 * The types of the links, as strings displayable to the user. The order of
	 * the strings should be the same as in the linkXMLTypes array
	 */
	public static final String[] linkDisplayNames = {
			Messages.getString("Link.General"), //$NON-NLS-1$
			Messages.getString("Link.Difficulty"), //$NON-NLS-1$
			Messages.getString("Link.Interpretation"), //$NON-NLS-1$
	};

	/**
	 * The types of the links, as the xml scheme defines. The order of the
	 * strings should be the same as in the linkDisplayNames array
	 */
	public static final String[] linkXMLTypes = {
			TYPE_GENERAL,
			TYPE_DIFFICULTY,
			TYPE_INTERPRETATION,
	};

	
	private String linkTypeXML;
	private Discussion linkedDiscussion;
	private List<SubLink> subLinkList;
	private IFile linkFile;
	private String subject;
	
	
//	public Link(Source linkedSource,Discussion linkedDiscussion,
//					String linkType,IFile linkFile, Excerption[] exp, String subject){
	public Link(Discussion linkedDiscussion, String linkTypeXML,
									IFile linkFile, String subject){
		
		subLinkList = new LinkedList<SubLink>();
		
		this.linkedDiscussion=linkedDiscussion;
		this.linkTypeXML = linkTypeXML;
		this.linkFile = linkFile;
		this.subject = subject;
		
		//linkDiscussionRoot();
	}
	
	/**
	 * Loads a discussion link from an XML element
	 * @param linkedDiscussion the discussion linked by this link
	 * @param linkElm the XML element describing this link
	 */
	public Link(Discussion linkedDiscussion, Element linkElm)
	{
		subLinkList = new LinkedList<SubLink>();
		
		this.linkedDiscussion=linkedDiscussion;
		this.linkFile = linkedDiscussion.getMyToK().getLinkFile();
		
		Node typeNode = DocumentHelper.createXPath("type").selectSingleNode(linkElm);  //$NON-NLS-1$
		Node subjNode = DocumentHelper.createXPath("linkSubject").selectSingleNode(linkElm);  //$NON-NLS-1$
		this.linkTypeXML = typeNode.getText();
		this.subject = subjNode.getText();
		
		List sublinkElms = DocumentHelper.createXPath("sublink").selectNodes(linkElm); //$NON-NLS-1$
		for (Object oSublinkElm : sublinkElms )
		{
			Element sublinkElm = (Element) oSublinkElm;
			
			subLinkList.add( new SubLink(linkedDiscussion.getMyToK(), sublinkElm) );
		}
		
	}

	
	/**
	 * Links an existing discussion to a segment in the root of the ToK project
	 */
	public void linkDiscussionRoot(Source linkedSource, LinkedList<Excerption> exp) {
		
		SubLink sl = new SubLink(linkedSource,exp);
		subLinkList.add(sl);
		
		String discFileName = linkedDiscussion.getDiscFileName();

		// Open the Links file
		Document doc = GeneralFunctions.readFromXML(linkFile);

		Node link = doc.selectSingleNode("//link/discussionFile[text()=\"" //$NON-NLS-1$
				+ discFileName + "\"]"); //$NON-NLS-1$
		Element newLink = null;
		if (link != null) {
			newLink = link.getParent();
		} else {

			Element links = doc.getRootElement();
			newLink = links.addElement("link"); //$NON-NLS-1$
			newLink.addElement("discussionFile").addText(discFileName); //$NON-NLS-1$
			newLink.addElement("type").addText(linkTypeXML); //$NON-NLS-1$
			newLink.addElement("linkSubject").addText(subject); //$NON-NLS-1$
		}

		Element subLink = newLink.addElement("sublink"); //$NON-NLS-1$
		subLink.addElement("sourceFile").addText(linkedSource.toString()); //$NON-NLS-1$
		for (Excerption element : exp) {

			subLink.add(element.toXML());

		}

		GeneralFunctions.writeToXml(linkFile, doc);
	}


	/** Returns the type of the link, as written in the xml */
	public String getLinkType() {
		return linkTypeXML;
	}
	
	public void setLinkType(String lt) {
		this.linkTypeXML=lt;
	}
	
	public Discussion getLinkedDiscussion() {
		return linkedDiscussion;
	}
	
	public void setLinkedDiscussion(Discussion d){
		this.linkedDiscussion = d;
	}
	
	public IFile getLinkFile() {
		return linkFile;
	}
	
	public void setLinkFile(IFile lf){
		this.linkFile = lf;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String s){
		this.subject = s;
	}



	public String getDisplayLinkType() {
		for (int i = 0; i < linkXMLTypes.length; i++) {
			if (linkXMLTypes[i].equals(linkTypeXML))
				return linkDisplayNames[i];
		}
		// if not found, perhaps this is a newly defined type of link
		return linkTypeXML;
	}

	public List<SubLink> getSubLinkList() {
		return subLinkList;
	}
	
}
