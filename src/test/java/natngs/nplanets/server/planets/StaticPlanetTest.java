package natngs.nplanets.server.planets;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.Universe;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StaticPlanetTest {

	@Test
	public void relativeToUniverse() {
		Location relativeLocation = new Location(12, 23, 34);
		Universe u = new Universe();
		StaticPlanet mp = new StaticPlanet(u, relativeLocation);

		Location atStart = mp.getLocation(0);
		Location atMomentA = mp.getLocation(42);
		Location atMomentB = mp.getLocation(42.3);

		assertEquals(relativeLocation, atStart);
		assertEquals(relativeLocation, atMomentA);
		assertEquals(relativeLocation, atMomentB);
	}
}
