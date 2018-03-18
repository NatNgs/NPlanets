package natngs.nplanets.server.ships;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.Universe;
import natngs.nplanets.server.planets.StaticPlanet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShipTest {
	private Universe u;

	@Before
	public void before() {
		u = new Universe();
	}

	@Test
	public void staticOriginStaticDestination() {
		StaticPlanet p1 = new StaticPlanet(u, new Location(-100,42,69));
		StaticPlanet p2 = new StaticPlanet(u, new Location(100,42,69));
		double launch = 69;
		double speed = 50;
		double arrival = launch+4;
		Ship s = Ship.buildShip(p1, p2, launch, speed);

		assertEquals(p1.getLocation(0), s.getLocation(0));
		assertEquals(p1.getLocation(69), s.getLocation(69));
		assertEquals(p2.getLocation(arrival), s.getLocation(arrival));
		assertEquals(p2.getLocation(arrival+200), s.getLocation(arrival + 200));

		double time = 69 * 0.5 + arrival * 0.5;
		assertEquals(Location.getMid(p1.getLocation(time), p2.getLocation(time), 0.5), s.getLocation(time));

		time = 69 * 0.1 + arrival * 0.9;
		assertEquals(Location.getMid(p1.getLocation(time), p2.getLocation(time), 0.9), s.getLocation(time));

		time = 69 * 0.9 + arrival * 0.1;
		assertEquals(Location.getMid(p1.getLocation(time), p2.getLocation(time), 0.1), s.getLocation(time));

	}


	@Test
	public void staticOriginMovingDestination() {

	}


	@Test
	public void movingOriginStaticDestination() {

	}


	@Test
	public void movingOriginMovingDestination() {

	}
}
