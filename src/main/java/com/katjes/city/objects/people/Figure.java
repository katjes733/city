package com.katjes.city.objects.people;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Figure.java
// Thana Konglikhit, October 2003, s4310170@maliwan.psu.ac.th
// Andrew Davison, dandrew@ratree.psu.ac.th

/*
 * The Figure class carries out three main tasks: 1. constructs the figure by connecting Limb objects; 2. processes limb
 * commands by passing them onto the relavant Limb objects 3. processes figure commands by moving or rotating the
 * TransformGroup for the figure (figureTG). The Limb hierarchy: Limb |--------------------- | | MoveableLimb
 * EllipticLimb | | MoveableEllipticLimb Limb defines the appearance of a limb (using a Lathe shape), and how it is
 * connected to other limbs via joints. The limb's initial orientation is set. Limb and EllipticLimb cannot be moved,
 * and do not use limb names. The MoveableLimb and MoveableEllipticLimb classes are moveable. They have limb names, and
 * x-, y-, z- axis ranges in which they can be rotated. If a range is not specified, then it is assumed to be 0 (i.e.
 * rotation is not possible around that axis). The figure's component limb objects are stored in the limbs ArrayList. A
 * limb object is stored in the ArrayList at the position specified by its limb number. A limbNames hashmap stores (limb
 * name, no.) pairs. It is used to determine a limb's number when a limb name is supplied by the user in a limb command.
 */
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

public class Figure extends Thread {

	// figure movement constants
	private final static double MOVERATE = 0.12;
	private final static double ROTATE_AMT = Math.PI / 16.0; // 11.25 degrees

	private final static int FWD = 0;
	private final static int BACK = 1;
	private final static int LEFT = 2;
	private final static int RIGHT = 3;
	private final static int UP = 4;
	private final static int DOWN = 5;

	private final static int CLOCK = 0; // clockwise turn
	private final static int CCLOCK = 1; // counter clockwise

	// axis constants
	private final static int X_AXIS = 0;
	private final static int Y_AXIS = 1;
	private final static int Z_AXIS = 2;

	// step to move in each direction
	private static final Vector3d fwdVec = new Vector3d(0, 0, MOVERATE);
	private static final Vector3d backVec = new Vector3d(0, 0, -MOVERATE);

	private static final Vector3d leftVec = new Vector3d(-MOVERATE, 0, 0);
	private static final Vector3d rightVec = new Vector3d(MOVERATE, 0, 0);

	private static final Vector3d upVec = new Vector3d(0, MOVERATE / 4, 0);
	private static final Vector3d downVec = new Vector3d(0, -MOVERATE / 4, 0);

	private String bottom;
	private String chest;
	private String neck;
	private String head;
	private String upper_right_arm;
	private String lower_right_arm;
	private String right_hand;
	private String upper_left_arm;
	private String lower_left_arm;
	private String left_hand;
	private String upper_right_leg;
	private String lower_right_leg;
	private String right_foot;
	private String upper_left_leg;
	private String lower_left_leg;
	private String left_foot;

	private Thread figureThread;
	private boolean ErstStart_durchgeführt = false;
	private static final int walkingFrames = 16;
	private static final int talkingFrames = 18;
	private boolean walking = false;
	private boolean talking = false;

	private final List<Limb> limbs;
	// Arraylist of Limb objects, indexed by limb number

	private final Map<String, Integer> limbNames;
	// holds (name, limbNo) pairs of visible limbs

	// for moving the figure as a whole
	private TransformGroup figureTG;
	private final Transform3D t3d, toMove, toRot; // for TG operations
	private int yCount; // used to position the figure along the y-axis

	public Figure(final int prefab) {
		yCount = 0; // the figure is on the floor initially

		setOutfit(prefab);

		t3d = new Transform3D();
		toMove = new Transform3D();
		toRot = new Transform3D();

		limbs = new ArrayList<>();
		limbNames = new HashMap<>();

		// construct the figure in parts (connected Limb objects)
		buildTorso();
		buildHead();

		buildRightArm();
		buildLeftArm();

		buildRightLeg();
		buildLeftLeg();

		printLimbsInfo();

		buildFigureGraph(); // convert the figure into a Java 3D graph

		figureThread = new Thread(this);
	} // end of Figure()

