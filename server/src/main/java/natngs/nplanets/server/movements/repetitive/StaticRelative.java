package natngs.nplanets.server.movements.repetitive;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ILocated;
import natngs.nplanets.server.movements.ARelativeMovement;

public class StaticRelative extends ARelativeMovement {
	private final Location relativeLocation;

	public StaticRelative(ILocated ref, Location relativeLocation) {
		super(ref);
		this.relativeLocation = relativeLocation;
	}

	@Override
	protected Location getRelativeLocation(double when) {
		return relativeLocation;
	}
}
