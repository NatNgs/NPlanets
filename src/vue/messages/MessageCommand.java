/**
 * 
 */
package vue.messages;


/**
 * @author user
 *
 */
public class MessageCommand extends Message {
	private Command c;

	public MessageCommand(String commandName, Command c) {
		super(commandName);
		this.c = c;
	}

	public Command getCommand() {
		return c;
	}

}
