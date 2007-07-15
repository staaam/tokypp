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

package lost.tok.excerptionsView;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import lost.tok.opTable.OperationTable;

public class OTSet extends HashSet<OperationTable> {
	private static final long serialVersionUID = 8498357334519966448L;

	private Hashtable<String, OperationTable> nameToOT = new Hashtable<String, OperationTable>();

	@Override
	public boolean add(OperationTable o) {
		if (!super.add(o))
			return false;
		nameToOT.put(ExcerptionView.nameFromOT(o), o);
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (!super.remove(o))
			return false;
		nameToOT.remove(ExcerptionView.nameFromOT((OperationTable) o));
		return true;
	}

	public boolean remove(String name) {
		return remove(get(name));
	}

	public Set<String> getNames() {
		return nameToOT.keySet();
	}

	public OperationTable get(String name) {
		if (!nameToOT.containsKey(name))
			return null;
		return nameToOT.get(name);
	}
	
	public void clear() {
		super.clear();
		nameToOT.clear();
	}
}

