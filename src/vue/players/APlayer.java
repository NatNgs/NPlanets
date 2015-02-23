package vue.players;

import vue.messages.Command;
import vue.messages.Message;
import control.Control;

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

	public final Message sendCommand(Command c) {
		return Control.sendCommand(this, c);
	}

	public abstract void recieveInfos(Message m);
}
