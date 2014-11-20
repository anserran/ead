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
package es.eucm.ead.editor.view.widgets.baseview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import es.eucm.ead.editor.view.widgets.AbstractWidget;

/**
 * Base container view for all Mokap interactions. It contains the following
 * layers (from top to bottom):
 * <ol>
 * <li><strong>Modals (all screen)</strong>: to show modal widgets (e.g.:
 * contextual/selection menus)</li>
 * <li><strong>Navigation</strong> (left): widget to navigate between different
 * views in the application</li>
 * <li><strong>Toolbar (top)</strong>: contains all buttons to perform
 * operations in the current context</li>
 * <li><strong>Selection context (right)</strong>: to show contextual
 * information of the current selection</li>
 * </ol>
 */
public class BaseView extends AbstractWidget {

	public static final float FLING_AREA_CM = 1.0f;

	public static final float FLING_MIN_VELOCITY_CM = 5.0f;

	public static final float HIDE_TIME = 0.5f;

	private static final int NAVIGATION_AREA = 0, SELECTION_CONTEXT_AREA = 1;

	private Actor toolbar;

	private Navigation navigation;

	protected SelectionContext selectionContext;

	private Actor content;

	public BaseView(Skin skin) {
		this(skin.get(BaseViewStyle.class));
	}

	public BaseView(BaseViewStyle style) {
		addActor(selectionContext = new SelectionContext());

		addActor(navigation = new Navigation(style));

		// Listens for fling gestures to quickly show hidden panels
		addListener(new ActorGestureListener() {

			private int area;

			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				area = event.isStopped() || pointer != 0 ? -1 : area(x, y);
				super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void fling(InputEvent event, float velocityX,
					float velocityY, int button) {
				if (!event.isStopped()) {
					switch (area) {
					case NAVIGATION_AREA:
						if (velocityX > cmToXPixels(FLING_MIN_VELOCITY_CM)) {
							navigation.show();
							event.stop();
						}
						break;
					case SELECTION_CONTEXT_AREA:
						if (velocityX < cmToXPixels(FLING_MIN_VELOCITY_CM)) {
							selectionContext.show();
							event.stop();
						}
						break;
					case -1:
						if (velocityX > cmToXPixels(FLING_MIN_VELOCITY_CM)) {
							selectionContext.hide();
							event.stop();
						}
						break;
					}
				}
			}
		});

		// Listens for drag to slowly show hidden panels
		addListener(new DragListener() {

			private int area;

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				area = area(x, y);
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				if (!event.isStopped()) {
					switch (area) {
					case NAVIGATION_AREA:
						navigation.displace(event, getDeltaX(), 0);
						break;
					case SELECTION_CONTEXT_AREA:
						selectionContext.displace(event, getDeltaX(), 0);
						break;
					}
				}
			}

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				if (!event.isStopped()) {
					switch (area) {
					case NAVIGATION_AREA:
						navigation.dragStop();
						break;
					case SELECTION_CONTEXT_AREA:
						selectionContext.dragStop();
						break;
					}
				}
			}
		});
	}

	private int area(float x, float y) {
		if (navigation.hasContent() && x < cmToXPixels(FLING_AREA_CM)) {
			return NAVIGATION_AREA;
		} else if (selectionContext.hasContent()
				&& x > Gdx.graphics.getWidth() - cmToXPixels(FLING_AREA_CM)) {
			return SELECTION_CONTEXT_AREA;
		} else {
			return -1;
		}
	}

	/**
	 * Sets the navigation actor, removing the current one (if any)
	 */
	public void setNavigation(Actor navigation) {
		this.navigation.setNavigation(navigation);
	}

	/**
	 * Toggles navigation panel
	 */
	public void toggleNavigation() {
		this.navigation.toggle();
	}

	public boolean isNavigationVisible() {
		return !navigation.isHidden();
	}

	public void hideNavigationRightAway() {
		navigation.hideRightAway();
	}

	public void exitFullscreen() {
		toolbar.clearActions();
		toolbar.addAction(Actions.sequence(
				Actions.touchable(Touchable.enabled), Actions.moveTo(0,
						getHeight() - toolbar.getHeight(), 0.25f,
						Interpolation.exp5Out)));
	}

	public void enterFullScreen() {
		toolbar.clearActions();
		toolbar.addAction(Actions.sequence(
				Actions.touchable(Touchable.disabled),
				Actions.moveTo(0, getHeight(), 0.25f, Interpolation.exp5Out)));
	}

	/**
	 * Sets the toolbar actor, removing the current one (if any)
	 */
	public void setToolbar(Actor toolbar) {
		if (this.toolbar != null) {
			this.toolbar.remove();
		}

		this.toolbar = toolbar;
		if (toolbar != null) {
			addActorBefore(navigation, toolbar);
			layoutToolbar();
		}
	}

	public void setContent(Actor content) {
		if (this.content != null) {
			this.content.remove();
		}

		this.content = content;

		if (content != null) {
			addActorAt(0, content);
		}
	}

	/**
	 * Sets the selection context, removing the current one (if any)
	 */
	public void setSelectionContext(Actor selectionContext) {
		this.selectionContext.setSelectionContext(selectionContext);
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (navigation != null) {
			navigation.invalidate();
		}
	}

	@Override
	public void layout() {
		setBounds(navigation, 0, 0, getWidth(), getHeight());
		layoutToolbar();
		layoutSelectionContext();
		if (content != null) {
			setBounds(content, 0, 0, getWidth(), getHeight());
		}
	}

	protected void layoutSelectionContext() {
		setBounds(selectionContext, 0, 0, getWidth(), getHeight()
				- (toolbar == null ? 0 : getPrefHeight(toolbar)));
	}

	private void layoutToolbar() {
		if (toolbar != null
				&& !MathUtils.isEqual(toolbar.getWidth(), getWidth(), 0.1f)) {
			setBounds(toolbar, 0, getHeight() - getPrefHeight(toolbar),
					getWidth(), getPrefHeight(toolbar));
		}
	}

	/**
	 * Moves with the default time and interpolation the given actor to the
	 * given coordinates
	 */
	public static void moveTo(Actor actor, float targetX, float targetY) {
		actor.clearActions();
		actor.addAction(Actions.moveBy(targetX - actor.getX(),
				targetY - actor.getY(), HIDE_TIME, Interpolation.exp5Out));
	}

	public static class BaseViewStyle {

		public Drawable navigationBackground;

		/**
		 * Max value for the navigation background alpha
		 */
		public float navigationBackgroundAlpha = 0.5f;

		public BaseViewStyle() {
		}

		public BaseViewStyle(Drawable navigationBackground) {
			this.navigationBackground = navigationBackground;
		}
	}

}
