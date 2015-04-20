/*******************************************************************************
 * Copyright (c) 2015 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package org.eclipse.fx.ui.workbench.renderers.base.services;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

/**
 * Service to constraint drag and drop operations
 */
public interface DnDConstraintService {
	/**
	 * Check if a split is allowed
	 * 
	 * @param element
	 *            the element
	 * @return <code>true</code> if a split is allowed
	 */
	public boolean splitAllowed(MUIElement element);
}
