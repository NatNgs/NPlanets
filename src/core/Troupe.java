package core;

import java.util.HashMap;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Troupe {
	private int nbPeople;
	private Planet source;

	// When not in this map, value considered is "0" (usage: 2^coef)
	private HashMap<CoefType, Double> coefs;

	public Troupe(Planet source, int nbPeople) {
		this.source = source;
		this.nbPeople = nbPeople;
		coefs = source.getAllCoefs();
	}

	public int getNbPeople() {
		return nbPeople;
	}

	public boolean merge(Troupe t2) {
		if (source != t2.source)
			return false;

		for(CoefType cType : CoefType.values()) {
			Double dc = coefs.get(cType);
			double val1 = dc == null?0:dc;

			dc = t2.coefs.get(cType);
			double val2 = dc == null?0:dc;

			if (val1 != val2)
				return false;
		}

		nbPeople += t2.nbPeople;
		t2.nbPeople = 0;
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return nbPeople + " troupes";
	}
}
