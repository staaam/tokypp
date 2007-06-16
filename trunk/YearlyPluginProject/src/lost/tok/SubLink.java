package lost.tok;

public class SubLink {

	private Source linkedSource;
	private Excerption[] exp;
	
	public SubLink(Source linkedSource, Excerption[]exp){
		this.linkedSource = linkedSource;
		this.exp = exp;
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
