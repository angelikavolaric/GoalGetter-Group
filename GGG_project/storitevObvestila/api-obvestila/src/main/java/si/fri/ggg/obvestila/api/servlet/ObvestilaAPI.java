package si.fri.ggg.obvestila.api.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import si.fri.ggg.obvestila.api.v1.viri.ObvestilaViri;
import si.fri.ggg.obvestila.entitete.ObvestilaEnt;

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

    // Constants for API authentication and endpoints
    private static final String YOUR_APP_API_KEY = "os_v2_app_pxxxv73w5ndt7ksyadgehusej4cg3rk6azremyvmmgaspus2aqub43zgwgcprulzbqb7na4i5f74oviilccho62idlnbffhq7tmehta";
    private static final String YOUR_APP_ID = "7def7aff-76eb-473f-aa58-00cc43d2444f";
    private static final String CLOCKIFY_API_URL = "https://api.clockify.me/api/v1/workspaces/%s/time-entries";

    @Inject
    ObvestilaViri obvestilaViri;

    /**
     * Method to send a message via OneSignal API
     *
     * @param zadeva    The subject of the message
     * @param sporocilo The content of the message
     * @return The response or error message
     */
    public String sendMessage(String zadeva, String sporocilo) {
        try {
            String apiUrl = "https://api.onesignal.com/notifications?c=email" + YOUR_APP_API_KEY;

            String jsonRequest = String.format("{\"app_id\": \"%s\", " +
                            "\"email_subject\": \"%s\", " +
                            "\"email_body\": \"<html>%s</html>\", " +
                            "\"email_from_name\": \"GoalGetterGroup\", " +
                            "\"email_from_address\": \"hello@thetribeofchaos.com\", " +
                            "\"name\": \"GoalGetterGroup\"}",
                    YOUR_APP_ID, zadeva, sporocilo);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("X-Api-Key", YOUR_APP_API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (postResponse.statusCode() == 201) {
                String response = postResponse.body();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response);

                String apiId = jsonNode.get("id").asText();
                String zac = jsonNode.get("start").asText();
                String kon = jsonNode.get("end").asText();

                ObvestilaEnt obvestilaEnt = new ObvestilaEnt();
                obvestilaEnt.setOpis(sporocilo);
                obvestilaEnt.setOuterAPIid(apiId);
                obvestilaEnt.setZacetek(zac);
                obvestilaEnt.setKonec(kon);

                obvestilaViri.postObvestila(obvestilaEnt);

                return apiId;
            } else {
                return "Error sending message: " + postResponse.statusCode() + " - " + postResponse.body();
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException("Error in sendMessage", e);
        }
    }

    /**
     * Method to get a specific time entry by its ID
     *
     * @param timeEntryId The ID of the time entry to retrieve
     * @return The result of the time entry retrieval
     */
    public String getTimeEntry(String timeEntryId) {
        return executeClockifyRequest("GET", timeEntryId, null);
    }

    /**
     * Method to retrieve all time entries
     *
     * @return The result of retrieving all time entries
     */
    public String getAllTimeEntries() {
        return executeClockifyRequest("GET", null, null);
    }

    /**
     * Method to delete a specific time entry by its ID
     *
     * @param timeEntryId The ID of the time entry to delete
     * @return The result of the deletion
     */
    public String deleteTimeEntry(String timeEntryId) {
        return executeClockifyRequest("DELETE", timeEntryId, null);
    }

    /**
     * Generic method for handling Clockify API requests
     *
     * @param method      The HTTP method (GET, DELETE)
     * @param timeEntryId The ID of the time entry (nullable for some requests)
     * @param payload     The request body (nullable for GET/DELETE)
     * @return The result of the request
     */
    private String executeClockifyRequest(String method, String timeEntryId, String payload) {
        try {
            String apiUrl = String.format(CLOCKIFY_API_URL, YOUR_APP_ID);

            if (timeEntryId != null) {
                apiUrl += "/" + timeEntryId;
            }

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("X-Api-Key", YOUR_APP_API_KEY);

            if ("POST".equalsIgnoreCase(method)) {
                requestBuilder.POST(HttpRequest.BodyPublishers.ofString(payload));
            } else if ("DELETE".equalsIgnoreCase(method)) {
                requestBuilder.DELETE();
            } else {
                requestBuilder.GET();
            }

            HttpRequest request = requestBuilder.build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 204) {
                return "Request successful: " + response.body();
            } else {
                return "Error in request: " + response.statusCode() + " - " + response.body();
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException("Error in executeClockifyRequest", e);
        }
    }

    /**
     * Main method to test the API methods
     */
    public static void main(String[] args) {
        ObvestilaAPI obvestilaAPI = new ObvestilaAPI();

        // Example of sending a message
        String zadeva = "Test";
        String sporocilo = "helloWorld"; // Start time in ISO 8601 format
        String result = obvestilaAPI.sendMessage(zadeva, sporocilo);
        System.out.println(result);

        // Example of getting a time entry by its ID
        // String timeEntryId = "6783be8c5be34b0f9660d627"; // Replace with an actual time entry ID from Clockify
        // String getResult = obvestilaAPI.getTimeEntry(timeEntryId);
        // System.out.println(getResult);

        // Example of deleting a time entry by its ID
        // String deleteResult = obvestilaAPI.deleteTimeEntry(timeEntryId);
        // System.out.println(deleteResult);
    }
}
