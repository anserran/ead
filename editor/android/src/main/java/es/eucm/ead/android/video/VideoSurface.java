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
package es.eucm.ead.android.video;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.badlogic.gdx.utils.Array;

import es.eucm.ead.android.platform.DeviceVideoControl;
import es.eucm.ead.android.platform.DeviceVideoControl.RecordingListener;

public class VideoSurface extends SurfaceView {

	private final VideoSurfaceCallback callback;

	@SuppressWarnings("deprecation")
	public VideoSurface(Context context) {
		super(context);

		this.callback = new VideoSurfaceCallback(this);
		// We're implementing the Callback interface and want to get notified
		// about certain surface events.
		getHolder().addCallback(this.callback);
		if (VERSION.SDK_INT < VERSION_CODES.HONEYCOMB) {
			// We're changing the surface to a PUSH surface, meaning we're
			// receiving
			// all buffer data from another component - the camera, in this
			// case.
			getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			// Note this method was deprecated from HONEYCOMB (11 SDK version),
			// which the documentation now reflects.
			/*
			 * The trick is in knowing when it was deprecated, which is kind of
			 * hard to determine from my experience. The documentation is always
			 * current for the latest API available, but you are probably not
			 * running this APP on the latest API, if I had to guess. So you
			 * still have to use this method (typically with PUSH_BUFFERS) to
			 * make it work on older platforms.
			 */
		}
	}

	public void startRecording(String path, RecordingListener listener) {
		this.callback.startRecording(path, listener);
	}

	public void stopRecording(DeviceVideoControl.RecordingListener listener) {
		this.callback.stopRecording(listener);
	}

	public boolean isRecording() {
		return this.callback.isRecording();
	}

	public Array<String> getQualities() {
		return this.callback.getQualities();
	}

	public void setRecordingProfile(String profile) {
		this.callback.setRecordingProfile(profile);
	}

	public String getCurrentProfile() {
		return this.callback.getCurrentProfile();
	}
}