package core;

import java.util.*;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Planet extends Element {
	private final static int DEFAULT_GEN_TROUPES = 10;

	// When not in this map, value considered is "0" (usage: 2^coef)
	private HashMap<CoefType, Double> coefs;
	private HashSet<Troupe> troupes;

	// CONSTRUCTORS //
	public Planet(Coord c, double size) {
		super(c, size);
		troupes = new HashSet<>();
		coefs = new HashMap<>();
	}

	// GETTERS and methods that don't modify planet objects //
	public double getCoef(CoefType type) {
		return coefs.get(type);
	}

	public HashMap<CoefType, Double> getAllCoefs() {
		return new HashMap<>(coefs);
	}

	// SETTERS and methods that modify planet objects //
	public void setCoef(CoefType cType, double value) {
		coefs.put(cType, value);
	}

	public void generateMoreTroupes() {
		if (!coefs.containsKey(CoefType.GenTroupesSpeed))
			setCoef(CoefType.GenTroupesSpeed, 0);

		troupes.add(new Troupe(this, (int) (DEFAULT_GEN_TROUPES * Math.pow(2,
				coefs.get(CoefType.GenTroupesSpeed)))));
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
		p.setCoef(CoefType.GenTroupesSpeed, Math.random() * 2 - 1);
		System.out.println("Planet have "
				+ (int) (p.getCoef(CoefType.GenTroupesSpeed) * 100)
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
