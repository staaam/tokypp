package editor.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import editor.diagram.edit.parts.OpinionEditPart;
import editor.diagram.edit.parts.RelationEditPart;

import editor.diagram.part.DiscussionVisualIDRegistry;

import editor.diagram.view.factories.OpinionViewFactory;
import editor.diagram.view.factories.RelationFirstViewFactory;
import editor.diagram.view.factories.RelationSecondViewFactory;
import editor.diagram.view.factories.RelationHasViewFactory;
import editor.diagram.view.factories.RelationHaveViewFactory;
import editor.diagram.view.factories.RelationViewFactory;

/**
 * @generated
 */
public class DiscussionViewProvider extends AbstractViewProvider {

	/**
	 * @generated
	 */
	protected Class getDiagramViewClass(IAdaptable semanticAdapter,
			String diagramKind) {
		EObject semanticElement = getSemanticElement(semanticAdapter);
		if (OpinionEditPart.MODEL_ID.equals(diagramKind)
				&& DiscussionVisualIDRegistry
						.getDiagramVisualID(semanticElement) != -1) {
			return OpinionViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		if (containerView == null) {
			return null;
		}
		IElementType elementType = getSemanticElementType(semanticAdapter);
		if (elementType != null
				&& !DiscussionElementTypes.isKnownElementType(elementType)) {
			return null;
		}
		EClass semanticType = getSemanticEClass(semanticAdapter);
		EObject semanticElement = getSemanticElement(semanticAdapter);
		int nodeVID = DiscussionVisualIDRegistry.getNodeVisualID(containerView,
				semanticElement, semanticType, semanticHint);
		switch (nodeVID) {
		case RelationEditPart.VISUAL_ID:
			return RelationViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		IElementType elementType = getSemanticElementType(semanticAdapter);
		if (elementType != null
				&& !DiscussionElementTypes.isKnownElementType(elementType)) {
			return null;
		}
		if (DiscussionElementTypes.RelationSecond_3001.equals(elementType)) {
			return RelationSecondViewFactory.class;
		}
		if (DiscussionElementTypes.RelationFirst_3002.equals(elementType)) {
			return RelationFirstViewFactory.class;
		}
		EClass semanticType = getSemanticEClass(semanticAdapter);
		if (semanticType == null) {
			return null;
		}
		EObject semanticElement = getSemanticElement(semanticAdapter);
		int linkVID = DiscussionVisualIDRegistry.getLinkWithClassVisualID(
				semanticElement, semanticType);
		switch (linkVID) {
		}
		return getUnrecognizedConnectorViewClass(semanticAdapter,
				containerView, semanticHint);
	}

	/**
	 * @generated
	 */
	private IElementType getSemanticElementType(IAdaptable semanticAdapter) {
		if (semanticAdapter == null) {
			return null;
		}
		return (IElementType) semanticAdapter.getAdapter(IElementType.class);
	}

	/**
	 * @generated
	 */
	private Class getUnrecognizedConnectorViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		// Handle unrecognized child node classes here
		return null;
	}

}
