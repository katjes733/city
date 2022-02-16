package com.katjes.city.graphics;

import javax.media.j3d.Appearance;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;

public class Background extends TransformGroup {
	private static final float Std_Spur_Breite = 2.5f;
	private static final float Std_Höhe = 5.0f;

	public Background(final int Einheiten_x, final int Einheiten_y,
			final Appearance Norden, final Appearance Süden,
			final Appearance Westen, final Appearance Osten) {
		this.addChild(make_Norden(Einheiten_x / 2, Einheiten_y / 2, Norden));
		this.addChild(make_Süden(Einheiten_x / 2, Einheiten_y / 2, Süden));
		this.addChild(make_Westen(Einheiten_x / 2, Einheiten_y / 2, Westen));
		this.addChild(make_Osten(Einheiten_x / 2, Einheiten_y / 2, Osten));
	}

	public Background(final int Einheiten_x, final int Einheiten_y,
			final Appearance Norden, final Appearance Süden,
			final Appearance Westen, final Appearance Osten,
			final Appearance Himmel) {
		this.addChild(make_Norden(Einheiten_x / 2, Einheiten_y / 2, Norden));
		this.addChild(make_Süden(Einheiten_x / 2, Einheiten_y / 2, Süden));
		this.addChild(make_Westen(Einheiten_x / 2, Einheiten_y / 2, Westen));
		this.addChild(make_Osten(Einheiten_x / 2, Einheiten_y / 2, Osten));
		this.addChild(make_Himmel(Einheiten_x / 2, Einheiten_y / 2, Himmel));
	}

	private TransformGroup make_Norden(final int Vers_x, final int Vers_y,
			final Appearance app) {
		final TransformGroup tg01 = new TransformGroup();
		final float x = Vers_x - 0.5f;
		final float y = Vers_y - 0.5f;

		tg01.addChild(new TexturedPlane(
				new Point3f(-x * Std_Spur_Breite * 2.0f, 0,
						-y * Std_Spur_Breite * 2.0f),
				new Point3f(x * Std_Spur_Breite * 2.0f, 0,
						-y * Std_Spur_Breite * 2.0f),
				new Point3f(x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						-y * Std_Spur_Breite * 2.0f),
				new Point3f(-x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						-y * Std_Spur_Breite * 2.0f),
				app));

		return tg01;
	}

	private TransformGroup make_Süden(final int Vers_x, final int Vers_y,
			final Appearance app) {
		final TransformGroup tg01 = new TransformGroup();
		final float x = Vers_x - 0.5f;
		final float y = Vers_y - 0.5f;

		tg01.addChild(new TexturedPlane(
				new Point3f(x * Std_Spur_Breite * 2.0f, 0,
						y * Std_Spur_Breite * 2.0f),
				new Point3f(-x * Std_Spur_Breite * 2.0f, 0,
						y * Std_Spur_Breite * 2.0f),
				new Point3f(-x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						y * Std_Spur_Breite * 2.0f),
				new Point3f(x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						y * Std_Spur_Breite * 2.0f),
				app));

		return tg01;
	}

	private TransformGroup make_Westen(final int Vers_x, final int Vers_y,
			final Appearance app) {
		final TransformGroup tg01 = new TransformGroup();
		final float x = Vers_x - 0.5f;
		final float y = Vers_y - 0.5f;

		tg01.addChild(new TexturedPlane(
				new Point3f(-x * Std_Spur_Breite * 2.0f, 0,
						y * Std_Spur_Breite * 2.0f),
				new Point3f(-x * Std_Spur_Breite * 2.0f, 0,
						-y * Std_Spur_Breite * 2.0f),
				new Point3f(-x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						-y * Std_Spur_Breite * 2.0f),
				new Point3f(-x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						y * Std_Spur_Breite * 2.0f),
				app));

		return tg01;
	}

	private TransformGroup make_Osten(final int Vers_x, final int Vers_y,
			final Appearance app) {
		final TransformGroup tg01 = new TransformGroup();
		final float x = Vers_x - 0.5f;
		final float y = Vers_y - 0.5f;

		tg01.addChild(new TexturedPlane(
				new Point3f(x * Std_Spur_Breite * 2.0f, 0,
						-y * Std_Spur_Breite * 2.0f),
				new Point3f(x * Std_Spur_Breite * 2.0f, 0,
						y * Std_Spur_Breite * 2.0f),
				new Point3f(x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						y * Std_Spur_Breite * 2.0f),
				new Point3f(x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						-y * Std_Spur_Breite * 2.0f),
				app));

		return tg01;
	}

	private TransformGroup make_Himmel(final int Vers_x, final int Vers_y,
			final Appearance app) {
		final TransformGroup tg01 = new TransformGroup();
		final float x = Vers_x - 0.5f;
		final float y = Vers_y - 0.5f;

		tg01.addChild(new TexturedPlane(new Point3f(x * Std_Spur_Breite * 2.0f,
				Std_Höhe * Std_Spur_Breite * 2.0f, y * Std_Spur_Breite * 2.0f),
				new Point3f(-x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						y * Std_Spur_Breite * 2.0f),
				new Point3f(-x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						-y * Std_Spur_Breite * 2.0f),
				new Point3f(x * Std_Spur_Breite * 2.0f,
						Std_Höhe * Std_Spur_Breite * 2.0f,
						-y * Std_Spur_Breite * 2.0f),
				app));

		return tg01;
	}
}