/*
 * Colton Hines
 * Sinclair Fuh
 * Computer Networks
 * COSC 336.01
 * LoginCommand class- authenticates a user if the password they input matches the password in the database. Prints out any messages from their inbox once
 * logged in.
 */
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class LoginCommand extends Command{

	private int increment = 0;
	@Override
	boolean matches(String keyword) {
		return false;
	}

	@Override
	void perform(Socket S, String username, String message, PrintWriter output, Users u) {
		
		if (u.users.get(username).getPassword().equals(message)){
			if (!u.users.get(username).getConnected()){
				User U = u.users.get(username);
			    output.println("102 Connected as " + username + ".");
			    U.setConnected(true);
			    
			    if (U.getMessages().size() > 0){
			        output.println("You have " + U.getMessages().size() + " messages.");
			        for (Message temp: U.getMessages()){
			    	   output.println("Message from " + temp.getFrom().getUser_name() + " follows: " + temp.getMessage());
			        }
			    }
			    return;
			} 
		}  
		
		return;
		
		
	}}
