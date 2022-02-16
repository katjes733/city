package com.katjes.city.objects;

import java.net.URL;

import javax.media.j3d.BackgroundSound;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.MediaContainer;
import javax.media.j3d.PointSound;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point2f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

public class CitySounds extends TransformGroup {
	private static final int NUMOFPOINTSOUNDS = 20;
	private static final int NUMOFBACKGROUNDSOUNDS = 2;
	private static final String SOUND_PATH = "sounds/";
	private static final BoundingSphere SOUNDBOUNDS = new BoundingSphere(
			new Point3d(0.0, 0.0, 0.0), 100.0);

	public PointSound[] pointSound = new PointSound[NUMOFPOINTSOUNDS];
	public BackgroundSound[] backSound = new BackgroundSound[NUMOFBACKGROUNDSOUNDS];
	// public

	public CitySounds() {
		loadSounds();
	}

	private void loadSounds() {
		/******************************************************************************************************/
		// Hier alle Sounds laden
		// Hinweis : nicht übertreiben mit den Sounds (schafft der Müll von Java3D auch nicht)
		// schon nun bei insgesamt 6 sounds ist die performance unter aller sau
		// Background
		backSound[0] = loadBackgroundSound(backSound[0], "rain3wav.wav", -1);
		this.addChild(backSound[0]);
		// loadBackgroundSound(backSound[0],"roar.au",-1);

		// PointSounds
		/*
		 * pointSound[0] = loadPointSound("forest.aiff",new Point3f(-30.0f,3.0f,0.0f),-1,setGain(20,1.0f,15.0f));
		 * this.addChild(pointSound[0]); pointSound[0].setEnable(true); /* pointSound[1] =
		 * loadPointSound("forest.aiff",new Point3f(-30.0f,3.0f,30.0f),-1,setGain(20,1.0f,15.0f));
		 * this.addChild(pointSound[1]); pointSound[1].setEnable(true);
		 */
		pointSound[2] = loadPointSound("forest.aiff",
				new Point3f(0.0f, 3.0f, 30.0f), -1, setGain(20, 1.0f, 15.0f));
		this.addChild(pointSound[2]);
		pointSound[2].setEnable(true);
		/*
		 * pointSound[3] = loadPointSound("night2.wav",new Point3f(-30.0f,3.0f,0.0f),-1,setGain(20,1.0f,15.0f));
		 * this.addChild(pointSound[3]); pointSound[3].setEnable(false); pointSound[4] = loadPointSound("night2.wav",new
		 * Point3f(-30.0f,3.0f,30.0f),-1,setGain(20,1.0f,15.0f)); this.addChild(pointSound[4]);
		 * pointSound[4].setEnable(false); pointSound[5] = loadPointSound("night2.wav",new
		 * Point3f(0.0f,3.0f,30.0f),-1,setGain(20,1.0f,15.0f)); this.addChild(pointSound[5]);
		 * pointSound[5].setEnable(false);
		 *//*
			 * pointSound[6] = loadPointSound("SEASHORE.wav",new Point3f(0.0f,3.0f,-30.0f),-1,setGain(20,1.0f,25.0f));
			 * this.addChild(pointSound[6]); pointSound[6].setEnable(true); /* pointSound[7] =
			 * loadPointSound("SEASHORE.wav",new Point3f(30.0f,3.0f,-30.0f),-1,setGain(20,1.0f,25.0f));
			 * this.addChild(pointSound[7]); pointSound[7].setEnable(true);
			 */
		pointSound[8] = loadPointSound("SEASHORE.wav",
				new Point3f(30.0f, 3.0f, 0.0f), -1, setGain(20, 1.0f, 25.0f));
		this.addChild(pointSound[8]);
		pointSound[8].setEnable(true);

		/*
		 * pointSound[9] = loadPointSound("ocean.aiff",new Point3f(0.0f,3.0f,-30.0f),-1,setGain(20,1.0f,25.0f));
		 * this.addChild(pointSound[9]); pointSound[9].setEnable(false); pointSound[10] =
		 * loadPointSound("ocean.aiff",new Point3f(30.0f,3.0f,-30.0f),-1,setGain(20,1.0f,25.0f));
		 * this.addChild(pointSound[10]); pointSound[10].setEnable(false); pointSound[11] =
		 * loadPointSound("ocean.aiff",new Point3f(30.0f,3.0f,0.0f),-1,setGain(20,1.0f,25.0f));
		 * this.addChild(pointSound[11]); pointSound[11].setEnable(false);
		 */

		pointSound[12] = loadPointSound("jackin.wav",
				new Point3f(6.85f, 1.82f, -2.2f), -1, setGain(10, 1.0f, 5f));
		this.addChild(pointSound[12]);
		pointSound[12].setEnable(true);
		/******************************************************************************************************/
	}

