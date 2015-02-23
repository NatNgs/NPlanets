package vue.messages;

/**
 * @author Nathaël Noguès
 * 
 */
public class MessageError extends Message {
	private String errorType;
	private Command commandError;

	public MessageError(String errorType, Command commandError) {
		super("error");
	}

	public String getErrorType() {
		return errorType;
	}

	public Command getCommandError() {
		return commandError;
	}

}
