package si.fri.ggg.obvestila.zrna;

import com.fasterxml.jackson.databind.ObjectMapper;
import si.fri.ggg.obvestila.entitete.ObvestilaEnt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ObvestilaAPIClient {

    private static final String API_URL = "https://api.myintervals.com/timer/";

    // Method to add a notification (Obvestila) via the external API
    public String dodajObvestilo(ObvestilaEnt obvestilo) throws IOException {
        // Convert the ObvestilaEnt object to JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInput = objectMapper.writeValueAsString(obvestilo);

        // Connect to the external API
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send the JSON data to the external API
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        // Read the response from the API
        int responseCode = connection.getResponseCode();
        System.out.println("HTTP response Code : " + responseCode);

        // Read the input stream to get the response content
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder output = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                output.append(inputLine);
            }
            return output.toString();
        }
    }
}
