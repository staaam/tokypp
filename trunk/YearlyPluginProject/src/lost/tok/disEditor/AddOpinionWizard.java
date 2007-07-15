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

package lost.tok.disEditor;

import lost.tok.Discussion;
import lost.tok.Messages;

import org.eclipse.jface.wizard.Wizard;

public class AddOpinionWizard extends Wizard {
	private AddOpinionWizardPage page;

	private DiscussionEditor discussionEditor = null;

	private Discussion discussion;

	private String opinionName = null;

	public AddOpinionWizard(DiscussionEditor discussionEditor) {
		super();

		this.discussionEditor = discussionEditor;
		discussion = discussionEditor.getDiscussion();

		setWindowTitle(Messages.getString("AddOpinionWizard.AddOpinion")); //$NON-NLS-1$
	}

	public AddOpinionWizard(Discussion discussion) {
		super();

		this.discussion = discussion;

		setWindowTitle(Messages.getString("AddOpinionWizard.AddOpinion")); //$NON-NLS-1$
	}

	@Override
	public void addPages() {
		page = new AddOpinionWizardPage(discussion);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		opinionName = page.getOpinion();
		if (discussionEditor == null) {
			discussion.addOpinion(opinionName);
		} else {
			discussionEditor.addOpinion(opinionName);
		}
		return true;
	}

	public String getOpinionName() {
		return opinionName;
	}
}
