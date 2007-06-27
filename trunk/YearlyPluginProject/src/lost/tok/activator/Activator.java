package lost.tok.activator;

import lost.tok.ToKChangeListener;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "lost.tok"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/** The path of source.xsd file */
	public static final String sourceXsdPath = "C:\\temp\\TestFiles\\"; //$NON-NLS-1$
	
	private ToKChangeListener listener;

	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		IWorkspace ws = ResourcesPlugin.getWorkspace(); 
		listener = new ToKChangeListener();
		ws.addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		if (listener != null)
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
		
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
