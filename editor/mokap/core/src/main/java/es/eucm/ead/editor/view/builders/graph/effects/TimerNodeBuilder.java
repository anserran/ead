package es.eucm.ead.editor.view.builders.graph.effects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import es.eucm.commander.Commander;
import es.eucm.ead.editor.view.SkinConstants;
import es.eucm.ead.editor.view.widgets.WidgetBuilder;
import es.eucm.ead.engine.I18N;
import es.eucm.ead.schema.components.behaviors.events.Timer;
import es.eucm.graph.model.Node;

public class TimerNodeBuilder extends EffectNodeBuilder<Timer> {

	public TimerNodeBuilder(Commander commander, Skin skin, I18N i18N) {
		super(commander, skin, i18N);
	}

	@Override
	public EffectModal<Timer> buildEditor() {
		return new TimerModal(this, commander, skin, i18N);
	}

	@Override
	public Drawable getIcon() {
		return skin.getDrawable(SkinConstants.IC_TIME);
	}

	@Override
	public Node newNode() {
		Node node = new Node();
		node.setContent(new Timer());
		return node;
	}

	@Override
	public boolean canAdd() {
		return false;
	}

	@Override
	public Actor buildNodeRepresentation(Node node) {
		Timer timer = (Timer) node.getContent();
		HorizontalGroup group = new HorizontalGroup();
		group.space(WidgetBuilder.dpToPixels(8));
		group.addActor(WidgetBuilder.icon(SkinConstants.IC_TIME,
				SkinConstants.STYLE_GRAY));
		group.addActor(new Label(i18N.m(timer.getRepeat(),
				"event.timer.repeat1", "event.timer.repeatN", timer.getTime(),
				timer.getRepeat()), skin));
		return group;
	}
}
