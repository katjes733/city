package com.katjes.city.objects.ground;

import javax.media.j3d.Appearance;
import javax.media.j3d.Billboard;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.katjes.city.graphics.TexturedPlane;

public class TreeFlat extends TransformGroup {

	public TreeFlat(final double width, final double height, final double x,
			final double y, final Appearance app) {
		this.addChild(make_Flat_Tree(width, height, x, y, app));
	}

	TransformGroup make_Flat_Tree(final double width, final double height,
			final double x, final double y, final Appearance app) {
		final Vector3d translate = new Vector3d(new Point3d(x, 0.0, -y));
		final Transform3D T3D = new Transform3D();
		final TransformGroup TGTranslate = new TransformGroup(); // Return Group
		final TransformGroup TGTarget = new TransformGroup();
		T3D.setTranslation(translate);
		TGTranslate.setTransform(T3D);

		TGTarget.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		final Billboard billboard = new Billboard(TGTarget);
		billboard.setSchedulingBounds(new BoundingSphere());

		final TexturedPlane TP01 = new TexturedPlane(
				new Point3f((float) (-width / 2), 0.0f, 0.0f),
				new Point3f((float) (width / 2), 0.0f, 0.0f),
				new Point3f((float) (width / 2), (float) height, 0.0f),
				new Point3f((float) (-width / 2), (float) height, 0.0f), app);
		// Test
		/*
		 * TexturedPlane TP02 = new TexturedPlane( new Point3f(0.0f,0.0f,(float)(width/2)),new
		 * Point3f(0.0f,0.0f,(float)(-width/2)), new Point3f(0.0f,(float)(height),(float)(-width/2)),new
		 * Point3f(0.0f,(float)(height),(float)(width/2)), app);
		 */

		TGTranslate.addChild(billboard);
		TGTranslate.addChild(TGTarget);
		TGTarget.addChild(TP01);
		// TGTarget.addChild(TP02);

		return TGTranslate;
	}
}