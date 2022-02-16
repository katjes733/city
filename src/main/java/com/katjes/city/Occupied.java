package com.katjes.city;
public class Occupied {
	/*	type :
	 *	0 : nicht besetzt
	 *	1 : besetzt allgemein
	 *	2 : besetzt Strasse
	 *	3 : besetzt Gebäude
	 *	4 : besetzt Bäume
	 *  5 : besetzt Wasser
	 *	6 : besetzt freie Fläche
	 */
	 
	private int e[][];	
	private int max;
	private int maximum;
	
	public Occupied (int Min, int Max) {
		maximum = Math.abs(Min) + Math.abs(Max) + 1;
		max = (int) (Math.abs(Min) + Math.abs(Max)/2);
		e = new int [max*2+1][max*2+1];
		
		for (int i=0; i<(Max*2+1); i++) {
			for (int j = 0; j<(Max*2+1); j++) {
				e[i][j] = 0;
			}
		}
	}
	
	public void set (int x, int y) {
		e[x+max][y+max] = 1;
	}
	
	public void set (int x, int y, int type) {
		e[x+max][y+max] = type;
	}
	
	public int get (int x, int y) {
		return (e[x+max][y+max]);
	}
	
	public int getMax () {
		return maximum;
	}
}