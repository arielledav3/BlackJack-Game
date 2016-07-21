import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Scanner keyboard = new Scanner(System.in);
    private static final int SERVER_PORT = 5000;

    //user enters string
    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            //getting rid of extra spaces
            String menuValue = keyboard.nextLine().trim();

            //NEW CODE FOR TXT
            //reading the txt file name
            String fileName = "rules.txt";

            //one line at a time
            String line = null;

            try {
                FileReader fileReader = 
                    new FileReader(fileName);

                BufferedReader bufferedReader = 
                    new BufferedReader(fileReader);

                while((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }    

                //closing the file
                bufferedReader.close();            
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                    "Unable to open: " + 
                    fileName);                
            }
            catch(IOException ex) {
                System.out.println(
                    "Error reading:  " 
                    + fileName);
            }
            //END TXT CODE
            
            if (!menuValue.isEmpty()) {
                return menuValue;
            }
            
            //if user types in wrong value
            System.out.println("Error! Enter 1 or 2: ");
        }
    }

    //user enters an integer
    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(keyboard.nextLine());
            } catch (Exception e) {
            	//if used types in wrong valus
                System.out.println("Error! Enter 1 or 2: ");
            }
        }
    }
    
    //reads lines from server
    private static String readServerMessages(BufferedReader fromServer) throws Exception {
        String string = "";
        
        while(true) {
            String message = fromServer.readLine();
            
            if(message.equalsIgnoreCase("EOL")) {
                break;
            }
            
            string += message + "\n";
        }
        
        //getting rid of empty spaces
        return string.trim();
    }
public static void startGame(String server){
	//getting the game started
	//'localhost'
	try {
		// Attempt to connect
        Socket socket = new Socket(server, SERVER_PORT);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter toServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        
        System.out.println("Waiting for server.\n");

        //starting cards are grabbed
        String message = readServerMessages(fromServer);
        System.out.println(message);
        
        //closes the socket
        if(message.contains("on going")) {
            socket.close();
            return;
        }
        
        //Arielle's code
        boolean going = true;
        //menu
        while(going == true) {
            System.out.println("Menu:");
            System.out.println("1: Hit");
            System.out.println("2: Stay");
            
            int choice = readInt("Your decision: ");
            
            if(choice == 1) {
                //hit
                toServer.println("HIT");
                message = readServerMessages(fromServer);
                
                System.out.println(message);
                
                //checking is player went bust
                if(message.contains("BUST")) {
                    //Arielle's code
                	going = false;
                	break;	
                }
            } else if(choice == 2) {
            	//stay
                toServer.println("STAY");
                System.out.println(readServerMessages(fromServer));
                going = false;
                break;
            } else {
                System.out.println("Error! Invalid choice.");
            }
        }
        
        //close socket
        socket.close();
    } catch(Exception e) {
        System.out.println("Error! Failed to connect to server. Try again!");
    }
}
    //client
    public static void main(String[] args) {
    	// the code for the loop
    	//Jannet Code
        String IpAddress = readString("Enter IP address: ");
       // while(true){
        	startGame(IpAddress);
        	//String answer = readString("Would you like to play again? (y/n): ");
        	//if(answer.equalsIgnoreCase("n")){
        	//	System.exit(0);
        	//}
        //}      
    }
}
