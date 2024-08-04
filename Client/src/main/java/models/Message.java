package models;

/* 
 * POJO for an Message object
 *
 *   {
    "sequence": "-",
    "timestamp": "_",
    "fromid": "xt0fer",
    "toid": "kristofer",
    "message": "Hello, Kristofer!"
  },

*
 */

import java.time.OffsetDateTime;

//Message can be compared to other "Message" objects
public class Message implements Comparable<Message> {
    // sample from server
    // {"sequence":"ea9ccec875bbbbdcca464eb59718ae7cba9def95","timestamp":"2023-08-06T18:45:21.083445025Z",
    // "fromid":"xt0fer","toid":"torvalds","message":"Can you hear me now?!"}

    //Private member variables within the message class
    private String message = "";
    private String toid = "";
    private String fromid = "";
    private String timestamp = "";
    private String sequence = "";

    //Default constructor
    public Message() {
    }

    //constructor with message attributes and parameters
    public Message (String message, String fromId, String toId, String timestamp, String sequence) {
        this.message = message;
        this.fromid = fromId;
        this.toid = toId;
        this.timestamp = timestamp;
        this.sequence = sequence;
    }

    //Constructor with timeStamp and sequence
    public Message (String message, String fromId, String toId) {
        this.message = message;
        this.fromid = fromId;
        this.toid = toId;
        //Adding timestamp instance
        this.timestamp = OffsetDateTime.now().toString();
    }

    //Constructor with recipient ID
    public Message (String message, String fromId) {
        this.message = message;
        this.fromid = fromId;
        //Adding timestamp instance
        this.timestamp = OffsetDateTime.now().toString();
        this.toid = "";
    }

    //toString method - provides a string representation of the Message object --> formatted for display
    @Override
    public String toString() {
        return "to: " + this.toid + "\nfrom: "+ this.fromid + "\n" + this.message + "\n----\n";
    }

    //CompareTo Method -> implements the comparable interface - compares attributes between the Messages
    // allowing messages to be sorted based opn their sequence
    @Override
    public int compareTo(Message o) {

        return this.sequence.compareTo(((Message) o).getSequence());
    }

    //Getter and Setters for Message
    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getToid() {

        return toid;
    }

    public void setToid(String toId) {

        this.toid = toId;
    }

    public String getFromid() {

        return fromid;
    }

    public void setFromid(String fromId) {

        this.fromid = fromId;
    }

    public String getTimestamp() {

        return timestamp;
    }

    public String getSequence() {

        return sequence;
    }
}