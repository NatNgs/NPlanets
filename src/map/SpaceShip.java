package map;

import java.util.HashSet;

import core.Coord;
import core.Troop;
import enums.CoefType;
import enums.Constants;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class SpaceShip {
	private Planet pSrc, pDest;
	private Coord cSrc, cCurt, cDest;
	private HashSet<Troop> crew;
	private double travelTime;
	private double speed;

	public SpaceShip(Planet source, Planet destination, HashSet<Troop> crew) {
		pSrc = source;
		pDest = destination;
		cSrc = source.getCoord();
		cDest = destination.getCoord();
		cCurt = cSrc;
		this.crew = crew;
		travelTime = 0;

		calcSpeed();
	}

	private void calcSpeed() {
		double totalSpeed = 0;
		int nbCrew = 0;
		for (Troop t : crew) {
			totalSpeed += t.getNbPeople()
					* Math.pow(2, t.getCoef(CoefType.Speed));
			nbCrew += t.getNbPeople();
		}
		if(nbCrew == 0)
			throw new RuntimeException(this.getClass()
					+ "::calcSpeed: Impossible to send vaisseau without crew.");
		speed = totalSpeed / nbCrew * Constants.VaisseauDefaultSpeed.getValue();
	}

	public Coord getCurrentSourceLocation() {
		return pSrc.getCoord();
	}
	public Coord getSourceLocation() {
		return cSrc;
	}
	public Coord getCurrentLocation() {
		return cCurt;
	}
	public Coord getDestinationLocation() {
		return cDest;
	}
	public Coord getCurrentDestinationLocation() {
		return pDest.getCoord();
	}

	public double getTotalTravelTime() {
		return cSrc.getDistance(cDest) / speed;
	}

	public double getCurentTravelTime() {
		return travelTime;
	}

	public HashSet<Troop> getTroupe() {
		return new HashSet<>(crew);
	}

	/**
	 * 
	 * @return 'true' if travel finished, 'false' otherwise
	 */
	public boolean increaseTravelTime() {
		if (travelTime >= getTotalTravelTime())
			return true;

		travelTime += speed;

		cCurt = Coord.getMidCoord(cSrc, cDest, travelTime
				/ getTotalTravelTime());

		if (travelTime >= getTotalTravelTime())
			return true;
		return false;
	}

}
