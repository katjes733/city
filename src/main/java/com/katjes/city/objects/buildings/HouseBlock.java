package com.katjes.city.objects.buildings;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

public class HouseBlock extends TransformGroup {
	private static final float Std_Spur_Breite = 2.5f;
	private static final double Bürgersteig_d = 0.161;
	private final Appearance Front_E1_1;
	private final Appearance Front_ab2_1;
	private final Appearance Front_E1_2;
	private final Appearance Front_ab2_2;
	private final Appearance Dach;

	public HouseBlock() {
		this.Front_E1_1 = null;
		this.Front_ab2_1 = null;
		this.Front_E1_2 = null;
		this.Front_ab2_2 = null;
		this.Dach = null;
	}

	public HouseBlock(final boolean Bürgersteig, final int x_Raster,
			final int y_Raster, final int Orientation, final int Groesse,
			final Appearance Front_E1_1, final Appearance Front_ab2_1,
			final Appearance Dach) {
		this.Front_E1_1 = Front_E1_1;
		this.Front_ab2_1 = Front_ab2_1;
		this.Front_E1_2 = null;
		this.Front_ab2_2 = null;
		this.Dach = Dach;

	}

	public HouseBlock(final boolean Bürgersteig, final int x_Raster,
			final int y_Raster, final int Orientation, final int Groesse,
			final Appearance Front_E1_1, final Appearance Front_ab2_1,
			final Appearance Front_E1_2, final Appearance Front_ab2_2,
			final Appearance Dach) {
		this.Front_E1_1 = Front_E1_1;
		this.Front_ab2_1 = Front_ab2_1;
		this.Front_E1_2 = Front_E1_2;
		this.Front_ab2_2 = Front_ab2_2;
		this.Dach = Dach;
	}

	private TransformGroup makeBuilding(final boolean Bürgersteig,
			final int x_Raster, final int y_Raster, final int Orientation,
			final int Groesse) {
		int groesse = 0;
		if (Groesse > 4) {
			groesse = 4;
		} else if (Groesse < 2) {
			groesse = 2;
		} else {
			groesse = Groesse;
		}

		final TransformGroup tg00 = new TransformGroup();
		final TransformGroup door[] = new TransformGroup[groesse * groesse + 1];
		door[0] = null;

		final Transform3D t3d01 = new Transform3D();
		final Transform3D t3d02 = new Transform3D();
		final Transform3D t3d03 = new Transform3D();

		return tg00;
	}

}