package core;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Coord {
	public final static Coord CENTER = new Coord(new double[] { 0, 0, 0 });
	private final double[] coord;


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
}
