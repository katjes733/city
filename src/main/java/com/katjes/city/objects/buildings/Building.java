package com.katjes.city.objects.buildings;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.katjes.city.graphics.TexturedPlane;
import com.katjes.city.objects.Object_3D;
import com.sun.j3d.utils.image.TextureLoader;

public class Building extends TransformGroup {
	private static final float Std_Breite = 2.5f;

	private static String Hauptpfad_Grafik;

	private static String Textur = "";

	public Building(final double faktor_Grundplatte, final String Pfad_Grafik,
			final float div) {
		Hauptpfad_Grafik = Pfad_Grafik;
		Textur = "";

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		addChild(make_Plate(Math.abs(faktor_Grundplatte), div));
	}

	public Building(final double faktor_Grundplatte, final String Pfad_Grafik,
			final String Textur, final float div) {
		Hauptpfad_Grafik = Pfad_Grafik;
		this.Textur = Textur;

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		addChild(make_Plate(Math.abs(faktor_Grundplatte), div));
	}

	public Building(final double faktor_Grundplatte, final String Pfad_Grafik,
			final String Model, final double Drehwinkel,
			final double Vergrößerung, final double y_Verschiebung,
			final float div) {
		Hauptpfad_Grafik = Pfad_Grafik;
		Textur = "";

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		make_Building(Math.abs(faktor_Grundplatte), Model, Drehwinkel,
				Vergrößerung, y_Verschiebung, div);
	}

	public Building(final double faktor_Grundplatte, final String Pfad_Grafik,
			final String Textur, final String Model, final double Drehwinkel,
			final double Vergrößerung, final double y_Verschiebung,
			final float div) {
		Hauptpfad_Grafik = Pfad_Grafik;
		this.Textur = Textur;

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		make_Building(Math.abs(faktor_Grundplatte), Model, Drehwinkel,
				Vergrößerung, y_Verschiebung, div);
	}

	private TransformGroup make_Plate(final double faktor, final float div) {
		TexturedPlane plate01;
		final TransformGroup ret = new TransformGroup();
		final Transform3D t3d01 = new Transform3D();
		final Appearance app_plate = new Appearance();
		boolean stdTexture = false;

		if (Textur == "") {
			Textur = "grass01.jpg";
			stdTexture = true;
		}

		plate01 = new TexturedPlane(new Point3f(-Std_Breite, 0.0f, -Std_Breite),
				new Point3f(-Std_Breite, 0.0f, Std_Breite),
				new Point3f(Std_Breite, 0.0f, Std_Breite),
				new Point3f(Std_Breite, 0.0f, -Std_Breite),
				Hauptpfad_Grafik + Textur, div);

		ret.addChild(plate01);

		t3d01.setTranslation(new Vector3d(0.0, 0.08, 0.0));
		ret.setTransform(t3d01);

		return ret;
	}

	private void make_Building(final double faktor, final String Model,
			final double Drehwinkel, final double Vergrößerung,
			final double y_Verschiebung, final float div) {
		final TransformGroup building_TG = new TransformGroup();
		final Transform3D t3d01 = new Transform3D();
		final Transform3D t3d02 = new Transform3D();

		final Object_3D building = new Object_3D(Model, Vergrößerung);

		t3d01.rotY(Drehwinkel / 180 * Math.PI);
		t3d02.setTranslation(new Vector3d(0.0, 0.04 + y_Verschiebung, 0.0));
		t3d01.mul(t3d01, t3d02);
		building.setTransform(t3d01);

		building_TG.addChild(building);
		building_TG.addChild(make_Plate(faktor, div));

		addChild(building_TG);
	}

	private static void Textur_laden(String Textur_Pfad,
			final Appearance appear) {

		/************************************************************************/
		/* benötigte Texturen werden geladen und den betreffenden Appearances */
		/* zugewiesen */

		TextureLoader Lader;
		ImageComponent2D Textur_Bild;
		Texture2D Textur;

		Textur_Pfad = Hauptpfad_Grafik + Textur_Pfad;

		try {
			Lader = new TextureLoader(Textur_Pfad, null);
			Textur_Bild = Lader.getImage();

			Textur = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
					Textur_Bild.getWidth(), Textur_Bild.getHeight());
			Textur.setImage(0, Textur_Bild);

			appear.setTexture(Textur);
		} catch (final Exception e) {
			System.err.println("Fehler beim Laden von Textur " + Textur_Pfad
					+ " !! - " + e);
		}
		/************************************************************************/
	}
}