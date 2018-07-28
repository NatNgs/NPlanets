package natngs.nplanets.server.movements.repetitive;

import natngs.nplanets.common.Location;
import natngs.nplanets.common.Vector;
import natngs.nplanets.server.ILocated;
import natngs.nplanets.server.movements.ARelativeMovement;

import java.util.ArrayList;
import java.util.List;

public class PolygonalMove extends ARelativeMovement {
	private final List<Location> points;
	private final double orbitDuration;

	public PolygonalMove(ILocated ref, List<Location> points, double orbitDuration) {
		super(ref);
		if (points.size() < 2)
			throw new RuntimeException("Need at least 2 points to make a PolygonalPlanet");
		this.points = new ArrayList<>(points);
		this.orbitDuration = orbitDuration;
	}

	@Override
	protected Location getRelativeLocation(double when) {
		when -= (int)(when / orbitDuration) * orbitDuration; // modulo orbitDuration
		when /= orbitDuration; // 0 to 1
		double pct = points.size() * when;
		int step = (int)pct; // get the current segment origin

		pct = pct - step; // percent of the current segment where the planet is

		Location step1 = points.get(step);
		Location step2 = points.get((step + 1) % points.size());

		double[] loc = new double[Vector.DIM];
		for (int i = Vector.DIM - 1; i >= 0; --i) {
			loc[i] = step1.get(i) * (1 - pct) + step2.get(i) * pct;
		}

		return new Location(loc);
	}
}
