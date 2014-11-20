package es.eucm.ead.editor.view.widgets.selectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.view.SkinConstants;
import es.eucm.ead.editor.view.widgets.MultiWidget;
import es.eucm.ead.editor.view.widgets.WidgetBuilder;
import es.eucm.ead.editor.view.widgets.galleries.ScenesGallery;
import es.eucm.ead.editor.view.widgets.layouts.Gallery.Cell;
import es.eucm.ead.editor.view.widgets.layouts.LinearLayout;
import es.eucm.ead.engine.I18N;

public class SceneSelector extends LinearLayout implements Selector<String> {

	private SelectorListener<String> selectorListener;

	public SceneSelector(Controller controller) {
		super(false);
		Skin skin = controller.getApplicationAssets().getSkin();
		I18N i18N = controller.getApplicationAssets().getI18N();

		MultiWidget toolbar = new MultiWidget(skin);
		LinearLayout buttons = new LinearLayout(true);
		toolbar.addWidgets(buttons);

		Actor cancel = WidgetBuilder.toolbarIcon(SkinConstants.IC_GO,
				i18N.m("cancel"));
		buttons.add(cancel);
		cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				selectorListener.cancelled();
			}
		});

		buttons.add(new Label(i18N.m("select.scene"), skin,
				SkinConstants.STYLE_TOOLBAR));
		buttons.addSpace();

		add(toolbar).expandX();
		add(
				new SelectScenesGallery(Gdx.graphics.getHeight() / 2.15f, 3,
						controller)).expand(true, true);
	}

	@Override
	public void prepare(SelectorListener<String> selectorListener,
			Object... args) {
		this.selectorListener = selectorListener;
		if (args.length > 0 && args[0] != null) {
			Actor actor = findActor((String) args[0]);
			if (actor instanceof Cell) {
				((Cell) actor).setChecked(true);
			}
		}
	}

	public class SelectScenesGallery extends ScenesGallery {

		public SelectScenesGallery(float rowHeight, int columns,
				Controller controller) {
			super(rowHeight, columns, controller);
		}

		@Override
		public Cell addTile(String id, String title, String thumbnailPath) {
			Cell cell = super.addTile(id, title, thumbnailPath);
			cell.setName(id);
			return cell;
		}

		@Override
		protected void prepareAddButton(Actor actor) {
			actor.remove();
		}

		@Override
		protected void prepareGalleryItem(Actor actor, final String id) {
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					selectorListener.selected(id);
				}
			});
		}
	}
}
