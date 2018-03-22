package natngs.nplanets.server;

import natngs.nplanets.common.Location;

public interface ILocated {
	int DIM = 2;

	Location getLocation(double when);
}
