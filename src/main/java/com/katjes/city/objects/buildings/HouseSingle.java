package com.katjes.city.objects.buildings;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.katjes.city.graphics.TexturedPlane;

public class HouseSingle extends TransformGroup {
	private static final float Std_Spur_Breite = 2.5f;
	private static final double Bürgersteig_d = 0.161;

	private int i = 0; // Zähler
	private float f = 0.0f;

	private final Appearance Garten;
	private final Appearance Gehweg;
	private final Appearance Zaun;
	private final Appearance Wand1Fenster;
	private final Appearance Tür;
	private final Appearance TürWand;
	private final Appearance Wand2Fenster;
	private final Appearance Wand3Fenster;
	private final Appearance Wand0Fenster;
	private final Appearance Decke;
	private final Appearance Dach;
	private final Appearance Tor;
	private final Appearance Baum01;

	public TexturedPlane Tür_TP;
	public TexturedPlane Tor_TP;

	public HouseSingle(final boolean Bürgersteig, final int Typ,
			final int x_Raster, final int y_Raster, final int Orientation,
			final Appearance Garten, final Appearance Gehweg,
			final Appearance Zaun, final Appearance Wand1Fenster,
			final Appearance Tür, final Appearance TürWand,
			final Appearance Wand2Fenster, final Appearance Wand3Fenster,
			final Appearance Wand0Fenster, final Appearance Decke,
			final Appearance Dach, final Appearance Tor,
			final Appearance Baum01) {
		this.Garten = Garten;
		this.Gehweg = Gehweg;
		this.Zaun = Zaun;
		this.Wand1Fenster = Wand1Fenster;
		this.Tür = Tür;
		this.TürWand = TürWand;
		this.Wand2Fenster = Wand2Fenster;
		this.Wand3Fenster = Wand3Fenster;
		this.Wand0Fenster = Wand0Fenster;
		this.Decke = Decke;
		this.Dach = Dach;
		this.Tor = Tor;
		this.Baum01 = Baum01;

		this.addChild(
				make_Haus(Bürgersteig, Typ, x_Raster, y_Raster, Orientation));
	}

