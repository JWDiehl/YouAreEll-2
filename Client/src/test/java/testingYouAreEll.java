import controllers.IdController;
import controllers.MessageController;
import controllers.ServerController;
import controllers.TransactionController;
import org.junit.Before;
import org.junit.Test;
import youareell.YouAreEll;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class testingYouAreEll {

    private YouAreEll urll;

    @Before
    public void setUp() {
        urll = new YouAreEll(new TransactionController(
                new MessageController(ServerController.shared()),
                new IdController(ServerController.shared())));
    }

    @Test
    public void testGetMessages() {
        String actual = urll.get_messages();
        // Assuming that there should be at least one message
        assertTrue("Expected to find at least one message", actual.split(" ").length > 1);
    }

    @Test
    public void testPostNameAndUserNameToServer() throws IOException {
        String results = urll.postId("id", "name", "githubUserName");
        String expected = "Id registered.";
        assertEquals("Expected ID registration message", expected, results);
    }

    @Test
    public void testPutMethod() throws IOException {
        String results = urll.putId("id", "name", "githubUserName");
        String expected = "User updated successfully.";
        assertEquals("Expected user update message", expected, results);
    }

    @Test
    public void testGetMessagesOfUser() {
        String results = urll.get_messagesOfUser("");
        int messageCount = results.split(" ").length;
        // Assuming that the user should have at least one message
        assertTrue("Expected at least one message for user", messageCount > 1);
    }

    @Test
    public void testSendMessage() throws IOException {
        String results = urll.sendMessage("JonnnD", "");
        int messageCount = results.split(",").length;
        // Assuming that sending a message returns a list with 5 items
        assertEquals("Expected 5 messages after sending a message", 5, messageCount);
    }

    @Test
    public void testSendMessageToUser() throws IOException {
        String results = urll.sendMessageToUser("hello world", "JonnnD", "JWDiehl");
        int messageCount = results.split(",").length;
        // Assuming that sending a message to a user returns a list with 5 items

        assertEquals("Expected 5 messages after sending a message to user", 5, messageCount);
    }
}
