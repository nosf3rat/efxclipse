/**
 * generated by Xtext 2.9.0
 */
package org.eclipse.fx.ide.css.cssDsl.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.fx.ide.css.cssDsl.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fx.ide.css.cssDsl.CssDslPackage
 * @generated
 */
public class CssDslAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static CssDslPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CssDslAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = CssDslPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CssDslSwitch<Adapter> modelSwitch =
    new CssDslSwitch<Adapter>()
    {
      @Override
      public Adapter caseStylesheet(Stylesheet object)
      {
        return createStylesheetAdapter();
      }
      @Override
      public Adapter caseCharsetRule(CharsetRule object)
      {
        return createCharsetRuleAdapter();
      }
      @Override
      public Adapter caseImportRule(ImportRule object)
      {
        return createImportRuleAdapter();
      }
      @Override
      public Adapter casePageRule(PageRule object)
      {
        return createPageRuleAdapter();
      }
      @Override
      public Adapter caseMediaRule(MediaRule object)
      {
        return createMediaRuleAdapter();
      }
      @Override
      public Adapter caseMediaList(MediaList object)
      {
        return createMediaListAdapter();
      }
      @Override
      public Adapter caseMedia(Media object)
      {
        return createMediaAdapter();
      }
      @Override
      public Adapter caseFontFaceRule(FontFaceRule object)
      {
        return createFontFaceRuleAdapter();
      }
      @Override
      public Adapter caseKeyframesRule(KeyframesRule object)
      {
        return createKeyframesRuleAdapter();
      }
      @Override
      public Adapter caseKeyframeSelector(KeyframeSelector object)
      {
        return createKeyframeSelectorAdapter();
      }
      @Override
      public Adapter caseRuleset(Ruleset object)
      {
        return createRulesetAdapter();
      }
      @Override
      public Adapter caseCssDeclaration(CssDeclaration object)
      {
        return createCssDeclarationAdapter();
      }
      @Override
      public Adapter caseSelector(Selector object)
      {
        return createSelectorAdapter();
      }
      @Override
      public Adapter caseSimpleSelectorForNegation(SimpleSelectorForNegation object)
      {
        return createSimpleSelectorForNegationAdapter();
      }
      @Override
      public Adapter caseCssSelector(CssSelector object)
      {
        return createCssSelectorAdapter();
      }
      @Override
      public Adapter caseSimpleSelector(SimpleSelector object)
      {
        return createSimpleSelectorAdapter();
      }
      @Override
      public Adapter caseClassSelector(ClassSelector object)
      {
        return createClassSelectorAdapter();
      }
      @Override
      public Adapter caseElementSelector(ElementSelector object)
      {
        return createElementSelectorAdapter();
      }
      @Override
      public Adapter caseUniversalSelector(UniversalSelector object)
      {
        return createUniversalSelectorAdapter();
      }
      @Override
      public Adapter caseIdSelector(IdSelector object)
      {
        return createIdSelectorAdapter();
      }
      @Override
      public Adapter caseCssProperty(CssProperty object)
      {
        return createCssPropertyAdapter();
      }
      @Override
      public Adapter casePseudoClassOrFunc(PseudoClassOrFunc object)
      {
        return createPseudoClassOrFuncAdapter();
      }
      @Override
      public Adapter casePseudoClass(PseudoClass object)
      {
        return createPseudoClassAdapter();
      }
      @Override
      public Adapter casePseudoClassFunction(PseudoClassFunction object)
      {
        return createPseudoClassFunctionAdapter();
      }
      @Override
      public Adapter caseCssTok(CssTok object)
      {
        return createCssTokAdapter();
      }
      @Override
      public Adapter caseURLType(URLType object)
      {
        return createURLTypeAdapter();
      }
      @Override
      public Adapter caseAttributeSelector(AttributeSelector object)
      {
        return createAttributeSelectorAdapter();
      }
      @Override
      public Adapter caseSymbolTok(SymbolTok object)
      {
        return createSymbolTokAdapter();
      }
      @Override
      public Adapter caseWSTok(WSTok object)
      {
        return createWSTokAdapter();
      }
      @Override
      public Adapter caseStringTok(StringTok object)
      {
        return createStringTokAdapter();
      }
      @Override
      public Adapter caseNumberTok(NumberTok object)
      {
        return createNumberTokAdapter();
      }
      @Override
      public Adapter caseUrlTok(UrlTok object)
      {
        return createUrlTokAdapter();
      }
      @Override
      public Adapter caseColorTok(ColorTok object)
      {
        return createColorTokAdapter();
      }
      @Override
      public Adapter caseIdentifierTok(IdentifierTok object)
      {
        return createIdentifierTokAdapter();
      }
      @Override
      public Adapter caseFuncTok(FuncTok object)
      {
        return createFuncTokAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.Stylesheet <em>Stylesheet</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.Stylesheet
   * @generated
   */
  public Adapter createStylesheetAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.CharsetRule <em>Charset Rule</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.CharsetRule
   * @generated
   */
  public Adapter createCharsetRuleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.ImportRule <em>Import Rule</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.ImportRule
   * @generated
   */
  public Adapter createImportRuleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.PageRule <em>Page Rule</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.PageRule
   * @generated
   */
  public Adapter createPageRuleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.MediaRule <em>Media Rule</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.MediaRule
   * @generated
   */
  public Adapter createMediaRuleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.MediaList <em>Media List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.MediaList
   * @generated
   */
  public Adapter createMediaListAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.Media <em>Media</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.Media
   * @generated
   */
  public Adapter createMediaAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.FontFaceRule <em>Font Face Rule</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.FontFaceRule
   * @generated
   */
  public Adapter createFontFaceRuleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.KeyframesRule <em>Keyframes Rule</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.KeyframesRule
   * @generated
   */
  public Adapter createKeyframesRuleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.KeyframeSelector <em>Keyframe Selector</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.KeyframeSelector
   * @generated
   */
  public Adapter createKeyframeSelectorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.Ruleset <em>Ruleset</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.Ruleset
   * @generated
   */
  public Adapter createRulesetAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.CssDeclaration <em>Css Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.CssDeclaration
   * @generated
   */
  public Adapter createCssDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.Selector <em>Selector</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.Selector
   * @generated
   */
  public Adapter createSelectorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.SimpleSelectorForNegation <em>Simple Selector For Negation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.SimpleSelectorForNegation
   * @generated
   */
  public Adapter createSimpleSelectorForNegationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.CssSelector <em>Css Selector</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.CssSelector
   * @generated
   */
  public Adapter createCssSelectorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.SimpleSelector <em>Simple Selector</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.SimpleSelector
   * @generated
   */
  public Adapter createSimpleSelectorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.ClassSelector <em>Class Selector</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.ClassSelector
   * @generated
   */
  public Adapter createClassSelectorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.ElementSelector <em>Element Selector</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.ElementSelector
   * @generated
   */
  public Adapter createElementSelectorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.UniversalSelector <em>Universal Selector</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.UniversalSelector
   * @generated
   */
  public Adapter createUniversalSelectorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.IdSelector <em>Id Selector</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.IdSelector
   * @generated
   */
  public Adapter createIdSelectorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.CssProperty <em>Css Property</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.CssProperty
   * @generated
   */
  public Adapter createCssPropertyAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.PseudoClassOrFunc <em>Pseudo Class Or Func</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.PseudoClassOrFunc
   * @generated
   */
  public Adapter createPseudoClassOrFuncAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.PseudoClass <em>Pseudo Class</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.PseudoClass
   * @generated
   */
  public Adapter createPseudoClassAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.PseudoClassFunction <em>Pseudo Class Function</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.PseudoClassFunction
   * @generated
   */
  public Adapter createPseudoClassFunctionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.CssTok <em>Css Tok</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.CssTok
   * @generated
   */
  public Adapter createCssTokAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.URLType <em>URL Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.URLType
   * @generated
   */
  public Adapter createURLTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.AttributeSelector <em>Attribute Selector</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.AttributeSelector
   * @generated
   */
  public Adapter createAttributeSelectorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.SymbolTok <em>Symbol Tok</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.SymbolTok
   * @generated
   */
  public Adapter createSymbolTokAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.WSTok <em>WS Tok</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.WSTok
   * @generated
   */
  public Adapter createWSTokAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.StringTok <em>String Tok</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.StringTok
   * @generated
   */
  public Adapter createStringTokAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.NumberTok <em>Number Tok</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.NumberTok
   * @generated
   */
  public Adapter createNumberTokAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.UrlTok <em>Url Tok</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.UrlTok
   * @generated
   */
  public Adapter createUrlTokAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.ColorTok <em>Color Tok</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.ColorTok
   * @generated
   */
  public Adapter createColorTokAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.IdentifierTok <em>Identifier Tok</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.IdentifierTok
   * @generated
   */
  public Adapter createIdentifierTokAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fx.ide.css.cssDsl.FuncTok <em>Func Tok</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fx.ide.css.cssDsl.FuncTok
   * @generated
   */
  public Adapter createFuncTokAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //CssDslAdapterFactory
