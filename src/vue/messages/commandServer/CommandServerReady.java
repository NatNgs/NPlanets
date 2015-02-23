/**
 * 
 */
package vue.messages.commandServer;

import vue.server.AServer;

/**
 * @author user
 *
 */
public class CommandServerReady extends CommandServer {
	private boolean ready;

	/**
	 * @param commandName
	 * @param sender
	 */
	public CommandServerReady(AServer sender, boolean ready) {
		super("open", sender);
		this.ready = ready;
	}

	public boolean isReady() {
		return ready;
	}

}
