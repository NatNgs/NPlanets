package users.test;

import java.util.HashMap;


public abstract class MainTest {


	public static void main(String[] args) {
		ServerTest serv = new ServerTest("s1");

		HashMap<String, String> command = new HashMap<>();
		HashMap<String, String> retour = null;

		command.put("command", "open");
		retour = serv.send(command);

		try {Thread.sleep((long) (500 + Math.random() * 1000));} catch (Exception e) {}
		PlayerTest pl1 = new PlayerTest("p1");

		try {Thread.sleep((long) (500 + Math.random() * 1000));} catch (Exception e) {}
		PlayerTest pl2 = new PlayerTest("p2");

		try {Thread.sleep((long) (500 + Math.random() * 1000));} catch (Exception e) {}
		PlayerTest pl3 = new PlayerTest("p3");

		try {
			Thread.sleep(4000);
		} catch (Exception e) {
		}

		command.put("command", "close");
		retour = serv.send(command);
	}

}
