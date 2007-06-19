package editor.diagram.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import editor.diagram.edit.policies.OpinionCanonicalEditPolicy;
import editor.diagram.edit.policies.OpinionItemSemanticEditPolicy;

import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;

/**
 * @generated
 */
public class OpinionEditPart extends DiagramEditPart {

	/**
	 * @generated
	 */
	public static String MODEL_ID = "Discussion"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 79;

	/**
	 * @generated
	 */
	public OpinionEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new OpinionItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new OpinionCanonicalEditPolicy());
	}
}
