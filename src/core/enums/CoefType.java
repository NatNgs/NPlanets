package core.enums;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public enum CoefType {
	Attack("attack"), Defense("defense"), Speed("speed"), GenTroupesSpeed(
			"genTroupesSpeed");

	private String ts;

	private CoefType(String s) {
		ts = s;
	}

	public String getName() {
		return ts;
	}
}
