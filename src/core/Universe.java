package core;

import java.util.HashMap;

import map.Planet;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Universe {
	private HashMap<Coord, Planet> planets;
	private double size; // Distance max avec le (0,0,0)

	public void setMap(int width, int size, int nbP) {
		planets = new HashMap<>();
		this.size = size;
		generateMap(nbP);
	}

	private void generateMap(int nbP) {
		planets.clear();

		for(int i=0; i<nbP; i++) {
			Planet p = null;
			do
				p = new Planet(new Coord(new double[] { getRandomPosition(),
						getRandomPosition(), getRandomPosition() }),
						Math.random() + 1);
			while (canPlace(p));
		}
	}
	private double getRandomPosition() {
		return (Math.random() * 2 - 1) * size;
	}

	private boolean canPlace(Planet param) {
		for (Planet p : planets.values())
			if (param.tooNearFrom(p))
				return false;
		return true;
	}

}
