/*
 * generated by Xtext 2.9.0
 */
package org.eclipse.fx.ide.css

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class CssDslStandaloneSetup extends CssDslStandaloneSetupGenerated {

	def static void doSetup() {
		new CssDslStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}
