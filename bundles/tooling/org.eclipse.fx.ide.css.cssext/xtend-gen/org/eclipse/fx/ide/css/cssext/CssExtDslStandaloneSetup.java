/**
 * generated by Xtext 2.9.0
 */
package org.eclipse.fx.ide.css.cssext;

import org.eclipse.fx.ide.css.cssext.CssExtDslStandaloneSetupGenerated;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
@SuppressWarnings("all")
public class CssExtDslStandaloneSetup extends CssExtDslStandaloneSetupGenerated {
  public static void doSetup() {
    CssExtDslStandaloneSetup _cssExtDslStandaloneSetup = new CssExtDslStandaloneSetup();
    _cssExtDslStandaloneSetup.createInjectorAndDoEMFRegistration();
  }
}
