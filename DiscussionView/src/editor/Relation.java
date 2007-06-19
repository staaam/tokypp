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
 * A representation of the model object '<em><b>Relation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link editor.Relation#getFirst <em>First</em>}</li>
 *   <li>{@link editor.Relation#getSecond <em>Second</em>}</li>
 * </ul>
 * </p>
 *
 * @see editor.EditorPackage#getRelation()
 * @model
 * @generated
 */
public interface Relation extends EObject {
	/**
	 * Returns the value of the '<em><b>First</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link editor.Opinion#getRelates <em>Relates</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>First</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First</em>' container reference.
	 * @see #setFirst(DiscussionObject)
	 * @see editor.EditorPackage#getRelation_First()
	 * @see editor.Opinion#getRelates
	 * @model opposite="relates"
	 * @generated
	 */
	DiscussionObject getFirst();

	/**
	 * Sets the value of the '{@link editor.Relation#getFirst <em>First</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First</em>' container reference.
	 * @see #getFirst()
	 * @generated
	 */
	void setFirst(DiscussionObject value);

	/**
	 * Returns the value of the '<em><b>Second</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link editor.Quote#getOpinion <em>Opinion</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Second</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Second</em>' container reference.
	 * @see #setSecond(DiscussionObject)
	 * @see editor.EditorPackage#getRelation_Second()
	 * @see editor.Quote#getOpinion
	 * @model opposite="opinion"
	 * @generated
	 */
	DiscussionObject getSecond();

	/**
	 * Sets the value of the '{@link editor.Relation#getSecond <em>Second</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second</em>' container reference.
	 * @see #getSecond()
	 * @generated
	 */
	void setSecond(DiscussionObject value);

} // Relation