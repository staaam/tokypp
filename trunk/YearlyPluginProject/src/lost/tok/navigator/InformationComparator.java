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

package lost.tok.navigator;

import java.util.Comparator;
import java.util.List;

import lost.tok.GeneralFunctions;
import lost.tok.ToK;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.views.navigator.ResourceSorter;


public class InformationComparator extends ResourceSorter implements Comparator<IResource>{
	
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
        if (!(o1 instanceof IResource && o2 instanceof IResource)) {
            return compareClass(o1, o2);
        }
        IResource r1 = (IResource) o1;
        IResource r2 = (IResource) o2;
        String l1 = r1.getFullPath().toString();
        l1 = l1.substring(0, l1.lastIndexOf('/'));
        String l2 = r2.getFullPath().toString();
        l2 = l2.substring(0, l2.lastIndexOf('/'));
        if (!((l1.contains(ToK.SOURCES_FOLDER) && l2.contains(ToK.SOURCES_FOLDER)) || (l1.contains(ToK.ROOTS_FOLDER) && l2.contains(ToK.ROOTS_FOLDER))) || l1.contains(ToK.UNPARSED_SOURCES_FOLDER)){
        	return super.compare(viewer, o1, o2);
        }
        
        // if the order file does not exist, report an error and use the default compare
		IFolder f = (IFolder) r1.getParent();
        IFile file = f.getFile("order.xml");
        
        if (!file.exists())
        {
        	System.err.println("Error: Missing order.xml when comparing " + r1.getProjectRelativePath().toString());
        	return super.compare(viewer, o1, o2);
        }
        
        // if both are sources and order.xml exists, use it to sort the files
        return compare(r1, r2);
	}

	/** 
	 * Compares according to the order.xml file which should be in the resource directory
	 * Is not consistent with equals
	 * Resources should be in the same path
	 * 
	 * @param r1 first res
	 * @param r2 second res
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	 */
	public int compare(IResource r1, IResource r2) {
		IFolder f = (IFolder) r1.getParent();
        IFile file = f.getFile("order.xml");
        
        if (!file.exists())
        {
        	System.err.println("Error: Missing order.xml when comparing " + r1.getProjectRelativePath().toString());
        	return r1.getName().compareTo(r2.getName());
        }
        
        Document d = GeneralFunctions.readFromXML(file);
        
        String s1 = r1.getName();
        String s2 = r2.getName();
        
        XPath xpathSelector = DocumentHelper.createXPath("//sub[name='" + s1 + "']");
		Node node1 = xpathSelector.selectSingleNode(d);
        
        xpathSelector = DocumentHelper.createXPath("//sub[name='" + s2 + "']");
		Node node2 = xpathSelector.selectSingleNode(d);
               
        xpathSelector = DocumentHelper.createXPath("//sub");
        List list = xpathSelector.selectNodes(d);
        int i1 = list.indexOf(node1);
        int i2 = list.indexOf(node2);

        return i1 - i2;
	}

}
