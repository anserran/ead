package es.eucm.ead.engine.processors.renderers;

import com.badlogic.ashley.core.Component;
import es.eucm.ead.engine.GameLoop;
import es.eucm.ead.engine.assets.GameAssets;
import es.eucm.ead.engine.components.renderers.VideoComponent;
import es.eucm.ead.schema.renderers.Video;

public class VideoProcessor extends RendererProcessor<Video> {

	public VideoProcessor(GameLoop gameLoop, GameAssets gameAssets) {
		super(gameLoop, gameAssets);
	}

	@Override
	public Component getComponent(Video video) {
		VideoComponent videoComponent = createComponent();
		videoComponent.setVideo(gameAssets.resolve(video.getUri()));
		return videoComponent;
	}

	protected VideoComponent createComponent() {
		return gameLoop.createComponent(VideoComponent.class);
	}
}
