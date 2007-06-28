package lost.tok.print;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lost.tok.Discussion;
import lost.tok.Link;
import lost.tok.Messages;
import lost.tok.Opinion;
import lost.tok.Quote;
import lost.tok.Source;
import lost.tok.SubLink;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class PrintDiscussion {

	public Discussion discussion;
	
	public PrintDiscussion(Shell shell, Discussion d) {	 
	     
		//create the discussion file
		 File tmp = null;
		 
		try {
			tmp = createDiscuttionFile(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//print the discussion file
		 printDiscussionFile(shell, tmp);
	}
	
	public File createDiscuttionFile(Discussion d) throws IOException{
		this.discussion = d;
		
		if (d==null)
			return null;
		
		IProject tokProject = d.getFile().getProject();
		IFile discIFile = tokProject.getFile(d.getDiscName() + ".txt"); //$NON-NLS-1$
		File discFile = discIFile.getLocation().toFile();
		
		FileWriter fw = new FileWriter(discFile);
		//fw.write("testing 123");
		
		
//		fw.write("=========================================\n");
//		fw.write("=========== DISCUSSION PRINT ============\n");
//		fw.write("=========================================\n");
		
		fw.write(Messages.getString("PrintDiscussion.PrintDiscussionName") + d.getDiscName() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		fw.write(Messages.getString("PrintDiscussion.PrintAuthorName") + d.getCreatorName() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		fw.write(Messages.getString("PrintDiscussion.PrintDescription") + d.getDescription() + "\n"); //$NON-NLS-1$
		
		//if the discussion was linked, the source it was linked to:
		Link link = d.getLink();
		
		if (link!=null){
			
			//the text of the link:
			fw.write(Messages.getString("PrintDiscussion.PrintLinkDescription") + link.getSubject() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
			//the link type
			fw.write(Messages.getString("PrintDiscussion.PrintLinkType") + link.getDisplayLinkType() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
			
			//links to sources:
			fw.write(Messages.getString("PrintDiscussion.PrintRelatedTo")); //$NON-NLS-1$
			for (SubLink sl: link.getSubLinkList()){
				fw.write("\t"+sl.getLinkedSource().toString() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	
		fw.write("\n"); //$NON-NLS-1$
		
		int opinionCounter = 1;
		int pos=1;
		List<Source> sourceList = new ArrayList<Source>();

		// opinions:		
		Opinion[] opinions = discussion.getOpinions();
		for (Opinion o : opinions ){
			fw.write("\n");
			fw.write(opinionCounter+") " + o.getName() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
			
			int quoteCounter = 1;
			
			//quotes
			Quote[] quotes = discussion.getQuotes(o.getName());
			for (Quote q : quotes){
				
				//list of sources for bibliography
				Source quoteSource = q.getSource();
							
//				if (sourceList==null || !(sourceList.contains(quoteSource))){
//					sourceList.add(quoteSource);
//					//pos++;
//				}
				boolean inList=false;
				for (Iterator i = sourceList.iterator(); i.hasNext();) {
					Source s = (Source) i.next();
					if ((s.getFile().getFullPath().toString().equals(
								quoteSource.getFile().getFullPath().toString()))){
						inList=true;
						pos=sourceList.indexOf(s)+1;
						continue;
					}
				}
				
				//this source is not yet in the list of sources
				if (!inList){
					sourceList.add(quoteSource);
					pos=sourceList.size();
				}
				
				if (q.getComment()==""){ //no comment on quote //$NON-NLS-1$
					fw.write("\t"+opinionCounter + "." + quoteCounter+") " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ q.getText() + " [" + pos + "]\n"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				else{ //with comment
					fw.write("\t"+opinionCounter + "." + quoteCounter+") " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ q.getText() + ", <" + Messages.getString("PrintDiscussion.PrintComment") + q.getComment()+"> [" + pos + "]\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					
				}
			
				quoteCounter++;
			}
			opinionCounter++;
		}
		
		
		//bibliography:
		fw.write("\n"); //$NON-NLS-1$
		fw.write(Messages.getString("PrintDiscussion.PrintBibliography")); //$NON-NLS-1$
				
		for (int i=0; i<sourceList.size();i++){
			SourceDocument sd = new SourceDocument();
			sd.set(sourceList.get(i));
			String author = sd.getAuthor();
			fw.write("["+ (i+1) +"]" + sourceList.get(i).getFile().getName() + ", " + author + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		
		//signature
		fw.write("\n"); //$NON-NLS-1$
		fw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"); //$NON-NLS-1$
		fw.write(Messages.getString("PrintDiscussion.PrintSignature")); //$NON-NLS-1$
		fw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"); //$NON-NLS-1$
		
		fw.flush();
		fw.close();
		
		return discFile;
		
	}
	
	public void printDiscussionFile(Shell shell,File file){
		if (file != null) {
			 // Have user select a printer
		     PrintDialog dialog = new PrintDialog(shell);
		     PrinterData printerData = dialog.open();
		     if (printerData != null) {
		    	 // Create the printer
		         Printer printer = new Printer(printerData);
		         try {
		        	 // Print the contents of the file
		        	 new WrappingPrinter(printer, file.getName(), getFileContents(file)).print();
		         } catch (Exception e) {
		           MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		           mb.setMessage(e.getMessage());
		           mb.open();
		         }

		        // Dispose the printer
		        printer.dispose();
		      }
		    }
	}
	
	private String getFileContents(File file) throws IOException {
		  StringBuffer contents = new StringBuffer();
		  BufferedReader reader = null;
		  try {
			  // Read in the file
			  reader = new BufferedReader(new FileReader(file));
			  while (reader.ready()) {
				  contents.append(reader.readLine());
				  contents.append("\n"); // Throw away LF chars, and just replace CR //$NON-NLS-1$
			  }
		  } 
		  finally {
			  if (reader != null) try {
				  reader.close();
			  } catch (IOException e) {}
		  }
		  return contents.toString();
	  }
	
	public void setDiscussion(Discussion d){
		this.discussion = d;
	}
	
	public Discussion getDiscussion(){
		return this.discussion;
	}
}

/*
============================================= 
	<Discussion Name>
	Author: <Name of the user who created the discussion>
	Description: <description>

	Related to: <Root's Name>
	Link Type: <Link Type>
	<The text of the link>

	1 <Opinion 1>
	    1.1 <Quote 1>, <Comment> [1] 
	    1.2 <Quote 2> [2]
	2 <Opinion 2>
	3 <Opinion 3>
	    3.1 <Quote 3> [3]
	    3.2 <Quote 4>, <Comment> [2] 
	    3.3 <Quote 5>, <Comment> [4] [5]

	Bibliography:
	  [1] <Source Name>, <Author Name> 
	  [2] <Source Name>, <Author Name>
	  [3] <Source Name>, <Author Name> 
	  [4] <Source Name>, <Author Name>
	  [5] <Source Name>, <Author Name>
	  
	=============================================
	  LOST - Tree of Knowladge
	============================================= 

*/