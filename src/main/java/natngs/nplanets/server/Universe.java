package java.natngs.nplanets.server;

import java.natngs.nplanets.common.ILocated;
import java.natngs.nplanets.common.Location;

public class Universe implements ILocated {
	@Override
	public Location getLocation(double when) {
		return Location.ZERO;
	}
}
