/**
 * eAdventure is a research project of the
 *    e-UCM research group.
 *
 *    Copyright 2005-2013 e-UCM research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          C Profesor Jose Garcia Santesmases sn,
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
package es.eucm.ead.editor.view.builders.mockup.gallery;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import es.eucm.ead.editor.assets.EditorAssets;
import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.Preferences;
import es.eucm.ead.editor.control.Preferences.PreferenceListener;
import es.eucm.ead.editor.control.actions.ChangeView;
import es.eucm.ead.editor.control.actions.CombinedAction;
import es.eucm.ead.editor.control.actions.NewGame;
import es.eucm.ead.editor.control.actions.OpenGame;
import es.eucm.ead.editor.model.Project;
import es.eucm.ead.editor.view.builders.mockup.menu.InitialScreen;
import es.eucm.ead.editor.view.builders.mockup.menu.ProjectScreen;
import es.eucm.ead.editor.view.listeners.ActionOnClickListener;
import es.eucm.ead.editor.view.widgets.mockup.buttons.IconButton;
import es.eucm.ead.editor.view.widgets.mockup.buttons.ProjectButton;
import es.eucm.ead.editor.view.widgets.mockup.buttons.ToolbarButton;
import es.eucm.ead.editor.view.widgets.mockup.panels.GalleryGrid;
import es.eucm.ead.engine.I18N;
import es.eucm.ead.schema.game.Game;

/**
 * The gallery that will display our projects. Has a top tool bar and a gallery
 * grid.
 */
public class ProjectGallery extends BaseGallery implements PreferenceListener {

	public static final String NAME = "mockup_project_gallery";

	private static final String ADD_PROJECT_BUTTON = "ic_newproject";
	private static final String PROJECTS = "ProjectGallery";
	private static final String IC_GO_BACK = "ic_goback";

	private boolean projectsChanged;
	private Button addProjectButton;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Actor build(Controller controller) {
		controller.getPreferences().addPreferenceListener(
				Preferences.RECENT_GAMES, this);
		this.projectsChanged = true;
		return super.build(controller);
	}

	@Override
	protected Button topLeftButton(Vector2 viewport, Skin skin,
			Controller controller) {
		Button backButton = new ToolbarButton(viewport, skin, IC_GO_BACK);
		backButton.addListener(new ActionOnClickListener(controller,
				ChangeView.NAME, InitialScreen.NAME));
		return backButton;
	}

	@Override
	protected void addElementsToTheGallery(Controller controller,
			GalleryGrid<Actor> galleryTable, Vector2 viewport, I18N i18n,
			Skin skin) {
		if (!this.projectsChanged) {
			Gdx.app.log(PROJECTS,
					"Projects are already loaded and nothing has changed!");
			return;
		}

		final FileHandle projectsRoot = InitialScreen.MOCKUP_PROJECT_FILE;
		if (!projectsRoot.exists()) {
			projectsRoot.mkdirs();
		}

		clearGallery();
		if (this.addProjectButton == null) {
			Project newProject = new Project();
			newProject.setTitle("");
			newProject.setDescription("");
			this.addProjectButton = new IconButton(viewport, skin,
					ADD_PROJECT_BUTTON, controller, CombinedAction.NAME,
					NewGame.NAME, new Object[] {
							projectsRoot.file().getAbsolutePath()
									+ File.separator
									+ i18n.m("project.untitled"), newProject,
							new Game() }, ChangeView.NAME,
					new Object[] { ProjectScreen.NAME });
		}
		galleryTable.addItem(this.addProjectButton);

		final String projectEnding = File.separator + "project.json";
		final EditorAssets editorAssets = controller.getEditorAssets();
		for (final FileHandle project : projectsRoot.list()) {
			if (project.isDirectory()) {
				final String rootProjectJsonPath = project.file()
						.getAbsolutePath();
				final String projectJsonPath = rootProjectJsonPath
						+ projectEnding;
				final FileHandle projectJsonFile = editorAssets
						.absolute(projectJsonPath);
				if (projectJsonFile.exists()) {
					Project proj = editorAssets.fromJson(Project.class,
							projectJsonFile);
					galleryTable.addItem(new ProjectButton(viewport, i18n,
							proj, skin, controller, CombinedAction.NAME,
							OpenGame.NAME,
							new Object[] { rootProjectJsonPath },
							ChangeView.NAME,
							new Object[] { ProjectScreen.NAME }));
				}
			}
		}
		this.projectsChanged = false;
		Gdx.app.log(PROJECTS, "Projects loaded successfully!");
	}

	@Override
	public void preferenceChanged(String preferenceName, Object newValue) {
		if (Preferences.RECENT_GAMES.equals(preferenceName)) {
			Gdx.app.log(
					PROJECTS,
					"Recent projects changed, project gallery will be reloaded on demand (next time needed)!");
			this.projectsChanged = true;
		}
	}

}