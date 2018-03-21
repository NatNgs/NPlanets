package natngs.nplanets.server.located.planets;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ARelativeLocated;
import natngs.nplanets.server.ILocated;

public class StaticPlanet extends ARelativeLocated {
	private final Location relativeLocation;

	public StaticPlanet(ILocated ref, Location relativeLocation) {
		super(ref);
		this.relativeLocation = relativeLocation;
	}

	@Override
	protected Location getRelativeLocation(double when) {
		return relativeLocation;
	}
}
