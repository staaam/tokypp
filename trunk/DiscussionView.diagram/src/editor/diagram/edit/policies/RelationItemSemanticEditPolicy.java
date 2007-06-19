package editor.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.notation.View;
import editor.Relation;

import editor.diagram.providers.DiscussionElementTypes;

import org.eclipse.gef.commands.UnexecutableCommand;

/**
 * @generated
 */
public class RelationItemSemanticEditPolicy extends
		DiscussionBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return getMSLWrapper(new DestroyElementCommand(req) {

			protected EObject getElementToDestroy() {
				View view = (View) getHost().getModel();
				EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
				if (annotation != null) {
					return view;
				}
				return super.getElementToDestroy();
			}

		});
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (DiscussionElementTypes.RelationSecond_3001 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingRelation_Second3001Command(req)
					: null;
		}
		if (DiscussionElementTypes.RelationFirst_3002 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingRelation_First3002Command(req)
					: null;
		}
		return super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingRelation_Second3001Command(
			CreateRelationshipRequest req) {
		Relation element = (Relation) getSemanticElement();
		if (element.getSecond() != null) {
			return UnexecutableCommand.INSTANCE;
		}

		return new Command() {
		};
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingRelation_First3002Command(
			CreateRelationshipRequest req) {
		Relation element = (Relation) getSemanticElement();
		if (element.getFirst() != null) {
			return UnexecutableCommand.INSTANCE;
		}

		return new Command() {
		};
	}
}
