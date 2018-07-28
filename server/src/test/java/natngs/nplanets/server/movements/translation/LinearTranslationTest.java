package natngs.nplanets.server.movements.translation;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ILocated;
import natngs.nplanets.server.Universe;
import natngs.nplanets.server.located.Planet;
import natngs.nplanets.server.movements.repetitive.PolygonalMove;
import natngs.nplanets.server.movements.repetitive.StaticRelative;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LinearTranslationTest {
	private Universe u;

	@Before
	public void before() {
		u = new Universe();
	}

	@Test
	public void staticOriginStaticDestination() throws Exception {
		Planet p1 = new Planet(new StaticRelative(u, new Location(-100, 42)));
		Planet p2 = new Planet(new StaticRelative(u, new Location(100, 42)));
		double launch = 69;
		double speed = 50;
		double arrival = launch + 4;

		List<ILocated> steps = new ArrayList<>();
		steps.add(p1);
		steps.add(p2);
		LinearTranslation lt = new LinearTranslation(steps, speed, launch);

		assertEquals(p1.getLocation(launch), lt.getLocation(launch));
		assertNotEquals(p1.getLocation(launch + 1), lt.getLocation(launch + 1));
		assertNotEquals(p2.getLocation(arrival - 1), lt.getLocation(arrival - 1));
		assertEquals(p2.getLocation(arrival), lt.getLocation(arrival));

		double time = launch * 0.5 + arrival * 0.5;
		assertEquals(Location.getMid(p1.getLocation(time), p2.getLocation(time), 0.5), lt.getLocation(time));

		time = launch * 0.11 + arrival * 0.89;
		assertEquals(Location.getMid(p1.getLocation(time), p2.getLocation(time), 0.89), lt.getLocation(time));

		time = launch * 0.91 + arrival * 0.09;
		assertEquals(Location.getMid(p1.getLocation(time), p2.getLocation(time), 0.09), lt.getLocation(time));
	}


	@Test
	public void staticOriginMovingDestination() throws Exception {
		Planet p1 = new Planet(new StaticRelative(u, new Location(-100, 0)));
		List<Location> l2 = new ArrayList<>();
		l2.add(new Location(100, 100));
		l2.add(new Location(100, -100));
		Planet p2 = new Planet(new PolygonalMove(u, l2, 400)); // Moving 1 each second
		double launch = 50;
		double speed = 4;
		double arrival = launch + 50;
		List<ILocated> steps = new ArrayList<>();
		steps.add(p1);
		steps.add(p2);
		LinearTranslation lt = new LinearTranslation(steps, speed, launch);

		assertEquals(p1.getLocation(launch), lt.getLocation(launch));
		assertNotEquals(p1.getLocation(launch + 1), lt.getLocation(launch + 1));
		assertNotEquals(p2.getLocation(arrival - 1), lt.getLocation(arrival - 1));
		assertEquals(p2.getLocation(arrival), lt.getLocation(arrival));
		assertEquals(new Location(0, 0), lt.getLocation((launch + arrival) / 2));
		assertEquals(new Location(78, 0), lt.getLocation(launch * 0.11 + arrival * 0.89));
		assertEquals(new Location(-82, 0), lt.getLocation(launch * 0.91 + arrival * 0.09));
	}


	@Test
	public void movingOriginStaticDestination() throws Exception {
		List<Location> l1 = new ArrayList<>();
		l1.add(new Location(100, 100));
		l1.add(new Location(100, -100));
		Planet p1 = new Planet(new PolygonalMove(u, l1, 400)); // Moving 1 each second
		Planet p2 = new Planet(new StaticRelative(u, new Location(-100, 0)));
		double launch = 50;
		double speed = 4;
		double arrival = launch + 50;
		List<ILocated> steps = new ArrayList<>();
		steps.add(p1);
		steps.add(p2);
		LinearTranslation lt = new LinearTranslation(steps, speed, launch);

		assertEquals(p1.getLocation(launch), lt.getLocation(launch));
		assertNotEquals(p1.getLocation(launch + 1), lt.getLocation(launch + 1));
		assertNotEquals(p2.getLocation(arrival - 1), lt.getLocation(arrival - 1));
		assertEquals(p2.getLocation(arrival), lt.getLocation(arrival));
		assertEquals(new Location(0, 25), lt.getLocation((launch + arrival) / 2));
		assertEquals(new Location(-78, 5.496), lt.getLocation(launch * 0.11 + arrival * 0.89));
		assertEquals(new Location(82, 45.5), lt.getLocation(launch * 0.91 + arrival * 0.09));
	}


	@Test
	public void movingOriginMovingDestination() throws Exception {
		List<Location> l1 = new ArrayList<>();
		l1.add(new Location(-100, -200));
		l1.add(new Location(-100, 0));
		Planet p1 = new Planet(new PolygonalMove(u, l1, 80)); // Moving 5 each second

		List<Location> l2 = new ArrayList<>();
		l2.add(new Location(100, 130));
		l2.add(new Location(100, -20));
		Planet p2 = new Planet(new PolygonalMove(u, l2, 66.667)); // Moving 4.5 each second

		double launch = 20;
		double speed = 5;
		double arrival = launch + 40;
		List<ILocated> steps = new ArrayList<>();
		steps.add(p1);
		steps.add(p2);
		LinearTranslation lt = new LinearTranslation(steps, speed, launch);

		assertEquals(new Location(-100, -100), p1.getLocation(launch));
		assertEquals(new Location(100, 100), p2.getLocation(arrival));

		assertEquals(p1.getLocation(launch), lt.getLocation(launch));
		assertNotEquals(p1.getLocation(launch + 1), lt.getLocation(launch + 1));
		assertNotEquals(p2.getLocation(arrival - 1), lt.getLocation(arrival - 1));
		assertEquals(p2.getLocation(arrival), lt.getLocation(arrival));
		assertEquals(new Location(0, 0), lt.getLocation((launch + arrival) / 2));
		assertEquals(new Location(78, 78), lt.getLocation(launch * 0.11 + arrival * 0.89));
		assertEquals(new Location(-82, -82), lt.getLocation(launch * 0.91 + arrival * 0.09));
	}
}
