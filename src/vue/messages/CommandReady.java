package vue.messages;

import vue.players.APlayer;

public class CommandReady extends Command {

	public CommandReady(APlayer sender) {
		super("ready", sender);
	}

}
