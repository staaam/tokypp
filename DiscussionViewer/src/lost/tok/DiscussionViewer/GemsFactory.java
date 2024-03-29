

/*
 * Created on Fri Jun 22 20:03:23 IDT 2007
 *
 * Generated by GEMS 
 */
 
package lost.tok.DiscussionViewer;


import java.util.Hashtable;
import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.gems.designer.model.Container;
import org.gems.designer.model.LinkedModel;
import org.gems.designer.model.ModelObject;
import org.gems.designer.ModelInstance;
import org.gems.designer.ModelRepository;
import org.gems.designer.model.Wire;

public class GemsFactory{
	private Root root_;
	//private LinkedList conns_;
	private Hashtable<Object, ModelObject> parts_ = new Hashtable<Object, ModelObject>();;
	private LinkedList<Wire> wires_ = new LinkedList<Wire>();
	private LinkedList<Runnable> requests_ = new LinkedList<Runnable>();
	private Hashtable connected_ = new Hashtable();
	
	public ModelInstance loadModel(lost.tok.DiscussionViewer.emf.Root root){
		Root groot = load(root);
		ModelInstance inst = ModelRepository.getInstance().getInstanceRepository().getInstance(groot.getModelInstanceID());
		inst.setRoot(groot);
		
		for(Runnable req : requests_)
			req.run();
		
		EList subts = root.getSubtypes();
    	for(Object obj : subts){
    		org.gems.designer.Subtype st = createSubtype((lost.tok.DiscussionViewer.emf.Subtype)obj);
    		inst.addSubType(st);
    	}
    	
    	return inst;
	}
	
	public void checkLinking(LinkedModel model){
		if(model.getModelLinkTarget() != null
		   && model.getModelLinkTarget().trim().length() > 0){
		   model.loadModel();  
		   map(model);
		}
	}
	
	public void map(ModelObject mo){
		if(mo instanceof EMFModelObject){
			EObject obj = ((EMFModelObject)mo).getEMFObject();
			String key = obj.eResource().getURI().toString()+"#"+mo.getID();
			parts_.put(key, mo);
		}
		if(mo instanceof Container){
			Container cont = (Container)mo;
			for(Object o : cont.getChildren()){
				if(o instanceof ModelObject){
					map((ModelObject)o);
				}
			}
		}
	}
	
	public Root load(lost.tok.DiscussionViewer.emf.Root root){
		Root groot = new Root(root);
		//root_ = groot;
		//conns_ = new LinkedList();
		//wires_ = new LinkedList<Wire>();
		//parts_ = new Hashtable<Object, ModelObject>();
		
		parts_.put(root,groot);
		
		
		lost.tok.DiscussionViewer.emf.DiscussionViewer rrm = root.getRealRoot();
		if(rrm != null){
			DiscussionViewer rr = (DiscussionViewer)getPart(groot, rrm);
			groot.setRealRoot(rr);			
		}
		
		
		
    	EList abstractdicobject = root.getAbstractDicObject();
    	for(Object obj : abstractdicobject){
    		getPart(groot,obj);
    	}
    	
    	
    	EList opinion = root.getOpinion();
    	for(Object obj : opinion){
    		getPart(groot,obj);
    	}
    	
    	
    	EList quote = root.getQuote();
    	for(Object obj : quote){
    		getPart(groot,obj);
    	}
    	
    	
    	EList discussionviewer = root.getDiscussionViewer();
    	for(Object obj : discussionviewer){
    		getPart(groot,obj);
    	}
    	
    	
    	EList relationcons = root.getRelationConnection();
    	for(Object obj : relationcons){
    		createRelationConnection(groot, (lost.tok.DiscussionViewer.emf.RelationConnection)obj);
    	} 
    	
    	EList containmentcons = root.getContainmentConnection();
    	for(Object obj : containmentcons){
    		createContainmentConnection(groot, (lost.tok.DiscussionViewer.emf.ContainmentConnection)obj);
    	} 
    	
    	
    	
    	
    	return groot;
	}
    
    public ModelObject load(Container root, Object child) {
    	if(child instanceof lost.tok.DiscussionViewer.emf.AbstractDicObject){
    		return loadAbstractDicObject(root,(lost.tok.DiscussionViewer.emf.AbstractDicObject)child);
    	}
    	
    	if(child instanceof lost.tok.DiscussionViewer.emf.Opinion){
    		return loadOpinion(root,(lost.tok.DiscussionViewer.emf.Opinion)child);
    	}
    	
    	if(child instanceof lost.tok.DiscussionViewer.emf.Quote){
    		return loadQuote(root,(lost.tok.DiscussionViewer.emf.Quote)child);
    	}
    	
    	if(child instanceof lost.tok.DiscussionViewer.emf.DiscussionViewer){
    		return loadDiscussionViewer(root,(lost.tok.DiscussionViewer.emf.DiscussionViewer)child);
    	}
    	
    	return null;
    }
    
