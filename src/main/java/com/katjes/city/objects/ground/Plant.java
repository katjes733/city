package com.katjes.city.objects.ground;

import java.util.Random;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.katjes.city.graphics.TexturedPlane;

public class Plant extends TransformGroup {
	private static float x;
	private static float y;
	private static int complexity = 2;

	private static final double querWinkel = 45.0;

	public Plant(final int complexity, final Appearance app) {
		x = 0.0f;
		y = 0.0f;
		this.complexity = complexity;
		this.addChild(make_Plant(1.0f, 1.0f, app));
	}

	public Plant(final int complexity, final double width, final double height,
			final Appearance app) {
		x = 0.0f;
		y = 0.0f;
		this.complexity = complexity;
		this.addChild(make_Plant((float) width, (float) height, app));
	}

	public Plant(final int complexity, final Appearance app, final double x,
			final double y) {
		this.x = (float) x;
		this.y = -(float) y;
		this.complexity = complexity;
		this.addChild(make_Plant(1.0f, 1.0f, app));
	}

	public Plant(final int complexity, final double width, final double height,
			final double x, final double y, final Appearance app) {
		this.x = (float) x;
		this.y = -(float) y;
		this.complexity = complexity;
		this.addChild(make_Plant((float) width, (float) height, app));
	}

	private TransformGroup make_Plant(final float width, final float height,
			final Appearance app) {
		final TransformGroup TGret = new TransformGroup();
		final Transform3D T3D = new Transform3D();
		final Random r = new Random();

		final float winkel = (float) (r.nextFloat() * Math.PI / 2);
		final float W1 = (float) (Math.cos(winkel) * width / 2.0f);
		final float W2 = (float) (Math.sin(winkel) * width / 2.0f);

		final TexturedPlane TP01 = new TexturedPlane(new Point3f(-W1, 0.0f, W2),
				new Point3f(W1, 0.0f, -W2), new Point3f(W1, height, -W2),
				new Point3f(-W1, height, W2), app);
		final TexturedPlane TP02 = new TexturedPlane(
				new Point3f(-W2, 0.0f, -W1), new Point3f(W2, 0.0f, W1),
				new Point3f(W2, height, W1), new Point3f(-W2, height, -W1),
				app);
		TGret.addChild(TP01);
		TGret.addChild(TP02);

		if (complexity > 2) {
			final TexturedPlane TP03 = new TexturedPlane(
					new Point3f(-W2, 0.0f, -W1), new Point3f(W2, 0.0f, W1),
					new Point3f(W2, height, W1), new Point3f(-W2, height, -W1),
					app);
			TGret.addChild(TP03);
		}

		T3D.setTranslation(new Vector3f(x, 0.0f, y));
		TGret.setTransform(T3D);

		return TGret;
	}

	public void moveTo(final double x, final double y) {
		final Transform3D T3D = new Transform3D();
		this.x = (float) x - this.x;
		this.y = -(float) y - this.y;
		T3D.setTranslation(new Vector3f(this.x, 0.0f, this.y));
		this.setTransform(T3D);
	}
}