package core;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Troupe {
	private int nbPeople;
	private Planet source;
	private double coefAtt; // 0 is normal, -1 is bad, 1 is good
	private double coefDef; // (usage: 2^coefxxx )
	private double coefVit; //

	public Troupe(Planet source, int nbPeople) {
		this.source = source;
		this.nbPeople = nbPeople;
		double[] coefs = source.getCoefs();
		coefAtt = coefs[0];
		coefDef = coefs[1];
		coefVit = coefs[2];
	}

	public int getNbPeople() {
		return nbPeople;
	}

	public boolean merge(Troupe t2) {
		if (coefAtt != t2.coefAtt || coefDef != t2.coefDef
				|| coefVit != t2.coefVit)
			return false;

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
		return nbPeople + " troupes [" + coefAtt + ", " + coefDef + ", "
				+ coefVit + "]";
	}
}
