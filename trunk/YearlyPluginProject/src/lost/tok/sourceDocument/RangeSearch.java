package lost.tok.sourceDocument;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Allows easily finding where a certain index falls, from previously declared ranges
 */
public class RangeSearch {
	LinkedList<LabeledPosition> ps;

	boolean sorted = false;

	LabeledPosition[] p;

	RangeSearch() {
		ps = new LinkedList<LabeledPosition>();
	}

	void clear() {
		ps.clear();
		p = null;
		sorted = false;
	}

	void add(Integer offset, Integer length, Chapter chap) {
		ps.add(new LabeledPosition(offset, length, chap));
		sorted = false;
	}

	Chapter search(Integer offset) {
		if (!sorted) {
			p = new LabeledPosition[ps.size()];
			ps.toArray(p);
			Arrays.sort(p);
		}
		Integer i = Arrays
				.binarySearch(p, new LabeledPosition(offset, 0, null));
		i = (i >= 0) ? i : -i - 2;
		if (p[i].offset + p[i].length > offset) {
			return p[i].getChapter();
		}
		return null;
	}
}