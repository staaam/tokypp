package lost.tok.opTable.sourceDocument;

import java.util.Arrays;
import java.util.LinkedList;

public class RangeSearch {
	LinkedList<LabeledPosition> ps;

	boolean sorted = false;

	LabeledPosition[] p;

	RangeSearch() {
		ps = new LinkedList<LabeledPosition>();
	}

	void add(Integer offset, Integer length, String name) {
		ps.add(new LabeledPosition(offset, length, name));
		sorted = false;
	}

	String search(Integer offset) {
		if (!sorted) {
			p = new LabeledPosition[ps.size()];
			ps.toArray(p);
			Arrays.sort(p);
		}
		Integer i = Arrays
				.binarySearch(p, new LabeledPosition(offset, 0, null));
		i = (i >= 0) ? i : -i - 2;
		if (p[i].offset + p[i].length > offset) {
			return p[i].getLabel();
		}
		return new String();
	}
}