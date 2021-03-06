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
package es.eucm.ead.editor.debugconfig.devices;

/**
 * Specs from: <a href=
 * "https://support.google.com/nexus/answer/3247662?hl=en&ref_topic=2841129">
 * https://support.google.com/nexus/answer/3247662?hl=en&ref_topic=2841129 </a>
 * Created by jtorrente on 1/03/15.
 */
public class Nexus6 implements DeviceSpecs {
	@Override
	public int pixelDensity() {
		return 493;
	}

	@Override
	public int screenWidth() {
		return 2560;
	}

	@Override
	public int screenHeight() {
		return 1440;
	}

	@Override
	public String name() {
		return "Nexus 6, 6\" smartphone, 16:9 screen";
	}
}