	public Figure(final String bottom, final String chest, final String neck,
			final String head, final String upper_right_arm,
			final String lower_right_arm, final String right_hand,
			final String upper_left_arm, final String lower_left_arm,
			final String left_hand, final String upper_right_leg,
			final String lower_right_leg, final String right_foot,
			final String upper_left_leg, final String lower_left_leg,
			final String left_foot) {
		yCount = 0; // the figure is on the floor initially

		this.bottom = bottom;
		this.chest = chest;
		this.neck = neck;
		this.head = head;
		this.upper_right_arm = upper_right_arm;
		this.lower_right_arm = lower_right_arm;
		this.right_hand = right_hand;
		this.upper_left_arm = upper_left_arm;
		this.lower_left_arm = lower_left_arm;
		this.left_hand = left_hand;
		this.upper_right_leg = upper_right_leg;
		this.lower_right_leg = lower_right_leg;
		this.right_foot = right_foot;
		this.upper_left_leg = upper_left_leg;
		this.lower_left_leg = lower_left_leg;
		this.left_foot = left_foot;

		t3d = new Transform3D();
		toMove = new Transform3D();
		toRot = new Transform3D();

		limbs = new ArrayList<>();
		limbNames = new HashMap<>();

		// construct the figure in parts (connected Limb objects)
		buildTorso();
		buildHead();

		buildRightArm();
		buildLeftArm();

		buildRightLeg();
		buildLeftLeg();

		printLimbsInfo();

		buildFigureGraph(); // convert the figure into a Java 3D graph
	} // end of Figure()

	private void buildTorso()
	/*
	 * A limb requires a limb number, its start and end joints, an axis of orientation and angle to that axis, and lathe
	 * shape coordinates and texture. If the limb will be moveable (i.e. a MoveableLimb or MoveableEllipticLimb object),
	 * then it also requires a name and x-, y-, z- ranges to restrict its movements.
	 */
	{
		// the figure's bottom
		final double xsIn1[] = { 0, -0.1, 0.22, -0.2, 0.001 };
		final double ysIn1[] = { 0, 0.03, 0.08, 0.25, 0.25 };
		final EllipticLimb limb1 = new EllipticLimb(1, "j0", "j1", Z_AXIS, 0,
				xsIn1, ysIn1, bottom);
		// no movement, so no name or ranges

		// the figure's chest: moveable so has a name ("chest") and rotation ranges
		final double xsIn2[] = { -0.001, -0.2, 0.36, 0.001 };
		final double ysIn2[] = { 0, 0, 0.50, 0.68 };
		final MoveableEllipticLimb limb2 = new MoveableEllipticLimb("chest", 2,
				"j1", "j2", Z_AXIS, 0, xsIn2, ysIn2, chest);
		limb2.setRanges(0, 120, -60, 60, -40, 40);
		// x range: 0 to 120; y range: -60 to 60; z range: -40 to 40

		limbs.add(limb1);
		limbs.add(limb2);

		limbNames.put("chest", new Integer(2));
		// store (name,number) pairs in limbNames
	} // end of buildTorso()

	private void buildHead() {
		// figure's neck
		final double xsIn3[] = { -0.001, -0.09, -0.09, 0.001 };
		final double ysIn3[] = { 0, 0, 0.1, 0.1 };
		final Limb limb3 = new Limb(3, "j2", "j3", Z_AXIS, 0, xsIn3, ysIn3,
				neck);
		// no movement

		// figure's head
		final double xsIn4[] = { -0.001, 0.09, 0.17, 0 };
		final double ysIn4[] = { 0, 0, 0.2, 0.4 };
		final MoveableLimb limb4 = new MoveableLimb("head", 4, "j3", "j4",
				Z_AXIS, 0, xsIn4, ysIn4, head);
		limb4.setRanges(-40, 40, -40, 40, -30, 30);

		limbs.add(limb3);
		limbs.add(limb4);

		limbNames.put("head", new Integer(4));
	} // end of buildHead()

