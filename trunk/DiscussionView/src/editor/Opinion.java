/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package editor;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Opinion</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link editor.Opinion#getName <em>Name</em>}</li>
 *   <li>{@link editor.Opinion#getRelates <em>Relates</em>}</li>
 * </ul>
 * </p>
 *
 * @see editor.EditorPackage#getOpinion()
 * @model
 * @generated
 */
public interface Opinion extends DiscussionObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>"General"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see editor.EditorPackage#getOpinion_Name()
	 * @model default="General"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link editor.Opinion#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Relates</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link editor.Relation#getFirst <em>First</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relates</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relates</em>' containment reference.
	 * @see #setRelates(Relation)
	 * @see editor.EditorPackage#getOpinion_Relates()
	 * @see editor.Relation#getFirst
	 * @model opposite="First" containment="true"
	 * @generated
	 */
	Relation getRelates();

	/**
	 * Sets the value of the '{@link editor.Opinion#getRelates <em>Relates</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relates</em>' containment reference.
	 * @see #getRelates()
	 * @generated
	 */
	void setRelates(Relation value);

} // Opinion