package natngs.nplanets.server.ships;

import natngs.nplanets.common.Location;
import natngs.nplanets.common.Vector;
import natngs.nplanets.server.ILocated;

public class Ship implements ILocated {
	private static final double TIME_PRECISION = 0.0001;
	private static final int FIND_PATH_LOOPS = 1000;
	private final Location origin;
	private final Location destination;
	private final double launchTime;
	private final double arrivalTime;

	private Ship(Location origin, Location destination, double launchTime, double arrivalTime) {
		this.origin = origin;
		this.destination = destination;
		this.launchTime = launchTime;
		this.arrivalTime = arrivalTime;
	}

	public static Ship buildShip(ILocated origin, ILocated destination, double launchTime, double speed) {
		// compute launching speed
		Vector inertialEnergy = new Vector(
				origin.getLocation(launchTime + 0.5),
				new Vector(origin.getLocation(launchTime-0.5), -1)
		);

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
