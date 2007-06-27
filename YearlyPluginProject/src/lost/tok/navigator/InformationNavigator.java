package lost.tok.navigator;

import java.util.Comparator;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.navigator.*;

public class InformationNavigator extends ResourceNavigator{
	
	public final static String ID = "lost.tok.navigator.Navigator"; //$NON-NLS-1$

	public InformationNavigator() {
		super();
	}
	
	
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		ResourceSorter sorter = new InformationComparator();
		super.setSorter(sorter);
	}
	
	
}
