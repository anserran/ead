package es.eucm.ead.engine.components.renderers.frames;

import com.badlogic.gdx.utils.Array;
import es.eucm.ead.engine.components.renderers.frames.sequences.Sequence;
import es.eucm.ead.engine.entities.actors.RendererActor;

public class FramesActor extends RendererActor {

	private float elapsedTime = 0;

	private FrameData currentFrame;

	private Array<FrameData> framesData = new Array<FrameData>();

	private Sequence function;

	public void setFunction(Sequence function) {
		this.function = function;
	}

	public void addFrame(RendererActor renderer, float duration) {
		framesData.add(new FrameData(renderer, duration, framesData.size));
		if (framesData.size == 1) {
			setCurrentFrame(0);
		}
	}

	private void setCurrentFrame(int index) {
		currentFrame = framesData.get(index);
		clearChildren();
		addActor(currentFrame.renderer);
	}

    @Override
    public void restart() {
        setCurrentFrame(function.getFirst(framesData.size));
        if (currentFrame != null) {
            currentFrame.renderer.restart();
        }
    }

	@Override
	public void act(float delta) {
		elapsedTime += delta;
		float surplus;
		do {
			surplus = elapsedTime - currentFrame.duration;
			if (surplus >= 0) {
				elapsedTime = surplus;
				setCurrentFrame(function.getNextIndex(currentFrame.index,
						framesData.size));
			}
		} while (surplus > 0 && currentFrame != null);
		super.act(delta);
	}

	@Override
	public float getPrefWidth() {
		return currentFrame == null ? 0 : currentFrame.renderer.getPrefWidth();
	}

	@Override
	public float getPrefHeight() {
		return currentFrame == null ? 0 : currentFrame.renderer.getPrefHeight();
	}

	@Override
	public void layout() {
		if (currentFrame != null) {
			currentFrame.renderer.setBounds(0, 0, getWidth(), getHeight());
		}
	}

	private static class FrameData {
		int index;
		float duration;
		RendererActor renderer;

		public FrameData(RendererActor renderer, float duration, int index) {
			this.renderer = renderer;
			this.duration = duration;
			this.index = index;
		}
	}

}
