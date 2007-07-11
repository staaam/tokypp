/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package lost.tok.DiscussionViewer;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

import org.gems.designer.InitialContentProvider;
import org.gems.designer.ModelInstance;
import org.gems.designer.ModelInstanceRepository;
import org.gems.designer.ModelRepository;
import org.gems.designer.PluginUtilities;


public class GemsWizardPage 
	extends WizardNewFileCreationPage 
	implements SelectionListener
{


private IWorkbench	workbench;
private static int exampleCount = 1;
private Button model1 = null;
private Button model2 = null;
private int modelSelected = 1;

public GemsWizardPage(IWorkbench aWorkbench, IStructuredSelection selection) {
	super("DiscussionViewer DSML", selection);  //$NON-NLS-1$
	this.setTitle("DiscussionViewer DSML");
	this.setDescription("Create a DiscussionViewer DSML instance");
	this.setImageDescriptor(ImageDescriptor.createFromFile(getClass(),"icons/logicbanner.gif"));  //$NON-NLS-1$
	this.workbench = aWorkbench;
}

public void createControl(Composite parent) {
	super.createControl(parent);
	this.setFileName("DiscussionViewer" + exampleCount + ".dis");  //$NON-NLS-2$//$NON-NLS-1$
	
	Composite composite = (Composite)getControl();

	new Label(composite,SWT.NONE);

	setPageComplete(validatePage());
}

protected InputStream getInitialContents() {
	ByteArrayInputStream bais = null;
	try{

			String intial = "<?xml version=\"1.0\" encoding=\"ASCII\"?>\n<discussionviewer:Root xmlns:discussionviewer=\"lost.tok.discussionViewer\"></discussionviewer:Root>";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(baos);
            out.write(intial);
            out.flush();
  			out.close();
            baos.close();
            bais = new ByteArrayInputStream(baos.toByteArray());
            bais.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return bais;
}

public boolean finish() {
	IFile newFile = createNewFile();
	if (newFile == null) 
		return false;  // ie.- creation was unsuccessful

    Hashtable useroptions = new Hashtable();
	InitialContentProvider[] initcontents = PluginUtilities.getInitialContentProviders(DiscussionViewerProvider.MODEL_ID);
	for(InitialContentProvider init : initcontents)
		init.provideContent(DiscussionViewerProvider.getInstance(),newFile, useroptions);
	

	// Since the file resource was created fine, open it for editing
	// iff requested by the user
	try {
		IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = dwindow.getActivePage();
		if (page != null)
			IDE.openEditor(page, newFile, true);
	} 
	catch (org.eclipse.ui.PartInitException e) {
		e.printStackTrace();
		return false;
	}
	exampleCount++;
	return true;
}

/**
 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(SelectionEvent)
 */
public void widgetSelected(SelectionEvent e) {
	
}

/**
 * Empty method
 */
public void widgetDefaultSelected(SelectionEvent e) {
}

}