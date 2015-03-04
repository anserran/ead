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
package es.eucm.ead.engine.processors.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import es.eucm.ead.engine.EntitiesLoader;
import es.eucm.ead.engine.GameLoop;
import es.eucm.ead.engine.assets.Assets.AssetLoadedCallback;
import es.eucm.ead.engine.assets.GameAssets;
import es.eucm.ead.engine.components.renderers.RendererComponent;
import es.eucm.ead.engine.processors.ComponentProcessor;
import es.eucm.ead.schema.components.Reference;
import es.eucm.ead.schema.entities.ModelEntity;
import es.eucm.ead.schemax.ModelStructure;

public class ReferenceProcessor extends ComponentProcessor<Reference> {

	private EntitiesLoader loader;
	private GameAssets assets;

	public ReferenceProcessor(GameLoop engine, GameAssets assets,
			EntitiesLoader loader) {
		super(engine);
		this.loader = loader;
		this.assets = assets;
	}

	@Override
	public RendererComponent getComponent(Reference reference) {

		String id = getLibraryPath() + reference.getFolder()
				+ reference.getEntity();
		RendererComponent referenceComponent = gameLoop
				.createComponent(RendererComponent.class);

		assets.get(id, Object.class, new ReferenceLoadedCallback(reference,
				referenceComponent));
		return referenceComponent;
	}

	private void set(Reference reference, RendererComponent component,
			ModelEntity entity) {
		String referenceLoadingPath = reference.getFolder()
				+ ModelStructure.CONTENTS_FOLDER;
		assets.setReferencePath(getLibraryPath() + referenceLoadingPath);
		Group group = loader.toEngineEntity(entity).getGroup();
		assets.setReferencePath(null);
		component.addRenderer(group);
	}

	protected String getLibraryPath() {
		return ModelStructure.LIBRARY_FOLDER;
	}

	private class ReferenceLoadedCallback implements
			AssetLoadedCallback<Object> {

		private Reference reference;

		private RendererComponent component;

		public ReferenceLoadedCallback(Reference reference,
				RendererComponent component) {
			this.reference = reference;
			this.component = component;
		}

		@Override
		public void loaded(String fileName, Object asset) {
			set(reference, component, (ModelEntity) asset);
		}

		@Override
		public void error(String fileName, Class type, Throwable exception) {
			Gdx.app.error("ReferenceProcessor", "Error loading reference "
					+ exception);
		}
	}
}
