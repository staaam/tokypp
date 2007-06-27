package lost.tok.navigator;

import java.util.Comparator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.views.navigator.ResourceSorter;


public class InformationComparator extends ResourceSorter{
	
	public InformationComparator(int criteria) {
		super(criteria);
	}
	


    /* (non-Javadoc)
     * Method declared on ViewerSorter.
     */
	public int compare(Viewer viewer, Object o1, Object o2) {
		 //have to deal with non-resources in navigator
        //if one or both objects are not resources, returned a comparison 
        //based on class.
        if (!(o1 instanceof IResource && o2 instanceof IResource)) {
            return compareClass(o1, o2);
        }
        IResource r1 = (IResource) o1;
        IResource r2 = (IResource) o2;

        return -super.compare(viewer, o1, o2);
	}

}