    	public AbstractDicObject loadAbstractDicObject(Container root, lost.tok.DiscussionViewer.emf.AbstractDicObject obj){
    	   AbstractDicObject gobj = null;
    	   
    	   if(obj instanceof lost.tok.DiscussionViewer.emf.Opinion){
    	   	 return loadOpinion(root,(lost.tok.DiscussionViewer.emf.Opinion)obj);
    	   }
    	   
    	   else {
    	   
    	   gobj = new AbstractDicObject(obj);  
    	   }
    	   loadAbstractDicObject(root,obj,gobj);
    	   parts_.put(obj,gobj);
    	   if(obj.eResource() != null){
    	     parts_.put(obj.eResource().getURI().toString()+"#"+obj.getId(),gobj);
    	   }
    	   return gobj;
    	}
   	
    	public Opinion loadOpinion(Container root, lost.tok.DiscussionViewer.emf.Opinion obj){
    	   Opinion gobj = null;
    	   
    	   gobj = new Opinion(obj);  
    	   
    	   loadOpinion(root,obj,gobj);
    	   parts_.put(obj,gobj);
    	   if(obj.eResource() != null){
    	     parts_.put(obj.eResource().getURI().toString()+"#"+obj.getId(),gobj);
    	   }
    	   return gobj;
    	}
   	
    	public Quote loadQuote(Container root, lost.tok.DiscussionViewer.emf.Quote obj){
    	   Quote gobj = null;
    	   
    	   gobj = new Quote(obj);  
    	   
    	   loadQuote(root,obj,gobj);
    	   parts_.put(obj,gobj);
    	   if(obj.eResource() != null){
    	     parts_.put(obj.eResource().getURI().toString()+"#"+obj.getId(),gobj);
    	   }
    	   return gobj;
    	}
   	
    	public DiscussionViewer loadDiscussionViewer(Container root, lost.tok.DiscussionViewer.emf.DiscussionViewer obj){
    	   DiscussionViewer gobj = null;
    	   
    	   gobj = new DiscussionViewer(obj);  
    	   
    	   loadDiscussionViewer(root,obj,gobj);
    	   parts_.put(obj,gobj);
    	   if(obj.eResource() != null){
    	     parts_.put(obj.eResource().getURI().toString()+"#"+obj.getId(),gobj);
    	   }
    	   return gobj;
    	}
   	
   	
   	 
    	public AbstractDicObject loadAbstractDicObject(Container root, lost.tok.DiscussionViewer.emf.AbstractDicObject obj, AbstractDicObject gobj){
    	   
    	   if(root != null)
    	     root.addChild(gobj,-1,false);
    	   
    	
    	   
    	   
    	   
    	   
    	   
    	   if(gobj instanceof LinkedModel){
    	   	  checkLinking((LinkedModel)gobj);
    	   }
    	   
    	   
    	   addDeferredRequest(new AbstractDicObjectConnector(root,obj,gobj));
    	   
    	   
    	   return gobj;
    	}
    	
    	
    	public class AbstractDicObjectConnector implements Runnable {
    	  private Container root_;
    	  private lost.tok.DiscussionViewer.emf.AbstractDicObject obj_;
    	  private AbstractDicObject gobj_;
    	  
    	  public AbstractDicObjectConnector(Container root, lost.tok.DiscussionViewer.emf.AbstractDicObject obj, AbstractDicObject gobj){
    	    root_ = root;
    	    obj_ = obj;
    	    gobj_ = gobj;
    	  }
    	  public void run(){
    		loadConnections(root_,obj_,gobj_);
    	  }
    	}
    	
    	public void loadConnections(Container root, lost.tok.DiscussionViewer.emf.AbstractDicObject obj, AbstractDicObject gobj){
    	
    	   EList Relates1 = obj.getRelates1();
    	   for(Object cobj : Relates1){
    	     createRelationConnection(root, gobj,getPart(root,cobj,false));
    	   }
    	   
    	   
    	   
    	   EList Relates2 = obj.getRelates2();
    	   for(Object cobj : Relates2){
    	     createRelationConnection(root, getPart(root,cobj,false),gobj);
    	   }
    	   
    	 }
    	
   	
    	public Opinion loadOpinion(Container root, lost.tok.DiscussionViewer.emf.Opinion obj, Opinion gobj){
    	   
    	   	loadAbstractDicObject(root,obj,gobj);
    	   
    	
    	   
    	   
    	   
    	   
    	   
    	   if(gobj instanceof LinkedModel){
    	   	  checkLinking((LinkedModel)gobj);
    	   }
    	   
    	   
    	   addDeferredRequest(new OpinionConnector(root,obj,gobj));
    	   
    	   
    	   return gobj;
    	}
    	
    	
    	public class OpinionConnector implements Runnable {
    	  private Container root_;
    	  private lost.tok.DiscussionViewer.emf.Opinion obj_;
    	  private Opinion gobj_;
    	  
