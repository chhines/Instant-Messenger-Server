/*
 * Colton Hines
 * Sinclair Fuh
 * Computer Networks
 * COSC 336.01
 * Application class- creates a server listening on port 77778 and spawns a thread for the server.
 */
import java.io.IOException;


public class Application {
	
	public Application() {
	}

	public static void main(String[] args) throws IOException {

		Server server = new Server(7778);
		Thread t = new Thread(server);
		t.start();
	}
}
