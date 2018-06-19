/*
 * Colton Hines
 * Sinclair Fuh
 * Computer Networks
 * COSC 336.01
 * Connection class- main loop of the program that outputs data to the client. 
 */
import java.awt.List;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Connection implements Runnable {

	private static final ArrayList<String> validcommands = new ArrayList<>(Arrays.asList("AUTH", "QUIT", "CRTE", "SEND","USRS", "SALL", "HIST", "HELP"));
	private java.util.Scanner input;
	private PrintWriter output;
	private java.net.Socket socket;
	private User curuser = null;
	private Users users;
	private static HashSet<PrintWriter> printers = new HashSet<PrintWriter>();
	private String logo = "  /$$$$$$            /$$   /$$                                          \r\n" + 
			" /$$__  $$          | $$  | $$                                          \r\n" + 
			"| $$  \\__/  /$$$$$$ | $$ /$$$$$$    /$$$$$$  /$$$$$$$                   \r\n" + 
			"| $$       /$$__  $$| $$|_  $$_/   /$$__  $$| $$__  $$                  \r\n" + 
			"| $$      | $$  \\ $$| $$  | $$    | $$  \\ $$| $$  \\ $$                  \r\n" + 
			"| $$    $$| $$  | $$| $$  | $$ /$$| $$  | $$| $$  | $$                  \r\n" + 
			"|  $$$$$$/|  $$$$$$/| $$  |  $$$$/|  $$$$$$/| $$  | $$                  \r\n" + 
			" \\______/  \\______/ |__/   \\___/   \\______/ |__/  |__/                  \r\n" + 
			"                    /$$$                                                \r\n" + 
			"                   /$$ $$                                               \r\n" + 
			"                  |  $$$                                                \r\n" + 
			"                   /$$ $$/$$                                            \r\n" + 
			"                  | $$  $$_/                                            \r\n" + 
			"                  | $$\\  $$                                             \r\n" + 
			"  /$$$$$$  /$$    |  $$$$/$$       /$$           /$$                    \r\n" + 
			" /$$__  $$|__/     \\____/\\_/      | $$          |__/                    \r\n" + 
			"| $$  \\__/ /$$ /$$$$$$$   /$$$$$$$| $$  /$$$$$$  /$$  /$$$$$$   /$$$$$$$\r\n" + 
			"|  $$$$$$ | $$| $$__  $$ /$$_____/| $$ |____  $$| $$ /$$__  $$ /$$_____/\r\n" + 
			" \\____  $$| $$| $$  \\ $$| $$      | $$  /$$$$$$$| $$| $$  \\__/|  $$$$$$ \r\n" + 
			" /$$  \\ $$| $$| $$  | $$| $$      | $$ /$$__  $$| $$| $$       \\____  $$\r\n" + 
			"|  $$$$$$/| $$| $$  | $$|  $$$$$$$| $$|  $$$$$$$| $$| $$       /$$$$$$$/\r\n" + 
			" \\______/ |__/|__/  |__/ \\_______/|__/ \\_______/|__/|__/      |_______/ \r\n" + 
			"  /$$$$$$                                                               \r\n" + 
			" /$$__  $$                                                              \r\n" + 
			"| $$  \\__/  /$$$$$$   /$$$$$$  /$$    /$$ /$$$$$$   /$$$$$$             \r\n" + 
			"|  $$$$$$  /$$__  $$ /$$__  $$|  $$  /$$//$$__  $$ /$$__  $$            \r\n" + 
			" \\____  $$| $$$$$$$$| $$  \\__/ \\  $$/$$/| $$$$$$$$| $$  \\__/            \r\n" + 
			" /$$  \\ $$| $$_____/| $$        \\  $$$/ | $$_____/| $$                  \r\n" + 
			"|  $$$$$$/|  $$$$$$$| $$         \\  $/  |  $$$$$$$| $$                  \r\n" + 
			" \\______/  \\_______/|__/          \\_/    \\_______/|__/                  ";
	
	String bye = "  /$$$$$$                            /$$ /$$                          \r\n" +
			" /$$__  $$                          | $$| $$                          \r\n" +
			"| $$  \\__/  /$$$$$$   /$$$$$$   /$$$$$$$| $$$$$$$  /$$   /$$  /$$$$$$ \r\n" +
			"| $$ /$$$$ /$$__  $$ /$$__  $$ /$$__  $$| $$__  $$| $$  | $$ /$$__  $$\r\n" +
			"| $$|_  $$| $$  \\ $$| $$  \\ $$| $$  | $$| $$  \\ $$| $$  | $$| $$$$$$$$\r\n" +
			"| $$  \\ $$| $$  | $$| $$  | $$| $$  | $$| $$  | $$| $$  | $$| $$_____/\r\n" +
			"|  $$$$$$/|  $$$$$$/|  $$$$$$/|  $$$$$$$| $$$$$$$/|  $$$$$$$|  $$$$$$$\r\n" +
			" \\______/  \\______/  \\______/  \\_______/|_______/  \\____  $$ \\_______/\r\n" +
			"                                                   /$$  | $$          \r\n" +
			"                                                  |  $$$$$$/          \r\n" +
			"                                                   \\______/           \r\n";
	
	Connection(java.net.Socket socket, Users u) {
		this.setSocket(socket);
		this.users = u;
	}
	
	public User getUser(){
		return curuser;
	}
	public java.util.Scanner input(){
		return input;
	}
	
	public java.io.PrintWriter output(){
		return output;
	}
	
	public java.net.Socket getSocket() {
		return socket;
	}

	public void setSocket(java.net.Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			input = new java.util.Scanner(socket.getInputStream());			
			output = new PrintWriter(socket.getOutputStream(), true);
			int hash = output.hashCode();
			printers.add(output);
			
			
			for(int i = 0; i < logo.length(); i++) {
				 output.print(logo.charAt(i));
			}
			
			
			
			output.println();
			output.println("Type 'help' for a list of commands!");
			CreateCommand c = new CreateCommand();
			LoginCommand l = new LoginCommand();
			String str;
			ArrayList<String> body = new ArrayList<String>();
			String command = "";
			String username = "";
			String details = "";
			Message message;
			User reciever = null;
            
			//Main loop of the program. Performs all commands until the user quits.
			while (!command.equals("QUIT")){
				try{
					str = input.nextLine();
				} catch (NoSuchElementException e){
					return;
				}
				    body = breakString(str);
				    command = body.get(0);
				    username = body.get(1);
				    details = body.get(2);
				    
				    if (command.equals("SALL")) {
				        body = break2(str);
				        command = body.get(0);
				        username = null;
				        details = body.get(1);
				    }
				
				if (!errorcheck(command,username,details,curuser)){
					
				} else if (command.equals("CRTE")){
					output.println(body);
					c.perform(socket, username,details, output, users);
					
				} else if (command.equals("AUTH")){
					l.perform(socket, username, details, output, users);
					
					if (users.users.get(username).getConnected() == true){
						curuser = users.users.get(username);
						output.println(output.hashCode());
						curuser.sethashCode(hash);
						
					}
					
				} else if (command.equals("USRS")){
					if (users.users.isEmpty()){
						output.println("There are no current users");
					} else {
					   for (User u: users.users.values()){
						   if(u.getConnected()) {
							   output.println("Connected Users:");
							   output.println(u.getUser_name());
						   }
						   else {
							   output.println("Unconnected Users:");
							   output.println(u.getUser_name());
						   }
					   }
					}
				} else if (command.equals("SEND")){
					reciever = users.users.get(username);
					message = new Message(curuser,details);
					curuser.addChatlog(message);
					reciever.addChatlog(message);
					
					if (reciever.getConnected()){
						output.println(reciever.hashcode());
						for(PrintWriter p: printers){
							if (reciever.hashcode() == p.hashCode()){
								p.println("Message from " + message.getFrom().getUser_name() + " follows: " + message.getMessage());
							}
						}
						output.println("Message sent.");
					} else {
						reciever.addMessages(message);
						output.println("User currently offline, message sent to inbox.");
					}
					
				} else if (command.equals("HELP")){
					output.println("There are 7 commands excluding HELP: ");
					output.println("CRTE, AUTH, SEND, SALL, HIST, USRS, and QUIT.");
					output.println("CRTE [user] [password] Creates a new user, first user is super user");
					output.println("AUTH [user] [password] Logs in a user");
					output.println("SEND [user] [message] send message to specific user");
					output.println("SALL [message] sends message to all online users. Only accessible by super user!");
					output.println("HIST shows all chat messages displayed per session.");
					output.println("USRS displays a list of all users, sorted by online status");
					output.println("QUIT ends the session for a user.");
				} else if (command.equals("HIST")) {
					if (curuser.getClog().isEmpty()) {
						output.println("No messages are associated with user " + curuser.getUser_name() + ".");
					} else {
		            for (Message m: curuser.getClog()) {
						output.println("[" + m.from() + "]: " + m.message());
					   }
					}
				} else if (command.equals("SALL")) {
					message = new Message(curuser,details);
					
					for (User u: users.users.values()) {
						if (u != curuser && u.getConnected()) {
							u.addChatlog(message);
							for(PrintWriter p: printers) {
								if (u.hashcode() == p.hashCode()){
									p.println("Message from admin " + message.getFrom().getUser_name() + " follows: " + message.getMessage());
								}
							}
						}
					}
					
				}
			}

			for(int i = 0; i < 4; i++) {
				output.println();
			}
			output.println(bye);
			output.println("Press any key to continue...");
			String pause = input.nextLine();
			
			if (curuser != null){
				curuser.setConnected(false);
				curuser = null;
			}
			
			getSocket().close();
			
		} catch (IOException e) {
			if (curuser != null){
				curuser.setConnected(false);
				curuser = null;
			}
			e.printStackTrace();
		} 
				
	}

	//Breaks up string for sendall command.
	public ArrayList <String> break2 (String s){
		ArrayList <String> items = new ArrayList <String>(Arrays.asList("",""));
		String body = s.trim();
        int i = 0;
		
		for(String w: body.split("\\s",2)){
			items.set(i,w);
			i+=1;
		}
		
		items.set(0, items.get(0).toUpperCase());
		return items;
	}
	
	//Breaks up string for the rest of the commands.
	public ArrayList <String> breakString (String s){
		
		
		ArrayList <String> items = new ArrayList <String>(Arrays.asList("","",""));
		String body = s.trim();
		
			
		int i = 0;
		
		for(String w: body.split("\\s",3)){
			items.set(i,w);
			i+=1;
		}
		
		items.set(0, items.get(0).toUpperCase());
		items.set(2, items.get(2).trim());
		
		return items;
	}
	
	//Checks for and prints out all error messages.
	public boolean errorcheck (String command, String username, String message,User curuser){
		
		if (!validcommands.contains(command)){
			output.println("205 No such command " + command + ".");
			return false;
		}
		
		if (command.equals("CRTE") || command.equals("AUTH") || command.equals("SEND")){
			if (username.trim().isEmpty() || message.trim().isEmpty()){
				output.println("207 A field was left empty.");
				return false;
			}
		}
		
		if (command.equals("SALL")) {
			if (curuser == null) {
				output.println("206 Not connected as a user.");
				return false;
			}
			if (message.trim().isEmpty()) {
				output.println("207 You can't send nothing to all users!");
				return false;
			}
			
			if (!curuser.isSuper_user()) {
				output.println("208 Only the super user can send to all users.");
				return false;
			}
		}
		if (!users.users.containsKey(username) && (command.equals("AUTH") || command.equals("SEND"))){
			output.println("200: user " + username + " doesn't exist.");
			return false;
		}
		
		if (command.equals("SEND") || command.equals("HIST") || command.equals("SALL")){
			if(curuser == null){
				output.println("206 Not connected as user.");
				return false;
			}
		}
		
		if (username != null && users.users.containsKey(username)){
			if (command.equals("CRTE")){
				output.println("203: user " + username + " already exists.");
				return false;
			}
			
			if (command.equals("AUTH")){
				if (users.users.get(username).getConnected()){
					output.println("201 user " + username + " already connected.");
					return false;
				}
				
				if (!users.users.get(username).getPassword().equals(message)){
					output.println("204 invalid username or password.");
					return false;
				}
				
				if (curuser!= null){
					output.println("202 Already connected as " + curuser.getUser_name() + ".");
					return false;
				}
				
			}
			
		}
		
		
		
		return true;
	}
	
}
