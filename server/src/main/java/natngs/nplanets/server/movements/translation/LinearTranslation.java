package natngs.nplanets.server.movements.translation;

import natngs.nplanets.common.Location;
import natngs.nplanets.common.Vector;
import natngs.nplanets.server.ILocated;
import natngs.nplanets.server.located.Ship;
import natngs.nplanets.server.movements.IMovement;

import java.util.ArrayList;
import java.util.List;

/**
 * Before departure time, movement is similar to StaticRelative of first step.
 * After that, object will move at constant speed to next points
 * After arrived at last step, movement is similar to StaticRelative of last step.
 */
public class LinearTranslation implements IMovement {
	private static final double TIME_PRECISION = 0.0001;
	private static final int FIND_PATH_LOOPS = 1000;
	private final List<ILocated> steps;
	private final List<SegmentInfo> segments = new ArrayList<>(); // one size less than 'steps'
	private final double speed;

	public LinearTranslation(List<ILocated> steps, double speed, double departureTime) throws Exception {
		if (steps.size() < 2) { // DEBUG
			throw new RuntimeException("Need at least 2 steps (Start and last locations)");
		}
		this.steps = steps;
		this.speed = speed;

		// build translation flight with 1 turn pause on each step
		for (int i = 0; i < steps.size() - 1; i++) {
			departureTime = buildSegment(i, departureTime); // next departure time
		}
	}

	private int countSegments() {
		return steps.size() - 1;
	}

	private double buildSegment(int segmentNumber, double departureTime) throws Exception {
		if (segmentNumber < 0 || segmentNumber >= countSegments()) { // DEBUG
			throw new RuntimeException("SegmentNumber cannot be " + segmentNumber + " in a " + countSegments() + " segments trip");
		}
		if (segmentNumber > segments.size()) { // DEBUG
			throw new RuntimeException("Cannot compute segment " + segmentNumber + " because segment " + (segments.size() - 1) + " is not yet built");
		}
		// clear next segments
		while (segments.size() > segmentNumber) {
			segments.remove(segments.size() - 1);
		}
		if (!segments.isEmpty() && segments.get(segments.size() - 1).arrivalTime > departureTime) { // DEBUG
			throw new RuntimeException("Segment " + segmentNumber + " cannot start before (" + departureTime + ") segment " + (segmentNumber - 1) + " ends (" + segments.get(segments.size() - 1) + ")");
		}

		ILocated origin = steps.get(segmentNumber);
		ILocated destination = steps.get(segmentNumber + 1);

		// compute launching speed
		Vector inertialEnergy = new Vector(
				origin.getLocation(departureTime + 0.5),
				new Vector(origin.getLocation(departureTime - 0.5), -1)
		);

		Location initialLocation = origin.getLocation(departureTime);
		Vector reverseInitialLoc = new Vector(initialLocation, -1);

		double arrival;
		double duration = 0;
		Location arrivalLocation;

		int loops = FIND_PATH_LOOPS;
		do {
			arrival = departureTime + duration;
			arrivalLocation = destination.getLocation(arrival);
			Vector shipVector = new Vector(arrivalLocation, reverseInitialLoc, new Vector(inertialEnergy, -duration));

			double distance = shipVector.getLength();
			duration = distance / speed;
			if (loops-- <= 0)
				throw new Exception("Difficulties to build LinearTranslation path");
		} while (Math.abs(departureTime - arrival + duration) > TIME_PRECISION);


		SegmentInfo segment = new SegmentInfo(departureTime, arrival, origin.getLocation(departureTime), destination.getLocation(arrival));
		segments.add(segment);

		return segment.arrivalTime;
	}


	@Override
	public Location getLocation(double when) {
		// check if before or after flight
		if (when <= segments.get(0).departureTime) {
			return steps.get(0).getLocation(when);
		} else if (when >= segments.get(segments.size() - 1).arrivalTime) {
			return steps.get(steps.size() - 1).getLocation(when);
		}

		// If in flight
		for (SegmentInfo seg : segments) {
			if (when > seg.departureTime && when <= seg.arrivalTime) {
				return Location.getMid(seg.departureLocation, seg.arrivalLocation, (when - seg.departureTime) / (seg.arrivalTime - seg.departureTime));
			}
		}

		throw new RuntimeException("This code should not been reachable - Problem in Movement\n"+ when + this.toString());
	}


	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (SegmentInfo si : segments) {
			str.append("\n> ").append(si.toString());
		}
		return str.toString();
	}

	private class SegmentInfo {
		private double departureTime;
		private double arrivalTime;
		private Location departureLocation;
		private Location arrivalLocation;

		private SegmentInfo(double departureTime, double arrivalTime, Location departureLocation, Location arrivalLocation) {
			this.departureTime = departureTime;
			this.arrivalTime = arrivalTime;
			this.departureLocation = departureLocation;
			this.arrivalLocation = arrivalLocation;
		}

		@Override
		public String toString() {
			return "from " + departureLocation + " (" + departureTime + ") to " + arrivalLocation + " (" + arrivalTime + ")";
		}
	}}
