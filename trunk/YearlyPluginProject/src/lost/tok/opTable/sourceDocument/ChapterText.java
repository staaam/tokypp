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
	
	private static int uniqueIdCounter = 0; // TODO(Shay): Why must label be unique?
	
	/** 
	 * Creates a new {chapter,sub chapter,text} ending at that offset  
	 * Related to Source Parser. 
	 */
	public void createNewChapter(Integer offset, String name)
	{
		String originalText = text;
		
		if (isUnparsed())
		{
			// change the label/name of this chapter to what it should be
			this.parent.name = name;
			this.parent.updateLabel();
		}
		else
		{
			Chapter myOldParent = this.parent;
			myOldParent.children.clear();
			Chapter myNewParent = new Chapter("", name);
			myOldParent.add(myNewParent);
			myNewParent.add(this);
			myNewParent.updateLabel();
		}
		
		this.name = "";
		this.label = Chapter.UNPARSED_STR + (uniqueIdCounter++); // FIXME(Shay): Should be some sort of xPath which I haven't understood
		this.text = originalText.substring(0, offset).trim() + "\n";
		this.excerPath = ""; // FIXME(Shay): Update this!
		
		if (!offset.equals(length-1))
		{
			Chapter textChapParent = new Chapter("",Chapter.UNPARSED_STR);
			ChapterText textChap = new ChapterText(Chapter.UNPARSED_STR, originalText.substring(offset).trim() + "\n");
			textChapParent.add(textChap);
			this.parent.parent.add(textChapParent);
			textChapParent.updateLabel();
		}
		// otherwise, no need to create a new chapter		
	}

	public boolean isUnparsed()
	{
		return label.equals(Chapter.UNPARSED_STR + "/0");
	}
}
