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

package lost.tok.sorter;

import java.util.Arrays;
import java.util.Vector;

import lost.tok.navigator.InformationComparator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * Determines the order of sources  
 * @author Shay
 *
 */
public class Sorter {
	
	/** The folder this sorter sorts */
	private IFolder folder;
	
	/**
	 * Creates a new sorter for the files in this directory
	 * @param folder the folder to sort
	 */
	public Sorter(IFolder folder)
	{
		this.folder = folder;
	}
	
	/**
	 * Reads the order.xml file and returns the resources described in it, according to their order
	 * 
	 * Note(Shay): Currently is a stub
	 */
	public IResource[] getSorted()
	{
		IResource[] resArr = null;
		try {
			resArr = folder.members();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		Vector<IResource> relevantRes = new Vector<IResource>(resArr.length);
		
		// prints error messages for non file/dir resources
		for (IResource res : resArr)
		{
			if (!(res instanceof IFile) && !(res instanceof IFolder))
				System.err.println("Error: Resource " + res.getFullPath() + " is not a file or folder");
			if (res.getName().toLowerCase().equals("order.xml"))
				continue;
			relevantRes.add(res);
		}
		
		IResource[] relArr = new IResource[relevantRes.size()];
		
		relevantRes.toArray(relArr);
		Arrays.sort(relArr, new InformationComparator());
		
		return relArr;
	}

}
