package client;

import java.util.Locale;

import data.WeatherData;

/**
 * Class that takes WeatherData and prints it formatted to the console 
 * @author Hagen Wittlich
 *
 */
public class WeatherDataFormatter {

	public static void printFormattedOutput(WeatherData data) {

		System.out.println("Weather data for " + data.getDate());
		System.out.println("*********************************");
		System.out.println("Min: " + calculateMin(data) + "°C. Max: " + calculateMax(data) + "°C. On Average: "
				+ String.format(Locale.ROOT, "%.01f", calculateAverage(data)) + "°C");

		for (int i = 0; i < 24; i++) {
			System.out.println((i < 10 ? "0" : "") + i + ":00 " + data.getValues().get(i) + "°C");
		}

	}

	/**
	 * Calculates the average temperature
	 * @param data
	 * @return average temperature
	 */
	private static float calculateAverage(WeatherData data) {
		Float sum = 0.0f;
		for (int i = 0; i < 24; i++) {
			sum += data.getValues().get(i);
		}
		return sum / 24.0f;
	}

	/**
	 * Calculates the maximum temperature
	 * @param data
	 * @return maximum temperature
	 */
	private static Float calculateMax(WeatherData data) {
		Float max = null;
		for (int i = 0; i < 24; i++) {
			max = (max == null || max < data.getValues().get(i)) ? data.getValues().get(i) : max;
		}
		return max;
	}
	
	/**
	 * Calculates the minimum temperature
	 * @param data
	 * @return minimum temperature
	 */
	private static Float calculateMin(WeatherData data) {
		Float min = null;
		for (int i = 0; i < 24; i++) {
			min = (min == null || min > data.getValues().get(i)) ? data.getValues().get(i) : min;
		}
		return min;
	}

}
