package lost.tok.navigator;

import java.util.Comparator;

import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.ui.views.navigator.*;

public class InformationNavigator extends ResourceNavigator{
	
	public final static String ID = "lost.tok.navigator.InformationNavigator"; //$NON-NLS-1$

	public InformationNavigator() {
		super();
		//ResourceSorter sorter = (new InformationComparator());
		//this.setSorter(sorter);
	}
	
	
	
}
