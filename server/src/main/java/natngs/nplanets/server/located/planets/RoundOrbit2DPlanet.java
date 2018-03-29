package natngs.nplanets.server.located.planets;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ILocated;

public class RoundOrbit2DPlanet extends Planet {
	private final double r;
	private final double initAngle;
	private final double orbitDuration;

	public RoundOrbit2DPlanet(ILocated ref, Location init, double orbitDuration) {
		super(ref);
		this.orbitDuration = orbitDuration;
		r = init.getDistance(Location.ZERO);
		initAngle = Math.atan2(init.get(1), init.get(0));
	}

	@Override
	protected Location getRelativeLocation(double when) {
		when -= (int)(when / orbitDuration) * orbitDuration; // modulo orbitDuration
		double newAngle = initAngle + when / (orbitDuration / (2 * Math.PI));
		return new Location(r * Math.cos(newAngle), r * Math.sin(newAngle));
	}
}
