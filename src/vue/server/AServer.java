package vue.server;

import vue.messages.Message;
import vue.messages.commandServer.CommandServer;
import control.Control;

public abstract class AServer {
	private String name;

	public AServer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public final Message sendCommand(CommandServer c) {
		return Control.sendCommand(this, c);
	}

	public abstract void recieveInfos(Message m);

}
