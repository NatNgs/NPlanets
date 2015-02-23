package vue.messages;

import vue.players.APlayer;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class CommandJoin extends Command {

	public CommandJoin(String commandName, APlayer sender) {
		super("join", sender);
	}

}
