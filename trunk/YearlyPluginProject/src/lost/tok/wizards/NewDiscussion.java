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

package lost.tok.wizards;

import java.io.FileNotFoundException;

import lost.tok.Messages;
import lost.tok.ToK;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewDiscussion extends Wizard implements INewWizard {
	private NewDiscussionPage page;

	private IProject project = null;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public NewDiscussion() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("NewDiscussionWizard.0")); //$NON-NLS-1$
	}

	public NewDiscussion(IProject project) {
		this();
		setProject(project);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewDiscussionPage();
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {
		try {
			ToK.getProjectToK(project).addDiscussion(page.getDiscussionName(), page.getDiscussionDescription());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		if (project != null)
			return;

		if (selection == null || selection.isEmpty() || selection.size() > 1)
			return;

		Object obj = selection.getFirstElement();
		if (obj instanceof IResource) {
			IResource resource = (IResource) obj;
			project = resource.getProject();
		}
	}

	private void setProject(IProject project) {
		this.project = project;
	}

	public IProject getProject() {
		return project;
	}

	public String getDiscussionName() {
		return page.getDiscussionName();
	}
	
	public String getDiscussionDescription() {
		return page.getDiscussionDescription();
	}
}
