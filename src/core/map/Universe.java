package core.map;

import java.util.HashMap;
import java.util.HashSet;

import core.Coord;
import core.TurnDepending;


/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Universe implements TurnDepending {
	private HashMap<Coord, Planet> planets;
	private HashMap<Coord, SpaceShip> spaceships;
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

	private void cleanSpaceships() {
		for (SpaceShip s : spaceships.values())
			if(s.hasFinished())
				spaceships.remove(s);
	}

	// OVERRIDE methods //
	@Override
	public void update(int nbTurnToSpend) {
		while (nbTurnToSpend > 0) {
			cleanSpaceships();
			HashSet<TurnDepending> tdObjects = new HashSet<>();
			tdObjects.addAll(planets.values());
			tdObjects.addAll(spaceships.values());

			for (TurnDepending tdObject : tdObjects)
				tdObject.update(1);
			nbTurnToSpend--;
		}
	}

}
