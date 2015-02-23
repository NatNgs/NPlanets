package vue.messages.commandPlayer;

import vue.messages.Command;
import vue.players.APlayer;


/**
 * 
 * @author Nathaël Noguès
 * 
 */
public abstract class CommandPlayer extends Command {
	private APlayer sender;

	public CommandPlayer(String commandName, APlayer sender) {
		super(commandName);
		this.sender = sender;
	}

	public APlayer getSender() {
		return sender;
	}

}
