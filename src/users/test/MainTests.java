package users.test;


public class MainTests {
	private int wait;

	public MainTests(int sleepTime) {
		wait = sleepTime;
	}

	private void testServer() {
		System.out.println("\n##### ##### ##### TEST SERVER ##### ##### #####");
		ServerTest s1 = new ServerTest("s1");
		ServerTest s2 = new ServerTest("s2");
		ServerTest s12 = new ServerTest("s1");
		s1.setSleep(wait);
		s2.setSleep(wait);

		// Tests illegal things when not open
		s1.close();
		s1.notReady();
		s1.ready();

		// Tests open
		s1.open();

		// Tests open twice
		s1.open();

		// Tests close
		s1.close();

		// Tests reopen
		s1.open();

		// Tests illegal things when not open but other server is open
		s2.close();
		s2.notReady();
		s2.ready();

		// Tests open 2nd server
		s2.open();

		// Test close server
		s2.close();

		// Tests open server same name
		s12.open();

		// Close servers
		s1.setOsef(true);
		s2.setOsef(true);
		s12.setOsef(true);
		s1.close();
		s2.close();
		s12.close();
		System.err.println("\n#### #### #### TEST SERVER #### #### ####\n\n");
		sleep(wait * 2);
	}

	private void testLobby() {
		System.out.println("\n##### ##### ##### TEST LOBBY ##### ##### #####");
		ServerTest s1 = new ServerTest("s1");
		PlayerTest p1 = new PlayerTest("p1");
		PlayerTest p2 = new PlayerTest("p2");
		s1.setSleep(wait);
		p1.setSleep(wait);
		p2.setSleep(wait);

		// Opening server
		s1.open();

		// Player doing illegal things
		p1.leave();
		p1.ready();
		p1.notReady();
		p1.join("wrong_server_name");

		// Player joining
		p1.join("s1");

		// Player doing illegal things
		p1.join("wrong_server_name");
		p1.join("s1");
		p1.notReady();

		// Server cant be ready
		s1.ready();
		s1.notReady();

		// Second player joining
		p2.join("s1");

		// And first player doing some illegal things
		p1.join("wrong_server_name");
		p1.join("s1");
		p1.notReady();

		// Second player quit and rejoin
		p2.leave();
		p2.join("s1");

		// Player is ready, or not
		p1.ready();
		p1.ready();
		p1.notReady();
		p1.notReady();

		// Server ready
		s1.ready();

		// Player leave (server should be not ready after that)
		p2.leave();

		// Server ready
		s1.ready();

		// Closing server with players on it
		s1.close();

		System.err.println("\n#### #### #### TEST LOBBY #### #### ####\n\n");
		sleep(wait * 2);
	}
	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		MainTests mt = new MainTests(1000);
		// mt.testServer(); // OK
		// mt.testLobby(); // OK
	}
}
