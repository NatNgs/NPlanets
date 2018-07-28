package natngs.nplanets.server.controller;

import natngs.nplanets.server.Universe;
import natngs.nplanets.server.located.Planet;
import natngs.nplanets.server.located.Ship;

import java.util.HashMap;
import java.util.Map;

public class GameController {
	private Universe u;
	private double currentTurn;
	private final Map<String, Planet> planets = new HashMap<>();
	private final Map<String, Ship> ships = new HashMap<>();

	public GameController() {
		u = new Universe();
		currentTurn = 0;
	}

	public void nextTurn() {
		currentTurn++;
	}


	public Universe getUniverse() {
		return u;
	}

	public void put(final String sun, final Planet planet) {
		planets.put(sun, planet);
	}

	public void put(final String sun, final Ship ship) {
		ships.put(sun, ship);
	}

	public Map<String, Planet> getPlanets() {
		return planets;
	}

	public Map<String, Ship> getShips() {
		return ships;
	}

	public double getTurn() {
		return currentTurn;
	}
}
