package lost.tok;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class ToKBuilder extends IncrementalProjectBuilder {

	public static String BUILDER_ID = "lost.tok.ToKBuilder";

	public ToKBuilder() {
		System.out.println("tokBuilder");
	}

	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		System.out.println("build");
		return null;
	}

}
