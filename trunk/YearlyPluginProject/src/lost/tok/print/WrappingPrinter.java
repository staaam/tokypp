package lost.tok.print;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.Printer;

/**
 * This class performs the printing, wrapping text as necessary
 */
public class WrappingPrinter {
	private Printer printer; // The printer
	private String fileName; // The name of the file to print
	private String contents; // The contents of the file to print
	private GC gc; // The GC to print on
	private int xPos, yPos; // The current x and y locations for print
	private Rectangle bounds; // The boundaries for the print
	private StringBuffer buf; // Holds a word at a time
	private int lineHeight; // The height of a line of text
	
	/**
	* WrappingPrinter constructor
	* @param printer the printer
	* @param fileName the fileName
	* @param contents the contents
	*/
	public WrappingPrinter(Printer printer, String fileName, String contents) {
		this.printer = printer;
		this.fileName = fileName;
		this.contents = contents;
	}

  /**
   * Prints the file
   */
  public void print() {
    // Start the print job
    if (printer.startJob(fileName)) {
      // Determine print area, with margins
      bounds = computePrintArea(printer);
      xPos = bounds.x;
      yPos = bounds.y;
      
      // Create the GC
      gc = new GC(printer);	     
      //MICHAL
      //  gc.getFont().getFontData()[0].setHeight(14);
      
      // Determine line height
      lineHeight = gc.getFontMetrics().getHeight();
      
      // Determine tab width--use three spaces for tabs
      int tabWidth = gc.stringExtent("   ").x;
      
      // Print the text
      printer.startPage();
      buf = new StringBuffer();
      char c;
      for (int i = 0, n = contents.length(); i < n; i++) {
        // Get the next character
        c = contents.charAt(i);
        
        // Check for newline
        if (c == '\n') {
          printBuffer();
          printNewline();
        }
        // Check for tab
        else if (c == '\t') {
          xPos += tabWidth;
        }
        else {
          buf.append(c);
          // Check for space
          if (Character.isWhitespace(c)) {
            printBuffer();
          }
        }
      }

      printer.endPage();
      printer.endJob();
      gc.dispose();
    }
  }

  /**
   * Prints the contents of the buffer
   */
  public void printBuffer() {
    // Get the width of the rendered buffer
    int width = gc.stringExtent(buf.toString()).x;
    
    // Determine if it fits
    if (xPos + width > bounds.x + bounds.width) {
      // Doesn't fit--wrap
      printNewline();
    }
    
    // Print the buffer
    gc.drawString(buf.toString(), xPos, yPos, false);
    xPos += width;
    buf.setLength(0);
  }

  /**
   * Prints a newline
   */
  void printNewline() {
    // Reset x and y locations to next line
    xPos = bounds.x;
    yPos += lineHeight;
    
    // Have we gone to the next page?
    if (yPos > bounds.y + bounds.height) {
      yPos = bounds.y;
      printer.endPage();
      printer.startPage();
    }
  }

  /**
   * Computes the print area, including margins
   * @param printer the printer
   * @return Rectangle
   */
  public Rectangle computePrintArea(Printer printer) {
    // Get the printable area
    Rectangle rect = printer.getClientArea();
    
    // Compute the trim
    Rectangle trim = printer.computeTrim(0, 0, 0, 0);
    
    // Get the printer's DPI
    Point dpi = printer.getDPI();
    
    // Calculate the printable area, using 1 inch margins
    int left = trim.x + dpi.x;
    if (left < rect.x) left = rect.x;
    
    int right = (rect.width + trim.x + trim.width) - dpi.x;
    if (right > rect.width) right = rect.width;
    
    int top = trim.y + dpi.y;
    if (top < rect.y) top = rect.y;
    
    int bottom = (rect.height + trim.y + trim.height) - dpi.y;
    if (bottom > rect.height) bottom = rect.height;
    
    return new Rectangle(left, top, right - left, bottom - top);
  }
}