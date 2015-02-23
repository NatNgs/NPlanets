
package vue.messages;

/**
 * @author Nathaël Noguès
 * 
 */
public class MessageServer extends Message {
	private String serverName;

	public MessageServer(String commandName, String serverName) {
		super("server_" + commandName);
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

}
