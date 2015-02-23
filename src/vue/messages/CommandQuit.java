package vue.messages;

import vue.players.APlayer;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class CommandQuit extends Command {

	public CommandQuit(APlayer sender) {
		super("quit", sender);
	}
}
