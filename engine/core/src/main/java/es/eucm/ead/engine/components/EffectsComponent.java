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
package es.eucm.ead.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import es.eucm.ead.engine.components.behaviors.BehaviorComponent;
import es.eucm.ead.schema.components.behaviors.Event;
import es.eucm.ead.schema.effects.Effect;

public class EffectsComponent extends BehaviorComponent implements Poolable {

	private Array<Effect> effectList;

	public EffectsComponent() {
		effectList = new Array<Effect>();
	}

	public Array<Effect> getEffectList() {
		return effectList;
	}

	@Override
	public void reset() {
		effectList.clear();
	}

	public void addEffect(Effect effect) {
		effectList.add(effect);
	}

	@Override
	public void addBehavior(Event event, Iterable effects) {
		for (Object o : effects) {
			effectList.add((Effect) o);
		}
	}

	@Override
	public boolean combine(Component component) {
		if (component.getClass() == getClass()) {
			EffectsComponent other = (EffectsComponent) component;
			effectList.addAll(other.effectList);
			return true;
		}
		return false;
	}

	@Override
	public Class getRuntimeBehaviorClass() {
		return null;
	}
}
