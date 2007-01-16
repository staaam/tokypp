/**
 * 
 */
package lost.tok;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;

import yearly_plugin_project.Activator;

/**
 * @author Team LOST
 * 
 */
public class ToK {

	private IWorkspace workSpaceTok;

	private IProject treeOfKnowledgeProj;

	private IProjectDescription projectDescrip;

	private IProgressMonitor progMonitor;

	private String projCreator;

	private IFolder srcFolder, discFolder;

	private IFile rootFile, authorFile, linkFile;

	private List<Discussion> discussions = new ArrayList<Discussion>();

	final static QualifiedName tokQName = new QualifiedName(null, "ToK Object");

	public void createToKProject(String projectName, String creator, String root)
			throws CoreException, IOException {
		projCreator = creator;
		if (!checkProjectName(projectName))
			return;

		try {
			workSpaceTok = ResourcesPlugin.getWorkspace();
		} catch (IllegalStateException e) {
			System.out.println("workspace problem");
			throw e;
		}

		treeOfKnowledgeProj = workSpaceTok.getRoot().getProject(projectName);

		try {
			if (!treeOfKnowledgeProj.exists())
				treeOfKnowledgeProj.create(projectDescrip, progMonitor);
			else
				return;

		} catch (CoreException e) {
			System.out.println("exception in project creation: " + e);
			throw e;
		}
		System.out
				.println("number of projects:"
						+ ResourcesPlugin.getWorkspace().getRoot()
								.getProjects().length);
		treeOfKnowledgeProj.open(progMonitor);

		// setting project properties
		String value = projCreator;
		QualifiedName name = new QualifiedName("TOK Project creator", "Creator");
		treeOfKnowledgeProj.setPersistentProperty(name, value);

		value = getRootName(root);
		name = new QualifiedName("TOK Project root", "Root");
		treeOfKnowledgeProj.setPersistentProperty(name, value);

		treeOfKnowledgeProj.setSessionProperty(tokQName, this);

		// see if we can get the property
		// System.out.println("the creator is:"+
		// treeOfKnowledgeProj.getPersistentProperty(name));
		if (!createToKLibraries(treeOfKnowledgeProj))
			return;
		if (!createToKFiles())
			return;
		if (!setTokRoot(root))
			return;

	}

	private boolean checkProjectName(String projectName) {
		if (projectName.replace('\\', '/').indexOf('/', 1) > 0) {
			// File name must be valid
			return false;
		} else
			return true;

	}

	public boolean createToKLibraries(IProject proj) throws CoreException {
		srcFolder = proj.getFolder("Sources");
		discFolder = proj.getFolder("Discussions");

		// at this point, no resources have been created
		if (!proj.exists())
			System.out.println("project does not exist");
		if (!proj.isOpen())
			proj.open(null);
		if (!srcFolder.exists())
			srcFolder.create(IResource.NONE, true, progMonitor);
		if (!discFolder.exists())
			discFolder.create(IResource.NONE, true, progMonitor);

		return true;
	}

