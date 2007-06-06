package lost.tok.sourceDocument;

import org.eclipse.jface.text.Position;

class LabeledPosition extends Position implements Comparable<LabeledPosition> {
	Chapter chapter;

	public LabeledPosition(Integer offset, Integer length, Chapter chapter) {
		super(offset, length);
		this.chapter = chapter;
	}

	public int compareTo(LabeledPosition o) {
		return new Integer(offset).compareTo(o.offset);
	}

	public Chapter getChapter() {
		return chapter;
	}
}