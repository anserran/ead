package es.eucm.ead.engine.components.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayer.CompletionListener;

public class VideoComponent extends RendererComponent implements
		CompletionListener {

	private VideoPlayer videoPlayer;

	private FileHandle videoFile;

	private boolean playing;

	private boolean error;

	public void setVideoPlayer(VideoPlayer videoPlayer) {
		this.videoPlayer = videoPlayer;
		videoPlayer.setOnCompletionListener(this);
	}

	public void setVideoFile(FileHandle videoFile) {
		this.videoFile = videoFile;
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
				videoPlayer.render();
			} catch (Exception e) {
				error = true;
			}
		}
	}

	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public float getHeight() {
		return 0;
	}

	@Override
	public Array<Polygon> getCollider() {
		return null;
	}

	@Override
	public void reset() {
		playing = false;
		error = false;
	}

	@Override
	public void onCompletionListener(FileHandle file) {

	}
}
