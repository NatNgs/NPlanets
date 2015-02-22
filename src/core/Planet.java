package core;

import java.util.HashSet;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Planet extends Element {
	private double coefAtt; // 0 is normal, -1 is bad, 1 is good
	private double coefDef; // (usage: att*(2^coefAtt) )
	private double coefVit; //
	private HashSet<Troupe> troupes;

	public Planet(Coord c, double size) {
		super(c, size);
		troupes = new HashSet<>();
	}

	public void setCoefs(double coefAtt, double coefDef, double coefVit) {
		this.coefAtt = coefAtt;
		this.coefDef = coefDef;
		this.coefVit = coefVit;
	}

	public double[] getCoefs() {
		return new double[] { coefAtt, coefDef, coefVit };
	}

}
