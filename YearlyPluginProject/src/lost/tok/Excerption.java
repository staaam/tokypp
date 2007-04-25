package lost.tok;

import lost.tok.GeneralFunctions.Properties;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * A piece of text taken from a source. Includes all the information needed to
 * generate the xml entity (as defined in the Spec.)
 * 
 * @author Team LOST
 */
public class Excerption extends Properties {
	/** The offset in the document of the excerptions end (the first char not included in the excerption) */
	Integer endPos;
	/** The path to the src file of the excerption */
	String sourceFilePath;
	/** The offset in the document of the excerption (inclusive) */
	Integer startPos;
	/** The excerption's text */
	String text;

	/**
	 * Builds an excerption from an XML element
	 * @param excerption the XML element describing the excerption
	 */
	public Excerption(Element excerption) {
		this(excerption.element("sourceFilePath").getText(), null, Integer
				.valueOf(excerption.element("startPos").getText()), Integer
				.valueOf(excerption.element("endPos").getText()));
	}

	/**
	 * Builds an excerption from relevant arguments
	 * @param path The path to the src file of the excerption
	 * @param text The text of the excerption
	 * @param start The offset in the document of the excerption (inclusive)
	 * @param end The offset in the docuemnt of the excerption's end (exclusive)
	 */
	public Excerption(String path, String text, int start, int end) {
		sourceFilePath = path;
		startPos = start;
		endPos = end;
		this.text = text;
	}

	/**
	 * Returns an XML element describing the excerption
	 * @return an XML element describing the excerption
	 */
	public Element toXML() {
		Element e = DocumentHelper.createElement("excerption");
		e.addElement("sourceFilePath").addText(sourceFilePath);
		e.addElement("startPos").addText(startPos.toString());
		e.addElement("endPos").addText(endPos.toString());
		return e;
	}

	/**
	 * Returns the offset in the document after the excerption
	 * @return the offset in the document after the excerption
	 */
	public int getEndPos() {
		return endPos;
	}

	/** Returns the path of the excerption's source */
	public String getSourceFilePath() {
		return sourceFilePath;
	}
	/** Returns the offset of the excerptions start */
	public int getStartPos() {
		return startPos;
	}
	/** Returns the text of the excerption */
	public String getText() {
		return text;
	}

	public int hashCode() {
		return (sourceFilePath + "/" + startPos + "-" + endPos).hashCode();
	}

	/** Sets the text of the excerption to the given text */
	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return sourceFilePath + "[" + startPos + ":" + endPos + "]";
	}
}
