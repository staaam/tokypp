/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Dic Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.AbstractDicObject#getRelates1 <em>Relates1</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.AbstractDicObject#getRelates2 <em>Relates2</em>}</li>
 * </ul>
 * </p>
 *
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getAbstractDicObject()
 * @model
 * @generated
 */
public interface AbstractDicObject extends ModelObject
{
  /**
   * Returns the value of the '<em><b>Relates1</b></em>' reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.AbstractDicObject}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Relates1</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Relates1</em>' reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getAbstractDicObject_Relates1()
   * @model type="lost.tok.DiscussionViewer.emf.AbstractDicObject" upper="2147483647"
   * @generated
   */
  EList getRelates1();

  /**
   * Returns the value of the '<em><b>Relates2</b></em>' reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.AbstractDicObject}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Relates2</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Relates2</em>' reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getAbstractDicObject_Relates2()
   * @model type="lost.tok.DiscussionViewer.emf.AbstractDicObject" upper="2147483647"
   * @generated
   */
  EList getRelates2();

} // AbstractDicObject