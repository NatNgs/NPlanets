package users.test;

import inter.APlayer;

import java.util.HashMap;

public class PlayerTest extends APlayer {

	public PlayerTest(String name) {
		super(name);
	}

	@Override
	public void recieveInfos(HashMap<String, String> m) {
		System.err.println(this + " Recieved: " + m + "\n");
		try{Thread.sleep(1000); }catch(Exception e) {}
	}

	public void send(HashMap<String, String> command) {
		System.out.println(this + " Send: " + command);
		try{Thread.sleep(1000); }catch(Exception e) {}

		HashMap<String, String> retour = sendCommand(command);
		System.out.println("Returned: " + retour + "\n");

		try{Thread.sleep(1000); }catch(Exception e) {}
	}

	@Override
	public String toString() {
		return getName();
	}

	public void sendJoin(String serverName) {
		HashMap<String, String> command = new HashMap<>();
		command.put("command", "join");
		command.put("server", serverName);
		send(command);
	}
	public void sendLeave() {
		HashMap<String, String> command = new HashMap<>();
		command.put("command", "leave");
		send(command);
	}
}
