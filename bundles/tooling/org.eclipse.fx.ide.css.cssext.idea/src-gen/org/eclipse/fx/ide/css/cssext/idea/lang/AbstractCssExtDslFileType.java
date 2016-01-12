/*
 * generated by Xtext 2.9.0
 */
package org.eclipse.fx.ide.css.cssext.idea.lang;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import javax.swing.Icon;
import org.eclipse.xtext.idea.Icons;
import org.jetbrains.annotations.NonNls;

public class AbstractCssExtDslFileType extends LanguageFileType {

	@NonNls 
	public static final String DEFAULT_EXTENSION = "cssext";

	protected AbstractCssExtDslFileType(final Language language) {
		super(language);
	}

	@Override
	public String getDefaultExtension() {
		return DEFAULT_EXTENSION;
	}

	@Override
	public String getDescription() {
		return "CssExtDsl files";
	}

	@Override
	public Icon getIcon() {
		return Icons.DSL_FILE_TYPE;
	}

	@Override
	public String getName() {
		return "CssExtDsl";
	}

}
