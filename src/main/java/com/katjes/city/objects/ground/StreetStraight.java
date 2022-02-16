package com.katjes.city.objects.ground;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.katjes.city.graphics.TexturedPlane;
import com.sun.j3d.utils.geometry.Box;

public class StreetStraight extends TransformGroup {
	private static final float Std_Spur_Breite = 2.5f;
	public static final int NUMOFSTREETLIGHTS = 2;

	private final Appearance app_Strasse_g;
	private final Appearance app_Bürgersteig01;
	private final Appearance LightSurface;

	public StreetLight[] SL;
	public boolean lights = false;

	public StreetStraight(final int Spuren, final boolean Bürgersteig,
			final boolean lights, final Appearance app_Strasse_g,
			final Appearance app_Bürgersteig01, final Appearance LightSurface) {
		this.app_Strasse_g = app_Strasse_g;
		this.app_Bürgersteig01 = app_Bürgersteig01;
		this.LightSurface = LightSurface;
		this.lights = lights;
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		switch (Spuren) {
		case 1: {
			if (Bürgersteig) {
				strasse_1_1();
			} else {
				strasse_1_0();
			}
			break;
		}
		default:
			strasse_1_0();
		}
	}

	public StreetStraight(final int Spuren, final boolean Bürgersteig,
			final boolean lights, final Appearance app_Strasse_g,
			final Appearance app_Bürgersteig01, final Appearance LightSurface,
			final int Orientation, final int x_Raster, final int y_Raster) {
		// Orientation : 1 : north-south
		// 2 : east-west

		SL = new StreetLight[NUMOFSTREETLIGHTS];

		Transform3D t3d01 = new Transform3D();
		Transform3D t3d02 = new Transform3D();
		Transform3D t3d03 = new Transform3D();

		this.app_Strasse_g = app_Strasse_g;
		this.app_Bürgersteig01 = app_Bürgersteig01;
		this.LightSurface = LightSurface;
		this.lights = lights;

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		switch (Spuren) {
		case 1: {
			if (Bürgersteig) {
				strasse_1_1();
			} else {
				strasse_1_0();
			}
			break;
		}
		default:
			strasse_1_0();
		}

		switch (Orientation) {
		case 1: {
			t3d01 = new Transform3D();
			t3d02 = new Transform3D();
			t3d03 = new Transform3D();

			t3d01.rotY(-Math.PI / 2);
			t3d02.setTranslation(new Vector3d(y_Raster * Std_Spur_Breite * 2.0,
					0.0, x_Raster * Std_Spur_Breite * 2.0));
			t3d03.mul(t3d01, t3d02);
			this.setTransform(t3d03);
			break;
		}
		case 2: {
			t3d01 = new Transform3D();
			t3d02 = new Transform3D();
			t3d03 = new Transform3D();

			t3d01.setTranslation(new Vector3d(x_Raster * Std_Spur_Breite * 2.0,
					0.0, y_Raster * Std_Spur_Breite * 2.0));
			this.setTransform(t3d01);
			break;
		}
		}
	}

	private void strasse_1_0() {
		final TexturedPlane T_strasse_1_0 = new TexturedPlane(
				new Point3f(-Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				new Point3f(-Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				app_Strasse_g);
		addChild(T_strasse_1_0);
	}

	private void strasse_1_1() {
		final TexturedPlane T_strasse_1_1 = new TexturedPlane(
				new Point3f(-Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				new Point3f(-Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				app_Strasse_g);
		addChild(T_strasse_1_1);

		final Box bürgersteig01 = new Box(Std_Spur_Breite, 0.05f,
				Std_Spur_Breite / 8.0f,
				Box.GENERATE_TEXTURE_COORDS + Box.GENERATE_NORMALS,
				app_Bürgersteig01);
		final TransformGroup tg01 = new TransformGroup();
		final Transform3D t3d01 = new Transform3D();
		tg01.addChild(bürgersteig01);
		t3d01.setTranslation(new Vector3d(0.0, 0.04, 0.875 * Std_Spur_Breite));
		tg01.setTransform(t3d01);
		addChild(tg01);

		final Box bürgersteig02 = new Box(Std_Spur_Breite, 0.05f,
				Std_Spur_Breite / 8.0f,
				Box.GENERATE_TEXTURE_COORDS + Box.GENERATE_NORMALS,
				app_Bürgersteig01);
		final TransformGroup tg02 = new TransformGroup();
		final Transform3D t3d02 = new Transform3D();
		tg02.addChild(bürgersteig02);
		t3d01.rotZ(Math.PI);
		t3d02.setTranslation(
				new Vector3d(0.0, -0.04, -0.875 * Std_Spur_Breite));
		t3d02.mul(t3d01, t3d02);
		tg02.setTransform(t3d02);
		addChild(tg02);

		if (lights) {
			SL[0] = new StreetLight(0, Std_Spur_Breite, 90, LightSurface);
			addChild(SL[0]);

			SL[1] = new StreetLight(0, -Std_Spur_Breite, -90, LightSurface);
			addChild(SL[1]);
		}
	}
}
