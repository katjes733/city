package com.katjes.city.objects.ground;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.katjes.city.graphics.TexturedPlane;

public class Field extends TransformGroup {
	private static final float Std_Spur_Breite = 2.50f;
	private static final float Bürgersteighöhe = 0.08f;

	public Field(final boolean Bürgersteig, final Appearance Textur,
			final int x_Raster, final int y_Raster) {
		final Transform3D t3d01 = new Transform3D();

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		if (Bürgersteig) {
			make_Flaeche(Bürgersteighöhe, Textur);
		} else {
			make_Flaeche(0.0f, Textur);
		}

		t3d01.setTranslation(
				new Vector3d(new Vector3d(x_Raster * Std_Spur_Breite * 2.0, 0.0,
						y_Raster * Std_Spur_Breite * 2.0)));
		this.setTransform(t3d01);
	}

	private void make_Flaeche(final float h, final Appearance Textur) {
		this.addChild(new TexturedPlane(new Point3f(-2.5f, h, -2.5f),
				new Point3f(-2.5f, h, 2.5f), new Point3f(2.5f, h, 2.5f),
				new Point3f(2.5f, h, -2.5f), Textur, 5.0f));
	}
}