/*
 * generated by Xtext 2.9.0
 */
package org.eclipse.fx.ide.css.cssext.idea.highlighting;

import org.eclipse.fx.ide.css.cssext.idea.lang.CssExtDslLanguage;
import org.eclipse.xtext.idea.highlighting.SemanticHighlightVisitor;

public class CssExtDslSemanticHighlightVisitor extends SemanticHighlightVisitor {
	public CssExtDslSemanticHighlightVisitor() {
		CssExtDslLanguage.INSTANCE.injectMembers(this);
	}
}
