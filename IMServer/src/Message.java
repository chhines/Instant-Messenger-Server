/*
 * Colton Hines
 * Sinclair Fuh
 * Computer Networks
 * COSC 336.01
 * Message class- defines messages as a string with an associated user (sender).
 */
public class Message {

		public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

		private User from;
		private String message;
		
		Message(User f, String m){
			this.from = f;
			this.message = m;
		}
		
		public User from() {
			return this.from;
		}
		
		public String message(){
			return this.message;
		}
		
}
