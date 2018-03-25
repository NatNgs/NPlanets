package natngs.nplanets.server.located.planets;

import natngs.nplanets.server.ARelativeLocated;
import natngs.nplanets.server.ILocated;

public abstract class Planet extends ARelativeLocated {
	protected Planet(ILocated ref) {
		super(ref);
	}
}
