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
package es.eucm.ead.editor.view.builders.home;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.MokapController.BackListener;
import es.eucm.ead.editor.control.actions.editor.Exit;
import es.eucm.ead.editor.control.background.BackgroundExecutor;
import es.eucm.ead.editor.control.background.BackgroundExecutor.BackgroundTaskListener;
import es.eucm.ead.editor.control.background.LoadProjects;
import es.eucm.ead.editor.view.builders.ViewBuilder;

public class HomeView implements ViewBuilder, BackListener {

	private Table view;

	private Controller controller;

	private LoadProjects loadProjects;

	@Override
	public void initialize(Controller c) {
		this.controller = c;

		Table container = new Table();
		loadProjects = new LoadProjects(controller, container);
		view = container;
	}

	@Override
	public Actor getView(Object... args) {
        view.clear();
		controller.getBackgroundExecutor().submit(loadProjects,
				new BackgroundTaskListener<Void>() {
					@Override
					public void completionPercentage(float percentage) {

					}

					@Override
					public void result(BackgroundExecutor backgroundExecutor,
							Void result) {

					}

					@Override
					public void error(Throwable e) {

					}
				});
		return view;
	}

	@Override
	public void release(Controller controller) {

	}

	@Override
	public void onBackPressed() {
		controller.action(Exit.class, false);
	}
}
