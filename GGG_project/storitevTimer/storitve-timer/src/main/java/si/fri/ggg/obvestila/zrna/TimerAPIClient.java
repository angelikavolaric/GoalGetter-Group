package si.fri.ggg.obvestila.zrna;

import com.fasterxml.jackson.databind.ObjectMapper;
import si.fri.ggg.timer.entitete.Timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TimerAPIClient {
    private static final String API_URL = "https://api.myintervals.com/timer/";

    public String dodajTimer(Timer timer) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInput = objectMapper.writeValueAsString(timer);

        //api connect
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try(OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        //http response code
        int responseCode = connection.getResponseCode();
        System.out.println("HTTP response Code : " + responseCode);

        try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String intputLine;
            StringBuilder output = new StringBuilder();
            while((intputLine = in.readLine()) != null) {
                output.append(intputLine);
            }
            return output.toString();
        }
    }
}
