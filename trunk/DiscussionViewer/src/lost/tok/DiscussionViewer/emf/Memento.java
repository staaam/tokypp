/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Memento</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Memento#getId <em>Id</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Memento#getData <em>Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getMemento()
 * @model
 * @generated
 */
public interface Memento extends EObject
{
  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * The default value is <code>"0"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(String)
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getMemento_Id()
   * @model default="0"
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link lost.tok.DiscussionViewer.emf.Memento#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(String value);

  /**
   * Returns the value of the '<em><b>Data</b></em>' containment reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.MementoValue}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Data</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Data</em>' containment reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getMemento_Data()
   * @model type="lost.tok.DiscussionViewer.emf.MementoValue" containment="true" upper="2000"
   * @generated
   */
  EList getData();

} // Memento