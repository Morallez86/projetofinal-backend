package aor.paj.projetofinalbackend.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Utility class for retrieving Locale objects based on country codes.
 * This class provides a mapping from country codes to corresponding Locale objects.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class LocaleService {
    private static final Map<String, Locale> countryToLocaleMap = new HashMap<>();

    static {
        countryToLocaleMap.put("US", Locale.ENGLISH);
        countryToLocaleMap.put("BR", new Locale("pt", "BR"));
        countryToLocaleMap.put("ES", new Locale("es", "ES"));
        countryToLocaleMap.put("PT", new Locale("pt", "PT"));
        countryToLocaleMap.put("AU", new Locale("en", "AU"));
    }

    /**
     * Retrieves the Locale object associated with a given country code.
     *
     * @param countryCode The country code for which to retrieve the Locale.
     * @return The Locale object corresponding to the provided country code,
     * or Locale#ENGLISH if no mapping exists for the given country code.
     */
    public static Locale getLocaleForCountry(String countryCode) {
        return countryToLocaleMap.getOrDefault(countryCode, Locale.ENGLISH);
    }
}
