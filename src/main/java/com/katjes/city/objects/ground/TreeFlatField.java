package com.katjes.city.objects.ground;

import java.util.Random;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.katjes.city.graphics.TexturedPlane;

public class TreeFlatField extends TransformGroup {
	private static final float Std_Spur_Breite = 2.50f;
	private static final float Bürgersteighöhe = 0.08f;
	private static final int Max_Objekte = 25;

	private final Boolean Bürgersteig;
	private final Appearance Boden;
	private final Appearance Baum01;
	// private final Appearance Baum02;
	// private final Appearance Baum03;
	// private final Appearance Baum04;
	// private final Appearance Baum05;

	public TreeFlatField(final Boolean Bürgersteig, final Appearance Boden,
			final Appearance Baum01, final int anz_Objekte, final int x_Raster,
			final int y_Raster) {
		int Anz_Objekte;

		this.Bürgersteig = Bürgersteig;

		this.Boden = Boden;
		this.Baum01 = Baum01;
		// this.Baum02 = Baum02;
		// this.Baum03 = Baum03;
		// this.Baum04 = Baum04;
		// this.Baum05 = Baum05;

		Anz_Objekte = anz_Objekte;

		if (Anz_Objekte > Max_Objekte) {
			Anz_Objekte = Max_Objekte;
		}
		if (Anz_Objekte < 0) {
			Anz_Objekte = 0;
		}

		this.addChild(
				make_Baum_Feld(Boden, Baum01, Anz_Objekte, x_Raster, y_Raster));
	}

	private TransformGroup make_Baum_Feld(final Appearance Boden,
			final Appearance Baum01, final int Anz_Objekte, final int x_Raster,
			final int y_Raster) {
		final TransformGroup Fläche = new TransformGroup();
		final Transform3D t3d01 = new Transform3D();

		// Boden
		Fläche.addChild(new TexturedPlane(
				new Point3f(-Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, Std_Spur_Breite),
				new Point3f(Std_Spur_Breite, 0.0f, -Std_Spur_Breite),
				new Point3f(-Std_Spur_Breite, 0.0f, -Std_Spur_Breite), Boden));

		final Random r = new Random();

		for (int i = 1; i < Anz_Objekte; i++) {
			Fläche.addChild(new TreeFlat(r.nextDouble() + 1,
					r.nextDouble() + 4,
					r.nextDouble() * 2 * Std_Spur_Breite - Std_Spur_Breite,
					r.nextDouble() * 2 * Std_Spur_Breite - Std_Spur_Breite,
					Baum01));

		}

		t3d01.setTranslation(new Vector3d(x_Raster * Std_Spur_Breite * 2, 0.0,
				-y_Raster * Std_Spur_Breite * 2));
		Fläche.setTransform(t3d01);

		return Fläche;
	}

}