package natngs.nplanets.common;

import java.util.Arrays;

public class Location {
	public static final double DELTA = 0.004;
	private static final int DIM = 3;
	public static final Location ZERO = new Location(0, 0, 0);
	public static final int X = 0, Y = 1, Z = 2;
	private final double[] loc;

	public Location(Location c, Location decal) {
		loc = new double[DIM];
		for(int i=DIM-1; i>=0; --i) {
			loc[i] = c.loc[i] + decal.loc[i];
		}
	}
	public Location(double... xyz) {
		if(xyz.length != DIM)
			throw new IllegalArgumentException(
					this.getClass() + "::new: Wrong dimension numbers: "+xyz.length+" instead of "+ DIM);
		loc = xyz;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Location)
			return getDistance((Location)obj) < DELTA;
		else if (obj instanceof double[] && ((double[]) obj).length == DIM)
			return getDistance(new Location((double[]) obj)) < DELTA;
		else
			return super.equals(obj);
	}

	public double getDistance(Location c) {
		double d = 0;
		for (int i = DIM-1; i >= 0; --i)
			d += (loc[i] - c.loc[i]) * (loc[i] - c.loc[i]);
		return Math.sqrt(d);
	}

	@Override
	public String toString() {
		return Arrays.toString(loc);
	}

	public static void main(String[] args) {
		Location c = new Location(32, 42.42, 0);
		System.out.println(c);
	}

	/**
	 * 
	 * @param cSrc
	 *            position will be returned if d is 0.0
	 * @param cDest
	 *            position will be returned if d is 1.0
	 * @param d
	 *            position percentage from cSrc to cDest in range [0, 1] (0.0 =
	 *            0%, 1.0 = 100%)
	 * @return new Coordinate at d% of traject from cSrc to cDest
	 */
	public static Location getMid(Location cSrc, Location cDest, double d) {
		if (d <= 0)
			return cSrc;
		if (d >= 1)
			return cDest;

		Location res = new Location(cSrc, ZERO);
		for (int i = DIM-1; i >= 0; --i)
			res.loc[i] = res.loc[i] * (1 - d) + cDest.loc[i] * d;

		return res;
	}

	public double get(int dim) {
		return loc[dim];
	}
}
