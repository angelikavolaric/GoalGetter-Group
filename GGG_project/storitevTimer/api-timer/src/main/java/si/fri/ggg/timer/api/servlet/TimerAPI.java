package si.fri.ggg.timer.api.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.ggg.timer.api.v1.viri.TimerViri;
import si.fri.ggg.timer.entitete.TimerEnt;


import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet("/servlet")
public class TimerAPI {

    //TimerEnt timerEnt = new TimerEnt();
    //api KEY : YmQyNmI5MWQtNGI5Yy00ODhiLTllMGUtNzk4YTFkZTVkMGJl
    // workspaceid ??? 6783b08b1c567d719f27a5ee
    //https://api.clockify.me/api/v1/file/image
    //https://api.clockify.me/api/v1/workspaces/{workspaceId}/time-entries


    //private static final String ACCESS_TOKEN = "YmQyNmI5MWQtNGI5Yy00ODhiLTllMGUtNzk4YTFkZTVkMGJl";
    private static final String WORKSPACE_ID = "6783b08b1c567d719f27a5ee";
    private static final String ACCESS_TOKEN = System.getenv("YOUR_ACCESS_TOKEN");
   // private static final String WORKSPACE_ID = "id";*/
    @Inject
    TimerViri timerViri;

    public String setTimer(String opis, String start, String stop)
    {
        try {
            String apiUrl = "https://api.clockify.me/api/v1/workspaces/" + WORKSPACE_ID + "/time-entries";
            String jsonRequest = String.format(
                    "{\"description\": \"%s\", \"start\": \"%s\", \"end\": \"%s\"}", opis,start,stop);


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("X-Api-Key", ACCESS_TOKEN)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
            //sending
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> postResponse =client.send(request, HttpResponse.BodyHandlers.ofString());
            //handle
            if (postResponse.statusCode() == 201) {
                //Parsing RAAH
                String response = postResponse.body();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response);

                String apiId = jsonNode.get("id").asText();
                String zac = jsonNode.get("start").asText();
                String kon = jsonNode.get("end").asText();

                /*TimerEnt timerEnt = new TimerEnt();
                timerEnt.setOpis(opis);
                timerEnt.setOuterAPIid(apiId);
                timerEnt.setZacetek(zac);
                timerEnt.setKonec(kon);*/

                //timerViri.postTimer(timerEnt);



                return apiId;
            } else {
                return "Error setting timer: " + postResponse.statusCode() + " - " + postResponse.body();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid API URL", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTimeEntry(String timeEntryId)
    {
        try {
            String apiUrl = "https://api.clockify.me/api/v1/workspaces/" + WORKSPACE_ID + "/time-entries/" + timeEntryId;


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("X-Api-Key", ACCESS_TOKEN)
                    .GET()
                    .build();
            //sending
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> getResponse =client.send(request, HttpResponse.BodyHandlers.ofString());
            //handle
            if (getResponse.statusCode() == 200) {
                return "Time Entry retrieved successfully: " + getResponse.body();
            } else {
                return "Error retrieving time entry: " + getResponse.statusCode() + " - " + getResponse.body();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid API URL", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAllTimeEntries()
    {
        try {
            String apiUrl = "https://api.clockify.me/api/v1/workspaces/" + WORKSPACE_ID + "/time-entries";


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("X-Api-Key", ACCESS_TOKEN)
                    .GET()
                    .build();
            //sending
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> getResponse =client.send(request, HttpResponse.BodyHandlers.ofString());
            //handle
            if (getResponse.statusCode() == 200) {
                return "Time Entries retrieved successfully: " + getResponse.body();
            } else {
                return "Error retrieving time entries: " + getResponse.statusCode() + " - " + getResponse.body();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid API URL", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteTimeEntry(String timeEntryId)
    {
        try {
            String apiUrl = "https://api.clockify.me/api/v1/workspaces/" + WORKSPACE_ID + "/time-entries/" + timeEntryId;


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("X-Api-Key", ACCESS_TOKEN)
                    .GET()
                    .build();
            //sending
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> deleteResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            //handle
            if (deleteResponse.statusCode() == 204) {
                return "Time Entry deleted successfully";
            } else {
                return "Error deleting time entry: " + deleteResponse.statusCode() + " - " + deleteResponse.body();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid API URL", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }







    public static void main(String[] args) {
        TimerAPI timerAPI = new TimerAPI();

        // Example of setting a timer
        String description = "Test Timer";
        String startTime = "2025-01-12T14:00:00Z"; // Start time in ISO 8601 format
        String stopTime = "2025-01-12T15:00:00Z"; // Stop time in ISO 8601 format

        String result = timerAPI.setTimer(description, startTime, stopTime);
        System.out.println(result);

        // Example of getting a time entry by its ID
        String timeEntryId = "6783be8c5be34b0f9660d627"; // Replace with an actual time entry ID from Clockify
        String getResult = timerAPI.getTimeEntry(timeEntryId);
        System.out.println(getResult);

        // Example of deleting a time entry by its ID
        String deleteResult = timerAPI.deleteTimeEntry(timeEntryId);
        System.out.println(deleteResult);
    }
}



