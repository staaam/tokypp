/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

package lost.tok.sourceDocument;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Allows easily finding where a certain index falls, from previously declared
 * ranges
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