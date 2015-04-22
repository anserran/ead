package es.eucm.ead.editor.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import es.eucm.ead.editor.control.engine.Engine;
import es.eucm.ead.engine.components.renderers.VideoComponent;

public class EditorVideoComponent extends VideoComponent {

	private Drawable drawable;

	private Engine engine;

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	@Override
	public void draw(Batch batch) {
		if (engine.isRunning()) {
			super.draw(batch);
		} else {
			drawable.draw(batch, 0, 0, getWidth(), getHeight());
		}
	}
}
