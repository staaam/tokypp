package editor.diagram.part;

import java.util.List;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.jface.resource.ImageDescriptor;
import editor.diagram.providers.DiscussionElementTypes;

import java.util.ArrayList;

import org.eclipse.gef.palette.PaletteGroup;

import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

/**
 * @generated
 */
public class DiscussionPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createeditor1Group());
	}

	/**
	 * @generated
	 */
	private PaletteContainer createeditor1Group() {
		PaletteContainer paletteContainer = new PaletteGroup("editor");
		paletteContainer.add(createOpinion1CreationTool());
		paletteContainer.add(createQuote2CreationTool());
		paletteContainer.add(createRelation3CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createOpinion1CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DiscussionElementTypes
				.getImageDescriptor(DiscussionElementTypes.Relation_1001);

		largeImage = smallImage;

		final List elementTypes = new ArrayList();
		elementTypes.add(DiscussionElementTypes.Relation_1001);
		ToolEntry result = new NodeToolEntry("Opinion", "Create new Opinion",
				smallImage, largeImage, elementTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createQuote2CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DiscussionElementTypes
				.getImageDescriptor(DiscussionElementTypes.RelationSecond_3001);

		largeImage = smallImage;

		final List relationshipTypes = new ArrayList();
		relationshipTypes.add(DiscussionElementTypes.RelationSecond_3001);
		ToolEntry result = new LinkToolEntry("Quote", "Create new Quote",
				smallImage, largeImage, relationshipTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createRelation3CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DiscussionElementTypes
				.getImageDescriptor(DiscussionElementTypes.RelationFirst_3002);

		largeImage = smallImage;

		final List relationshipTypes = new ArrayList();
		relationshipTypes.add(DiscussionElementTypes.RelationFirst_3002);
		ToolEntry result = new LinkToolEntry("Relation", "Create new Relation",
				smallImage, largeImage, relationshipTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private static class NodeToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List elementTypes;

		/**
		 * @generated
		 */
		private NodeToolEntry(String title, String description,
				ImageDescriptor smallIcon, ImageDescriptor largeIcon,
				List elementTypes) {
			super(title, description, smallIcon, largeIcon);
			this.elementTypes = elementTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	/**
	 * @generated
	 */
	private static class LinkToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List relationshipTypes;

		/**
		 * @generated
		 */
		private LinkToolEntry(String title, String description,
				ImageDescriptor smallIcon, ImageDescriptor largeIcon,
				List relationshipTypes) {
			super(title, description, smallIcon, largeIcon);
			this.relationshipTypes = relationshipTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
}
