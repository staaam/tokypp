package lost.tok.print;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import lost.tok.Discussion;
import lost.tok.Link;
import lost.tok.Opinion;
import lost.tok.Quote;
import lost.tok.Source;
import lost.tok.SubLink;
import lost.tok.sourceDocument.SourceDocument;

public class PrintDiscussion {

	public Discussion discussion;
	
	public void createDiscuttionFile(Discussion d){
		
		this.discussion = d;
		
		if (d==null)
			return;
		
		System.out.println("=========================================");
		System.out.println("=========== DISCUSSION PRINT ============");
		System.out.println("=========================================");
		
		System.out.println("Discussion Name: " + d.getDiscName());
		System.out.println("Author: " + d.getCreatorName());
		//to un-comment when getDescription() will be created
		//System.out.println("Description: " + d.getDescription());
		
		//if the discussion was linked, the source it was linked to:
		Link link = d.getLink();
		
		if (link!=null){
			
			//the text of the link:
			System.out.println(link.getSubject());
			//the link type
			System.out.println("Link Type " + link.getDisplayLinkType());
			
			//links to sources:
			System.out.println("Related to: ");
			for (SubLink sl: link.getSubLinkList()){
				System.out.println(sl.getLinkedSource());
			}
		}
	
		System.out.println("");
		
		int opinionCounter = 1;
		int pos=1;
		List<Source> sourceList = new ArrayList<Source>();

		// opinions:		
		Opinion[] opinions = discussion.getOpinions();
		for (Opinion o : opinions ){
			System.out.println(opinionCounter+") " + o.getName());
			
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
				
				if (q.getComment()==""){ //no comment on quote
					System.out.println("	"+opinionCounter + "." + quoteCounter+") "
							+ q.getText() + " [" + pos + "]");
				}
				else{ //with comment
					System.out.println("	"+opinionCounter + "." + quoteCounter+") "
							+ q.getText() + ", <comment: " + q.getComment()+"> [" + pos + "]");
					
				}
			
				quoteCounter++;
			}
			opinionCounter++;
		}
		
		
		//bibliography:
		System.out.println("");
		System.out.println("Bibliography:");
				
		for (int i=0; i<sourceList.size();i++){
			SourceDocument sd = new SourceDocument();
			sd.set(sourceList.get(i));
			String author = sd.getAuthor();
			System.out.println("["+ (i+1) +"]" + sourceList.get(i).getFile().getName() + ", " + author);
		}
		
		//signature
		System.out.println("");
		System.out.println("=========================================");
		System.out.println("======= LOST - Tree of Knowladge ========");
		System.out.println("=========================================");
		
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
	link class??
*/