	private void buildRightArm() {
		/*
		 * An invisible limb connecting the neck and upper right arm. It is invisible since no lathe shape coords or
		 * texture are supplied. However, it does have a length (0.35).
		 */
		final Limb limb5 = new Limb(5, "j2", "j5", Z_AXIS, 95, 0.35);

		// upper right arm
		final double xsIn6[] = { 0, 0.1, 0.08, 0 };
		final double ysIn6[] = { 0, 0.08, 0.45, 0.55 };
		final MoveableLimb limb6 = new MoveableLimb("urArm", 6, "j5", "j6",
				Z_AXIS, 80, xsIn6, ysIn6, upper_right_arm);
		limb6.setRanges(-60, 180, -90, 90, -90, 30);

		// lower right arm
		final double xsIn7[] = { 0, 0.08, 0.055, 0 };
		final double ysIn7[] = { 0, 0.08, 0.38, 0.43 };
		final MoveableLimb limb7 = new MoveableLimb("lrArm", 7, "j6", "j7",
				Z_AXIS, 5, xsIn7, ysIn7, lower_right_arm);
		limb7.setRanges(0, 150, -90, 90, -90, 90);

		// right hand
		final double xsIn8[] = { 0, 0.06, 0.04, 0 };
		final double ysIn8[] = { 0, 0.07, 0.16, 0.2 };
		final MoveableEllipticLimb limb8 = new MoveableEllipticLimb("rHand", 8,
				"j7", "j8", Z_AXIS, 0, xsIn8, ysIn8, right_hand);
		limb8.setRanges(-50, 50, -40, 90, -40, 40);

		limbs.add(limb5);
		limbs.add(limb6);
		limbs.add(limb7);
		limbs.add(limb8);

		limbNames.put("urArm", new Integer(6));
		limbNames.put("lrArm", new Integer(7));
		limbNames.put("rHand", new Integer(8));
	} // end of buildRightArm()

	private void buildLeftArm()
	// very similar to buildRightArm()
	{
		// invisible limb connecting the neck and upper left arm
		final Limb limb9 = new Limb(9, "j2", "j9", Z_AXIS, -95, 0.35);

		// upper left arm
		final double xsIn10[] = { 0, 0.1, 0.08, 0 };
		final double ysIn10[] = { 0, 0.08, 0.45, 0.55 };
		final MoveableLimb limb10 = new MoveableLimb("ulArm", 10, "j9", "j10",
				Z_AXIS, -80, xsIn10, ysIn10, upper_left_arm);
		limb10.setRanges(-60, 180, -90, 90, -30, 90);

		// lower left arm
		final double xsIn11[] = { 0, 0.08, 0.055, 0 };
		final double ysIn11[] = { 0, 0.08, 0.38, 0.43 };
		final MoveableLimb limb11 = new MoveableLimb("llArm", 11, "j10", "j11",
				Z_AXIS, -5, xsIn11, ysIn11, lower_left_arm);
		limb11.setRanges(0, 150, -90, 90, -90, 90);

		// left hand
		final double xsIn12[] = { 0, 0.06, 0.04, 0 };
		final double ysIn12[] = { 0, 0.07, 0.16, 0.2 };
		final MoveableEllipticLimb limb12 = new MoveableEllipticLimb("lHand",
				12, "j11", "j12", Z_AXIS, 0, xsIn12, ysIn12, left_hand);
		limb12.setRanges(-50, 50, -90, 40, -40, 40);

		limbs.add(limb9);
		limbs.add(limb10);
		limbs.add(limb11);
		limbs.add(limb12);

		limbNames.put("ulArm", new Integer(10));
		limbNames.put("llArm", new Integer(11));
		limbNames.put("lHand", new Integer(12));
	} // end of buildLeftArm()

