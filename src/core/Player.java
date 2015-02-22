package core;

import java.util.HashSet;

import core.map.Planet;
import core.map.SpaceShip;

public class Player implements TurnDepending {
	// May temporary contain some planets not owned by the player
	private HashSet<Planet> planets;
	private HashSet<SpaceShip> spaceships;
	private HashSet<BattleReport> reports;

	// CONSTRUCTORS //
	public Player() {
		planets = new HashSet<>();
		spaceships = new HashSet<>();
		reports = new HashSet<>();
	}

	// GETTERS and methods that don't modify objects //

	// SETTERS and methods that modify objects //
	private void cleanPossessions() {
		for (SpaceShip s : new HashSet<>(spaceships))
			if (s.hasFinished())
				spaceships.remove(s);

		for (Planet p : new HashSet<>(planets))
			if (p.getOwner() != this)
				planets.remove(p);
	}

	public void addPlanet(Planet p) {
		planets.add(p);
	}

	public void addReport(BattleReport br) {
		reports.add(br);
	}

	// OVERRIDE //
	@Override
	public void update(int nbTurnToSpend) {
		while (nbTurnToSpend > 0) {
			cleanPossessions();

			for (SpaceShip s : spaceships)
				s.update(1);
			nbTurnToSpend--;
		}
	}
}
