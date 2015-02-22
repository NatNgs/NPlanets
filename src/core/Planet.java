package core;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Planet extends Element {
	private final static int DEFAULT_GEN_TROUPES = 10;

	private double coefAtt; // 0 is normal, -1 is bad, 1 is good
	private double coefDef; // (usage: 2^coefxxx )
	private double coefVit; //
	private HashSet<Troupe> troupes;
	private double coefGenTroupes; // 0 is normal, -1 bad and 1 good.

	// CONSTRUCTORS //
	public Planet(Coord c, double size) {
		super(c, size);
		troupes = new HashSet<>();
		coefAtt = 0;
		coefDef = 0;
		coefVit = 0;
		coefGenTroupes = 0;
	}

	// GETTERS and methods that don't modify planet objects //
	public double[] getCoefs() {
		return new double[] { coefAtt, coefDef, coefVit };
	}

	// SETTERS and methods that modify planet objects //
	public void setCoefsTroupes(double coefAtt, double coefDef, double coefVit) {
		this.coefAtt = coefAtt;
		this.coefDef = coefDef;
		this.coefVit = coefVit;
	}

	public void setCoefGenTroupes(double coefGenTroupes) {
		this.coefGenTroupes = coefGenTroupes;
	}

	public void generateMoreTroupes() {
		troupes.add(new Troupe(this, (int) (DEFAULT_GEN_TROUPES * Math.pow(2,
				coefGenTroupes))));
		cleanTroupes();
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

	@Override
	public String toString() {
		return "Planet: " + troupes;
	}

	/**
	 * Onli for tests to this class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Planet p = new Planet(new Coord(new double[] { 0, 0, 0 }), 1);
		p.setCoefGenTroupes(Math.random() * 2 - 1);
		System.out.println("Planet have " + (int) (p.coefGenTroupes * 100)
				/ 100. + " coef.");
		System.out.println(p.toString());
		p.generateMoreTroupes();
		System.out.println(p.toString());
		p.generateMoreTroupes();
		System.out.println(p.toString());
		p.generateMoreTroupes();
		System.out.println(p.toString());
	}
}