	private void buildRightLeg() {
		// invisible limb connecting the bottom and upper right leg
		final Limb limb13 = new Limb(13, "j0", "j13", Z_AXIS, 50, 0.20);

		// upper right leg
		final double xsIn14[] = { 0, 0.12, 0.1, 0 };
		final double ysIn14[] = { 0, 0.1, 0.6, 0.7 };
		final MoveableLimb limb14 = new MoveableLimb("urLeg", 14, "j13", "j14",
				Z_AXIS, 130, xsIn14, ysIn14, upper_right_leg);
		limb14.setRanges(-45, 80, -20, 20, -45, 30);

		// lower right leg
		final double xsIn15[] = { 0, 0.10, 0.06, 0 };
		final double ysIn15[] = { 0, 0.15, 0.62, 0.7 };
		final MoveableLimb limb15 = new MoveableLimb("lrLeg", 15, "j14", "j15",
				Z_AXIS, 0, xsIn15, ysIn15, lower_right_leg);
		limb15.setRange(X_AXIS, -120, 0);

		// invisible limb connecting lower right leg and right foot
		final Limb limb16 = new Limb(16, "j15", "j16", Z_AXIS, 0, 0.07);

		// right foot
		final double xsIn17[] = { 0, 0.08, 0.06, 0 };
		final double ysIn17[] = { 0, 0.07, 0.21, 0.25 };
		final MoveableEllipticLimb limb17 = new MoveableEllipticLimb("rFoot",
				17, "j16", "j17", X_AXIS, 90, xsIn17, ysIn17, right_foot);
		limb17.setRanges(-90, 0, 0, 0, -30, 30);

		limbs.add(limb13);
		limbs.add(limb14);
		limbs.add(limb15);
		limbs.add(limb16);
		limbs.add(limb17);

		limbNames.put("urLeg", new Integer(14));
		limbNames.put("lrLeg", new Integer(15));
		limbNames.put("rFoot", new Integer(17));
	} // end of buildRightLeg()

	private void buildLeftLeg()
	// very similar to buildRightLeg()
	{
		// invisible limb connecting the bottom and upper left leg
		final Limb limb18 = new Limb(18, "j0", "j18", Z_AXIS, -50, 0.20);

		// upper left leg
		final double xsIn19[] = { 0, 0.12, 0.1, 0 };
		final double ysIn19[] = { 0, 0.1, 0.6, 0.7 };
		final MoveableLimb limb19 = new MoveableLimb("ulLeg", 19, "j18", "j19",
				Z_AXIS, -130, xsIn19, ysIn19, upper_left_leg);
		limb19.setRanges(-45, 80, -20, 20, -30, 45);

		// lower left leg
		final double xsIn20[] = { 0, 0.10, 0.06, 0 };
		final double ysIn20[] = { 0, 0.15, 0.62, 0.7 };
		final MoveableLimb limb20 = new MoveableLimb("llLeg", 20, "j19", "j20",
				Z_AXIS, 0, xsIn20, ysIn20, lower_left_leg);
		limb20.setRange(X_AXIS, -120, 0);

		// invisible limb connecting between lower left leg and left foot
		final Limb limb21 = new Limb(21, "j20", "j21", Z_AXIS, 0, 0.07);

		// left foot
		final double xsIn22[] = { 0, 0.08, 0.06, 0 };
		final double ysIn22[] = { 0, 0.07, 0.21, 0.25 };
		final MoveableEllipticLimb limb22 = new MoveableEllipticLimb("lFoot",
				22, "j21", "j22", X_AXIS, 90, xsIn22, ysIn22, left_foot);
		limb22.setRanges(-90, 0, 0, 0, -30, 30);

		limbs.add(limb18);
		limbs.add(limb19);
		limbs.add(limb20);
		limbs.add(limb21);
		limbs.add(limb22);

		limbNames.put("ulLeg", new Integer(19));
		limbNames.put("llLeg", new Integer(20));
		limbNames.put("lFoot", new Integer(22));
	} // end of buildLeftLeg()

	private void printLimbsInfo()
	/*
	 * Used for debugging. Print limb info for each Limb object, and the name->number limb mapping.
	 */
	{
		/*
		 * for(int i = 0; i < limbs.size(); i++) ((Limb)limbs.get(i)).printLimb();
		 */

		// no use ;)
		/*
		 * int i = 0; Iterator it = limbNames.keySet().iterator(); while( it.hasNext() ){ String lNm = (String)
		 * it.next(); System.out.print( "(" + lNm + " = " + limbNames.get(lNm) + ") " ); i++; if (i == 5) { // max
		 * number of elements on a line System.out.println(); i = 0; } } System.out.println();
		 */
	} // end of printLimbsInfo()

