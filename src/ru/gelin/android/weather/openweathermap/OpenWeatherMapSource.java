package ru.gelin.android.weather.openweathermap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import ru.gelin.android.weather.Location;
import ru.gelin.android.weather.Weather;
import ru.gelin.android.weather.WeatherException;
import ru.gelin.android.weather.WeatherSource;
import ru.gelin.android.weather.source.HttpWeatherSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 *  Weather source implementation which uses openweathermap.org
 */
public class OpenWeatherMapSource extends HttpWeatherSource implements WeatherSource {

    /** API URL */
    static final String API_URL = "http://openweathermap.org/data/getrect?";

    JSONObject json;

    @Override
    public Weather query(Location location) throws WeatherException {
        String url = API_URL + location.getQuery();
        JSONTokener parser = new JSONTokener(readJSON(url));
        try {
            this.json = (JSONObject)parser.nextValue();
        } catch (JSONException e) {
            throw new WeatherException("can't parse weather", e);
        }
        return null;
    }

    @Override
    public Weather query(Location location, Locale locale) throws WeatherException {
        return query(location);
        //TODO: what to do with locale?
    }

    String readJSON(String url) throws WeatherException {
        StringBuilder result = new StringBuilder();
        InputStreamReader reader = getReaderForURL(url);
        try {
            int c = 0;  //TODO: optimize
            c = reader.read();
            while (c >= 0) {
                result.append((char)c);
                c = reader.read();
            }
        } catch (IOException e) {
            throw new WeatherException("can't read weather", e);
        }
        return result.toString();
    }

}
