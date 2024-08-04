package youareell;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import controllers.*;

public class YouAreEll {
    private TransactionController tt;

    public YouAreEll (TransactionController t) {
        this.tt = t;
    }

    public static void main(String[] args) {
        // hmm: is this Dependency Injection?
        YouAreEll urlhandler = new YouAreEll(
            new TransactionController(
                new MessageController(ServerController.shared()), 
                new IdController(ServerController.shared())
        ));
        System.out.println(urlhandler.get_ids());
    }

    public String get_ids() {
        List<models.Id> allIds = tt.getIds();
        StringBuilder sb = new StringBuilder();
        for (models.Id id : allIds) {
            sb.append(id.toString()+"\n");
        }
        return sb.toString();
    }

    public String get_messages() {
        List<models.Message> latestMessages = tt.getMessages();
        StringBuilder sb = new StringBuilder();
        for (models.Message msg : latestMessages) {
            sb.append(msg.toString()+"\n");
        }
        return sb.toString();
    }


    public String get_messagesOfUser(String userName) {
        List<models.Message> latestMessages = tt.getMessagesOfUser(userName);
        StringBuilder sb = new StringBuilder();
        for (models.Message msg : latestMessages) {
            sb.append(msg.toString()+ "\n");
        }
        return sb.toString();
    }

    public String sendMessageToUser(String message, String fromId, String toId) throws IOException {
        String m = tt.sendMessageToUser(message, fromId, toId);
        return m;
    }

    public String sendMessage(String userName, String message) throws IOException {
        String m = tt.sendMessage(userName, message);
        return m;
    }

    public String postId(String idtoRegister, String name, String githubName) throws IOException {
        return tt.postId(idtoRegister, name, githubName);
    }

    public String putId(String idtoRegister, String name, String githubName) throws IOException {
        return tt.putId(idtoRegister, name, githubName);
    }
}
