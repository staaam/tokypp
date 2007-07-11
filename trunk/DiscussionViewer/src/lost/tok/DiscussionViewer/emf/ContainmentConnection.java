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
 * A representation of the model object '<em><b>Containment Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.ContainmentConnection#getSource <em>Source</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.ContainmentConnection#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getContainmentConnection()
 * @model
 * @generated
 */
public interface ContainmentConnection extends EObject
{
  /**
   * Returns the value of the '<em><b>Source</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Source</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Source</em>' reference.
   * @see #setSource(Opinion)
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getContainmentConnection_Source()
   * @model
   * @generated
   */
  Opinion getSource();

  /**
   * Sets the value of the '{@link lost.tok.DiscussionViewer.emf.ContainmentConnection#getSource <em>Source</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Source</em>' reference.
   * @see #getSource()
   * @generated
   */
  void setSource(Opinion value);

  /**
   * Returns the value of the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target</em>' reference.
   * @see #setTarget(Quote)
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getContainmentConnection_Target()
   * @model
   * @generated
   */
  Quote getTarget();

  /**
   * Sets the value of the '{@link lost.tok.DiscussionViewer.emf.ContainmentConnection#getTarget <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target</em>' reference.
   * @see #getTarget()
   * @generated
   */
  void setTarget(Quote value);

} // ContainmentConnection