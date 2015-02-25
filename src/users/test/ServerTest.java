package users.test;

import inter.AServer;

import java.util.HashMap;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class ServerTest extends AServer {

	public ServerTest(String name) {
		super(name);
	}

	@Override
	public void recieveInfos(HashMap<String, String> m) {
		System.err.println(this + " Recieved: " + m);
	}

	public HashMap<String, String> send(HashMap<String, String> command) {
		HashMap<String, String> retour = sendCommand(command);
		System.out.println(this + " Send: " + command + "\nReturned: " + retour
				+ "\n");
		return retour;
	}

	@Override
	public String toString() {
		return getName();
	}
}
