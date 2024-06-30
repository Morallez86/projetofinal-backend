package aor.paj.projetofinalbackend.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeoLocationService {

    private static final String API_URL = "https://ipinfo.io/";
    private static final String API_TOKEN = "460cf23aedeb18";

    public static String getCountryCode(String ipAddress) throws Exception {
        URL url = new URL(API_URL + ipAddress + "/json?token=" + API_TOKEN);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            return jsonObject.get("country").getAsString();
        } else {
            throw new RuntimeException("Error retrieving country code. Response code: " + responseCode);
        }
    }
}
