package com.katjes.city.graphics;

import java.util.Random;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.katjes.city.City;

public class Attachment2ViewPlatform extends TransformGroup
		implements Runnable {
	private static final float divisionsPerSquare = 40.0f;
	private static final double randomFactor01 = 1.5;
	private static final float scaleFactorForSplashes = 0.5f;
	private static final int numOfSplashes = 51;

	private static TexturedPlane tp, tp2;
	private TextureAnimation nothing = null;
	// nur public damit änderung des delays ausgeführt werden kann hinterher ggf. wieder auf private setzen
	public TextureAnimation Rain, Splash, Splash_up;
	private TextureAnimation ta_Rain;
	private static City city;
	private static double dist;
	private Thread raining;
	private final Random r;
	private final TransformGroup RainDrops;

	private final TransformGroup Splashes;
	private final TransformGroup[] Splash_TG;
	private TextureAnimation Splash_TA_1, Splash_TA_2;
	private final TexturedPlane[] Splash_TP;
	private Thread Splash_TH_1, Splash_TH_2;

	private boolean rainingActive = false;

	public Attachment2ViewPlatform(final City obj, final double dist,
			final double side) {
		r = new Random();

		nothing = new TextureAnimation();
		final TransparencyAttributes tra = new TransparencyAttributes();
		tra.setTransparencyMode(TransparencyAttributes.BLENDED);
		tra.setTransparency(1f);
		nothing.setTransparencyAttributes(tra);

		RainDrops = new TransformGroup();
		Splashes = new TransformGroup();

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		RainDrops.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		RainDrops.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Splashes.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Splashes.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Splash_TG = new TransformGroup[numOfSplashes];
		Splash_TP = new TexturedPlane[numOfSplashes];

		Rain = new TextureAnimation("raindrop", 8, 25, true, -1);
		Splash = new TextureAnimation("splash", 3, 75, true, 1);
		Splash_up = new TextureAnimation("splash_up", 3, 75, true, 1);

		city = obj;
		final float f = (float) side;
		ta_Rain = nothing;
		this.dist = dist;

		final Transform3D t3d01 = new Transform3D();
		final Transform3D t3d02 = new Transform3D();
		final Transform3D t3d03 = new Transform3D();

		city.vp.getViewPlatformTransform().getTransform(t3d01);
		// Nun ist die Transformationsinformation in t3d01 gespeichert

		Point3f p1 = new Point3f(-f / 2.0f, -f / 2.0f, 0.0f);
		Point3f p2 = new Point3f(f / 2.0f, -f / 2.0f, 0.0f);
		Point3f p3 = new Point3f(f / 2.0f, f / 2.0f, 0.0f);
		Point3f p4 = new Point3f(-f / 2.0f, f / 2.0f, 0.0f);

		tp = new TexturedPlane(p1, p2, p3, p4, ta_Rain, divisionsPerSquare);

		this.addChild(tp);

		p1 = new Point3f(-f, f / 2.0f, 0.0f);
		p2 = new Point3f(f, f / 2.0f, 0.0f);
		p3 = new Point3f(f, f * 2.5f, 0.0f);
		p4 = new Point3f(-f, f * 2.5f, 0.0f);

		tp2 = new TexturedPlane(p1, p2, p3, p4, ta_Rain,
				divisionsPerSquare * 2.0f);

		this.addChild(tp2);

		for (int i = 0; i < numOfSplashes; i++) {
			Splash_TG[i] = new TransformGroup();
			if (i % 2 == 0) {
				p1 = new Point3f(-0.025f * scaleFactorForSplashes, 0.00f,
						0.0f * scaleFactorForSplashes);
				p2 = new Point3f(0.025f * scaleFactorForSplashes, 0.00f,
						0.0f * scaleFactorForSplashes);
				p3 = new Point3f(0.025f * scaleFactorForSplashes, 0.05f,
						0.0f * scaleFactorForSplashes);
				p4 = new Point3f(-0.025f * scaleFactorForSplashes, 0.05f,
						0.0f * scaleFactorForSplashes);
			} else {
				p1 = new Point3f(-0.025f * scaleFactorForSplashes,
						0.00f * 0.707f * scaleFactorForSplashes,
						0.0f * 0.707f * scaleFactorForSplashes);
				p2 = new Point3f(0.025f * scaleFactorForSplashes,
						0.00f * 0.707f * scaleFactorForSplashes,
						0.0f * 0.707f * scaleFactorForSplashes);
				p3 = new Point3f(0.025f * scaleFactorForSplashes,
						0.05f * 0.707f * scaleFactorForSplashes,
						-0.05f * 0.707f * scaleFactorForSplashes);
				p4 = new Point3f(-0.025f * scaleFactorForSplashes,
						0.05f * 0.707f * scaleFactorForSplashes,
						-0.05f * 0.707f * scaleFactorForSplashes);
			}

			Splash_TP[i] = new TexturedPlane(p1, p2, p3, p4, nothing);

			Splash_TG[i].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
			Splash_TG[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

			Splash_TG[i].addChild(Splash_TP[i]);
			Splashes.addChild(Splash_TG[i]);
		}
		this.addChild(Splashes);

		t3d02.setTranslation(new Vector3d(0.0, 0.0, -dist));
		t3d03.mul(t3d01, t3d02);
		this.setTransform(t3d03);
	}

	public void rotateY() {
		final Transform3D t3d01 = new Transform3D();
		final Transform3D t3d02 = new Transform3D();
		final Transform3D t3d03 = new Transform3D();

		city.vp.getViewPlatformTransform().getTransform(t3d01);
		// Nun ist die Transformationsinformation in t3d01 gespeichert

		t3d02.setTranslation(new Vector3d(0.0, 0.0, -dist));
		t3d03.mul(t3d01, t3d02);
		this.setTransform(t3d03);
	}

	public void rotateX(final double angle, final boolean YRotation) {
		final Transform3D t3d01 = new Transform3D();
		final Transform3D t3d02 = new Transform3D();
		Transform3D t3d03 = new Transform3D();
		final Transform3D t3d04 = new Transform3D();
		final Transform3D t3d05 = new Transform3D();

		city.vp.getViewPlatformTransform().getTransform(t3d03);
		// Nun ist die Transformationsinformation in t3d01 gespeichert

		t3d04.rotX(-angle);
		t3d05.mul(t3d03, t3d04);

		t3d03 = new Transform3D();

		t3d02.setTranslation(new Vector3d(0.0, dist * Math.sin(angle),
				-dist * Math.cos(angle)));
		t3d03.mul(t3d05, t3d02);
		if (YRotation) {
			this.setTransform(t3d03);
		} else {
			RainDrops.setTransform(t3d03);
		}
	}

	public void startIt() {
		System.out.println("Starte Threads");
		rainingActive = true;

		final Transform3D t3d01 = new Transform3D();
		Transform3D t3d02;
		Transform3D t3d03;

		city.vp.getViewPlatformTransform().getTransform(t3d01);

		Splash_TA_1 = Splash;
		Splash_TA_2 = Splash_up;

		for (int i = 0; i < numOfSplashes; i++) {

			if (i % 2 == 0) {
				Splash_TP[i].setAppearance(Splash_TA_1);
			} else {
				Splash_TP[i].setAppearance(Splash_TA_2);
			}

			t3d02 = new Transform3D();
			t3d03 = new Transform3D();
			t3d02.setTranslation(new Vector3d(
					r.nextDouble() * randomFactor01 - randomFactor01 / 2,
					-0.365,
					r.nextDouble() * randomFactor01 - randomFactor01 / 2));
			t3d03.mul(t3d01, t3d02);
			Splash_TG[i].setTransform(t3d02);
		}

		Splash_TH_1 = new Thread(Splash_TA_1);
		Splash_TH_2 = new Thread(Splash_TA_2);

		Splash_TH_1.start();
		Splash_TH_2.start();

		ta_Rain = Rain;
		tp.setAppearance(ta_Rain);
		tp2.setAppearance(ta_Rain);

		raining = new Thread(ta_Rain);
		raining.start();

	}

	public void stop() {
		rainingActive = false;

		ta_Rain = nothing;
		tp.setAppearance(ta_Rain);
		tp2.setAppearance(ta_Rain);

		Splash_TA_1 = nothing;
		Splash_TA_2 = nothing;

		for (int i = 0; i < numOfSplashes; i++) {

			if (i % 2 == 0) {
				Splash_TP[i].setAppearance(Splash_TA_1);
			} else {
				Splash_TP[i].setAppearance(Splash_TA_2);
			}
		}

		Splash_TH_1.stop();
		Splash_TH_2.stop();

		raining.stop();

		System.out.println("Stoppe Threads");
	}

	public void run() {
		final Transform3D t3d01 = new Transform3D();
		Transform3D t3d02;
		Transform3D t3d03;

		city.vp.getViewPlatformTransform().getTransform(t3d01);

		boolean finishedBefore = false;

		while (1 > 0) {
			try {
				Thread.sleep(100);
			} catch (final Exception e) {
			}

			if (rainingActive) {

				if (Splash_TA_1.getFinished() && !finishedBefore) {

					finishedBefore = true;

					Splash_TH_1.stop();
					Splash_TH_2.stop();

					for (int i = 0; i < numOfSplashes; i++) {
						t3d02 = new Transform3D();
						t3d03 = new Transform3D();

						t3d02.setTranslation(new Vector3d(
								r.nextDouble() * randomFactor01
										- randomFactor01 / 2,
								-0.365, r.nextDouble() * randomFactor01
										- randomFactor01 / 2));
						t3d03.mul(t3d01, t3d02);
						Splash_TG[i].setTransform(t3d02);
					}

					Splash_TH_1 = new Thread(Splash_TA_1);
					Splash_TH_2 = new Thread(Splash_TA_2);

					Splash_TH_1.start();
					Splash_TH_2.start();

				}

				if (!Splash_TA_1.getFinished()) {
					finishedBefore = false;
				}
			}

		}
	}
}