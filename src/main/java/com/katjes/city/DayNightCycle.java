package com.katjes.city;

import java.awt.Color;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Fog;
import javax.media.j3d.LinearFog;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class DayNightCycle extends TransformGroup implements Runnable {
	private static final Color3f WEISS = new Color3f(1f, 1f, 1f);
	private static final Color3f SCHWARZ = new Color3f(0f, 0f, 0f);
	private static final int DAYNIGHTDELAY = 20;
	private static final int RAINDELAY = 5;
	private static final float FOGCOLOR = 0.87f;
	private static final int NUMOFSUNLIGHTS = 2;
	private static final int LIGHTSWITCHVALUE = 127;

	private final City city;
	private Thread thisThread;
	private Color3f colorOfLight;
	private boolean fadeToDay = false;
	private boolean fadeToNight = false;

	private boolean raining = false;
	private boolean fadeToRain = false;
	private boolean fadeOutRain = false;
	private boolean fadeReady = true;

	private LinearFog fogLinear;
	private final DirectionalLight[] sunLight;
	private final AmbientLight ambientLight;

	public DayNightCycle(final City city) {
		this.city = city;
		this.addChild(addFog());

		sunLight = new DirectionalLight[NUMOFSUNLIGHTS];

		sunLight[0] = new DirectionalLight(new Color3f(1.0f, 1.0f, 1.0f),
				new Vector3f(-1.0f, -1.0f, -1.0f));
		sunLight[0].setCapability(DirectionalLight.ALLOW_COLOR_READ);
		sunLight[0].setCapability(DirectionalLight.ALLOW_COLOR_WRITE);
		sunLight[0].setCapability(DirectionalLight.ALLOW_STATE_READ);
		sunLight[0].setCapability(DirectionalLight.ALLOW_STATE_WRITE);
		sunLight[0].setInfluencingBounds(city.begrenzung);
		this.addChild(sunLight[0]);

		sunLight[1] = new DirectionalLight(new Color3f(1.0f, 1.0f, 1.0f),
				new Vector3f(1.0f, -1.0f, 1.0f));
		sunLight[1].setCapability(DirectionalLight.ALLOW_COLOR_READ);
		sunLight[1].setCapability(DirectionalLight.ALLOW_COLOR_WRITE);
		sunLight[1].setCapability(DirectionalLight.ALLOW_STATE_READ);
		sunLight[1].setCapability(DirectionalLight.ALLOW_STATE_WRITE);
		sunLight[1].setInfluencingBounds(city.begrenzung);
		this.addChild(sunLight[1]);

		ambientLight = new AmbientLight(new Color3f(1.0f, 1.0f, 1.0f));
		ambientLight.setCapability(AmbientLight.ALLOW_COLOR_READ);
		ambientLight.setCapability(AmbientLight.ALLOW_COLOR_WRITE);
		ambientLight.setCapability(DirectionalLight.ALLOW_STATE_READ);
		ambientLight.setCapability(DirectionalLight.ALLOW_STATE_WRITE);
		ambientLight.setInfluencingBounds(city.begrenzung);
		this.addChild(ambientLight);
	}

	public void FadeToDay() {
		fadeToDay = true;
		fadeToNight = false;
	}

	public void FadeToNight() {
		fadeToDay = false;
		fadeToNight = true;
	}

	public void FadeToRain() {
		fadeToRain = true;
		fadeOutRain = false;
		// ggf. deaktivieren und an anderer stelle einbauen, denn so startet regen und es wird dann erst langsam dunkler
		raining = true;
	}

	public void FadeOutRain() {
		fadeToRain = false;
		fadeOutRain = true;
		raining = false;
	}

	public void run() {
		Color3f compareLightColor, compareFogColor;
		Color colorOfMainLight, colorOfFog;
		int red = 0, green = 0, blue = 0;
		float redf = 0f, greenf = 0f, bluef = 0f;
		final float factor = FOGCOLOR;

		while (true) {
			try {
				Thread.sleep(100);
			} catch (final Exception e) {
			}

			if (fadeToDay && !fadeToNight) {
				compareLightColor = new Color3f(0f, 0f, 0f);
				compareFogColor = new Color3f(0f, 0f, 0f);
				red = 0;
				green = 0;
				blue = 0;

				redf = 0f;
				greenf = 0f;
				bluef = 0f;

				while (red < 255 && green < 255 && blue < 255 && !raining
						|| red < 192 && green < 192 && blue < 192 && raining) {

					sunLight[0].getColor(compareLightColor);
					fogLinear.getColor(compareFogColor);

					colorOfMainLight = compareLightColor.get();
					colorOfFog = compareFogColor.get();

					red = colorOfMainLight.getRed();
					green = colorOfMainLight.getGreen();
					blue = colorOfMainLight.getBlue();
					/*
					 * redf = (float) colorOfFog.getRed(); greenf= (float) colorOfFog.getGreen(); bluef = (float)
					 * colorOfFog.getBlue();
					 */
					if (red < 255) {
						red++;
					}
					if (redf < FOGCOLOR * 255) {
						redf += factor;
					}

					if (green < 255) {
						green++;
					}
					if (greenf < FOGCOLOR * 255) {
						greenf += factor;
					}

					if (blue < 255) {
						blue++;
					}
					if (bluef < FOGCOLOR * 255) {
						bluef += factor;
					}

					if (red > LIGHTSWITCHVALUE && green > LIGHTSWITCHVALUE
							&& blue > LIGHTSWITCHVALUE) {
						for (int i = 0; i < city.CO.NUMMAXSTREETLIGHTS; i++) {
							city.CO.light[i].switchLightOff();
						}
					}

					compareLightColor.set(new Color(red, green, blue));
					compareFogColor.set(
							new Color((int) redf, (int) greenf, (int) bluef));

					for (int i = 0; i < NUMOFSUNLIGHTS; i++) {
						sunLight[i].setColor(compareLightColor);
					}

					ambientLight.setColor(compareLightColor);

					fogLinear.setColor(compareFogColor);

					try {
						Thread.sleep(DAYNIGHTDELAY);
					} catch (final Exception e) {
						System.out.println("1 : " + e);
					}
				}
				fadeToDay = false;
				fadeToNight = false;
				fadeToRain = false;
				fadeOutRain = false;
			} else if (!fadeToDay && fadeToNight) {

				compareLightColor = new Color3f(1f, 1f, 1f);
				compareFogColor = new Color3f(FOGCOLOR, FOGCOLOR, FOGCOLOR);
				red = 255;
				green = 255;
				blue = 255;

				redf = FOGCOLOR * 255f;
				greenf = FOGCOLOR * 255f;
				bluef = FOGCOLOR * 255f;

				while (red > 0 && green > 0 && blue > 0) {

					sunLight[0].getColor(compareLightColor);
					fogLinear.getColor(compareFogColor);

					colorOfMainLight = compareLightColor.get();
					colorOfFog = compareFogColor.get();

					red = colorOfMainLight.getRed();
					green = colorOfMainLight.getGreen();
					blue = colorOfMainLight.getBlue();

					if (red == 192) {
						redf = FOGCOLOR * 255f * 0.75f;
					}
					if (green == 192) {
						greenf = FOGCOLOR * 255f * 0.75f;
					}
					if (blue == 192) {
						bluef = FOGCOLOR * 255f * 0.75f;
					}
					/*
					 * redf = (float) colorOfFog.getRed(); greenf= (float) colorOfFog.getGreen(); bluef = (float)
					 * colorOfFog.getBlue();
					 */
					System.out.println(red + " " + green + " " + blue + " "
							+ redf + " " + greenf + " " + bluef);

					if (red > 0) {
						red--;
					}
					if (redf > 0f) {
						redf -= factor;
					}

					if (green > 0) {
						green--;
					}
					if (greenf > 0f) {
						greenf -= factor;
					}

					if (blue > 0) {
						blue--;
					}
					if (bluef > 0f) {
						bluef -= factor;
					}

					if (red < LIGHTSWITCHVALUE && green < LIGHTSWITCHVALUE
							&& blue < LIGHTSWITCHVALUE) {
						for (int i = 0; i < city.CO.NUMMAXSTREETLIGHTS; i++) {
							city.CO.light[i].switchLightOn();
						}
					}

					compareLightColor.set(new Color(red, green, blue));
					compareFogColor.set(
							new Color((int) redf, (int) greenf, (int) bluef));

					for (int i = 0; i < NUMOFSUNLIGHTS; i++) {
						sunLight[i].setColor(compareLightColor);
					}

					ambientLight.setColor(compareLightColor);

					fogLinear.setColor(compareFogColor);

					try {
						Thread.sleep(DAYNIGHTDELAY);
					} catch (final Exception e) {
						System.out.println("2 : " + e);
					}
				}
				fadeToDay = false;
				fadeToNight = false;
				fadeToRain = false;
				fadeOutRain = false;
			}

			if (city.EC.getTagAktiv()) {
				if (fadeToRain && !fadeOutRain) {
					fadeReady = false;
					compareLightColor = new Color3f(1f, 1f, 1f);
					compareFogColor = new Color3f(FOGCOLOR, FOGCOLOR, FOGCOLOR);
					red = 255;
					green = 255;
					blue = 255;

					redf = FOGCOLOR * 255f;
					greenf = FOGCOLOR * 255f;
					bluef = FOGCOLOR * 255f;

					while (red > 192 && green > 192 && blue > 192) {

						sunLight[0].getColor(compareLightColor);
						fogLinear.getColor(compareFogColor);

						colorOfMainLight = compareLightColor.get();
						colorOfFog = compareFogColor.get();

						red = colorOfMainLight.getRed();
						green = colorOfMainLight.getGreen();
						blue = colorOfMainLight.getBlue();
						/*
						 * redf = (float) colorOfFog.getRed(); greenf= (float) colorOfFog.getGreen(); bluef = (float)
						 * colorOfFog.getBlue();
						 */
						if (red > 0) {
							red--;
						}
						if (redf > 0f) {
							redf -= factor;
						}

						if (green > 0) {
							green--;
						}
						if (greenf > 0f) {
							greenf -= factor;
						}

						if (blue > 0) {
							blue--;
						}
						if (bluef > 0f) {
							bluef -= factor;
						}

						compareLightColor.set(new Color(red, green, blue));
						compareFogColor.set(new Color((int) redf, (int) greenf,
								(int) bluef));

						for (int i = 0; i < NUMOFSUNLIGHTS; i++) {
							sunLight[i].setColor(compareLightColor);
						}

						ambientLight.setColor(compareLightColor);

						fogLinear.setColor(compareFogColor);

						try {
							Thread.sleep(RAINDELAY);
						} catch (final Exception e) {
							System.out.println("2 : " + e);
						}
					}
					fadeToRain = false;
					fadeOutRain = false;
					fadeReady = true;
				} else if (!fadeToRain && fadeOutRain) {
					fadeReady = false;
					compareLightColor = new Color3f(0.75f, 0.75f, 0.75f);
					compareFogColor = new Color3f(0.75f * FOGCOLOR,
							0.75f * FOGCOLOR, 0.75f * FOGCOLOR);
					red = 192;
					green = 192;
					blue = 192;

					redf = 0.75f * FOGCOLOR * 255f;
					greenf = 0.75f * FOGCOLOR * 255f;
					bluef = 0.75f * FOGCOLOR * 255f;

					while (red < 255 && green < 255 && blue < 255 && !raining
							|| red < 192 && green < 192 && blue < 192
									&& raining) {

						sunLight[0].getColor(compareLightColor);
						fogLinear.getColor(compareFogColor);

						colorOfMainLight = compareLightColor.get();
						colorOfFog = compareFogColor.get();

						red = colorOfMainLight.getRed();
						green = colorOfMainLight.getGreen();
						blue = colorOfMainLight.getBlue();
						/*
						 * redf = (float) colorOfFog.getRed(); greenf= (float) colorOfFog.getGreen(); bluef = (float)
						 * colorOfFog.getBlue();
						 */
						if (red < 255) {
							red++;
						}
						if (redf < FOGCOLOR * 255) {
							redf += factor;
						}

						if (green < 255) {
							green++;
						}
						if (greenf < FOGCOLOR * 255) {
							greenf += factor;
						}

						if (blue < 255) {
							blue++;
						}
						if (bluef < FOGCOLOR * 255) {
							bluef += factor;
						}

						compareLightColor.set(new Color(red, green, blue));
						compareFogColor.set(new Color((int) redf, (int) greenf,
								(int) bluef));

						for (int i = 0; i < NUMOFSUNLIGHTS; i++) {
							sunLight[i].setColor(compareLightColor);
						}

						ambientLight.setColor(compareLightColor);

						fogLinear.setColor(compareFogColor);

						System.out.println(red + " " + green + " " + blue + " "
								+ redf + " " + greenf + " " + bluef);
						try {
							Thread.sleep(RAINDELAY);
						} catch (final Exception e) {
							System.out.println("1 : " + e);
						}
					}
					fadeToRain = false;
					fadeOutRain = false;
					fadeReady = true;
				}
			}
		}
	}

	public boolean getFadeReady() {
		return fadeReady;
	}

	private LinearFog addFog() {
		fogLinear = new LinearFog(FOGCOLOR, FOGCOLOR, FOGCOLOR, 20.0f, 35.0f);
		fogLinear.setInfluencingBounds(city.begrenzung);
		fogLinear.setCapability(Fog.ALLOW_COLOR_READ);
		fogLinear.setCapability(Fog.ALLOW_COLOR_WRITE);
		return fogLinear;
	}
}