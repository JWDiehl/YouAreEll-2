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
    public ArrayList<Message> getMessages() {
       String jsonInput = sc.getMessages();
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();
        List<Message> msgs;
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
    public ArrayList<Message> getMessagesForId(Id Id) {

        return null;
    }
    public Message getMessageForSequence(String seq) {

        return null;
    }
    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {

        return null;
    }

    public Message postMessage(Id myId, Id toId, Message msg) {

        return null;
    }


    public List<Message> getMessageOfUsername(String userName) {
        String jsonInput = sc.getMessagesOfUserName(userName);

        //convert JSON to array of IDs
        ObjectMapper mapper = new ObjectMapper();
        List<Message> msgs;

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

    public String sendMessage(Message m) throws IOException {
        //String JSON;
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, m);
        String jsonBody = writer.toString();
        String results = sc.sendMessage(jsonBody, m.getFromid());

        return results;
    }

    public String sendMessageToUser(Message m) throws IOException {
        //String JSON;
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, m);
        String jsonBody = writer.toString();
        String results = sc.sendMessageToUser(jsonBody, m.getFromid(), m.getToid());

        return results;
    }
 
}