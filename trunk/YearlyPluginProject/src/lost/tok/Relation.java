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

import java.util.HashMap;
import java.util.Set;

import org.dom4j.Element;

public class Relation {
	private static final HashMap<String, String> relTypes = new HashMap<String, String>();
	private static final HashMap<String, String> relTypesBack = new HashMap<String, String>();
	static {
		relTypes.put("disagree", Messages.getString("Discussion.1")); //$NON-NLS-1$ //$NON-NLS-2$
		relTypes.put("explain", Messages.getString("Discussion.2")); //$NON-NLS-1$ //$NON-NLS-2$
		
		for (String s : relTypes.keySet())
			relTypesBack.put(relTypes.get(s), s);
	}
	
	private static final String ELM_TYPE = "type"; //$NON-NLS-1$
	private static final String ELM_COMMENT = "comment"; //$NON-NLS-1$
	private static final String ELM_ID2 = "id2"; //$NON-NLS-1$
	private static final String ELM_ID1 = "id1"; //$NON-NLS-1$
	Object element1;
	Object element2;
	
	String comment;
	String typeXML;
	
	Discussion d;
	
	/**
	 * Creates new relation object of the given discussion from xml element
	 * @param d - discussion that contains the relation
	 * @param e - XML data
	 */
	public Relation(Discussion d, Element e) {
		this(d,
				Integer.parseInt(e.element(ELM_ID1).getText()),
				Integer.parseInt(e.element(ELM_ID2).getText()),
				e.element(ELM_COMMENT).getText(),
				relTypes.get(e.element(ELM_TYPE).getText()));
	}

	/**
	 * Creates new relation object of the given discussion
	 * @param d - discussion that contains the relation
	 * @param id1 - id of the first entity of the relation
	 * @param id2 - id of the second entity of the relation
	 * @param comment - comment of the relation
	 * @param type - type of the relation
	 */
	public Relation(Discussion d, Integer id1, Integer id2, String comment, String type) {
		super();
		this.d = d;
		this.element1 = getElement(id1);
		this.element2 = getElement(id2);
		this.comment = comment;
		this.typeXML = relTypesBack.get(type);
	}

	/**
	 * Fills the givven XML element with the xml data of the relation
	 * @param e - element to fill
	 */
	public void fillElement(Element e) {
		e.addElement(ELM_ID1).addText(Integer.toString(getId(element1)));
		e.addElement(ELM_ID2).addText(Integer.toString(getId(element2)));
		e.addElement(ELM_COMMENT).addText(comment);
		e.addElement(ELM_TYPE).addText(typeXML);
	}

	private int getId(Object element) {
		if (element instanceof Opinion) {
			Opinion o = (Opinion) element;
			return o.getId();
		}
		if (element instanceof Quote) {
			Quote q = (Quote) element;
			return q.getID();
		}
		return 0;
	}

	/** Return first entity of the relation */
	public Object getFirst() {
		return element1;
	}
	
	/** Return name of first entity of the relation */
	public String getFirstName() {
		return getText(element1);
	}

	/** Return second entity of the relation */
	public Object getSecond() {
		return element2;
	}
	
	/** Return name of second entity of the relation */
	public String getSecondName() {
		return getText(element2);
	}
	
	private String getText(Object element) {
		if (element instanceof Opinion) {
			Opinion o = (Opinion) element;
			return o.getName();
		}
		if (element instanceof Quote) {
			Quote q = (Quote) element;
			return q.getText();
		}
		return ""; //$NON-NLS-1$
	}
	
	private Object getElement(int id) {
		try {
			return d.getOpinion(id);
		} catch (Exception e) {
		}
		try {
			return d.getQuote(id);
		} catch (Exception e) {
		}
		return null;
	}
	
	/** Returns type of the relation */
	public String getRelationType() {
		return relTypes.get(typeXML);
	}

	/** Returns comment of the relation */
	public String getComment() {
		return comment;
	}

	/** Returns all known relation types */
	public static String[] getRelTypes() {
		Set<String> rels = relTypesBack.keySet();
		return rels.toArray(new String[rels.size()]);
	}
	
	
}
