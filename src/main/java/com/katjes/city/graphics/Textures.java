package com.katjes.city.graphics;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.image.TextureLoader;

public class Textures {
	private static final String PARENT_PATH_TEXTURES = "graphics/";
	private static final Color3f schwarz = new Color3f(0f, 0f, 0f);
	private static final Color3f weiss = new Color3f(1f, 1f, 1f);

	public Appearance lightSurface, app_Norden, app_Süden, app_Westen,
			app_Osten, app_grass01, app_Strasse_t, app_Strasse_plus,
			app_Strasse_g, app_Strasse_Abb, app_Bürgersteig01,
			app_Bürgersteig02, app_Bürgersteig03, app_Water, Zaun, Wand1Fenster,
			Tür, TürWand, Wand0Fenster, Wand2Fenster, Wand3Fenster, Decke, Dach,
			Baum01, Baum02, Baum03, Baum04, Rinde01, Pflanze01, Pflanze02,
			Pflanze03;

	public TextureAnimation ani_Water, app_Himmel;

	public Textures() {
		loadStaticTextures();
		loadChangingTextures();
	}

	private void loadStaticTextures() {
		/******************************************************************************************************/
		// Hier alle Einzelbild-Texturen laden lassen
		lightSurface = getAppearance("LightSurface02.jpg", false);
		app_Norden = getAppearance("Skyline01.jpg", false);
		app_Süden = getAppearance("Gebirge01.jpg", false);
		app_Westen = getAppearance("desert01.jpg", false);
		app_Osten = getAppearance("Skyline01.jpg", false);
		app_grass01 = getAppearance("grass04.jpg", false);
		app_Strasse_t = getAppearance("t-kreuzung1_01.jpg", false);
		app_Strasse_plus = getAppearance("kreuzung1_01.jpg", false);
		app_Strasse_g = getAppearance("road1_01.jpg", false);
		app_Strasse_Abb = getAppearance("Abbiegung1_01.jpg", false);
		app_Bürgersteig01 = getAppearance("Sidewalk01.jpg", false);
		app_Bürgersteig02 = getAppearance("Sidewalk02.jpg", false);
		app_Bürgersteig03 = getAppearance("Sidewalk03.jpg", false);
		app_Water = getAppearance("water01.jpg", false);
		Wand1Fenster = getAppearance("haus/Wand1Fenster.jpg", false);
		Tür = getAppearance("haus/Door.jpg", false);
		TürWand = getAppearance("haus/DoorWall.jpg", false);
		Wand0Fenster = getAppearance("haus/Wand0Fenster.jpg", false);
		Wand2Fenster = getAppearance("haus/Wand2Fenster.jpg", false);
		Wand3Fenster = getAppearance("haus/Wand3Fenster.jpg", false);
		Decke = getAppearance("haus/Decke.jpg", false);
		Dach = getAppearance("haus/Dach.jpg", false);

		Zaun = getAppearance("haus/Zaun.gif", true);
		Baum01 = getAppearance("Baum/Baum011.gif", true);
		Baum02 = getAppearance("Baum/planta256.gif", true);
		Baum03 = getAppearance("Baum/treeleaf01.gif", true);
		Baum04 = getAppearance("Baum/Baum001.gif", true);
		Pflanze01 = getAppearance("Baum/plantb256.gif", true);
		Pflanze02 = getAppearance("Baum/plantc256.gif", true);
		Pflanze03 = getAppearance("Baum/plantd256.gif", true);
		Rinde01 = getAppearance("Baum/bark01.jpg", true);

		/******************************************************************************************************/
	}

	private void loadChangingTextures() {
		/******************************************************************************************************/
		// Hier alle Mehrbild-Texturen (Animationen) laden lassen
		ani_Water = new TextureAnimation("waterclearX", 8, 500, false, -1);
		new Thread(ani_Water).start();

		app_Himmel = new TextureAnimation("sky", 8, 1000, false, -1);
		new Thread(app_Himmel).start();

		/******************************************************************************************************/
	}

	private Appearance getAppearance(final String relativePathToTextureFile,
			final boolean isTransparent) {
		final Appearance returnValue = new Appearance();
		final String absolutePathToTexture = getClass().getClassLoader()
				.getResource(PARENT_PATH_TEXTURES + relativePathToTextureFile)
				.getPath();
		System.out.println("Loading texture: " + absolutePathToTexture);

		final TextureLoader loader = new TextureLoader(absolutePathToTexture,
				null);

		final Texture texture = loader.getTexture();

		if (isTransparent) {
			final TransparencyAttributes tra = new TransparencyAttributes();
			tra.setTransparencyMode(TransparencyAttributes.BLENDED);
			tra.setTransparency(0f);
			returnValue.setTransparencyAttributes(tra);
		}
		final PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		returnValue.setPolygonAttributes(pa);

		final TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);

		returnValue.setTextureAttributes(texAttr);

		final Material m = new Material(weiss, schwarz, weiss, schwarz, 1.0f);
		m.setLightingEnable(true);
		returnValue.setMaterial(m);

		returnValue.setTexture(texture);
		return returnValue;

	}
}