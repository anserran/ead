package es.eucm.ead.editor.view.builders.graph.effects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import es.eucm.commander.Commander;
import es.eucm.ead.engine.I18N;
import es.eucm.ead.schema.components.behaviors.events.Timer;
import es.eucm.graph.core.NodeBuilder;

public class TimerModal extends EffectModal<Timer> {

	public TimerModal(NodeBuilder<Timer> nodeBuilder, Commander commander,
			Skin skin, I18N i18N) {
		super(nodeBuilder, commander, skin, i18N);
	}

	@Override
	protected Actor buildEditor(Skin skin, I18N i18N) {
		return new Actor();
	}

	@Override
	protected void updateEditor(Timer effect) {

	}
}
