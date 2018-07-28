package natngs.nplanets.server.movements.repetitive;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.Universe;
import natngs.nplanets.server.located.Planet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StaticRelativeTest {
	private Universe u;

	@Before
	public void before() {
		u = new Universe();
	}

	@Test
	public void relativeToUniverse() {
		Location relativeLocation = new Location(12, 23);
		StaticRelative mp = new StaticRelative(u, relativeLocation);

		Location atStart = mp.getLocation(0);
		Location atMomentA = mp.getLocation(42);
		Location atMomentB = mp.getLocation(42.3);

		assertEquals(relativeLocation, atStart);
		assertEquals(relativeLocation, atMomentA);
		assertEquals(relativeLocation, atMomentB);
	}

	@Test
	public void relativeToAnotherPlanet() {
		Location relativeLoc1 = new Location(12, 23);
		Location relativeLoc2 = new Location(45, 56);
		StaticRelative mp1 = new StaticRelative(u, relativeLoc1);
		StaticRelative mp2 = new StaticRelative(new Planet(mp1), relativeLoc2);

		Location atStart = mp2.getLocation(0);
		Location atMomentA = mp2.getLocation(42);
		Location atMomentB = mp2.getLocation(42.3);

		Location absoluteLocation = new Location(12 + 45, 23 + 56);
		assertEquals(absoluteLocation, atStart);
		assertEquals(absoluteLocation, atMomentA);
		assertEquals(absoluteLocation, atMomentB);
	}
}
