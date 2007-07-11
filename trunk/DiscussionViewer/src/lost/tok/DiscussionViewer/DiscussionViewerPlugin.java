package lost.tok.DiscussionViewer;

import org.eclipse.ui.plugin.*;
import org.osgi.framework.BundleContext;
import java.util.*;


public class DiscussionViewerPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static DiscussionViewerPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
  

	public DiscussionViewerPlugin() {
		super();
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle("lost.tok.DiscussionViewer.DiscussionViewerPluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}


	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	public static DiscussionViewerPlugin getDefault() {
		return plugin;
	}


	public static String getResourceString(String key) {
		ResourceBundle bundle = DiscussionViewerPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
}
