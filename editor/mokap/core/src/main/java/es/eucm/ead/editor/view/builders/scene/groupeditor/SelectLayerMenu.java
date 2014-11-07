package es.eucm.ead.editor.view.builders.scene.groupeditor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import es.eucm.ead.editor.view.widgets.AbstractWidget;
import es.eucm.ead.editor.view.widgets.ContextMenu;

public class SelectLayerMenu extends ContextMenu {

	private GroupEditor groupEditor;

	private Array<Actor> layersSelected;

	public SelectLayerMenu(Array<Actor> layersSelected, GroupEditor groupEditor) {
		super();
		this.groupEditor = groupEditor;
		this.layersSelected = layersSelected;
	}

	@Override
	public void show() {
		clearChildren();
		for (Actor layer : layersSelected) {
			Layer layerWidget = new Layer();
			layerWidget.setActor(layer);
            add(layerWidget).fillX();
            row();
		}
        pack();
		super.show();
	}

    @Override
    public void hide(Runnable runnable) {
    }

    public static class Layer extends AbstractWidget {

		private static final float CM = 2.0f;

		private Actor actor;

		public void setActor(Actor actor) {
			this.actor = actor;
		}

		@Override
		protected void applyTransform(Batch batch, Matrix4 transform) {
			float scale = Math.min(getWidth() / actor.getWidth(), getHeight()
					/ actor.getHeight());
			transform.scl(scale, scale, 1.0f);
			super.applyTransform(batch, transform);
		}

		@Override
		protected void drawChildren(Batch batch, float parentAlpha) {
			actor.draw(batch, parentAlpha);
		}

		@Override
		public float getPrefWidth() {
			return cmToXPixels(CM);
		}

		@Override
        public float getPrefHeight() {
			return cmToYPixels(CM);
		}
	}
}
