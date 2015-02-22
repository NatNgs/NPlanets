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
	private double coefDef; // (usage: att*(2^coefAtt) )
	private double coefVit; //

	public Troupe(Planet source, int nbPeople) {
		this.source = source;
		this.nbPeople = nbPeople;
		double[] coefs = source.getCoefs();
		coefAtt = coefs[0];
		coefDef = coefs[1];
		coefVit = coefs[2];
	}

	public boolean canMergeWith(Troupe t) {
		return coefAtt == t.coefAtt && coefDef == t.coefDef
				&& coefVit == t.coefVit;
	}

	public static Troupe merge(Troupe t1, Troupe t2) {
		if (!t1.canMergeWith(t2))
			return null;

		t1.nbPeople += t2.nbPeople;
		t2.nbPeople = 0;
		return t1;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
