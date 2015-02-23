/**
 * 
 */
package vue.messages.commandServer;

import vue.server.AServer;

/**
 * @author user
 *
 */
public class CommandServerClose extends CommandServer {

	/**
	 * @param commandName
	 * @param sender
	 */
	public CommandServerClose(AServer sender) {
		super("open", sender);
	}

}
