package core.map;

import java.util.HashSet;

import core.*;
import core.enums.CoefType;
import core.enums.Constants;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class SpaceShip implements TurnDepending {
	private Player owner;
	private Planet pSrc, pDest;
	private Coord cSrc, cCurt, cDest;
	private HashSet<Troop> crew;
	private double travelTime;
	private double speed;

	// CONSTRUCTORS //
	public SpaceShip(Planet source, Planet destination, HashSet<Troop> crew) {
		owner = source.getOwner();
		if(owner == null)
			throw new IllegalArgumentException(
					this.getClass()
					+ "::new: Impossible to send Spaceship from a non player planet.");
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
			totalSpeed += t.getNbSoldiers()
					* Math.pow(2, t.getCoef(CoefType.Speed));
			nbCrew += t.getNbSoldiers();
		}
		if(nbCrew == 0)
			throw new RuntimeException(this.getClass()
					+ "::calcSpeed: Impossible to send vaisseau without crew.");
		if (totalSpeed == 0)
			throw new RuntimeException(this.getClass()
					+ "::calcSpeed: Impossible to send vaisseau with 0 speed.");
		speed = totalSpeed / nbCrew * Constants.VaisseauDefaultSpeed.getValue();
	}

	// GETTERS and methods that don't modify objects //
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

	public HashSet<Troop> getTroops() {
		return new HashSet<>(crew);
	}

	public boolean hasFinished() {
		return cCurt == cDest;
	}

	public Player getOwner() {
		return owner;
	}

	public int getNbSoldiers() {
		int nb = 0;
		for (Troop t : crew)
			nb += t.getNbSoldiers();
		return nb;
	}

	// SETTERS and methods that modify objects //
	/**
	 * 
	 * @return 'true' if travel finished, 'false' otherwise
	 */
	public boolean increaseTravelTime(int nbMore) {
		travelTime += speed * nbMore;

		cCurt = Coord.getMidCoord(cSrc, cDest, travelTime
				/ getTotalTravelTime());

		if (travelTime >= getTotalTravelTime())
			return true;
		return false;
	}

	// OVERRIDE methods //
	@Override
	public void update(int nbTurnToSpend) {
		if (increaseTravelTime(nbTurnToSpend))
			pDest.recieveSpaceship(this);
	}

}