    	  public OpinionConnector(Container root, lost.tok.DiscussionViewer.emf.Opinion obj, Opinion gobj){
    	    root_ = root;
    	    obj_ = obj;
    	    gobj_ = gobj;
    	  }
    	  public void run(){
    		loadConnections(root_,obj_,gobj_);
    	  }
    	}
    	
    	public void loadConnections(Container root, lost.tok.DiscussionViewer.emf.Opinion obj, Opinion gobj){
    	
    	   EList oneOpinion = obj.getOneOpinion();
    	   for(Object cobj : oneOpinion){
    	     createContainmentConnection(root, gobj,getPart(root,cobj,false));
    	   }
    	   
    	   
    	   
    	 }
    	
   	
    	public Quote loadQuote(Container root, lost.tok.DiscussionViewer.emf.Quote obj, Quote gobj){
    	   
    	   	loadAbstractDicObject(root,obj,gobj);
    	   
    	
    	   
    	   
    	   
    	   
    	   
    	   if(gobj instanceof LinkedModel){
    	   	  checkLinking((LinkedModel)gobj);
    	   }
    	   
    	   
    	   addDeferredRequest(new QuoteConnector(root,obj,gobj));
    	   
    	   
    	   return gobj;
    	}
    	
    	
    	public class QuoteConnector implements Runnable {
    	  private Container root_;
    	  private lost.tok.DiscussionViewer.emf.Quote obj_;
    	  private Quote gobj_;
    	  
    	  public QuoteConnector(Container root, lost.tok.DiscussionViewer.emf.Quote obj, Quote gobj){
    	    root_ = root;
    	    obj_ = obj;
    	    gobj_ = gobj;
    	  }
    	  public void run(){
    		loadConnections(root_,obj_,gobj_);
    	  }
    	}
    	
    	public void loadConnections(Container root, lost.tok.DiscussionViewer.emf.Quote obj, Quote gobj){
    	
    	   
    	   
    	   EList manyQuotes = obj.getManyQuotes();
    	   for(Object cobj : manyQuotes){
    	     createContainmentConnection(root, getPart(root,cobj,false),gobj);
    	   }
    	   
    	 }
    	
   	
    	public DiscussionViewer loadDiscussionViewer(Container root, lost.tok.DiscussionViewer.emf.DiscussionViewer obj, DiscussionViewer gobj){
    	   
    	   if(root != null)
    	     root.addChild(gobj,-1,false);
    	   
    	
    	   
    	   
    	   
    	   
    	   
    	   if(gobj instanceof LinkedModel){
    	   	  checkLinking((LinkedModel)gobj);
    	   }
    	   
    	   
    	   
    	   return gobj;
    	}
    	
    	
   	
   	public org.gems.designer.Subtype createSubtype(lost.tok.DiscussionViewer.emf.Subtype subt){
   		ModelObject base = parts_.get(subt.getBase());
   		String name = subt.getName();
   		EMFSubtypeImpl esub = new EMFSubtypeImpl(base,subt,name);
   		for(Object obj : subt.getInstances()){
   			esub.addInstance(parts_.get(obj),false);
   		}
   		
 
		for(Object lo : subt.getLinks()){
		   lost.tok.DiscussionViewer.emf.SubtypeLink link = (lost.tok.DiscussionViewer.emf.SubtypeLink)lo;
		   esub.createUpdater(parts_.get(link.getBase()),parts_.get(link.getInstance()),link);   
		}
   		return esub;
   	}
   
