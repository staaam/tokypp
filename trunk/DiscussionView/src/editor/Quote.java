/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package editor;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Quote</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link editor.Quote#getOpinion <em>Opinion</em>}</li>
 * </ul>
 * </p>
 *
 * @see editor.EditorPackage#getQuote()
 * @model
 * @generated
 */
public interface Quote extends DiscussionObject {
	/**
	 * Returns the value of the '<em><b>Opinion</b></em>' containment reference.
	 * The default value is <code>""</code>.
	 * It is bidirectional and its opposite is '{@link editor.Relation#getSecond <em>Second</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Opinion</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Opinion</em>' containment reference.
	 * @see #setOpinion(Relation)
	 * @see editor.EditorPackage#getQuote_Opinion()
	 * @see editor.Relation#getSecond
	 * @model opposite="Second" containment="true"
	 * @generated
	 */
	Relation getOpinion();

	/**
	 * Sets the value of the '{@link editor.Quote#getOpinion <em>Opinion</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Opinion</em>' containment reference.
	 * @see #getOpinion()
	 * @generated
	 */
	void setOpinion(Relation value);

} // Quote