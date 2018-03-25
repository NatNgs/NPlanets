package natngs.nplanets.client.model;

import java.util.ArrayList;

public class Planet implements Astre {

	protected String id;
	protected ArrayList<Planet> satellites;
	private Astre referentiel;
	protected Coord coord;
	protected double var;    // vitesse angulaire autour du référentiel
	protected double taille;
	protected double vitesse;

	public Planet(String id, Astre ref, Coord c, double var, double taille) {
		this.id = id;
		referentiel = ref;
		coord = c;
		this.var = var;
		this.taille = taille;
		vitesse = 1;//1/Math.sqrt(size);
		satellites = new ArrayList<>();

		if (ref instanceof Star) {
			int nbP = (int)(Math.random() * 5);
			for (int i = 0; i < nbP; i++)
				addSatellite();
		}

	}

	private Planet addSatellite() {
		double taille = Math.random() * this.taille / 5 + 1;
		double rayon = (satellites.size() > 0 ? satellites.get(satellites.size() - 1).getCoord().getDistance(coord) : 0) + this.taille * (2 + Math.random() * 3);

		Coord c = new Coord(rayon, Math.random() * Math.PI * 2, this);
		double g = Math.pow(taille, 3) + Math.pow(this.taille, 3);
		double var = 1 / Math.sqrt(rayon * rayon * rayon / g);

		if (Math.random() > 0.5)    // une chance sur 2 de tourner a l'envers
			var *= -1;

		Planet p = new Planet(id + (char)(satellites.size() + 'a'), this, c, var, taille);
		satellites.add(p);
		return p;
	}

	protected void avancer(double nb) {
		coord = getFutureCoord(nb);
		for (Planet p : satellites)
			p.avancer(nb);
	}

	public ArrayList<Planet> getSatellites() {
		return new ArrayList<Planet>(satellites);
	}

	public double getTaille() {
		return taille;
	}

	public double getRayonZoneOrbite() {
		if (satellites.size() < 1)
			return taille;

		Planet s = satellites.get(satellites.size() - 1);
		return s.coord.getRayon() + s.taille * 2;
	}

	public String getId() {
		return id;
	}

	public double[] getRect() {
		double zo = getRayonZoneOrbite();
		return new double[]{coord.getX() - zo, coord.getY() - zo, 2 * zo, 2 * zo};
	}

	public Coord getFutureCoord(double tps) {
		return new Coord(coord.getRayon(), coord.getAngle() + tps * var, referentiel);
	}

	@Override
	public double getVS() {
		return var * coord.getRayon() + referentiel.getVS();
	}

	@Override
	public Coord getCoord() {
		return coord;
	}

	@Override
	public String toString() {
		return "Planet: " + coord + ", size:" + (int)taille + ",\tvar:" + var + " (1t / " + Math.abs((int)(Math.PI * 2 / var)) + ")";
	}


}
