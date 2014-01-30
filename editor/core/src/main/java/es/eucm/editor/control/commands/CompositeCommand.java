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
package es.eucm.editor.control.commands;

import com.badlogic.gdx.Gdx;
import java.util.Arrays;
import java.util.List;

import es.eucm.ead.editor.control.commands.Command;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

import es.eucm.editor.model.EditorModel;
import es.eucm.editor.model.ModelEvent;

/**
 * Class that handles multiple commands in a list as a single one. Commands will
 * be executed in order of addition (first added first), and will be undone in
 * reverse order.
 */
public class CompositeCommand extends Command {
	protected String commandName;

	/**
	 * The list of Command objects to be treated as a single one
	 */
	private final List<Command> commandList;

	/**
	 * @param list
	 *            Command objects to be treated as a single one
	 */
	public CompositeCommand(List<Command> list) {
		this.commandList = list;
		commandName = generateName();
	}

	/**
	 * @param comms
	 *            array of Command objects to be treated as a single one
	 */
	public CompositeCommand(Command... comms) {
		this.commandList = Arrays.asList(comms);
		commandName = generateName();
	}

	private String generateName() {
		StringBuilder sb = new StringBuilder("Multiple[");
		for (Command c : commandList) {
			sb.append(c).append(' ');
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Performs this list of commands. Failure in any triggers an undo of
	 * previously-completed list-commands.
	 */
	@Override
	public ModelEvent doCommand(EditorModel em) {
		Stack<Command> done = new Stack<Command>();
		ModelEvent mmc = new ModelEvent(this);
		for (Command c : commandList) {
			ModelEvent me = c.doCommand(em);
			if (me != null) {
				done.push(c);
				mmc.merge(me);
			} else {
				// undo in reverse order
				while (!done.empty()) {
					done.pop().undoCommand(em);
				}
				return null;
			}
		}
		return mmc;
	}

	/**
	 * Returns true if all commands in the list can be undone
	 */
	@Override
	public boolean canUndo() {
		for (Command c : commandList) {
			if (!c.canUndo()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Undoes all commands in this list. Failure to undo any triggers a redo of
	 * previously-completed undos.
	 */
	@Override
	public ModelEvent undoCommand(EditorModel em) {
		Stack<Command> undone = new Stack<Command>();
		// use reverse order (last-to-first)
		ModelEvent mmc = new ModelEvent(this);
		for (Command c : new ListReverser<Command>(commandList)) {
			ModelEvent me = c.undoCommand(em);
			if (me != null) {
				undone.push(c);
				mmc.merge(me);
			} else {
				Gdx.app.error("CompositeCommand", "error undoing " + c);
				// redo in reverse order
				while (!undone.empty()) {
					undone.pop().undoCommand(em);
				}
				return null;
			}
		}
		return mmc;
	}

	/**
	 * Returns true if all commands in the list can be redone
	 */
	@Override
	public boolean canRedo() {
		for (Command c : commandList) {
			if (!c.canRedo()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Repeats all commands in this list. Failure to undo any triggers a undo of
	 * previously-completed redos.
	 */
	@Override
	public ModelEvent redoCommand(EditorModel em) {
		ArrayList<Command> redone = new ArrayList<Command>();
		ModelEvent mmc = new ModelEvent(this);
		for (Command c : commandList) {
			ModelEvent me = c.redoCommand(em);
			if (me != null) {
				mmc.merge(me);
			} else {
				Gdx.app.error("CompositeCommand", "error redoing " + c);
				for (Command good : redone) {
					good.redoCommand(em);
				}
				return null;
			}
		}
		return mmc;
	}

	/**
	 * Combines this command with another. Essentially a NOP, as we do not want
	 * to combine command-lists
	 */
	@Override
	public boolean combine(Command other) {
		return false;
	}

	private static class ListReverser<T> implements Iterable<T> {
		private ListIterator<T> listIterator;

		public ListReverser(List<T> wrappedList) {
			this.listIterator = wrappedList.listIterator(wrappedList.size());
		}

		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>() {
				@Override
				public boolean hasNext() {
					return listIterator.hasPrevious();
				}

				@Override
				public T next() {
					return listIterator.previous();
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException("Not supported");
				}
			};
		}
	}
}