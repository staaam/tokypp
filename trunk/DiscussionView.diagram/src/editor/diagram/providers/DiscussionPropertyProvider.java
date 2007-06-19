package editor.diagram.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.properties.GetPropertySourceOperation;
import org.eclipse.gmf.runtime.common.ui.services.properties.ICompositePropertySource;
import org.eclipse.gmf.runtime.common.ui.services.properties.IPropertiesProvider;
import org.eclipse.gmf.runtime.emf.ui.properties.providers.GenericEMFPropertiesProvider;
import org.eclipse.gmf.runtime.notation.View;
import editor.diagram.edit.parts.OpinionEditPart;

import editor.diagram.part.DiscussionVisualIDRegistry;

/**
 * @generated
 */
public class DiscussionPropertyProvider extends GenericEMFPropertiesProvider
		implements IPropertiesProvider {

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetPropertySourceOperation) {
			Object object = ((GetPropertySourceOperation) operation)
					.getObject();
			return getSemanticElement(object) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public ICompositePropertySource getPropertySource(Object object) {
		EObject element = getSemanticElement(object);
		if (element != null) {
			return super.getPropertySource(element);
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected EObject getSemanticElement(Object object) {
		View view = null;
		if (object instanceof View) {
			view = (View) object;
		} else if (object instanceof EditPart) {
			EditPart editPart = (EditPart) object;
			if (editPart.getModel() instanceof View) {
				view = (View) editPart.getModel();
			}
		}
		if (view != null
				&& OpinionEditPart.MODEL_ID.equals(DiscussionVisualIDRegistry
						.getModelID(view))) {
			return view.getElement();
		}
		return null;
	}

}
