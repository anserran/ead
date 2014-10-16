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
package es.eucm.ead.editor.control.actions.model.scene.transform;

import es.eucm.ead.editor.control.commands.Command;
import es.eucm.ead.editor.control.commands.FieldCommand;
import es.eucm.ead.schema.entities.ModelEntity;
import es.eucm.ead.schemax.FieldName;

/**
 * <p>
 * Moves the current selection
 * </p>
 * <dl>
 * <dt><strong>Arguments</strong></dt>
 * <dd><strong>args[0]</strong> <em>{@link Type}</em> the direction of the movement</dd>
 * </dl>
 */
public class MoveSelection extends TransformSelection {

	private static final float AMOUNT = 20F;

	public enum Type {
		LEFT, RIGHT, UP, DOWN
	}

	public MoveSelection() {
		super(true, false, Type.class);
	}

	@Override
	public Command performOverModelEntity(ModelEntity modelEntity,
			Object... args) {
		Type type = (Type) args[0];
		float value;
		String fieldName = null;

		if (type == Type.UP) {

			fieldName = FieldName.Y;
			value = modelEntity.getY() + AMOUNT;

		} else if (type == Type.DOWN) {

			fieldName = FieldName.Y;
			value = modelEntity.getY() - AMOUNT;

		} else if (type == Type.LEFT) {

			fieldName = FieldName.X;
			value = modelEntity.getX() - AMOUNT;

		} else {

			fieldName = FieldName.X;
			value = modelEntity.getX() + AMOUNT;
		}

		return new FieldCommand(modelEntity, fieldName, value);

	}
}
