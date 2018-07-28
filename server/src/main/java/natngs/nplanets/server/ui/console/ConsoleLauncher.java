package natngs.nplanets.server.ui.console;

import natngs.nplanets.common.Location;
import natngs.nplanets.server.ILocated;
import natngs.nplanets.server.Universe;
import natngs.nplanets.server.controller.GameController;
import natngs.nplanets.server.located.Planet;
import natngs.nplanets.server.movements.repetitive.RoundOrbit2D;
import natngs.nplanets.server.located.Ship;
import natngs.nplanets.server.movements.repetitive.StaticRelative;
import natngs.nplanets.server.movements.translation.LinearTranslation;

import java.util.*;

public class ConsoleLauncher {
	private GameController gc = new GameController();
	private boolean exit;
	private final Scanner sc;

	private ConsoleLauncher() {
		System.out.println("CONSOLE LAUNCHER STARTED");

		sc = new Scanner(System.in);
		printCmdList();

		exit = false;
		do {
			System.out.print("\n> ");
			final List<String> params = new ArrayList<>(Arrays.asList(sc.nextLine().toUpperCase().split(" ")));
			if (params.isEmpty())
				continue;

			final String cmd = params.remove(0).toUpperCase();
			cmdParser(cmd, params);
			try {
				Thread.sleep(50);
			} catch (InterruptedException ignored) {
			}
		} while (!exit);
		System.out.println("CONSOLE LAUNCHER TERMINATED");
	}

	private void cmdParser(String cmd, List<String> params) {
		switch (cmd) {
			case "H":
			case "HELP": {
				printCmdList();
				break;
			}
			case "QUIT":
			case "EXIT": {
				exit = true;
				break;
			}
			case "DEMO": {
				if (params.isEmpty()) {
					System.err.println("DEMO needs an argument");
				} else {
					int turn = Integer.parseInt(params.remove(0));
					demo(turn);
				}
				break;
			}
			default:
				System.err.println("Command not recognized: '" + cmd + "'");
		}
	}

	private void printCmdList() {
		String[] cmds = new String[]{
				"H / HELP : Print this message",
				"EXIT / QUIT : Stop NPlanets",
				"DEMO [turns<int>]: Lets play a game demonstration during [turns] turns"
		};

		Arrays.sort(cmds);
		System.out.println("Commands:\n- " + String.join("\n- ", cmds));
	}

	private void demo(int turns) {
		Universe u = gc.getUniverse();
		// Location in UA (sun-earth distance)
		gc.put("Sun", new Planet(new StaticRelative(u, new Location(0, 0))));

		// Orbit duration in months (12*earth orbit duration)

		gc.put("Mercury", new Planet(new RoundOrbit2D(u, new Location(0, 0.387), 12 * 0.241)));
		gc.put("Venus", new Planet(new RoundOrbit2D(u, new Location(0, 0.723), 12 * 0.615)));
		gc.put("Earth", new Planet(new RoundOrbit2D(u, new Location(0, 1.000), 12 * 1.000)));
		gc.put("Mars", new Planet(new RoundOrbit2D(u, new Location(0, 1.523), 12 * 1.881)));
		gc.put("Jupiter", new Planet(new RoundOrbit2D(u, new Location(0, 5.204), 12 * 11.862)));
		gc.put("Saturn", new Planet(new RoundOrbit2D(u, new Location(0, 9.583), 12 * 29.457)));
		gc.put("Uranus", new Planet(new RoundOrbit2D(u, new Location(0, 19.218), 12 * 84.021)));
		gc.put("Neptune", new Planet(new RoundOrbit2D(u, new Location(0, 30.110), 12 * 164.8)));
		gc.put("Pluto", new Planet(new RoundOrbit2D(u, new Location(0, 39.48), 12 * 248.000)));

		List<ILocated> steps = new ArrayList<>();
		steps.add(gc.getPlanets().get("Earth"));
		steps.add(gc.getPlanets().get("Jupiter"));
		steps.add(gc.getPlanets().get("Saturn"));
		steps.add(gc.getPlanets().get("Pluto"));

		try {
			// Speed in AU per month (speedup x2)
			Ship voyager = new Ship(new LinearTranslation(steps, 3.6 * 2 / 12, 0));
			System.out.println("Voyager:" + voyager.toString());
			gc.put("Voyager", voyager);

			do {
				System.out.println("Press Enter...");
				sc.nextLine();
				printGameDescription();
				gc.nextTurn();
			} while (--turns > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printGameDescription() {
		double turn = gc.getTurn();
		List<String> desc = new ArrayList<>();
		for (Map.Entry<String, Planet> p : gc.getPlanets().entrySet()) {
			desc.add(p.getKey() + ": " + p.getValue().getLocation(turn));
		}
		for (Map.Entry<String, Ship> p : gc.getShips().entrySet()) {
			desc.add(p.getKey() + ": " + p.getValue().getLocation(turn));
		}
		System.out.println("Turn " + turn + ":\n\t" + String.join("\n\t", desc));
	}

	public static void main(String[] args) {
		new ConsoleLauncher();
	}
}
