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
package es.eucm.ead.engine.processors.renderers;

import es.eucm.ead.engine.ComponentLoader;
import es.eucm.ead.engine.GameLoop;
import es.eucm.ead.engine.assets.GameAssets;
import es.eucm.ead.engine.components.renderers.RendererComponent;
import es.eucm.ead.engine.components.renderers.frames.FramesActor;
import es.eucm.ead.engine.components.renderers.frames.sequences.LastFrameSequence;
import es.eucm.ead.engine.components.renderers.frames.sequences.LinearSequence;
import es.eucm.ead.engine.components.renderers.frames.sequences.RandomSequence;
import es.eucm.ead.engine.components.renderers.frames.sequences.YoyoSequence;
import es.eucm.ead.schema.renderers.Frame;
import es.eucm.ead.schema.renderers.Frames;

public class FramesProcessor extends RendererProcessor<Frames> {

	private ComponentLoader componentLoader;

	private LinearSequence linearSequence = new LinearSequence();

	private RandomSequence randomSequence = new RandomSequence();

	private LastFrameSequence lastFrameSequence = new LastFrameSequence();

	public FramesProcessor(GameLoop engine, GameAssets gameAssets,
			ComponentLoader componentLoader) {
		super(engine, gameAssets);
		this.componentLoader = componentLoader;
	}

	@Override
	public RendererComponent getComponent(Frames component) {
		RendererComponent rendererComponent = gameLoop
				.createComponent(RendererComponent.class);
		FramesActor frames = createActor();
		for (Frame f : component.getFrames()) {
			RendererComponent renderer = (RendererComponent) componentLoader
					.toEngineComponent(f.getRenderer());
			frames.addFrame(renderer.getRendererActor(), f.getTime());
			gameLoop.freeComponent(renderer);
		}
		switch (component.getSequence()) {
		case LINEAR:
			frames.setSequence(linearSequence);
			break;
		case RANDOM:
			frames.setSequence(randomSequence);
			break;
		case YOYO:
			// Yoyo sequences cannot be shared since they hold state
			frames.setSequence(new YoyoSequence());
			break;
		case LASTFRAME:
			frames.setSequence(lastFrameSequence);
			break;
		}
		rendererComponent.addRenderer(frames);
		return rendererComponent;
	}

	protected FramesActor createActor() {
		return new FramesActor();
	}
}
