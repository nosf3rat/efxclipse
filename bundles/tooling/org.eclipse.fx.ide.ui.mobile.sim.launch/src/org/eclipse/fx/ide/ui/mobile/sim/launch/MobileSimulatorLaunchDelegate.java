package org.eclipse.fx.ide.ui.mobile.sim.launch;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.JavaLaunchDelegate;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.internal.core.PDECore;

@SuppressWarnings("restriction")
public class MobileSimulatorLaunchDelegate extends JavaLaunchDelegate {
	@Override
	public String[] getClasspath(ILaunchConfiguration configuration) throws CoreException {
		Set<String> paths = new HashSet<>(Arrays.asList(super.getClasspath(configuration)));
		paths.add(getSimulatorPath());	
		return paths.toArray(new String[0]);
	}

	@Override
	public String getProgramArguments(ILaunchConfiguration configuration) throws CoreException {
		String s = super.getProgramArguments(configuration);
		s += " -deviceType " + configuration.getAttribute(MobileSimulatorLaunchConfigurationTabGroup.DEVICE_TYPE, DeviceType.IPHONE.name());
		s += " -deviceOrientation " + configuration.getAttribute(MobileSimulatorLaunchConfigurationTabGroup.DEVICE_ORIENTATION, Orientation.VERTICAL.name());
		s += " -displaySize " + configuration.getAttribute(MobileSimulatorLaunchConfigurationTabGroup.DEVICE_SIZE, "640x1136");
		s += " -applicationClass " + configuration.getAttribute(MobileSimulatorLaunchConfigurationTabGroup.APP_CLASS, "");
		
		System.err.println("LAUNCH: " + s);
		return s;
	}

	private String getSimulatorPath() {
		IPluginModelBase findModel = PDECore.getDefault().getModelManager().findModel("org.eclipse.fx.ide.ui.mobile.sim.device");
		
		String installLocation = findModel == null ? null : findModel.getInstallLocation();
		if( installLocation == null ) {
			installLocation = Platform.getBundle("org.eclipse.fx.ide.ui.mobile.sim.device").getLocation();
		}
		File f = new File(installLocation);
		
		if( f.isDirectory() ) {
			return installLocation+"/bin";
		} else {
			return installLocation;
		}
	}
}
