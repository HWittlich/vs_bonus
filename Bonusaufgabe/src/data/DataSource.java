package data;
import java.time.LocalDate;

/**
 * Interface that provides WeatherData
 * 
 * @author Hagen Wittlich
 *
 */
public interface DataSource {

	/**
	 * method to get WeatherData for a specified date
	 * 
	 * @param date
	 * @return WeatherData if there's an entry for the date, otherwise returns null.
	 * 
	 */
	public WeatherData getData(LocalDate date);
}
