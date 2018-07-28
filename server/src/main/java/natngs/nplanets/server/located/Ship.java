package natngs.nplanets.server.located;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ILocated;
import natngs.nplanets.server.movements.IMovement;

public class Ship implements ILocated {
	private final IMovement movement;

	public Ship(IMovement movement) throws Exception {
		this.movement = movement;
	}

	@Override
	public Location getLocation(double when) {
		return movement.getLocation(when);
	}


	@Override
	public String toString() {
		return movement.toString();
	}
}
