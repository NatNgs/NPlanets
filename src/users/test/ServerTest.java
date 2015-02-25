package users.test;

import inter.AServer;

import java.util.HashMap;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class ServerTest extends AServer {
	private int sleep;
	private boolean osef;

	public ServerTest(String name) {
		super(name);
		sleep = 1000;
		osef = false;
	}

	@Override
	public void recieveInfos(HashMap<String, String> m) {
		System.out.println("\t" + this + " Recieved: " + m);
		try{Thread.sleep(sleep); }catch(Exception e) {}
	}
	public void send(HashMap<String, String> command) {
		if(!osef) {
			System.out.println(this + " Send: " + command);
			try {Thread.sleep(sleep / 3);} catch (Exception e) {}

			HashMap<String, String> retour = sendCommand(command);
			if (retour.get("state").equals("ok"))
				System.out.println("Returned: " + retour + "\n");
			else
				System.err.println("Returned: " + retour + "\n");

			try {Thread.sleep(sleep * 2 / 3);} catch (Exception e) {}
		}
	}

	@Override
	public String toString() {
		return getName();
	}

	public void open() {
		HashMap<String, String> command = new HashMap<>();
		command.put("command", "open");
		send(command);
	}
	public void close() {
		HashMap<String, String> command = new HashMap<>();
		command.put("command", "close");
		send(command);
	}
	public void ready() {
		HashMap<String, String> command = new HashMap<>();
		command.put("command", "ready");
		send(command);
	}
	public void notReady() {
		HashMap<String, String> command = new HashMap<>();
		command.put("command", "not_ready");
		send(command);
	}

	public void setSleep(int ms) {
		sleep = ms;
	}
	public void setOsef(boolean osef) {
		this.osef = osef;
	}
}
