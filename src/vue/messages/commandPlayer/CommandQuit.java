package vue.messages.commandPlayer;

import vue.players.APlayer;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class CommandQuit extends CommandPlayer {

	public CommandQuit(APlayer sender) {
		super("quit", sender);
	}
}
