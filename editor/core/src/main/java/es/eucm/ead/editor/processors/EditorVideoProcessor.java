package es.eucm.ead.editor.processors;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;

import es.eucm.ead.editor.assets.ApplicationAssets;
import es.eucm.ead.editor.components.EditorVideoComponent;
import es.eucm.ead.editor.control.engine.Engine;
import es.eucm.ead.engine.GameLoop;
import es.eucm.ead.engine.assets.GameAssets;
import es.eucm.ead.engine.components.renderers.VideoComponent;
import es.eucm.ead.engine.processors.renderers.VideoProcessor;

public class EditorVideoProcessor extends VideoProcessor {

	private Drawable drawable;

	private Engine engine;

	public EditorVideoProcessor(GameLoop gameLoop, GameAssets gameAssets,
			ApplicationAssets editorGameAssets, Engine engine) {
		super(gameLoop, gameAssets);
		drawable = editorGameAssets.getSkin()
				.getDrawable("tablet_presentation");
		this.engine = engine;
	}

	@Override
	protected VideoComponent createComponent() {
		EditorVideoComponent videoComponent = Pools
				.obtain(EditorVideoComponent.class);
		videoComponent.setDrawable(drawable);
		videoComponent.setEngine(engine);
		return videoComponent;
	}
}
