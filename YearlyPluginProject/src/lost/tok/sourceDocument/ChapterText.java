package lost.tok.sourceDocument;

import java.util.Vector;

public class ChapterText extends Chapter {
	/** The path as defined for the Excerption xml */
	String excerPath;

	String text;

	Integer pathOffset; // offset in path

	public ChapterText(String name, String text) {
		super("", name); //$NON-NLS-1$
		excerPath = null;
		length = text.length();
		pathOffset = offset;
		this.text = text;
	}

	/*
	 * Shay: This was written in Iteration 1 in order to allow cut operations
	 * public ChapterText(String text, Integer offset) { super("", "");
	 * this.excerPath = null; this.length = text.length(); this.pathOffset =
	 * offset; this.text = text; }
	 */

	public Integer getInnerLength() {
		return text.length();
	}

	/**
	 * Returns the path as should be used by Excerptions
	 */
	public String getExcerptionPath() {
		if (excerPath == null) {
			excerPath = ""; //$NON-NLS-1$
			// Lazy Initialization
			Vector<Chapter> chaps = new Vector<Chapter>(10);
			Chapter currParent = getParent();
			while (currParent != null) {
				chaps.add(currParent);
				currParent = currParent.getParent();
			}

			// Using -2 and not -1 because we want to skip the root
			for (int i = chaps.size() - 2; i >= 0; i--) {
				excerPath += chaps.get(i).name;
				if (i != 0) {
					excerPath += "/"; //$NON-NLS-1$
				}
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

	/*
	 * Shay: This is unused. Probably left overs from iteration 1 cut operation
	 * public ChapterText split(Integer from) { ChapterText e = new
	 * ChapterText(text.substring(from), pathOffset + from); text =
	 * text.substring(0, from - 1); return e; }
	 */

	public String toString() {
		return text;
	}

	/**
	 * Creates a new {chapter,sub chapter,text} ending at that offset Returns
	 * the ChapterText with the unparsed text, or itself if no such chap was
	 * created
	 */
	public ChapterText createNewChapter(Integer offset, String name) {
		String originalText = text;

		if (isUnparsed()) {
			// change the label/name of this chapter to what it should be
			parent.name = name;
			parent.updateLabel();
		} else {
			Chapter myOldParent = parent;
			myOldParent.children.clear();
			Chapter myNewParent = new Chapter("", name); //$NON-NLS-1$
			myOldParent.add(myNewParent);
			myNewParent.add(this);
			myNewParent.updateLabel();
		}

		this.name = name; // _this_ is now considered parsed
		label = ""; //$NON-NLS-1$
		text = originalText.substring(0, offset).trim() + "\n"; //$NON-NLS-1$
		excerPath = null;

		if (!offset.equals(length - 1)) {
			Chapter textChapParent = new Chapter("", Chapter.UNPARSED_STR); //$NON-NLS-1$
			ChapterText textChap = new ChapterText(Chapter.UNPARSED_STR,
					originalText.substring(offset).trim() + "\n"); //$NON-NLS-1$
			textChapParent.add(textChap);
			parent.parent.add(textChapParent);
			textChapParent.updateLabel();
			return textChap;
		}
		// otherwise, no need to create a new chapter
		return this;
	}

	public boolean isUnparsed() {
		return name.equals(Chapter.UNPARSED_STR);
	}

	public boolean containsUnparsed() {
		return isUnparsed();
	}

	/**
	 * Checks if it is ok to name the chapter's text with this name
	 * 
	 * @param name
	 *            the new name
	 * @return true if it is ok, false otherwise (ie has brother with the same
	 *         name)
	 */
	public boolean isLegalName(String newName) {
		if (newName.equals(Chapter.UNPARSED_STR)) {
			return false;
		}

		if (!isUnparsed()) {
			return true; // a new sub-level will be created. there are no
							// brothers
		}

		if (parent == null) {
			return true;
		}

		Chapter gp = parent.parent;
		if (gp == null) {
			return true;
		}

		for (Chapter uncle : gp.children) {
			if (uncle.name.equals(newName)) {
				return false;
			}
		}
		return true;
	}

	public String getText() {
		return text;
	}
}
