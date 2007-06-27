package lost.tok;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
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
			
			IResource res = delta.getResource();
			IProject proj = res.getProject();
			IFolder discussionsFolder = proj.getFolder(ToK.DISCUSSION_FOLDER);
			
			if (res.getParent().equals(discussionsFolder)
				&& res.getFileExtension().equals("dis"))
			{
				System.out.println("Shay said: Oh noes! Evil user is removing discussion! " + res.getName());
				
				Job job = new RemoveDiscussionJob(res);
				job.schedule();
			}
			
		}
		
		private class RemoveDiscussionJob extends Job{
			private IResource res;
			public RemoveDiscussionJob(IResource res)
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
