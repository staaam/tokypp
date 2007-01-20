package lost.tok.opTable.sourceDocument;

import org.eclipse.jface.text.Position;

class LabeledPosition extends Position implements Comparable<LabeledPosition> {
	String label;

	public LabeledPosition(Integer offset, Integer length, String name) {
		super(offset, length);
		label = name;
	}

	public int compareTo(LabeledPosition o) {
		return new Integer(offset).compareTo(o.offset);
	}

	public String getLabel() {
		return label;
	}
}