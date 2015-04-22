package es.eucm.ead.engine.components.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayer.CompletionListener;
import com.badlogic.gdx.video.VideoPlayer.VideoSizeListener;
import com.badlogic.gdx.video.VideoPlayerCreator;

public class VideoComponent extends RendererComponent implements
		CompletionListener, VideoSizeListener {

	private VideoPlayer videoPlayer;

	private FileHandle videoFile;

	private boolean playing;

	private boolean error;
	private FitViewport viewport;

	public void setVideo(FileHandle videoFile) {
		this.videoFile = videoFile;
		viewport = new FitViewport(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.videoPlayer = VideoPlayerCreator.createVideoPlayer(viewport);
		videoPlayer.setOnCompletionListener(this);
		videoPlayer.setOnVideoSizeListener(this);
	}

	@Override
	public void draw(Batch batch) {
		if (!error && !playing) {
			try {
				videoPlayer.play(videoFile);
				playing = true;
			} catch (Exception e) {
				Gdx.app.error("VideoComponent", "Unable to play video "
						+ videoFile, e);
				error = true;
			}
		}

		if (!error) {
			try {
				if (videoPlayer.isBuffered()) {
					videoPlayer.render();
				}
			} catch (Exception e) {
				error = true;
			}
		}
	}

	@Override
	public float getWidth() {
		return Gdx.graphics.getWidth();
	}

	@Override
	public float getHeight() {
		return Gdx.graphics.getHeight();
	}

	@Override
	public Array<Polygon> getCollider() {
		return null;
	}

	@Override
	public void reset() {
		playing = false;
		error = false;
		videoPlayer.stop();
		videoPlayer.dispose();
	}

	@Override
	public boolean hit(float x, float y) {
		return playing;
	}

	@Override
	public void onCompletionListener(FileHandle file) {
		playing = false;
		Gdx.app.getApplicationListener().resize(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
	}

	@Override
	public void onVideoSize(float width, float height) {
		viewport.setWorldSize(width, height);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
}
