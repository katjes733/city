package com.katjes.city.objects.ground;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.katjes.city.objects.Object_3D;

public class TreeField extends TransformGroup {
	private static final float Std_Spur_Breite = 2.50f;
	private static final float Bürgersteighöhe = 0.08f;
	private static final int Max_Objekte = 25;

	public TreeField(final Appearance Boden, final String Baum_Objekt,
			final int anz_Objekte, final int x_Raster, final int y_Raster) {
		int Anz_Objekte;

		Anz_Objekte = anz_Objekte;

		if (Anz_Objekte > Max_Objekte) {
			Anz_Objekte = 25;
		}
		if (Anz_Objekte < 0) {
			Anz_Objekte = 0;
		}

		this.addChild(make_Baum_Feld(Boden, Baum_Objekt, Anz_Objekte, x_Raster,
				y_Raster));
	}

	private TransformGroup make_Baum_Feld(final Appearance Boden,
			final String Baum_Objekt, final int anz_Objekte, final int x_Raster,
			final int y_Raster) {
		final TransformGroup Fläche = new TransformGroup();
		final Transform3D t3d01 = new Transform3D();
		final int Obj_per_Side;
		final int Zähler = 0;
		final double Div_per_Side;
		final double move_Lenght;
		final double v_x, v_y;

		Fläche.addChild(new Field(true, Boden, 0, 0));

		Fläche.addChild(new Object_3D(Baum_Objekt, 100.0));

		/*
		 * if (anz_Objekte > 0) { Obj_per_Side = (int) Math.sqrt((double) anz_Objekte); Div_per_Side = (double)
		 * Std_Spur_Breite*2/Obj_per_Side; TransformGroup[] temp = new TransformGroup [anz_Objekte+1]; Object_3D[] Baum
		 * = new Object_3D [anz_Objekte+1]; Random r = new Random (); // Basis-Positionierung vorne rechts move_Lenght =
		 * (((double)(Obj_per_Side))/2-0.5)*Div_per_Side;
		 * System.out.println("Obj_per_Side : "+Obj_per_Side+" ; Div_per_Side : "+Div_per_Side+" ; move_Lenght : "
		 * +move_Lenght); for (int i = 0; i < Obj_per_Side; i++) { for (int j = 0; j < Obj_per_Side; j++) {
		 * Baum[++Zähler] = new Object_3D (Baum_Objekt,100.0); v_x = (r.nextDouble()-0.5)*Div_per_Side; v_y =
		 * (r.nextDouble()-0.5)*Div_per_Side; t3d01.setTranslation(new
		 * Vector3d(-Div_per_Side*i+move_Lenght+v_x,0.0,-Div_per_Side*j+move_Lenght+v_y));
		 * Baum[Zähler].setTransform(t3d01); Fläche.addChild(Baum[Zähler]); } } }
		 */

		return Fläche;
	}

}