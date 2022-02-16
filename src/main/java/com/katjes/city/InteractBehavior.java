package com.katjes.city;

import java.awt.AWTEvent;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.SceneGraphPath;
import javax.media.j3d.Transform3D;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Vector3d;

import com.katjes.city.graphics.TexturedPlane;
import com.sun.j3d.utils.behaviors.vp.ViewPlatformBehavior;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class InteractBehavior extends ViewPlatformBehavior {
	private static final double ROT_AMT = Math.PI / 90.0; // 2,5 degrees
	private static final double MOVE_STEP = 0.2;
	private static final double DOWNVIEW_LIMIT = 20; // in degree
	private static final double UPVIEW_LIMIT = 20; // in degree

	// hardwired movement vectors
	private static final Vector3d FWD = new Vector3d(0, 0, -MOVE_STEP);
	private static final Vector3d BACK = new Vector3d(0, 0, MOVE_STEP);
	private static final Vector3d LEFT = new Vector3d(-MOVE_STEP, 0, 0);
	private static final Vector3d RIGHT = new Vector3d(MOVE_STEP, 0, 0);
	private static final Vector3d UP = new Vector3d(0, MOVE_STEP, 0);
	private static final Vector3d DOWN = new Vector3d(0, -MOVE_STEP, 0);

	// key names
	private final int forwardKey = KeyEvent.VK_W;
	private final int backKey = KeyEvent.VK_S;
	private final int strafeleftKey = KeyEvent.VK_A;
	private final int straferightKey = KeyEvent.VK_D;
	private final int leftKey = KeyEvent.VK_LEFT;
	private final int rightKey = KeyEvent.VK_RIGHT;
	private final int downKey = KeyEvent.VK_UP;
	private final int upKey = KeyEvent.VK_DOWN;
	private final int threadStart = KeyEvent.VK_Z;
	private final int threadStop = KeyEvent.VK_M;
	private final int Windr = KeyEvent.VK_P;
	private final int Windl = KeyEvent.VK_O;
	private final int start_Rain = KeyEvent.VK_L;
	private final int stop_Rain = KeyEvent.VK_K;
	private final int DelayPlus = KeyEvent.VK_J;
	private final int DelayMinus = KeyEvent.VK_H;
	private final int DelayInfo = KeyEvent.VK_I;

	private final WakeupCriterion[] Interaktion_WuC = new WakeupCriterion[3];
	private final City city;

	// for repeated calcs
	private final Transform3D t3d = new Transform3D();
	private final Transform3D toMove = new Transform3D();
	private final Transform3D toRot = new Transform3D();
	private final Transform3D toRot2 = new Transform3D();

	// for mouse resetting
	private Robot r;
	private boolean notMove = false;

	private int x_old = 400;
	private int y_old = 300;

	private static boolean ESC_pressed = false;

	private double XRotPos;

	WakeupOr Interaktion;

	private int Mode = 1; // 1:free;2:human;3:car

	public InteractBehavior(final City obj) {
		city = obj;
		Interaktion_WuC[1] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		Interaktion_WuC[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED);
		Interaktion = new WakeupOr(Interaktion_WuC);

	} // end of InteraktBehavior()

	public InteractBehavior(final City obj, final ViewingPlatform vp) {
		city = obj;
		Interaktion_WuC[1] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		Interaktion_WuC[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED);
		Interaktion = new WakeupOr(Interaktion_WuC);

	} // end of InteraktBehavior()

	public InteractBehavior(final City obj, final BranchGroup col_TG) {
		city = obj;
		Interaktion_WuC[1] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		Interaktion_WuC[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED);
		Interaktion_WuC[2] = new WakeupOnCollisionEntry(col_TG);
		Interaktion = new WakeupOr(Interaktion_WuC);

	} // end of InteraktBehavior()

	@Override
	public void initialize() {
		XRotPos = 0.0;
		try {
			r = new Robot();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		wakeupOn(Interaktion);
	}

	@Override
	public void processStimulus(final Enumeration criteria) {
		// respond to a keypress

		WakeupCriterion wakeup;
		AWTEvent[] event;

		while (criteria.hasMoreElements()) {
			wakeup = (WakeupCriterion) criteria.nextElement();

			// Collision detection
			if (wakeup instanceof WakeupOnCollisionEntry) {

				final WakeupOnCollisionEntry w = (WakeupOnCollisionEntry) wakeup;
				// Kollisionsobjekt ermitteln:
				final SceneGraphPath p = w.getTriggeringPath();
				final Node n = p.getObject();
				if (n instanceof TexturedPlane) {
					System.out.println("unwichtige Kollision !!");
				} else {
					System.out.println("Kollision mit Planeten mit " + n);
				}

			}

			// Movement detection
			if (wakeup instanceof WakeupOnAWTEvent) {
				event = ((WakeupOnAWTEvent) wakeup).getAWTEvent();
				for (final AWTEvent element : event) {
					if (element.getID() == KeyEvent.KEY_PRESSED
							&& ESC_pressed != true) {
						processKeyEvent((KeyEvent) element);
					} else if (element.getID() == MouseEvent.MOUSE_MOVED
							&& ESC_pressed != true) {
						processMouseEvent((MouseEvent) element);
					}
				}
			}
		}
		wakeupOn(Interaktion);
	} // end of processStimulus()

	private void processKeyEvent(final KeyEvent eventKey) {
		final int keyCode = eventKey.getKeyCode();

		if (keyCode == threadStart) {
			city.CO.figure2.startWalking();
			city.CO.figure.startWalking();

		} else if (keyCode == threadStop) {
			System.out.println("Thread Stop-Aufforderung gesendet !");
			city.CO.figure2.stopWalking();
			city.CO.figure.stopWalking();

		} else if (keyCode == Windr) {
			city.CO.tree01.windItRelative(5.0);
		} else if (keyCode == Windl) {
			city.CO.tree01.windItRelative(-5.0);
		} else if (keyCode == start_Rain) {
			// city.a.startAni();
			city.EC.DNC.FadeToDay();
			// new Thread(city.a).start(); schon vorher auskommentiert
		} else if (keyCode == stop_Rain) {
			// city.a.stopAni();
			city.EC.DNC.FadeToNight();
		} else if (keyCode == DelayPlus) {
			if (city.EC.a.Rain.getDelay() <= 950) {
				city.EC.a.Rain.setDelay(city.EC.a.Rain.getDelay() + 5);
				System.out.println(city.EC.a.Rain.getDelay());
			}

		} else if (keyCode == DelayMinus) {
			if (city.EC.a.Rain.getDelay() >= 10) {
				city.EC.a.Rain.setDelay(city.EC.a.Rain.getDelay() - 5);
				System.out.println(city.EC.a.Rain.getDelay());
			}
		} else if (keyCode == DelayInfo) {
			System.out.println("Rain : " + city.EC.a.Rain.getDelay() + " ; "
					+ "Splash : " + city.EC.a.Splash.getDelay() + " ; "
					+ "Splash_up : " + city.EC.a.Splash_up.getDelay() + " ; ");
		} else {
			standardMove(keyCode);
		}

	} // end of processKeyEvent()

	private void processMouseEvent(final MouseEvent eventMouse) {
		int x;
		int y;

		// System.out.println("Mouse event...");

		x = eventMouse.getX();
		y = eventMouse.getY();

		if (x > x_old) {
			// rotate right
			// System.out.println("rotate right...");
			switch (Mode) {
			case 1: {
				rotateY(-ROT_AMT);
				break;
			}
			case 2: {
				rotateY_human(-ROT_AMT);
				break;
			}
			case 3: {
				//
				break;
			}
			}
		} else if (x < x_old) {
			// rotate left
			// System.out.println("rotate left...");
			switch (Mode) {
			case 1: {
				rotateY(ROT_AMT);
				break;
			}
			case 2: {
				rotateY_human(ROT_AMT);
				break;
			}
			case 3: {
				//
				break;
			}
			}
		}

		if (y < y_old) {
			// rotate down
			// System.out.println("rotate down...");
			switch (Mode) {
			case 1: {
				rotateX(ROT_AMT);
				break;
			}
			case 2: {
				rotateX_human(ROT_AMT);
				break;
			}
			case 3: {
				//
				break;
			}
			}
		} else if (y > y_old) {
			// rotate up
			// System.out.println("rotate up...");
			switch (Mode) {
			case 1: {
				rotateX(-ROT_AMT);
				break;
			}
			case 2: {
				rotateX_human(-ROT_AMT);
				break;
			}
			case 3: {
				//
				break;
			}
			}
		}

		x_old = x;
		y_old = y;
	} // end of processMouseEvent()

	private void standardMove(final int keycode)
	/*
	 * Make viewer moves forward or backward; rotate left or right;
	 */
	{
		if (keycode == forwardKey) {
			switch (Mode) {
			case 1: {
				doMove(FWD);
				break;
			}
			case 2: {
				doMove_human(FWD);
				break;
			}
			case 3: {
				//
				break;
			}
			}
			// System.out.println("Bewege vorwärts...");
		} else if (keycode == backKey) {
			switch (Mode) {
			case 1: {
				doMove(BACK);
				break;
			}
			case 2: {
				doMove_human(BACK);
				break;
			}
			case 3: {
				//
				break;
			}
			}
			// System.out.println("Bewege rückwärts...");
		} else if (keycode == leftKey) {
			switch (Mode) {
			case 1: {
				rotateY(ROT_AMT);
				break;
			}
			case 2: {
				rotateY_human(ROT_AMT);
				break;
			}
			case 3: {
				//
				break;
			}
			}
		} else if (keycode == rightKey) {
			switch (Mode) {
			case 1: {
				rotateY(-ROT_AMT);
				break;
			}
			case 2: {
				rotateY_human(-ROT_AMT);
				break;
			}
			case 3: {
				//
				break;
			}
			}
			// System.out.println("Drehe rechts...");
		} else if (keycode == straferightKey) {
			switch (Mode) {
			case 1: {
				doMove(RIGHT);
				break;
			}
			case 2: {
				doMove_human(RIGHT);
				break;
			}
			case 3: {
				//
				break;
			}
			}
			// System.out.println("Gleite rechts...");
		} else if (keycode == strafeleftKey) {
			switch (Mode) {
			case 1: {
				doMove(LEFT);
				break;
			}
			case 2: {
				doMove_human(LEFT);
				break;
			}
			case 3: {
				//
				break;
			}
			}
			// System.out.println("Gleite links...");
		} else if (keycode == downKey) {
			switch (Mode) {
			case 1: {
				doMove(UP);
				// rotateX(ROT_AMT);
				break;
			}
			case 2: {
				doMove_human(UP);
				// rotateX_human(ROT_AMT);
				break;
			}
			case 3: {
				//
				break;
			}
			}
			// System.out.println("Kippe vorne...");
		} else if (keycode == upKey) {
			switch (Mode) {
			case 1: {
				doMove(DOWN);
				// rotateX(-ROT_AMT);
				break;
			}
			case 2: {
				doMove_human(DOWN);
				// rotateX_human(-ROT_AMT);
				break;
			}
			case 3: {
				//
				break;
			}
			}
			// System.out.println("Kippe hinten...");
		}

	} // end of standardMove()

	private void rotateY(final double radians)
	// rotate about y-axis by radians
	{
		targetTG.getTransform(t3d); // targetTG is the ViewPlatform's tranform
		toRot.rotY(radians);
		t3d.mul(toRot);
		targetTG.setTransform(t3d);
	} // end of rotateY()

	private void rotateY_human(final double radians)
	// rotate about y-axis by radians
	{
		targetTG.getTransform(t3d); // targetTG is the ViewPlatform's tranform
		toRot2.rotX(-XRotPos);
		toRot.rotY(radians);
		t3d.mul(toRot2);
		t3d.mul(toRot);
		toRot2.rotX(XRotPos);
		t3d.mul(toRot2);
		targetTG.setTransform(t3d);

		city.EC.a.rotateX(XRotPos, true);
	} // end of rotateY_human()

	private void rotateX(final double radians)
	// rotate about x-axis by radians
	{
		targetTG.getTransform(t3d); // targetTG is the ViewPlatform's tranform
		toRot.rotX(radians);
		t3d.mul(toRot);
		targetTG.setTransform(t3d);
	} // end of rotateX()

	private void rotateX_human(final double radians)
	// rotate about x-axis by radians
	{
		if (setXRotPos(radians)) {
			targetTG.getTransform(t3d); // targetTG is the ViewPlatform's tranform
			toRot.rotX(radians);
			t3d.mul(toRot);
			targetTG.setTransform(t3d);

			city.EC.a.rotateX(XRotPos, false);
			// System.out.println("XRotPos : " + XRotPos);
		}
	} // end of rotateX_human()

	private void doMove(final Vector3d theMove) {
		// performs movement
		targetTG.getTransform(t3d);
		toMove.setTranslation(theMove);
		t3d.mul(toMove);
		targetTG.setTransform(t3d);
	} // end of doMove()

	private void doMove_human(final Vector3d theMove) {
		// performs movement
		targetTG.getTransform(t3d);
		toRot2.rotX(-XRotPos);
		toMove.setTranslation(theMove);
		t3d.mul(toRot2);
		t3d.mul(toMove);
		toRot2.rotX(XRotPos);
		t3d.mul(toRot2);
		targetTG.setTransform(t3d);

		city.EC.a.rotateX(XRotPos, true);
	} // end of doMove_human()

	private boolean setXRotPos(final double radians) {
		boolean ret = false;

		if (XRotPos + radians <= Math.PI / 2 - Math.toRadians(UPVIEW_LIMIT)
				&& XRotPos + radians >= -Math.PI / 2
						+ Math.toRadians(DOWNVIEW_LIMIT)) {
			XRotPos += radians;
			ret = true;
		} else {
			ret = false;
		}

		return ret;
	}

	public static void setESC_pressed(final boolean e) {
		ESC_pressed = e;
	}

	public static boolean getESC_pressed() {
		return ESC_pressed;
	}

	public void setMode(final int mode) {
		Mode = mode;
	}

	public void resetMousePosition() {
		final int x_JFrame = city.City.getX();
		final int y_JFrame = city.City.getY();
		final int x_Canvas3D = city.c.getX();
		final int y_Canvas3D = city.c.getY();
		final int Width_Canvas3D = city.c.getWidth();
		final int Height_Canvas3D = city.c.getHeight();

		notMove = true;
		System.out.println("JFrame:x=" + x_JFrame + ";y=" + y_JFrame
				+ ";Canvas3D:x=" + x_Canvas3D + ";y=" + y_Canvas3D + ";w="
				+ Width_Canvas3D + ";h=" + Height_Canvas3D);

		try {
			x_old = Width_Canvas3D;
			y_old = Height_Canvas3D;
			r.mouseMove(x_JFrame + x_Canvas3D + Width_Canvas3D / 2,
					y_JFrame + y_Canvas3D + Height_Canvas3D / 2);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		notMove = false;
	}

} // end of KeyBehavior class
