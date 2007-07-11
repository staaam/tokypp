/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerFactory
 * @model kind="package"
 * @generated
 */
public interface DiscussionViewerPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "lost.tok.DiscussionViewer.emf";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "lost.tok.discussionViewer";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "discussionviewer";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  DiscussionViewerPackage eINSTANCE = lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl.init();

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.MementoValueImpl <em>Memento Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.MementoValueImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getMementoValue()
   * @generated
   */
  int MEMENTO_VALUE = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMENTO_VALUE__NAME = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMENTO_VALUE__VALUE = 1;

  /**
   * The number of structural features of the '<em>Memento Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMENTO_VALUE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.MementoImpl <em>Memento</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.MementoImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getMemento()
   * @generated
   */
  int MEMENTO = 1;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMENTO__ID = 0;

  /**
   * The feature id for the '<em><b>Data</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMENTO__DATA = 1;

  /**
   * The number of structural features of the '<em>Memento</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMENTO_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.SubtypeImpl <em>Subtype</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.SubtypeImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getSubtype()
   * @generated
   */
  int SUBTYPE = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTYPE__NAME = 0;

  /**
   * The feature id for the '<em><b>Base</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTYPE__BASE = 1;

  /**
   * The feature id for the '<em><b>Instances</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTYPE__INSTANCES = 2;

  /**
   * The feature id for the '<em><b>Links</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTYPE__LINKS = 3;

  /**
   * The number of structural features of the '<em>Subtype</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTYPE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.SubtypeLinkImpl <em>Subtype Link</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.SubtypeLinkImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getSubtypeLink()
   * @generated
   */
  int SUBTYPE_LINK = 3;

  /**
   * The feature id for the '<em><b>Base</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTYPE_LINK__BASE = 0;

  /**
   * The feature id for the '<em><b>Instance</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTYPE_LINK__INSTANCE = 1;

  /**
   * The number of structural features of the '<em>Subtype Link</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTYPE_LINK_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.ModelObjectImpl <em>Model Object</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.ModelObjectImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getModelObject()
   * @generated
   */
  int MODEL_OBJECT = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__NAME = 0;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__ID = 1;

  /**
   * The feature id for the '<em><b>X</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__X = 2;

  /**
   * The feature id for the '<em><b>Y</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__Y = 3;

  /**
   * The feature id for the '<em><b>Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__WIDTH = 4;

  /**
   * The feature id for the '<em><b>Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__HEIGHT = 5;

  /**
   * The feature id for the '<em><b>Expanded Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__EXPANDED_WIDTH = 6;

  /**
   * The feature id for the '<em><b>Expanded Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__EXPANDED_HEIGHT = 7;

  /**
   * The feature id for the '<em><b>Expanded</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__EXPANDED = 8;

  /**
   * The feature id for the '<em><b>Subtype</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__SUBTYPE = 9;

  /**
   * The feature id for the '<em><b>Visible</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__VISIBLE = 10;

  /**
   * The feature id for the '<em><b>Model Link Target</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT__MODEL_LINK_TARGET = 11;

  /**
   * The number of structural features of the '<em>Model Object</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_OBJECT_FEATURE_COUNT = 12;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.AbstractDicObjectImpl <em>Abstract Dic Object</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.AbstractDicObjectImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getAbstractDicObject()
   * @generated
   */
  int ABSTRACT_DIC_OBJECT = 5;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__NAME = MODEL_OBJECT__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__ID = MODEL_OBJECT__ID;

  /**
   * The feature id for the '<em><b>X</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__X = MODEL_OBJECT__X;

  /**
   * The feature id for the '<em><b>Y</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__Y = MODEL_OBJECT__Y;

  /**
   * The feature id for the '<em><b>Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__WIDTH = MODEL_OBJECT__WIDTH;

  /**
   * The feature id for the '<em><b>Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__HEIGHT = MODEL_OBJECT__HEIGHT;

  /**
   * The feature id for the '<em><b>Expanded Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__EXPANDED_WIDTH = MODEL_OBJECT__EXPANDED_WIDTH;

  /**
   * The feature id for the '<em><b>Expanded Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__EXPANDED_HEIGHT = MODEL_OBJECT__EXPANDED_HEIGHT;

  /**
   * The feature id for the '<em><b>Expanded</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__EXPANDED = MODEL_OBJECT__EXPANDED;

  /**
   * The feature id for the '<em><b>Subtype</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__SUBTYPE = MODEL_OBJECT__SUBTYPE;

  /**
   * The feature id for the '<em><b>Visible</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__VISIBLE = MODEL_OBJECT__VISIBLE;

  /**
   * The feature id for the '<em><b>Model Link Target</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__MODEL_LINK_TARGET = MODEL_OBJECT__MODEL_LINK_TARGET;

  /**
   * The feature id for the '<em><b>Relates1</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__RELATES1 = MODEL_OBJECT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Relates2</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT__RELATES2 = MODEL_OBJECT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Abstract Dic Object</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_DIC_OBJECT_FEATURE_COUNT = MODEL_OBJECT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.OpinionImpl <em>Opinion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.OpinionImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getOpinion()
   * @generated
   */
  int OPINION = 6;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__NAME = ABSTRACT_DIC_OBJECT__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__ID = ABSTRACT_DIC_OBJECT__ID;

  /**
   * The feature id for the '<em><b>X</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__X = ABSTRACT_DIC_OBJECT__X;

  /**
   * The feature id for the '<em><b>Y</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__Y = ABSTRACT_DIC_OBJECT__Y;

  /**
   * The feature id for the '<em><b>Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__WIDTH = ABSTRACT_DIC_OBJECT__WIDTH;

  /**
   * The feature id for the '<em><b>Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__HEIGHT = ABSTRACT_DIC_OBJECT__HEIGHT;

  /**
   * The feature id for the '<em><b>Expanded Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__EXPANDED_WIDTH = ABSTRACT_DIC_OBJECT__EXPANDED_WIDTH;

  /**
   * The feature id for the '<em><b>Expanded Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__EXPANDED_HEIGHT = ABSTRACT_DIC_OBJECT__EXPANDED_HEIGHT;

  /**
   * The feature id for the '<em><b>Expanded</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__EXPANDED = ABSTRACT_DIC_OBJECT__EXPANDED;

  /**
   * The feature id for the '<em><b>Subtype</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__SUBTYPE = ABSTRACT_DIC_OBJECT__SUBTYPE;

  /**
   * The feature id for the '<em><b>Visible</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__VISIBLE = ABSTRACT_DIC_OBJECT__VISIBLE;

  /**
   * The feature id for the '<em><b>Model Link Target</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__MODEL_LINK_TARGET = ABSTRACT_DIC_OBJECT__MODEL_LINK_TARGET;

  /**
   * The feature id for the '<em><b>Relates1</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__RELATES1 = ABSTRACT_DIC_OBJECT__RELATES1;

  /**
   * The feature id for the '<em><b>Relates2</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__RELATES2 = ABSTRACT_DIC_OBJECT__RELATES2;

  /**
   * The feature id for the '<em><b>One Opinion</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION__ONE_OPINION = ABSTRACT_DIC_OBJECT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Opinion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPINION_FEATURE_COUNT = ABSTRACT_DIC_OBJECT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.QuoteImpl <em>Quote</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.QuoteImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getQuote()
   * @generated
   */
  int QUOTE = 7;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__NAME = ABSTRACT_DIC_OBJECT__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__ID = ABSTRACT_DIC_OBJECT__ID;

  /**
   * The feature id for the '<em><b>X</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__X = ABSTRACT_DIC_OBJECT__X;

  /**
   * The feature id for the '<em><b>Y</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__Y = ABSTRACT_DIC_OBJECT__Y;

  /**
   * The feature id for the '<em><b>Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__WIDTH = ABSTRACT_DIC_OBJECT__WIDTH;

  /**
   * The feature id for the '<em><b>Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__HEIGHT = ABSTRACT_DIC_OBJECT__HEIGHT;

  /**
   * The feature id for the '<em><b>Expanded Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__EXPANDED_WIDTH = ABSTRACT_DIC_OBJECT__EXPANDED_WIDTH;

  /**
   * The feature id for the '<em><b>Expanded Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__EXPANDED_HEIGHT = ABSTRACT_DIC_OBJECT__EXPANDED_HEIGHT;

  /**
   * The feature id for the '<em><b>Expanded</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__EXPANDED = ABSTRACT_DIC_OBJECT__EXPANDED;

  /**
   * The feature id for the '<em><b>Subtype</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__SUBTYPE = ABSTRACT_DIC_OBJECT__SUBTYPE;

  /**
   * The feature id for the '<em><b>Visible</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__VISIBLE = ABSTRACT_DIC_OBJECT__VISIBLE;

  /**
   * The feature id for the '<em><b>Model Link Target</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__MODEL_LINK_TARGET = ABSTRACT_DIC_OBJECT__MODEL_LINK_TARGET;

  /**
   * The feature id for the '<em><b>Relates1</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__RELATES1 = ABSTRACT_DIC_OBJECT__RELATES1;

  /**
   * The feature id for the '<em><b>Relates2</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__RELATES2 = ABSTRACT_DIC_OBJECT__RELATES2;

  /**
   * The feature id for the '<em><b>Many Quotes</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE__MANY_QUOTES = ABSTRACT_DIC_OBJECT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Quote</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUOTE_FEATURE_COUNT = ABSTRACT_DIC_OBJECT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.DiscussionViewerImpl <em>Discussion Viewer</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getDiscussionViewer()
   * @generated
   */
  int DISCUSSION_VIEWER = 8;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__NAME = MODEL_OBJECT__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__ID = MODEL_OBJECT__ID;

  /**
   * The feature id for the '<em><b>X</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__X = MODEL_OBJECT__X;

  /**
   * The feature id for the '<em><b>Y</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__Y = MODEL_OBJECT__Y;

  /**
   * The feature id for the '<em><b>Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__WIDTH = MODEL_OBJECT__WIDTH;

  /**
   * The feature id for the '<em><b>Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__HEIGHT = MODEL_OBJECT__HEIGHT;

  /**
   * The feature id for the '<em><b>Expanded Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__EXPANDED_WIDTH = MODEL_OBJECT__EXPANDED_WIDTH;

  /**
   * The feature id for the '<em><b>Expanded Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__EXPANDED_HEIGHT = MODEL_OBJECT__EXPANDED_HEIGHT;

  /**
   * The feature id for the '<em><b>Expanded</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__EXPANDED = MODEL_OBJECT__EXPANDED;

  /**
   * The feature id for the '<em><b>Subtype</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__SUBTYPE = MODEL_OBJECT__SUBTYPE;

  /**
   * The feature id for the '<em><b>Visible</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__VISIBLE = MODEL_OBJECT__VISIBLE;

  /**
   * The feature id for the '<em><b>Model Link Target</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER__MODEL_LINK_TARGET = MODEL_OBJECT__MODEL_LINK_TARGET;

  /**
   * The number of structural features of the '<em>Discussion Viewer</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISCUSSION_VIEWER_FEATURE_COUNT = MODEL_OBJECT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.RelationConnectionImpl <em>Relation Connection</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.RelationConnectionImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getRelationConnection()
   * @generated
   */
  int RELATION_CONNECTION = 9;

  /**
   * The feature id for the '<em><b>Source</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RELATION_CONNECTION__SOURCE = 0;

  /**
   * The feature id for the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RELATION_CONNECTION__TARGET = 1;

  /**
   * The number of structural features of the '<em>Relation Connection</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RELATION_CONNECTION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.ContainmentConnectionImpl <em>Containment Connection</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.ContainmentConnectionImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getContainmentConnection()
   * @generated
   */
  int CONTAINMENT_CONNECTION = 10;

  /**
   * The feature id for the '<em><b>Source</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTAINMENT_CONNECTION__SOURCE = 0;

  /**
   * The feature id for the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTAINMENT_CONNECTION__TARGET = 1;

  /**
   * The number of structural features of the '<em>Containment Connection</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTAINMENT_CONNECTION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link lost.tok.DiscussionViewer.emf.impl.RootImpl <em>Root</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see lost.tok.DiscussionViewer.emf.impl.RootImpl
   * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getRoot()
   * @generated
   */
  int ROOT = 11;

  /**
   * The feature id for the '<em><b>Mementos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROOT__MEMENTOS = 0;

  /**
   * The feature id for the '<em><b>Subtypes</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROOT__SUBTYPES = 1;

  /**
   * The feature id for the '<em><b>Real Root</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROOT__REAL_ROOT = 2;

  /**
   * The feature id for the '<em><b>Abstract Dic Object</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROOT__ABSTRACT_DIC_OBJECT = 3;

  /**
   * The feature id for the '<em><b>Opinion</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROOT__OPINION = 4;

  /**
   * The feature id for the '<em><b>Quote</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROOT__QUOTE = 5;

  /**
   * The feature id for the '<em><b>Discussion Viewer</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROOT__DISCUSSION_VIEWER = 6;

  /**
   * The feature id for the '<em><b>Relation Connection</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROOT__RELATION_CONNECTION = 7;

  /**
   * The feature id for the '<em><b>Containment Connection</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROOT__CONTAINMENT_CONNECTION = 8;

  /**
   * The number of structural features of the '<em>Root</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROOT_FEATURE_COUNT = 9;


  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.MementoValue <em>Memento Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Memento Value</em>'.
   * @see lost.tok.DiscussionViewer.emf.MementoValue
   * @generated
   */
  EClass getMementoValue();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.MementoValue#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see lost.tok.DiscussionViewer.emf.MementoValue#getName()
   * @see #getMementoValue()
   * @generated
   */
  EAttribute getMementoValue_Name();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.MementoValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see lost.tok.DiscussionViewer.emf.MementoValue#getValue()
   * @see #getMementoValue()
   * @generated
   */
  EAttribute getMementoValue_Value();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.Memento <em>Memento</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Memento</em>'.
   * @see lost.tok.DiscussionViewer.emf.Memento
   * @generated
   */
  EClass getMemento();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.Memento#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see lost.tok.DiscussionViewer.emf.Memento#getId()
   * @see #getMemento()
   * @generated
   */
  EAttribute getMemento_Id();

  /**
   * Returns the meta object for the containment reference list '{@link lost.tok.DiscussionViewer.emf.Memento#getData <em>Data</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Data</em>'.
   * @see lost.tok.DiscussionViewer.emf.Memento#getData()
   * @see #getMemento()
   * @generated
   */
  EReference getMemento_Data();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.Subtype <em>Subtype</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Subtype</em>'.
   * @see lost.tok.DiscussionViewer.emf.Subtype
   * @generated
   */
  EClass getSubtype();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.Subtype#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see lost.tok.DiscussionViewer.emf.Subtype#getName()
   * @see #getSubtype()
   * @generated
   */
  EAttribute getSubtype_Name();

  /**
   * Returns the meta object for the reference '{@link lost.tok.DiscussionViewer.emf.Subtype#getBase <em>Base</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Base</em>'.
   * @see lost.tok.DiscussionViewer.emf.Subtype#getBase()
   * @see #getSubtype()
   * @generated
   */
  EReference getSubtype_Base();

  /**
   * Returns the meta object for the reference list '{@link lost.tok.DiscussionViewer.emf.Subtype#getInstances <em>Instances</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Instances</em>'.
   * @see lost.tok.DiscussionViewer.emf.Subtype#getInstances()
   * @see #getSubtype()
   * @generated
   */
  EReference getSubtype_Instances();

  /**
   * Returns the meta object for the containment reference list '{@link lost.tok.DiscussionViewer.emf.Subtype#getLinks <em>Links</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Links</em>'.
   * @see lost.tok.DiscussionViewer.emf.Subtype#getLinks()
   * @see #getSubtype()
   * @generated
   */
  EReference getSubtype_Links();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.SubtypeLink <em>Subtype Link</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Subtype Link</em>'.
   * @see lost.tok.DiscussionViewer.emf.SubtypeLink
   * @generated
   */
  EClass getSubtypeLink();

  /**
   * Returns the meta object for the reference '{@link lost.tok.DiscussionViewer.emf.SubtypeLink#getBase <em>Base</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Base</em>'.
   * @see lost.tok.DiscussionViewer.emf.SubtypeLink#getBase()
   * @see #getSubtypeLink()
   * @generated
   */
  EReference getSubtypeLink_Base();

  /**
   * Returns the meta object for the reference '{@link lost.tok.DiscussionViewer.emf.SubtypeLink#getInstance <em>Instance</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Instance</em>'.
   * @see lost.tok.DiscussionViewer.emf.SubtypeLink#getInstance()
   * @see #getSubtypeLink()
   * @generated
   */
  EReference getSubtypeLink_Instance();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.ModelObject <em>Model Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Model Object</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject
   * @generated
   */
  EClass getModelObject();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#getName()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_Name();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#getId()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_Id();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#getX <em>X</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>X</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#getX()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_X();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#getY <em>Y</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Y</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#getY()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_Y();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#getWidth <em>Width</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Width</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#getWidth()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_Width();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#getHeight <em>Height</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Height</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#getHeight()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_Height();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#getExpandedWidth <em>Expanded Width</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Expanded Width</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#getExpandedWidth()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_ExpandedWidth();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#getExpandedHeight <em>Expanded Height</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Expanded Height</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#getExpandedHeight()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_ExpandedHeight();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#isExpanded <em>Expanded</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Expanded</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#isExpanded()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_Expanded();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#isSubtype <em>Subtype</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Subtype</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#isSubtype()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_Subtype();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#isVisible <em>Visible</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Visible</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#isVisible()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_Visible();

  /**
   * Returns the meta object for the attribute '{@link lost.tok.DiscussionViewer.emf.ModelObject#getModelLinkTarget <em>Model Link Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Model Link Target</em>'.
   * @see lost.tok.DiscussionViewer.emf.ModelObject#getModelLinkTarget()
   * @see #getModelObject()
   * @generated
   */
  EAttribute getModelObject_ModelLinkTarget();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.AbstractDicObject <em>Abstract Dic Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Abstract Dic Object</em>'.
   * @see lost.tok.DiscussionViewer.emf.AbstractDicObject
   * @generated
   */
  EClass getAbstractDicObject();

  /**
   * Returns the meta object for the reference list '{@link lost.tok.DiscussionViewer.emf.AbstractDicObject#getRelates1 <em>Relates1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Relates1</em>'.
   * @see lost.tok.DiscussionViewer.emf.AbstractDicObject#getRelates1()
   * @see #getAbstractDicObject()
   * @generated
   */
  EReference getAbstractDicObject_Relates1();

  /**
   * Returns the meta object for the reference list '{@link lost.tok.DiscussionViewer.emf.AbstractDicObject#getRelates2 <em>Relates2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Relates2</em>'.
   * @see lost.tok.DiscussionViewer.emf.AbstractDicObject#getRelates2()
   * @see #getAbstractDicObject()
   * @generated
   */
  EReference getAbstractDicObject_Relates2();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.Opinion <em>Opinion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Opinion</em>'.
   * @see lost.tok.DiscussionViewer.emf.Opinion
   * @generated
   */
  EClass getOpinion();

  /**
   * Returns the meta object for the reference list '{@link lost.tok.DiscussionViewer.emf.Opinion#getOneOpinion <em>One Opinion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>One Opinion</em>'.
   * @see lost.tok.DiscussionViewer.emf.Opinion#getOneOpinion()
   * @see #getOpinion()
   * @generated
   */
  EReference getOpinion_OneOpinion();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.Quote <em>Quote</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Quote</em>'.
   * @see lost.tok.DiscussionViewer.emf.Quote
   * @generated
   */
  EClass getQuote();

  /**
   * Returns the meta object for the reference list '{@link lost.tok.DiscussionViewer.emf.Quote#getManyQuotes <em>Many Quotes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Many Quotes</em>'.
   * @see lost.tok.DiscussionViewer.emf.Quote#getManyQuotes()
   * @see #getQuote()
   * @generated
   */
  EReference getQuote_ManyQuotes();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.DiscussionViewer <em>Discussion Viewer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Discussion Viewer</em>'.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewer
   * @generated
   */
  EClass getDiscussionViewer();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.RelationConnection <em>Relation Connection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Relation Connection</em>'.
   * @see lost.tok.DiscussionViewer.emf.RelationConnection
   * @generated
   */
  EClass getRelationConnection();

  /**
   * Returns the meta object for the reference '{@link lost.tok.DiscussionViewer.emf.RelationConnection#getSource <em>Source</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Source</em>'.
   * @see lost.tok.DiscussionViewer.emf.RelationConnection#getSource()
   * @see #getRelationConnection()
   * @generated
   */
  EReference getRelationConnection_Source();

  /**
   * Returns the meta object for the reference '{@link lost.tok.DiscussionViewer.emf.RelationConnection#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Target</em>'.
   * @see lost.tok.DiscussionViewer.emf.RelationConnection#getTarget()
   * @see #getRelationConnection()
   * @generated
   */
  EReference getRelationConnection_Target();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.ContainmentConnection <em>Containment Connection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Containment Connection</em>'.
   * @see lost.tok.DiscussionViewer.emf.ContainmentConnection
   * @generated
   */
  EClass getContainmentConnection();

  /**
   * Returns the meta object for the reference '{@link lost.tok.DiscussionViewer.emf.ContainmentConnection#getSource <em>Source</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Source</em>'.
   * @see lost.tok.DiscussionViewer.emf.ContainmentConnection#getSource()
   * @see #getContainmentConnection()
   * @generated
   */
  EReference getContainmentConnection_Source();

  /**
   * Returns the meta object for the reference '{@link lost.tok.DiscussionViewer.emf.ContainmentConnection#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Target</em>'.
   * @see lost.tok.DiscussionViewer.emf.ContainmentConnection#getTarget()
   * @see #getContainmentConnection()
   * @generated
   */
  EReference getContainmentConnection_Target();

  /**
   * Returns the meta object for class '{@link lost.tok.DiscussionViewer.emf.Root <em>Root</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Root</em>'.
   * @see lost.tok.DiscussionViewer.emf.Root
   * @generated
   */
  EClass getRoot();

  /**
   * Returns the meta object for the containment reference list '{@link lost.tok.DiscussionViewer.emf.Root#getMementos <em>Mementos</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Mementos</em>'.
   * @see lost.tok.DiscussionViewer.emf.Root#getMementos()
   * @see #getRoot()
   * @generated
   */
  EReference getRoot_Mementos();

  /**
   * Returns the meta object for the containment reference list '{@link lost.tok.DiscussionViewer.emf.Root#getSubtypes <em>Subtypes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Subtypes</em>'.
   * @see lost.tok.DiscussionViewer.emf.Root#getSubtypes()
   * @see #getRoot()
   * @generated
   */
  EReference getRoot_Subtypes();

  /**
   * Returns the meta object for the containment reference '{@link lost.tok.DiscussionViewer.emf.Root#getRealRoot <em>Real Root</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Real Root</em>'.
   * @see lost.tok.DiscussionViewer.emf.Root#getRealRoot()
   * @see #getRoot()
   * @generated
   */
  EReference getRoot_RealRoot();

  /**
   * Returns the meta object for the containment reference list '{@link lost.tok.DiscussionViewer.emf.Root#getAbstractDicObject <em>Abstract Dic Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Abstract Dic Object</em>'.
   * @see lost.tok.DiscussionViewer.emf.Root#getAbstractDicObject()
   * @see #getRoot()
   * @generated
   */
  EReference getRoot_AbstractDicObject();

  /**
   * Returns the meta object for the containment reference list '{@link lost.tok.DiscussionViewer.emf.Root#getOpinion <em>Opinion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Opinion</em>'.
   * @see lost.tok.DiscussionViewer.emf.Root#getOpinion()
   * @see #getRoot()
   * @generated
   */
  EReference getRoot_Opinion();

  /**
   * Returns the meta object for the containment reference list '{@link lost.tok.DiscussionViewer.emf.Root#getQuote <em>Quote</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Quote</em>'.
   * @see lost.tok.DiscussionViewer.emf.Root#getQuote()
   * @see #getRoot()
   * @generated
   */
  EReference getRoot_Quote();

  /**
   * Returns the meta object for the containment reference list '{@link lost.tok.DiscussionViewer.emf.Root#getDiscussionViewer <em>Discussion Viewer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Discussion Viewer</em>'.
   * @see lost.tok.DiscussionViewer.emf.Root#getDiscussionViewer()
   * @see #getRoot()
   * @generated
   */
  EReference getRoot_DiscussionViewer();

  /**
   * Returns the meta object for the containment reference list '{@link lost.tok.DiscussionViewer.emf.Root#getRelationConnection <em>Relation Connection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Relation Connection</em>'.
   * @see lost.tok.DiscussionViewer.emf.Root#getRelationConnection()
   * @see #getRoot()
   * @generated
   */
  EReference getRoot_RelationConnection();

  /**
   * Returns the meta object for the containment reference list '{@link lost.tok.DiscussionViewer.emf.Root#getContainmentConnection <em>Containment Connection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Containment Connection</em>'.
   * @see lost.tok.DiscussionViewer.emf.Root#getContainmentConnection()
   * @see #getRoot()
   * @generated
   */
  EReference getRoot_ContainmentConnection();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  DiscussionViewerFactory getDiscussionViewerFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.MementoValueImpl <em>Memento Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.MementoValueImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getMementoValue()
     * @generated
     */
    EClass MEMENTO_VALUE = eINSTANCE.getMementoValue();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MEMENTO_VALUE__NAME = eINSTANCE.getMementoValue_Name();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MEMENTO_VALUE__VALUE = eINSTANCE.getMementoValue_Value();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.MementoImpl <em>Memento</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.MementoImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getMemento()
     * @generated
     */
    EClass MEMENTO = eINSTANCE.getMemento();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MEMENTO__ID = eINSTANCE.getMemento_Id();

    /**
     * The meta object literal for the '<em><b>Data</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MEMENTO__DATA = eINSTANCE.getMemento_Data();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.SubtypeImpl <em>Subtype</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.SubtypeImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getSubtype()
     * @generated
     */
    EClass SUBTYPE = eINSTANCE.getSubtype();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SUBTYPE__NAME = eINSTANCE.getSubtype_Name();

    /**
     * The meta object literal for the '<em><b>Base</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUBTYPE__BASE = eINSTANCE.getSubtype_Base();

    /**
     * The meta object literal for the '<em><b>Instances</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUBTYPE__INSTANCES = eINSTANCE.getSubtype_Instances();

    /**
     * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUBTYPE__LINKS = eINSTANCE.getSubtype_Links();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.SubtypeLinkImpl <em>Subtype Link</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.SubtypeLinkImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getSubtypeLink()
     * @generated
     */
    EClass SUBTYPE_LINK = eINSTANCE.getSubtypeLink();

    /**
     * The meta object literal for the '<em><b>Base</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUBTYPE_LINK__BASE = eINSTANCE.getSubtypeLink_Base();

    /**
     * The meta object literal for the '<em><b>Instance</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUBTYPE_LINK__INSTANCE = eINSTANCE.getSubtypeLink_Instance();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.ModelObjectImpl <em>Model Object</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.ModelObjectImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getModelObject()
     * @generated
     */
    EClass MODEL_OBJECT = eINSTANCE.getModelObject();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__NAME = eINSTANCE.getModelObject_Name();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__ID = eINSTANCE.getModelObject_Id();

    /**
     * The meta object literal for the '<em><b>X</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__X = eINSTANCE.getModelObject_X();

    /**
     * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__Y = eINSTANCE.getModelObject_Y();

    /**
     * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__WIDTH = eINSTANCE.getModelObject_Width();

    /**
     * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__HEIGHT = eINSTANCE.getModelObject_Height();

    /**
     * The meta object literal for the '<em><b>Expanded Width</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__EXPANDED_WIDTH = eINSTANCE.getModelObject_ExpandedWidth();

    /**
     * The meta object literal for the '<em><b>Expanded Height</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__EXPANDED_HEIGHT = eINSTANCE.getModelObject_ExpandedHeight();

    /**
     * The meta object literal for the '<em><b>Expanded</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__EXPANDED = eINSTANCE.getModelObject_Expanded();

    /**
     * The meta object literal for the '<em><b>Subtype</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__SUBTYPE = eINSTANCE.getModelObject_Subtype();

    /**
     * The meta object literal for the '<em><b>Visible</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__VISIBLE = eINSTANCE.getModelObject_Visible();

    /**
     * The meta object literal for the '<em><b>Model Link Target</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_OBJECT__MODEL_LINK_TARGET = eINSTANCE.getModelObject_ModelLinkTarget();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.AbstractDicObjectImpl <em>Abstract Dic Object</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.AbstractDicObjectImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getAbstractDicObject()
     * @generated
     */
    EClass ABSTRACT_DIC_OBJECT = eINSTANCE.getAbstractDicObject();

    /**
     * The meta object literal for the '<em><b>Relates1</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ABSTRACT_DIC_OBJECT__RELATES1 = eINSTANCE.getAbstractDicObject_Relates1();

    /**
     * The meta object literal for the '<em><b>Relates2</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ABSTRACT_DIC_OBJECT__RELATES2 = eINSTANCE.getAbstractDicObject_Relates2();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.OpinionImpl <em>Opinion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.OpinionImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getOpinion()
     * @generated
     */
    EClass OPINION = eINSTANCE.getOpinion();

    /**
     * The meta object literal for the '<em><b>One Opinion</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OPINION__ONE_OPINION = eINSTANCE.getOpinion_OneOpinion();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.QuoteImpl <em>Quote</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.QuoteImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getQuote()
     * @generated
     */
    EClass QUOTE = eINSTANCE.getQuote();

    /**
     * The meta object literal for the '<em><b>Many Quotes</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference QUOTE__MANY_QUOTES = eINSTANCE.getQuote_ManyQuotes();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.DiscussionViewerImpl <em>Discussion Viewer</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getDiscussionViewer()
     * @generated
     */
    EClass DISCUSSION_VIEWER = eINSTANCE.getDiscussionViewer();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.RelationConnectionImpl <em>Relation Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.RelationConnectionImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getRelationConnection()
     * @generated
     */
    EClass RELATION_CONNECTION = eINSTANCE.getRelationConnection();

    /**
     * The meta object literal for the '<em><b>Source</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RELATION_CONNECTION__SOURCE = eINSTANCE.getRelationConnection_Source();

    /**
     * The meta object literal for the '<em><b>Target</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RELATION_CONNECTION__TARGET = eINSTANCE.getRelationConnection_Target();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.ContainmentConnectionImpl <em>Containment Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.ContainmentConnectionImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getContainmentConnection()
     * @generated
     */
    EClass CONTAINMENT_CONNECTION = eINSTANCE.getContainmentConnection();

    /**
     * The meta object literal for the '<em><b>Source</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTAINMENT_CONNECTION__SOURCE = eINSTANCE.getContainmentConnection_Source();

    /**
     * The meta object literal for the '<em><b>Target</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTAINMENT_CONNECTION__TARGET = eINSTANCE.getContainmentConnection_Target();

    /**
     * The meta object literal for the '{@link lost.tok.DiscussionViewer.emf.impl.RootImpl <em>Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see lost.tok.DiscussionViewer.emf.impl.RootImpl
     * @see lost.tok.DiscussionViewer.emf.impl.DiscussionViewerPackageImpl#getRoot()
     * @generated
     */
    EClass ROOT = eINSTANCE.getRoot();

    /**
     * The meta object literal for the '<em><b>Mementos</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROOT__MEMENTOS = eINSTANCE.getRoot_Mementos();

    /**
     * The meta object literal for the '<em><b>Subtypes</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROOT__SUBTYPES = eINSTANCE.getRoot_Subtypes();

    /**
     * The meta object literal for the '<em><b>Real Root</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROOT__REAL_ROOT = eINSTANCE.getRoot_RealRoot();

    /**
     * The meta object literal for the '<em><b>Abstract Dic Object</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROOT__ABSTRACT_DIC_OBJECT = eINSTANCE.getRoot_AbstractDicObject();

    /**
     * The meta object literal for the '<em><b>Opinion</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROOT__OPINION = eINSTANCE.getRoot_Opinion();

    /**
     * The meta object literal for the '<em><b>Quote</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROOT__QUOTE = eINSTANCE.getRoot_Quote();

    /**
     * The meta object literal for the '<em><b>Discussion Viewer</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROOT__DISCUSSION_VIEWER = eINSTANCE.getRoot_DiscussionViewer();

    /**
     * The meta object literal for the '<em><b>Relation Connection</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROOT__RELATION_CONNECTION = eINSTANCE.getRoot_RelationConnection();

    /**
     * The meta object literal for the '<em><b>Containment Connection</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROOT__CONTAINMENT_CONNECTION = eINSTANCE.getRoot_ContainmentConnection();

  }

} //DiscussionViewerPackage
