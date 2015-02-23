
package vue.messages;

/**
 * @author Nathaël Noguès
 * 
 */
public class MessagePlayerJoin extends Message {
	private String playerName;

	public MessagePlayerJoin(String playerName) {
		super("player_join");
	}

	public String getPlayerName() {
		return playerName;
	}

}
