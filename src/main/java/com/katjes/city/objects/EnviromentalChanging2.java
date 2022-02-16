package com.katjes.city.objects;

import java.util.Random;

import com.katjes.city.City;

public class EnviromentalChanging2 extends Thread {
	private final City city;

	private final Random r;

	public EnviromentalChanging2(final City city) {
		this.city = city;
		r = new Random();
	}

	@Override
	public void run() {
		try {
			while (1 > 0) {
				// *************************************************************************************************//
				// Hier Skriptausführungen einfügen
				// Skripte Start

				if (r.nextInt(2) == 0) {
					city.EC.a.startIt();
				} else {
					city.EC.a.stop();
				}

				System.out.println("Aktive Threads : " + this.activeCount());
				// Skripte Ende
				// *************************************************************************************************//

				Thread.currentThread().sleep(2000);
			}
		} catch (final Exception e) {
			System.out.println("Error : " + e);
		}
	}
}