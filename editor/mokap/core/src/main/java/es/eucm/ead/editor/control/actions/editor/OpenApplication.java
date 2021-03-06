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
package es.eucm.ead.editor.control.actions.editor;

import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.actions.EditorAction;
import es.eucm.ead.editor.control.actions.editor.OpenLastProject.ErrorCallback;
import es.eucm.ead.editor.platform.MokapPlatform;
import es.eucm.ead.editor.platform.Platform;
import es.eucm.ead.editor.utils.ProjectUtils;
import es.eucm.ead.editor.view.builders.home.HomeView;

/**
 * <p>
 * Tries to open the last opened game or
 * {@link Platform#getApplicationArguments()}[0] if any.
 * </p>
 * <dl>
 * <dt><strong>Arguments</strong></dt>
 * <dd><strong>None</strong></dd>
 * </dl>
 */
public class OpenApplication extends EditorAction {

	public OpenApplication() {
		super(true, false);
	}

	@Override
	public void perform(Object... args) {

		MokapPlatform platform = (MokapPlatform) controller.getPlatform();
		Object[] appArgs = platform.getApplicationArguments();
		String importProjectPath = (appArgs == null || appArgs.length != 1) ? null
				: (String) appArgs[0];

		Class elseView = HomeView.class;
		if (importProjectPath != null && !importProjectPath.isEmpty()
				&& importProjectPath.endsWith(ProjectUtils.ZIP_EXTENSION)) {
			controller.action(ImportProject.class, elseView,
					new ShowErrorToastCallback(controller));
		} else {
			controller.action(OpenLastProject.class, elseView,
					new ShowErrorToastCallback(controller));
		}
	}

	public static class ShowErrorToastCallback implements ErrorCallback {

		private static final String PROJECT_NOT_FOUND = "project.not.found";
		private static final String PROJECT_IS_CORRUPTED = "project.is.corrupted";
		private Controller controller;

		public ShowErrorToastCallback(Controller controller) {
			this.controller = controller;
		}

		@Override
		public void error(Result result, String projectPath) {
			String message;
			if (result == Result.PROJECT_NOT_FOUND) {
				message = PROJECT_NOT_FOUND;

			} else {
				message = PROJECT_IS_CORRUPTED;

			}
			controller.action(ShowTextDialog.class, controller
					.getApplicationAssets().getI18N().m(message, projectPath));
		}
	}
}
