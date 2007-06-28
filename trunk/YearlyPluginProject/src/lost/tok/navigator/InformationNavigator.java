package lost.tok.navigator;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.navigator.ResourceNavigator;
import org.eclipse.ui.views.navigator.ResourceSorter;

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
