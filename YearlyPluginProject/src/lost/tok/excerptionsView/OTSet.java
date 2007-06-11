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

