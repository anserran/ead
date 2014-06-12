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

package es.eucm.ead.schema.components.behaviors.events;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import es.eucm.ead.schema.components.behaviors.Event;

/**
 * Defines a touch event.
 * 
 */
@Generated("org.jsonschema2pojo")
public class Touch extends Event {

	/**
	 * The type of touch interaction
	 * 
	 */
	private Touch.Type type = Touch.Type.fromValue("click");

	/**
	 * The type of touch interaction
	 * 
	 */
	public Touch.Type getType() {
		return type;
	}

	/**
	 * The type of touch interaction
	 * 
	 */
	public void setType(Touch.Type type) {
		this.type = type;
	}

	@Generated("org.jsonschema2pojo")
	public static enum Type {

		PRESS("press"), CLICK("click");
		private final String value;
		private static Map<String, Touch.Type> constants = new HashMap<String, Touch.Type>();

		static {
			for (Touch.Type c : Touch.Type.values()) {
				constants.put(c.value, c);
			}
		}

		private Type(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public static Touch.Type fromValue(String value) {
			Touch.Type constant = constants.get(value);
			if (constant == null) {
				throw new IllegalArgumentException(value);
			} else {
				return constant;
			}
		}

	}

}
