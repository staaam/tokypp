package lost.tok;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import lost.tok.activator.Activator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;

/**
 * @author Team LOST
 * 
 */
public class ToK {

	private IProject treeOfKnowledgeProj;

	private IProgressMonitor progMonitor;

	private IFolder srcFolder, discFolder, unparsedSrcFolder, rootFolder;

	private IFile authorFile, linkFile;

	private List<Discussion> discussions = null;

	public final static QualifiedName tokQName = new QualifiedName(null,
			"ToK Object"); //$NON-NLS-1$

	/** The QualifiedName of the creatorQName property */
	public final static QualifiedName creatorQName = new QualifiedName(
			"lost.tok", "Creator"); //$NON-NLS-1$ //$NON-NLS-2$

	/** The minimal ranking of authors */
	public static final int MIN_AUTHOR_GROUP = 1;
	/** The maximal ranking of authoers */
	public static final int MAX_AUTHOR_GROUP = 5;
	/** The name of folder of our sources */
	static public final String SOURCES_FOLDER = Messages.getString("ToK.srcFolder"); //$NON-NLS-1$
	/** The name of folder of our roots */
	static public final String ROOTS_FOLDER = Messages.getString("ToK.rootFolder"); //$NON-NLS-1$
	/** The name of the discussions folder */
	static public final String DISCUSSION_FOLDER = Messages.getString("ToK.DiscFolder"); //$NON-NLS-1$
	/** The name of the folder in which we store our unparsed sources */
	static public final String UNPARSED_SOURCES_FOLDER = Messages.getString("ToK.UnparsedFolder"); //$NON-NLS-1$

	public ToK(IProject project) {
		createToKFromProject(project);
	}

	public IFile getLinkFile() {
		createLinksFile();
		return linkFile;
	}

	public IFile getAuthorFile() {
		createAuthorsFile();
		return authorFile;
	}

	/**
	 * Creating a new ToK project
	 * @param projectName
	 * @param creator
	 * @param root
	 */
	public ToK(String projectName, String creator, String root) {
		//checking if a project with the same name already exists
		if (!checkFileName(projectName)) {
			return;
		}

		createToKFromProject(ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName));

		//setting the creator name as a property of the project
		try {
			treeOfKnowledgeProj.setPersistentProperty(creatorQName, creator);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		if(root.length() != 0)
			setTokRoot(root);
	}

	private void createToKFromProject(IProject project) {
		System.out.println("createToKFromProject"); //$NON-NLS-1$

		treeOfKnowledgeProj = project;
		progMonitor = new NullProgressMonitor();

		if (!treeOfKnowledgeProj.exists()) {
			try {
				treeOfKnowledgeProj.create(progMonitor);
			} catch (CoreException e) {
				System.out.println("exception in project create: " + e); //$NON-NLS-1$
			}
		}

		try {
			treeOfKnowledgeProj.open(progMonitor);
		} catch (CoreException e) {
			System.out.println("exception in project open: " + e); //$NON-NLS-1$
		}

		try {
			treeOfKnowledgeProj.setSessionProperty(tokQName, this);
		} catch (CoreException e) {
			System.out.println("exception in setSessionProperty: " + e); //$NON-NLS-1$
		}

		ToKNature.setNature(treeOfKnowledgeProj);

		try {
			createToKLibraries();
		} catch (CoreException e) {
			e.printStackTrace();
		}

		createToKFiles();
		refresh();	
	}

