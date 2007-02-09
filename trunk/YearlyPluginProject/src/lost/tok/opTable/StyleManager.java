package lost.tok.opTable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * Provides default Styles
 */
public class StyleManager {

	final static public Color MARKED_FG_TEXT_COLOR = new Color(Display
			.getCurrent(), 20, 170, 40);

	final static public Color MARKED_BG_TEXT_COLOR = new Color(Display
			.getCurrent(), 240, 255, 240);

	final static public Color NORMAL_FG_TEXT_COLOR = new Color(Display
			.getCurrent(), 0, 0, 0);

	final static public Color UNPARSED_TEXT_FG_COLOR = new Color(Display
			.getCurrent(), 140, 140, 140);

	final static public Color NORMAL_BG_TEXT_COLOR = new Color(Display
			.getCurrent(), 255, 255, 255);

	final static public Color CHAPTER_FG_TEXT_COLOR = new Color(Display
			.getCurrent(), 0, 0, 192);

	final static public Color UNPARSED_CHAPTER_FG_TEXT_COLOR = new Color(
			Display.getCurrent(), 192, 0, 0);

	final static public Color CHAPTER_BG_TEXT_COLOR = new Color(Display
			.getCurrent(), 255, 255, 255);

	final static public Color DISABLED_FG_TEXT_COLOR = new Color(Display
			.getCurrent(), 127, 127, 127);

	/**
	 * Returns the default style for marked text
	 */
	static public StyleRange getMarkedStyle() {
		return new StyleRange(0, 0, MARKED_FG_TEXT_COLOR, MARKED_BG_TEXT_COLOR);
	}

	/**
	 * Returns the default style for normal text
	 */
	static public StyleRange getNormalStyle() {
		return new StyleRange(0, 0, NORMAL_FG_TEXT_COLOR, NORMAL_BG_TEXT_COLOR);
	}

	/**
	 * Returns the default style for unparsed text
	 */
	static public StyleRange getUnparsedStyle() {
		return new StyleRange(0, 0, UNPARSED_TEXT_FG_COLOR,
				NORMAL_BG_TEXT_COLOR);
	}

	/**
	 * Returns the default style for chapter's title
	 */
	static public StyleRange getChapterStyle() {
		return new StyleRange(0, 0, CHAPTER_FG_TEXT_COLOR,
				CHAPTER_BG_TEXT_COLOR, SWT.BOLD);
	}

	/**
	 * Returns the default style for unparsed chapters
	 */
	static public StyleRange getUnparsedChapterStyle() {
		return new StyleRange(0, 0, UNPARSED_CHAPTER_FG_TEXT_COLOR,
				CHAPTER_BG_TEXT_COLOR, SWT.BOLD);
	}

}
