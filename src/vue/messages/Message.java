package vue.messages;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public abstract class Message {
	private String name;

	public Message(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
