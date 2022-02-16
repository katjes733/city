package com.katjes.city.objects.people;

// MoveableEllipticLimb.java
// Thana Konglikhit, October 2003, s4310170@maliwan.psu.ac.th
// Andrew Davison, dandrew@ratree.psu.ac.th

/*
 * A MoveableLimb object but using an elliptical lathe shape rather than a circular one. Almost the same as the
 * EllipticLimb class.
 */
import javax.media.j3d.Texture;

import com.sun.j3d.utils.image.TextureLoader;

public class MoveableEllipticLimb extends MoveableLimb {
	public MoveableEllipticLimb(final String lName, final int lNo,
			final String jn0, final String jn1, final int axis,
			final double angle, final double[] xs, final double[] ys,
			final String tex) {
		super(lName, lNo, jn0, jn1, axis, angle, xs, ys, tex);
	}

	@Override
	protected void makeShape()
	// overridden to make a EllipseShape3D instead of LatheShape3D
	{
		EllipseShape3D es;
		if (texPath != null) {
			// System.out.println("Loading textures/" + texPath);
			final TextureLoader texLd = new TextureLoader(texPath, null);
			final Texture tex = texLd.getTexture();
			es = new EllipseShape3D(xsIn, ysIn, tex);
		} else {
			es = new EllipseShape3D(xsIn, ysIn, null);
		}

		zAxisTG.addChild(es); // add the shape to the limb's graph
	} // end of makeEllipseShape()

} // end of MoveableEllipticLimb class
