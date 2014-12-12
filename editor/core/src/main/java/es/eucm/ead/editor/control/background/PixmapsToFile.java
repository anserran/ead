package es.eucm.ead.editor.control.background;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;

/**
 * Creates a new Pixmap with a set of pixmaps and then writes it to a file.
 * Pixmaps are expected to come slice as columns of the total pixmap
 */
public class PixmapsToFile extends BackgroundTask<String> {

	private Pixmap[] pixmaps;

	private FileHandle path;

	public PixmapsToFile(Pixmap[] pixmaps, FileHandle path) {
		this.pixmaps = pixmaps;
		this.path = path;
	}

	@Override
	public String call() throws Exception {
		int height = 0;
		int width = 0;
		for (Pixmap pixmap : pixmaps) {
			height = Math.max(height, pixmap.getHeight());
			width += pixmap.getWidth();
		}

		Pixmap pixmap = new Pixmap(width, height, Format.RGB888);
		int xOffset = 0;
		for (Pixmap p : pixmaps) {
			pixmap.drawPixmap(p, xOffset, 0);
			xOffset += p.getWidth();
			p.dispose();
		}

		PixmapIO.writePNG(path, pixmap);
		pixmap.dispose();

		return path.path();
	}
}
