package map;

import core.Coord;
import core.Troupe;
import enums.CoefType;
import enums.Constants;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Vaisseau {
	private Planet pSrc, pDest;
	private Coord cSrc, cCurt, cDest;
	private Troupe t;
	private double travelTime;
	private double speed;

	public Vaisseau(Planet source, Planet destination, Troupe t) {
		pSrc = source;
		pDest = destination;
		cSrc = source.getCoord();
		cDest = destination.getCoord();
		cCurt = cSrc;
		this.t = t;
		travelTime = 0;
		speed = Constants.VaisseauDefaultSpeed.getValue()
				* Math.pow(2, t.getCoef(CoefType.Speed));
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

	public Troupe getTroupe() {
		return t;
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
