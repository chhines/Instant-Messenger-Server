import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

//CRTE, AUTH, SEND, QUIT

public abstract class Command extends Object{
   
	abstract boolean matches (String keyword);
	
	abstract void perform(Socket S, String username, String message, PrintWriter output, Users u);
	
	
}
