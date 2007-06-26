package lost.tok.html.srcElem;

public class Heading implements SrcElem
{
	
	/** The Heading's text */
	private String title;
	/** The depth of the heading (i.e. h1,h2,h3...) */
	private int depth;
	
	/**
	 * Creates a new heading
	 * @param title The Heading's text
	 * @param depth The depth of the heading (i.e. h1,h2,h3...)
	 */
	public Heading(String title, int depth)
	{
		this.title = title;
		this.depth = depth;
	}
	
	public String getHTMLText()
	{		
		// the biggest heading allowed is h2. h1 is reserved for the page's title
		String hTitle = "h" + (depth + 1);  //$NON-NLS-1$
		String id = (getID() == null) ? ("") : ("id=#" + getID()); //$NON-NLS-1$ //$NON-NLS-2$
		return "<" + hTitle + " " + id + ">" + title + "</" + hTitle + ">\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}
	
	public String getID() 
	{
		// TODO(Shay, low): Change this and add a table of content
		return null;		
	}
}
