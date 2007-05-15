/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package lost.tok.wizards;

import java.util.List;

import lost.tok.Messages;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.wizards.datatransfer.ZipFileImportWizard;

// TODO: Auto-generated Javadoc
/**
 * The Class ImportDiscussion.
 */
public class ImportDiscussion extends ZipFileImportWizard {

	/** The import page. */
	private ImportDiscussionPage importPage;

	/** The workbench. */
	private IWorkbench workbench;

	/** The selection. */
	private IStructuredSelection selection;

	/**
	 * Instantiates a new import discussion.
	 */
	public ImportDiscussion() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		this.workbench = workbench;
		this.selection = currentSelection;
		List selectedResources = IDE.computeSelectedResources(currentSelection);
		if (!selectedResources.isEmpty()) {
			this.selection = new StructuredSelection(selectedResources);
		}

		setDefaultPageImageDescriptor(IDEWorkbenchPlugin
				.getIDEImageDescriptor("wizban/importzip_wiz.png"));//$NON-NLS-1$
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("ImportDiscussion.0")); // NON-NLS-1
																	// //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	@Override
	public void addPages() {
		importPage = new ImportDiscussionPage(workbench, selection); // NON-NLS-1
		addPage(importPage);
	}

	/*
	 * (non-Javadoc) Method declared on IWizard.
	 */
	@Override
	public boolean performCancel() {
		return importPage.cancel();
	}

	/*
	 * (non-Javadoc) Method declared on IWizard.
	 */
	@Override
	public boolean performFinish() {
		return importPage.finish();
	}
}
