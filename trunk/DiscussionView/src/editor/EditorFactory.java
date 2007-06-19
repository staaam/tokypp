/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package editor;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see editor.EditorPackage
 * @generated
 */
public interface EditorFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EditorFactory eINSTANCE = editor.impl.EditorFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Opinion</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Opinion</em>'.
	 * @generated
	 */
	Opinion createOpinion();

	/**
	 * Returns a new object of class '<em>Quote</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Quote</em>'.
	 * @generated
	 */
	Quote createQuote();

	/**
	 * Returns a new object of class '<em>Relation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Relation</em>'.
	 * @generated
	 */
	Relation createRelation();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EditorPackage getEditorPackage();

} //EditorFactory
