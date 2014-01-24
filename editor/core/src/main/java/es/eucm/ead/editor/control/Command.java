/**
 * eAdventure is a research project of the
 *    e-UCM research group.
 *
 *    Copyright 2005-2013 e-UCM research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          C Profesor Jose Garcia Santesmases sn,
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
package es.eucm.ead.editor.control;

import es.eucm.ead.editor.model.EditorModel;
import es.eucm.ead.editor.model.ModelEvent;

/**
 * Actions define tasks that can be performed over the game model. This is part
 * of the "Action" pattern, used to allow the easy implementation of undo and
 * re-do mechanisms.
 */
public abstract class Command {

	/**
	 * Time stamp of when the action was created
	 */
	protected long timeStamp = System.currentTimeMillis();

	/**
	 * Get the time when the action was created
	 * 
	 * @return The time when the action was created
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Do the actual work. Returns a ChangeEvent if it could be performed, null
	 * in other case.
	 * 
	 * @return True if the action was performed correctly
	 */
	public abstract ModelEvent performCommand(EditorModel em);

	/**
	 * Returns true if the action can be undone
	 * 
	 * @return True if the action can be undone
	 */
	public abstract boolean canUndo();

	/**
	 * Undo the work done by the action. Returns true if it could be undone,
	 * false in other case.
	 * 
	 * @return a ChangeEvent if it could be performed, null in other case.
	 */
	public abstract ModelEvent undoCommand(EditorModel em);

	/**
	 * Returns true if the action can be redone
	 * 
	 * @return True if the action can be redone
	 */
	public abstract boolean canRedo();

	/**
	 * Re-do the work done by the action before it was undone.
	 * 
	 * @return a ChangeEvent if it could be performed, null in other case.
	 */
	public abstract ModelEvent redoCommand(EditorModel em);

	/**
	 * Combines this action with other similar action (if possible). Useful for
	 * combining simple changes such as characters typed in the same field.
	 * 
	 * @param other
	 *            The other action with which this action can be combined if
	 *            possible
	 * @return true if the actions were combined
	 */
	public abstract boolean combine(Command other);

}
