package lost.tok.navigator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.views.navigator.ResourceSorter;


public class InformationComparator extends ResourceSorter{
	
	public InformationComparator() {
		super(1);
	}
	


    /* (non-Javadoc)
     * Method declared on ViewerSorter.
     */
	public int compare(Viewer viewer, Object o1, Object o2) {
		 //have to deal with non-resources in navigator
        //if one or both objects are not resources, returned a comparison 
        //based on class.
//        if (!(o1 instanceof IResource && o2 instanceof IResource)) {
//            return compareClass(o1, o2);
//        }
//        IResource r1 = (IResource) o1;
//        IResource r2 = (IResource) o2;
//        IProject project = r1.getProject();
//        String sor = ToK.getProjectToK(project).getSourcesFolder().getFullPath().toString();
//        String rot = ToK.getProjectToK(project).getRootsFolder().getFullPath().toString();
//        String s1 = r1.getFullPath().toString();
//        String s2 = r2.getFullPath().toString();
//        
//        if ((s1.startsWith(sor) && s2.startsWith(sor)) || (s1.startsWith(rot) && s2.startsWith(rot))){
//        	IFile order = (IFile) r1.getParent().findMember("order.xml");
//        	Document d = GeneralFunctions.readFromXML(order);
//        	XPath xpathSelector = DocumentHelper.createXPath("");
//    		List result = xpathSelector. .selectNodes(d);
//        	return super.compare(viewer, o1, o2);
//        }

        return super.compare(viewer, o1, o2);
	}

}
