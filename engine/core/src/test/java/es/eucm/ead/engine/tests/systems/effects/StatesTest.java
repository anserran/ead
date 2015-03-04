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
package es.eucm.ead.engine.tests.systems.effects;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import es.eucm.ead.engine.EngineTest;
import es.eucm.ead.engine.GameLoop;
import es.eucm.ead.engine.components.renderers.RendererComponent;
import es.eucm.ead.engine.components.renderers.StatesActor;
import es.eucm.ead.engine.entities.EngineEntity;
import es.eucm.ead.engine.entities.actors.RendererActor;
import es.eucm.ead.engine.processors.ComponentProcessor;
import es.eucm.ead.engine.processors.renderers.StatesProcessor;
import es.eucm.ead.engine.systems.EffectsSystem;
import es.eucm.ead.engine.systems.effects.ChangeStateExecutor;
import es.eucm.ead.schema.effects.ChangeState;
import es.eucm.ead.schema.entities.ModelEntity;
import es.eucm.ead.schema.renderers.Renderer;
import es.eucm.ead.schema.renderers.State;
import es.eucm.ead.schema.renderers.States;
import es.eucm.ead.schemax.Layer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests {@link ChangeState} and
 * {@link es.eucm.ead.engine.components.renderers.StatesActor} in general
 * Created by jtorrente on 16/01/15.
 */
public class StatesTest extends EngineTest implements EntityListener {

	private ChangeStateExecutor changeStateExecutor;

	@Before
	public void setup() {
		componentLoader.registerComponentProcessor(States.class,
				new StatesProcessor(gameLoop, gameAssets, componentLoader));
		componentLoader.registerComponentProcessor(MockRenderer.class,
				new MockRendererProcessor(gameLoop));
		EffectsSystem effectsSystem = new EffectsSystem(gameLoop,
				variablesManager, gameAssets);
		changeStateExecutor = new ChangeStateExecutor();
		effectsSystem.registerEffectExecutor(ChangeState.class,
				changeStateExecutor);
		gameLoop.addSystem(effectsSystem);
		gameLoop.addEntityListener(this);
	}

	@Test
	public void testZeroStatesEntity() {
		StatesActor sc = buildEntityWithStates(0);
		assertEquals(0, sc.getHeight(), 0.001F);
		assertEquals(0, sc.getWidth(), 0.001F);
		assertNull(sc.getCollider());
		sc.changeState(null);
		sc.changeState("tag1");
	}

	@Test
	public void testDefault() {
		StatesActor sc = buildEntityWithStates(2,
				new String[] { "", "default" });
		assertEquals(200, sc.getHeight(), 0.001F);
		assertEquals(2, sc.getWidth(), 0.001F);
	}

	@Test
	public void testEffect() {
		StatesActor sc = buildEntityWithStates(2,
				new String[] { "", "default" });
		ChangeState effect = new ChangeState();
		effect.setStateTag("tag1");
		changeStateExecutor.execute(
				gameLoop.getEntitiesFor(
						Family.all(RendererComponent.class).get()).iterator()
						.next(), effect);
		stage.draw();
		assertEquals(100, sc.getHeight(), 0.001F);
	}

	private StatesActor buildEntityWithStates(int nStates) {
		return buildEntityWithStates(nStates, new String[nStates]);
	}

	private StatesActor buildEntityWithStates(int nStates,
			String[] additionalStates) {
		ModelEntity modelEntity = new ModelEntity();
		States states = new States();
		modelEntity.getComponents().add(states);
		for (int i = 1; i <= nStates; i++) {
			State state = new State();
			MockRenderer mockRenderer = new MockRenderer();
			mockRenderer.value = i;
			state.setRenderer(mockRenderer);
			state.setRenderer(mockRenderer);
			state.getStates().add("tag" + i);
			state.getStates().add(additionalStates[i - 1]);
			states.getStates().add(state);
		}
		EngineEntity entity = entitiesLoader.toEngineEntity(modelEntity);
		RendererComponent sc = entity.getComponent(RendererComponent.class);
		gameLoop.addEntity(entity);
		gameLoop.update(0);
		gameView.addEntityToLayer(Layer.SCENE, entity);
		stage.draw();
		return (StatesActor) sc.getRendererActor().getChildren().get(0);
	}

	@Override
	public void entityAdded(Entity entity) {

	}

	@Override
	public void entityRemoved(Entity entity) {

	}

	private static class MockRendererProcessor extends
			ComponentProcessor<MockRenderer> {

		public MockRendererProcessor(GameLoop gameLoop) {
			super(gameLoop);
		}

		@Override
		public Component getComponent(MockRenderer component) {
			MockRendererActor mockRendererComponent = new MockRendererActor();
			mockRendererComponent.value = component.value;
			RendererComponent rendererComponent = new RendererComponent();
			rendererComponent.addRenderer(mockRendererComponent);
			return rendererComponent;
		}
	}

	private static class MockRenderer extends Renderer {
		public int value;
	}

	private static class MockRendererActor extends RendererActor {

		public int value;

		@Override
		public float getPrefWidth() {
			return value;
		}

		@Override
		public float getPrefHeight() {
			return value * 100;
		}

		@Override
		public Array<Polygon> getCollider() {
			Array<Polygon> collider = new Array<Polygon>();
			Polygon newPolygon = new Polygon();
			newPolygon.setPosition(value, value);
			collider.add(newPolygon);
			return collider;
		}
	}
}
