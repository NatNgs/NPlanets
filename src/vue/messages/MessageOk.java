package vue.messages;

/**
 * @author Nathaël Noguès
 * 
 */
public class MessageOk extends Message {
	private Command c;

	public MessageOk(Command c) {
		super("ok");
	}

	public Command getCommand() {
		return c;
	}

}
