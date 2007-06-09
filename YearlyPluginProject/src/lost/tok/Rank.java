package lost.tok;

import org.dom4j.Element;

public class Rank {
	
	/**********************************************************************
	 * M E M B E R S
	 **********************************************************************/
	
	String name;
	int id;

	
	/**********************************************************************
	 * C ' T O R S
	 **********************************************************************/
	
	public Rank(String rankName, int rankId) {
		name = rankName;
		id = rankId;
	}

	public Rank(Element e) {
		this(e.element("name").getText(), Integer.valueOf(e.element("id").getText()));
	}

	
	/**********************************************************************
	 * P R O P E R T I E S
	 **********************************************************************/
	
	public int getId() {
		return id;
	}

	public void setId(int rankId) {
		id = rankId;
	}

	public String getName() {
		return name;
	}

	public void setName(String rankName) {
		name = rankName;
	}
}

