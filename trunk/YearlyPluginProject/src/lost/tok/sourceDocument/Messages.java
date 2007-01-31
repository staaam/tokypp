package lost.tok.sourceDocument;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "lost.tok.sourceDocument.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			ResourceBundle rs = RESOURCE_BUNDLE;
			return RESOURCE_BUNDLE.getString(key);

		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
