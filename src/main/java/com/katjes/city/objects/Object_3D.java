package com.katjes.city.objects;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.mnstarfire.loaders3d.Inspector3DS;

public class Object_3D extends TransformGroup {
	private final Inspector3DS loader;
	private static TransformGroup theModel;

	// private static final String stdPath = "/home/ws04ba19/cgr2004/City/models/";
	private static final String stdPath = "/City/models";

	public Object_3D(final String Model) {
		loader = new Inspector3DS(stdPath + Model);
		loader.setTextureLightingOn(); // turns on modulate mode for textures (lighting)
		loader.parseIt();
		theModel = loader.getModel();
		repos();
		addChild(theModel);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		theModel.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		theModel.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	}

	public Object_3D(final String Model, final double ScaleFactor) {
		loader = new Inspector3DS(stdPath + Model);
		loader.setTextureLightingOn(); // turns on modulate mode for textures (lighting)
		loader.parseIt();
		theModel = loader.getModel();
		repos(ScaleFactor, ScaleFactor, ScaleFactor);
		addChild(theModel);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		theModel.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		theModel.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	}

	public Object_3D(final String Model, final double ScaleFactorX,
			final double ScaleFactorY, final double ScaleFactorZ) {
		loader = new Inspector3DS(stdPath + Model);
		loader.setTextureLightingOn(); // turns on modulate mode for textures (lighting)
		loader.parseIt();
		theModel = loader.getModel();
		repos(ScaleFactorX, ScaleFactorY, ScaleFactorZ);
		addChild(theModel);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		theModel.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		theModel.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	}

	private void repos() {
		repos(1.0, 1.0, 1.0);
	}

	private void repos(final double factorX, final double factorY,
			final double factorZ) {
		final double ScaleFactor = 0.005;

		final Transform3D scale = new Transform3D();
		final Transform3D rotate = new Transform3D();
		final Transform3D move = new Transform3D();
		final Transform3D result = new Transform3D();

		scale.setScale(new Vector3d(ScaleFactor * factorX,
				ScaleFactor * factorY, ScaleFactor * factorZ));
		rotate.rotY(Math.PI / 2);
		move.setTranslation(new Vector3d(0.0, 0.0, 0.0));
		result.mul(scale, rotate);
		result.mul(result, move);

		theModel.setTransform(result);
	}

	public void move_to(final double x, final double y, final double z) {
		final Transform3D move = new Transform3D();

		move.setTranslation(new Vector3d(x, y, z));
		this.setTransform(move);
	}

	public void scale_all(final double factor) {
		scale(factor, factor, factor);
	}

	public void scale(final double factorX, final double factorY,
			final double factorZ) {
		final Transform3D scaleIt = new Transform3D();
		scaleIt.setScale(new Vector3d(factorX, factorY, factorZ));
		this.setTransform(scaleIt);
	}

	public void rotate(final double degreeX, final double degreeY,
			final double degreeZ) {
		final Transform3D rotateX = new Transform3D();
		final Transform3D rotateY = new Transform3D();
		final Transform3D rotateZ = new Transform3D();
		final Transform3D result = new Transform3D();

		rotateX.rotX(degreeX / 180 * Math.PI);
		rotateY.rotY(degreeY / 180 * Math.PI);
		rotateZ.rotZ(degreeZ / 180 * Math.PI);

		result.mul(rotateX, rotateY);
		result.mul(result, rotateZ);
		this.setTransform(result);
	}
}
