package lost.tok.linkDisView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.Link;
import lost.tok.opTable.OperationTable;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;


public class LinkDisView extends ViewPart {
	public final static String ID = "lost.tok.linkDisView.LinkDisView"; //$NON-NLS-1$
		
	class ViewLabelProvider extends LabelProvider {

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;

			if (obj instanceof TreeNode)
				obj = ((TreeNode) obj).getValue();
			
			if (obj instanceof Discussion) {
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					imageKey);
		}

		public String getText(Object obj) {
			if (obj instanceof TreeNode)
				obj = ((TreeNode) obj).getValue();
			
			if (obj instanceof Discussion) {
				Discussion d = (Discussion) obj;
				Link l = d.getLink();
				return d.getDiscName() + " (Link type: " + l.getDisplayLinkType() + ", Link subject: '" + l.getSubject() + "')";
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
		
//		getViewSite().getPage().addPartListener(new IPartListener() {
//			public void partActivated(IWorkbenchPart part) {
//				if (shownPart != part)
//					update(part);
//			}
//
//			public void partBroughtToTop(IWorkbenchPart part) {
//				if (shownPart != part)
//					update(part);
//			}
//
//			public void partClosed(IWorkbenchPart part) {
//				if (shownPart == part)
//					clear();
//			}
//
//			public void partDeactivated(IWorkbenchPart part) {
//			}
//
//			public void partOpened(IWorkbenchPart part) {
//				if (shownPart != part)
//					update(part);
//			}
//			
//		});
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
				ITreeSelection selection = (ITreeSelection) viewer.getSelection();
				TreeNode n = (TreeNode) selection.getFirstElement();
				
				if (n.getValue() instanceof Discussion)
					n = n.getChildren()[0];
				
				if (n.getValue() instanceof Excerption) {
					Excerption e = (Excerption) n.getValue();
					//ExcerptionView.getView().get
					
					IEditorPart editor = getViewSite().getPage().getActiveEditor();
					if (editor instanceof OperationTable) {
						OperationTable ot = (OperationTable) editor;
						ot.scrollToExcerption(e);
						ot.activate();
					}
				}
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
}










