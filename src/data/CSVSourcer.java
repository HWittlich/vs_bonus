package data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of DataSource interface that uses a CSV file as source
 * 
 * @author Hagen Wittlich
 *
 */
public class CSVSourcer implements DataSource {

	private List<WeatherData> database;

	/**
	 * Default Constructor with default path
	 */
	public CSVSourcer() {
		this(System.getProperty("user.dir") + "\\WeatherData.csv");
	}

	/**
	 * Constructor with specified path
	 */
	public CSVSourcer(String path) {
		System.out.println("Searching for " + path);
		database = processInputFile(path);
	}

	@Override
	public WeatherData getData(LocalDate date) {
		for (WeatherData entry : database) {
			if ( entry!=null && entry.getDate().isEqual(date)) {
				return entry;
			}
		}
		System.out.println("Could not find an entry for " + date);
		return null;
	}

	/**
	 * method to parse a csv file into a List of WeatherData objects
	 * 
	 * @param inputFilePath - path to the file that has to be parsed
	 * @return List of WeatherData entries
	 */
	private List<WeatherData> processInputFile(String inputFilePath) {

		List<WeatherData> inputList = new ArrayList<WeatherData>();

		try {
			File inputF = new File(inputFilePath);
			InputStream inputFS = new FileInputStream(inputF);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
			// skip the header of the csv
			inputList = br.lines().skip(1).map(mapValueToItem).collect(Collectors.toList());
			br.close();
		} catch (DateTimeParseException | IOException | NumberFormatException e) {
			System.out.println("Error while reading CSV. Please check the CSV file for format errors and make sure it is in the right location:");
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		System.out.println("Successfully loaded data from " + inputFilePath);
		return inputList;

	}

	/**
	 * Function that maps the hourly weather data and Date, min, max and average
	 * temperature. Puts null
	 */
	private Function<String, WeatherData> mapValueToItem = (line) -> {

		String[] p = line.split(";");// a CSV has comma separated lines

		WeatherData item = new WeatherData();
		item.setDate(LocalDate.parse(p[0])); // <-- this is the first column in the csv file

		Map<Integer, Float> weatherdata = new HashMap<>();
		for (int i = 1; i <= 24; i++) {
			float temperature = Float.parseFloat(p[i]);
			weatherdata.put(i - 1, temperature);
		}
		item.setValues(weatherdata);
		return item;

	};
	
//	 /**
//         * Function that maps the hourly weather data and Date, min, max and average
//         * temperature. Puts null if data is not complete.
//         */
//        private Function<String, WeatherData> mapValueToItem = (line) -> {
//
//                String[] p = line.split(";");// a CSV has comma separated lines
//
//                WeatherData item = new WeatherData();
//                item.setDate(LocalDate.parse(p[0])); // <-- this is the first column in the csv file
//
//                Map<Integer, Float> weatherdata = new HashMap<>();
//
//                for (int i = 1; i <= 24; i++) {
//                    try {
//                        float temperature = Float.parseFloat(p[i]);
//                        weatherdata.put(i - 1, temperature);
//                    } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
//                        item = null;
//                        return item;
//                    }
//                }
//                item.setValues(weatherdata);
//                return item;
//        };

}
