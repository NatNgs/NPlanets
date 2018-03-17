package java.natngs.nplanets.server.planets;

import java.natngs.nplanets.common.ILocated;
import java.natngs.nplanets.common.Location;
import java.natngs.nplanets.server.ARelativeLocated;

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
