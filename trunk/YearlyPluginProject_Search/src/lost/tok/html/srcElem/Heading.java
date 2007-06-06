package lost.tok.html.srcElem;

public class Heading implements SrcElem
{
	
	/** The Heading's text */
	private String title;
	/** The depth of the heading (i.e. h1,h2,h3...) */
	private int depth;
	/** The path in the xml source document of this heading */
	@SuppressWarnings("unused")
	private String xPath;
	
	/**
	 * Creates a new heading
	 * @param title The Heading's text
	 * @param depth The depth of the heading (i.e. h1,h2,h3...)
	 * @param xPath The path in the xml source document of this heading
	 */
	public Heading(String title, int depth, String xPath)
	{
		this.title = title;
		this.depth = depth;
		this.xPath = xPath;
	}
	
	public String getHTMLText()
	{
		// TODO(Shay): Find how deep is this title
		// TODO(Shay): Set the h's level and id accordingly
		
		String hTitle = "h" + depth; 
		String id = (getID() == null) ? ("") : ("id=#" + getID());
		return "<" + hTitle + " " + id + ">" + title + "</" + hTitle + ">";
	}
	
	public String getID() 
	{
		// TODO(Shay): Change this and add a table of content
		return null;		
	}
}
