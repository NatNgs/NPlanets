package enums;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public enum Constants {
	PlanetDefaultGenTroupesSpeed(10), VaisseauDefaultSpeed(1), TroupeDefaultAdaptationTime(
			10);

	private double value;

	private Constants(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
}
