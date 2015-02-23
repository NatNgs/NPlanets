
package vue.messages;

/**
 * @author Nathaël Noguès
 * 
 */
public class MessagePlayer extends Message {
	private String playerName;

	public MessagePlayer(String commandName, String playerName) {
		super("player_" + commandName);
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

}
