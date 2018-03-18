package natngs.nplanets.common;

import java.util.Arrays;

public class Vector {
	public static final double DELTA = 0.004;
	protected static final int DIM = 3;
	public static final Vector ZERO = new Vector(0, 0, 0);
	protected final double[] loc;

	public Vector(Vector c, double tx) {
		loc = new double[DIM];
		for (int i = DIM - 1; i >= 0; --i) {
			loc[i] = c.loc[i] * tx;
		}
	}

	public Vector(Vector... vs) {
		loc = new double[DIM];
		for (int i = DIM - 1; i >= 0; --i)
			for (Vector v : vs)
				loc[i] += v.loc[i];
	}

	public Vector(double... xyz) {
		if (xyz.length != DIM)
			throw new IllegalArgumentException(
					this.getClass() + "::new: Wrong dimension numbers: " + xyz.length + " instead of " + DIM);
		loc = xyz;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector)
			return getDistance((Vector)obj) < DELTA;
		else if (obj instanceof double[] && ((double[])obj).length == DIM)
			return getDistance(new Vector((double[])obj)) < DELTA;
		else
			return super.equals(obj);
	}

	public double getDistance(Vector c) {
		double d = 0;
		for (int i = DIM - 1; i >= 0; --i)
			d += (loc[i] - c.loc[i]) * (loc[i] - c.loc[i]);
		return Math.sqrt(d);
	}

	public double getLength() {
		double d = 0;
		for (int i = DIM - 1; i >= 0; --i)
			d += loc[i] * loc[i];
		return Math.sqrt(d);
	}

	@Override
	public String toString() {
		return Arrays.toString(loc);
	}

	public double get(int dim) {
		return loc[dim];
	}
}