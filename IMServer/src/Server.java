/*
 * Colton Hines
 * Sinclair Fuh
 * Computer Networks
 * COSC 336.01
 * Server class- creates a connection for each client that connects to the server and spawns a thread for each.
 */
import java.io.IOException;
import java.net.ServerSocket;

public class Server implements java.lang.Runnable {

	private java.net.ServerSocket server_socket;
	private Users user_database = new Users();
	
	public Server(int port) throws java.io.IOException{
		server_socket = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		while(true){
		try {
			Connection c = new Connection(server_socket.accept(), user_database);
			Thread t = new Thread(c);
			t.start();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
}
