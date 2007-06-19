/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package editor;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Discussion Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link editor.DiscussionObject#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see editor.EditorPackage#getDiscussionObject()
 * @model abstract="true"
 * @generated
 */
public interface DiscussionObject extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see editor.EditorPackage#getDiscussionObject_Id()
	 * @model default=""
	 * @generated
	 */
	int getId();

	/**
	 * Sets the value of the '{@link editor.DiscussionObject#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(int value);

} // DiscussionObject