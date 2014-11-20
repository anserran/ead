package es.eucm.ead.editor.view.builders.scene;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import es.eucm.ead.editor.view.widgets.AbstractWidget;
import es.eucm.ead.editor.view.widgets.layouts.LinearLayout;

public class GroupContext extends AbstractWidget {

	private LinearLayout icons;

	private LinearLayout content;

	public GroupContext(Skin skin) {
		this(skin.get(GroupContextStyle.class));
	}

	public GroupContext(GroupContextStyle style) {
		addActor(icons = new LinearLayout(false, style.iconsBackground));
		addActor(content = new LinearLayout(false, style.background));
	}

	@Override
	public void layout() {
		float width = getPrefWidth(icons);
		setBounds(icons, -width, 0, width, getHeight());
		setBounds(content, 0, 0, getWidth(), getHeight());
	}

	public static class GroupContextStyle {

		public Drawable iconsBackground;

		public Drawable background;
	}

}
