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

package lost.tok.search;

import java.util.EnumSet;
import java.util.List;

import lost.tok.Messages;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.search.internal.ui.text.FileSearchQuery;
import org.eclipse.search.internal.ui.text.FileSearchResult;
import org.eclipse.search.internal.ui.text.SearchResultUpdater;
import org.eclipse.search.ui.text.FileTextSearchScope;

/**
 * Class that manages ToK Search Query, extends FileSearchQuery
 * 
 * @author Michael Gelfand
 * 
 */
public class ToKSearchQuery extends FileSearchQuery {

	private FileSearchResult fResult;
	private String searchPattern;
	private EnumSet<SearchOption> searchOptions;
	private List<IResource> scope;

	public ToKSearchQuery(String searchPattern,
			EnumSet<SearchOption> searchOptions, List<IResource> scope) {
		super(searchPattern, false, 
				searchOptions.contains(SearchOption.CASE_SENSITIVE),
				FileTextSearchScope.newSearchScope(
						scope.toArray(new IResource[scope.size()]),
						new String[0], true));
		
		this.searchPattern = searchPattern;
		this.searchOptions = searchOptions;
		this.scope = scope;
	}

	public String getLabel() {
		return Messages.getString("ToKSearchQuery.SearchFor") + ": '" + searchPattern + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public IStatus run(IProgressMonitor monitor) {
		getSearchResult().removeAll();

		IResourceVisitor visitor = new ToKSearchVisitor(searchPattern,
				searchOptions, getSearchResult());
		monitor.beginTask(Messages.getString("ToKSearchQuery.Searching") + "...", IProgressMonitor.UNKNOWN); //$NON-NLS-1$ //$NON-NLS-2$
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

	public FileSearchResult getSearchResult() {
		if (fResult == null) {
			fResult = new FileSearchResult(this);
			new SearchResultUpdater(fResult);
		}
		return fResult;
	}
}
