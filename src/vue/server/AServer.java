package vue.server;

import java.util.HashMap;

import control.Control;

public abstract class AServer {
	private String name;

	public AServer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public final HashMap<String, String> sendCommand(HashMap<String, String> c) {
		return Control.sendCommand(this, c);
	}

	public abstract void recieveInfos(HashMap<String, String> m);

}
