package controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

//this class calls the server and returns the string to the calling controller

public class ServerController {
    //base URL for the server = all endpoints are appended
    private String rootURL = "http://zipcode.rocks:8085";

    //A singlton instance of ServerController - only one instance of this class will exist
    private static ServerController svr = new ServerController();

    //private constructor
    private ServerController() {
    }

    //Provides the single instance of ServerController
    public static ServerController shared() {

        return svr;
    }

    /**
     * Sends a GET or POST request to the specified URL.
     *
     * @param url      The URL to send the request to
     * @param method   Either "GET" or "POST"
     * @param body     The body of the request, if any
     * @return         A string containing the response from the server
     */

    //METHOD -- handles sending HTTP requests to the server and returns severs response to a string
    public String sendRequest(String resource, String method, String body) {
        //resource -- the specific endpoint or resource path appended to the base URL
        //method -- the HTTP method to use
        //body -- the body of the request for POST and PUT methods

        try {
            //Construct URL - combine URL and resource to form complete URL
            String urn = rootURL + resource;
            URL obj = new URL(urn);
            //Create an HTTP URL connection and set the request method (GET, POST, PUT)
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            

            if (method.equals("POST") || method.equals("PUT")) {
                // Send POST request using the body parameter
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(body);
                wr.flush();
                wr.close();
            }

            int responseCode = con.getResponseCode();

            BufferedReader in;
            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return content.toString();


            //exception handling
        } catch (IOException e) {
            System.out.println("Error sending request: " + e.getMessage());
            return "";
        }
    }

    //Sends a get message to /messages to retrieve messages
    public String getMessages() {

        return sendRequest("/messages", "GET", "");
    }

    //sneds a get requests to /ids to retrieve IDs
    public String getIds() {

        return sendRequest("/ids", "GET", "");
    }

    public static void main(String[] args) {
        ServerController me = ServerController.shared();
        System.out.println("Ids ************");
        System.out.println(me.getIds());
        // System.out.println("Messages ************");
        // System.out.println(me.getMessages());

    }

    //sends a POST request to /ids with the given body to create a new ID
    public String postId(String body) {
        return sendRequest("/ids", "POST", body);
    }

    //sends a put request to IDS with the  given body to update an existing ID
    public String putId(String body) {
        return sendRequest("/ids", "PUT", body);
    }

    //Sends a POST request to /ids/ from ID / to create a new messages from fromID
    public String sendMessage(String jsonBody, String fromid) {
        return sendRequest("/ids/" + fromid + "/messages", "POST", jsonBody);
    }

    //sends a post request to /ids / fromID / messages to create a message to a specific user
    public String sendMessageToUser(String jsonBody, String fromid, String toid) {
        return sendRequest("/ids/" + fromid + "/messages", "POST", jsonBody);
    }

    //sends a get request to ids/username/ messages to retrieve message for a user with the given userName
    public String getMessagesOfUserName(String userName) {
        return sendRequest("/ids/" + userName + "/messages", "GET", "");
    }
}

// ServerController.shared.doGet()