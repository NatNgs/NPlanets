package vue.messages.commandPlayer;

import vue.players.APlayer;

public class CommandReady extends CommandPlayer {
	private boolean ready;

	public CommandReady(APlayer sender, boolean ready) {
		super("ready", sender);
		this.ready = ready;
	}

	public boolean isReady() {
		return ready;
	}

}
