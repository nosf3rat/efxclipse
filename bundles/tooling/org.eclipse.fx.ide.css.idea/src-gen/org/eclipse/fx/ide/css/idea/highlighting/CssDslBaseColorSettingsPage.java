/*
 * generated by Xtext 2.9.0
 */
package org.eclipse.fx.ide.css.idea.highlighting;

import org.eclipse.fx.ide.css.idea.lang.CssDslLanguage;
import org.eclipse.xtext.idea.highlighting.AbstractColorSettingsPage;

public class CssDslBaseColorSettingsPage extends AbstractColorSettingsPage {
	
	public CssDslBaseColorSettingsPage() {
		CssDslLanguage.INSTANCE.injectMembers(this);
	}

	@Override
	public String getDisplayName() {
		return CssDslLanguage.INSTANCE.getDisplayName();
	}
}
