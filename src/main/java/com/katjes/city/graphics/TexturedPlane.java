package com.katjes.city.graphics;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.image.TextureLoader;

public class TexturedPlane extends Shape3D {
	private static final Color3f blau = new Color3f(0f, 0f, 1f);
	private static final Color3f schwarz = new Color3f(0f, 0f, 0f);
	private static final Color3f weiss = new Color3f(1f, 1f, 1f);
	private static final Color3f gelb = new Color3f(1f, 1f, 0f);

	private static final int NUM_VERTS = 4;
	// private QuadArray plane;
	private GeometryInfo plane;

	public TexturedPlane(final Point3f p1, final Point3f p2, final Point3f p3,
			final Point3f p4, final String fnm) {
		createGeometry(p1, p2, p3, p4, 1.0f);
		createAppearance(fnm);
	} // end of TexturedPlane()

	public TexturedPlane(final Point3f p1, final Point3f p2, final Point3f p3,
			final Point3f p4, final String fnm, final float div) {
		createGeometry(p1, p2, p3, p4, div);
		createAppearance(fnm);
	} // end of TexturedPlane()

	public TexturedPlane(final Point3f p1, final Point3f p2, final Point3f p3,
			final Point3f p4, final Appearance Textur) {
		createGeometry(p1, p2, p3, p4, 1.0f);
		setAppearance(Textur);
	} // end of TexturedPlane()

	public TexturedPlane(final Point3f p1, final Point3f p2, final Point3f p3,
			final Point3f p4, final Appearance Textur, final float div) {
		createGeometry(p1, p2, p3, p4, div);
		setAppearance(Textur);
	} // end of TexturedPlane()

	private void createGeometry(final Point3f p1, final Point3f p2,
			final Point3f p3, final Point3f p4, final float div) {
		this.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
		this.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);

		// plane = new QuadArray(NUM_VERTS,
		// GeometryArray.COORDINATES |
		// GeometryArray.TEXTURE_COORDINATE_2 );

		plane = new GeometryInfo(GeometryInfo.QUAD_ARRAY);

		final Point3f[] p = new Point3f[4];
		p[0] = p1;
		p[1] = p2;
		p[2] = p3;
		p[3] = p4;

		plane.setCoordinates(p);

		plane.setTextureCoordinateParams(1, 2);
		final TexCoord2f[] q = new TexCoord2f[4];
		q[0] = new TexCoord2f();
		q[0].set(0.0f, 0.0f);

		q[1] = new TexCoord2f();
		q[1].set(1.0f * div, 0.0f);

		q[2] = new TexCoord2f();
		q[2].set(1.0f * div, 1.0f * div);

		q[3] = new TexCoord2f();
		q[3].set(0.0f, 1.0f * div);
		plane.setTextureCoordinates(0, q);

		final NormalGenerator norms = new NormalGenerator();
		norms.generateNormals(plane);
		/*
		 * // anti-clockwise from bottom left plane.setCoordinate(0, p1); plane.setCoordinate(1, p2);
		 * plane.setCoordinate(2, p3); plane.setCoordinate(3, p4); TexCoord2f q = new TexCoord2f(); q.set(0.0f, 0.0f);
		 * plane.setTextureCoordinate(0, 0, q); q.set(1.0f*div, 0.0f); plane.setTextureCoordinate(0, 1, q);
		 * q.set(1.0f*div, 1.0f*div); plane.setTextureCoordinate(0, 2, q); q.set(0.0f, 1.0f*div);
		 * plane.setTextureCoordinate(0, 3, q);
		 */

		setGeometry(plane.getGeometryArray());
	} // end of createGeometry()

	private void createAppearance(final String fnm) {

		final TextureLoader loader = new TextureLoader(fnm, null);
		final ImageComponent2D im = loader.getImage();
		if (im == null) {
			System.out.println("Load failed for texture: " + fnm);
		} else {
			final Appearance app = new Appearance();

			// blended transparency so texture can be irregular
			final TransparencyAttributes tra = new TransparencyAttributes();
			tra.setTransparencyMode(TransparencyAttributes.BLENDED);
			app.setTransparencyAttributes(tra);

			// Create a two dimensional texture
			// Set the texture from the first loaded image
			final Texture2D texture = new Texture2D(Texture.BASE_LEVEL,
					Texture.RGBA, im.getWidth(), im.getHeight());
			texture.setImage(0, im);
			app.setTexture(texture);

			final Material m = new Material(schwarz, schwarz, schwarz, weiss,
					60.0f);
			m.setLightingEnable(true);
			app.setMaterial(m);

			final PolygonAttributes pa = new PolygonAttributes();
			pa.setCullFace(PolygonAttributes.CULL_NONE);
			app.setPolygonAttributes(pa);

			setAppearance(app);
		}
	} // end of createAppearance()

	public void setApp(final Appearance app) {
		setAppearance(app);
	}

	public void setDiv(final int div) {
		final TexCoord2f[] q = new TexCoord2f[4];

		q[0].set(0.0f, 0.0f);
		q[1].set(1.0f * div, 0.0f);
		q[2].set(1.0f * div, 1.0f * div);
		q[3].set(0.0f, 1.0f * div);
		plane.setTextureCoordinates(0, q);

		final NormalGenerator norms = new NormalGenerator();
		norms.generateNormals(plane);

		setGeometry(plane.getGeometryArray());
		/*
		 * TexCoord2f q = new TexCoord2f(); q.set(0.0f, 0.0f); plane.setTextureCoordinate(0, 0, q); q.set(1.0f*div,
		 * 0.0f); plane.setTextureCoordinate(0, 1, q); q.set(1.0f*div, 1.0f*div); plane.setTextureCoordinate(0, 2, q);
		 * q.set(0.0f, 1.0f*div); plane.setTextureCoordinate(0, 3, q); setGeometry(plane);
		 */
	}

} // end of TexturedPlane class