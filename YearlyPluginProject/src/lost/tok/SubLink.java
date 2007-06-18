package lost.tok;

import java.util.LinkedList;

import lost.tok.sourceDocument.SourceDocument;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SubLink {

	private Source linkedSource;
	private Excerption[] exp;
	
	public SubLink(Source linkedSource, Excerption[]exp){
		this.linkedSource = linkedSource;
		this.exp = exp;
	}
	
	/** Creates a sublink from an XML sublink element */
	public SubLink(ToK tok, Element sublinkElm)
	{
		LinkedList<Excerption> exList = new LinkedList<Excerption>(); 
		
		String srcName = DocumentHelper.createXPath("sourceFile").selectSingleNode(sublinkElm).getText();
		this.linkedSource = new Source(tok, srcName);
		
		SourceDocument srcDoc = new SourceDocument();
		srcDoc.set(linkedSource);
		
		for (Object exElmObj : DocumentHelper.createXPath("excerption").selectNodes(sublinkElm) )
		{
			Element exElm = (Element) exElmObj;
			Excerption excerption = new Excerption(exElm);
			excerption.loadText(srcDoc);
			exList.add ( excerption );
		}
		this.exp = exList.toArray( new Excerption [ exList.size() ] );
	}
	
	public Source getLinkedSource() {
		return linkedSource;
	}
	
	public void setLinkedSource(Source s){
		this.linkedSource = s;
	}
	
	public Excerption[] getExcerption() {
		return exp;
	}
	
	public void setExcerption(Excerption[] e){
		this.exp = e;
	}
}