    public void createRelationConnection(Container root, lost.tok.DiscussionViewer.emf.RelationConnection con){
        if(con.getSource() == null || con.getTarget() ==  null)
           return;
    	ModelObject src = parts_.get(con.getSource());
    	ModelObject trg = parts_.get(con.getTarget());
    	if(src == null || trg == null)
    	   return;
    	   
    	if(connected_.get(src.getID()+"->"+trg.getID()+"::RelationConnection") != null)
    	   return;
    	   
    	if(!isContainedOrLinked(root, src) || !isContainedOrLinked(root, trg))
    		return;
    		
    	Wire w = new Wire();
    	w.setSource(src,false);
    	w.setTarget(trg,false);
    	w.setSourceTerminal("0");
    	w.setTargetTerminal("0");
    	EMFRelationProxy proxy = new EMFRelationProxy(con);
    	w.setConnectionType(RelationConnectionType.INSTANCE, false);
    	RelationConnectionType.INSTANCE.installAttributes(w,proxy);
    	src.connectOutput(w,false);
    	trg.connectInput(w,false);
    	wires_.add(w);
    	connected_.put(src.getID()+"->"+trg.getID()+"::RelationConnection",Boolean.TRUE);
    }
    
    public void createRelationConnection(Container root, ModelObject src, ModelObject trg){

    	if(src == null || trg == null)
    	   return;
    	   
    	if(connected_.get(src.getID()+"->"+trg.getID()+"::RelationConnection") != null)
    	   return;
    	   
    	if(!isContainedOrLinked(root, src) || !isContainedOrLinked(root, trg))
    		return;
    		
    	Wire w = new Wire();
    	w.setSource(src,false);
    	w.setTarget(trg,false);
    	w.setSourceTerminal("0");
    	w.setTargetTerminal("0");
    	w.setConnectionType(RelationConnectionType.INSTANCE, false);
    	src.connectOutput(w,false);
    	trg.connectInput(w,false);
    	wires_.add(w);
    	connected_.put(src.getID()+"->"+trg.getID()+"::RelationConnection",Boolean.TRUE);
    }
    public void createContainmentConnection(Container root, lost.tok.DiscussionViewer.emf.ContainmentConnection con){
        if(con.getSource() == null || con.getTarget() ==  null)
           return;
    	ModelObject src = parts_.get(con.getSource());
    	ModelObject trg = parts_.get(con.getTarget());
    	if(src == null || trg == null)
    	   return;
    	   
    	if(connected_.get(src.getID()+"->"+trg.getID()+"::ContainmentConnection") != null)
    	   return;
    	   
    	if(!isContainedOrLinked(root, src) || !isContainedOrLinked(root, trg))
    		return;
    		
    	Wire w = new Wire();
    	w.setSource(src,false);
    	w.setTarget(trg,false);
    	w.setSourceTerminal("0");
    	w.setTargetTerminal("0");
    	EMFContainmentProxy proxy = new EMFContainmentProxy(con);
    	w.setConnectionType(ContainmentConnectionType.INSTANCE, false);
    	ContainmentConnectionType.INSTANCE.installAttributes(w,proxy);
    	src.connectOutput(w,false);
    	trg.connectInput(w,false);
    	wires_.add(w);
    	connected_.put(src.getID()+"->"+trg.getID()+"::ContainmentConnection",Boolean.TRUE);
    }
    
    public void createContainmentConnection(Container root, ModelObject src, ModelObject trg){

    	if(src == null || trg == null)
    	   return;
    	   
    	if(connected_.get(src.getID()+"->"+trg.getID()+"::ContainmentConnection") != null)
    	   return;
    	   
    	if(!isContainedOrLinked(root, src) || !isContainedOrLinked(root, trg))
    		return;
    		
    	Wire w = new Wire();
    	w.setSource(src,false);
    	w.setTarget(trg,false);
    	w.setSourceTerminal("0");
    	w.setTargetTerminal("0");
    	w.setConnectionType(ContainmentConnectionType.INSTANCE, false);
    	src.connectOutput(w,false);
    	trg.connectInput(w,false);
    	wires_.add(w);
    	connected_.put(src.getID()+"->"+trg.getID()+"::ContainmentConnection",Boolean.TRUE);
    }
    
    public ModelObject getPart(Container root, Object key){
		return getPart(root,key,true);
	}
	
	public ModelObject getPart(Object key){
		return getPart(null,key,true);
	}
	
	public boolean isContainedOrLinked(Container root, ModelObject curr){
    	return root.firstCommonParent(curr) != null;
    }
	
	public ModelObject getPart(Container root, Object key, boolean load){
		ModelObject part = parts_.get(key);
		if(part == null && key instanceof lost.tok.DiscussionViewer.emf.ModelObject && key != null){
		    lost.tok.DiscussionViewer.emf.ModelObject obj = (lost.tok.DiscussionViewer.emf.ModelObject)key;
		    if(obj.eResource() != null)
		   	   part = parts_.get(obj.eResource().getURI().toString()+"#"+obj.getId());
		}
		if(part == null && load){
			part = load(root,key);
		}
		return part;
	}
	
	public void addDeferredRequest(Runnable request){
		requests_.add(request);
	}
}

