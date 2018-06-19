/*
 * Colton Hines
 * Sinclair Fuh
 * Computer Networks
 * COSC 336.01
 * User class- defines the user type for the server. Every user has a connection, an inbox (messages), a history (chatlog), a password, username, printwriter, hashcode,
 * and booleans defining whether the user is connected or whether they are a superuser.
 */
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class User extends Object{

	private Connection connection;
	private java.util.List<Message> messages = new ArrayList<Message>();
	private java.util.List<Message> Chatlog = new ArrayList<Message>();
	private String password;
	private boolean super_user;
	private String user_name;
	private PrintWriter p;
	private boolean connected = false;
	private int hashcode = 0;
	
	public User(String user_name, String password, boolean super_user, PrintWriter p) {
		this.user_name = user_name;
		this.password = password;
		this.super_user = super_user;
		this.p = p;
	}
	
	public boolean authenticate (String input){
		if (input.equals(this.password)){
			return true;
		} else {
		    return false;
		}
	}
	
	public void clearMessages(){
		this.messages.clear();
	}
	
	public void connect (Connection c){
		this.connection = c;
	}
	
	public void disconnect (){
		try {
			this.connection.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sethashCode (int h){
		this.hashcode = h;
	}
	
	public int hashcode(){
		return hashcode;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public java.util.List<Message> getMessages() {
		return messages;
	}

	public void addMessages(Message m) {
		this.messages.add(m);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSuper_user() {
		return super_user;
	}

	public void setSuper_user(boolean super_user) {
		this.super_user = super_user;
	}

	public List<Message> getClog(){
		return Chatlog;
	}
	
	public void addChatlog (Message m) {
		this.Chatlog.add(m);
	}
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public PrintWriter getPrintWriter (){
		return p;
	}
	
	public java.util.List<Message> messages(){
		return this.messages;
	}
	
	public boolean getConnected(){
		return connected;
	}
	
	public void setConnected(boolean t){
		this.connected = t;
	}
	}

