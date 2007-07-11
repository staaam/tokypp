/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf.impl;

import lost.tok.DiscussionViewer.emf.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DiscussionViewerFactoryImpl extends EFactoryImpl implements DiscussionViewerFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DiscussionViewerFactory init()
  {
    try
    {
      DiscussionViewerFactory theDiscussionViewerFactory = (DiscussionViewerFactory)EPackage.Registry.INSTANCE.getEFactory("lost.tok.discussionViewer"); 
      if (theDiscussionViewerFactory != null)
      {
        return theDiscussionViewerFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new DiscussionViewerFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DiscussionViewerFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case DiscussionViewerPackage.MEMENTO_VALUE: return createMementoValue();
      case DiscussionViewerPackage.MEMENTO: return createMemento();
      case DiscussionViewerPackage.SUBTYPE: return createSubtype();
      case DiscussionViewerPackage.SUBTYPE_LINK: return createSubtypeLink();
      case DiscussionViewerPackage.MODEL_OBJECT: return createModelObject();
      case DiscussionViewerPackage.ABSTRACT_DIC_OBJECT: return createAbstractDicObject();
      case DiscussionViewerPackage.OPINION: return createOpinion();
      case DiscussionViewerPackage.QUOTE: return createQuote();
      case DiscussionViewerPackage.DISCUSSION_VIEWER: return createDiscussionViewer();
      case DiscussionViewerPackage.RELATION_CONNECTION: return createRelationConnection();
      case DiscussionViewerPackage.CONTAINMENT_CONNECTION: return createContainmentConnection();
      case DiscussionViewerPackage.ROOT: return createRoot();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MementoValue createMementoValue()
  {
    MementoValueImpl mementoValue = new MementoValueImpl();
    return mementoValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Memento createMemento()
  {
    MementoImpl memento = new MementoImpl();
    return memento;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Subtype createSubtype()
  {
    SubtypeImpl subtype = new SubtypeImpl();
    return subtype;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SubtypeLink createSubtypeLink()
  {
    SubtypeLinkImpl subtypeLink = new SubtypeLinkImpl();
    return subtypeLink;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelObject createModelObject()
  {
    ModelObjectImpl modelObject = new ModelObjectImpl();
    return modelObject;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AbstractDicObject createAbstractDicObject()
  {
    AbstractDicObjectImpl abstractDicObject = new AbstractDicObjectImpl();
    return abstractDicObject;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Opinion createOpinion()
  {
    OpinionImpl opinion = new OpinionImpl();
    return opinion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Quote createQuote()
  {
    QuoteImpl quote = new QuoteImpl();
    return quote;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DiscussionViewer createDiscussionViewer()
  {
    DiscussionViewerImpl discussionViewer = new DiscussionViewerImpl();
    return discussionViewer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RelationConnection createRelationConnection()
  {
    RelationConnectionImpl relationConnection = new RelationConnectionImpl();
    return relationConnection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContainmentConnection createContainmentConnection()
  {
    ContainmentConnectionImpl containmentConnection = new ContainmentConnectionImpl();
    return containmentConnection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Root createRoot()
  {
    RootImpl root = new RootImpl();
    return root;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DiscussionViewerPackage getDiscussionViewerPackage()
  {
    return (DiscussionViewerPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  public static DiscussionViewerPackage getPackage()
  {
    return DiscussionViewerPackage.eINSTANCE;
  }

} //DiscussionViewerFactoryImpl
