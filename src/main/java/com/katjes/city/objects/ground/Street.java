package com.katjes.city.objects.ground;

import javax.media.j3d.TransformGroup;

public class Street extends TransformGroup {
	private static final float Std_Spur_Breite = 2.5f;
	private static final int Max_Div = 11;
	private final int Orientation = 0;

	private final int[][] Path = new int[Max_Div][Max_Div];

	public Street(final int Orientation) {
	}

	public int getOrientation() {
		return Orientation;
	}

	public void setPath(final int Path[][]) {
		for (int i = 0; i < Max_Div; i++) {
			for (int j = 0; j < Max_Div; j++) {
				this.Path[i][j] = Path[i][j];
			}
		}
	}

	// public checkPath

}