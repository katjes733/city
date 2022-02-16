package com.katjes.city.objects;

import java.util.Random;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.katjes.city.Occupied;
import com.katjes.city.graphics.Textures;
import com.katjes.city.graphics.Background;
import com.katjes.city.objects.buildings.HouseDual;
import com.katjes.city.objects.buildings.HouseSingle;
import com.katjes.city.objects.ground.StreetTurn;
import com.katjes.city.objects.ground.TreeFlatField;
import com.katjes.city.objects.ground.Field;
import com.katjes.city.objects.ground.Plant;
import com.katjes.city.objects.ground.StreetLight;
import com.katjes.city.objects.ground.Tree;
import com.katjes.city.objects.ground.StreetStraight;
import com.katjes.city.objects.ground.StreetIntersection;
import com.katjes.city.objects.ground.StreetIntersectionT;
import com.katjes.city.objects.people.Figure;

public class CityObjects extends TransformGroup {
	public static final int NUMMAXSTREETLIGHTS = 8;
	public static final int NUMMAX3DTREE = 100;

	private final Random r = new Random();

	public Textures t;
	private final Occupied b;

	public StreetLight[] light = new StreetLight[NUMMAXSTREETLIGHTS];
	public Tree tree01, tree02;

	public Tree[] tree = new Tree[NUMMAX3DTREE];

	public Figure figure, figure2, human1, human2;

	public CityObjects() {
		// Alle Texturen laden
		t = new Textures();
		b = new Occupied(-6, 6);

		createLights(); // temporär
		createStreets();
		createAreas();
		createHouses();
		// createPeople ();
		createPlants();
	}

	private void createLights() {
		/******************************************************************************************************/
		// Hier alle Lichter erzeugen
		// Hinweise mehr als 8 StreetLIghts verkraftet Java3D nicht (scheiß rückständiger müll !!!!)
		// Abhilfe : durch dynamische Auswertung des aktuellen Sichtbereichs können bis zu acht Lichtquellen
		// gleichzeitig dargestellt werden.
		// nötig ist dazu eine Routine.... vorschläge her ;)

		light[0] = new StreetLight(2.5, -7.5, 0, t.lightSurface);
		this.addChild(light[0]);

		light[1] = new StreetLight(2.5, -17.5, 0, t.lightSurface);
		this.addChild(light[1]);

		light[2] = new StreetLight(-2.5, 7.5, 180, t.lightSurface);
		this.addChild(light[2]);

		light[3] = new StreetLight(-2.5, 17.5, 180, t.lightSurface);
		this.addChild(light[3]);

		light[4] = new StreetLight(7.5, 2.5, 90, t.lightSurface);
		this.addChild(light[4]);

		light[5] = new StreetLight(17.5, 2.5, 90, t.lightSurface);
		this.addChild(light[5]);

		light[6] = new StreetLight(-7.5, -2.5, -90, t.lightSurface);
		this.addChild(light[6]);

		light[7] = new StreetLight(-17.5, -2.5, -90, t.lightSurface);
		this.addChild(light[7]);

		/******************************************************************************************************/
	}

