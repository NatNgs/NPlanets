package natngs.nplanets.server.movements;

import natngs.nplanets.common.Location;

public interface IMovement {
	Location getLocation(double when);
}
