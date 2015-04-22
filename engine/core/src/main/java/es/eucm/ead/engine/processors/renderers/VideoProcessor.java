package es.eucm.ead.engine.processors.renderers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.video.VideoPlayer;

import com.badlogic.gdx.video.VideoPlayerCreator;
import es.eucm.ead.engine.GameLoop;
import es.eucm.ead.engine.assets.GameAssets;
import es.eucm.ead.engine.components.renderers.VideoComponent;
import es.eucm.ead.schema.renderers.Video;

public class VideoProcessor extends RendererProcessor<Video> {

	private VideoPlayer videoPlayer;

	public VideoProcessor(GameLoop gameLoop, GameAssets gameAssets) {
		super(gameLoop, gameAssets);
	}

	@Override
	public Component getComponent(Video video) {
		if (videoPlayer == null) {
			videoPlayer = VideoPlayerCreator.createVideoPlayer();
		}

		VideoComponent videoComponent = gameLoop
				.createComponent(VideoComponent.class);
		videoComponent.setVideoPlayer(videoPlayer);
		videoComponent.setVideoFile(gameAssets.resolve(video.getUri()));
		return videoComponent;
	}
}