	private void createStreets() {
		/******************************************************************************************************/
		// Hier alle Strassen + Hintergrund erzeugen
		this.addChild(new Background(14, 14, t.app_Norden, t.app_Süden,
				t.app_Westen, t.app_Osten, t.app_Himmel));

		this.addChild(new StreetIntersection(1, true, t.app_Strasse_plus,
				t.app_Bürgersteig02, 0, 0));
		b.set(0, 0, 2);

		this.addChild(
				new StreetTurn(1, true, t.app_Strasse_Abb, t.app_Bürgersteig01,
						t.app_Bürgersteig02, t.app_Bürgersteig03, 2, -5, -5));
		b.set(-5, -5, 2);
		this.addChild(
				new StreetTurn(1, true, t.app_Strasse_Abb, t.app_Bürgersteig01,
						t.app_Bürgersteig02, t.app_Bürgersteig03, 3, -5, 5));
		b.set(-5, 5, 2);
		this.addChild(
				new StreetTurn(1, true, t.app_Strasse_Abb, t.app_Bürgersteig01,
						t.app_Bürgersteig02, t.app_Bürgersteig03, 4, 5, 5));
		b.set(5, 5, 2);
		this.addChild(
				new StreetTurn(1, true, t.app_Strasse_Abb, t.app_Bürgersteig01,
						t.app_Bürgersteig02, t.app_Bürgersteig03, 1, 5, -5));
		b.set(5, -5, 2);

		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -1, 0));
		b.set(-1, 0, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -2, 0));
		b.set(-2, 0, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -3, 0));
		b.set(-3, 0, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -4, 0));
		b.set(-4, 0, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 1, 0));
		b.set(1, 0, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 2, 0));
		b.set(2, 0, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 3, 0));
		b.set(3, 0, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 4, 0));
		b.set(4, 0, 2);

		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -1, -5));
		b.set(-1, -5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -2, -5));
		b.set(-2, -5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -3, -5));
		b.set(-3, -5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -4, -5));
		b.set(-4, -5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 1, -5));
		b.set(1, -5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 2, -5));
		b.set(2, -5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 3, -5));
		b.set(3, -5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 4, -5));
		b.set(4, -5, 2);

		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -1, 5));
		b.set(-1, 5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -2, 5));
		b.set(-2, 5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -3, 5));
		b.set(-3, 5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, -4, 5));
		b.set(-4, 5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 1, 5));
		b.set(1, 5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 2, 5));
		b.set(2, 5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 3, 5));
		b.set(3, 5, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 2, 4, 5));
		b.set(4, 5, 2);

		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 0, -1));
		b.set(0, -1, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 0, -2));
		b.set(0, -2, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 0, -3));
		b.set(0, -3, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 0, -4));
		b.set(0, -4, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 0, 1));
		b.set(0, 1, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 0, 2));
		b.set(0, 2, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 0, 3));
		b.set(0, 3, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 0, 4));
		b.set(0, 4, 2);

		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, -5, -1));
		b.set(-5, -1, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, -5, -2));
		b.set(-5, -2, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, -5, -3));
		b.set(-5, -3, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, -5, -4));
		b.set(-5, -4, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, -5, 1));
		b.set(-5, 1, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, -5, 2));
		b.set(-5, 2, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, -5, 3));
		b.set(-5, 3, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, -5, 4));
		b.set(-5, 4, 2);

		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 5, -1));
		b.set(5, -1, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 5, -2));
		b.set(5, -2, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 5, -3));
		b.set(5, -3, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 5, -4));
		b.set(5, -4, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 5, 1));
		b.set(5, 1, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 5, 2));
		b.set(5, 2, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 5, 3));
		b.set(5, 3, 2);
		this.addChild(new StreetStraight(1, true, false, t.app_Strasse_g,
				t.app_Bürgersteig01, t.lightSurface, 1, 5, 4));
		b.set(5, 4, 2);

		this.addChild(new StreetIntersectionT(1, true, t.app_Strasse_t,
				t.app_Bürgersteig01, t.app_Bürgersteig02, 2, -5, 0));
		b.set(-5, 0, 2);
		this.addChild(new StreetIntersectionT(1, true, t.app_Strasse_t,
				t.app_Bürgersteig01, t.app_Bürgersteig02, 4, 5, 0));
		b.set(5, 0, 2);
		this.addChild(new StreetIntersectionT(1, true, t.app_Strasse_t,
				t.app_Bürgersteig01, t.app_Bürgersteig02, 1, 0, -5));
		b.set(0, -5, 2);
		this.addChild(new StreetIntersectionT(1, true, t.app_Strasse_t,
				t.app_Bürgersteig01, t.app_Bürgersteig02, 3, 0, 5));
		b.set(0, 5, 2);

		/******************************************************************************************************/
	}

	private void createAreas() {
		/******************************************************************************************************/
		// Hier alle Flächen erzeugen
		for (int y = -6; y <= 6; y++) {
			for (int x = -6; x <= 6; x++) {
				if (b.get(x, y) == 0) {
					if (y == -6 || x == 6) {
						this.addChild(new Field(true, t.ani_Water, x, y));
					} else {
						this.addChild(new Field(true, t.app_grass01, x, y));
					}
				}
			}
		}

		/******************************************************************************************************/
	}

	private void createHouses() {
		/******************************************************************************************************/
		// Hier alle Häuser erzeugen
		// Block oben links
		this.addChild(new HouseDual(true, -2, -1, 1, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke,
				t.Dach));

		this.addChild(new HouseDual(true, -3, -1, 1, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke,
				t.Dach));

		this.addChild(new HouseDual(true, -4, -1, 1, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke,
				t.Dach));

		this.addChild(new HouseSingle(true, 3, -1, -1, 1, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 2, -1, -2, 2, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 1, -1, -3, 2, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 4, -1, -4, 3, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 3, -2, -4, 3, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 4, -3, -4, 3, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 1, -4, -2, 4, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseDual(true, -4, -3, 4, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke,
				t.Dach));

		this.addChild(new HouseSingle(true, 3, -4, -4, 3, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		// Block oben rechts
		this.addChild(new HouseSingle(true, 4, 1, -4, 4, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseDual(true, 1, -3, 4, t.app_grass01, t.app_Water,
				t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand, t.Wand2Fenster,
				t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach));

		this.addChild(new HouseDual(true, 1, -2, 4, t.app_grass01, t.app_Water,
				t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand, t.Wand2Fenster,
				t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach));

		this.addChild(new HouseSingle(true, 3, 1, -1, 4, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 2, 2, -1, 1, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseDual(true, 3, -1, 1, t.app_grass01, t.app_Water,
				t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand, t.Wand2Fenster,
				t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach));

		this.addChild(new HouseSingle(true, 3, 4, -1, 1, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		// Block unten rechts
		this.addChild(new HouseDual(true, 1, 1, 4, t.app_grass01, t.app_Water,
				t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand, t.Wand2Fenster,
				t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach));

		this.addChild(new HouseSingle(true, 1, 1, 2, 4, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 2, 1, 3, 4, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseDual(true, 1, 4, 4, t.app_grass01, t.app_Water,
				t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand, t.Wand2Fenster,
				t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach));

		this.addChild(new HouseSingle(true, 4, 2, 4, 1, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 3, 3, 4, 1, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 1, 2, 1, 3, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 2, 3, 1, 3, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 3, 4, 1, 2, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseDual(true, 4, 2, 2, t.app_grass01, t.app_Water,
				t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand, t.Wand2Fenster,
				t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach));

		this.addChild(new HouseSingle(true, 4, 4, 3, 2, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		// Block unten links
		this.addChild(new HouseSingle(true, 5, -1, 2, 2, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 5, -1, 3, 2, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 5, -4, 2, 4, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 5, -4, 3, 4, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 5, -2, 1, 3, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 5, -3, 1, 3, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 5, -2, 4, 1, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));

		this.addChild(new HouseSingle(true, 5, -3, 4, 1, t.app_grass01,
				t.app_Water, t.Zaun, t.Wand1Fenster, t.Tür, t.TürWand,
				t.Wand2Fenster, t.Wand3Fenster, t.Wand0Fenster, t.Decke, t.Dach,
				t.Tür, t.Baum01));
		/******************************************************************************************************/
	}

	private void createPeople() {
		/******************************************************************************************************/
		// Hier alle Menschen erzeugen
		figure = new Figure(2);
		this.addChild(
				makeFigure(figure, 1.77, new Vector3d(-2.2, 0.10, 2.2), 90.0));

		figure2 = new Figure(1);
		this.addChild(makeFigure(figure2, 1.87, new Vector3d(2.2, 0.10, -2.2),
				-90.0));

		human1 = new Figure(2);
		this.addChild(
				makeFigure(human1, 1.72, new Vector3d(7.2, 0.10, -2.2), -90.0));
		human1.startTalking();

		human2 = new Figure(1);
		this.addChild(
				makeFigure(human2, 1.92, new Vector3d(6.5, 0.10, -2.2), 90.0));
		human2.startTalking();
		/******************************************************************************************************/
	}

	private void createPlants() {
		/******************************************************************************************************/
		// Hier alle Pflanzen erzeugen
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum01, 25, -3, 3));

		this.addChild(new Plant(2, 1.35, 2.0, 6.25, 3.75, t.Baum02));

		tree01 = new Tree(2, 6, 2.5, t.Rinde01, t.Baum03);
		this.addChild(tree01);
		tree01.moveTo(5.0, 5.0);
		tree01.moveTo(-5.0, -5.0);

		tree02 = new Tree(2, 6, 2.5, t.Rinde01, t.Baum02);
		this.addChild(tree02);
		tree02.moveTo(3.75, 3.75);

		// Für Block oben rechts
		// Baumreihe oben rechts oben
		for (int i = 0; i < 10; i++) {
			if (r.nextInt() == 0) {
				tree[i] = new Tree(2, 6, -3 + 6 * r.nextDouble(), t.Rinde01,
						t.Baum02);
			} else {
				tree[i] = new Tree(2, 6, -3 + 6 * r.nextDouble(), t.Rinde01,
						t.Baum03);
			}
			this.addChild(tree[i]);
			tree[i].moveTo(3.5 + i * 2, 21.5);

			if (i != 9) {
				if (i % 4 == 0) {
					this.addChild(new Plant(2, 1 + r.nextDouble(),
							1 + r.nextDouble(), 4.5 + i * 2, 21.5, t.Baum02));
				}
				if (i % 4 == 1) {
					this.addChild(
							new Plant(2, 1 + r.nextDouble(), 1 + r.nextDouble(),
									4.5 + i * 2, 21.5, t.Pflanze01));
				}
				if (i % 4 == 2) {
					this.addChild(
							new Plant(2, 1 + r.nextDouble(), 1 + r.nextDouble(),
									4.5 + i * 2, 21.5, t.Pflanze02));
				}
				if (i % 4 == 3) {
					this.addChild(
							new Plant(2, 1 + r.nextDouble(), 1 + r.nextDouble(),
									4.5 + i * 2, 21.5, t.Pflanze03));
				}
			}
		}

		// Baumreihe oben rechts rechts
		for (int i = 0; i < 9; i++) {
			tree[i] = new Tree(2, 6, -3 + 6 * r.nextDouble(), t.Rinde01,
					t.Baum02);
			this.addChild(tree[i]);
			tree[i].moveTo(21.5, 3.5 + i * 2);

			if (i != 9) {
				if (i % 4 == 0) {
					this.addChild(new Plant(2, 1 + r.nextDouble(),
							1 + r.nextDouble(), 21.5, 4.5 + i * 2, t.Baum02));
				}
				if (i % 4 == 1) {
					this.addChild(
							new Plant(2, 1 + r.nextDouble(), 1 + r.nextDouble(),
									21.5, 4.5 + i * 2, t.Pflanze01));
				}
				if (i % 4 == 2) {
					this.addChild(
							new Plant(2, 1 + r.nextDouble(), 1 + r.nextDouble(),
									21.5, 4.5 + i * 2, t.Pflanze02));
				}
				if (i % 4 == 3) {
					this.addChild(
							new Plant(2, 1 + r.nextDouble(), 1 + r.nextDouble(),
									21.5, 4.5 + i * 2, t.Pflanze03));
				}
			}
		}

		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 5, 4, 4));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 5, 3, 4));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 5, 2, 4));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 5, 4, 3));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 5, 4, 2));

		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 15, 3, 3));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 15, 2, 3));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 15, 3, 2));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 25, 2, 2));

		// Für Block oben links
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 15, -2, 2));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 15, -3, 2));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 15, -2, 3));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 15, -3, 3));

		tree[20] = new Tree(2, 6, -3 + 6 * r.nextDouble(), t.Rinde01, t.Baum02);
		this.addChild(tree[20]);
		tree[20].moveTo(-3.75, 3.75);

		this.addChild(new Plant(2, 0.5 + r.nextDouble(), 0.5 + r.nextDouble(),
				-3, 4.5, t.Baum02));
		this.addChild(new Plant(2, 0.5 + r.nextDouble(), 0.5 + r.nextDouble(),
				-4, 6.5, t.Pflanze01));

		tree[21] = new Tree(2, 6, -3 + 6 * r.nextDouble(), t.Rinde01, t.Baum02);
		this.addChild(tree[21]);
		tree[21].moveTo(-3.75, 20);

		this.addChild(new Plant(2, 0.5 + r.nextDouble(), 0.5 + r.nextDouble(),
				-3, 21.5, t.Pflanze02));
		this.addChild(new Plant(2, 0.5 + r.nextDouble(), 0.5 + r.nextDouble(),
				-4, 18.5, t.Pflanze03));

		// Für Block unten links
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum01, 15, -2, -2));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum01, 15, -2, -3));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum01, 15, -3, -2));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum01, 15, -3, -3));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum01, 15, -4, -4));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum01, 15, -1, -4));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 5, -1, -1));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 20, -4, -1));

		this.addChild(new Plant(2, 1.35, 2.0, -4, -6, t.Pflanze01));
		this.addChild(new Plant(2, 1.35, 2.0, -6, -4, t.Pflanze03));

		// Für Block unten rechts
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 20, 4, -4));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum01, 15, 2, -2));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum01, 15, 2, -3));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum01, 15, 3, -2));
		this.addChild(
				new TreeFlatField(true, t.app_grass01, t.Baum04, 20, 3, -3));

		// Für Rand aussen unten
		for (int i = 0; i < 12; i++) {
			this.addChild(new TreeFlatField(true, t.app_grass01, t.Baum04, 15,
					i - 6, -6));
		}

		// Für Rand aussen links
		for (int i = 0; i < 11; i++) {
			this.addChild(new TreeFlatField(true, t.app_grass01, t.Baum04, 15,
					-6, i - 5));
			/*****************************************************************************************************/
		}
	}

	private static TransformGroup makeFigure(final Figure t_figure,
			final double height, final Vector3d position,
			final double Drehung) {
		TransformGroup tg = new TransformGroup();
		final Transform3D scale = new Transform3D();
		final Transform3D pos = new Transform3D();
		final Transform3D rotY = new Transform3D();
		double scalefactor;

		scalefactor = height / 5.5;
		tg = t_figure.getFigureTG();

		scale.setScale(new Vector3d(scalefactor, scalefactor, scalefactor));
		pos.setTranslation(position);
		rotY.rotY(Drehung / 180.0 * Math.PI);

		scale.mul(rotY, scale);
		scale.mul(pos, scale);

		tg.setTransform(scale);
		return tg;

	} // end of addFigure()
}