package lost.tok.opTable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * Provides default Styles
 */
public class StyleManager {

	/** The Foreground color for marked text */
	final static public Color MARKED_FG_TEXT_COLOR = new Color(Display
			.getCurrent(), 20, 170, 40);

	/** The Background color for marked text */
	final static public Color MARKED_BG_TEXT_COLOR = new Color(Display
			.getCurrent(), 240, 255, 240);

	/** The normal foreground color */
	final static public Color NORMAL_FG_TEXT_COLOR = new Color(Display
			.getCurrent(), 0, 0, 0);

	/** The foreground color of an unparsed text */
	final static public Color UNPARSED_TEXT_FG_COLOR = new Color(Display
			.getCurrent(), 140, 140, 140);

	/** The background color of a normal text */
	final static public Color NORMAL_BG_TEXT_COLOR = new Color(Display
			.getCurrent(), 255, 255, 255);

	/** The foreground color of a chapter's title */
	final static public Color CHAPTER_FG_TEXT_COLOR = new Color(Display
			.getCurrent(), 0, 0, 192);

	/** The foreground color of an unparsed chapter */
	final static public Color UNPARSED_CHAPTER_FG_TEXT_COLOR = new Color(
			Display.getCurrent(), 192, 0, 0);

	/** The background color of a chapter */
	final static public Color CHAPTER_BG_TEXT_COLOR = new Color(Display
			.getCurrent(), 255, 255, 255);

	/** The forground color of disabled text */
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
