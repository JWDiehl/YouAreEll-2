package controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Id;
import models.Message;

public class MessageController {

    //Reference to the ServerController
    //Instance used to communicate with the server
    ServerController sc;

    //A hashset to keep track of messages that have already been seen
    //The same message should not be processed more than once
    private HashSet<Message> messagesSeen;
    // why a HashSet??

    //A hashSet does not allow duplicate entries, therefore this will ensure each message is stored only once

    public MessageController(ServerController shared) {
        sc = shared;
        messagesSeen = new HashSet<Message>();
    }

    //get Methods to fetch all messages from the server and converts them from JSON to message objects in Java
    public ArrayList<Message> getMessages() {
       String jsonInput = sc.getMessages(); // retrieves messages in JSON format
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper(); // convert JSON to List<Message>
        List<Message> msgs;

        //Error - handling
        try {
            msgs = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Message.class));

            ArrayList<Message> msgList = new ArrayList<>(msgs);
            // return array of Ids
            return msgList;
        } catch (JsonMappingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return null;
    }

    //fetch messages with a specific ID
    public ArrayList<Message> getMessagesForId(Id Id) {

        return null;
    }

    //fetch a specific message based on its sequence identifier - like the JSON in PostMan
    public Message getMessageForSequence(String seq) {

        return null;
    }

    //fetch messages exchanged with a specific friend but the implementation is not provided
    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {

        return null;
    }

    //Intended to post a message from myID to toID
    public Message postMessage(Id myId, Id toId, Message msg) {

        return null;
    }


    //fetch messages for a specific username and converts them from JSON to a list of Message objects
    public List<Message> getMessageOfUsername(String userName) {
        String jsonInput = sc.getMessagesOfUserName(userName); // retrieves the messages in JSON format

        //convert JSON to array of IDs
        ObjectMapper mapper = new ObjectMapper(); // ObjectMapper to convert to JSON to a List<Message>
        List<Message> msgs;

        //Catches and handles erros in JSON processing
        try {
            msgs = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Message.class));

            ArrayList<Message> msgList = new ArrayList<>(msgs);
            //return Array of IDs
            return msgList;
        } catch (JsonMappingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return null;
    }

    //THIS is for sending messages to a specific user
    public String sendMessage(Message m) throws IOException {
        //String JSON;
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, m);
        String jsonBody = writer.toString();
        String results = sc.sendMessage(jsonBody, m.getFromid()); // calls with the JSON string, senders ID

        return results;
    }

    public String sendMessageToUser(Message m) throws IOException {
        //String JSON;
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, m);
        String jsonBody = writer.toString();
        String results = sc.sendMessageToUser(jsonBody, m.getFromid(), m.getToid()); //  // calls with the JSON string, senders ID and recipients ID

        return results;
    }
 
}