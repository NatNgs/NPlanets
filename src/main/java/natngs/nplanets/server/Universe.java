package natngs.nplanets.server;

import natngs.nplanets.common.ILocated;
import natngs.nplanets.common.Location;

public class Universe implements ILocated {
	@Override
	public Location getLocation(double when) {
		return Location.ZERO;
	}
}
