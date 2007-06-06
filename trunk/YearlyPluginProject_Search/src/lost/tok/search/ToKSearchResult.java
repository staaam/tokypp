package lost.tok.search;

import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.search.ui.text.Match;

public class ToKSearchResult extends AbstractTextSearchResult {
	private ToKSearchQuery searchQuery;
	
	public ToKSearchResult(ToKSearchQuery query) {
		this.searchQuery = query;
	}

	@Override
	public IEditorMatchAdapter getEditorMatchAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFileMatchAdapter getFileMatchAdapter() {
//		return null;
		return new IFileMatchAdapter() {

			public Match[] computeContainedMatches(AbstractTextSearchResult result, IFile file) {
				return result.getMatches(file);
			}

			public IFile getFile(Object element) {
				return (IFile)element;
			}
			
		};
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLabel() {
		return getQuery().getLabel() + " - result";
	}

	public ISearchQuery getQuery() {
		return searchQuery;
	}

	public String getTooltip() {
		// TODO Auto-generated method stub
		return null;
	}

}
