package lost.tok.html.srcElem;

/**
 * An element in a source document
 * Could be either a heading or a (possibly linked) paragraph
 * @author Team Lost
 */
public interface SrcElem 
{
	/**
	 * Returns the text which should be embeded in the html document
	 */
	public String getHTMLText();
	/**
	 * Returns the id of this element, or null if there's no id
	 */
	public String getID();
}
