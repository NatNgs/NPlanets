package core.map;

import java.util.HashSet;

import core.model.Coord;
import core.model.TurnDepending;


/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Universe implements TurnDepending {
	private HashSet<Planet> planets;
	private double size; // Distance max avec le (0,0,0)

	public void setMap(int width, int size, int nbP) {
		planets = new HashSet<>();
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
			while (!canPlace(p));
		}
	}
	private double getRandomPosition() {
		return (Math.random() * 2 - 1) * size;
	}

	private boolean canPlace(Planet param) {
		for (Planet p : planets)
			if (param.tooNearFrom(p))
				return false;
		return true;
	}


	// OVERRIDE methods //
	@Override
	public void update(int nbTurnToSpend) {
		while (nbTurnToSpend > 0) {
			for (Planet p : planets)
				p.update(1);

			nbTurnToSpend--;
		}
	}

}
