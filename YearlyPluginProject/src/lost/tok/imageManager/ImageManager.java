package lost.tok.imageManager;

import java.io.InputStream;
import java.util.EnumMap;

import lost.tok.GeneralFunctions;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageManager {
	static private final String ICONS_FOLDER = "icons/";
	static EnumMap<ImageType, Image> m = new EnumMap<ImageType, Image>(ImageType.class);
	static {
		putImage(ImageType.DISCUSSION, ICONS_FOLDER + "discussion.gif");
		putImage(ImageType.LINK, ICONS_FOLDER + "link_ico.gif");
		putImage(ImageType.OPINION, ICONS_FOLDER + "chat.ico");
		putImage(ImageType.ROOT, ICONS_FOLDER + "opTable.bmp");
		putImage(ImageType.QUOTE, ICONS_FOLDER + "AddQuote.bmp");
		putImage(ImageType.EXCERPTION, ICONS_FOLDER + "exp.bmp");
		putImage(ImageType.COMMENT, ICONS_FOLDER + "comment.bmp");
		putImage(ImageType.DESCRIPTION, ICONS_FOLDER + "edit.jpg");
		putImage(ImageType.TREE_BIG, ICONS_FOLDER + "treeBig.jpg");
		
	}

	private static void putImage(ImageType i, String filename) {
		m.put(i, newImage(filename));
	}
	
	public static Image newImage(String filename) {
		try {
			InputStream inputStream = GeneralFunctions.getInputStream(filename);
			return new Image(Display.getCurrent(), inputStream);
		} catch (Exception e) {
		}
		return null;
	}

	public static Image getImage(ImageType i) {
		return m.get(i);
	}
}
