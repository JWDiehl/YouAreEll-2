package youareell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import controllers.IdController;
import controllers.MessageController;
import controllers.ServerController;
import controllers.TransactionController;

// URLShell is a Console view for youareell.YouAreEll.
public class URLShell {
    public static void prettyPrint(String output) {
        // yep, make an effort to format things nicely, eh?
        System.out.println(output);
    }

    //Entry point of the application --> an instance of URLShell and its run method

    //new Transaction controller initialized with MessageController and IdController
    public static void main(String[] args) throws java.io.IOException {
        new URLShell().run();
    }

    public void run() throws IOException {
        YouAreEll urll = new YouAreEll(new TransactionController(new MessageController(ServerController.shared()),
                new IdController(ServerController.shared())));

        //Continuesly reads user input from the console
        String commandLine;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        ProcessBuilder pb = new ProcessBuilder();
        List<String> history = new ArrayList<String>();
        int index = 0;
        //we break out with <ctrl c>
        while (true) {
            //read what the user enters
            System.out.println("cmd? ");
            commandLine = console.readLine();

            //input parsed into array of strings(command and arguments)
            String[] commands = commandLine.split(" ");
            List<String> list = new ArrayList<String>();

            //if the user entered a return, just loop again
            if (commandLine.equals(""))
                continue;
            if (commandLine.equals("exit")) {
                System.out.println("\n*** Bye!\n");
                break;
            }

            //loop through to see if parsing worked
            for (int i = 0; i < commands.length; i++) {
                //System.out.println(commands[i]); //***check to see if parsing/split worked***
                list.add(commands[i]);
                System.out.println(list);

            }
            //System.out.print(list); //***check to see if list was added correctly***
            history.add(commandLine);
            System.out.println("history" + history);
            try {
                //display history of shell with index
                if (list.get(list.size() - 1).equals("history")) {
                    for (String s : history)
                        System.out.println((index++) + " " + s);
                    // read();
                    continue;
                }

                // Specific Commands.

                // ids
                if (list.get(0).contains("ids")) {
                    String results = urll.get_ids();
                    URLShell.prettyPrint(results);
                    //  read();
                    continue;
                }

                // messages


                if (list.get(0).contains("messages") && list.size() == 1) {
                    String results = urll.get_messages();
                    URLShell.prettyPrint(results);
                    continue;
                }
                // get last 20 messages sent to specific user
                // you need to add a bunch more.
                if (list.get(0).contains("messages") && list.size() == 2) {
                    String results = urll.get_messagesOfUser(list.get(1));
                    URLShell.prettyPrint(results);
                    continue;
                }
                if (list.get(0).contains("send") && list.contains("to") && list.size() > 3) {
                    int indexOfUserTo = list.indexOf("to") + 1;
                    //  System.out.println("index of " + list.get(indexOfUserTo));


                    String message = "";
                    for (int i = 2; i < indexOfUserTo - 1; i++) {
                        message += list.get(i) + " ";
                    }
                    // System.out.println(message);
//
                    String results = urll.sendMessageToUser(message, list.get(1), list.get(indexOfUserTo));
                    URLShell.prettyPrint(results);
                    continue;
                }
                if (list.get(0).contains("send") && list.size() == 3) {
                    String message = "";
                    for (int i = 2; i < list.size(); i++) {
                        message += list.get(i);
                    }

                    String results = urll.sendMessage(list.get(1), message);
                    System.out.println("1 " + list.get(1));
                    System.out.println("2" + list.get(2));
                    URLShell.prettyPrint(results);
                    continue;
                }
                // method for post new id to server
                if (list.get(0).contains("id")) {
                    String results=  urll.postId(list.get(1), list.get(2), list.get(3));
                    URLShell.prettyPrint(results);
//                     Process process = pb.start();
//                      String results= read(process);
//                      System.out.println("results are : " + results);
                    continue;
                }
                // method for put, to updated user
                if (list.get(0).contains("put")) {
                    String results = urll.putId(list.get(1), list.get(2), list.get(3));
                    URLShell.prettyPrint(results);
                    continue;
                }


                //!! command returns the last command in history
                if (list.get(list.size() - 1).equals("!!")) {
                    pb.command(history.get(history.size() - 2));
//                   Process process = pb.start();
//                 String results= read(process);
//                   System.out.println("results are : " + results);


                }//!<integer value i> command
                // there is BUG in this code, can you find it?
                else if (list.get(list.size() - 1).charAt(0) == '!') {
                    int b = Character.getNumericValue(list.get(list.size() - 1).charAt(1));
                    if (b <= history.size())//check if integer entered isn't bigger than history size
                        pb.command(history.get(b));
                } else {
                    pb.command(list);
                }

                // // wait, wait, what curiousness is this?
                // Process process = pb.start();
//
//                public void read(){
//                    try{
//                    Process process = pb.start();//obtain the input stream
//                    InputStream is = process.getInputStream();
//                    InputStreamReader isr = new InputStreamReader(is);
//                    BufferedReader br = new BufferedReader(isr);
//
//                    //read output of the process
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        System.out.println(line);
//                        br.close();
//                    }
//                } catch(IOException e){
//                        System.out.println("Input Error, Please try again!");
//                    }
//
//            }
//                finally {
//                 System.out.println("Input Error, Please try again!");
//            }

                //catch ioexception, output appropriate message, resume waiting for input
                // catch (IOException e) {
                //     System.out.println("Input Error, Please try again!");
                // }
                // So what, do you suppose, is the meaning of this comment?
                /** The steps are:
                 * 1. parse the input to obtain the command and any parameters
                 * 2. create a ProcessBuilder object
                 * 3. start the process
                 * 4. obtain the output stream
                 * 5. output the contents returned by the command
                 */


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



    }
    public String read(Process process){
        try{
            // ProcessBuilder pb = new ProcessBuilder();

            //Process process = pb.start();//obtain the input stream
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();

            //read output of the process
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                stringBuilder.append(line);
                stringBuilder.append(System.getProperty("line.seperator"));
                br.close();
                // br.close();
            }
            return stringBuilder.toString();
        } catch(IOException e){
            System.out.println("Input Error, Please try again!");
        }
        return "";

    }

}