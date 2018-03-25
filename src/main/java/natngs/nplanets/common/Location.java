package natngs.nplanets.common;

import static natngs.nplanets.server.ILocated.DIM;

public class Location extends Vector {
	public static final Location ZERO;

	static {
		double[] loc = new double[DIM];
		for (int i = DIM - 1; i >= 0; --i) {
			loc[i] = 0;
		}
		ZERO = new Location(loc);
	}

	public Location(Location c, Vector decal) {
		super(c, decal);
	}

	public Location(double... loc) {
		super(loc);
	}

	/**
	 * @param cSrc  position will be returned if d is 0.0
	 * @param cDest position will be returned if d is 1.0
	 * @param d     position percentage from cSrc to cDest in range [0, 1] (0.0 =
	 *              0%, 1.0 = 100%)
	 * @return new Coordinate at d% of traject from cSrc to cDest
	 */
	public static Location getMid(Location cSrc, Location cDest, double d) {
		if (d <= 0)
			return cSrc;
		if (d >= 1)
			return cDest;

		Location res = new Location(cSrc, ZERO);
		for (int i = DIM - 1; i >= 0; --i)
			res.loc[i] = (int)((res.loc[i] * (1 - d) + cDest.loc[i] * d) / DELTA) * DELTA;

		return res;
	}
}
