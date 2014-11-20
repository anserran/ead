package es.eucm.ead.editor.view.builders.scene.interaction;

import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.Selection;
import es.eucm.ead.editor.model.Q;
import es.eucm.ead.editor.view.SkinConstants;
import es.eucm.ead.editor.view.widgets.WidgetBuilder;
import es.eucm.ead.editor.view.widgets.layouts.LinearLayout;
import es.eucm.ead.schema.components.ModelComponent;
import es.eucm.ead.schema.entities.ModelEntity;

public abstract class ComponentEditor extends LinearLayout {

	private Controller controller;

	private String componentId;

	public ComponentEditor(String icon, String label, String componentId,
			Controller controller) {
		super(false);
		this.controller = controller;
		this.componentId = componentId;
		LinearLayout header = new LinearLayout(true);
		header.defaultWidgetsMargin(WidgetBuilder.dpToPixels(16),
				WidgetBuilder.dpToPixels(16), 0, 0);
		header.add(WidgetBuilder.icon(icon, SkinConstants.STYLE_EDITION));
		header.add(WidgetBuilder.label(label, SkinConstants.STYLE_EDITION));
		header.addSpace();
		header.add(
				WidgetBuilder.icon(SkinConstants.IC_DELETE,
						SkinConstants.STYLE_EDITION)).margin(0, 0,
				WidgetBuilder.dpToPixels(16), 0);
		add(header).expandX();
		buildContent();
		addSpace();
	}

	public void prepare() {
		ModelEntity sceneElement = (ModelEntity) controller.getModel().getSelection()
				.getSingle(Selection.SCENE_ELEMENT);
        if ( sceneElement != null ){
            ModelComponent component = Q.getComponentById(sceneElement, componentId);
        }
        
	}

	protected abstract void buildContent();

	@Override
	public float getPrefWidth() {
		return Math.max(super.getPrefWidth(), WidgetBuilder.dpToPixels(200));
	}
}
