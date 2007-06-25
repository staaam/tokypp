package lost.tok;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class GeneralFunctions {

	public static class Properties {
		HashMap<QualifiedName, Object> properties = new HashMap<QualifiedName, Object>();

		public Object getProperty(QualifiedName key) {
			return properties.get(key);
		}

		public Object setProperty(QualifiedName key, Object value) {
			return properties.put(key, value);
		}
	}

	/** Returns true if the project is running in an LTR language */
	public static boolean isLTR() {
		return Messages
				.getString("LanguageDirection").toLowerCase().equals("ltr"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/** Returns the 'normal' language code for the current language (i.e. en for English and he for Hebrew) */
	public static String langCode() {
		return Messages.getString("LanguageCode").toLowerCase();		
	}

	public static Document readFromXML(IFile file) {
		return readFromXML(file.getLocation().toOSString());
	}

	public static Document readFromXML(String path) {
		try {
			return readFromXML(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Document readFromXML(InputStream inputStream) {
		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		try {
			BufferedReader rdr = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8")); //$NON-NLS-1$
			doc = reader.read(rdr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static void writeToXml(IFile file, Document document) {
		writeToXml(file.getLocation().toOSString(), document);
	}

	public static void writeToXml(String path, Document doc) {
		try {
			OutputFormat outformat = OutputFormat.createPrettyPrint();
			outformat.setEncoding("UTF-8"); //$NON-NLS-1$
			outformat.setTrimText(false);

			FileOutputStream fos = new FileOutputStream(path);
			BufferedWriter wrtr = new BufferedWriter(new OutputStreamWriter(
					fos, "UTF-8")); //$NON-NLS-1$

			XMLWriter writer = new XMLWriter(wrtr, outformat);
			writer.write(doc);
			writer.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a copy of the text with:
	 *   &lt;  replaced by &amp;lt;
	 *   &gt;  replaced by &amp;gt;
	 *   \n    replaced by &lt;br /&gt;
	 *   &amp; replaced by &amp;amp; 
	 * @param text the text to replaced
	 * @return an xml-escaped version of the text
	 */
	public static String xmlEscape(String text)
	{
		String s = text;
		s = s.replaceAll("<", "&lt;");
		s = s.replaceAll(">", "&gt;");
		s = s.replaceAll("\n", "<br />");
		s = s.replaceAll("&", "&amp;");
		return s;
	}

	public static void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "lost.tok", //$NON-NLS-1$
				IStatus.OK, message, null);
		throw new CoreException(status);
	}

	public static boolean fileInFolder(IFile file, IFolder folder) {
		String r = folder.getFullPath().toPortableString();
		return file.getFullPath().toPortableString().startsWith(r);
	}
	
	public static String elementToString(Element elem)
	{
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding("UTF-8"); //$NON-NLS-1$
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String retVal;
		try {
			XMLWriter writer = new XMLWriter(baos, outformat);
			writer.write(elem);
			writer.flush();
			retVal = baos.toString("UTF-8");
		} catch (IOException e) {
			retVal = "Error " + e.getMessage();
			e.printStackTrace();
		}
		
		return retVal;
	}

	/**
	 * Shows the view identified by the given view id in this page and gives it
	 * focus. If there is a view identified by the given view id (and with no
	 * secondary id) already open in this page, it is given focus.
	 * 
	 * @param viewId the id of the view extension to use
	 * @param bringToTop 
	 * @return the shown view
	 */
	public static IViewPart getView(String viewId, boolean bringToTop) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();

		if (activePage == null)
			return null;

		IViewPart view = activePage.findView(viewId);

		if (view == null)
			try {
				view = activePage.showView(viewId);
				activePage.getActiveEditor().setFocus();
			} catch (PartInitException e) {
			}
		else if (bringToTop)
			activePage.bringToTop(view);
		
		return view;
	}
}
