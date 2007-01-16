package lost.tok.opTable.sourceDocument;

import java.util.Vector;

public class ChapterText extends Chapter {
	/** The xPath of this Node in the xml */
	String xPath;

	/** The path as defined for the Excerption xml */
	String excerPath;

	String text;

	Integer pathOffset; // offset in path

	public ChapterText(String xPath, String text) {
		this(xPath, text, 0);
	}

	public ChapterText(String xPath, String text, Integer offset) {
		super(xPath + "/" + String.valueOf(offset), "");
		this.xPath = xPath;
		this.excerPath = null;
		this.length = text.length();
		this.pathOffset = offset;
		this.text = text;
	}

	public Integer getInnerLength() {
		return text.length();
	}

	public String getXPath() {
		return xPath;
	}

	/**
	 * Returns the path as should be used by Excerptions
	 */
	public String getExcerptionPath() {
		if (excerPath == null) {
			excerPath = "";
			// Lazy Initialization
			Vector<Chapter> chaps = new Vector<Chapter>(10);
			Chapter currParent = this.getParent();
			while (currParent != null) {
				chaps.add(currParent);
				currParent = currParent.getParent();
			}

			// Using -2 and not -1 because we want to skip the root
			for (int i = chaps.size() - 2; i >= 0; i--) {
				excerPath += chaps.get(i).name;
				if (i != 0)
					excerPath += "/";
			}
		}
		return excerPath;
	}

	// TODO: Michael, could you verify that this comment is correct?
	/**
	 * Returns the offset of this excerption in the original document (original
	 * document = a document in which there are no chapters and only text)
	 */
	public Integer getPathOffset() {
		return pathOffset;
	}

	public ChapterText split(Integer from) {
		ChapterText e = new ChapterText(xPath, text.substring(from), pathOffset
				+ from);
		text = text.substring(0, from - 1);
		return e;
	}

	public String toString() {
		return text;
	}
}
