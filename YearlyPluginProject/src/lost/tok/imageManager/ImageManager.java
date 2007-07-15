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

package lost.tok.imageManager;

import java.io.InputStream;
import java.util.EnumMap;

import lost.tok.GeneralFunctions;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageManager {
	static private final String ICONS_FOLDER = "icons/"; //$NON-NLS-1$
	static EnumMap<ImageType, Image> m = new EnumMap<ImageType, Image>(ImageType.class);
	static {
		putImage(ImageType.DISCUSSION, ICONS_FOLDER + "discussion.gif"); //$NON-NLS-1$
		putImage(ImageType.LINK, ICONS_FOLDER + "link_ico.gif"); //$NON-NLS-1$
		putImage(ImageType.OPINION, ICONS_FOLDER + "chat.ico"); //$NON-NLS-1$
		putImage(ImageType.ROOT, ICONS_FOLDER + "opTable.bmp"); //$NON-NLS-1$
		putImage(ImageType.SOURCE, ICONS_FOLDER + "opTable.bmp"); //$NON-NLS-1$
		putImage(ImageType.QUOTE, ICONS_FOLDER + "AddQuote.bmp"); //$NON-NLS-1$
		putImage(ImageType.EXCERPTION, ICONS_FOLDER + "exp.bmp"); //$NON-NLS-1$
		putImage(ImageType.COMMENT, ICONS_FOLDER + "comment.bmp"); //$NON-NLS-1$
		putImage(ImageType.DESCRIPTION, ICONS_FOLDER + "edit.jpg"); //$NON-NLS-1$
		putImage(ImageType.TREE_BIG, ICONS_FOLDER + "treeBig.jpg"); //$NON-NLS-1$
		
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
