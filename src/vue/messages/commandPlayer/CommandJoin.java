package vue.messages.commandPlayer;

import vue.players.APlayer;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class CommandJoin extends CommandPlayer {
	private String serverName;

	public CommandJoin(String serverName, APlayer sender) {
		super("join", sender);
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

}