	private PointSound loadPointSound(final String fileName,
			final Point3f position, final int loop, final Point2f[] gainList) {
		PointSound targetSoundObject;

		final BoundingSphere soundBounds = new BoundingSphere(
				new Point3d(position.x, position.y, position.z), 16.0);
		targetSoundObject = new PointSound();
		targetSoundObject.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		targetSoundObject.setCapability(PointSound.ALLOW_INITIAL_GAIN_WRITE);
		targetSoundObject.setCapability(PointSound.ALLOW_SOUND_DATA_WRITE);
		targetSoundObject
				.setCapability(PointSound.ALLOW_SCHEDULING_BOUNDS_WRITE);
		targetSoundObject.setCapability(PointSound.ALLOW_CONT_PLAY_WRITE);
		targetSoundObject.setCapability(PointSound.ALLOW_RELEASE_WRITE);
		targetSoundObject.setCapability(PointSound.ALLOW_DURATION_READ);
		targetSoundObject.setCapability(PointSound.ALLOW_IS_PLAYING_READ);
		targetSoundObject.setCapability(PointSound.ALLOW_LOOP_WRITE);
		targetSoundObject.setSchedulingBounds(soundBounds);

		final MediaContainer sample = new MediaContainer();
		sample.setCapability(MediaContainer.ALLOW_URL_WRITE);
		sample.setCapability(MediaContainer.ALLOW_URL_READ);
		sample.setURLObject(getSoundUrl(fileName));
		// sample.setCacheEnable(false);

		targetSoundObject.setLoop(loop);
		targetSoundObject.setContinuousEnable(false);
		targetSoundObject.setReleaseEnable(false);
		targetSoundObject.setSoundData(sample);
		targetSoundObject.setInitialGain(1.0f);
		targetSoundObject.setDistanceGain(gainList);
		targetSoundObject.setPosition(position);

		// this.addChild(targetSoundObject);
		return targetSoundObject;
	}

	private BackgroundSound loadBackgroundSound(
			BackgroundSound targetSoundObject, final String fileName,
			final int loop) {
		targetSoundObject = new BackgroundSound();
		targetSoundObject.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		targetSoundObject.setCapability(PointSound.ALLOW_INITIAL_GAIN_WRITE);
		targetSoundObject.setCapability(PointSound.ALLOW_SOUND_DATA_WRITE);
		targetSoundObject
				.setCapability(PointSound.ALLOW_SCHEDULING_BOUNDS_WRITE);
		targetSoundObject.setCapability(PointSound.ALLOW_CONT_PLAY_WRITE);
		targetSoundObject.setCapability(PointSound.ALLOW_RELEASE_WRITE);
		targetSoundObject.setCapability(PointSound.ALLOW_DURATION_READ);
		targetSoundObject.setCapability(PointSound.ALLOW_IS_PLAYING_READ);
		targetSoundObject.setCapability(PointSound.ALLOW_LOOP_WRITE);
		targetSoundObject.setSchedulingBounds(SOUNDBOUNDS);

		final MediaContainer sample = new MediaContainer();
		sample.setCapability(MediaContainer.ALLOW_URL_WRITE);
		sample.setCapability(MediaContainer.ALLOW_URL_READ);
		sample.setURLObject(getSoundUrl(fileName));
		// sample.setCacheEnable(false);

		targetSoundObject.setLoop(loop);
		targetSoundObject.setContinuousEnable(false);
		targetSoundObject.setReleaseEnable(false);
		targetSoundObject.setSoundData(sample);
		targetSoundObject.setInitialGain(1.0f);

		// this.addChild(targetSoundObject);
		return targetSoundObject;
	}

	private Point2f[] setGain(int factors, final float startGain,
			final float endDistance) {
		if (factors < 2) {
			factors = 2;
		}

		final Point2f[] p2f = new Point2f[factors + 1];
		final float decreaseDistFactor = endDistance / factors;
		final float decreaseGainFactor = startGain / factors;

		for (int i = 0; i < factors + 1; i++) {
			p2f[i] = new Point2f(i * decreaseDistFactor,
					1.0f - i * decreaseGainFactor);
			// System.out.println((i * decreaseDistFactor) + " ; " + (1.0f - i * decreaseGainFactor));
		}

		return p2f;
	}

	public void changeSound(final PointSound ps, final String fileName) {
		final MediaContainer sample = new MediaContainer();
		sample.setCapability(MediaContainer.ALLOW_URL_WRITE);
		sample.setCapability(MediaContainer.ALLOW_URL_READ);
		sample.setURLObject(getSoundUrl(fileName));

		ps.setSoundData(sample);
	}

	private URL getSoundUrl(final String fileName) {
		final URL returnValue = getClass().getClassLoader()
				.getResource(SOUND_PATH + fileName);
		System.out.println("Sound: " + returnValue);
		return returnValue;
	}
}