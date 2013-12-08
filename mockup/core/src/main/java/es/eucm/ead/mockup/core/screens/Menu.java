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
/***************************************************************************\
 *  @author Antonio Calvo Morata & Dan Cristian Rotaru						*
 *  																		*
 *  ************************************************************************\
 * 	This file is a prototype for eAdventure Mockup							*
 *  																		*
 *  ************************************************************************/

package es.eucm.ead.mockup.core.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import es.eucm.ead.core.EAdEngine;
import es.eucm.ead.mockup.core.MockupEngine;
import es.eucm.ead.mockup.core.facade.IAnswerListener;
import es.eucm.ead.mockup.core.scene.MockupSceneManager;

public class Menu extends BaseScreen implements IAnswerListener {

	public static TextureRegion title;

	private boolean close;

	private TextButton btnVistaPrevia;

	private TextButton btnGaleria;

	private TextButton btnVerVideo;

	private TextButton btnGrabarVideo;

	private TextButton addSceneElement;

	private TextButton readGame;

	private MockupSceneManager msmTest;

	@Override
	public void create() {
		TextureAtlas ta = am.get(atlas_src, TextureAtlas.class);
		title = ta.findRegion("name");
		setUpRoot();

		MiTransitionListener transitionListener = new MiTransitionListener();
		readGame = new TextButton("ReadGame", skin);
		readGame.addListener(transitionListener);

		btnVistaPrevia = new TextButton("Vista Previa", skin);
		btnVistaPrevia.addListener(transitionListener);

		btnGaleria = new TextButton("Galer�a", skin);
		btnGaleria.addListener(transitionListener);

		btnVerVideo = new TextButton("Ver v�deo", skin);
		btnVerVideo.addListener(transitionListener);

		btnGrabarVideo = new TextButton("Grabar v�deo", skin);
		btnGrabarVideo.addListener(transitionListener);

		addSceneElement = new TextButton("AddSceneElement", skin);
		addSceneElement.addListener(transitionListener);

		root.add(btnGrabarVideo).left();
		root.add(btnVerVideo).colspan(2).right();
		root.row();
		root.add(readGame).expand().colspan(3);
		root.row();
		root.add(btnVistaPrevia).left();
		root.add(btnGaleria);
		root.add(addSceneElement).right();
		root.debug();

		msmTest = ((MockupSceneManager) MockupEngine.sceneManager);

	}

	private class MiTransitionListener extends ClickListener {

		@Override
		public void clicked(InputEvent event, float x, float y) {
			final BaseScreen next = getNextScreen(event.getListenerActor());
			if (next == null)
				return;

			stage.addAction(sequence(moveTo(-stage.getWidth(), 0, .25f),
					run(new Runnable() {
						@Override
						public void run() {
							mockup.setScreen(next);
						}
					})));
		}

		private BaseScreen getNextScreen(Actor target) {
			BaseScreen next = null;
			if (target == readGame) {
				msmTest.readGame();//next = game.cameraScreen;
			} else if (target == addSceneElement) {
				msmTest.addSceneElement();//next = game.scenes;				
			} /*else if(target == btnGaleria){
				next = game.gallery;				
				} else if(target == btnGrabarVideo){
				next = game.video;				
				} else if(target == btnVerVideo){
				next = game.playingscreen;				
				} else if(target == btnVistaPrevia){
				next = game.view;				
				}*/
			return next;
		}
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(inputMultiplexer);
		close = false;
		root.setVisible(true);
		stage.addAction(sequence(moveTo(-stage.getWidth(), 0), moveTo(0, 0,
				.25f)));
	}

	@Override
	public void render(float delta) {
		clearColor();
		//stage.act(delta);
		EAdEngine.engine.render();
		stage.act(delta);

	}

	@Override
	public void onHidden() {
		root.setVisible(false);
	}

	public void draw() {
		sb.begin();
		sb.draw(title, halfscreenw / 2f, screenh - 200, halfscreenw, 100);
		sb.end();
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK || keycode == Keys.BACKSPACE) {
			if (!close) {
				close = true;
				resolver.showDecisionBox(IAnswerListener.QUESTION_EXIT,
						"�Salir?", "�Est�s seguro?", "S�", "No", this);
			}
		}
		return true;
	}

	@Override
	public void onReceiveAnswer(int question, int answer) {
		if (question == IAnswerListener.QUESTION_EXIT) {
			if (close) {
				if (answer == IAnswerListener.QUESTION_EXIT_ANSWER_YES) {
					Gdx.app.exit();
				} else {
					close = false;
				}
			}
		}
	}
}
