/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * example snippet: Hello World
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.fx.runtime.swtutil.SWTUtil;
import org.eclipse.fx.runtime.swtutil.SWTUtil.BlockCondition;
import org.eclipse.fx.runtime.swtutil.SWTUtil.SWTAppStart;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet1 implements SWTAppStart {

	@Override
	public BlockCondition createApp(Display display) {
		Shell shell = new Shell(display);
		shell.setSize(200, 200);
		shell.open();
		// We don't need that in FX
		 while (!shell.isDisposed ()) {
		 if (!display.readAndDispatch ()) display.sleep ();
		 }
		 display.dispose();
		 return null;
	}
	
	public static void main(String[] args) {
		SWTUtil.getInstance().bootstrap(new Snippet1());
	}	
}
