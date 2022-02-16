package com.katjes.city.objects.ground;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.katjes.city.graphics.TexturedPlane;
import com.sun.j3d.utils.geometry.Box;

public class StreetIntersection extends TransformGroup {
	private static final float Std_Spur_Breite = 2.5f;

	Appearance app_Strasse_plus;
	Appearance app_Bürgersteig02;

	public StreetIntersection(final int Spuren, final boolean Bürgersteig,
			final Appearance app_Strasse_plus,
			final Appearance app_Bürgersteig02) {
		this.app_Strasse_plus = app_Strasse_plus;
		this.app_Bürgersteig02 = app_Bürgersteig02;

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		switch (Spuren) {
		case 1: {
			if (Bürgersteig) {
				kreuzung_1_1();
			} else {
				kreuzung_1_0();
			}
			break;
		}
		default:
			kreuzung_1_0();
		}
	}

	public StreetIntersection(final int Spuren, final boolean Bürgersteig,
			final Appearance app_Strasse_plus,
			final Appearance app_Bürgersteig02, final int x_Raster,
			final int y_Raster) {
		final Transform3D t3d01 = new Transform3D();

		this.app_Strasse_plus = app_Strasse_plus;
		this.app_Bürgersteig02 = app_Bürgersteig02;

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		switch (Spuren) {
		case 1: {
			if (Bürgersteig) {
				kreuzung_1_1();
			} else {
				kreuzung_1_0();
			}
			break;
		}
		default:
			kreuzung_1_0();
		}

		t3d01.setTranslation(
				new Vector3d(new Vector3d(x_Raster * Std_Spur_Breite * 2.0, 0.0,
						y_Raster * Std_Spur_Breite * 2.0)));
		this.setTransform(t3d01);
	}

	private void kreuzung_1_0() {
		final TexturedPlane T_strasse_1_0 = new TexturedPlane(
				new Point3f(-Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				new Point3f(-Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				app_Strasse_plus);
		addChild(T_strasse_1_0);
	}

	private void kreuzung_1_1() {
		final TexturedPlane T_strasse_1_1 = new TexturedPlane(
				new Point3f(-Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				new Point3f(-Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				app_Strasse_plus);
		addChild(T_strasse_1_1);

		final Box bürgersteig01 = new Box(Std_Spur_Breite / 8.0f, 0.05f,
				Std_Spur_Breite / 8.0f,
				Box.GENERATE_TEXTURE_COORDS + Box.GENERATE_NORMALS,
				app_Bürgersteig02);
		final TransformGroup tg01 = new TransformGroup();
		final Transform3D t3d01 = new Transform3D();
		tg01.addChild(bürgersteig01);
		t3d01.setTranslation(new Vector3d(0.875 * Std_Spur_Breite, 0.04,
				0.875 * Std_Spur_Breite));
		tg01.setTransform(t3d01);
		addChild(tg01);

		final Box bürgersteig02 = new Box(Std_Spur_Breite / 8.0f, 0.05f,
				Std_Spur_Breite / 8.0f,
				Box.GENERATE_TEXTURE_COORDS + Box.GENERATE_NORMALS,
				app_Bürgersteig02);
		final TransformGroup tg02 = new TransformGroup();
		final Transform3D t3d02 = new Transform3D();
		tg02.addChild(bürgersteig02);
		t3d01.rotY(Math.PI / 2);
		t3d02.setTranslation(new Vector3d(0.875 * Std_Spur_Breite, 0.04,
				0.875 * Std_Spur_Breite));
		t3d02.mul(t3d01, t3d02);
		tg02.setTransform(t3d02);
		addChild(tg02);

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

		final Box bürgersteig04 = new Box(Std_Spur_Breite / 8.0f, 0.05f,
				Std_Spur_Breite / 8.0f,
				Box.GENERATE_TEXTURE_COORDS + Box.GENERATE_NORMALS,
				app_Bürgersteig02);
		final TransformGroup tg04 = new TransformGroup();
		final Transform3D t3d04 = new Transform3D();
		tg04.addChild(bürgersteig04);
		t3d01.rotZ(Math.PI);
		t3d04.setTranslation(new Vector3d(0.875 * Std_Spur_Breite, -0.04,
				-0.875 * Std_Spur_Breite));
		t3d04.mul(t3d01, t3d04);
		tg04.setTransform(t3d04);
		addChild(tg04);
	}
}