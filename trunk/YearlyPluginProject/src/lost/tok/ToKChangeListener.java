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

package lost.tok;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Spots changes in the project's resources
 * @author Team Lost
 */
public class ToKChangeListener implements IResourceChangeListener {

	public void resourceChanged(IResourceChangeEvent event) {
		try {
			event.getDelta().accept(new ToKDeltaVisitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private class ToKDeltaVisitor implements IResourceDeltaVisitor {

		public boolean visit(IResourceDelta delta) throws CoreException {
					
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				// do nothing
				break;
			case IResourceDelta.REMOVED:
				handleDeletedDiscussion(delta);
				// handle removed resource
				break;
			case IResourceDelta.CHANGED:
				// do nothing
				break;
			}
			return true; // means that we will check the children too
		}

		/**
		 * Removes a discussion from the links.xml file
		 */
		private void handleDeletedDiscussion(IResourceDelta delta) {
			assert(delta.getKind() == IResourceDelta.REMOVED);
			
			
			// Michael changed :)
			
			if (!(delta.getResource() instanceof IFile))
				return;
			
			IFile res = (IFile) delta.getResource();
			
			if (!Discussion.isDiscussion(res))
				return;

			System.out.println("Shay said: Oh noes! Evil user is removing discussion! " + res.getName());
			
			Job job = new RemoveDiscussionJob(res);
			job.schedule();
		}
		
		private class RemoveDiscussionJob extends Job{
			private IFile res;
			public RemoveDiscussionJob(IFile res)
			{
				super("Delete " + res.getName());
				this.res = res;
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					IProject proj = res.getProject();
					ToK tok = ToK.getProjectToK(proj);
					tok.removeDiscussion(res);
					return Status.OK_STATUS;
				} catch (Exception e) {
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}
			}
		}

	}

}
