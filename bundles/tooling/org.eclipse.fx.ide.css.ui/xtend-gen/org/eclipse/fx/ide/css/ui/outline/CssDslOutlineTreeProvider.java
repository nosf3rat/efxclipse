/**
 * generated by Xtext 2.9.0
 */
package org.eclipse.fx.ide.css.ui.outline;

import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fx.ide.css.cssDsl.CssDeclaration;
import org.eclipse.fx.ide.css.cssDsl.CssProperty;
import org.eclipse.fx.ide.css.cssDsl.CssTok;
import org.eclipse.fx.ide.css.cssDsl.Ruleset;
import org.eclipse.fx.ide.css.cssDsl.Selector;
import org.eclipse.fx.ide.css.cssDsl.Stylesheet;
import org.eclipse.fx.ide.css.util.Util;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.xbase.lib.Extension;

/**
 * Customization of the default outline structure.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#outline
 */
@SuppressWarnings("all")
public class CssDslOutlineTreeProvider extends DefaultOutlineTreeProvider {
  @Extension
  private Util util = new Util();
  
  public void _createChildren(final DocumentRootNode parentNode, final Stylesheet stylesheet) {
    EList<Ruleset> _ruleset = stylesheet.getRuleset();
    for (final Ruleset ruleset : _ruleset) {
      EList<Selector> _selectors = ruleset.getSelectors();
      for (final Selector s : _selectors) {
        {
          EList<CssDeclaration> _declarations = ruleset.getDeclarations();
          final boolean isLeaf = _declarations.isEmpty();
          final EObjectNode node = this.createEObjectNode(parentNode, ruleset);
          ITextRegion _significantTextRegion = this.locationInFileProvider.getSignificantTextRegion(s);
          node.setShortTextRegion(_significantTextRegion);
          if ((!isLeaf)) {
            this.createChildren(node, ruleset);
          }
        }
      }
    }
  }
  
  public String _text(final Ruleset ruleset) {
    String _xifexpression = null;
    boolean _isMetaDataBlock = this.util.isMetaDataBlock(ruleset);
    if (_isMetaDataBlock) {
      _xifexpression = "Meta Data";
    } else {
      _xifexpression = this.labelProvider.getText(ruleset);
    }
    return _xifexpression;
  }
  
  public void _createChildren(final IOutlineNode parentNode, final Ruleset ruleset) {
    EList<CssDeclaration> _declarations = ruleset.getDeclarations();
    for (final CssDeclaration d : _declarations) {
      {
        final EObjectNode node = this.createEObjectNode(parentNode, d);
        CssProperty _property = d.getProperty();
        ITextRegion _significantTextRegion = this.locationInFileProvider.getSignificantTextRegion(_property);
        node.setShortTextRegion(_significantTextRegion);
      }
    }
  }
  
  public Image _image(final CssDeclaration d) {
    CssProperty _property = d.getProperty();
    return this.labelProvider.getImage(_property);
  }
  
  public String _text(final CssDeclaration d) {
    String _xblockexpression = null;
    {
      final StringBuilder valueBuilder = new StringBuilder();
      EList<CssTok> _valueTokens = d.getValueTokens();
      final Iterator<CssTok> iterator = _valueTokens.iterator();
      while (iterator.hasNext()) {
        {
          final CssTok next = iterator.next();
          String _text = this.labelProvider.getText(next);
          valueBuilder.append(_text);
        }
      }
      CssProperty _property = d.getProperty();
      String _text = this.labelProvider.getText(_property);
      String _plus = (_text + ": ");
      String _string = valueBuilder.toString();
      String _trim = _string.trim();
      String _plus_1 = (_plus + _trim);
      String _xifexpression = null;
      boolean _isImportant = d.isImportant();
      if (_isImportant) {
        _xifexpression = " !important";
      } else {
        _xifexpression = "";
      }
      _xblockexpression = (_plus_1 + _xifexpression);
    }
    return _xblockexpression;
  }
  
  public boolean _isLeaf(final CssDeclaration d) {
    return true;
  }
  
  public Image _image(final Ruleset o) {
    return this.labelProvider.getImage(o);
  }
  
  public Image _image(final CssProperty o) {
    return this.labelProvider.getImage(o);
  }
  
  public boolean _isLeaf(final CssProperty o) {
    return true;
  }
  
  public boolean _isLeaf(final Ruleset o) {
    EList<CssDeclaration> _declarations = o.getDeclarations();
    return _declarations.isEmpty();
  }
}
