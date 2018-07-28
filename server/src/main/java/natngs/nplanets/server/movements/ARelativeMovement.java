package natngs.nplanets.server.movements;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ILocated;
import natngs.nplanets.server.movements.IMovement;

public abstract class ARelativeMovement implements IMovement {
	private final ILocated ref;

	protected ARelativeMovement(ILocated ref) {
		this.ref = ref;
	}

	@Override
	public final Location getLocation(double when) {
		return new Location(ref.getLocation(when), getRelativeLocation(when));
	}

	protected abstract Location getRelativeLocation(double when);
}
