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

	public Vaisseau(Planet source, Planet destination, Troupe t) {
		pSrc = source;
		pDest = destination;
		cSrc = source.getCoord();
		cDest = destination.getCoord();
		cCurt = cSrc;
		this.t = t;
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
		return cSrc.getDistance(cDest)
				/ (Constants.VaisseauDefaultSpeed.getValue() * Math.pow(2,
						t.getCoef(CoefType.Speed)));
	}

}
