/*
 * generated by Xtext 2.9.0
 */
package org.eclipse.fx.ide.css.ui.labeling

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.fx.ide.css.cssDsl.AttributeSelector
import org.eclipse.fx.ide.css.cssDsl.ClassSelector
import org.eclipse.fx.ide.css.cssDsl.CssProperty
import org.eclipse.fx.ide.css.cssDsl.CssSelector
import org.eclipse.fx.ide.css.cssDsl.CssTok
import org.eclipse.fx.ide.css.cssDsl.ElementSelector
import org.eclipse.fx.ide.css.cssDsl.FuncTok
import org.eclipse.fx.ide.css.cssDsl.IdSelector
import org.eclipse.fx.ide.css.cssDsl.IdentifierTok
import org.eclipse.fx.ide.css.cssDsl.NumberTok
import org.eclipse.fx.ide.css.cssDsl.PseudoClassFunction
import org.eclipse.fx.ide.css.cssDsl.Ruleset
import org.eclipse.fx.ide.css.cssDsl.Selector
import org.eclipse.fx.ide.css.cssDsl.SimpleSelector
import org.eclipse.fx.ide.css.cssDsl.SimpleSelectorForNegation
import org.eclipse.fx.ide.css.cssDsl.StringTok
import org.eclipse.fx.ide.css.cssDsl.Stylesheet
import org.eclipse.fx.ide.css.cssDsl.SymbolTok
import org.eclipse.fx.ide.css.cssDsl.UrlTok
import org.eclipse.fx.ide.css.cssDsl.WSTok
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider
import org.eclipse.fx.ide.css.cssDsl.PseudoClass

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
class CssDslLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}
	
	override protected convertToString(Object text) {
		if (text instanceof CharSequence) {
			text.toString
		}
		else {
			super.convertToString(text)
		}
	}
	
	
	def text(StringTok strTok) {
		if (strTok.value != null) {
			'''"«strTok.value»"'''
		}
		else {
			'''null''';
		}
	}
	
	def text(Ruleset value) {
		value.selectors.map[s|text(s)].join(", ")
	}
	
	def String text(Selector value) {
		val b = new StringBuilder(/*"se-"*/);
		
		for( ss : value.getSimpleselectors() ) {
			b.append(text(ss));
		}
		
		if( value.getSelector() != null ) {
			if( value.getCombinator() != null ) {
				b.append(" " + value.getCombinator());
			}
			b.append(" " + text(value.getSelector()));
		}
		
		b.toString();
	}
	
	
	def image(Selector s) {
		"selector_16x16.png";
	}
	
	def image(Ruleset ele) {
		"selector_16x16.png";
	}
	
	def image(CssProperty ele) {
		"property_16x16.png";
	}
	
	def image(CssSelector cssSelector) {
		"selector_16x16.png";
	}
	
	def expand(PseudoClassFunction pseudoFunc) {
		val b = new StringBuilder();
		if (pseudoFunc.isNot()) {
			// :not pseudo function
			b.append("not(");
			b.append(getText(pseudoFunc.getParamSelector()));
			b.append(")");
		}
		else {
			// normal pseudo function
			b.append(pseudoFunc.getName());
			b.append("(");
			val iterator = pseudoFunc.getParams().iterator();
			while (iterator.hasNext()) {
				val next = iterator.next();
				b.append(getText(next));
			}
			b.append(")");
		}
		return b.toString();
	}
	
	def text(IdentifierTok tok) {
		tok.name
	}
	
	def text(NumberTok tok) {
		String.valueOf(tok.^val)
	}
	
	def text(SymbolTok tok) {
		tok.symbol
	}
	
	def text(WSTok tok) {
		" "
	}
	
	def text(FuncTok tok) {
		'''«getText(tok.name)»(«tok.params.map[t|getText(t)].join(", ")»)'''
	}
	
	def text(UrlTok tok) {
		'''url(«tok.url.url»)'''
	}
	
	def text(EObject object) '''«object.toString»'''
	
	def text(CssTok tok) {
		tok.toString
	}
	
	
	
	def text(ElementSelector s)		'''«s.name»'''
	
	def text(CssSelector s) {
		dispatchCssSelector(s)
	}
	
	def dispatch dispatchCssSelector(IdSelector s)				'''#«s.name»'''
	def dispatch dispatchCssSelector(PseudoClass s)			''':«s»'''
	def dispatch dispatchCssSelector(PseudoClassFunction s) 	''':«expand(s)»'''
	def dispatch dispatchCssSelector(AttributeSelector s)		'''[«expand(s)»]'''
	def dispatch dispatchCssSelector(ClassSelector s)			'''.«s.name»'''
	
	//def text(CssSelector s) { "WTF?" + s.toString }
	
	def text(CssProperty p) '''«p.name»'''
	
	def expand(AttributeSelector s) {
		if (s.getOp() != null && s.getValue() != null) {
			return s.getName() + s.getOp() + s.getValue();
		}
		else {
			return s.getName();
		}
	}
	
	def text(SimpleSelectorForNegation value) {
		val b = new StringBuilder(/*"si-"*/);

		if( value.getElement() != null ) {
			b.append(getText(value.getElement()));
		}
		else if (value.getUniversal() != null) {
			b.append("*");
		}
		
		for( CssSelector sub : value.getSubSelectors() ) {
			b.append(getText(sub));
		}
		
		return b.toString();
	}
	
	def text(SimpleSelector value) {
		val b = new StringBuilder(/*"si-"*/);
		
		if( value.getElement() != null ) {
			b.append(text(value.getElement()));
		}
		else if (value.getUniversal() != null) {
			b.append("*");
		}
		
		for( CssSelector sub : value.getSubSelectors() ) {
			b.append(text(sub));
//			if (sub instanceof IdSelector) {
//				b.append(((IdSelector) sub).getName());
//			}
//			else if (sub instanceof ClassSelector) {
//				b.append(((ClassSelector) sub).getName());
//			}
//			else if (sub instanceof PseudoClass) {
//				b.append(((PseudoClass) sub).getName());
//			}
		}
		return b.toString();
	}
	
	def text(Stylesheet value) '''stylesheet'''
}