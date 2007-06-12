package lost.tok;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;

/**
 * @author Team LOST
 * 
 */
public class ToK {

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
	static public final String SOURCES_FOLDER = Messages
			.getString("ToK.srcFolder"); //$NON-NLS-1$

	/** The name of folder of our roots */
	static public final String ROOTS_FOLDER = Messages
			.getString("ToK.rootFolder"); //$NON-NLS-1$

	/** The name of the discussions folder */
	static public final String DISCUSSION_FOLDER = Messages
			.getString("ToK.DiscFolder"); //$NON-NLS-1$

	/** The name of the folder in which we store our unparsed sources */
	static public final String UNPARSED_SOURCES_FOLDER = Messages
			.getString("ToK.UnparsedFolder"); //$NON-NLS-1$

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
	 * Returns ToK object of the given project.
	 * If the project is not ToK project, returns <code>null</code>
	 * 
	 * @param project to get ToK of
	 * 
	 * @return assosiated ToK object or <code>null</code> if project
	 *  is not ToK project
	 */
	public static ToK getProjectToK(IProject project) {
		try {
			Object o = project.getSessionProperty(tokQName);
			if (o != null) {
				return (ToK) o;
			}
			return new ToK(project);
		} catch (CoreException e) {
			System.out.println("getProjectToK failed\n" + e);
		}
		return null;
	}

	private IProject tokProject;

	private IProgressMonitor progMonitor = new NullProgressMonitor();

	private IFolder srcFolder, discFolder, unparsedSrcFolder, rootFolder;

	private IFile authorFile, linkFile;

	private SortedSet<Discussion> discussions = null;

	public ToK(IProject project) throws CoreException {
		createToKFromProject(project);
	}

	/**
	 * Creating a new ToK project
	 * 
	 * @param projectName
	 * @param creator
	 * @param root
	 * @throws CoreException 
	 */
	public ToK(String projectName, String creator, String root) throws CoreException {
		// checking if a project with the same name already exists
		if (!checkFileName(projectName)) {
			GeneralFunctions.throwCoreException("Wrong project name specified");
		}
		
		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		if (!p.exists())
			p.create(null);
			
		p.open(null);
		
		ToKNature.setNature(p);

		createToKFromProject(p);

		// setting the creator name as a property of the project
		p.setPersistentProperty(creatorQName, creator);

		if (root.length() != 0)
			setTokRoot(root);
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
		setLatestDiscussionOpinion(discName, null);
		refresh();
	}

	/**
	 * Stores given discussion and opinion as latest used
	 * Stores it like Persistent Properties of the project
	 * If discussion == null then opinion = null
	 * @param discussion - latest discussion
	 * @param opinion - latest opinion
	 */
	public void setLatestDiscussionOpinion(String discussion, String opinion) {
		if (discussion == null) opinion = null;
		setPersistentProperty(Discussion.LATEST_QNAME, discussion);
		setPersistentProperty(Opinion.LATEST_QNAME, opinion);
	}

