package lost.tok;

public class Link {

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
}
