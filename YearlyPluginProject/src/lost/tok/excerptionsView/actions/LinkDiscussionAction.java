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

package lost.tok.excerptionsView.actions;

import lost.tok.excerptionsView.ExcerptionView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * The action of linking a discussion to the currently marked text
 */
public class LinkDiscussionAction implements IViewActionDelegate {
	private ExcerptionView view;

	public void run(IAction action) {
		assert (view != null);

		view.linkDiscussion();
	}

	public void init(IViewPart view) {
		assert (view instanceof ExcerptionView);
		this.view = (ExcerptionView) view;
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
