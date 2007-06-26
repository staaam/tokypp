package lost.tok;

import java.util.EnumSet;
import java.util.LinkedList;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.SearchResultEvent;

import junit.framework.TestCase;
import lost.tok.search.SearchOption;
import lost.tok.search.ToKSearchQuery;

public class Search extends TestCase {
	
	public void testEmptySearch() {
		ToKSearchQuery query;
		try {
			query = new ToKSearchQuery("",
				EnumSet.noneOf(SearchOption.class),
				new LinkedList<IResource>());
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		
		ISearchResult result = query.getSearchResult();
		result.addListener(new ISearchResultListener() {
			public void searchResultChanged(SearchResultEvent e) {
				assert(false);
			}
			
		});
		query.run(new NullProgressMonitor());
		
		System.out.println("res");
	}

}
