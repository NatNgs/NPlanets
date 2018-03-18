package natngs.nplanets.server.ships;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.Universe;
import natngs.nplanets.server.planets.PolygonalPlanet;
import natngs.nplanets.server.planets.StaticPlanet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

		assertEquals(p1.getLocation(launch), s.getLocation(launch));
		assertNotEquals(p1.getLocation(launch + 1), s.getLocation(launch + 1));
		assertNotEquals(p2.getLocation(arrival - 1), s.getLocation(arrival - 1));
		assertEquals(p2.getLocation(arrival), s.getLocation(arrival));

		double time = launch * 0.5 + arrival * 0.5;
		assertEquals(Location.getMid(p1.getLocation(time), p2.getLocation(time), 0.5), s.getLocation(time));

		time = launch * 0.11 + arrival * 0.89;
		assertEquals(Location.getMid(p1.getLocation(time), p2.getLocation(time), 0.89), s.getLocation(time));

		time = launch * 0.91 + arrival * 0.09;
		assertEquals(Location.getMid(p1.getLocation(time), p2.getLocation(time), 0.09), s.getLocation(time));
	}


	@Test
	public void staticOriginMovingDestination() {
		StaticPlanet p1 = new StaticPlanet(u, new Location(-100, 0, 0));
		List<Location> l2 = new ArrayList<>();
		l2.add(new Location(100, 100, 0));
		l2.add(new Location(100, -100, 0));
		PolygonalPlanet p2 = new PolygonalPlanet(u, l2, 400); // Moving 1 each second
		double launch = 50;
		double speed = 4;
		double arrival = launch + 50;
		Ship s = Ship.buildShip(p1, p2, launch, speed);

		assertEquals(p1.getLocation(launch), s.getLocation(launch));
		assertNotEquals(p1.getLocation(launch+1), s.getLocation(launch+1));
		assertNotEquals(p2.getLocation(arrival-1), s.getLocation(arrival-1));
		assertEquals(p2.getLocation(arrival), s.getLocation(arrival));
		assertEquals(new Location(0,0,0), s.getLocation((launch + arrival)/2));
		assertEquals(new Location(78,0,0), s.getLocation(launch * 0.11 + arrival * 0.89));
		assertEquals(new Location (-82, 0, 0), s.getLocation(launch * 0.91 + arrival * 0.09));
	}


	@Test
	public void movingOriginStaticDestination() {

	}


	@Test
	public void movingOriginMovingDestination() {

	}
}
