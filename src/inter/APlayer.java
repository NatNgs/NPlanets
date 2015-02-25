package inter;

import java.util.HashMap;

import core.control.Control;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public abstract class APlayer {
	private String name;

	public APlayer(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final HashMap<String, String> sendCommand(HashMap<String, String> c) {
		return Control.sendCommand(this, c);
	}

	public abstract void recieveInfos(HashMap<String, String> m);
}
