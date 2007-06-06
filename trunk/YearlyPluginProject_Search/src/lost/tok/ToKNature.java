package lost.tok;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class ToKNature implements IProjectNature {

	/**
	 * ID of this project nature
	 */
	public static final String NATURE_ID = "lost.tok.ToKNature"; //$NON-NLS-1$

	IProject project;

	public void configure() throws CoreException {
		System.out.println("configure"); //$NON-NLS-1$

		IProjectDescription desc = project.getDescription();
		ICommand[] commands = desc.getBuildSpec();

		for (ICommand element : commands) {
			if (element.getBuilderName().equals(ToKBuilder.BUILDER_ID)) {
				return;
			}
		}

		ICommand[] newCommands = new ICommand[commands.length + 1];
		System.arraycopy(commands, 0, newCommands, 0, commands.length);
		ICommand command = desc.newCommand();
		command.setBuilderName(ToKBuilder.BUILDER_ID);
		newCommands[newCommands.length - 1] = command;
		desc.setBuildSpec(newCommands);
		project.setDescription(desc, null);
	}

	public void deconfigure() throws CoreException {
		System.out.println("deconfigure"); //$NON-NLS-1$
		IProjectDescription description = getProject().getDescription();
		ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(ToKBuilder.BUILDER_ID)) {
				ICommand[] newCommands = new ICommand[commands.length - 1];
				System.arraycopy(commands, 0, newCommands, 0, i);
				System.arraycopy(commands, i + 1, newCommands, i,
						commands.length - i - 1);
				description.setBuildSpec(newCommands);
				return;
			}
		}
	}

	public IProject getProject() {
		System.out.println("getProject"); //$NON-NLS-1$
		return project;
	}

	public void setProject(IProject project) {
		System.out.println("setProject"); //$NON-NLS-1$
		this.project = project;
	}

	public static void setNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for (String element : natures) {
				if (NATURE_ID.equals(element)) {
					// nature already exists
					return;
				}
			}

			// Add the nature
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} catch (CoreException e) {
		}

	}

}
