package natngs.nplanets.server.located.planets;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ARelativeLocated;
import natngs.nplanets.server.ILocated;

import java.util.ArrayList;
import java.util.List;

public class PolygonalPlanet extends ARelativeLocated {
	private final List<Location> points;
	private final double orbitDuration;

	public PolygonalPlanet(ILocated ref, List<Location> points, double orbitDuration) {
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

		return new Location(
				step1.get(0) * (1 - pct) + step2.get(0) * pct,
				step1.get(1) * (1 - pct) + step2.get(1) * pct,
				step1.get(2) * (1 - pct) + step2.get(2) * pct);
	}
}
