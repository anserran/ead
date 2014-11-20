package es.eucm.ead.editor.view.builders.scene.interaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.view.SkinConstants;
import es.eucm.ead.editor.view.widgets.WidgetBuilder;
import es.eucm.ead.editor.view.widgets.selectors.SceneSelector;

public class LinkEditor extends ComponentEditor {

	private Controller controller;

	private SceneSelector sceneSelector;

    private Label nextScene;

	public LinkEditor(Controller controller) {
		super(SkinConstants.IC_LINK, controller.getApplicationAssets()
				.getI18N().m("link"), "", controller);

		sceneSelector = new SceneSelector(controller);
		sceneSelector
				.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	protected void buildContent() {
        nextScene = WidgetBuilder.label("", SkinConstants.STYLE_EDITION);
        add(nextScene);
	}
}
