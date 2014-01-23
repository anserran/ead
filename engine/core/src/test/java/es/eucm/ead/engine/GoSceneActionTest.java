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
package es.eucm.ead.engine;

import com.badlogic.gdx.Gdx;
import es.eucm.ead.engine.mock.MockGame;
import es.eucm.ead.schema.actions.GoScene;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GoSceneActionTest {

	private static MockGame mockGame;

	@BeforeClass
	public static void setUp() {
		mockGame = new MockGame();
	}

	@Test
	public void testGoExistingScene() {

		assertEquals(Engine.sceneManager.getCurrentScenePath(),
				"scenes/scene1.json");

		GoScene goScene = new GoScene();
		goScene.setName("scene2");

		mockGame.addActionToDummyActor(goScene);
		mockGame.act();

		assertEquals(Engine.sceneManager.getCurrentScenePath(),
				"scenes/scene2.json");
	}

	@Test
	public void testGoUnexistingScene() {
		String currentScene = Engine.sceneManager.getCurrentScenePath();

		GoScene goScene = new GoScene();
		goScene.setName("ñor");

		mockGame.addActionToDummyActor(goScene);
		mockGame.act();

		assertEquals(Engine.sceneManager.getCurrentScenePath(), currentScene);
	}

	@AfterClass
	public static void tearDown() {
		Gdx.app.exit();
	}

}