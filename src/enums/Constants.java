package enums;

public enum Constants {
	PlanetDefaultGenTroupesSpeed(10.0), TroupeDefaultAdaptationTime(10.0);

	private double value;

	private Constants(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
}
