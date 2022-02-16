package com.katjes.city.graphics;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;

import org.apache.commons.lang3.StringUtils;

import com.sun.j3d.utils.image.TextureLoader;

public class TextureAnimation extends Appearance implements Runnable {
	private static Color3f schwarz = new Color3f(0f, 0f, 0f);
	private static Color3f weiss = new Color3f(1f, 1f, 1f);
	private int DELAY = 25; // ms delay between frames
	private static final String PARENT_PATH_TEXTURES = "graphics/";

	private ImageComponent2D ims[];
	private Texture2D texture;

	private int rep; // -1:endless,>0:x-times
	private boolean finished = false;

	public TextureAnimation() {

	}

	public TextureAnimation(final String Filename, final int Anzahl,
			final boolean trans, final int rep) {
		this.rep = rep;
		loadImages(Filename, Anzahl);

		if (!trans) {
			createAppearance();
		} else {
			createAppearance_Trans();
		}
	} // end of ImagesSeries()

	public TextureAnimation(final String Filename, final int Anzahl,
			final int delay, final boolean trans, final int rep) {
		this.DELAY = delay;
		this.rep = rep;
		loadImages(Filename, Anzahl);

		if (!trans) {
			createAppearance();
		} else {
			createAppearance_Trans();
		}
	} // end of ImagesSeries()

	private void loadImages(final String filenamePrefix, final int num) {
		TextureLoader loader;
		final List<ImageComponent2D> listOfImageComponent2D = new ArrayList<>();

		final ClassLoader classLoader = getClass().getClassLoader();

		URL url = null;
		int counter = 1;
		while ((url = classLoader
				.getResource(
						PARENT_PATH_TEXTURES + filenamePrefix
								+ StringUtils.leftPad(Integer.toString(counter),
										3, "0")
								+ ".gif")) != null
				|| (url = classLoader.getResource(PARENT_PATH_TEXTURES
						+ filenamePrefix
						+ StringUtils.leftPad(Integer.toString(counter), 3, "0")
						+ ".jpg")) != null) {
			loader = new TextureLoader(url.getPath(), null);
			listOfImageComponent2D.add(loader.getImage());
			counter++;
		}

		ims = listOfImageComponent2D.toArray(new ImageComponent2D[0]);

	}

	private void createAppearance() {

		// blended transparency so texture can be irregular
		final TransparencyAttributes tra = new TransparencyAttributes();
		tra.setTransparencyMode(TransparencyAttributes.NONE);
		this.setTransparencyAttributes(tra);

		// mix the texture and the material colour
		final TextureAttributes ta = new TextureAttributes();
		ta.setTextureMode(TextureAttributes.MODULATE);
		this.setTextureAttributes(ta);

		// set material and lighting
		final Material mat = new Material(weiss, schwarz, weiss, schwarz, 1.0f);
		mat.setLightingEnable(true);
		this.setMaterial(mat);

		// Create a two dimensional texture
		// Set the texture from the first loaded image
		texture = new Texture2D(Texture2D.BASE_LEVEL, Texture.RGBA,
				ims[1].getWidth(), ims[1].getHeight());
		texture.setImage(0, ims[1]);
		texture.setCapability(Texture.ALLOW_IMAGE_WRITE); // texture can change
		texture.setCapability(Texture.ALLOW_ENABLE_WRITE);
		this.setTexture(texture);

	} // end of createAppearance()

	private void createAppearance_Trans() {

		final TransparencyAttributes tra = new TransparencyAttributes();
		tra.setTransparencyMode(TransparencyAttributes.BLENDED);
		tra.setTransparency(0f);
		this.setTransparencyAttributes(tra);

		final PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		this.setPolygonAttributes(pa);

		final TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		this.setTextureAttributes(texAttr);

		final Material m = new Material(weiss, schwarz, weiss, schwarz, 1.0f);
		m.setSpecularColor(schwarz);
		m.setLightingEnable(true);
		this.setMaterial(m);

		// Create a two dimensional texture
		// Set the texture from the first loaded image
		texture = new Texture2D(Texture2D.BASE_LEVEL, Texture.RGBA,
				ims[1].getWidth(), ims[1].getHeight());
		texture.setImage(0, ims[1]);
		texture.setCapability(Texture.ALLOW_IMAGE_WRITE); // texture can change
		texture.setCapability(Texture.ALLOW_ENABLE_WRITE);
		this.setTexture(texture);

	} // end of createAppearance()

	public void showSeries() {
		int i = 1;
		int counter = 0;

		finished = false;

		if (rep == -1) {
			while (1 > 0) {
				texture.setImage(0, ims[i]);
				try {
					Thread.currentThread().sleep(DELAY); // wait a while
				} catch (final Exception ex) {
				}
				// System.out.println("Durchlauf : " + i);
				i++;
				if (i >= ims.length) {
					i = 1;
				}
			}
		} else {
			while (counter < rep) {
				texture.setImage(0, ims[i]);
				try {
					Thread.currentThread().sleep(DELAY); // wait a while
				} catch (final Exception ex) {
				}
				// System.out.println("Durchlauf : " + i);
				i++;
				if (i >= ims.length) {
					i = 1;
					counter++;
				}
			}
			finished = true;
		}
	} // end of showSeries()

	public void setDelay(final int delay) {
		this.DELAY = delay;
	}

	public int getDelay() {
		return this.DELAY;
	}

	@Override
	public void run() {
		showSeries();
	}

	public boolean getFinished() {
		return finished;
	}

} // end of ImagesSeries class