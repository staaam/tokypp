/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Memento Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.MementoValue#getName <em>Name</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.MementoValue#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getMementoValue()
 * @model
 * @generated
 */
public interface MementoValue extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getMementoValue_Name()
   * @model default="0"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link lost.tok.DiscussionViewer.emf.MementoValue#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(String)
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getMementoValue_Value()
   * @model default="0"
   * @generated
   */
  String getValue();

  /**
   * Sets the value of the '{@link lost.tok.DiscussionViewer.emf.MementoValue#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(String value);

} // MementoValue