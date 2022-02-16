package com.katjes.city;
/*
 * City.java
 */

import java.awt.Cursor;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.MemoryImageSource;

import javax.media.j3d.Appearance;
import javax.media.j3d.AudioDevice;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.JFrame;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.katjes.city.objects.CityObjects;
import com.katjes.city.objects.CitySounds;
import com.sun.j3d.audioengines.javasound.JavaSoundMixer;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.picking.PickIntersection;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
/**
 *
 * @author Katjes
 */
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class City extends JFrame {
	// Festlegung eines Hauptpfades
	private static final double Start_x = 2.0;
	private static final double Start_y = 0.9;
	private static final double Start_z = 10.0;

	// static Component beobachter;

	static Wait myWait = new Wait();
	static City City;
	static SimpleUniverse su;
	static Background bg;
	static Canvas3D c;
	static BranchGroup ursprung;
	static BranchGroup city_BG;
	static BranchGroup streets_TG;
	static TransformGroup buildings_TG;
	static BoundingSphere begrenzung;

	static Appearance bg_App;

	static InteractBehavior IB;
	public static ViewingPlatform vp;

	// static Figure figure,figure2;
	// static Tree tree01,tree02;

	public static EnviromentalChanging EC;

	static Thread Attach;

	static CityObjects CO;
	static CitySounds CS;

	static boolean Maus_in_Canvas3D;
	static boolean ESC_pressed = true;

	Wait myWait01 = new Wait();

	Cursor CDefault = new Cursor(DEFAULT_CURSOR);
	int[] pixels = new int[16 * 16];
	Image image = Toolkit.getDefaultToolkit()
			.createImage(new MemoryImageSource(16, 16, pixels, 0, 16));
	Cursor CInvisible = Toolkit.getDefaultToolkit().createCustomCursor(image,
			new Point(0, 0), "InvisibleCursor");

	// static besetzt b;

	static Point3d StartPunkt;

	static double view_x;
	static double view_y;
	static double view_z;

	private final PickTool picker; // global

	public City() {
		StartPunkt = new Point3d(Start_x, Start_y, Start_z);
		picker = new PickTool(streets_TG); // only check the floor
		picker.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
		initCity();

	}

	public void initCity() {
		System.out.println("Initializing World...");

		getContentPane().setLayout(null);
		final GraphicsConfiguration CG = SimpleUniverse
				.getPreferredConfiguration();
		myWait01.progress(70);
		c = new Canvas3D(CG);

		c.addMouseListener(new Mausbetritt());
		c.addKeyListener(new Key_Pressed());
		c.addMouseMotionListener(new Mausbewegung());
		c.setDoubleBufferEnable(true);

		c.setBounds(0, 0, 800, 600);
		getContentPane().add(c);
		su = new SimpleUniverse(c);

		myWait01.progress(80);
		su.getViewingPlatform().setNominalViewingTransform();
		myWait01.progress(85);
		su.addBranchGraph(prepare_City());
		myWait01.progress(90);

		createAudioDevice();

		// Frame definieren
		setTitle("City");
		setSize(806, 632);
		setResizable(false);
		setVisible(true);

		myWait01.progress(100);
	}

	private void createAudioDevice() {
		System.out.println("Initializing AudioDevice...");
		final PhysicalEnvironment environment = su.getViewer()
				.getPhysicalEnvironment();
		final AudioDevice audioDev = new JavaSoundMixer(environment);
		audioDev.initialize();
		// environment.setAudioDevice(audioDev);
	}

	public static BranchGroup prepare_City() {
		ursprung = new BranchGroup();

		city_BG = new BranchGroup();
		city_BG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		city_BG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		streets_TG = new BranchGroup();
		// streets_TG.setCapability(BranchGroup.ALLOW_TRANSFORM_WRITE);
		// streets_TG.setCapability(BranchGroup.ALLOW_TRANSFORM_READ);

		buildings_TG = new TransformGroup();
		buildings_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		buildings_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		ursprung.addChild(city_BG);

		begrenzung = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 4000.0);

		// Hintergrund:
		bg = new Background();
		bg.setApplicationBounds(begrenzung);
		final BranchGroup backGeoBranch = new BranchGroup();
		final Sphere sphereObj = new Sphere(1.0f,
				Sphere.GENERATE_NORMALS | Sphere.GENERATE_NORMALS_INWARD
						| Sphere.GENERATE_TEXTURE_COORDS,
				45);
		bg_App = sphereObj.getAppearance();
		bg_App.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
		bg_App.setCapability(Appearance.ALLOW_TEXTURE_READ);
		backGeoBranch.addChild(sphereObj);
		bg.setGeometry(backGeoBranch);
		city_BG.addChild(bg);

		// Textur_laden("sky02.jpg",bg_App);

		bg.setCapability(Background.ALLOW_IMAGE_WRITE);
		bg.setCapability(Background.ALLOW_IMAGE_READ);
		bg.setCapability(Background.ALLOW_COLOR_READ);
		bg.setCapability(Background.ALLOW_COLOR_WRITE);

		// adjust viewpoint parameters
		final View userView = su.getViewer().getView();
		// userView.setFieldOfView( Math.toRadians(90.0)); // wider FOV
		// 10 and 0.1; keep ratio between 100-1000
		userView.setBackClipDistance(20); // can see a long way
		userView.setFrontClipDistance(0.05); // can see close things
		userView.setSceneAntialiasingEnable(true); // Anti-Aliasing disabled
		// Sorgt für korrekte Sortierung von transparenten Objekten
		userView.setTransparencySortingPolicy(View.TRANSPARENCY_SORT_GEOMETRY);

		vp = su.getViewingPlatform();
		// PlatformGeometry pg = HeadupDisplay();
		// vp.setPlatformGeometry(pg);

		// Start-Position festlegen
		final TransformGroup Pos_TG = vp.getViewPlatformTransform();
		final Transform3D Pos_t3d = new Transform3D();
		Pos_t3d.setTranslation(new Vector3d(Start_x, Start_y, Start_z));
		Pos_TG.setTransform(Pos_t3d);

		// Interaktionsmöglichkeit festlegen
		IB = new InteractBehavior(City, city_BG);
		IB.setSchedulingBounds(begrenzung);
		IB.setMode(2);
		vp.setViewPlatformBehavior(IB);

		generate_scenery();

		ursprung.compile();
		return ursprung;
	}

	// Erstellung der virtuellen Welt
	// im hinblick auf Fertigstellung :
	// unbedingt aufräumen, d.h. den teil, der der erstellung der Szenery dient, in extra methode,
	// appearances in extra methode etc.
	private static void generate_scenery() {
		// streets_TG.addChild(new baum_Feld(t.app_grass01,"Wald01.3DS",30,0,0));
		// a little tree *LOL*
		// streets_TG.addChild(new Flat_Tree(2.0,4.0,10.0,5.0,t.Baum01));

		/*

		*/

		CO = new CityObjects();
		streets_TG.addChild(CO);
		city_BG.addChild(streets_TG);

		CS = new CitySounds();
		streets_TG.addChild(CS);

		// CS.backSound[0].setEnable(true);

		EC = new EnviromentalChanging(City);
		new Thread(EC).start();

	}

	public Point3d getLandHeight(final double x, final double z,
			final double currHeight) {
		final Point3d pickStart = new Point3d(x, 2, z); // start high
		picker.setShapeRay(pickStart, new Vector3d(0.0, -1.0, 0.0)); // shoot ray downwards
		final PickResult picked = picker.pickClosest();
		if (picked != null) { // pick sometimes misses an edge/corner
			if (picked.numIntersections() != 0) { // sometimes no intersects
				final PickIntersection pi = picked.getIntersection(0);
				Point3d nextPt;
				try { // handles 'Interp point outside quad' error
					nextPt = pi.getPointCoordinates();
				} catch (final Exception e) {
					System.out.println(e);
					return null;
				}
				return nextPt;
			}
		}
		return null; // if we reach here, return old height
	}

	public static void main(final String[] args) {
		City = new City();
	}

	class Mausbetritt extends MouseAdapter {
		@Override
		public void mouseEntered(final MouseEvent event) {
			// City.c.setCursor(CInvisible);
			Maus_in_Canvas3D = true;
			// ESC_pressed = false;
			// IB.setESC_pressed (false);
			City.c.requestFocus();
		}

		@Override
		public void mouseExited(final MouseEvent event) {
			// City.c.setCursor(CDefault);
			if (IB.getESC_pressed() == false) {
				IB.resetMousePosition();
			} else {
				Maus_in_Canvas3D = false;
				City.c.transferFocus();
			}
		}
	}

	class Key_Pressed extends KeyAdapter {

		@Override
		public void keyPressed(final KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_ESCAPE
					&& Maus_in_Canvas3D == true) {
				City.c.setCursor(CDefault);
				ESC_pressed = true;
				IB.setESC_pressed(true);
			}
		}

		@Override
		public void keyReleased(final KeyEvent event) {

		}
	}

	class Mausbewegung extends MouseMotionAdapter {

		@Override
		public void mouseMoved(final MouseEvent event) {
			if (event.getX() >= City.c.getWidth() / 2 - 30
					&& event.getX() <= City.c.getWidth() / 2 + 30
					&& event.getY() >= City.c.getHeight() / 2 - 30
					&& event.getY() <= City.c.getHeight() / 2 + 30) {
				City.c.setCursor(CInvisible);
				ESC_pressed = false;
				IB.setESC_pressed(false);
			}

		}

		@Override
		public void mouseDragged(final MouseEvent event) {
			mouseMoved(event);
		}

	}

}
