package controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Id;

public class IdController {
    ServerController sc;
    private HashMap<String, Id> allIds;

    Id myId;

    public IdController(ServerController shared) {
        sc = shared;
        allIds = new HashMap<String, Id>();
    }

    public ArrayList<Id> getIds() {
        String jsonInput = sc.getIds();
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();
        List<Id> ids;
        try {
            ids = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Id.class));

            ArrayList<Id> idList = new ArrayList<>(ids);
            // return array of Ids
            return idList;
        } catch (JsonMappingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return null;
    }

    public Id postId(Id id) throws IOException {
        // create json from id
        StringWriter writer = new StringWriter(); //StringWriter creates a StringWriter to capture JSON output
        ObjectMapper mapper = new ObjectMapper(); //ObjectMapper instance for converting objects to and from JSON
        mapper.writeValue(writer, id); // Converts the ID object into JSON and writes it to the StringWriter
        String json = writer.toString(); // Retrieves the JSON String representation of the Id object
        System.out.println("string: " + json);
        // call server, get json result Or error
        String results = sc.postId(json); // Sends the JSON string to the server using the POSTID method that i wrote in ServerController and recieves a response

        // result json to Id obj

        return mapper.readValue(results, Id.class); // Converts the servers JSON response back into an Id object
    }

    public Id putId(Id id) throws IOException {
        // create json from id
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, id);
        String json = writer.toString();
        System.out.println("string: " + json);
        // call server, get json result Or error
        String results = sc.putId(json); // Sends the JSON string to the server using the PUTID method that i wrote in ServerController - recieves a respnse

        // result json to Id obj

        return mapper.readValue(results, Id.class);
    }
 
}