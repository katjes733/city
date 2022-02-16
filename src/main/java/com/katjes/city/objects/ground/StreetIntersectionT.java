package com.katjes.city.objects.ground;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.katjes.city.graphics.TexturedPlane;
import com.sun.j3d.utils.geometry.Box;

public class StreetIntersectionT extends TransformGroup {
	private static final float Std_Spur_Breite = 2.5f;

	private final Appearance app_Strasse_t;
	private final Appearance app_Bürgersteig01;
	private final Appearance app_Bürgersteig02;

	public StreetIntersectionT(final int Spuren, final boolean Bürgersteig,
			final Appearance app_Strasse_t, final Appearance app_Bürgersteig01,
			final Appearance app_Bürgersteig02, final int Orientation,
			final int x_Raster, final int y_Raster) {
		// Orientation : 1 : T
		// 2 : |-
		// 3 : L
		// 4 : -|

		final Transform3D t3d01 = new Transform3D();
		final Transform3D t3d02 = new Transform3D();

		this.app_Strasse_t = app_Strasse_t;
		this.app_Bürgersteig01 = app_Bürgersteig01;
		this.app_Bürgersteig02 = app_Bürgersteig02;

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		switch (Spuren) {
		case 1: {
			if (Bürgersteig) {
				t_kreuzung_1_1();
			} else {
				t_kreuzung_1_0();
			}
			break;
		}
		default:
			t_kreuzung_1_0();
		}

		switch (Orientation) {
		case 1: {
			t3d02.setTranslation(new Vector3d(x_Raster * Std_Spur_Breite * 2.0,
					0.0, y_Raster * Std_Spur_Breite * 2.0));
			this.setTransform(t3d02);
			break;
		}
		case 2: {
			t3d01.rotY(Math.PI / 2);
			t3d02.setTranslation(new Vector3d(-y_Raster * Std_Spur_Breite * 2.0,
					0.0, x_Raster * Std_Spur_Breite * 2.0));
			t3d02.mul(t3d01, t3d02);
			this.setTransform(t3d02);
			break;
		}
		case 3: {
			t3d01.rotY(-Math.PI);
			t3d02.setTranslation(new Vector3d(-x_Raster * Std_Spur_Breite * 2.0,
					0.0, -y_Raster * Std_Spur_Breite * 2.0));
			t3d02.mul(t3d01, t3d02);
			this.setTransform(t3d02);
			break;
		}
		case 4: {
			t3d01.rotY(-Math.PI / 2);
			t3d02.setTranslation(new Vector3d(-y_Raster * Std_Spur_Breite * 2.0,
					0.0, -x_Raster * Std_Spur_Breite * 2.0));
			t3d02.mul(t3d01, t3d02);
			this.setTransform(t3d02);
			break;
		}
		}
	}

	private void t_kreuzung_1_0() {
		final TexturedPlane T_strasse_1_0 = new TexturedPlane(
				new Point3f(-Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				new Point3f(-Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				app_Strasse_t);
		addChild(T_strasse_1_0);
	}

	private void t_kreuzung_1_1() {
		final TexturedPlane T_strasse_1_1 = new TexturedPlane(
				new Point3f(-Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				new Point3f(-Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				app_Strasse_t);
		addChild(T_strasse_1_1);

		final Box bürgersteig00 = new Box(Std_Spur_Breite, 0.05f,
				Std_Spur_Breite / 8.0f,
				Box.GENERATE_TEXTURE_COORDS + Box.GENERATE_NORMALS,
				app_Bürgersteig01);
		final TransformGroup tg00 = new TransformGroup();
		final Transform3D t3d01 = new Transform3D();
		final Transform3D t3d02 = new Transform3D();
		tg00.addChild(bürgersteig00);
		t3d01.rotZ(Math.PI);
		t3d02.setTranslation(
				new Vector3d(0.0, -0.04, -0.875 * Std_Spur_Breite));
		t3d02.mul(t3d01, t3d02);
		tg00.setTransform(t3d02);
		addChild(tg00);

		final Box bürgersteig01 = new Box(Std_Spur_Breite / 8.0f, 0.05f,
				Std_Spur_Breite / 8.0f,
				Box.GENERATE_TEXTURE_COORDS + Box.GENERATE_NORMALS,
				app_Bürgersteig02);
		final TransformGroup tg01 = new TransformGroup();
		final Transform3D t3d04 = new Transform3D();
		tg01.addChild(bürgersteig01);
		t3d04.setTranslation(new Vector3d(0.875 * Std_Spur_Breite, 0.04,
				0.875 * Std_Spur_Breite));
		tg01.setTransform(t3d04);
		addChild(tg01);

		final Box bürgersteig03 = new Box(Std_Spur_Breite / 8.0f, 0.05f,
				Std_Spur_Breite / 8.0f,
				Box.GENERATE_TEXTURE_COORDS + Box.GENERATE_NORMALS,
				app_Bürgersteig02);
		final TransformGroup tg03 = new TransformGroup();
		final Transform3D t3d03 = new Transform3D();
		tg03.addChild(bürgersteig03);
		t3d01.rotY(-Math.PI / 2);
		t3d03.setTranslation(new Vector3d(0.875 * Std_Spur_Breite, 0.04,
				0.875 * Std_Spur_Breite));
		t3d03.mul(t3d01, t3d03);
		tg03.setTransform(t3d03);
		addChild(tg03);
	}
}