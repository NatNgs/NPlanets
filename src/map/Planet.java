package map;

import java.util.*;

import core.Coord;
import core.Troupe;
import enums.CoefType;
import enums.Constants;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Planet {
	private Coord coord; // Planet's location
	private double size;
	// When not in this map, value considered is "0" (usage: 2^coef)
	private HashMap<CoefType, Double> coefs;
	private HashSet<Troupe> troupes;


	// CONSTRUCTORS //
	public Planet(Coord c, double size) {
		coord = c;
		this.size = size;
		troupes = new HashSet<>();
		coefs = new HashMap<>();
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

	// SETTERS and methods that modify objects //
	public void setCoef(CoefType cType, double value) {
		coefs.put(cType, value);
	}

	public void generateMoreTroupes() {
		if (!coefs.containsKey(CoefType.GenTroupesSpeed))
			setCoef(CoefType.GenTroupesSpeed, 0);

		troupes.add(new Troupe(this,
				(int) (Constants.PlanetDefaultGenTroupesSpeed.getValue() * Math
						.pow(2, coefs.get(CoefType.GenTroupesSpeed)))));
		cleanTroupes();
	}

	public void adaptAllTroupes() {
		for (Troupe t : troupes)
			t.adaptCaracts(this);
	}

	private void cleanTroupes() {
		ArrayList<Troupe> alT = new ArrayList<>(troupes);
		for (int i = 0; i < alT.size() - 1; i++) {
			Troupe t1 = alT.get(i);
			if (t1.getNbPeople() == 0) {
				troupes.remove(t1);
				continue;
			}

			for (int j = i + 1; j < alT.size(); j++) {
				Troupe t2 = alT.get(j);

				t1.merge(t2); // Merge if possible, do nothing otherwise

				if (t2.getNbPeople() == 0) {
					troupes.remove(t2);
					alT.remove(t2);
					j--;
					continue;
				}
			}
		}
	}


	// OVERRIDE //
	@Override
	public String toString() {
		return "Planet: " + troupes;
	}
}
