package natngs.nplanets.server.movements.repetitive;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ILocated;
import natngs.nplanets.server.movements.ARelativeMovement;

public class RoundOrbit2D extends ARelativeMovement {
	private final double r;
	private final double initAngle;
	private final double orbitDuration;

	public RoundOrbit2D(ILocated ref, Location init, double orbitDuration) {
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
