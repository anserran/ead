package es.eucm.ead.editor.control.background;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Array;
import es.eucm.ead.editor.assets.ApplicationAssets;
import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.actions.editor.OpenGame;
import es.eucm.ead.editor.model.Q;
import es.eucm.ead.editor.utils.ProjectUtils;
import es.eucm.ead.editor.view.SkinConstants;
import es.eucm.ead.editor.view.widgets.WidgetBuilder;
import es.eucm.ead.engine.assets.Assets.AssetLoadedCallback;
import es.eucm.ead.schema.editor.components.GameData;
import es.eucm.ead.schema.editor.components.Thumbnail;
import es.eucm.ead.schema.entities.ModelEntity;
import es.eucm.ead.schemax.GameStructure;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by angel on 13/11/14.
 */
public class LoadProjects extends BackgroundTask<Void> implements
		AssetLoadedCallback<Texture> {

	private Controller controller;

	private float totalProjects;

	private float projectsLoaded;

	private Table container;

	private ConcurrentHashMap<String, String> thumbnailsToProjects = new ConcurrentHashMap<String, String>();

	public LoadProjects(Controller controller, Table container) {
		this.controller = controller;
		this.container = container;
	}

	@Override
	public Void call() throws Exception {
		thumbnailsToProjects.clear();
		ApplicationAssets assets = controller.getApplicationAssets();
		FileHandle projectsFolder = assets.absolute(controller.getPlatform()
				.getDefaultProjectsFolder());
		if (projectsFolder.exists()) {
			Array<String> projectPaths = ProjectUtils
					.findProjects(projectsFolder);
			setProjectsToLoad(projectPaths.size);
			for (String projectPath : projectPaths) {
				String thumbnailPath = findThumbnail(projectPath);
				if (thumbnailPath != null) {
					thumbnailsToProjects.put(thumbnailPath, projectPath);
					controller.getApplicationAssets().get(thumbnailPath,
							Texture.class, this);
				}
				projectLoaded();
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
				return controller
						.getApplicationAssets()
						.absolute(path)
						.child(Q.getComponent(scene, Thumbnail.class).getPath())
						.path();
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

	@Override
	public void loaded(String fileName, Texture asset) {
		String project = thumbnailsToProjects.remove(fileName);
		ImageButton sceneButton = WidgetBuilder.imageButton(controller
				.getApplicationAssets().getSkin(), SkinConstants.STYLE_SCENE,
				OpenGame.class, project);
		sceneButton.setStyle(new ImageButtonStyle(sceneButton.getStyle()));

		if (container.getChildren().size % 3 == 0) {
			container.row().fillX();
		}
		container.add(sceneButton).width(Value.percentWidth(0.33f, container));
	}
}
