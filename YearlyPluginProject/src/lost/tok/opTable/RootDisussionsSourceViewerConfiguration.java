package lost.tok.opTable;

import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

/**
 * 
 * Configures Root Discussions Viewer (Editor) to catch Double Click and Hover
 * events
 * 
 * @author Michael Gelfand
 * 
 */
public class RootDisussionsSourceViewerConfiguration extends
		SourceViewerConfiguration {
	private RootDiscussionsView rootDiscussions;

	public RootDisussionsSourceViewerConfiguration(
			RootDiscussionsView rootDiscussions) {
		this.rootDiscussions = rootDiscussions;
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		return rootDiscussions;
	}

	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer,
			String contentType, int stateMask) {
		return rootDiscussions;
	}

	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer,
			String contentType) {
		return rootDiscussions;
	}
}
