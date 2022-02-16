package com.katjes.city.objects.buildings;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.katjes.city.graphics.TexturedPlane;

public class HouseDual extends TransformGroup {
	private static final float Std_Spur_Breite = 2.5f;
	private static final double Bürgersteig_d = 0.161;
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

	public TexturedPlane Tür_TP01;
	public TexturedPlane Tür_TP02;

	public HouseDual() {
		this.Garten = null;
		this.Gehweg = null;
		this.Zaun = null;
		this.Wand1Fenster = null;
		this.Tür = null;
		this.TürWand = null;
		this.Wand2Fenster = null;
		this.Wand3Fenster = null;
		this.Wand0Fenster = null;
		this.Decke = null;
		this.Dach = null;

		// this.addChild(make_Haus());
	}

	public HouseDual(final boolean Bürgersteig, final int x_Raster,
			final int y_Raster, final int Orientation, final Appearance Garten,
			final Appearance Gehweg, final Appearance Zaun,
			final Appearance Wand1Fenster, final Appearance Tür,
			final Appearance TürWand, final Appearance Wand2Fenster,
			final Appearance Wand3Fenster, final Appearance Wand0Fenster,
			final Appearance Decke, final Appearance Dach) {
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

		this.addChild(make_Haus(Bürgersteig, x_Raster, y_Raster, Orientation));
	}

