package es.eucm.ead.editor.control.background;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import es.eucm.ead.editor.assets.ApplicationAssets;
import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.model.Q;
import es.eucm.ead.editor.utils.ProjectUtils;
import es.eucm.ead.schema.editor.components.GameData;
import es.eucm.ead.schema.editor.components.Thumbnail;
import es.eucm.ead.schema.entities.ModelEntity;
import es.eucm.ead.schemax.GameStructure;

/**
 * Created by angel on 13/11/14.
 */
public class LoadProjects extends BackgroundTask<Void> {

	private Controller controller;

	private float totalProjects;

	private float projectsLoaded;

	@Override
	public Void call() throws Exception {
		ApplicationAssets assets = controller.getApplicationAssets();
		FileHandle projectsFolder = assets.absolute(controller.getPlatform()
				.getDefaultProjectsFolder());
		if (projectsFolder.exists()) {
			Array<String> projectPaths = ProjectUtils
					.findProjects(projectsFolder);
			setProjectsToLoad(projectPaths.size);
			for (String path : projectPaths) {
				String thumbnailPath = findThumbnail(path);
                if (thumbnailPath != null){
                    controller.getApplicationAssets().load
                }
			}
		} else {
			projectsFolder.mkdirs();
		}
		return null;
	}

	private void setProjectsToLoad(float projects) {
		totalProjects = projects;
		projectsLoaded = 0;
		setCompletionPercentage(0.0f);
	}

	private void projectLoaded() {
		projectsLoaded += 1.0f;
		setCompletionPercentage(projectsLoaded / totalProjects);
	}

	private String findThumbnail(String path) {
		ModelEntity game = findGame(path);
		if (game != null) {
			String scenePath = Q.getComponent(game, GameData.class)
					.getInitialScene();
			ModelEntity scene = findScene(path, scenePath);
			if (Q.hasComponent(scene, Thumbnail.class)) {
				return Q.getComponent(scene, Thumbnail.class).getPath();
			}
		}
		return null;
	}

	private ModelEntity findGame(String path) {
		FileHandle game = controller.getApplicationAssets().absolute(path)
				.child(GameStructure.GAME_FILE);
		if (game.exists()) {
			return controller.getEditorGameAssets().fromJson(ModelEntity.class,
					game);
		} else {
			return null;
		}
	}

	private ModelEntity findScene(String projectPath, String scenePath) {
		FileHandle scene = controller.getApplicationAssets()
				.absolute(projectPath).child(scenePath);
		if (scene.exists()) {
			return controller.getEditorGameAssets().fromJson(ModelEntity.class,
					scene);
		} else {
			return null;
		}
	}

}
