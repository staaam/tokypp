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
		// the biggest heading allowed is h2. h1 is reserved for the page's title
		String hTitle = "h" + (depth + 1); 
		String id = (getID() == null) ? ("") : ("id=#" + getID());
		return "<" + hTitle + " " + id + ">" + title + "</" + hTitle + ">\n";
	}
	
	public String getID() 
	{
		// TODO(Shay): Change this and add a table of content
		return null;		
	}
}
