package lost.tok.html.srcElem;

public class Heading implements SrcElem
{
	/** The Heading's text */
	private String title;
	/** The path in the xml source document of this heading */
	@SuppressWarnings("unused")
	private String xPath;
	
	/**
	 * Creates a new heading
	 * @param title The Heading's text
	 * @param xPath The path in the xml source document of this heading
	 */
	Heading(String title, String xPath)
	{
		this.title = title;
		this.xPath = xPath;
	}
	
	public String getHTMLText()
	{
		// TODO(Shay): Find how deep is this title
		// TODO(Shay): Set the h's level and id accordingly
		
		int hLevel = 1;
		String hTitle = "h" + String.valueOf(hLevel); 
		String id = (getID() == null) ? ("") : ("id=#" + getID());
		return "<" + hTitle + " " + id + ">" + title + "</" + hTitle + ">";
	}
	
	public String getID() 
	{
		// TODO(Shay): Change this and add a table of content
		return null;		
	}
}
