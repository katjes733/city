package com.katjes.city.objects.ground;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Light;
import javax.media.j3d.Material;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class StreetLight extends TransformGroup {
	private static final float RADIUSOFCOLUMN = 0.025f;
	private static final Color3f STDLIGHTCOLOR = new Color3f(1f, 1f, 0.75f);
	private static final Color3f GRAU = new Color3f(0.5f, 0.5f, 0.5f);
	private static final Color3f SCHWARZ = new Color3f(0f, 0f, 0f);
	private static final Color3f WEISS = new Color3f(1f, 1f, 1f);
	private static final Color3f GELB = new Color3f(1f, 1f, 0f);
	private static final Color3f GELBVERWASCHEN = new Color3f(1f, 1f, 0.8f);
	private static final Color3f WEISSLAMPEAUS = new Color3f(1f, 1f, 0.9f);
	private static final Material LIGHTOFFM = new Material(WEISSLAMPEAUS,
			SCHWARZ, WEISSLAMPEAUS, WEISS, 1.0f);
	private static final Material LIGHTONM = new Material(SCHWARZ, GELB,
			SCHWARZ, SCHWARZ, 1.0f);
	private static final float LIGHTTRANSPARENCY = 0.1f;

	private SpotLight light;
	private TransformGroup subTGroup1;
	private TransformGroup subTGroup2;
	private TransformGroup subTGroup3;
	private TransformGroup subTGroup4;
	private TransformGroup tempGroup1;
	private Appearance lamp;

	private boolean lightOn = false;
	private boolean lightOff = true;

	public StreetLight(final double posX, final double posY, final int angle) {
		MakeStreetLightColored(posX, posY, angle, GRAU);
	}

	public StreetLight(final double posX, final double posY, final int angle,
			final Color3f color) {
		MakeStreetLightColored(posX, posY, angle, color);
	}

	public StreetLight(final double posX, final double posY, final int angle,
			final Appearance texture) {
		MakeStreetLightTextured(posX, posY, angle, texture);
	}

	private void MakeStreetLightColored(final double posX, final double posY,
			final int angle, final Color3f color) {
		final Appearance surface = new Appearance();

		final Material m = new Material(color, SCHWARZ, color, color, 10.0f);
		m.setLightingEnable(true);
		surface.setMaterial(m);

		MakeStreetLightTextured(posX, posY, angle, surface);
	}

	private void MakeStreetLightTextured(final double posX, final double posY,
			final int angle, final Appearance texture) {
		Transform3D t3d01 = new Transform3D();
		Transform3D t3d02 = new Transform3D();
		Transform3D t3d03 = new Transform3D();

		lamp = new Appearance();
		lamp.setCapability(Appearance.ALLOW_MATERIAL_READ);
		lamp.setCapability(Appearance.ALLOW_MATERIAL_WRITE);

		LIGHTOFFM.setLightingEnable(true);
		LIGHTONM.setLightingEnable(true);

		final TransparencyAttributes tra = new TransparencyAttributes();
		tra.setTransparencyMode(TransparencyAttributes.NONE);
		tra.setTransparency(LIGHTTRANSPARENCY);
		lamp.setTransparencyAttributes(tra);

		lamp.setMaterial(LIGHTOFFM);

		subTGroup1 = new TransformGroup();
		subTGroup2 = new TransformGroup();
		subTGroup3 = new TransformGroup();
		subTGroup4 = new TransformGroup();
		tempGroup1 = new TransformGroup();

		final Cylinder c1 = new Cylinder(RADIUSOFCOLUMN, 2.0f,
				Primitive.GENERATE_TEXTURE_COORDS + Primitive.GENERATE_NORMALS,
				20, 20, texture);
		subTGroup1.addChild(c1);
		t3d01.setTranslation(new Vector3f(0f, 1.0f, 0f));
		subTGroup1.setTransform(t3d01);

		t3d01 = new Transform3D();

		final Cylinder c2 = new Cylinder(RADIUSOFCOLUMN, 0.5f,
				Primitive.GENERATE_TEXTURE_COORDS + Primitive.GENERATE_NORMALS,
				20, 20, texture);
		subTGroup2.addChild(c2);
		t3d01.setTranslation(new Vector3d(
				-Math.sin(Math.PI / 4) * (0.5 - RADIUSOFCOLUMN) / 2,
				2.0 + Math.sin(Math.PI / 4) * (0.5 - 2 * RADIUSOFCOLUMN) / 2,
				0));
		t3d02.rotZ(Math.toRadians(45));
		t3d03.mul(t3d01, t3d02);
		subTGroup2.setTransform(t3d03);

		t3d01 = new Transform3D();
		t3d02 = new Transform3D();
		t3d03 = new Transform3D();

		final Cylinder c3 = new Cylinder(RADIUSOFCOLUMN, 0.75f,
				Primitive.GENERATE_TEXTURE_COORDS + Primitive.GENERATE_NORMALS,
				20, 20, texture);
		subTGroup3.addChild(c3);
		t3d01.setTranslation(new Vector3d(
				-Math.sin(Math.PI / 4) * (0.5 - 1.5 * RADIUSOFCOLUMN)
						- 0.75 / 2,
				2.0 + Math.sin(Math.PI / 4) * (0.5 - 2 * RADIUSOFCOLUMN)
						+ RADIUSOFCOLUMN / 2,
				0));
		t3d02.rotZ(Math.toRadians(90));
		t3d03.mul(t3d01, t3d02);
		subTGroup3.setTransform(t3d03);

		t3d01 = new Transform3D();
		t3d02 = new Transform3D();
		t3d03 = new Transform3D();

		final Cylinder c4 = new Cylinder(RADIUSOFCOLUMN * 5, 0.05f,
				Primitive.GENERATE_TEXTURE_COORDS + Primitive.GENERATE_NORMALS,
				20, 20, lamp);
		subTGroup4.addChild(c4);
		t3d01.setTranslation(new Vector3d(
				-Math.sin(Math.PI / 4) * (0.5 - 1.5 * RADIUSOFCOLUMN) - 0.75
						+ RADIUSOFCOLUMN * 5,
				2.0 + Math.sin(Math.PI / 4) * (0.5 - 2 * RADIUSOFCOLUMN)
						- RADIUSOFCOLUMN,
				0));
		subTGroup4.setTransform(t3d01);

		final BoundingSphere bounds = new BoundingSphere(
				new Point3d(posX, 0.0, -posY), 100.0);

		light = new SpotLight(false, STDLIGHTCOLOR, new Point3f(
				(float) (-Math.sin(Math.PI / 4) * (0.5 - 1.5 * RADIUSOFCOLUMN)
						- 0.75 + RADIUSOFCOLUMN * 5),
				(float) (2.0
						+ Math.sin(Math.PI / 4) * (0.5 - 2 * RADIUSOFCOLUMN)
						- RADIUSOFCOLUMN),
				0f), new Point3f(1f, 0f, 0f), new Vector3f(0f, -1f, 0f),
				60.0f * (float) Math.PI / 180.0f, 1.0f);
		light.setCapability(Light.ALLOW_STATE_READ);
		light.setCapability(Light.ALLOW_STATE_WRITE);

		light.setInfluencingBounds(bounds);
		subTGroup4.addChild(light);

		tempGroup1.addChild(subTGroup1);
		tempGroup1.addChild(subTGroup2);
		tempGroup1.addChild(subTGroup3);
		tempGroup1.addChild(subTGroup4);

		t3d01 = new Transform3D();
		t3d02 = new Transform3D();
		t3d03 = new Transform3D();

		t3d01.rotY(Math.toRadians(angle));

		tempGroup1.setTransform(t3d01);

		this.addChild(tempGroup1);

		t3d02.setTranslation(new Vector3d(posX, 0, -posY));
		this.setTransform(t3d02);
	}

	public void switchLightOn() {
		if (!lightOn && lightOff) {
			lamp.setMaterial(LIGHTONM);
			light.setEnable(true);
			lightOn = true;
			lightOff = false;
		}
	}

	public void switchLightOff() {
		if (lightOn && !lightOff) {
			lamp.setMaterial(LIGHTOFFM);
			light.setEnable(false);
			lightOn = false;
			lightOff = true;
		}
	}
}