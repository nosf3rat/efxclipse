/**
 * generated by Xtext 2.9.0
 */
package org.eclipse.fx.ide.css.cssDsl.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fx.ide.css.cssDsl.CssDslPackage;
import org.eclipse.fx.ide.css.cssDsl.Media;
import org.eclipse.fx.ide.css.cssDsl.MediaList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Media List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fx.ide.css.cssDsl.impl.MediaListImpl#getEntry <em>Entry</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MediaListImpl extends MinimalEObjectImpl.Container implements MediaList
{
  /**
   * The cached value of the '{@link #getEntry() <em>Entry</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEntry()
   * @generated
   * @ordered
   */
  protected EList<Media> entry;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MediaListImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return CssDslPackage.Literals.MEDIA_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Media> getEntry()
  {
    if (entry == null)
    {
      entry = new EObjectContainmentEList<Media>(Media.class, this, CssDslPackage.MEDIA_LIST__ENTRY);
    }
    return entry;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case CssDslPackage.MEDIA_LIST__ENTRY:
        return ((InternalEList<?>)getEntry()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case CssDslPackage.MEDIA_LIST__ENTRY:
        return getEntry();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case CssDslPackage.MEDIA_LIST__ENTRY:
        getEntry().clear();
        getEntry().addAll((Collection<? extends Media>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case CssDslPackage.MEDIA_LIST__ENTRY:
        getEntry().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case CssDslPackage.MEDIA_LIST__ENTRY:
        return entry != null && !entry.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //MediaListImpl
