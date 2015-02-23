package vue.messages;

import vue.players.APlayer;


/**
 * 
 * @author Nathaël Noguès
 * 
 */
public abstract class Command extends Message {
	private APlayer sender;

	public Command(String commandName, APlayer sender) {
		super(commandName);
		this.sender = sender;
	}

	public APlayer getSender() {
		return sender;
	}

}