	private TransformGroup make_Haus(final boolean Bürgersteig, final int Typ,
			final int x_Raster, final int y_Raster, final int Orientation) {
		final TransformGroup tg00 = new TransformGroup(); // Return TG
		final TransformGroup tg01 = new TransformGroup(); // Helping TG
		final TransformGroup tg02 = new TransformGroup(); // Helping TG
		final Transform3D t3d01 = new Transform3D();
		final Transform3D t3d02 = new Transform3D();
		final Transform3D t3d03 = new Transform3D();

		tg01.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 5f),
				new Point3f(-0.5f, 0f, 5f), new Point3f(-0.5f, 0f, 4f),
				new Point3f(-2.5f, 0f, 4f), Garten));// Garten 1
		tg01.addChild(new TexturedPlane(new Point3f(-0.5f, 0f, 5f),
				new Point3f(0.5f, 0f, 5f), new Point3f(0.5f, 0f, 4f),
				new Point3f(-0.5f, 0f, 4f), Gehweg));// Gehweg
		tg01.addChild(new TexturedPlane(new Point3f(0.5f, 0f, 5f),
				new Point3f(2.5f, 0f, 5f), new Point3f(2.5f, 0f, 4f),
				new Point3f(0.5f, 0f, 4f), Garten)); // Garten 2
		if (Typ != 2 && Typ != 4 && Typ != 5) {
			tg01.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 5f),
					new Point3f(-2.5f, 1f, 5f), new Point3f(-2.5f, 1f, 4f),
					new Point3f(-2.5f, 0f, 4f), Zaun)); // Zaun
		}
		tg01.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 5f),
				new Point3f(-1.5f, 0f, 5f), new Point3f(-1.5f, 1f, 5f),
				new Point3f(-2.5f, 1f, 5f), Zaun)); // Zaun
		tg01.addChild(new TexturedPlane(new Point3f(-1.5f, 0f, 5f),
				new Point3f(-0.5f, 0f, 5f), new Point3f(-0.5f, 1f, 5f),
				new Point3f(-1.5f, 1f, 5f), Zaun)); // Zaun
		tg01.addChild(new TexturedPlane(new Point3f(0.5f, 0f, 5f),
				new Point3f(1.5f, 0, 5f), new Point3f(1.5f, 1f, 5f),
				new Point3f(0.5f, 1f, 5f), Zaun)); // Zaun
		tg01.addChild(new TexturedPlane(new Point3f(1.5f, 0f, 5f),
				new Point3f(2.5f, 0, 5f), new Point3f(2.5f, 1f, 5f),
				new Point3f(1.5f, 1f, 5f), Zaun)); // Zaun
		if (Typ != 1 && Typ != 3 && Typ != 5) {
			tg01.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 5f),
					new Point3f(2.5f, 0, 4f), new Point3f(2.5f, 1f, 4f),
					new Point3f(2.5f, 1f, 5f), Zaun)); // Zaun
		}
		tg01.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 4f),
				new Point3f(-0.5f, 0, 4f), new Point3f(-0.5f, 2.5f, 4f),
				new Point3f(-2.5f, 2.5f, 4f), Wand1Fenster)); // Wand mit 1 Fenster
		Tür_TP = new TexturedPlane(new Point3f(-0.5f, 0f, 4f),
				new Point3f(0.5f, 0, 4f), new Point3f(0.5f, 2f, 4f),
				new Point3f(-0.5f, 2f, 4f), Tür); // Tür
		tg01.addChild(Tür_TP);
		tg01.addChild(new TexturedPlane(new Point3f(-0.5f, 2f, 4f),
				new Point3f(0.5f, 2, 4f), new Point3f(0.5f, 2.5f, 4f),
				new Point3f(-0.5f, 2.5f, 4f), TürWand)); // Wand über Tür
		tg01.addChild(new TexturedPlane(new Point3f(0.5f, 0f, 4f),
				new Point3f(2.5f, 0, 4f), new Point3f(2.5f, 2.5f, 4f),
				new Point3f(0.5f, 2.5f, 4f), Wand1Fenster)); // Wand mit 1 Fenster
		tg01.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, -5f),
				new Point3f(-2.5f, 0, 4f), new Point3f(-2.5f, 2.5f, 4f),
				new Point3f(-2.5f, 2.5f, -5f), Wand2Fenster)); // Seiten-Wand mit 2 Fenster
		tg01.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 4f),
				new Point3f(2.5f, 0, -5f), new Point3f(2.5f, 2.5f, -5f),
				new Point3f(2.5f, 2.5f, 4f), Wand2Fenster)); // Seiten-Wand mit 2 Fenster
		tg01.addChild(new TexturedPlane(new Point3f(2.5f, 0f, -5f),
				new Point3f(-2.5f, 0, -5f), new Point3f(-2.5f, 2.5f, -5f),
				new Point3f(2.5f, 2.5f, -5f), Wand0Fenster)); // Rück-Wand
		tg01.addChild(new TexturedPlane(new Point3f(2.5f, 2.5f, 4f),
				new Point3f(2.5f, 2.5f, -5f), new Point3f(-2.5f, 2.5f, -5f),
				new Point3f(-2.5f, 2.5f, 4f), Decke, 4.0f)); // Zwischendecke 1.Etage
		tg01.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 4f),
				new Point3f(2.5f, 0f, -5f), new Point3f(-2.5f, 0f, -5f),
				new Point3f(-2.5f, 0f, 4f), Decke, 4.0f)); // Zwischendecke Erdgeschoss
		tg01.addChild(new TexturedPlane(new Point3f(-2.5f, 2.5f, 4f),
				new Point3f(2.5f, 2.5f, 4f), new Point3f(2.5f, 5f, 4f),
				new Point3f(-2.5f, 5f, 4f), Wand3Fenster)); // Front-Wand mit 3 Fenster 1.ETG
		tg01.addChild(new TexturedPlane(new Point3f(-2.5f, 2.5f, -5f),
				new Point3f(-2.5f, 2.5f, 4f), new Point3f(-2.5f, 5f, 4f),
				new Point3f(-2.5f, 5f, -5f), Wand2Fenster)); // Seiten-Wand mit 2 Fenster 1.ETG
		tg01.addChild(new TexturedPlane(new Point3f(2.5f, 2.5f, 4f),
				new Point3f(2.5f, 2.5f, -5f), new Point3f(2.5f, 5f, -5f),
				new Point3f(2.5f, 5f, 4f), Wand2Fenster)); // Seiten-Wand mit 2 Fenster 1.ETG
		tg01.addChild(new TexturedPlane(new Point3f(2.5f, 2.5f, -5f),
				new Point3f(-2.5f, 2.5f, -5f), new Point3f(-2.5f, 5f, -5f),
				new Point3f(2.5f, 5f, -5f), Wand0Fenster)); // Rück-Wand 1.ETG
		tg01.addChild(new TexturedPlane(new Point3f(2.5f, 5f, 4f),
				new Point3f(2.5f, 5f, -5f), new Point3f(-2.5f, 5f, -5f),
				new Point3f(-2.5f, 5f, 4f), Decke, 4.0f)); // Zwischendecke 2.Etage
		tg01.addChild(new TexturedPlane(new Point3f(-2.6f, 4.75f, 4.25f),
				new Point3f(2.6f, 4.75f, 4.25f), new Point3f(2.6f, 7.5f, 1.5f),
				new Point3f(-2.6f, 7.5f, 1.5f), Dach, 8.0f)); // Dach vorne
		tg01.addChild(new TexturedPlane(new Point3f(2.6f, 4.75f, -5.25f),
				new Point3f(-2.6f, 4.75f, -5.25f),
				new Point3f(-2.6f, 7.5f, -2.5f), new Point3f(2.6f, 7.5f, -2.5f),
				Dach, 8.0f)); // Dach hinten
		tg01.addChild(new TexturedPlane(new Point3f(-2.6f, 7.5f, 1.5f),
				new Point3f(2.6f, 7.5f, 1.5f), new Point3f(2.6f, 7.5f, -2.5f),
				new Point3f(-2.6f, 7.5f, -2.5f), Dach, 8.0f)); // Dach oben
		tg01.addChild(new TexturedPlane(new Point3f(-2.5f, 5f, -5f),
				new Point3f(-2.5f, 5f, 4f), new Point3f(-2.5f, 7.5f, 1.5f),
				new Point3f(-2.5f, 7.5f, -2.5f), Wand0Fenster)); // Seitenwand Dach
		tg01.addChild(new TexturedPlane(new Point3f(2.5f, 5f, 4f),
				new Point3f(2.5f, 5f, -5f), new Point3f(2.5f, 7.5f, -2.5f),
				new Point3f(2.5f, 7.5f, 1.5f), Wand0Fenster)); // Seitenwand Dach

		if (Bürgersteig) {
			if (Typ == 1 || Typ == 3) {
				t3d01.setTranslation(new Vector3d(-2.5, Bürgersteig_d, 0.0));
			} else if (Typ == 2 || Typ == 4) {
				t3d01.setTranslation(new Vector3d(2.5, Bürgersteig_d, 0.0));
			} else {
				t3d01.setTranslation(new Vector3d(0.0, Bürgersteig_d, 0.0));
			}
		} else {
			if (Typ == 1 || Typ == 3) {
				t3d01.setTranslation(new Vector3d(-2.5, 0.0, 0.0));
			} else if (Typ == 2 || Typ == 4) {
				t3d01.setTranslation(new Vector3d(2.5, 0.0, 0.0));
			} else {
				t3d01.setTranslation(new Vector3d(0.0, 0.0, 0.0));
			}
		}

		tg01.setTransform(t3d01);

		// mit Garage
		if (Typ == 2) {
			tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, -5f),
					new Point3f(-2.5f, 0f, 3f), new Point3f(-2.5f, 2.5f, 3f),
					new Point3f(-2.5f, 2.5f, -5f), Wand2Fenster)); // Seitenwand Garage links
			tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 3f),
					new Point3f(-2.5f, 0f, 4f), new Point3f(-2.5f, 1f, 4f),
					new Point3f(-2.5f, 1f, 3f), Zaun)); // Zaun Garage links
			tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 4f),
					new Point3f(-2.5f, 0f, 5f), new Point3f(-2.5f, 1f, 5f),
					new Point3f(-2.5f, 1f, 4f), Zaun)); // Zaun Garage links
		}
		if (Typ == 1) {
			tg02.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 3f),
					new Point3f(2.5f, 0f, -5f), new Point3f(2.5f, 2.5f, -5f),
					new Point3f(2.5f, 2.5f, 3f), Wand2Fenster)); // Seitenwand Garage rechts
			tg02.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 5f),
					new Point3f(2.5f, 0f, 4f), new Point3f(2.5f, 1f, 4f),
					new Point3f(2.5f, 1f, 5f), Zaun)); // Zaun Garage links
			tg02.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 4f),
					new Point3f(2.5f, 0f, 3f), new Point3f(2.5f, 1f, 3f),
					new Point3f(2.5f, 1f, 4f), Zaun)); // Zaun Garage links
		}
		if (Typ == 1 || Typ == 2) {
			tg02.addChild(new TexturedPlane(new Point3f(2.5f, 0f, -5f),
					new Point3f(-2.5f, 0f, -5f), new Point3f(-2.5f, 2.5f, -5f),
					new Point3f(2.5f, 2.5f, -5f), Wand0Fenster)); // Rückwand Garage
			Tor_TP = new TexturedPlane(new Point3f(-2.5f, 0f, 3f),
					new Point3f(2.5f, 0, 3f), new Point3f(2.5f, 2.5f, 3f),
					new Point3f(-2.5f, 2.5f, 3f), Tor); // Garagen Tor
			tg02.addChild(Tor_TP);
			tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 2.5f, 3.1f),
					new Point3f(2.5f, 2.5f, 3.1f), new Point3f(2.5f, 2.5f, -5f),
					new Point3f(-2.5f, 2.5f, -5f), Dach, 8.0f)); // Garagen Dach
			tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 5f),
					new Point3f(2.5f, 0f, 5f), new Point3f(2.5f, 0f, -5f),
					new Point3f(-2.5f, 0f, -5f), Gehweg, 5.0f)); // Einfahrt
		}

		// mit Garten
		if (Typ == 4) {
			for (i = -5; i <= -1; i++) {
				tg02.addChild(
						new TexturedPlane(new Point3f(i + 1 + 2.5f, 0f, -5f),
								new Point3f(i + 2.5f, 0f, -5f),
								new Point3f(i + 2.5f, 1.0f, -5f),
								new Point3f(i + 1 + 2.5f, 1f, -5f), Zaun)); // Zaun
				tg02.addChild(
						new TexturedPlane(new Point3f(i + 1 + 2.5f, 0f, 5f),
								new Point3f(i + 2.5f, 0f, 5f),
								new Point3f(i + 2.5f, 1.0f, 5f),
								new Point3f(i + 1 + 2.5f, 1f, 5f), Zaun)); // Zaun
				tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 5f),
						new Point3f(2.5f, 0f, 5f), new Point3f(2.5f, 0f, -5f),
						new Point3f(-2.5f, 0f, -5f), Garten, 5.0f)); // Garten
			}
		}
		if (Typ == 3) {
			for (i = 0; i <= 4; i++) {
				tg02.addChild(
						new TexturedPlane(new Point3f(i + 1 - 2.5f, 0f, -5f),
								new Point3f(i - 2.5f, 0f, -5f),
								new Point3f(i - 2.5f, 1f, -5f),
								new Point3f(i + 1 - 2.5f, 1f, -5f), Zaun)); // Zaun
				tg02.addChild(
						new TexturedPlane(new Point3f(i + 1 - 2.5f, 0f, 5f),
								new Point3f(i - 2.5f, 0f, 5f),
								new Point3f(i - 2.5f, 1f, 5f),
								new Point3f(i + 1 - 2.5f, 1f, 5f), Zaun)); // Zaun
				tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 5f),
						new Point3f(2.5f, 0f, 5f), new Point3f(2.5f, 0f, -5f),
						new Point3f(-2.5f, 0f, -5f), Garten, 5.0f)); // Garten
			}
		}
		if (Typ == 5) {
			tg02.addChild(new TexturedPlane(new Point3f(0f - 2.5f, 0f, -5f),
					new Point3f(-0.5f - 2.5f, 0f, -5f),
					new Point3f(-0.5f - 2.5f, 1f, -5f),
					new Point3f(0f - 2.5f, 1f, -5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(-0.5f - 2.5f, 0f, -5f),
					new Point3f(-1.5f - 2.5f, 0f, -5f),
					new Point3f(-1.5f - 2.5f, 1f, -5f),
					new Point3f(-0.5f - 2.5f, 1f, -5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(-1.5f - 2.5f, 0f, -5f),
					new Point3f(-2.5f - 2.5f, 0f, -5f),
					new Point3f(-2.5f - 2.5f, 1f, -5f),
					new Point3f(-1.5f - 2.5f, 1f, -5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(5f, 0f, -5f),
					new Point3f(4f, 0f, -5f), new Point3f(4f, 1f, -5f),
					new Point3f(5f, 1f, -5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(4f, 0f, -5f),
					new Point3f(3f, 0f, -5f), new Point3f(3f, 1f, -5f),
					new Point3f(4f, 1f, -5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(3f, 0f, -5f),
					new Point3f(2.5f, 0f, -5f), new Point3f(2.5f, 1f, -5f),
					new Point3f(3f, 1f, -5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(0f - 2.5f, 0f, 5f),
					new Point3f(-0.5f - 2.5f, 0f, 5f),
					new Point3f(-0.5f - 2.5f, 1f, 5f),
					new Point3f(0f - 2.5f, 1f, 5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(-0.5f - 2.5f, 0f, 5f),
					new Point3f(-1.5f - 2.5f, 0f, 5f),
					new Point3f(-1.5f - 2.5f, 1f, 5f),
					new Point3f(-0.5f - 2.5f, 1f, 5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(-1.5f - 2.5f, 0f, 5f),
					new Point3f(-2.5f - 2.5f, 0f, 5f),
					new Point3f(-2.5f - 2.5f, 1f, 5f),
					new Point3f(-1.5f - 2.5f, 1f, 5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 5f),
					new Point3f(3f, 0f, 5f), new Point3f(3f, 1f, 5f),
					new Point3f(2.5f, 1f, 5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(3f, 0f, 5f),
					new Point3f(4f, 0f, 5f), new Point3f(4f, 1f, 5f),
					new Point3f(3f, 1f, 5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(4f, 0f, 5f),
					new Point3f(5f, 0f, 5f), new Point3f(5f, 1f, 5f),
					new Point3f(4f, 1f, 5f), Zaun)); // Zaun
			tg02.addChild(new TexturedPlane(new Point3f(-5f, 0f, 5f),
					new Point3f(-2.5f, 0f, 5f), new Point3f(-2.5f, 0f, -5f),
					new Point3f(-5f, 0f, -5f), Garten, 3.0f)); // Garten
			tg02.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 5f),
					new Point3f(5f, 0f, 5f), new Point3f(5f, 0f, -5f),
					new Point3f(2.5f, 0f, -5f), Garten, 3.0f)); // Garten
		}

		if (Typ == 4 || Typ == 5) {
			for (i = -5; i <= 4; i++) {
				if (Typ == 5) {
					f = 2.0f;
				} else {
					f = 1.0f;
				}
				tg02.addChild(new TexturedPlane(new Point3f(-2.5f * f, 0f, i),
						new Point3f(-2.5f * f, 0f, i + 1),
						new Point3f(-2.5f * f, 1f, i + 1),
						new Point3f(-2.5f * f, 1f, i), Zaun)); // Zaun
			}
		}

		if (Typ == 3 || Typ == 5) {
			for (i = -5; i <= 4; i++) {
				if (Typ == 5) {
					f = 2.0f;
				} else {
					f = 1.0f;
				}
				tg02.addChild(new TexturedPlane(new Point3f(2.5f * f, 0f, i),
						new Point3f(2.5f * f, 0f, i + 1),
						new Point3f(2.5f * f, 1f, i + 1),
						new Point3f(2.5f * f, 1f, i), Zaun)); // Zaun
			}
		}

		if (Bürgersteig) {
			if (Typ == 1 || Typ == 3) {
				t3d02.setTranslation(new Vector3d(2.5, Bürgersteig_d, 0.0));
			} else if (Typ == 2 || Typ == 4) {
				t3d02.setTranslation(new Vector3d(-2.5, Bürgersteig_d, 0.0));
			} else {
				t3d02.setTranslation(new Vector3d(0.0, Bürgersteig_d, 0.0));
			}
		} else {
			if (Typ == 1 || Typ == 3) {
				t3d02.setTranslation(new Vector3d(2.5, 0.0, 0.0));
			} else if (Typ == 2 || Typ == 4) {
				t3d02.setTranslation(new Vector3d(-2.5, 0.0, 0.0));
			} else {
				t3d02.setTranslation(new Vector3d(0.0, 0.0, 0.0));
			}
		}

		tg02.setTransform(t3d02);

		tg00.addChild(tg01);
		tg00.addChild(tg02);

		t3d01.set(Transform3D.ZERO);
		t3d01.setScale(new Vector3d(0.5, 0.5, 0.5));

		// tg00.setTransform(t3d02);

		/*
		 * t3d01.set(Transform3D.ZERO); t3d02.set(Transform3D.ZERO); t3d03.set(Transform3D.ZERO);
		 */

		switch (Orientation) {
		case 1: {
			t3d02.rotY(0.0);
			t3d01.mul(t3d01, t3d02);
			t3d03.setTranslation(new Vector3d(x_Raster * Std_Spur_Breite * 4,
					0.0, y_Raster * Std_Spur_Breite * 4));
			t3d01.mul(t3d01, t3d03);
			tg00.setTransform(t3d01);

			break;
		}
		case 2: {
			t3d02.rotY(Math.PI / 2);
			t3d01.mul(t3d01, t3d02);
			tg00.setTransform(t3d01);
			t3d03.setTranslation(new Vector3d(-y_Raster * Std_Spur_Breite * 4,
					0.0, x_Raster * Std_Spur_Breite * 4));
			t3d01.mul(t3d01, t3d03);
			tg00.setTransform(t3d01);

			break;
		}
		case 3: {
			t3d02.rotY(Math.PI);
			t3d01.mul(t3d01, t3d02);
			tg00.setTransform(t3d01);
			t3d03.setTranslation(new Vector3d(-x_Raster * Std_Spur_Breite * 4,
					0.0, -y_Raster * Std_Spur_Breite * 4));
			t3d01.mul(t3d01, t3d03);
			tg00.setTransform(t3d01);

			break;
		}
		case 4: {
			t3d02.rotY(Math.PI * 3 / 2);
			t3d01.mul(t3d01, t3d02);
			tg00.setTransform(t3d01);
			t3d03.setTranslation(new Vector3d(y_Raster * Std_Spur_Breite * 4,
					0.0, -x_Raster * Std_Spur_Breite * 4));
			t3d01.mul(t3d01, t3d03);
			tg00.setTransform(t3d01);

			break;
		}
		}
		return tg00;
	}

}