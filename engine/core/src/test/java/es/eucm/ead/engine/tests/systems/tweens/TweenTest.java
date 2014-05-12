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
package es.eucm.ead.engine.tests.systems.tweens;

import org.junit.Before;

import es.eucm.ead.engine.GameLoop;
import es.eucm.ead.engine.entities.ActorEntity;
import es.eucm.ead.engine.processors.tweens.TweensProcessor;
import es.eucm.ead.engine.systems.tweens.TweenSystem;
import es.eucm.ead.engine.systems.tweens.tweencreators.TweenCreator;
import es.eucm.ead.schema.components.tweens.Tween;
import es.eucm.ead.schema.components.tweens.Tweens;

/**
 * Base class to create tests for tweens
 */
public abstract class TweenTest {

	protected GameLoop gameLoop;

	protected TweenSystem tweenSystem;

	private TweensProcessor tweensProcessor;

	@Before
	public void setUp() {
		gameLoop = new GameLoop();
		tweenSystem = new TweenSystem();
		gameLoop.addSystem(tweenSystem);
		tweenSystem
				.registerBaseTweenCreator(getTweenClass(), getTweenCreator());
		tweensProcessor = new TweensProcessor(gameLoop);
	}

	/**
	 * @return class of the ween. Used to register the tween creator
	 */
	public abstract Class getTweenClass();

	/**
	 * @return tween creator to be registered associated with the class returned
	 *         in {@link #getTweenClass()}
	 */
	public abstract TweenCreator getTweenCreator();

	/**
	 * Creates an entity with the given tween and adds it to the gameloop
	 */
	protected ActorEntity addEntityWithTweens(Tween tween) {
		Tweens tweens = new Tweens();
		tweens.getTweens().add(tween);
		return addEntityWithTweens(tweens);
	}

	/**
	 * Creates an entity with the given tween and adds it to the gameloop
	 */
	protected ActorEntity addEntityWithTweens(Tweens tweens) {
		ActorEntity entity = gameLoop.createEntity();
		entity.add(tweensProcessor.getComponent(tweens));
		gameLoop.addEntity(entity);
		return entity;
	}
}
