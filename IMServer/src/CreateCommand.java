/*
 * Colton Hines
 * Sinclair Fuh
 * Computer Networks
 * COSC 336.01
 * CreateCommand class- creates a user and populates user values for that user.
 */
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class CreateCommand extends Command {
    
	boolean first = true;
	
	public boolean matches(String keyword) {
		if (keyword.equals("CRTE")){
			return true;
		}
		return false;
	}

	void perform(Socket S, String username, String message, PrintWriter output, Users users) {
		
		try {
			PrintWriter p = new PrintWriter(S.getOutputStream());
			User newuser = new User(username,message,users.users.values().isEmpty(), p);
			newuser.setConnected(false);
			users.users.put(username, newuser);
			if (newuser.isSuper_user()){
				output.println(username + " created as super user.");
			} else {
				output.println(username + " created as user.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}



}
