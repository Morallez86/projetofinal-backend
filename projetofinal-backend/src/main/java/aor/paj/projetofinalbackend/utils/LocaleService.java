package aor.paj.projetofinalbackend.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocaleService {
    private static final Map<String, Locale> countryToLocaleMap = new HashMap<>();

    static {
        countryToLocaleMap.put("US", Locale.ENGLISH);
        countryToLocaleMap.put("BR", new Locale("pt", "BR"));
        countryToLocaleMap.put("ES", new Locale("es", "ES"));
        countryToLocaleMap.put("PT", new Locale("pt", "PT"));
        countryToLocaleMap.put("AU", new Locale("en", "AU"));
    }

    public static Locale getLocaleForCountry(String countryCode) {
        return countryToLocaleMap.getOrDefault(countryCode, Locale.ENGLISH);
    }
}
