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

	// GETTERS and methods that don't modify planet objects //
	public double getCoef(CoefType type) {
		return coefs.get(type);
	}
	public int getNbPeople() {
		return nbPeople;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return nbPeople + " troupes";
	}

	// SETTERS and methods that modify planet objects //
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

	public void adaptCaracts(Planet p) {
		for (CoefType cType : CoefType.values()) {
			double tCoef = getCoef(cType);
			double pCoef = p.getCoef(cType);
			if (tCoef == pCoef)
				continue;

			double ecart = pCoef
					/ Constants.TroupeDefaultAdaptationTime.getValue();
			if (tCoef > pCoef) {
				double newValue = tCoef - ecart;
				if (newValue < pCoef)
					newValue = pCoef;
				coefs.put(cType, newValue);
			} else { // if (tCoef < pCoef)
				double newValue = tCoef + ecart;
				if (newValue > pCoef)
					newValue = pCoef;
				coefs.put(cType, newValue);
			}
		}
	}
}
