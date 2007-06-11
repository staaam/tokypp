package lost.tok.print;

import lost.tok.Discussion;
import lost.tok.Link;

public class PrintDiscussion {

	public Discussion discussion;
	
	
	
	
	
	public void createDiscuttionFile(Discussion d){
		
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
			System.out.println("Related to: " + link.getLinkedSource());
			System.out.println("Link Type " + link.getLinkType());
			
//			the text of the link:
			System.out.println(link.getSubject());
		}
		
		
		//opinions:
		
		
		//bibliography:
		
		
		//signature
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