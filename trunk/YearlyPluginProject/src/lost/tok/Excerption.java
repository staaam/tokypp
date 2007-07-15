/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

package lost.tok;

import java.util.Iterator;
import java.util.List;

import lost.tok.GeneralFunctions.Properties;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * A piece of text taken from a source. Includes all the information needed to
 * generate the xml entity (as defined in the Spec.)
 * 
 * @author Team LOST
 */
public class Excerption extends Properties {
	/**
	 * The offset of the end in the chapter of the excerptions (the first char not
	 * included in the excerption)
	 */
	private Integer endPos;

	/** The path in the src file of the excerption */
	private String pathInSourceFile;

	/** The offset of the excerption's start in the chapter of the excerption (inclusive) */
	private Integer startPos;

	/** The excerption's text */
	private String text;

	/**
	 * Builds an excerption from an XML element
	 * 
	 * @param excerption
	 *            the XML element describing the excerption
	 */
	public Excerption(Element excerption) {
		this(excerption.element("sourceFilePath").getText(), null, Integer //$NON-NLS-1$
				.valueOf(excerption.element("startPos").getText()), Integer //$NON-NLS-1$
				.valueOf(excerption.element("endPos").getText())); //$NON-NLS-1$
	}

	/**
	 * Builds an excerption from relevant arguments
	 * 
	 * @param path
	 *            The excerption path of the chapter in the xml document
	 * @param text
	 *            The text of the excerption
	 * @param start
	 *            The offset in the document of the excerption (inclusive)
	 * @param end
	 *            The offset in the docuemnt of the excerption's end (exclusive)
	 */
	public Excerption(String path, String text, int start, int end) {
		pathInSourceFile = path;
		startPos = start;
		endPos = end;
		this.text = text;
	}

	/**
	 * Returns an XML element describing the excerption
	 * 
	 * @return an XML element describing the excerption
	 */
	public Element toXML() {
		Element e = DocumentHelper.createElement("excerption"); //$NON-NLS-1$
		e.addElement("sourceFilePath").addText(pathInSourceFile); //$NON-NLS-1$
		e.addElement("startPos").addText(startPos.toString()); //$NON-NLS-1$
		e.addElement("endPos").addText(endPos.toString()); //$NON-NLS-1$
		return e;
	}

	/**
	 * Returns the offset in the document after the excerption
	 * 
	 * @return the offset in the document after the excerption
	 */
	public int getEndPos() {
		return endPos;
	}

	/** Returns the path of the excerption's source */
	public String getEPath() {
		return pathInSourceFile;
	}

	/** Returns the offset of the excerptions start */
	public int getStartPos() {
		return startPos;
	}

	/** Returns the text of the excerption */
	public String getText() {
		if (text == null)
		{
			System.out.println("ERROR: getText was called and text was null");
			assert(false);
		}
		return text;
	}

	public int hashCode() {
		return (pathInSourceFile + "/" + startPos + "-" + endPos).hashCode(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/** Sets the text of the excerption to the given text */
	public void setText(String text) {
		this.text = text;
	}
	
	/** Sets the excerption's text field according to the given source */
	public void loadText(Source src)
	{
		SourceDocument sd = new SourceDocument();
		sd.set(src);
		loadText(sd);
	}

	/** Sets the excerption's text field according to the given source document */
	public void loadText(SourceDocument srcDoc)
	{
		ChapterText c = srcDoc.getChapterTextFromEPath(pathInSourceFile);
		text = c.getText().substring(startPos, endPos);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Excerption))
			return false;
		
		Excerption e = (Excerption) o;
		if (e.getText().equals(getText())&&
			e.getStartPos() == getStartPos() &&
			e.getEndPos() == getEndPos())
			return true;
		
		return false;
	}

	public String toString() {
		return pathInSourceFile + "[" + startPos + ":" + endPos + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	/**
	 * Concatinates a bunch of excerptions into a long string, seperated by ellipsis
	 * @param excerpts the excerptions to join
	 * @return a string containing the text of all the excerptions
	 */
	static public String concat(List<Excerption> excerpts) 
	{
		StringBuffer s = new StringBuffer();
		Iterator<Excerption> i = excerpts.iterator();
		while (i.hasNext()) {
			Excerption e = i.next();
			s.append( e.getText() );
			if (i.hasNext()) {
				s.append(" [...] "); //$NON-NLS-1$
			}
		}
		return s.toString();
	}
}
