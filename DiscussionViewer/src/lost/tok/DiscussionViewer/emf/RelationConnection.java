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
 * A representation of the model object '<em><b>Relation Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.RelationConnection#getSource <em>Source</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.RelationConnection#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRelationConnection()
 * @model
 * @generated
 */
public interface RelationConnection extends EObject
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
   * @see #setSource(AbstractDicObject)
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRelationConnection_Source()
   * @model
   * @generated
   */
  AbstractDicObject getSource();

  /**
   * Sets the value of the '{@link lost.tok.DiscussionViewer.emf.RelationConnection#getSource <em>Source</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Source</em>' reference.
   * @see #getSource()
   * @generated
   */
  void setSource(AbstractDicObject value);

  /**
   * Returns the value of the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target</em>' reference.
   * @see #setTarget(AbstractDicObject)
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRelationConnection_Target()
   * @model
   * @generated
   */
  AbstractDicObject getTarget();

  /**
   * Sets the value of the '{@link lost.tok.DiscussionViewer.emf.RelationConnection#getTarget <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target</em>' reference.
   * @see #getTarget()
   * @generated
   */
  void setTarget(AbstractDicObject value);

} // RelationConnection