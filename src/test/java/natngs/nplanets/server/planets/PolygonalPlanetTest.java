package natngs.nplanets.server.planets;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ARelativeLocated;
import natngs.nplanets.server.Universe;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class PolygonalPlanetTest {
	private Universe u;

	@Before
	public void before() {
		u = new Universe();
	}

	@Test
	public void relativeToUniverse() {
		List<Location> polygon = new ArrayList<>();
		polygon.add(new Location(1, -1, -1));
		polygon.add(new Location(1, 1, -1));
		polygon.add(new Location(-1, 1, -1));
		polygon.add(new Location(-1, 1, 1));
		polygon.add(new Location(-1, -1, 1));
		polygon.add(new Location(1, -1, 1));
		double orbitDuration = 6;

		ARelativeLocated p = new PolygonalPlanet(u, polygon, orbitDuration);

		assertEquals(new Location(1, -1, -1), p.getLocation(0));
		assertEquals(new Location(1, -0.8, -1), p.getLocation(0.1));
		assertEquals(new Location(1, 0, -1), p.getLocation(0.5));
		assertEquals(new Location(1, 0.8, -1), p.getLocation(0.9));
		assertEquals(new Location(1, 1, -1), p.getLocation(1));
		assertEquals(new Location(0, 1, -1), p.getLocation(1.5));
		assertEquals(new Location(-1, 1, -1), p.getLocation(2));
		assertEquals(new Location(-1, 1, 0), p.getLocation(2.5));
		assertEquals(new Location(-1, 1, 1), p.getLocation(3));
		assertEquals(new Location(-1, -1, 1), p.getLocation(4));
		assertEquals(new Location(1, -1, 1), p.getLocation(5));
		assertEquals(new Location(1, -1, -1), p.getLocation(6));
		assertEquals(new Location(1, 1, -1), p.getLocation(7));
		assertEquals(new Location(-1, 1, -1), p.getLocation(8));
		assertEquals(new Location(1, -1, -1), p.getLocation(orbitDuration*42));
	}

	@Test
	public void relativeToAnotherPlanet() {
		List<Location> polygon1 = new ArrayList<>();
		polygon1.add(new Location(1, 0, 0));
		polygon1.add(new Location(0,1,0));
		polygon1.add(new Location(0,0,1));
		double orbit1Duration = 12;

		List<Location> polygon2 = new ArrayList<>();
		polygon2.add(new Location(-1, 0, 0));
		polygon2.add(new Location(0, -1, 0));
		polygon2.add(new Location(0, 0, -1));
		double orbit2Duration = 6;

		ARelativeLocated p1 = new PolygonalPlanet(u, polygon1, orbit1Duration);
		ARelativeLocated p2 = new PolygonalPlanet(p1, polygon2, orbit2Duration);

		assertEquals(new Location(0, 0, 0), p2.getLocation(0)); // st 0/0
		assertEquals(new Location(0.25, -0.25, 0), p2.getLocation(1));
		assertEquals(new Location(0.5, -0.5, 0), p2.getLocation(2)); // st x/1
		assertEquals(new Location(0.25, 0.25, -0.5), p2.getLocation(3));
		assertEquals(new Location(0, 1, -1), p2.getLocation(4)); // st 1/2
		assertEquals(new Location(-0.5, 0.75, -0.25), p2.getLocation(5));
		assertEquals(new Location(-1, 0.5, 0.5), p2.getLocation(6)); // st x/0
		assertEquals(new Location(-0.5, -0.25, 0.75), p2.getLocation(7));
		assertEquals(new Location(0, -1, 1), p2.getLocation(8)); // st 2/1
		assertEquals(new Location(0.25, -0.5, 0.25), p2.getLocation(9));
		assertEquals(new Location(0.5, 0, -0.5), p2.getLocation(10)); // st x/2
		assertEquals(new Location(0.25, 0, -0.25), p2.getLocation(11));
		assertEquals(new Location(0, 0, 0), p2.getLocation(12)); // st 0/0
	}
}