	// Evgeni
	public void addSource(String filePathGivenByUser) {
		if (!Source.isValid(new StreamSource(new File(filePathGivenByUser)))) {
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
		if (!filePathGivenByUser.equals(tokProject.getFullPath()
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

	public boolean createToKFiles() {
		// creating the files

		createAuthorsFile();

		createLinksFile();

		return true;

	}

	/**
	 * Creation of the ToK libraries
	 * 
	 * @return
	 * @throws CoreException
	 */
	public boolean createToKLibraries() throws CoreException {
		srcFolder = tokProject.getFolder(SOURCES_FOLDER);
		rootFolder = tokProject.getFolder(ROOTS_FOLDER);
		discFolder = tokProject.getFolder(DISCUSSION_FOLDER);
		unparsedSrcFolder = tokProject
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

	public IFile getAuthorFile() {
		createAuthorsFile();
		return authorFile;
	}

	public Discussion getDiscussion(String discName) throws CoreException {
		SortedSet<Discussion> discussions = getDiscussions();
		for (Discussion discussion : discussions) {
			if (discussion.getDiscName().equalsIgnoreCase(discName)) {
				return discussion;
			}
		}
		GeneralFunctions.throwCoreException("No such discussion exists!"); //$NON-NLS-1$
		return null;
	}

	/**
	 * Returns the folder in which discussions are stored
	 */
	public IFolder getDiscussionFolder() {
		return discFolder;
	}

	public SortedSet<Discussion> getDiscussions() {
		if (discussions == null) {
			loadDiscussions();
		}
		return discussions;
	}

	public IFile getLinkFile() {
		createLinksFile();
		return linkFile;
	}

	public IProject getProject() {
		return tokProject;
	}

	public String getProjectCreator() {
		try {
			return tokProject.getPersistentProperty(creatorQName);
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the folder in which resource files are stored
	 */
	public IFolder getSourcesFolder() {
		return srcFolder;
	}

	/**
	 * Returns the folder in which roots files are stored
	 */
	public IFolder getRootsFolder() {
		return rootFolder;
	}

	/**
	 * Returns source object for requested source
	 * 
	 * @param src -
	 *            relative to the project
	 * @return source object for requested source
	 */
	public Source getSource(String src) {
		return new Source(this, src);
	}
	
	/**
	 * Returns all the sources and roots in the ToK
	 * @return an array containing all the source files (and root files) in the ToK
	 */
	public Source[] getSources() {
		refresh();
		
		LinkedList<Source> srcs = new LinkedList<Source>();
		
		IFolder sourcesFolder = tokProject.getFolder(SOURCES_FOLDER);
		IFolder rootsFolder = tokProject.getFolder(ROOTS_FOLDER);
		
		srcs.addAll( getSources(sourcesFolder) );
		srcs.addAll( getSources(rootsFolder) );
		
		Source[] srcArray = new Source[ srcs.size() ];
		
		return srcs.toArray( srcArray );
	}
	
	/**
	 * Returns all the sources inside the folder (and subfolders)
	 * @param folder the folder to search in
	 * @return a list of all the contained source, in no particular order
	 */
	protected LinkedList<Source> getSources(IFolder folder)
	{
		IResource[] members = null;
		try {
			members = folder.members();
		} catch (CoreException e) {
			// shouldn't happen
			e.printStackTrace();
		}
		
		LinkedList<Source> srcs = new LinkedList<Source>();
		
		for (IResource res : members)
		{
			if (res instanceof IFile)
			{
				IFile file = (IFile)res;
				if (Source.isSource(file))
					srcs.add( new Source(file) );
			}
			else if (res instanceof IFolder)
			{
				IFolder subfolder = (IFolder)res;
				LinkedList<Source> subsrcs = getSources(subfolder); // rec call
				srcs.addAll( subsrcs );
			}
			else
			{
				// Note(Shay): as far as I know, we shouldn't get here
				System.out.println("Unexpected File Type: " + res.toString());
			}
		}
		
		return srcs;
	}

	public IWorkspace getWorkspace() {
		return getProject().getWorkspace();
	}

	
	public Document linksSkeleton() {
		Document linkDoc = DocumentHelper.createDocument();

		// Create the Skeleton of the Links file
		linkDoc.addElement("links"); //$NON-NLS-1$
		return linkDoc;
	}

	/**
	 * Reloads the discussions in the tree
	 */
	public void loadDiscussions() {
		discussions = new TreeSet<Discussion>();

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
			e.printStackTrace();
		}

	}

	// Evgeni
	public void RankChangeByUser(Author author) {
		try {
			// Didn't validated the input because author rank will be
			// a combo box option (no validation needed)
			// System.out.println("Author name = " + author.name
			// + " , Current rank = " + author.rank
			// + "\nType new rank (1 - 5): 8");
			// char inputRank = (char) System.in.read();

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

	public Document authorsSkeleton() {
		Document authDoc = DocumentHelper.createDocument();

		// Create the Skeleton of the authors file
		Element authElm = authDoc.addElement("authors"); //$NON-NLS-1$

		for (int i = MIN_AUTHOR_GROUP; i <= MAX_AUTHOR_GROUP; i++) {
			Element inAuthElm = authElm.addElement("authorsGroup"); //$NON-NLS-1$
			inAuthElm.addElement("id").addText(String.valueOf(i)); //$NON-NLS-1$
			inAuthElm.addElement("name").addText( //$NON-NLS-1$
					"Rank " + String.valueOf(i)); //$NON-NLS-1$
			inAuthElm.addElement("nextGroupId").addText( //$NON-NLS-1$
					String.valueOf(nextOf(i)));
			inAuthElm.addElement("prevGroupId").addText( //$NON-NLS-1$
					String.valueOf(prevOf(i)));
		}
		return authDoc;
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

	private void createAuthorsFile() {
		authorFile = tokProject.getFile(Messages
				.getString("ToK.authFile")); //$NON-NLS-1$
		if (!authorFile.exists()) {
			GeneralFunctions.writeToXml(authorFile, authorsSkeleton());
		}
	}

	private void createLinksFile() {
		linkFile = tokProject.getFile(Messages
				.getString("ToK.linksFile")); //$NON-NLS-1$
		if (!linkFile.exists()) {
			GeneralFunctions.writeToXml(linkFile, linksSkeleton());
		}
	}

	private void createToKFromProject(IProject project) throws CoreException {
		System.out.println("createToKFromProject"); //$NON-NLS-1$

		if (project.getNature(ToKNature.NATURE_ID) == null)
			GeneralFunctions.throwCoreException("Project is not ToK project");
		
		tokProject = project;

		tokProject.setSessionProperty(tokQName, this);

		createToKLibraries();

		createToKFiles();

		refresh();
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

	private int nextOf(int i) {
		i = i + 1;
		return (i > MAX_AUTHOR_GROUP) ? MIN_AUTHOR_GROUP : i;
	}

	private int prevOf(int i) {
		i = i - 1;
		return (i < MIN_AUTHOR_GROUP) ? MAX_AUTHOR_GROUP : i;
	}

	/**
	 * Updates the items displayed in the various folders
	 */
	private void refresh() {
		try {
			tokProject.refreshLocal(IResource.DEPTH_INFINITE,
					progMonitor);
		} catch (CoreException e) {
			e.printStackTrace();
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

	/**
	 * Returns the value of the persistent property of this resource identified
	 * by the given key, or <code>null</code> if this resource has no such property.
	 *
	 * @param key the qualified name of the property
	 * @return the string value of the property, 
	 *     or <code>null</code> if this resource has no such property
	 * @see #setPersistentProperty(QualifiedName, String)
	 */
	public String getPersistentProperty(QualifiedName key) {
		try {
			return getProject().getPersistentProperty(key);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sets the value of the persistent property of this resource identified
	 * by the given key. If the supplied value is <code>null</code>,
	 * the persistent property is removed from this resource. The change
	 * is made immediately on disk.
	 * <p>
	 * Persistent properties are intended to be used by plug-ins to store
	 * resource-specific information that should be persisted across platform sessions.
	 * The value of a persistent property is a string that must be short -
	 * 2KB or less in length. Unlike session properties, persistent properties are
	 * stored on disk and maintained across workspace shutdown and restart.
	 * </p>
	 * <p>
	 * The qualifier part of the property name must be the unique identifier
	 * of the declaring plug-in (e.g. <code>"com.example.plugin"</code>).
	 * </p>
	 *
	 * @param key the qualified name of the property
	 * @param value the string value of the property, 
	 *     or <code>null</code> if the property is to be removed
	 * @see #getPersistentProperty(QualifiedName)
	 */
	private void setPersistentProperty(QualifiedName key, String value) {
		try {
			getProject().setPersistentProperty(key, value);
		} catch (CoreException e) {
			e.printStackTrace();
		}		
	}
}
