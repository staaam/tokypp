package lost.tok.sourceParser.wizards;

//import lost.tok.ToK;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.sourceDocument.Chapter;
import lost.tok.sourceParser.SourceParser;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.jaxen.dom4j.Dom4jXPath;

public class UnparsedDocWizard extends Wizard implements INewWizard {
	private UnparsedDocWizardPage page;

	private ISelection selection;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public UnparsedDocWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new UnparsedDocWizardPage(selection);
		addPage(page);
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. Gets the data from the page and executes the action
	 */
	public boolean performFinish() {
		
		// page's fields
		final String title = page.getSourceTitle();
		final String author = page.getAuthorName();
		final String srcPath = page.getSourcePath();
		final String fileName = page.getInputFileName();
		final String projName = page.getTargetProjectName();
		final boolean isRoot = page.getIsRoot();
			
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(title, author, srcPath, fileName, projName, isRoot, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage()); //$NON-NLS-1$
			return false;
		}
		return true;
	}
	
	private void doFinish(
			String title,
			String author,
			String srcPath,
			String fullFileName,
			String projName,
			boolean isRoot,
			IProgressMonitor monitor)
			throws CoreException 
	{
		// create a sample file
		monitor.beginTask(Messages.getString("SPWizard.1") + title, 2); //$NON-NLS-1$
		
		final IFile eclipseFile = getUnparsedTargetIFile(projName, fullFileName);
		// Check for existance, Create the xml initial file
		if (eclipseFile.exists()) 
		{
			// shouldn't happen. If it did, just open the same file in the editor
			// as usual
		}
		else
		{
			try {
				createResourceDocument(fullFileName, title, author, srcPath, null, eclipseFile);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		eclipseFile.setPersistentProperty(ToK.isRootQName, isRoot ? "True" : "False"); //$NON-NLS-1$ //$NON-NLS-2$
		
		monitor.worked(1);
		monitor.setTaskName(Messages.getString("SPWizard.4")); //$NON-NLS-1$
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, eclipseFile, SourceParser.EditorID, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);
	}
		
	static public IFile getUnparsedTargetIFile(String projName, String fullFileName)
	{
		String fileName = getFileNameWithoutExtension(fullFileName);		
				
		IProject targetProj = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
		return targetProj.getFile(ToK.UNPARSED_SOURCES_FOLDER + "\\" + fileName + ".upsrc"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	static public IFile getParsedTargetIFile(String projName, String fullFileName)
	{
		String fileName = getFileNameWithoutExtension(fullFileName);
				
		IProject targetProj = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
		return targetProj.getFile(ToK.SOURCES_FOLDER + "\\" + fileName + ".src"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/** Returns only the name of the file, without its path or extension */
	static private String getFileNameWithoutExtension(String fullFileName)
	{
		Path fullFileNamePath = new Path(fullFileName);
		String fileName = fullFileNamePath.lastSegment();
		
		int extensionStart = fileName.lastIndexOf('.');
		String withoutExtension = 
			(extensionStart != -1) ? fileName.substring(0, extensionStart):fileName;
		return withoutExtension;
	}

	/** Creates an xml describing the unparsed document */
	static private void createResourceDocument
		(String inputFile, String title, String author, String srcPath, IProgressMonitor monitor, IFile targetFile)
			throws IOException, CoreException
	{
		File inFile = new File(inputFile);
		long inputLength = inFile.length();
		
		// a reader for the original, unparsed file
		BufferedReader rdr = 
			new BufferedReader(
				new InputStreamReader(
					new FileInputStream(inputFile),"UTF-8")); //$NON-NLS-1$
		
		StringBuffer content = new StringBuffer((int)inputLength);
		String line = rdr.readLine();
		while (line != null)
		{
			content.append(line + "\n"); //$NON-NLS-1$
			line = rdr.readLine();
		}
		rdr.close();

		/*File target = new File(targetFile);
		target.createNewFile();
		// a writer, for the xml unparsed file
		BufferedWriter wrtr =
			new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(targetFile),"UTF-8"));*/
		
/*
		// Shay: Since the Dom4j code in this case turned out
		// to be longer and more comlicated,
		// I chose to stick to the manual version
		StringBuffer sb = new StringBuffer(500);
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<source>\n");
		if (srcPath.equals(""))
			sb.append("\t<name>" + title + "</name>\n");
		else
			sb.append("\t<name>" + srcPath + "\\" + title + "</name>\n");
		sb.append("\t<author>" + author + "</author>\n");
		sb.append("\t<child>\n");
		sb.append("\t\t<text>\n");
		sb.append("\t\t\t<name>" + Chapter.UNPARSED_STR + "</name>\n");
		sb.append("\t\t\t<content>\n");
		
		// write the actual text
		String line = rdr.readLine();
		while (line != null)
		{
			// BUG: string was not escaped :(
			sb.append(line + "\n");
			line = rdr.readLine();
		}
		rdr.close();
		
		// close the open tags
		sb.append("</content>\n");
		sb.append("\t\t</text>\n");
		sb.append("\t</child>\n");
		sb.append("</source>\n");
		byte[] bytes = sb.toString().getBytes("UTF-8");
		*/
		
		Document unparsedXML = DocumentHelper.createDocument();
		unparsedXML.setXMLEncoding("UTF-8"); //$NON-NLS-1$
		Element source = unparsedXML.addElement("Source"); //$NON-NLS-1$
		Element sourceName = source.addElement("name"); //$NON-NLS-1$
		if (srcPath.equals("")) //$NON-NLS-1$
			sourceName.addText(title);
		else
			sourceName.addText(srcPath + "\\" + title); //$NON-NLS-1$
		source.addElement("author").addText(author); //$NON-NLS-1$
		Element text = source.addElement("child").addElement("text"); //$NON-NLS-1$ //$NON-NLS-2$
		text.addElement("name").addText(Chapter.UNPARSED_STR); //$NON-NLS-1$
		text.addElement("content").addText(content.toString()); //$NON-NLS-1$
		
		String xmlString = unparsedXML.asXML();
		byte[] bytes = xmlString.getBytes("UTF-8"); //$NON-NLS-1$
		
		ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
		targetFile.create(bs, true, monitor);
	}
}