	public boolean createToKFiles() throws CoreException, FileNotFoundException {
		// creating the files

		authorFile = treeOfKnowledgeProj.getFile("Authors.xml");
		if (authorFile.exists()) {
			System.out.println("Authors already exists!");
			return false;
		}
		linkFile = treeOfKnowledgeProj.getFile("Links.xml");
		if (linkFile.exists()) {
			System.out.println("Links already exists!");
			return false;
		}

		Document authDoc = DocumentHelper.createDocument();

		// Create the Skeleton of the authors file
		Element authElm = authDoc.addElement("authors");
		Element inAuthElm = authElm.addElement("authorsGroup");
		inAuthElm.addElement("id").addText("1");
		inAuthElm.addElement("name").addText("first author group");
		inAuthElm.addElement("nextGroupId").addText("2");
		inAuthElm.addElement("prevGroupId").addText("0");

		inAuthElm = authElm.addElement("authorsGroup");
		inAuthElm.addElement("id").addText("2");
		inAuthElm.addElement("name").addText("second author group");
		inAuthElm.addElement("nextGroupId").addText("3");
		inAuthElm.addElement("prevGroupId").addText("1");

		inAuthElm = authElm.addElement("authorsGroup");
		inAuthElm.addElement("id").addText("3");
		inAuthElm.addElement("name").addText("third author group");
		inAuthElm.addElement("nextGroupId").addText("4");
		inAuthElm.addElement("prevGroupId").addText("2");

		inAuthElm = authElm.addElement("authorsGroup");
		inAuthElm.addElement("id").addText("4");
		inAuthElm.addElement("name").addText("fourth author group");
		inAuthElm.addElement("nextGroupId").addText("5");
		inAuthElm.addElement("prevGroupId").addText("3");

		inAuthElm = authElm.addElement("authorsGroup");
		inAuthElm.addElement("id").addText("5");
		inAuthElm.addElement("name").addText("fifth author group");
		inAuthElm.addElement("nextGroupId").addText("0");
		inAuthElm.addElement("prevGroupId").addText("4");

		try {

			OutputFormat outformat = OutputFormat.createPrettyPrint();
			outformat.setEncoding("UTF-8");
			IPath res = authorFile.getLocation();
			FileWriter fw = new FileWriter(res.toOSString());
			XMLWriter writer = new XMLWriter(fw, outformat);
			writer.write(authDoc);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		Document linkDoc = DocumentHelper.createDocument();

		// Create the Skeleton of the Links file
		linkDoc.addElement("links");

		try {
			// authorFile.create(IResource.NONE, true, progMonitor);

			OutputFormat outformat = OutputFormat.createPrettyPrint();
			outformat.setEncoding("UTF-8");
			IPath res = linkFile.getLocation();
			FileWriter fw = new FileWriter(res.toOSString());
			XMLWriter writer = new XMLWriter(fw, outformat);
			writer.write(linkDoc);
			writer.flush();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public boolean setTokRoot(String rootPath) throws CoreException,
			IOException {
		// checking file exists
		File tempFile = new File(rootPath);

		if (!tempFile.exists() || !isExtentionLegel(rootPath, "src")) {
			System.out.println("file is none existant or extention not src");
			return false;
		}

		// getting the root name
		String rootName = getRootName(rootPath);

		// This should add the new root file to sources folder
		rootFile = srcFolder.getFile(rootName);
		FileInputStream fins = new FileInputStream(rootPath);
		rootFile.create(fins, true, this.progMonitor);

		// setting the root atribute
		QualifiedName name = new QualifiedName("TOK Root File", "Is Root");
		rootFile.setPersistentProperty(name, "true");

		// see if we can get the property
		// System.out.println("is root:"+ rootFile.getPersistentProperty(name));
		return true;
	}

	private boolean isExtentionLegel(String rootPath, String ext) {
		int dotLoc = rootPath.lastIndexOf('.');
		if (dotLoc == -1)
			return false;
		else {
			String extension = rootPath.substring(dotLoc + 1);
			if (extension.equalsIgnoreCase(ext) == false) {
				return false;
			}
		}
		return true;
	}

	private String getRootName(String rootPath) {
		int slashLoc = rootPath.lastIndexOf('\\');
		if (slashLoc == -1)
			return rootPath;
		else
			return rootPath.substring(slashLoc + 1);
	}

	// Evgeni
	public void addSource(String filePathGivenByUser) {
		try {
			// Validate source.xml file with source.xsd
			final String sl = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(sl);
			StreamSource ss = new StreamSource(Activator.sourceXsdPath
					+ "source.xsd");
			Schema schema = factory.newSchema(ss);
			Validator validator = schema.newValidator();
			File fileToValidate = new File(filePathGivenByUser);
			validator.validate(new StreamSource(fileToValidate));
			// System.out.println("Source file is valid ");
		} catch (Exception e) {
			System.out.println("FAILED validating source file ");
			return;
		}

		/*
		 * yet another validation method SAXParserFactory spf =
		 * SAXParserFactory.newInstance(); spf.setSchema(schema); SAXParser
		 * parser = spf.newSAXParser(); parser.parse(is, dh);
		 */

		String filePathVarified = new String(filePathGivenByUser);
		int beginIndex = filePathVarified.lastIndexOf("\\");
		String fileNameVarified = filePathVarified.substring(beginIndex);

		// check if file with that name exists in Sources folder
		if (!filePathGivenByUser.equals(treeOfKnowledgeProj.getFullPath()
				.toString()
				+ "/Sources/" + fileNameVarified)) {
			try {
				// This should add the new source file to sources folder
				IFile sourceFile = srcFolder.getFile(fileNameVarified);
				FileInputStream fins = new FileInputStream(filePathVarified);
				sourceFile.create(fins, true, this.progMonitor);
				// System.out.println("Source file was added to ToK ");
			} catch (Exception e) {
				System.out.println("FAILED to add source file to ToK ");
				return;
			}

			/*
			 * yet another copying method IFile tokSrcFile =
			 * m_srcFolder.getFile(srcFile.getName()); FileInputStream fins =
			 * new FileInputStream(srcFile.getName()); tokSrcFile.create(fins,
			 * true, this.progMonitor);
			 */
		}

		Author sourceAuthor = new Author();

		try {
			// Open source file and get author name
			Document sourceDocumentObject;
			File sourceFile = new File(filePathVarified);
			SAXReader sourceReader = new SAXReader();
			sourceDocumentObject = sourceReader.read(sourceFile);
			sourceAuthor.name = sourceDocumentObject.getRootElement().element(
					"author").getText();
			// System.out.println("Source's author is " + sourceAuthor.name);
		} catch (Exception e) {
			System.out.println("FAILED to get author name from source file \n"
					+ e.getMessage());
			return;
		}

		try {
			// Open authors XML file
			Document authorsDocumentObject;
			File authorsFile = new File(treeOfKnowledgeProj.getLocationURI()
					.getPath()
					+ "/Authors.xml");
			SAXReader authorsReader = new SAXReader();
			authorsDocumentObject = authorsReader.read(new FileInputStream(
					authorsFile));
			Iterator groupsIterator = authorsDocumentObject.getRootElement()
					.elementIterator("authorsGroup");
			int authorDefaultRank = 3;
			Boolean authorExists = false;

			// check if current author exists in authors XML
			while (groupsIterator.hasNext()) {
				Element groupElement = (Element) groupsIterator.next();
				Iterator authorsIterator = groupElement
						.elementIterator("author");
				while (authorsIterator.hasNext()) {
					Element authorElement = (Element) authorsIterator.next();
					if (authorElement.getTextTrim().equals(sourceAuthor.name)) {
						authorExists = true;
						sourceAuthor.rank = Integer.parseInt(groupElement
								.element("id").getText());
						break;
					}
				}
			}

			// if author exists in authors XML
			if (authorExists) {
				System.out.println("Author allready exists ");
			} else {
				// System.out.println("Author DOESN'T exist ");
				sourceAuthor.rank = authorDefaultRank;
				AddAuthorToFile(sourceAuthor);
			}
			//RankChangeByUser(sourceAuthor);
			 System.out.println("Author's rank changed");
		} catch (Exception e) {
			System.out.println("FAILED to open Aouthors file \n"
					+ e.getMessage());
			return;
		}
	}

	// Evgeni
	private void AddAuthorToFile(Author author) {
		try {
			Document authorsDocumentObject;
			File authorsFile = new File(treeOfKnowledgeProj.getLocationURI()
					.getPath()
					+ "/Authors.xml");
			SAXReader authorsReader = new SAXReader();
			authorsDocumentObject = authorsReader.read(new FileInputStream(
					authorsFile));
			Iterator groupsIterator = authorsDocumentObject.getRootElement()
					.elementIterator("authorsGroup");

			while (groupsIterator.hasNext()) {
				Element element = (Element) groupsIterator.next();
				if (Integer.parseInt(element.element("id").getTextTrim()) == author.rank) {
					Element newAuthorElem = element.addElement("author");
					newAuthorElem.addText(author.name);
					OutputFormat outformat = OutputFormat.createPrettyPrint();
					outformat.setEncoding("UTF-8");
					IPath res = authorFile.getLocation();
					FileWriter fw = new FileWriter(res.toOSString());
					XMLWriter writer = new XMLWriter(fw, outformat);
					writer.write(authorsDocumentObject);
					writer.flush();
				}
			}
			System.out.println("Added new author tag to " + author.rank
					+ " group");
		} catch (Exception e) {
			System.out.println("FAILED to add new author to Authors file");
			return;
		}
	}

	// Evgeni
	private void RemoveAuthorFromFile(Author author) {
		try {
			Document authorsDocumentObject;
			File authorsFile = new File(treeOfKnowledgeProj.getLocationURI()
					.getPath()
					+ "/Authors.xml");
			SAXReader authorsReader = new SAXReader();
			authorsDocumentObject = authorsReader.read(new FileInputStream(
					authorsFile));
			Iterator groupsIterator = authorsDocumentObject.getRootElement()
					.elementIterator("authorsGroup");

			while (groupsIterator.hasNext()) {
				Element groupElement = (Element) groupsIterator.next();
				Iterator authorsIterator = groupElement
						.elementIterator("author");
				while (authorsIterator.hasNext()) {
					Element authorElement = (Element) authorsIterator.next();
					if (authorElement.getTextTrim().equals(author.name)) {
						groupElement.remove(authorElement);
						OutputFormat outformat = OutputFormat
								.createPrettyPrint();
						outformat.setEncoding("UTF-8");
						IPath res = authorFile.getLocation();
						FileWriter fw = new FileWriter(res.toOSString());
						XMLWriter writer = new XMLWriter(fw, outformat);
						writer.write(authorsDocumentObject);
						writer.flush();
						break;
					}
				}
			}
			System.out.println("Removed author tag from " + author.rank
					+ " group");
		} catch (Exception e) {
			System.out.println("FAILED to remove author from Authors file");
			return;
		}
	}

	// Evgeni
	private void ChangeAuthorRank(Author author, int rank) {
		try {
			Document authorsDocumentObject;
			File authorsFile = new File(treeOfKnowledgeProj.getLocationURI()
					.getPath()
					+ "/Authors.xml");
			SAXReader authorsReader = new SAXReader();
			authorsDocumentObject = authorsReader.read(new FileInputStream(
					authorsFile));
			Iterator groupsIterator = authorsDocumentObject.getRootElement()
					.elementIterator("authorsGroup");
			int authorDefaultRank = 3;
			Boolean authorExists = false;

			// check if current author exists in authors XML
			while (groupsIterator.hasNext()) {
				Element groupElement = (Element) groupsIterator.next();
				Iterator authorsIterator = groupElement
						.elementIterator("author");
				while (authorsIterator.hasNext()) {
					Element authorElement = (Element) authorsIterator.next();
					if (authorElement.getTextTrim().equals(author.name)) {
						authorExists = true;
						author.rank = Integer.parseInt(groupElement.element(
								"id").getText());
						break;
					}
				}
			}

			if (authorExists) {
				RemoveAuthorFromFile(author);
				author.rank = rank;
			} else {
				author.rank = authorDefaultRank;
			}

			AddAuthorToFile(author);

			// System.out.println("Author file updated " + author.name + "
			// rank");
		} catch (Exception e) {
			System.out.println("FAILED to update Authors file ");
			return;
		}
	}

	// Evgeni
	public void RankChangeByUser(Author author) {
		try {
			// Didn't validated the input because author rank will be
			// a combo box option (no validation needed)
			System.out.println("Author name = " + author.name
					+ " , Current rank = " + author.rank
					+ "\nType new rank (1 - 5): ");
			char inputRank = (char) System.in.read();
			int newRank = Character.digit(inputRank, Character.MAX_RADIX);
			if (newRank < 1 || newRank > 5) {
				System.out.println(newRank + " is bad rank input !!");
				return;
			}
			if (newRank != author.rank) {
				ChangeAuthorRank(author, newRank);
				// System.out.println("Changed " + author.name + " rank to " +
				// newRank);
			} else {
				// System.out.println(author.name + " rank stays " +
				// author.rank);
			}
		} catch (Exception e) {
			System.out.println("FAILED to change " + author.name + " rank");
			return;
		}

		// TODO: In after first iteration:
		// Pop a dialog which displays Authors name and its current rank
		// and a ComboBox of posible ranks.
		// The user will choose a rank and subbmit or decline.
		// If rank changed, use 'ChangeAuthorRank()' method
	}

	/**
	 * Adds a new discussion to the ToK project
	 * 
	 * @param discName
	 *            the name of the discussion to be created
	 */
	public void addDiscussion(String discName) {

		// Create the Skeleton of the discussion
		Document doc = DocumentHelper.createDocument();
		Element disc = doc.addElement("discussion");
		disc.addElement("name").addText(discName);
		disc.addElement("user").addText(projCreator);
		Element defOpin = disc.addElement("opinion");
		defOpin.addElement("id").addText("1");
		defOpin.addElement("name").addText("Default Opinion");

		IFile file = discFolder.getFile(discName + ".dis");
		if (file.exists()) {
			System.out.println("Discussion already exists!");
		} else {

			// Add the new discussion object to the discussions
			Discussion tempDiscussion = new Discussion(this, discName,
					projCreator);
			discussions.add(tempDiscussion);

			OutputFormat outformat = OutputFormat.createPrettyPrint();
			outformat.setEncoding("UTF-8");
			IPath res = file.getLocation();

			try {
				FileWriter fw = new FileWriter(res.toOSString());
				XMLWriter writer = new XMLWriter(fw, outformat);
				writer.write(doc);
				writer.flush();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	}

	/**
	 * Links an existing discussion to a segment in the root of the ToK project
	 * 
	 * @param disc
	 *            an object representing n existing discussion
	 */
	public void linkDiscussionRoot(Discussion disc, Excerption exp) {

		String discName = disc.getDiscName();

		// Open the Links file
		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = linkFile.getLocation().toOSString();
		File file = new File(path);

		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		// Add pre-defined link element to the Links file
		Element links = doc.getRootElement();
		Element newLink = links.addElement("link");
		newLink.addElement("discussionFile").addText(discName + ".dis");
		newLink.addElement("type").addText("difficulty");
		newLink.add(exp.toXML());
		newLink.addElement("linkSubject").addText("What is an Excerption?!");

		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding("UTF-8");

		try {

			FileWriter fw = new FileWriter(linkFile.getLocation().toOSString());
			XMLWriter writer = new XMLWriter(fw, outformat);
			writer.write(doc);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates a new discussion and links it to a segment in the root of the ToK
	 * project
	 * 
	 * @param discName
	 *            the name of the discussion
	 */
	public void linkNewDiscussionRoot(String discName, Excerption exp) {

		addDiscussion(discName);
		try {
			linkDiscussionRoot(getDiscussion(discName), new Excerption(
					"/Bible/The Begining/The Continue/The First Paragraph", "",
					2, 22));
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "Yearly_Plugin_Project",
				IStatus.OK, message, null);
		throw new CoreException(status);
	}

	public IWorkspace getWorkspace() {
		return workSpaceTok;
	}

	public IProject getProject() {
		return treeOfKnowledgeProj;
	}

	public String getProjectCreator() {
		return projCreator;
	}

	public IFolder getResourceFolder() {
		return srcFolder;
	}

	public IFolder getDiscussionFolder() {
		return discFolder;
	}

	public IFile getRootFile() {
		return rootFile;
	}

	public static ToK getProjectToK(IProject project) {
		try {
			return (ToK) project.getSessionProperty(tokQName);
		} catch (CoreException e) {
			// TODO: First iteration only
			return new ToK();
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public Discussion getDiscussion(String discName) throws CoreException {

		for (int i = 0; i < discussions.size(); i++) {
			if (discussions.get(i).getDiscName().equalsIgnoreCase(discName)) {
				return discussions.get(i);
			}
		}
		throwCoreException("No such discussion exists!");
		return null;

	}

	public List<Discussion> getDiscussions() {
		return discussions;
	}
}
