package lost.tok.search;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;

public class ToKSearchQuery implements ISearchQuery {

	public boolean canRerun() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canRunInBackground() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public ISearchResult getSearchResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public IStatus run(IProgressMonitor monitor)
			throws OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

}
