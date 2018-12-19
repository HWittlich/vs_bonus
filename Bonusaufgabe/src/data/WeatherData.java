package data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing the weather data that is associated with a certain date
 * 
 * @author Hagen Wittlich
 *
 */
public class WeatherData implements Serializable {

	private static final long serialVersionUID = 1L;
	LocalDate date;
//	private float min;
//	private float max;
//	private float avg;
	private Map<Integer, Float> values = new HashMap<>();

	/**
	 * gets the date of the weather data
	 * 
	 * @return LocalDate
	 */
	public LocalDate getDate() {
		return date;
	}
	/**
	 * sets the date
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

//	/**
//	 * gets the minimum temperature
//	 * @return minimum temperature
//	 */
//	public float getMin() {
//		return min;
//	}
//
//	/**
//	 * sets the minimum temperature
//	 * @param min
//	 */
//	public void setMin(float min) {
//		this.min = min;
//	}
//	
//	/**
//	 * gets the maximum temperature
//	 * @return maximum temperature
//	 */
//	public float getMax() {
//		return max;
//	}
//	
//	/**
//	 * sets the maximum temperature
//	 * @param max - maximum temperature
//	 */
//	public void setMax(float max) {
//		this.max = max;
//	}
//	
//	/**
//	 * gets the average temperature
//	 * @return average temperature
//	 */
//	public float getAvg() {
//		return avg;
//	}
//
//	/**
//	 * sets the average temperature
//	 * @param average temperature
//	 */
//	public void setAvg(float avg) {
//		this.avg = avg;
//	}
	
	/**
	 *  gets the hourly temperature values 
	 * @return a Map<Integer, Float> 
	 */
	public Map<Integer, Float> getValues() {
		return values;
	}
	
	/**
	 * sets the hourly temperature values 
	 * @param values
	 */
	public void setValues(Map<Integer, Float> values) {
		this.values = values;
	}

//	@Override
//	public String toString() {
//		String string = date.toString() + " Min: " + min + " Max: " + max + " Avg: " + avg + values.toString();
//		return string;
//	}
}
