package lost.tok.navigator;


import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;

import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;
import org.eclipse.ui.views.navigator.NavigatorDragAdapter;
import org.eclipse.ui.views.navigator.ResourcePatternFilter;

import org.eclipse.ui.views.navigator.ResourceNavigator;
import org.eclipse.ui.views.navigator.ResourceSorter;

public class InformationNavigator extends ResourceNavigator{
	
	public final static String ID = "lost.tok.navigator.Navigator"; //$NON-NLS-1$
	private Listener dragDetectListener;
	protected boolean dragDetected;

	public InformationNavigator() {
		super();
	}
	
	
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		ResourceSorter sorter = new InformationComparator();
		super.setSorter(sorter);
	}
		
	
	protected void handleSelectionChanged(SelectionChangedEvent event) {
		super.handleSelectionChanged(event);
    }
	
	 public void dispose() {
		 super.dispose();
	 }
	
	
	protected void initDragAndDrop() {
		 int ops = DND.DROP_COPY | DND.DROP_MOVE;
	        Transfer[] transfers = new Transfer[] {
	                LocalSelectionTransfer.getInstance(),
	                ResourceTransfer.getInstance(), FileTransfer.getInstance(),
	                PluginTransfer.getInstance() };
	        TreeViewer viewer = getTreeViewer();
	        viewer.addDragSupport(ops, transfers, new NavigatorDragAdapter(viewer));
	        ToKDropAdapter adapter = new ToKDropAdapter(viewer);
	        adapter.setFeedbackEnabled(false);
	        viewer.addDropSupport(ops | DND.DROP_DEFAULT, transfers, adapter);
	        dragDetectListener = new Listener() {
	            public void handleEvent(Event event) {
	                dragDetected = true;
	            }
	        };
	        viewer.getControl().addListener(SWT.DragDetect, dragDetectListener);
    }
	
	protected void initFilters(TreeViewer viewer) {
        super.initFilters(viewer);
        ResourcePatternFilter f = new ResourcePatternFilter();
        String st[] = {"order.xml", ".project"};

        
        f.setPatterns(st);
        viewer.addFilter(f);
    }
}