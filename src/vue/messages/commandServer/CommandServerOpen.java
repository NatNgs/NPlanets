/**
 * 
 */
package vue.messages.commandServer;

import vue.server.AServer;

/**
 * @author user
 *
 */
public class CommandServerOpen extends CommandServer {

	/**
	 * @param commandName
	 * @param sender
	 */
	public CommandServerOpen(AServer sender) {
		super("open", sender);
	}

}
