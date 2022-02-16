package com.katjes.city;

import java.util.Random;

import com.katjes.city.graphics.Attachment2ViewPlatform;

public class EnviromentalChanging extends Thread {
	private static final int Regenwahrscheinlichkeit = 25; // Prozent
	private static final int Windwahrscheinlichkeit = 75; // Prozent
	private static final int TagNachtVerhältnis = 50; // Prozent
	private static final long TagesDauer = 30; // Sekunden

	private Thread TH;
	private final City city;

	private final Random r;
	private long time = 0;
	private boolean RegenAktiv = false;
	private boolean RegenAktivb = false;
	private boolean WindAktiv = false;
	private boolean TagAktiv = true;
	private long DauerRegen = 0;
	private long DauerRegenAktiv = 0;
	private long DauerWind = 0;
	private long DauerWindAktiv = 0;
	private long DauerTag = 0;
	private long DauerTagAktiv = 0;
	private long DauerNacht = 0;
	private long DauerNachtAktiv = 0;

	public Attachment2ViewPlatform a;
	public DayNightCycle DNC;

	public EnviromentalChanging(final City city) {
		this.city = city;
		r = new Random();

		// hier Einfügen gewünschter Umgebungsveränderungen
		// Regen

		a = new Attachment2ViewPlatform(city, 0.5, 1.0);
		new Thread(a).start();
		city.streets_TG.addChild(a);

		// Wind

		// Tag/Nacht Zyklus

		DNC = new DayNightCycle(city);
		new Thread(DNC).start();
		city.streets_TG.addChild(DNC);

	}

	@Override
	public void run() {
		try {
			while (1 > 0) {
				// *************************************************************************************************//
				// Hier Skriptausführungen einfügen
				// Skripte Start
				Regen();

				// noch nicht aktiv
				// Wind();

				Tageszeit();

				System.out.println("Aktive Threads : " + TH.activeCount());
				// Skripte Ende
				// *************************************************************************************************//

				Thread.sleep(1000);

				time++;
				DauerRegenAktiv++;
				DauerWindAktiv++;
				if (TagAktiv) {
					DauerTagAktiv++;
				} else {
					DauerNachtAktiv++;
				}
			}
		} catch (final Exception e) {
		}
	}

	private void Regen() {
		try {

			System.out
					.println("Regen : " + DauerRegenAktiv + " / " + DauerRegen);
			if (DauerRegenAktiv >= DauerRegen) {
				DauerRegen = r.nextInt(
						(int) (TagesDauer * TagNachtVerhältnis / 400)) + 5;
				DauerRegenAktiv = 0;
				if (r.nextFloat() > 1.0f - Regenwahrscheinlichkeit / 100.0f) {
					if (!RegenAktiv) {
						RegenAktiv = true;
						// Starte Regen
						if (RegenAktiv != RegenAktivb) {
							do {
								DNC.FadeToRain();
							} while (!DNC.getFadeReady());
						}
						a.startIt();
						City.CS.backSound[0].setEnable(true);

						RegenAktivb = true;
						System.out.println("Starte Regen");
					}
				} else {
					RegenAktiv = false;
					// Stoppe Regen
					a.stop();
					City.CS.backSound[0].setEnable(false);
					if (RegenAktiv != RegenAktivb) {
						do {
							DNC.FadeOutRain();
						} while (!DNC.getFadeReady());
					}

					RegenAktivb = false;
					System.out.println("Stoppe Regen");
				}
			}

		} catch (final Exception e) {
		}
	}

	private void Wind() {
		try {

			System.out.println("Wind  : " + DauerWindAktiv + " / " + DauerWind);
			if (DauerWindAktiv >= DauerWind) {
				DauerWind = r.nextInt(
						(int) (TagesDauer * TagNachtVerhältnis / 400)) + 5;
				DauerWindAktiv = 0;
				if (r.nextFloat() > 1.0f - Windwahrscheinlichkeit / 100.0f) {
					WindAktiv = true;
					// Starte Wind ((int)(r.nextFloat()*100)) als Intensität
					System.out.println("Starte Wind");
				} else {
					WindAktiv = false;
					// Stoppe Wind
					System.out.println("Stoppe Wind");
				}
			}

		} catch (final Exception e) {
		}
	}

	private void Tageszeit() {
		// Initialisierung Start
		if (DauerTag == 0) {
			DauerTag = TagesDauer;
		}
		if (DauerNacht == 0) {
			DauerNacht = TagesDauer * 100 / TagNachtVerhältnis - TagesDauer;
		}
		// Initialisierung Ende

		if (TagAktiv) {
			System.out.println("Tag   : " + DauerTagAktiv + " / " + DauerTag);
			if (DauerTagAktiv >= DauerTag) {
				TagAktiv = false;
				DNC.FadeToNight();
				DauerNachtAktiv = 0;
			}
		} else {
			System.out
					.println("Nacht : " + DauerNachtAktiv + " / " + DauerNacht);
			if (DauerNachtAktiv >= DauerNacht) {
				TagAktiv = true;
				DNC.FadeToDay();
				DauerTagAktiv = 0;
				// Soundsteuerung

				/*
				 * city.CS.pointSound[0].setEnable(true); city.CS.pointSound[1].setEnable(true);
				 * city.CS.pointSound[2].setEnable(true); city.CS.pointSound[3].setEnable(false);
				 * city.CS.pointSound[4].setEnable(false); city.CS.pointSound[5].setEnable(false);
				 * city.CS.pointSound[6].setEnable(true); city.CS.pointSound[7].setEnable(true);
				 * city.CS.pointSound[8].setEnable(true); city.CS.pointSound[9].setEnable(false);
				 * city.CS.pointSound[10].setEnable(false); city.CS.pointSound[11].setEnable(false);
				 */
			}
		}
	}

	public boolean getTagAktiv() {
		return TagAktiv;
	}
}