	private TransformGroup make_Haus(final boolean Bürgersteig,
			final int x_Raster, final int y_Raster, final int Orientation) {
		final TransformGroup tg00 = new TransformGroup();
		final TransformGroup tg01 = new TransformGroup();
		final TransformGroup tg02 = new TransformGroup();
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
		tg01.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 5f),
				new Point3f(-2.5f, 1f, 5f), new Point3f(-2.5f, 1f, 4f),
				new Point3f(-2.5f, 0f, 4f), Zaun)); // Zaun
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
		tg01.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 5f),
				new Point3f(2.5f, 0, 4f), new Point3f(2.5f, 1f, 4f),
				new Point3f(2.5f, 1f, 5f), Zaun)); // Zaun
		tg01.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 4f),
				new Point3f(-0.5f, 0, 4f), new Point3f(-0.5f, 2.5f, 4f),
				new Point3f(-2.5f, 2.5f, 4f), Wand1Fenster)); // Wand mit 1 Fenster
		Tür_TP02 = new TexturedPlane(new Point3f(-0.5f, 0f, 4f),
				new Point3f(0.5f, 0, 4f), new Point3f(0.5f, 2f, 4f),
				new Point3f(-0.5f, 2f, 4f), Tür); // Tür
		tg01.addChild(Tür_TP02);
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
			t3d01.setTranslation(new Vector3d(-2.5, Bürgersteig_d, 0.0));
		} else {
			t3d01.setTranslation(new Vector3d(-2.5, 0.0, 0.0));
		}

		t3d02.setScale(new Vector3d(1.0, 1.0, 1.0));
		t3d01.mul(t3d01, t3d02);
		tg01.setTransform(t3d01);

		tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 5f),
				new Point3f(-0.5f, 0f, 5f), new Point3f(-0.5f, 0f, 4f),
				new Point3f(-2.5f, 0f, 4f), Garten));// Garten 1
		tg02.addChild(new TexturedPlane(new Point3f(-0.5f, 0f, 5f),
				new Point3f(0.5f, 0f, 5f), new Point3f(0.5f, 0f, 4f),
				new Point3f(-0.5f, 0f, 4f), Gehweg));// Gehweg
		tg02.addChild(new TexturedPlane(new Point3f(0.5f, 0f, 5f),
				new Point3f(2.5f, 0f, 5f), new Point3f(2.5f, 0f, 4f),
				new Point3f(0.5f, 0f, 4f), Garten)); // Garten 2
		tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 5f),
				new Point3f(-2.5f, 1f, 5f), new Point3f(-2.5f, 1f, 4f),
				new Point3f(-2.5f, 0f, 4f), Zaun)); // Zaun
		tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 5f),
				new Point3f(-1.5f, 0f, 5f), new Point3f(-1.5f, 1f, 5f),
				new Point3f(-2.5f, 1f, 5f), Zaun)); // Zaun
		tg02.addChild(new TexturedPlane(new Point3f(-1.5f, 0f, 5f),
				new Point3f(-0.5f, 0f, 5f), new Point3f(-0.5f, 1f, 5f),
				new Point3f(-1.5f, 1f, 5f), Zaun)); // Zaun
		tg02.addChild(new TexturedPlane(new Point3f(0.5f, 0f, 5f),
				new Point3f(1.5f, 0, 5f), new Point3f(1.5f, 1f, 5f),
				new Point3f(0.5f, 1f, 5f), Zaun)); // Zaun
		tg02.addChild(new TexturedPlane(new Point3f(1.5f, 0f, 5f),
				new Point3f(2.5f, 0, 5f), new Point3f(2.5f, 1f, 5f),
				new Point3f(1.5f, 1f, 5f), Zaun)); // Zaun
		tg02.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 5f),
				new Point3f(2.5f, 0, 4f), new Point3f(2.5f, 1f, 4f),
				new Point3f(2.5f, 1f, 5f), Zaun)); // Zaun
		tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, 4f),
				new Point3f(-0.5f, 0, 4f), new Point3f(-0.5f, 2.5f, 4f),
				new Point3f(-2.5f, 2.5f, 4f), Wand1Fenster)); // Wand mit 1 Fenster
		Tür_TP01 = new TexturedPlane(new Point3f(-0.5f, 0f, 4f),
				new Point3f(0.5f, 0, 4f), new Point3f(0.5f, 2f, 4f),
				new Point3f(-0.5f, 2f, 4f), Tür); // Tür
		tg02.addChild(Tür_TP01);
		tg02.addChild(new TexturedPlane(new Point3f(-0.5f, 2f, 4f),
				new Point3f(0.5f, 2, 4f), new Point3f(0.5f, 2.5f, 4f),
				new Point3f(-0.5f, 2.5f, 4f), TürWand)); // Wand über Tür
		tg02.addChild(new TexturedPlane(new Point3f(0.5f, 0f, 4f),
				new Point3f(2.5f, 0, 4f), new Point3f(2.5f, 2.5f, 4f),
				new Point3f(0.5f, 2.5f, 4f), Wand1Fenster)); // Wand mit 1 Fenster
		tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 0f, -5f),
				new Point3f(-2.5f, 0, 4f), new Point3f(-2.5f, 2.5f, 4f),
				new Point3f(-2.5f, 2.5f, -5f), Wand2Fenster)); // Seiten-Wand mit 2 Fenster
		tg02.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 4f),
				new Point3f(2.5f, 0, -5f), new Point3f(2.5f, 2.5f, -5f),
				new Point3f(2.5f, 2.5f, 4f), Wand2Fenster)); // Seiten-Wand mit 2 Fenster
		tg02.addChild(new TexturedPlane(new Point3f(2.5f, 0f, -5f),
				new Point3f(-2.5f, 0, -5f), new Point3f(-2.5f, 2.5f, -5f),
				new Point3f(2.5f, 2.5f, -5f), Wand0Fenster)); // Rück-Wand
		tg02.addChild(new TexturedPlane(new Point3f(2.5f, 2.5f, 4f),
				new Point3f(2.5f, 2.5f, -5f), new Point3f(-2.5f, 2.5f, -5f),
				new Point3f(-2.5f, 2.5f, 4f), Decke, 4.0f)); // Zwischendecke 1.Etage
		tg02.addChild(new TexturedPlane(new Point3f(2.5f, 0f, 4f),
				new Point3f(2.5f, 0f, -5f), new Point3f(-2.5f, 0f, -5f),
				new Point3f(-2.5f, 0f, 4f), Decke, 4.0f)); // Zwischendecke Erdgeschoss
		tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 2.5f, 4f),
				new Point3f(2.5f, 2.5f, 4f), new Point3f(2.5f, 5f, 4f),
				new Point3f(-2.5f, 5f, 4f), Wand3Fenster)); // Front-Wand mit 3 Fenster 1.ETG
		tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 2.5f, -5f),
				new Point3f(-2.5f, 2.5f, 4f), new Point3f(-2.5f, 5f, 4f),
				new Point3f(-2.5f, 5f, -5f), Wand2Fenster)); // Seiten-Wand mit 2 Fenster 1.ETG
		tg02.addChild(new TexturedPlane(new Point3f(2.5f, 2.5f, 4f),
				new Point3f(2.5f, 2.5f, -5f), new Point3f(2.5f, 5f, -5f),
				new Point3f(2.5f, 5f, 4f), Wand2Fenster)); // Seiten-Wand mit 2 Fenster 1.ETG
		tg02.addChild(new TexturedPlane(new Point3f(2.5f, 2.5f, -5f),
				new Point3f(-2.5f, 2.5f, -5f), new Point3f(-2.5f, 5f, -5f),
				new Point3f(2.5f, 5f, -5f), Wand0Fenster)); // Rück-Wand 1.ETG
		tg02.addChild(new TexturedPlane(new Point3f(2.5f, 5f, 4f),
				new Point3f(2.5f, 5f, -5f), new Point3f(-2.5f, 5f, -5f),
				new Point3f(-2.5f, 5f, 4f), Decke, 4.0f)); // Zwischendecke 2.Etage
		tg02.addChild(new TexturedPlane(new Point3f(-2.6f, 4.75f, 4.25f),
				new Point3f(2.6f, 4.75f, 4.25f), new Point3f(2.6f, 7.5f, 1.5f),
				new Point3f(-2.6f, 7.5f, 1.5f), Dach, 8.0f)); // Dach vorne
		tg02.addChild(new TexturedPlane(new Point3f(2.6f, 4.75f, -5.25f),
				new Point3f(-2.6f, 4.75f, -5.25f),
				new Point3f(-2.6f, 7.5f, -2.5f), new Point3f(2.6f, 7.5f, -2.5f),
				Dach, 8.0f)); // Dach hinten
		tg02.addChild(new TexturedPlane(new Point3f(-2.6f, 7.5f, 1.5f),
				new Point3f(2.6f, 7.5f, 1.5f), new Point3f(2.6f, 7.5f, -2.5f),
				new Point3f(-2.6f, 7.5f, -2.5f), Dach, 8.0f)); // Dach oben
		tg02.addChild(new TexturedPlane(new Point3f(-2.5f, 5f, -5f),
				new Point3f(-2.5f, 5f, 4f), new Point3f(-2.5f, 7.5f, 1.5f),
				new Point3f(-2.5f, 7.5f, -2.5f), Wand0Fenster)); // Seitenwand Dach
		tg02.addChild(new TexturedPlane(new Point3f(2.5f, 5f, 4f),
				new Point3f(2.5f, 5f, -5f), new Point3f(2.5f, 7.5f, -2.5f),
				new Point3f(2.5f, 7.5f, 1.5f), Wand0Fenster)); // Seitenwand Dach

		if (Bürgersteig) {
			t3d01.setTranslation(new Vector3d(2.5, Bürgersteig_d, 0.0));
		} else {
			t3d01.setTranslation(new Vector3d(2.5, 0.0, 0.0));
		}

		t3d02.setScale(new Vector3d(1.0, 1.0, 1.0));
		t3d01.mul(t3d01, t3d02);
		tg02.setTransform(t3d01);

		tg00.addChild(tg01);
		tg00.addChild(tg02);

		t3d02.setScale(new Vector3d(0.5, 0.5, 0.5));
		t3d01.setTranslation(new Vector3d(0.0, 0.0, 0.0));
		t3d01.mul(t3d01, t3d02);

		tg00.setTransform(t3d01);

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