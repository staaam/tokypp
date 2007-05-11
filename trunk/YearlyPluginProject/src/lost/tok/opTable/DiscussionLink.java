package lost.tok.opTable;

import lost.tok.Discussion;

import org.dom4j.Element;

public class DiscussionLink {

	public String discussion;

	private String linkSubject;

	private String type;

	public DiscussionLink(Element e) {
		this(e.element("discussionFile").getText(), e.element("type")
				.getText(), e.element("linkSubject").getText());
	}

	public DiscussionLink(String discussionFile, String type,
			String linkSubject) {
		discussion = Discussion.getNameFromFile(discussionFile);
		this.type = type;
		this.linkSubject = linkSubject;
	}

	public String getDiscussionFile() {
		return discussion;
	}

	public String getLinkSubject() {
		return linkSubject;
	}

	public String getType() {
		return type;
	}

}