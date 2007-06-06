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
import org.eclipse.search.internal.ui.text.FileSearchResult;
import org.eclipse.search.internal.ui.text.SearchResultUpdater;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.NewSearchUI;

public class ToKSearchQuery implements ISearchQuery {

	private ToKSearchResult fResult;
	
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


	public IStatus run(IProgressMonitor monitor) {
		getSearchResult().removeAll();
		
		IResourceVisitor visitor = new ToKSearchVisitor(
				searchPattern, searchOptions, getSearchResult()
				);
		monitor.beginTask("Searching...", IProgressMonitor.UNKNOWN);
		for (IResource r : scope) {
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;

			try {
				r.accept(visitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		monitor.done();
		return Status.OK_STATUS;
	}

	public ToKSearchResult getSearchResult() {
		if (fResult == null) {
			fResult= new ToKSearchResult(this);
			new SearchResultUpdater(fResult);
		}
		return fResult;
	}
}
