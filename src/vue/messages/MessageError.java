package vue.messages;


/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class MessageError extends MessageCommand {
	private String errorType;

	public MessageError(String errorType, Command commandError) {
		super("error", commandError);
	}

	public String getErrorType() {
		return errorType;
	}
}
