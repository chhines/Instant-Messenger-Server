/*
 * Colton Hines
 * Sinclair Fuh
 * Computer Networks
 * COSC 336.01
 * Users class- central user database of the server.
 */
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Users {

	Map<String,User> users = new HashMap<String,User>();
	
	Users(){
	}
	
	public User getUser(String user_name) {
		return users.get(user_name);
	}
	
	public boolean isEmpty() {
		return users.isEmpty();
	}
	
	public void newUser(String user_name, String password, boolean super_user, PrintWriter p){
		users.put(user_name, new User(user_name,password,super_user, p));
	}
}
