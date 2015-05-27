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
package es.eucm.ead.editor.editorui.widgets;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import es.eucm.ead.editor.editorui.UITest;
import es.eucm.ead.editor.view.SkinConstants;
import es.eucm.ead.editor.view.widgets.CirclesMenu;
import es.eucm.ead.editor.view.widgets.IconButton;
import es.eucm.ead.editor.view.widgets.WidgetBuilder;
import es.eucm.ead.editor.view.widgets.layouts.LinearLayout;
import es.eucm.i18n.I18N;

public class CirclesMenuTest extends UITest {

	@Override
	protected Actor buildUI(Skin skin, I18N i18n) {

		final CirclesMenu menu = WidgetBuilder.circlesMenu(Align.left,
				new String[] { SkinConstants.IC_ADD,
						SkinConstants.IC_BRING_TO_FRONT,
						SkinConstants.IC_CAMERA, SkinConstants.IC_PLAY },
				new Class[] { null, null, null, null }, new Object[][] { null,
						null, null, null });

		LinearLayout container = new LinearLayout(false).background(skin
				.getDrawable(SkinConstants.DRAWABLE_BLACK_BG));
		IconButton iconButton = WidgetBuilder.toolbarIcon(SkinConstants.IC_ADD,
				null);
		container.add(iconButton);
		container.addSpace();

		iconButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controller.getViews().showModal(menu, 0, 0);
			}
		});

		return container;
	}

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 360;
		new LwjglApplication(new CirclesMenuTest(), config);
	}
}
