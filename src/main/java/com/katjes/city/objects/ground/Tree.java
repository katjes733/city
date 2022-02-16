package com.katjes.city.objects.ground;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class Tree extends TransformGroup implements Runnable {
	private static TransformGroup[] tg;
	private static TransformGroup leafs;
	private static TransformGroup TGret;
	private static int Elements;
	private static double StartWinkel;
	private static double Winkel;
	private static float diameter;
	private static double x = 0.0;
	private static double y = 0.0;
	private static int intensityOfWind = 0;
	private static int complexity = 2;

	public Tree(final int complexity, final int Elements) {
		this.complexity = complexity;
		TGret = new TransformGroup();
		make_Tree(Elements, 5.0, (float) 0.5, null, null);
		this.addChild(TGret);
	}

	public Tree(final int complexity, final int Elements,
			final Appearance bark) {
		this.complexity = complexity;
		TGret = new TransformGroup();
		make_Tree(Elements, 5.0, (float) 0.5, bark, null);
		this.addChild(TGret);
	}

	public Tree(final int complexity, final int Elements, final double Winkel,
			final Appearance bark) {
		this.complexity = complexity;
		TGret = new TransformGroup();
		make_Tree(Elements, Winkel, (float) 0.5, bark, null);
		this.addChild(TGret);
	}

	public Tree(final int complexity, final int Elements, final double Winkel,
			final Appearance bark, final Appearance leaf) {
		this.complexity = complexity;
		TGret = new TransformGroup();
		make_Tree(Elements, Winkel, (float) 0.5, bark, leaf);
		this.addChild(TGret);
	}

	private void make_Tree(final int Elements, final double Winkel,
			final float diameter, final Appearance bark,
			final Appearance leaf) {
		tg = new TransformGroup[Elements];
		leafs = new TransformGroup();
		double f1 = 0.0f;
		double f2 = 0.0f;
		this.Elements = Elements;
		StartWinkel = Winkel;
		this.Winkel = Winkel;
		this.diameter = diameter;

		leafs.addChild(new Plant(complexity, 4.0, 1.5, 0.0, 0.0, leaf));
		leafs.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		leafs.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		for (int i = 0; i <= Elements; i++) {
			if (i < Elements) {
				final Cylinder c = new Cylinder(diameter / 4.0f, 0.5f,
						Primitive.GENERATE_TEXTURE_COORDS
								+ Primitive.GENERATE_NORMALS,
						5, 5, bark);
				tg[i] = new TransformGroup();
				tg[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				tg[i].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				tg[i].addChild(c);
				f1 -= Math.sin(Math.toRadians(i * Winkel)) / 2.0;
				if (this.Winkel > 0) {
					f2 -= Math.sin(Math.toRadians(i * Winkel)) * diameter / 4.0;
				} else {
					f2 += Math.sin(Math.toRadians(i * Winkel)) * diameter / 4.0;
				}
				final Transform3D t3d01 = new Transform3D();
				final Transform3D t3d02 = new Transform3D();
				final Transform3D t3d03 = new Transform3D();
				t3d01.setTranslation(
						new Vector3d(f1, 0.25 + (double) i / 2 + f2, 0.0));
				t3d02.rotZ(Math.toRadians(-i * Winkel));
				t3d03.mul(t3d02, t3d01);
				tg[i].setTransform(t3d03);
				TGret.addChild(tg[i]);
			} else {
				// f1-=(Math.sin(Math.toRadians((i-1)*Winkel)))/2.0;
				if (this.Winkel > 0) {
					f2 -= Math.sin(Math.toRadians((i - 1) * Winkel)) * diameter
							/ 4.0;
				} else {
					f2 += Math.sin(Math.toRadians((i - 1) * Winkel)) * diameter
							/ 4.0;
				}
				final Transform3D t3d01 = new Transform3D();
				final Transform3D t3d02 = new Transform3D();
				final Transform3D t3d03 = new Transform3D();
				t3d01.setTranslation(
						new Vector3d(f1, -0.25 + (double) i / 2 + f2, 0.0));
				t3d02.rotZ(Math.toRadians(-(i - 1) * Winkel));
				t3d03.mul(t3d02, t3d01);
				leafs.setTransform(t3d03);
				TGret.addChild(leafs);
			}
		}
	}

	public static void windItRelative(final double WinkelX) {
		final double WinkelRelative = WinkelX / Elements;
		Winkel += WinkelRelative;
		float f1 = 0.0f;
		float f2 = 0.0f;
		for (int i = 0; i <= Elements; i++) {
			if (i < Elements) {
				f1 -= (float) Math.sin(Math.toRadians(i * Winkel)) / 2.0f;
				if (Winkel > 0) {
					f2 -= (float) Math.sin(Math.toRadians(i * Winkel))
							* diameter / 4.0f;
				} else {
					f2 += (float) Math.sin(Math.toRadians(i * Winkel))
							* diameter / 4.0f;
				}
				final Transform3D t3d01 = new Transform3D();
				final Transform3D t3d02 = new Transform3D();
				final Transform3D t3d03 = new Transform3D();
				t3d01.setTranslation(
						new Vector3d(f1, 0.25 + (double) i / 2 + f2, 0.0));
				t3d02.rotZ(Math.toRadians(-i * Winkel));
				t3d03.mul(t3d02, t3d01);
				tg[i].setTransform(t3d03);
			} else {
				if (Winkel > 0) {
					f2 -= (float) Math.sin(Math.toRadians((i - 1) * Winkel))
							* diameter / 4.0f;
				} else {
					f2 += (float) Math.sin(Math.toRadians((i - 1) * Winkel))
							* diameter / 4.0f;
				}
				final Transform3D t3d01 = new Transform3D();
				final Transform3D t3d02 = new Transform3D();
				final Transform3D t3d03 = new Transform3D();
				t3d01.setTranslation(
						new Vector3d(f1, -0.25 + (double) i / 2 + f2, 0.0));
				t3d02.rotZ(Math.toRadians(-(i - 1) * Winkel));
				t3d03.mul(t3d02, t3d01);
				leafs.setTransform(t3d03);
			}
		}
	}

	private void makeWindAnimation(final int intensity) {
	}

	public void setIntensityOfWind(final int i) {
		if (i < 0) {
			intensityOfWind = 0;
		} else if (i > 100) {
			intensityOfWind = 100;
		} else {
			intensityOfWind = i;
		}
	}

	public void moveTo(final double x, final double y) {
		final Transform3D T3D = new Transform3D();
		this.x = x;
		this.y = -y;
		T3D.setTranslation(new Vector3d(this.x, 0.0f, this.y));
		this.setTransform(T3D);
	}

	public void run() {
		makeWindAnimation(intensityOfWind);
	}
}