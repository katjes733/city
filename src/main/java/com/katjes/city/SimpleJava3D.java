package com.katjes.city;

import javax.media.j3d.BranchGroup;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class SimpleJava3D {
	public SimpleJava3D() {
		final SimpleUniverse simpleUniverse = new SimpleUniverse();
		final BranchGroup branchGroup = new BranchGroup();
		branchGroup.addChild(new ColorCube(0.3));
		simpleUniverse.getViewingPlatform().setNominalViewingTransform();
		simpleUniverse.addBranchGraph(branchGroup);
	}

	public static void main(final String[] args) {
		new SimpleJava3D();
	}
}
