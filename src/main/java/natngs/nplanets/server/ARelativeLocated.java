package natngs.nplanets.server;

import natngs.nplanets.common.ILocated;
import natngs.nplanets.common.Location;

public abstract class ARelativeLocated implements ILocated {
	private final ILocated ref;

	protected ARelativeLocated( ILocated ref) {
		this.ref = ref;
	}

	@Override
	public final Location getLocation(double when) {
		return new Location(ref.getLocation(when), getRelativeLocation(when));
	}

	protected abstract Location getRelativeLocation(double when);
}
