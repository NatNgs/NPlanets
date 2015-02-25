package core.map;

import java.util.*;

import core.*;
import core.enums.CoefType;
import core.enums.Constants;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Planet implements TurnDepending {
	private Player owner; // Can be Null
	private Coord coord; // Planet's location
	private double size;
	// When not in this map, value considered is "0" (usage: 2^coef)
	private HashMap<CoefType, Double> coefs;
	private HashSet<Troop> troops;


	// CONSTRUCTORS //
	public Planet(Coord c, double size) {
		coord = c;
		this.size = size;
		troops = new HashSet<>();
		coefs = new HashMap<>();
		owner = null;
	}


	// GETTERS and methods that don't modify objects //
	public double getCoef(CoefType type) {
		return coefs.get(type);
	}

	public HashMap<CoefType, Double> getAllCoefs() {
		return new HashMap<>(coefs);
	}

	public boolean tooNearFrom(Planet p) {
		return coord.getDistance(p.coord) > (size + p.size) * (size + p.size);
	}

	public Coord getCoord() {
		return coord;
	}

	public int getNbSoldiers() {
		int nb = 0;
		for (Troop t : troops)
			nb += t.getNbSoldiers();
		return nb;
	}

	public Player getOwner() {
		return owner;
	}

	// SETTERS and methods that modify objects //
	public void addTroops(Collection<Troop> troops) {
		this.troops.addAll(troops);
	}
	public void setCoef(CoefType cType, double value) {
		coefs.put(cType, value);
	}

	private void generateMoreTroupes() {
		if (!coefs.containsKey(CoefType.GenTroupesSpeed))
			setCoef(CoefType.GenTroupesSpeed, 0);

		troops.add(new Troop(this,
				(int) (Constants.PlanetDefaultGenTroupesSpeed.getValue() * Math
						.pow(2, coefs.get(CoefType.GenTroupesSpeed)))));
	}

	private void adaptAllTroupes() {
		for (Troop t : troops)
			t.adaptCaracts(this);
	}

	private void cleanTroops() {
		ArrayList<Troop> alT = new ArrayList<>(troops);
		for (int i = 0; i < alT.size() - 1; i++) {
			Troop t1 = alT.get(i);
			if (t1.getNbSoldiers() == 0) {
				troops.remove(t1);
				continue;
			}

			for (int j = i + 1; j < alT.size(); j++) {
				Troop t2 = alT.get(j);

				t1.merge(t2); // Merge if possible, do nothing otherwise

				if (t2.getNbSoldiers() == 0) {
					troops.remove(t2);
					alT.remove(t2);
					j--;
					continue;
				}
			}
		}
	}

	public void setOwner(Player p) {
		owner = p;

	}

	// GAME methods //
	public SpaceShip sendSpaceship(Planet destination, int nbCrew) {
		if (destination.equals(this))
			throw new IllegalArgumentException(this.getClass()+"::sendSpaceship: Destination can't be source.");

		HashSet<Troop> travelingTroops = new HashSet<>();
		int howManySoldiers = 0;
		for (Troop t : new HashSet<>(troops))
			if(howManySoldiers + t.getNbSoldiers() > nbCrew) {
				travelingTroops.add(t.split(nbCrew-howManySoldiers));
				howManySoldiers = nbCrew;
				break;
			}
			else {
				travelingTroops.add(t);
				troops.remove(t);
				howManySoldiers+=t.getNbSoldiers();
				if(howManySoldiers == nbCrew)
					break;
			}
		// Impossible to gather as soldiers as needed
		if (howManySoldiers < nbCrew) {
			troops.addAll(travelingTroops);
			throw new IllegalArgumentException(this.getClass()
					+ "::sendSpaceship: Not enough soldiers on the planet.");
		}

		return new SpaceShip(this, destination, travelingTroops);
	}
	public boolean sendInterceptor(SpaceShip v, int nbCrew) {
		// Not yet implemented
		throw new RuntimeException(this.getClass()
				+ "::sendInterceptor not yet implemented.");
	}

	public void recieveSpaceship(SpaceShip s) {
		if (s.getOwner() == getOwner())
			addTroops(s.getTroops());
		else {
			BattleReport br = new BattleReport(s, this);
			s.getOwner().addReport(br);
			if (owner != null)
				owner.addReport(br);
		}
	}

	// OVERRIDE //
	@Override
	public void update(int nbTurnToSpend) {
		while (nbTurnToSpend > 0) {
			adaptAllTroupes();
			generateMoreTroupes();
			cleanTroops();
			System.gc();
			nbTurnToSpend--;
		}
	}

	@Override
	public String toString() {
		return "Planet: " + troops;
	}
}
