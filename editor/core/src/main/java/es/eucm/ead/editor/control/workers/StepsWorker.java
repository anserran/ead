package es.eucm.ead.editor.control.workers;

import es.eucm.ead.engine.assets.Assets;

/**
 * Steps worker, to help divide long process in the OpenGL thread. If assets is
 * passed as argument, the first step is not invoked until all assets are loaded
 */
public class StepsWorker extends Worker {

	private Assets assets;

	private int totalSteps;

	private int step;

	public StepsWorker() {
		super(true, false);
	}

	@Override
	protected void prepare() {
		totalSteps = getArg(0);
		if (args.length == 2) {
			assets = getArg(1);
		}
	}

	@Override
	protected boolean step() {
		if (assets == null || assets.isDoneLoading()) {
			result(step++);
			return step >= totalSteps;
		}
		return false;
	}
}
