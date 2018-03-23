package natngs.nplanets.ui.plateauGeneral;

import java.util.ArrayList;

public class Star implements Astre {

	protected ArrayList<Planet> planet;
	protected Coord coord;
	protected double size;
	protected String id;

	public Star(String id, Coord c) {
		this.id = id;
		planet = new ArrayList<>();
		coord = c;
		size = Math.random()*900+100;
		int nbP = (int) (Math.random()*10+5);
		for(int i=0; i<nbP; i++)
			addPlanet();
	}

	private Planet addPlanet() {
		double taille = Math.random()*this.size /10+10;
		double rayon = this.size *(5+Math.random()*5);

		if(planet.size()>0) {
			Planet p = planet.get(planet.size()-1);
			rayon += p.getCoord().getDistance(coord) + p.getRayonZoneOrbite();
		}

		Coord c = new Coord(rayon, Math.random()*Math.PI*2, this);

		double g = Math.pow(taille, 3)+Math.pow(this.size, 3);
		double var = 1/Math.sqrt(rayon*rayon*rayon/g);

		if(Math.random()>0.5)	// une chance sur 2 de tourner a l'envers
			var*=-1;

		Planet p = new Planet(id+(planet.size()+1), this, c, var, taille);
		planet.add(p);
		return p;
	}

	protected void move(double nb) {
		for(Planet p : planet)
			p.avancer(nb);
	}

	public ArrayList<Planet> getPlanets() {
		return new ArrayList<Planet>(planet);
	}

	public double getSize() {
		return size;
	}

	public double getOrbitRadius() {
		if(planet.size()>0) {
			Planet p = planet.get(planet.size()-1);
			return p.coord.getRayon() + p.getRayonZoneOrbite();
		}
		else
			return size;
	}

	public double[] getBounds() {
		double zo = getOrbitRadius();
		return new double[]{coord.getX()-zo, coord.getY()-zo, 2*zo, 2*zo};
	}

	@Override
	public Coord getCoord() {
		return coord;
	}

	@Override
	public String toString() {
		return "Star: "+coord+", size:"+(int)size;
	}

	@Override
	public double getVS() {
		return 0;
	}

	public String getId() {
		return id;
	}
}
