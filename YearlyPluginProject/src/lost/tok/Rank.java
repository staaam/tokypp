package lost.tok;

import org.dom4j.Element;

/**
 * Contains all data related to authors rank
 * @author evgeni
 *
 */
public class Rank {

	String name;
	int id;

	/**
	 * Rank class construcor
	 * @param rankName
	 * @param rankId
	 */
	public Rank(String rankName, int rankId) {
		name = rankName;
		id = rankId;
	}

	/**
	 * Rank class construcor
	 * @param rankName
	 * @param rankId
	 */
	public Rank(Element e) {
		this(e.element("name").getText(), Integer.valueOf(e.element("id").getText())); //$NON-NLS-1$ //$NON-NLS-2$
	}


	/**
	 * Get rank ID
	 * @return
	 */
	public int getId() {
		return id;
	}

	
	/**
	 * Set rank ID
	 * @param rankId
	 */
	public void setId(int rankId) {
		id = rankId;
	}

	
	/**
	 * Get rank name
	 * @return
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Set rank name
	 * @param rankId
	 */
	public void setName(String rankName) {
		name = rankName;
	}
}

