package com.katjes.city.objects.people;

import java.text.DecimalFormat;

// LatheShape3D.java
// Andrew Davison, July 2003, dandrew@ratree.psu.ac.th

/*
 * A LatheCurve is rotated around the y-axis to make a shape. Its appearance is set to a colour or a texture. The
 * texture is wrapped around the shape and stretched to its max height. The rotation of the curve to make the shape uses
 * code derived from the SurfaceOfRevolution class by Chris Buckalew. The colour of the surface is pink, or some
 * specified colour, or some specified texture.
 */
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.TexCoord2f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

public class LatheShape3D extends Shape3D {
	private final static double RADS_DEGREE = Math.PI / 180.0;

	private static final double ANGLE_INCR = 15.0; // 20, 15, 10, 5
	// the angle turned through to create a face of the solid
	private static final int NUM_SLICES = (int) (360.0 / ANGLE_INCR);

	// default dark and light colours for shape
	private static final Color3f pink = new Color3f(1.0f, 0.75f, 0.8f);
	private static final Color3f darkPink = new Color3f(0.25f, 0.18f, 0.2f);

	private static final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);

	private double height; // height of the shape

	public LatheShape3D(final double xsIn[], final double ysIn[],
			final Texture tex) {
		final LatheCurve lc = new LatheCurve(xsIn, ysIn);
		buildShape(lc.getXs(), lc.getYs(), lc.getHeight(), tex);
	}

	public LatheShape3D(final double xsIn[], final double ysIn[],
			final Color3f darkCol, final Color3f lightCol)
	// two colours required: a dark and normal version of the colour
	{
		final LatheCurve lc = new LatheCurve(xsIn, ysIn);
		buildShape(lc.getXs(), lc.getYs(), lc.getHeight(), darkCol, lightCol);
	}

	// -------------------- build the shape -------------------------------

	private void buildShape(final double[] xs, final double[] ys,
			final double h) {
		height = h;
		createGeometry(xs, ys, false);
		createAppearance(darkPink, pink);
	} // end of buildShape()

	private void buildShape(final double[] xs, final double[] ys,
			final double h, final Color3f darkCol, final Color3f lightCol) {
		height = h;
		createGeometry(xs, ys, false);

		if (darkCol == null || lightCol == null) {
			System.out.println("One of the colours is null; using defaults");
			createAppearance(darkPink, pink);
		} else {
			createAppearance(darkCol, lightCol);
		}
	} // end of buildShape()

	private void buildShape(final double[] xs, final double[] ys,
			final double h, final Texture tex) {
		height = h;
		if (tex == null) {
			System.out.println("The texture is null; using default colours");
			createGeometry(xs, ys, false);
			createAppearance(darkPink, pink);
		} else {
			createGeometry(xs, ys, true);
			createAppearance(tex);
		}
	} // end of buildShape()

	private void createGeometry(final double[] xs, final double[] ys,
			final boolean usingTexture)
	/*
	 * Create the surface, using the curve defined by the (x,y) coords in xs[] and ys[]. The surface is a QuadArray,
	 * which is given normals so it will reflect light. Texture coordinates may be defined to wrap the image around the
	 * outside of the shape, starting from the back, wrapping counter-clockwise (left to right) around the front, and
	 * back to the back.
	 */
	{
		final double verts[] = surfaceRevolve(xs, ys);
		// printVerts(verts);

		// use GeometryInfo to compute normals
		final GeometryInfo geom = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
		geom.setCoordinates(verts);

		if (usingTexture) {
			geom.setTextureCoordinateParams(1, 2); // set up texture coords
			final TexCoord2f[] texCoords = initTexCoords(verts);
			// printTexCoords(texCoords);
			correctTexCoords(texCoords);
			geom.setTextureCoordinates(0, texCoords);
		}

		final NormalGenerator norms = new NormalGenerator();
		norms.generateNormals(geom);

		setGeometry(geom.getGeometryArray()); // convert back to geo array
	} // end of createGeometry()

	private void printVerts(final double[] verts)
	// for debugging purposes
	{
		final DecimalFormat df = new DecimalFormat("0.###"); // 3 dp

		final int numPerLine = 6; // multiple of 3
		int count = 0;
		System.out.println("No. vertices: " + verts.length + "\n");

		for (int i = 0; i < verts.length; i = i + 3) {
			if (count == numPerLine) {
				System.out.println();
				count = 0;
			}
			System.out.print(
					"(" + df.format(verts[i]) + ", " + df.format(verts[i + 1])
							+ ", " + df.format(verts[i + 2]) + ")  ");
			count += 3;
		}
		System.out.println("\n");
	} // end of printVerts()

	private TexCoord2f[] initTexCoords(final double[] verts)
	/*
	 * Wrap the texture around the shape, the left edge starting at the back, going counter-clockwise round the front.
	 * Th texture is stretched along the y-axis so a t value of 1 equals the max height of the shape. s is obtained from
	 * the angle made by the (x,z) coordinate; t is the scaled height of a coordinate.
	 */
	{
		final int numVerts = verts.length;
		// System.out.println("No. verts.: " + numVerts)

		final TexCoord2f[] tcoords = new TexCoord2f[numVerts / 3];

		double x, y, z;
		float sVal, tVal;
		double angle, frac;

		int idx = 0;
		for (int i = 0; i < numVerts / 3; i++) {
			x = verts[idx];
			y = verts[idx + 1];
			z = verts[idx + 2];

			angle = Math.atan2(x, z); // -PI to PI
			frac = angle / Math.PI; // -1.0 to 1.0
			sVal = (float) (0.5 + frac / 2); // 0.0f to 1.0f

			tVal = (float) (y / height); // 0.0f to 1.0f; uses global height value

			tcoords[i] = new TexCoord2f(sVal, tVal);
			idx += 3;
		}

		return tcoords;
	} // end of initTexCoords()

	private void printTexCoords(final TexCoord2f[] tcoords)
	// for debugging purposes
	{
		System.out.println("No. tex coords: " + tcoords.length + "\n");

		for (int i = 0; i < tcoords.length; i = i + 2) {
			System.out.println(tcoords[i] + "  " + tcoords[i + 1]);
		}
		System.out.println("\n");
	} // end of printTexCoords()

	private void correctTexCoords(final TexCoord2f[] tcoords)
	/*
	 * Find texture squares where the texture coords are reversed, and un-reverse them. A reversal occurs at the
	 * junction between -PI and PI of tan(x/z) (at the back of the shape). The s coords on the -PI side will be near 0,
	 * the ones on the PI side will be near 1, which will make the square show a reversed texture. The correction is to
	 * change the 0 values to 1's, which will make the square show the texture near the s value of 1.
	 */
	{
		// System.out.println("Checking");
		for (int i = 0; i < tcoords.length; i = i + 4) {
			if (tcoords[i].x < tcoords[i + 3].x
					&& tcoords[i + 1].x < tcoords[i + 2].x) { // should not increase
				// System.out.println(tcoords[i] + " " + tcoords[i+1] );
				// System.out.println(tcoords[i+2] + " " + tcoords[i+3] + "\n");
				// tcoords[i].x = 1.0f;
				tcoords[i].x = (1.0f + tcoords[i + 3].x) / 2; // between x and 1.0
				// tcoords[i+1].x = 1.0f;
				tcoords[i + 1].x = (1.0f + tcoords[i + 2].x) / 2; // between x and 1.0
			}
		}
		// System.out.println("\n");
	} // end of correctTexCoords()

	private void createAppearance(final Color3f darkCol, final Color3f lightCol)
	/*
	 * The appearance is a colour which relects light. The dark colour is used for ambient effects, the light colour for
	 * diffuse.
	 */
	{
		final Appearance app = new Appearance();

		final PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE); // see both sides of shape
		app.setPolygonAttributes(pa);

		final Material mat = new Material(darkCol, black, lightCol, black,
				1.0f);
		// sets ambient, emissive, diffuse, specular, shininess
		mat.setLightingEnable(true); // lighting switched on
		app.setMaterial(mat);

		setAppearance(app);
	} // end of createAppearance()

	private void createAppearance(final Texture tex)
	/*
	 * The appearance is a texture which reflects light. The texture is stretched over the shape using the textured
	 * coords created in createGeometry()
	 */
	{
		final Appearance app = new Appearance();

		final PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE); // see both sides of shape
		app.setPolygonAttributes(pa);

		// mix the texture and the material colour
		final TextureAttributes ta = new TextureAttributes();
		ta.setTextureMode(TextureAttributes.MODULATE);
		app.setTextureAttributes(ta);

		final Material mat = new Material(); // set a default white material
		mat.setSpecularColor(black); // no specular color
		mat.setLightingEnable(true);
		app.setMaterial(mat);

		app.setTexture(tex);

		setAppearance(app);
	} // end of createAppearance()

	// --------------- surface revolution methods -----------------
	/*
	 * Deruved from the SurfaceOfRevolution class by Chris Buckalew, (c) 1994-2002. Part of his FreeFormDef.java
	 * example. http://www.csc.calpoly.edu/~buckalew/474Lab6-W03.html
	 */

	private double[] surfaceRevolve(final double xs[], final double ys[])
	/*
	 * Each adjacent pairs of coords in the curve are made into one face of the surface. A face is constructed in
	 * counter-clockwise order, so that its normal will face outwards. The coords in the xs[] and ys[] arrays are
	 * assumed to be in increasing order.
	 */
	{
		checkCoords(xs);

		final double[] coords = new double[NUM_SLICES * (xs.length - 1) * 4
				* 3];

		int index = 0;
		for (int i = 0; i < xs.length - 1; i++) {
			for (int slice = 0; slice < NUM_SLICES; slice++) {
				addCorner(coords, xs[i], ys[i], slice, index); // bottom right
				index += 3;

				addCorner(coords, xs[i + 1], ys[i + 1], slice, index); // top right
				index += 3;

				addCorner(coords, xs[i + 1], ys[i + 1], slice + 1, index); // top left
				index += 3;

				addCorner(coords, xs[i], ys[i], slice + 1, index); // bottom left
				index += 3;
			}
		}
		return coords;
	} // end of SurfaceRevolve()

	private void checkCoords(final double xs[]) {
		// all x points should be >= 0, since we are revolving around the y-axis
		for (int i = 0; i < xs.length; i++) {
			if (xs[i] < 0) {
				System.out.println(
						"Warning: setting xs[" + i + "] from -ve to 0");
				xs[i] = 0;
			}
		}
	} // end of checkCoords()

	private void addCorner(final double[] coords, final double xOrig,
			final double yOrig, final int slice, final int index)
	/*
	 * Create a new (x,y,z) coordinate, except when the rotation has come back to the start. Then use the original
	 * coords.
	 */
	{
		final double angle = RADS_DEGREE * (slice * ANGLE_INCR);

		if (slice == NUM_SLICES) {
			coords[index] = xOrig;
		} else {
			coords[index] = xCoord(xOrig, angle); // x
		}

		coords[index + 1] = yOrig; // y

		if (slice == NUM_SLICES) {
			coords[index + 2] = 0;
		} else {
			coords[index + 2] = zCoord(xOrig, angle); // z
		}
	} // end of addCorner()

	/*
	 * ------------------------------------------ The following methods rotate the radius unchanged, creating a circle
	 * of points. These methods can be overridden to vary the radii of the points, e.g. to make an ellipse.
	 */

	protected double xCoord(final double radius, final double angle) {
		return radius * Math.cos(angle);
	}

	protected double zCoord(final double radius, final double angle) {
		return radius * Math.sin(angle);
	}

} // end of LatheShape3D class