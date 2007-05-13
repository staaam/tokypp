package lost.tok;


import org.dom4j.Element;

public class DiscussionLink {
	
	/**
	 * The types of the links, as strings displayable to the user
	 * The order of the strings should be the same as in the linkXMLTypes array
	 */
	public static final String[] linkDisplayNames = { Messages.getString("Link.General"), Messages.getString("Link.Difficulty"), //$NON-NLS-1$ //$NON-NLS-2$
			Messages.getString("Link.Interpretation") }; //$NON-NLS-1$
	
	/**
	 * The types of the links, as the xml scheme defines
	 * The order of the strings should be the same as in the linkDisplayNames array
	 */
	public static final String[] linkXMLTypes = {"general", "difficulty", "interpretation"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	private String discussion;

	private String linkSubject;

	private String type;

	public DiscussionLink(Element e) {
		this(e.element("discussionFile").getText(), e.element("type") //$NON-NLS-1$ //$NON-NLS-2$
				.getText(), e.element("linkSubject").getText()); //$NON-NLS-1$
	}

	public DiscussionLink(String discussionFile, String type,
			String linkSubject) {
		discussion = Discussion.getNameFromFile(discussionFile);
		this.type = type;
		this.linkSubject = linkSubject;
	}

	/** Returns the name of the discussion file */
	public String getDiscussionFile() {
		return discussion;
	}

	public String getLinkSubject() {
		return linkSubject;
	}

	/**
	 * Returns the type of the discussion, as written in the xml
	 */
	public String getXMLType() {
		return type;
	}
	
	/**
	 * Returns the type of the discussion, as a strign displayable to the user
	 */
	public String getDisplayType() {
		for (int i=0; i < linkXMLTypes.length; i++)
		{
			if (linkXMLTypes[i].equals(type))
				return linkDisplayNames[i];
		}
		// if not found, perhaps this is a newly defined type of link
		return type;
	}

}