package vue.messages;

import vue.players.APlayer;

public class CommandReady extends Command {
	private boolean ready;

	public CommandReady(APlayer sender, boolean ready) {
		super("ready", sender);
	}

	public boolean isReady() {
		return ready;
	}

}
