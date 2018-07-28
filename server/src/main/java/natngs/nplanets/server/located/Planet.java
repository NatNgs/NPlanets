package natngs.nplanets.server.located;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ILocated;
import natngs.nplanets.server.movements.ARelativeMovement;

public class Planet implements ILocated {
	private final ARelativeMovement movement;

	public Planet(ARelativeMovement movement) {
		this.movement = movement;
	}

	@Override
	public Location getLocation(double when) {
		return movement.getLocation(when);
	}
}
