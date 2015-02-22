package core;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public enum CoefType {
	Attack("attack"), Defense("defense"), Speed("speed"), GenTroupesSpeed(
			"genTroupesSpeed"), AttAdapt("attackAdapt"), DefAdapt(
					"defenseAdapt"), SpdAdapt("speedAdapt");

	private String ts;

	private CoefType(String s) {
		ts = s;
	}

	public String getName() {
		return ts;
	}
}
