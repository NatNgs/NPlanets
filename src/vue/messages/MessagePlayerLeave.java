
package vue.messages;

/**
 * @author Nathaël Noguès
 * 
 */
public class MessagePlayerLeave extends Message {
	private String playerName;

	public MessagePlayerLeave(String playerName) {
		super("player_leave");
	}

	public String getPlayerName() {
		return playerName;
	}

}
