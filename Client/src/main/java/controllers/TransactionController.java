package controllers;

import models.Id;
import models.Message;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TransactionController {
    // Base URL for the server
    private String rootURL = "http://zipcode.rocks:8085";
    private MessageController msgCtrl; // an instance of MessageController -- used to manage messages
    private IdController idCtrl; // instance of IdController used to manage IDszzzz

    //Initializes the TransactionController with instances of MessageController and IDController -- allows the transactionController to delegate operatiosn to these controllers
    public TransactionController(MessageController m, IdController j) {
        msgCtrl = m;
        idCtrl = j;
    }

    //Retireves a list of ID objects by delegating idCtrl
    public List<Id> getIds() {

        return idCtrl.getIds();
    }

    //placeholder methods for retrieving and setting a specific ID currently returns NULL
    public String getId(String id) {
        return null;
    }
    public String putId(String id) {
        return null;
    }

    public String deleteId(String id) {
        return null;
    }

    public String postId(String idtoRegister, String name, String githubName) throws IOException {
        Id tid = new Id(idtoRegister, name, githubName);
        tid = idCtrl.postId(tid);
        return ("Id registered.");
        //return null;
    }

    public String putId(String idtoRegister, String name, String githubName) throws IOException {
        Id tid = new Id(idtoRegister, name, githubName);
        tid = idCtrl.putId(tid);
        return "User dupdated succesfully.";
    }

    //Retrieves a list of messages by delegating to msgCtrl
    public List<Message> getMessages() {

        //returns a list of message objects
        return msgCtrl.getMessages();
    }

    //Retrieves messages for specific user by delegating top msgCtrl
    public List<Message> getMessagesOfUser(String userName) {
        //returns a list of message objects for the given username
        return msgCtrl.getMessageOfUsername(userName);
    }

    //Sends a message from a specific user
    public String sendMessage(String userName, String message) throws IOException {

        Date date = new Date();
        //Initializes a messages with the provided content and senders username
        Message m = new Message(message, userName);
        //Uses msgCtrl to send a message
        String t = msgCtrl.sendMessage(m);

        return t;
    }

    public String sendMessageToUser(String message, String fromId, String toId) throws IOException {
        Date date = new Date();
        //Initializes Message with the provided content, senders ID and recipient ID
        Message m = new Message(message, fromId, toId);

        //Ues msg CTRL to send the message
        String t = msgCtrl.sendMessageToUser(m);
        //Returns the servers response
        return t;
    }
}