	private void buildFigureGraph()
	/*
	 * Convert the figure into a Java 3D subgraph, stored in figureTG. offsetTG is connected to figureTG; it specifies
	 * the distance from the floor to the first joint and limb. The figure's graph will hang below offsetTG.
	 */
	{
		final HashMap<String, TransformGroup> joints = new HashMap<>();
		/*
		 * joints will contain (jointName, TG) pairs. Each TG is the position of the joint in the scene. A limb
		 * connected to a joint is placed in the scene by using the TG associated with that joint.
		 */

		figureTG = new TransformGroup();
		figureTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		figureTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); // can alter

		final TransformGroup offsetTG = new TransformGroup();
		final Transform3D trans = new Transform3D();
		trans.setTranslation(new Vector3d(0, 1.24, 0));
		// an offset from the ground to the first joint
		offsetTG.setTransform(trans);

		joints.put("j0", offsetTG); // store starting joint (assume it is j0)

		/*
		 * Grow the subgraph for each limb object, attaching it to the figure's subgraph below offsetTG.
		 */
		Limb li;
		for (final Object limb : limbs) {
			li = (Limb) limb;
			li.growLimb(joints);
		}

		figureTG.addChild(offsetTG);
	} // end of buildFigureGraph()

	public TransformGroup getFigureTG() {
		return figureTG;
	}

	// ----------------- limb-related operations ---------------------

	public int checkLimbNo(final int no)
	// Is no a limb number?
	{
		if (limbNames.containsValue(new Integer(no))) {
			return no;
		} else {
			return -1; // no not found in limbNames
		}
	} // end of checkLimbNo()

	public int findLimbNo(final String name)
	// Find the limb number for this name
	{
		final Integer iNo = limbNames.get(name);
		if (iNo == null) {
			return -1;
		} else {
			return iNo.intValue();
		}
	} // end of findLimbNo()

	public void updateLimb(final int limbNo, final int axis, final double angle)
	/*
	 * Apply the limb operation to the limb with limbNo. The operation is a rotation of angle degress around axis.
	 */
	/*
	 * commands : limbNo: | function : -------------------------------- 7 | unterer rechter Arm 8 | rechte Hand 6 |
	 * oberer rechter Arm 10 | oberer linker Arm 12 | linke Hand 11 | unterer linker Arm 15 | unteres rechtes Bein 2 |
	 * Oberkörper 19 | oberer linkes Bein 4 | Kopf 22 | linker Fuß 20 | unteres linkes Bein 14 | oberes rechtes Bein 17
	 * | rechter Fuß axis : | function : -------------------------------- 0 | Kippen/Neigen nach vorn 1 | Drehung 2 |
	 * Kippen/Neigen zur Seite Movement of whole figure see below : doMove,doRotateY
	 */
	{
		final Limb li = limbs.get(limbNo - 1); // assume limbNo input is correct
		li.updateLimb(axis, angle); // send axis and angle
	} // end of updateLimb()

	public void reset()
	// restore each limb to its original position in space
	{
		Limb li;
		for (final Object limb : limbs) {
			li = (Limb) limb;
			li.reset();
		}
	} // end of reset()

	// ------------------- figure movement operations ------------

	public void doMove(final int dir)
	/*
	 * Move the figure forwards, backwards, left, right, up, or down. doMove() can be called from LocationBeh or
	 * CommandsPanel.
	 */
	{
		if (dir == FWD) {
			doMove1(fwdVec);
		} else if (dir == BACK) {
			doMove1(backVec);
		} else if (dir == LEFT) {
			doMove1(leftVec);
		} else if (dir == RIGHT) {
			doMove1(rightVec);
		} else if (dir == UP) {
			doMove1(upVec);
			yCount++;
		} else if (dir == DOWN) {
			if (yCount > 0) {
				doMove1(downVec);
				yCount--;
			}
		} else {
			System.out.println("Unknown doMove() call");
		}
	} // end of doMove()

	private void doMove1(final Vector3d theMove)
	// Move the figure by the amount in theMove
	{
		figureTG.getTransform(t3d);
		toMove.setTranslation(theMove); // overwrite previous trans
		t3d.mul(toMove);
		figureTG.setTransform(t3d);
	} // end of doMove1()

	public void doRotateY(final int rotDir)
	/*
	 * Rotate the figure clockwise ot counter-clockwise around the y-axis. doRotateY() can be called from LocationBeh or
	 * CommandsPanel.
	 */
	{
		if (rotDir == CLOCK) {
			doRotateY1(-ROTATE_AMT); // clockwise
		} else if (rotDir == CCLOCK) {
			doRotateY1(ROTATE_AMT); // counter-clockwise
		} else {
			System.out.println("Unknown doRotateY() call");
		}
	} // end of doRotateY()

	public void doRotateY1(final double radians)
	// Rotate the figure by radians amount around its y-axis
	{
		figureTG.getTransform(t3d);
		toRot.rotY(radians); // overwrite previous rotation
		t3d.mul(toRot);
		figureTG.setTransform(t3d);
	} // end of doRotateY1()

	private void setOutfit(final int prefab) {
		switch (prefab) {
		case 1: {
			bottom = "denim.jpg";
			chest = "camoflage.jpg";
			neck = "skin.jpg";
			head = "head.jpg";
			upper_right_arm = "rightarm.jpg";
			lower_right_arm = "skin.jpg";
			right_hand = "skin.jpg";
			upper_left_arm = "leftarm.jpg";
			lower_left_arm = "skin.jpg";
			left_hand = "skin.jpg";
			upper_right_leg = "denim.jpg";
			lower_right_leg = "lowDenim.jpg";
			right_foot = "shoes.jpg";
			upper_left_leg = "denim.jpg";
			lower_left_leg = "lowDenim.jpg";
			left_foot = "shoes.jpg";
			break;
		}
		case 2: {
			bottom = "jeansh01.jpg";
			chest = "t-shirt01.jpg";
			neck = "skin.jpg";
			head = "head01.jpg";
			upper_right_arm = "T-shirt-aermel01.jpg";
			lower_right_arm = "skin.jpg";
			right_hand = "skin.jpg";
			upper_left_arm = "T-shirt-aermel01.jpg";
			lower_left_arm = "skin.jpg";
			left_hand = "skin.jpg";
			upper_right_leg = "jeanso01.jpg";
			lower_right_leg = "jeansu01.jpg";
			right_foot = "Shoes01.jpg";
			upper_left_leg = "jeanso01.jpg";
			lower_left_leg = "jeansu01.jpg";
			left_foot = "Shoes01.jpg";
			break;
		}
		// etc.
		// .
		// .
		// .
		default: {
			bottom = "denim.jpg";
			chest = "camoflage.jpg";
			neck = "skin.jpg";
			head = "head.jpg";
			upper_right_arm = "rightarm.jpg";
			lower_right_arm = "skin.jpg";
			right_hand = "skin.jpg";
			upper_left_arm = "leftarm.jpg";
			lower_left_arm = "skin.jpg";
			left_hand = "skin.jpg";
			upper_right_leg = "denim.jpg";
			lower_right_leg = "lowDenim.jpg";
			right_foot = "shoes.jpg";
			upper_left_leg = "denim.jpg";
			lower_left_leg = "lowDenim.jpg";
			left_foot = "shoes.jpg";
			break;
		}
		}
	}

	public void startWalking() {
		walking = true;
		if (!ErstStart_durchgeführt) {
			figureThread.start();
			ErstStart_durchgeführt = true;

		} else {
			figureThread.resume();
		}
		// System.out.printf("Thread gestartet");

	}

	public void stopWalking() {
		walking = false;
		figureThread.suspend();
		// System.out.printf("Thread gestoppt");
	}

	public void startTalking() {
		talking = true;
		if (!ErstStart_durchgeführt) {
			figureThread.start();
			ErstStart_durchgeführt = true;
			updateLimb(7, 0, 90);
		} else {
			figureThread.resume();
		}
	}

	public void stopTalking() {
		talking = false;
		figureThread.suspend();
	}

	private void talk(final int frame) {

		switch (frame) {
		case 1: {
			updateLimb(7, 0, 15); // Arm rechts 105°
			break;
		}
		case 2: {
			updateLimb(7, 0, 15); // Arm rechts 120°
			break;
		}
		case 3: {
			updateLimb(7, 0, -15);// Arm rechts 105°
			updateLimb(11, 0, 15);// Arm links 15°

			break;
		}
		case 4: {
			updateLimb(7, 0, -15);// Arm rechts 90°
			updateLimb(11, 0, 15);// Arm links 30°

			break;
		}
		case 5: {
			updateLimb(7, 0, -15);// Arm rechts 75°
			updateLimb(11, 0, 15);// Arm links 45°

			break;
		}
		case 6: {
			updateLimb(7, 0, -15);// Arm rechts 60°
			updateLimb(11, 0, 15);// Arm links 60°

			break;
		}
		case 7: {
			updateLimb(7, 0, -15);// Arm rechts 45°
			updateLimb(11, 0, 15);// Arm links 75°

			break;
		}
		case 8: {
			updateLimb(7, 0, -15);// Arm rechts 30°
			updateLimb(11, 0, 15);// Arm links 90°

			break;
		}
		case 9: {
			updateLimb(7, 0, -15);// Arm rechts 15°
			updateLimb(11, 0, 15);// Arm links 105°

			break;
		}
		case 10: {
			// updateLimb(7,0,-15);// Arm rechts 0°
			updateLimb(11, 0, 15);// Arm links 120°

			break;
		}
		case 11: {
			updateLimb(7, 0, 15);// Arm rechts 30°
			updateLimb(11, 0, -15);// Arm links 105°

			break;
		}
		case 12: {
			updateLimb(7, 0, 15); // Arm rechts 45°
			updateLimb(11, 0, -15); // Arm links 90°

			break;
		}
		case 13: {
			updateLimb(7, 0, 15); // Arm rechts 60°
			updateLimb(11, 0, -15); // Arm links 75°

			break;
		}
		case 14: {
			updateLimb(7, 0, 15); // Arm rechts 75°
			updateLimb(11, 0, -15); // Arm links 60°

			break;
		}
		case 15: {
			updateLimb(7, 0, 15); // Arm rechts 90°
			updateLimb(11, 0, -15); // Arm links 45°

			break;
		}
		case 16: {
			// updateLimb(7,0,15); // Arm rechts 90°
			updateLimb(11, 0, -15); // Arm links 30°

			break;
		}
		case 17: {
			// updateLimb(7,0,15); // Arm rechts 90°
			updateLimb(11, 0, -15); // Arm links 15°

			break;
		}
		case 18: {
			// updateLimb(7,0,15); // Arm rechts 90°
			updateLimb(11, 0, -15); // Arm links 0°

			break;
		}
		}
	}

	private void walk(final int frame) {
		/*
		 * double factor=0; double factor2 = 0; double Schrittlänge = 6; if ((frame % 12) == 0) { reset(); } factor =
		 * Math.cos(2*Math.PI/walkingFrames*frame); updateLimb(14,0,factor*Schrittlänge);
		 * updateLimb(19,0,-factor*Schrittlänge); updateLimb(6,0,factor*Schrittlänge) ;
		 * updateLimb(10,0,-factor*Schrittlänge); //factor2 = Math.sin(2*Math.PI/walkingFrames*frame);
		 * System.out.println("Info : "+ (factor*Schrittlänge) + " " + (-factor*Schrittlänge)); if ((frame%12) != 0) {
		 * doMove(FWD); }
		 */

		switch (frame) {
		case 1: {
			updateLimb(14, 0, 6);
			updateLimb(6, 0, 6);
			updateLimb(19, 0, -6);
			updateLimb(10, 0, -6);
			doMove(FWD);
			break;
		}
		case 2: {
			updateLimb(14, 0, 6);
			updateLimb(6, 0, 6);
			updateLimb(19, 0, -6);
			updateLimb(10, 0, -6);
			doMove(FWD);
			break;
		}
		case 3: {
			updateLimb(14, 0, 6);
			updateLimb(6, 0, 6);
			updateLimb(15, 0, -6);
			updateLimb(7, 0, 6);
			updateLimb(19, 0, -6);
			updateLimb(10, 0, -4);
			updateLimb(11, 0, 6);
			doMove(FWD);
			doMove(DOWN);
			updateLimb(2, 1, 10);
			break;
		}
		case 4: {
			updateLimb(14, 0, 6);
			updateLimb(6, 0, 6);
			updateLimb(19, 0, -4);
			doMove(FWD);
			doMove(DOWN);
			updateLimb(2, 1, 10);
			break;
		}
		case 5: {
			updateLimb(14, 0, -6);
			updateLimb(6, 0, -6);
			updateLimb(19, 0, 4);
			doMove(FWD);
			doMove(UP);
			updateLimb(2, 1, -10);
			break;
		}
		case 6: {
			updateLimb(14, 0, -6);
			updateLimb(6, 0, -6);
			updateLimb(15, 0, 6);
			updateLimb(7, 0, -6);
			updateLimb(19, 0, 6);
			updateLimb(10, 0, 4);
			updateLimb(11, 0, -6);
			doMove(FWD);
			doMove(UP);
			updateLimb(2, 1, -10);
			break;
		}
		case 7: {
			updateLimb(14, 0, -6);
			updateLimb(6, 0, -6);
			updateLimb(19, 0, 6);
			updateLimb(10, 0, 6);
			doMove(FWD);
			break;
		}
		case 8: {
			updateLimb(19, 0, -6);
			updateLimb(10, 0, -6);
			updateLimb(14, 0, 6);
			updateLimb(6, 0, 6);
			reset();
			doMove(FWD);
			break;
		}
		case 9: {
			updateLimb(19, 0, 6);
			updateLimb(10, 0, 6);
			updateLimb(14, 0, -6);
			updateLimb(6, 0, -6);
			doMove(FWD);
			break;
		}
		case 10: {
			updateLimb(19, 0, 6);
			updateLimb(10, 0, 6);
			updateLimb(14, 0, -6);
			updateLimb(6, 0, -6);
			doMove(FWD);
			break;
		}
		case 11: {
			updateLimb(19, 0, 6);
			updateLimb(10, 0, 6);
			updateLimb(20, 0, -6);
			updateLimb(11, 0, 6);
			updateLimb(14, 0, -6);
			updateLimb(6, 0, -6);
			updateLimb(7, 0, 6);
			doMove(FWD);
			doMove(DOWN);
			updateLimb(2, 1, -10);
			break;
		}
		case 12: {
			updateLimb(19, 0, 6);
			updateLimb(10, 0, 6);
			updateLimb(14, 0, -4);
			doMove(FWD);
			doMove(DOWN);
			updateLimb(2, 1, -10);
			break;
		}
		case 13: {
			updateLimb(19, 0, -6);
			updateLimb(10, 0, -6);
			updateLimb(14, 0, 4);
			doMove(FWD);
			doMove(UP);
			updateLimb(2, 1, 10);
			break;
		}
		case 14: {
			updateLimb(19, 0, -6);
			updateLimb(10, 0, -6);
			updateLimb(20, 0, 6);
			updateLimb(11, 0, -6);
			updateLimb(14, 0, 6);
			updateLimb(6, 0, 6);
			updateLimb(7, 0, -6);
			doMove(FWD);
			doMove(UP);
			updateLimb(2, 1, 10);
			break;
		}
		case 15: {
			updateLimb(19, 0, -6);
			updateLimb(10, 0, -6);
			updateLimb(14, 0, 6);
			updateLimb(6, 0, 6);
			doMove(FWD);
			break;
		}
		case 16: {
			updateLimb(19, 0, -6);
			updateLimb(10, 0, -6);
			updateLimb(14, 0, 6);
			updateLimb(6, 0, 6);
			reset();
			doMove(FWD);
			break;
		}
		default: {
			reset();
			break;
		}
		}
	}
	/*
	 * public void stop () { figureThread = null; }
	 */

	@Override
	public void run() {
		final Thread thisThread = Thread.currentThread();
		int frame = -1;
		while (figureThread == thisThread) {
			if (walking) {
				try {
					thisThread.sleep(3000 / walkingFrames);
					// System.out.printf("Thread läuft");
				} catch (final InterruptedException e) {
				}

				walk(++frame);
				if (frame == walkingFrames) {
					frame = 0;
				}
			} else if (talking) {
				try {
					thisThread.sleep(3000 / talkingFrames);
					// System.out.printf("Thread läuft");
				} catch (final InterruptedException e) {
				}

				talk(++frame);
				if (frame == talkingFrames) {
					frame = 0;
				}
			}
		}
	}
} // end of Figure class