	public static boolean checkFileName(String projectName) {
		if (projectName.replace('\\', '/').indexOf('/', 1) > 0)
			return false;
		
		String name = "/" + projectName; //$NON-NLS-1$
		if (!new Path(name).isValidPath(name)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Creation of the ToK libraries
	 * @return
	 * @throws CoreException
	 */
	public boolean createToKLibraries() throws CoreException {
		srcFolder = treeOfKnowledgeProj.getFolder(SOURCES_FOLDER);
		rootFolder = treeOfKnowledgeProj.getFolder(ROOTS_FOLDER);
		discFolder = treeOfKnowledgeProj.getFolder(DISCUSSION_FOLDER);
		unparsedSrcFolder = treeOfKnowledgeProj
				.getFolder(UNPARSED_SOURCES_FOLDER);

		if (!srcFolder.exists()) {
			srcFolder.create(IResource.NONE, true, progMonitor);
		}
		if (!rootFolder.exists()) {
			rootFolder.create(IResource.NONE, true, progMonitor);
		}
		if (!discFolder.exists()) {
			discFolder.create(IResource.NONE, true, progMonitor);
		}
		if (!unparsedSrcFolder.exists()) {
			unparsedSrcFolder.create(IResource.NONE, true, progMonitor);
		}

		return true;
	}

	public boolean createToKFiles() {
		// creating the files

		createAuthorsFile();

		createLinksFile();

		return true;

	}

	private void createLinksFile() {
		linkFile = treeOfKnowledgeProj.getFile(Messages.getString("ToK.linksFile")); //$NON-NLS-1$
		if (!linkFile.exists()) {
			GeneralFunctions.writeToXml(linkFile, linksSkeleton());
		}
	}

	private void createAuthorsFile() {
		authorFile = treeOfKnowledgeProj.getFile(Messages.getString("ToK.authFile")); //$NON-NLS-1$
		if (!authorFile.exists()) {
			GeneralFunctions.writeToXml(authorFile, authorsSkeleton());
		}
	}

	public Document linksSkeleton() {
		Document linkDoc = DocumentHelper.createDocument();

		// Create the Skeleton of the Links file
		linkDoc.addElement("links"); //$NON-NLS-1$
		return linkDoc;
	}

	private Document authorsSkeleton() {
		Document authDoc = DocumentHelper.createDocument();

		// Create the Skeleton of the authors file
		Element authElm = authDoc.addElement("authors"); //$NON-NLS-1$

		for (int i = MIN_AUTHOR_GROUP; i <= MAX_AUTHOR_GROUP; i++) {
			Element inAuthElm = authElm.addElement("authorsGroup"); //$NON-NLS-1$
			inAuthElm.addElement("id").addText(String.valueOf(i)); //$NON-NLS-1$
			inAuthElm.addElement("name").addText( //$NON-NLS-1$
					"author group " + String.valueOf(i)); //$NON-NLS-1$
			inAuthElm.addElement("nextGroupId").addText( //$NON-NLS-1$
					String.valueOf(nextOf(i)));
			inAuthElm.addElement("prevGroupId").addText( //$NON-NLS-1$
					String.valueOf(prevOf(i)));
		}
		return authDoc;
	}

	private int nextOf(int i) {
		i = i + 1;
		return (i > MAX_AUTHOR_GROUP) ? MIN_AUTHOR_GROUP : i;
	}

	private int prevOf(int i) {
		i = i - 1;
		return (i < MIN_AUTHOR_GROUP) ? MAX_AUTHOR_GROUP : i;
	}

	public boolean setTokRoot(String rootPath) {
		// getting the root name
		String rootName = getRootName(rootPath);

		// This should add the new root file to roots folder
		try {
			rootFolder.getFile(rootName).create(new FileInputStream(rootPath),
					true, progMonitor);
		} catch (FileNotFoundException e) {
			System.out.println("file is none existant or extention not src"); //$NON-NLS-1$
			return false;
		} catch (CoreException e) {
			e.printStackTrace();
		}

		// not relevant, since there may be several sources
		// setting the root atribute
		// QualifiedName name = new QualifiedName("TOK Root File", "Is Root");
		// rootFile.setPersistentProperty(name, "true");

		return true;
	}

	private boolean isExtentionLegel(String rootPath, String ext) {
		int dotLoc = rootPath.lastIndexOf('.');
		if (dotLoc == -1) {
			return false;
		} else {
			String extension = rootPath.substring(dotLoc + 1);
			if (extension.equalsIgnoreCase(ext) == false) {
				return false;
			}
		}
		return true;
	}

	private String getRootName(String rootPath) {
		int slashLoc = rootPath.lastIndexOf('\\');
		if (slashLoc != -1) {
			rootPath = rootPath.substring(slashLoc + 1);
		}

		slashLoc = rootPath.lastIndexOf('/');
		if (slashLoc != -1) {
			rootPath = rootPath.substring(slashLoc + 1);
		}
		
		return rootPath;
	}

	// Evgeni
	public void addSource(String filePathGivenByUser) {
		try {
			// Validate source.xml file with source.xsd
			final String sl = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(sl);
			StreamSource ss = new StreamSource(Activator.sourceXsdPath
					+ "source.xsd"); //$NON-NLS-1$
			Schema schema = factory.newSchema(ss);
			Validator validator = schema.newValidator();
			File fileToValidate = new File(filePathGivenByUser);
			validator.validate(new StreamSource(fileToValidate));
			// System.out.println("Source file is valid ");
		} catch (Exception e) {
			System.out.println("FAILED validating source file "); //$NON-NLS-1$
			return;
		}

		/*
		 * yet another validation method SAXParserFactory spf =
		 * SAXParserFactory.newInstance(); spf.setSchema(schema); SAXParser
		 * parser = spf.newSAXParser(); parser.parse(is, dh);
		 */

		String filePathVarified = new String(filePathGivenByUser);
		int beginIndex = filePathVarified.lastIndexOf("\\"); //$NON-NLS-1$
		String fileNameVarified = filePathVarified.substring(beginIndex);

		// check if file with that name exists in Sources folder
		if (!filePathGivenByUser.equals(treeOfKnowledgeProj.getFullPath()
				.toString()
				+ "/" + SOURCES_FOLDER + "/" + fileNameVarified)) { //$NON-NLS-1$  //$NON-NLS-2$
			try {
				// This should add the new source file to sources folder
				IFile sourceFile = srcFolder.getFile(fileNameVarified);
				FileInputStream fins = new FileInputStream(filePathVarified);
				sourceFile.create(fins, true, progMonitor);
				refresh();
				// System.out.println("Source file was added to ToK ");
			} catch (Exception e) {
				System.out.println("FAILED to add source file to ToK "); //$NON-NLS-1$
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
			Document sourceDocumentObject = GeneralFunctions
					.readFromXML(filePathVarified);
			sourceAuthor.name = sourceDocumentObject.getRootElement().element(
					"author").getText(); //$NON-NLS-1$
			// System.out.println("Source's author is " + sourceAuthor.name);
		} catch (Exception e) {
			System.out.println("FAILED to get author name from source file \n" //$NON-NLS-1$
					+ e.getMessage());
			return;
		}

		try {
			// Open authors XML file
			Document authorsDocumentObject = GeneralFunctions
					.readFromXML(getAuthorFile());

			Iterator groupsIterator = authorsDocumentObject.getRootElement()
					.elementIterator("authorsGroup"); //$NON-NLS-1$
			int authorDefaultRank = 3;
			Boolean authorExists = false;

			// check if current author exists in authors XML
			while (groupsIterator.hasNext()) {
				Element groupElement = (Element) groupsIterator.next();
				Iterator authorsIterator = groupElement
						.elementIterator("author"); //$NON-NLS-1$
				while (authorsIterator.hasNext()) {
					Element authorElement = (Element) authorsIterator.next();
					if (authorElement.getTextTrim().equals(sourceAuthor.name)) {
						authorExists = true;
						sourceAuthor.rank = Integer.parseInt(groupElement
								.element("id").getText()); //$NON-NLS-1$
						break;
					}
				}
			}

			// if author exists in authors XML
			if (authorExists) {
				System.out.println("Author allready exists "); //$NON-NLS-1$
			} else {
				// System.out.println("Author DOESN'T exist ");
				sourceAuthor.rank = authorDefaultRank;
				AddAuthorToFile(sourceAuthor);
			}
			// RankChangeByUser(sourceAuthor);
			System.out.println("Author's rank changed"); //$NON-NLS-1$
		} catch (Exception e) {
			System.out.println("FAILED to open Aouthors file \n" //$NON-NLS-1$
					+ e.getMessage());
			return;
		}
	}

	// Evgeni
	private void AddAuthorToFile(Author author) {
		try {
			Document authorsDocumentObject = GeneralFunctions
					.readFromXML(getAuthorFile());

			Iterator groupsIterator = authorsDocumentObject.getRootElement()
					.elementIterator("authorsGroup"); //$NON-NLS-1$

			while (groupsIterator.hasNext()) {
				Element element = (Element) groupsIterator.next();
				if (Integer.parseInt(element.element("id").getTextTrim()) == author.rank) { //$NON-NLS-1$
					Element newAuthorElem = element.addElement("author"); //$NON-NLS-1$
					newAuthorElem.addText(author.name);
					GeneralFunctions.writeToXml(getAuthorFile(),
							authorsDocumentObject);
				}
			}
			System.out.println("Added new author tag to " + author.rank //$NON-NLS-1$
					+ " group"); //$NON-NLS-1$
		} catch (Exception e) {
			System.out.println("FAILED to add new author to Authors file"); //$NON-NLS-1$
			return;
		}
	}

	// Evgeni
	private void RemoveAuthorFromFile(Author author) {
		try {
			Document authorsDocumentObject = GeneralFunctions
					.readFromXML(getAuthorFile());
			Iterator groupsIterator = authorsDocumentObject.getRootElement()
					.elementIterator("authorsGroup"); //$NON-NLS-1$

			while (groupsIterator.hasNext()) {
				Element groupElement = (Element) groupsIterator.next();
				Iterator authorsIterator = groupElement
						.elementIterator("author"); //$NON-NLS-1$
				while (authorsIterator.hasNext()) {
					Element authorElement = (Element) authorsIterator.next();
					if (authorElement.getTextTrim().equals(author.name)) {
						groupElement.remove(authorElement);
						GeneralFunctions.writeToXml(getAuthorFile(),
								authorsDocumentObject);
						break;
					}
				}
			}
			System.out.println("Removed author tag from " + author.rank //$NON-NLS-1$
					+ " group"); //$NON-NLS-1$
		} catch (Exception e) {
			System.out.println("FAILED to remove author from Authors file"); //$NON-NLS-1$
			return;
		}
	}

	// Evgeni
	private void ChangeAuthorRank(Author author, int rank) {
		try {
			Document authorsDocumentObject = GeneralFunctions
					.readFromXML(getAuthorFile());
			Iterator groupsIterator = authorsDocumentObject.getRootElement()
					.elementIterator("authorsGroup"); //$NON-NLS-1$
			int authorDefaultRank = 3;
			Boolean authorExists = false;

			// check if current author exists in authors XML
			while (groupsIterator.hasNext()) {
				Element groupElement = (Element) groupsIterator.next();
				Iterator authorsIterator = groupElement
						.elementIterator("author"); //$NON-NLS-1$
				while (authorsIterator.hasNext()) {
					Element authorElement = (Element) authorsIterator.next();
					if (authorElement.getTextTrim().equals(author.name)) {
						authorExists = true;
						author.rank = Integer.parseInt(groupElement.element(
								"id").getText()); //$NON-NLS-1$
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

		} catch (Exception e) {
			System.out.println("FAILED to update Authors file "); //$NON-NLS-1$
			return;
		}
	}

	// Evgeni
	public void RankChangeByUser(Author author) {
		try {
			// Didn't validated the input because author rank will be
			// a combo box option (no validation needed)
//			System.out.println("Author name = " + author.name
//					+ " , Current rank = " + author.rank
//					+ "\nType new rank (1 - 5):  8");
//			char inputRank = (char) System.in.read();
			
			System.out.println("Author name = " + author.name //$NON-NLS-1$
					+ " , Current rank = " + author.rank //$NON-NLS-1$
					+ "\nUser enters new rank: 8"); //$NON-NLS-1$
			
			char inputRank = '8';
			int newRank = Character.digit(inputRank, Character.MAX_RADIX);
			if (newRank < 1 || newRank > 5) {
				System.out.println(newRank + " is bad rank input !!"); //$NON-NLS-1$
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
			System.out.println("FAILED to change " + author.name + " rank"); //$NON-NLS-1$ //$NON-NLS-2$
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
		getDiscussions().add(
				new Discussion(this, discName, getProjectCreator()));
		refresh();
	}

	/**
	 * Updates the items displayed in the various folders
	 */
	private void refresh() {
		try {
			treeOfKnowledgeProj.refreshLocal(IResource.DEPTH_INFINITE, progMonitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Links an existing discussion to a segment in the root of the ToK project
	 * 
	 * @param disc
	 *            an object representing an existing discussion
	 */
	public void linkDiscussionRoot(Discussion disc, Source sourceFile,
			Excerption[] exp, String subject, String linkType) {

		String discFileName = disc.getDiscFileName();

		// Open the Links file
		Document doc = GeneralFunctions.readFromXML(getLinkFile());

		Node link = doc.selectSingleNode("//link/discussionFile[text()=\"" //$NON-NLS-1$
				+ discFileName + "\"]"); //$NON-NLS-1$
		Element newLink = null;
		if (link != null) {
			newLink = link.getParent();
		} else {

			Element links = doc.getRootElement();
			newLink = links.addElement("link"); //$NON-NLS-1$
			newLink.addElement("discussionFile").addText(discFileName); //$NON-NLS-1$
			newLink.addElement("type").addText(linkType); //$NON-NLS-1$
			newLink.addElement("linkSubject").addText(subject); //$NON-NLS-1$
		}

		Element subLink = newLink.addElement("sublink"); //$NON-NLS-1$

		for (Excerption element : exp) {

			subLink.addElement("sourceFile").addText(sourceFile.toString()); //$NON-NLS-1$
			subLink.add(element.toXML());

		}

		GeneralFunctions.writeToXml(getLinkFile(), doc);
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "Yearly_Plugin_Project", //$NON-NLS-1$
				IStatus.OK, message, null);
		throw new CoreException(status);
	}

	public IWorkspace getWorkspace() {
		return getProject().getWorkspace();
	}

	public IProject getProject() {
		return treeOfKnowledgeProj;
	}

	public String getProjectCreator() {
		try {
			return treeOfKnowledgeProj.getPersistentProperty(creatorQName);
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the folder in which resource files are stored
	 */
	public IFolder getResourceFolder() {
		return srcFolder;
	}

	/**
	 * Returns the folder in which roots files are stored
	 */
	public IFolder getRootFolder() {
		return rootFolder;
	}
	
	/**
	 * Returns the folder in which discussions are stored
	 */
	public IFolder getDiscussionFolder() {
		return discFolder;
	}

	public static ToK getProjectToK(IProject project) {
		try {
			Object o = project.getSessionProperty(tokQName);
			if (o == null) {
				throw new CoreException(Status.CANCEL_STATUS);
			}
			return (ToK) o;
		} catch (CoreException e) {
			return new ToK(project);
		}
	}

	public Discussion getDiscussion(String discName) throws CoreException {
		List<Discussion> discussions = getDiscussions();
		for (Discussion discussion : discussions) {
			if (discussion.getDiscName().equalsIgnoreCase(discName)) {
				return discussion;
			}
		}
		throwCoreException("No such discussion exists!"); //$NON-NLS-1$
		return null;
	}

	public List<Discussion> getDiscussions() {
		if (discussions == null) {
			loadDiscussions();
		}
		return discussions;
	}

	private void loadDiscussions() {
		discussions = new LinkedList<Discussion>();

		try {
			IResource[] files = getDiscussionFolder().members();
			for (IResource resource : files) {
				if (resource instanceof IFile) {
					IFile file = (IFile) resource;
					if (isExtentionLegel(file.getName(), "dis")) { //$NON-NLS-1$
						discussions.add(new Discussion(this, file.getLocation()
								.toOSString()));
					}
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Returns source object for requested source
	 * 
	 * @param src - relative to the project
	 * @return source object for requested source
	 */
	public Source getSource(String src) {
		return new Source(treeOfKnowledgeProj.getFile(src));
	}
}
