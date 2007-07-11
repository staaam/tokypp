  


/*
 * Created on Fri Jun 22 20:03:23 IDT 2007
 *
 * Generated by GEMS 
 */
 
package lost.tok.DiscussionViewer;


import org.gems.designer.model.ModelObject;
import org.gems.designer.ModelRepository;
import org.eclipse.emf.common.util.EList;
import org.gems.designer.model.Container;
import org.gems.designer.model.ModelObject;
import org.gems.designer.model.event.ModelChangeEvent;

public class EMFSubtypeImpl extends org.gems.designer.SubtypeImpl {
	public class EMFInstanceUpdater extends InstanceUpdater{
		private lost.tok.DiscussionViewer.emf.SubtypeLink link_;
		
		public EMFInstanceUpdater(ModelObject base, ModelObject m) {
			super(base, m);
			link_ = lost.tok.DiscussionViewer.emf.impl.DiscussionViewerFactoryImpl.eINSTANCE.createSubtypeLink();
			link_.setBase(((EMFModelObject)base).getEMFObject());
			link_.setInstance(((EMFModelObject)m).getEMFObject());
			getModel().getLinks().add(link_);
		}
		
		public EMFInstanceUpdater(ModelObject base, ModelObject m, lost.tok.DiscussionViewer.emf.SubtypeLink link) {
			super(base, m);
			link_ = link;
		}
		

		@Override
		public void unRegister() {
			getModel().getLinks().remove(link_);
			super.unRegister();
		}
		
	}
    lost.tok.DiscussionViewer.emf.Subtype model_;

	public EMFSubtypeImpl(ModelObject obj, String name){
		super(obj,name);
		register();
	}
	
	public EMFSubtypeImpl(ModelObject obj, lost.tok.DiscussionViewer.emf.Subtype emfobj, String name){
		super(obj,name);
		model_ = emfobj;
		
	}
	
	public void createUpdater(ModelObject base, ModelObject cop){
		new EMFInstanceUpdater(base,cop);
	}
	
	public void createUpdater(ModelObject base, ModelObject cop, lost.tok.DiscussionViewer.emf.SubtypeLink link){
		new EMFInstanceUpdater(base,cop,link);
	}
	
	public void addInstance(ModelObject subt){
		addInstance(subt,true);
	}
	
	public void addInstance(ModelObject subt, boolean modifymodel){
		if(modifymodel)
			getModel().getInstances().add(((lost.tok.DiscussionViewer.EMFModelObject)subt).getEMFObject());
		super.addInstance(subt);
	}
	
	public void removeInstance(ModelObject subt){
		getModel().getInstances().remove(((lost.tok.DiscussionViewer.EMFModelObject)subt).getEMFObject());
		super.removeInstance(subt);
	}
	
	public void unRegister(){
		Root root = (Root)ModelRepository.getInstance().getInstanceRepository().getInstance(getBase().getModelInstanceID()).getRoot();
		if(root != null)
			root.getEMFObject().getSubtypes().remove(getModel());
	}
	
	public void register(){
		Root root = (Root)ModelRepository.getInstance().getInstanceRepository().getInstance(getBase().getModelInstanceID()).getRoot();
		if(root != null && !root.getEMFObject().getSubtypes().contains(getModel()))
			root.getEMFObject().getSubtypes().add(getModel());
	}
	
    public lost.tok.DiscussionViewer.emf.Subtype getModel(){
		if(model_ == null){
			model_ = lost.tok.DiscussionViewer.emf.impl.DiscussionViewerFactoryImpl.eINSTANCE.createSubtype();
			model_.setBase(((lost.tok.DiscussionViewer.EMFModelObject)getBase()).getEMFObject());
			//model_.eAdapters().add(this);
			model_.setName(getName());
			register();
		}
		return model_;
	}
}