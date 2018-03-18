package natngs.nplanets.server.ships;

import natngs.nplanets.common.Location;
import natngs.nplanets.common.Vector;
import natngs.nplanets.server.ARelativeLocated;
import natngs.nplanets.server.ILocated;
import natngs.nplanets.server.Universe;

public class Ship implements ILocated {
	private static final double TIME_PRECISION = 0.01;
	private static final int FIND_PATH_LOOPS = 1000;
	private final Location origin;
	private final Location destination;
	private final double launchTime;
	private final double arrivalTime;

	private Ship(Location origin, Location destination, double launchTime, double arrivalTime) {
		System.out.println("Building ship launching at: " + launchTime+", arriving at: "+ arrivalTime);
		this.origin = origin;
		this.destination = destination;
		this.launchTime = launchTime;
		this.arrivalTime = arrivalTime;
	}

	public static Ship buildShip(ARelativeLocated origin, ARelativeLocated destination, double launchTime, double speed) {
		// compute launching speed
		Vector inertialEnergy = new Vector(
				origin.getLocation(launchTime + 0.5),
				new Vector(origin.getLocation(launchTime-0.5), -1)
		);
		System.out.println(inertialEnergy);

		Location initialLocation = origin.getLocation(launchTime);
		Vector reverseInitialLoc = new Vector(initialLocation, -1);

		double arrival;
		double duration = 0;
		Location arrivalLocation;

		int loops = FIND_PATH_LOOPS;
		do {
			arrival = launchTime + duration;
			arrivalLocation = destination.getLocation(arrival);
			Vector shipVector = new Vector(arrivalLocation, reverseInitialLoc, new Vector(inertialEnergy, -duration));

			double distance = shipVector.getLength();
			duration = distance / speed;
			System.out.println("Computing duration " + loops + ": " + arrival+", "+duration);
			loops--;
		} while (loops > 0 && Math.abs(launchTime - arrival + duration) > TIME_PRECISION);

		return new Ship(initialLocation, arrivalLocation, launchTime, arrival);
	}

	@Override
	public Location getLocation(double when) {
		if (when <= launchTime) {
			return origin;
		} else if (when >= arrivalTime) {
			return destination;
		}
		return Location.getMid(origin, destination, (when - launchTime) / (arrivalTime - launchTime));
	}
}
