package lost.tok.search;

import java.util.EnumSet;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;

public class ToKSearchQuery implements ISearchQuery {

	private ToKSearchResult searchResult = new ToKSearchResult(this);
	
	private String searchPattern;
	private EnumSet<SearchOption> searchOptions;
	private List<IResource> scope;

	public ToKSearchQuery(String searchPattern,
			EnumSet<SearchOption> searchOptions,
			List<IResource> scope) {
		this.searchPattern = searchPattern;
		this.searchOptions = searchOptions;
		this.scope = scope;
	}

	public boolean canRerun() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canRunInBackground() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getLabel() {
		return "Searching for: '" + searchPattern + "'";
	}

	public ISearchResult getSearchResult() {
		return searchResult;
	}

	public IStatus run(IProgressMonitor monitor) {
		IResourceVisitor visitor = new ToKSearchVisitor(
				searchPattern, searchOptions, searchResult
				);
		monitor.beginTask("Searching...", IProgressMonitor.UNKNOWN);
		for (IResource r : scope) {
			if (monitor.isCanceled())
				return new Status(IStatus.CANCEL, "lost.tok", //$NON-NLS-1$
					IStatus.OK, "Search canceled", null);

			try {
				r.accept(visitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return new Status(IStatus.OK, "lost.tok", //$NON-NLS-1$
				IStatus.OK, "Ok", null);
	}

}
