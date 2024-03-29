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

package lost.tok.linkDisView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.Link;
import lost.tok.Messages;
import lost.tok.imageManager.ImageManager;
import lost.tok.imageManager.ImageType;
import lost.tok.opTable.OperationTable;
import lost.tok.opTable.RootDiscussionsPart;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


public class LinkDisView extends ViewPart {
	public final static String ID = "lost.tok.linkDisView.LinkDisView"; //$NON-NLS-1$
		
	class ViewLabelProvider extends LabelProvider {

		public Image getImage(Object obj) {
			ImageType t = ImageType.EXCERPTION; 

			if (obj instanceof TreeNode)
				obj = ((TreeNode) obj).getValue();
			
			if (obj instanceof Discussion)
				t = ImageType.DISCUSSION;
			
			return ImageManager.getImage(t);
		}

		public String getText(Object obj) {
			if (obj instanceof TreeNode)
				obj = ((TreeNode) obj).getValue();
			
			if (obj instanceof Discussion) {
				Discussion d = (Discussion) obj;
				Link l = d.getLink();
				return d.getDiscName() + " (" + Messages.getString("LinkDisView.LinkType") + ": " + l.getDisplayLinkType() //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ ", " + Messages.getString("LinkDisView.LinkSubject") + ": '" + l.getSubject() + "')"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
			
			if (obj instanceof Excerption) {
				Excerption e = (Excerption) obj;
				return e.getText();
			}
			
			return obj.toString();
		}
	}

	private TreeViewer viewer;
	private OperationTable lastOT = null;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new TreeNodeContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
		hookDoubleClickAction();
	}

	/**
	 * Should handle double click event in Excerption View
	 * When source double clicked, it becomes active
	 * When excerption double clicked, it's source becomes
	 * active and scrolled to selected excerption 
	 *
	 */
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				highlightSelection(viewer.getSelection());
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/**
	 * Returns the ExcerptionView object. If the view not shown in current
	 * perspective, function shows it
	 * 
	 * @return ExceptionView object
	 */
	public static LinkDisView getView(boolean bringToTop) {
		return (LinkDisView) GeneralFunctions.getView(LinkDisView.ID, bringToTop);
	}

	public void update(OperationTable ot) {
		if (viewer == null || viewer.getContentProvider() == null) return ;
		
		
		HashMap<Discussion, List<Excerption>> expsMap = ot.getLinksMap();
		
		if (!ot.isRootDiscussionsView() || expsMap == null) {
			viewer.setInput(new TreeNode[0]);
			lastOT = null;
			return;
		}
		
		if (lastOT == ot) return;
		
		lastOT = ot;

		LinkedList<TreeNode> discs = new LinkedList<TreeNode>(); 
		for (Discussion d : expsMap.keySet()) {
			List<Excerption> exps = expsMap.get(d);
			TreeNode[] expNodes = new TreeNode[exps.size()];
			for (int i = 0; i < expNodes.length; i++)
				expNodes[i] = new TreeNode(exps.get(i));
			
			TreeNode dNode = new TreeNode(d);
			dNode.setChildren(expNodes);
			
			discs.add(dNode);
		}
			
		viewer.setInput(discs.toArray(new TreeNode[discs.size()]));
		viewer.expandAll();
	}
	
	int lastExcerptionIndex = 0;
	Object lastExcerption = null;

	int lastDiscussionIndex = 0;
	Object lastDiscussion = null;
	
	public void next() {
		Object input = viewer.getInput();

		IContentProvider contentProvider = viewer.getContentProvider();
		if (!(contentProvider instanceof TreeNodeContentProvider))
			return;
		
		TreeNodeContentProvider tcp = (TreeNodeContentProvider) contentProvider;
		
		Object[] d = tcp.getElements(input);
		if (d == null || d.length == 0) return;
		
		if (lastDiscussion == null || 
				lastDiscussionIndex >= d.length || 
				d[lastDiscussionIndex] != lastDiscussion) {
			lastDiscussionIndex = 0;
			lastDiscussion = d[lastDiscussionIndex];
		}
	
		Object[] e = tcp.getChildren(lastDiscussion);
		if (lastExcerption == null || 
				lastExcerptionIndex >= e.length ||
				e[lastExcerptionIndex] != lastExcerption)
			lastExcerptionIndex = -1;
		
		lastExcerptionIndex++;
		if (lastExcerptionIndex >= e.length) {
			lastDiscussionIndex = (lastDiscussionIndex + 1) % d.length;
			lastDiscussion = d[lastDiscussionIndex];
			e = tcp.getChildren(lastDiscussion);

			lastExcerptionIndex = 0;
		}
		lastExcerption = e[lastExcerptionIndex];
		setCurrentExcerption();
	}

	public void prev() {
		Object input = viewer.getInput();

		IContentProvider contentProvider = viewer.getContentProvider();
		if (!(contentProvider instanceof TreeNodeContentProvider))
			return;
		
		TreeNodeContentProvider tcp = (TreeNodeContentProvider) contentProvider;
		
		Object[] d = tcp.getElements(input);
		if (d == null || d.length == 0) return;
		
		if (lastDiscussion == null || 
				lastDiscussionIndex >= d.length || 
				d[lastDiscussionIndex] != lastDiscussion) {
			lastDiscussionIndex = d.length - 1;
			lastDiscussion = d[lastDiscussionIndex];
		}
	
		Object[] e = tcp.getChildren(lastDiscussion);
		if (lastExcerption == null || 
				lastExcerptionIndex >= e.length ||
				e[lastExcerptionIndex] != lastExcerption)
			lastExcerptionIndex = e.length;
		
		lastExcerptionIndex--;
		if (lastExcerptionIndex < 0) {
			lastDiscussionIndex = (lastDiscussionIndex - 1) % d.length;
			lastDiscussion = d[lastDiscussionIndex];
			e = tcp.getChildren(lastDiscussion);

			lastExcerptionIndex = e.length - 1;
		}
		lastExcerption = e[lastExcerptionIndex];
		setCurrentExcerption();
	}

	private void setCurrentExcerption() {
		Object[] path = new Object[3];
		path[0] = viewer.getInput();
		path[1] = lastDiscussion;
		path[2] = lastExcerption;
		TreeSelection ts = new TreeSelection(new TreePath(path));
		viewer.setSelection(ts, true);
		highlightSelection(ts);
	}

	private void highlightSelection(ISelection ts) {
		ITreeSelection selection = (ITreeSelection) ts;
		TreeNode n = (TreeNode) selection.getFirstElement();
		
		if (n.getValue() instanceof Discussion) {
			Discussion d = (Discussion) n.getValue();
			RootDiscussionsPart.openDiscussionLink(d.getLink());
		}
		
		if (n.getValue() instanceof Excerption) {
			Excerption e = (Excerption) n.getValue();

			lastOT.scrollToExcerption(e);
			lastOT.activate();
		}
	}
	
//	static private int arraySearch(Object[] a, Object e) {
//		for (int i = 0; i < a.length; i++) {
//			if (e == a) return i;
//		}
//		return -1;
//	}
//	
}
