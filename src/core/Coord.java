package core;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Coord {
	public final static Coord CENTER = new Coord(new double[] { 0, 0, 0 });
	private final double[] coord;

	public Coord(Coord c) {
		coord = new double[c.coord.length];
		for (int i = 0; i < c.coord.length; i++)
			coord[i] = c.coord[i];
	}
	public Coord(double[] xyz) {
		if(xyz.length != 3)
			throw new RuntimeException(
					"Impossible to make a new coord with other than 3 dimentions.");
		coord = xyz;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coord)
			return getDistance((Coord)obj) == 0;
		if (obj instanceof double[] && ((double[]) obj).length == 3)
			return getDistance(new Coord((double[]) obj)) == 0;
		else
			return super.equals(obj);
	}

	public double getDistance(Coord c) {
		double d = 0;
		for (int i = 0; i < coord.length; i++)
			d += (coord[i] - c.coord[i]) * (coord[i] - c.coord[i]);
		return Math.sqrt(d);
	}

	@Override
	public String toString() {
		return coord.toString();
	}

	public static void main(String[] args) {
		Coord c = new Coord(new double[] { 32, 42.42, 0 });
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
	public static Coord getMidCoord(Coord cSrc, Coord cDest, double d) {
		if (d <= 0)
			return cSrc;
		if (d >= 1)
			return cDest;

		Coord res = new Coord(cSrc);
		for (int i = 0; i < cDest.coord.length; i++)
			res.coord[i] = res.coord[i] * (1 - d) + cDest.coord[i] * d;

		return res;
	}
}
