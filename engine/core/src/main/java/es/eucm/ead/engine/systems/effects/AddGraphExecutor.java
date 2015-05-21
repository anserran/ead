package es.eucm.ead.engine.systems.effects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pools;

import es.eucm.ead.engine.components.GraphComponent;
import es.eucm.ead.schema.effects.AddGraph;

public class AddGraphExecutor extends EffectExecutor<AddGraph> {

	@Override
	public void execute(Entity target, AddGraph effect) {
		GraphComponent graphComponent = Pools.obtain(GraphComponent.class);
		graphComponent.add(effect.getGraph(),
				effect.getGraph().getNode(effect.getStart()));
        target.add(graphComponent);
	}
}
