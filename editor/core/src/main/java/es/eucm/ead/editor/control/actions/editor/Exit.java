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

import com.badlogic.gdx.Gdx;

import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.actions.EditorAction;
import es.eucm.ead.editor.view.builders.classic.dialogs.InfoDialogBuilder;
import es.eucm.ead.editor.view.controllers.DialogController.DialogButtonListener;
import es.eucm.i18n.I18N;

/**
 * <p>
 * Closes the entire editor, finalizing the application completely.
 * </p>
 * 
 * <dl>
 * <dt><strong>Arguments</strong></dt>
 * <dd><strong>args[0]</strong> <em>(Optional) Boolean</em> Whether it should
 * ask for saving confirmation (true) or not (false).</dd>
 * </dl>
 * 
 */
public class Exit extends EditorAction {

	private I18N i18N;

	private DialogButtonListener yesListener = new DialogButtonListener() {
		@Override
		public void selected() {
			while (controller.getCommands().getCommandsStack().size() > 0) {
				controller.getCommands().popStack(false);
			}
			controller.action(Save.class);
			exit();
		}
	};

	private DialogButtonListener noListener = new DialogButtonListener() {
		@Override
		public void selected() {
			exit();
		}
	};

	public Exit() {
		super(true, false, new Class[] {}, new Class[] { Boolean.class });
	}

	@Override
	public void initialize(Controller controller) {
		super.initialize(controller);
		i18N = controller.getApplicationAssets().getI18N();
	}

	@Override
	public void perform(Object... args) {
		if (controller.getCommands().commandsPendingToSave()) {
			if (args.length == 1 && !(Boolean) args[0]) {
				yesListener.selected();
			} else {
				controller.action(ShowDialog.class, InfoDialogBuilder.class,
						i18N.m("dialog.unsavedchanges"),
						i18N.m("dialog.unsavedchanges.message"),
						i18N.m("general.yes"), yesListener,
						i18N.m("general.no"), noListener,
						i18N.m("general.cancel"), null);
			}
		} else {
			exit();
		}
	}

	private void exit() {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				Gdx.app.exit();
			}
		});
	}
}
