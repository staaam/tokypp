package lost.tok;

import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.core.resources.IFile;

//this class represents a link between the discussion and the source
public class Link {

	/**
	 * The types of the links, as strings displayable to the user. The order of
	 * the strings should be the same as in the linkXMLTypes array
	 */
	public static final String[] linkDisplayNames = {
			Messages.getString("Link.General"), Messages.getString("Link.Difficulty"), //$NON-NLS-1$ //$NON-NLS-2$
			Messages.getString("Link.Interpretation") }; //$NON-NLS-1$

	/**
	 * The types of the links, as the xml scheme defines. The order of the
	 * strings should be the same as in the linkDisplayNames array
	 */
	public static final String[] linkXMLTypes = {
			"general", "difficulty", "interpretation" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	
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
	 * Links an existing discussion to a segment in the root of the ToK project
	 */
	public void linkDiscussionRoot(Source linkedSource, Excerption[]exp) {
		
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

		for (Excerption element : exp) {

			subLink.addElement("sourceFile").addText(linkedSource.toString()); //$NON-NLS-1$
			subLink.add(element.toXML());

		}

		GeneralFunctions.writeToXml(linkFile, doc);
	}


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
