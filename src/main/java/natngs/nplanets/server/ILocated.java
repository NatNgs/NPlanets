package natngs.nplanets.server;

import natngs.nplanets.common.Location;

public interface ILocated {
	Location getLocation(double when);
}
