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

import java.util.LinkedList;

import lost.tok.sourceDocument.SourceDocument;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SubLink {

	private Source linkedSource;
	private LinkedList<Excerption>  exList;
	private String text;
	
	public SubLink(Source linkedSource, LinkedList<Excerption>  exList){
		this.linkedSource = linkedSource;
		this.exList = exList;
	}
	
	/** Creates a sublink from an XML sublink element */
	public SubLink(ToK tok, Element sublinkElm)
	{
		exList = new LinkedList<Excerption>(); 
		
		String srcName = DocumentHelper.createXPath("sourceFile").selectSingleNode(sublinkElm).getText(); //$NON-NLS-1$
		this.linkedSource = new Source(tok, srcName);
		
		SourceDocument srcDoc = new SourceDocument();
		srcDoc.set(linkedSource);
		
		for (Object exElmObj : DocumentHelper.createXPath("excerption").selectNodes(sublinkElm) ) //$NON-NLS-1$
		{
			Element exElm = (Element) exElmObj;
			Excerption excerption = new Excerption(exElm);
			excerption.loadText(srcDoc);
			exList.add ( excerption );
		}
	}
	
	public Source getLinkedSource() {
		return linkedSource;
	}
	
	public void setLinkedSource(Source s){
		this.linkedSource = s;
	}
	
//	public Excerption[] getExcerption() {
//		return exp;
//	}
//
	public LinkedList<Excerption> getExcerption() {
		return exList;
	}
	
	public String getText() {
		if (text == null) {
			text = Excerption.concat(exList);
		}
		return text;
	}
	
//	public void setExcerption(Excerption[] e){
//		this.exp = e;
//	}
	
	public void setExcerption(LinkedList<Excerption> e){
		this.exList = e;
	}
}
