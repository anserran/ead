/**
 * eAdventure is a research project of the
 *    e-UCM research group.
 *
 *    Copyright 2005-2014 e-UCM research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          CL Profesor Jose Garcia Santesmases 9,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.eucm.ead.editor.control.pastelisteners;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import es.eucm.ead.editor.control.Clipboard.CopyListener;
import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.actions.editor.AddLabel;
import es.eucm.ead.editor.control.actions.editor.ExecuteWorker;
import es.eucm.ead.editor.control.actions.model.AddSceneElement;
import es.eucm.ead.editor.control.workers.DownloadFile;
import es.eucm.ead.editor.control.workers.Worker.WorkerListener;
import es.eucm.ead.editor.utils.ProjectUtils;
import es.eucm.ead.engine.assets.Assets;
import es.eucm.ead.schema.components.controls.Label;
import es.eucm.ead.schemax.ModelStructure;

public class TextCopyListener implements CopyListener<String> {

	private Controller controller;

	public TextCopyListener(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void cut(String object) {
	}

	@Override
	public void paste(String object) {
		if (object.matches(Assets.URL_PATTERN)
				&& ProjectUtils.isSupportedImage(object)) {
			String extension = object.substring(object.lastIndexOf('.') + 1);
			String fileName = object.substring(object.lastIndexOf('/') + 1,
					object.lastIndexOf('.'));

			FileHandle dstImage = ProjectUtils
					.getNonExistentFile(controller.getEditorGameAssets()
							.resolve(ModelStructure.IMAGES_FOLDER), fileName,
							extension);
			controller.action(ExecuteWorker.class, DownloadFile.class,
					new URLImage(dstImage.path()), object, dstImage);
		} else {
			Label label = new Label();
			label.setText(object);
			controller.action(AddLabel.class, label);
		}
	}

	@Override
	public void paste(Array<String> object) {
	}

	public class URLImage implements WorkerListener {

		private String dstImage;

		public URLImage(String dstImage) {
			this.dstImage = dstImage;
		}

		@Override
		public void start() {

		}

		@Override
		public void result(Object... results) {

		}

		@Override
		public void done() {
			controller.action(AddSceneElement.class, controller.getTemplates()
					.createSceneElement(dstImage, false));
		}

		@Override
		public void error(Throwable ex) {

		}

		@Override
		public void cancelled() {

		}
	}
}
