package si.fri.ggg.obvestila.api.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ObvestilaAPI {

    //TimerEnt timerEnt = new TimerEnt();
    //api KEY : YmQyNmI5MWQtNGI5Yy00ODhiLTllMGUtNzk4YTFkZTVkMGJl
    // workspaceid ??? 6783b08b1c567d719f27a5ee
    //https://api.clockify.me/api/v1/file/image
    //https://api.clockify.me/api/v1/workspaces/{workspaceId}/time-entries
//os_v2_app_pxxxv73w5ndt7ksyadgehusej6datp5cqb4u4dednskx6acxuuy73cmhsizmfhrkid6wtfewqta5rmr4iylmtatm6m4a4vr5rwq4qta
    private static final String YOUR_APP_API_KEY = "os_v2_app_pxxxv73w5ndt7ksyadgehusej4cg3rk6azremyvmmgaspus2aqub43zgwgcprulzbqb7na4i5f74oviilccho62idlnbffhq7tmehta";
    private static final String YOUR_APP_ID = "7def7aff-76eb-473f-aa58-00cc43d2444f";
    /*private static final String ACCESS_TOKEN = "YOUR_ACCESS_TOKEN";
    private static final String WORKSPACE_ID = "id";*/
    @Inject
    ObvestilaViri obvestilaViri;

    public String sendMessage(String zadeva, String sporocilo)
    {
        try {
            String apiUrl = "https://api.onesignal.com/notifications?c=email" + YOUR_APP_API_KEY;
            String jsonRequest = String.format("{\"app_id\": \"%s\",
                    \"email_subject\": \"$s\",
                    \"email_body": \"<html>%s</html>\",
                    \"email_from_name\": \"GoalGetterGroup\",
                    \"email_from_address\": \"hello@thetribeofchaos.com\",
                    \"name\": \"GoalGetterGroup\"}", zadeva, sporocilo);

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

                TimerEnt timerEnt = new TimerEnt();
                timerEnt.setOpis(opis);
                timerEnt.setOuterAPIid(apiId);
                timerEnt.setZacetek(zac);
                timerEnt.setKonec(kon);

                //timerViri.postTimer(timerEnt);



                return apiId;
            } else {
                return "Error sending message: " + postResponse.statusCode() + " - " + postResponse.body();
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
        ObvestilaAPI obvestilaAPI = new ObvestilaAPI();

        // Example of setting a timer
        String zadeva = "Test";
        String sporocilo = "helloWorld"; // Start time in ISO 8601 format

        String result = ObvestilaAPI.sendMessage(zadeva, sporocilo);
        System.out.println(result);

        // Example of getting a time entry by its ID
        /*  String timeEntryId = "6783be8c5be34b0f9660d627"; // Replace with an actual time entry ID from Clockify
        String getResult = timerAPI.getTimeEntry(timeEntryId);
        System.out.println(getResult);

        // Example of deleting a time entry by its ID
        String deleteResult = timerAPI.deleteTimeEntry(timeEntryId);
        System.out.println(deleteResult);*/
    }
}




