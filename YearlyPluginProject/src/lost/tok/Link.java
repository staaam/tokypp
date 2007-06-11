package lost.tok;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.core.resources.IFile;

//this class represents a link between the discussion and the source
public class Link {

	private String linkType;
	private Discussion linkedDiscussion;
	private Source linkedSource;
	private IFile linkFile;
	private Excerption[] exp;
	private String subject;
	
	
	public Link(Source linkedSource,Discussion linkedDiscussion,
					String linkType,IFile linkFile, Excerption[] exp, String subject){
		
		this.linkedSource=linkedSource;
		this.linkedDiscussion=linkedDiscussion;
		this.linkType = linkType;
		this.linkFile = linkFile;
		
		linkDiscussionRoot();
	}
	
	/**
	 * Links an existing discussion to a segment in the root of the ToK project
	 */
	public void linkDiscussionRoot() {

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
			newLink.addElement("type").addText(linkType); //$NON-NLS-1$
			newLink.addElement("linkSubject").addText(subject); //$NON-NLS-1$
		}

		Element subLink = newLink.addElement("sublink"); //$NON-NLS-1$

		for (Excerption element : exp) {

			subLink.addElement("sourceFile").addText(linkedSource.toString()); //$NON-NLS-1$
			subLink.add(element.toXML());

		}

		GeneralFunctions.writeToXml(linkFile, doc);
	}

	public Source getLinkedSource() {
		return linkedSource;
	}
	
	public void setLinkedSource(Source s){
		this.linkedSource = s;
	}

	public String getLinkType() {
		return linkType;
	}
	
	public void setLinkType(String lt) {
		this.linkType=lt;
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
	
	public Excerption[] getExcerption() {
		return exp;
	}
	
	public void setExcerption(Excerption[] e){
		this.exp = e;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String s){
		this.subject = s;
	}
	
}
