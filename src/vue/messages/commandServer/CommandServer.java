package vue.messages.commandServer;

import vue.messages.Command;
import vue.server.AServer;


/**
 * 
 * @author Nathaël Noguès
 * 
 */
public abstract class CommandServer extends Command {
	private AServer sender;

	public CommandServer(String commandName, AServer sender) {
		super(commandName);
		this.sender = sender;
	}

	public AServer getSender() {
		return sender;
	}

}
