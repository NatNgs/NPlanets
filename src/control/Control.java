package control;

import java.util.HashMap;
import java.util.HashSet;

import vue.players.APlayer;
import vue.server.AServer;
import core.Player;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Control {
	private static final HashMap<AServer, Control> servers = new HashMap<>();
	private HashMap<APlayer, Player> players;
	private HashSet<APlayer> playersReady;

	private Control() {
		players = new HashMap<>();
		playersReady = new HashSet<>();
	}

	// PRIVATE access methods //
	private void update(int nbTurnToSpend) {
		while (nbTurnToSpend > 0) {
			for (Player p : players.values())
				p.update(1);
			nbTurnToSpend--;
		}
	}

	private void sendMessageToAll(HashMap<String, String> m) {
		for (APlayer ap : players.keySet())
			ap.recieveInfos(m);
	}

	private static AServer getServerByPlayer(APlayer aPlayer) {
		for (AServer as : servers.keySet())
			if (servers.get(as).players.containsKey(aPlayer))
				return as;
		return null;
	}

	private static AServer getServerByName(String name) {
		for (AServer as : servers.keySet())
			if (as.getName().equalsIgnoreCase(name))
				return as;
		return null;
	}

	// Player Command Methods //
	private static HashMap<String, String> playerJoin(APlayer aPlayer,
			HashMap<String, String> command) {
		HashMap<String, String> toSender = new HashMap<>(command);

		// If wrong use (no server name specified)
		if (!command.containsKey("server")) {
			toSender.put("state", "wrong_command_use");
			return toSender;
		}

		// If player already is on a server
		AServer curentServer = getServerByPlayer(aPlayer);
		AServer server = getServerByName(command.get("server"));
		if (curentServer != null)
			if (curentServer == server) {
				toSender.put("state", "already");
				return toSender;
			}
			else {
				toSender.put("state", "another");
				return toSender;
			}

		if (server == null) {
			toSender.put("state", "not_found");
			return toSender;
		}

		Control game = servers.get(server);

		game.players.put(aPlayer, new Player());

		HashMap<String, String> toOthers = new HashMap<>();
		toOthers.put("command", "player_join");
		toOthers.put("player", aPlayer.getName());
		game.sendMessageToAll(toOthers);

		toSender.put("state", "ok");
		return toSender;
	}

	private HashMap<String, String> playerLeave(APlayer aPlayer,
			HashMap<String, String> c) {

		HashMap<String, String> toOthers = new HashMap<>();
		toOthers.put("command", "player_leave");
		toOthers.put("player", aPlayer.getName());
		sendMessageToAll(toOthers);
		players.remove(aPlayer);

		HashMap<String, String> toSender = new HashMap<>(c);
		toSender.put("state", "ok");
		return toSender;
	}

	private HashMap<String, String> playerReady(APlayer aPlayer,
			HashMap<String, String> c) {
		if (!players.containsKey(aPlayer))
			return new MessageError("Player not in game", c);

		if (c.isReady()) {
			if (playersReady.contains(aPlayer))
				return new MessageError("Player already ready", c);
			else
				playersReady.add(aPlayer);
		} else if (!playersReady.contains(aPlayer))
			return new MessageError("Player is already not ready", c);
		else
			playersReady.remove(aPlayer);

		sendMessageToAll(new MessagePlayer(c.isReady() ? "ready" : "not_ready",
				aPlayer.getName()));

		return new MessageCommand("ok", c);
	}

	// Server Command Methods //
	private static HashMap<String, String> newServer(AServer aServer,
			HashMap<String, String> c) {
		if (servers.containsKey(aServer))
			return new MessageError("Server '" + c.getName()
					+ "' is already open", c);

		servers.put(aServer, new Control());
		return new MessageCommand("ok", c);
	}

	private HashMap<String, String> serverClose(AServer aServer,
			HashMap<String, String> c) {
		if (!servers.containsKey(aServer))
			return new MessageError("Server '" + c.getName()
					+ "' is already closed", c);

		servers.remove(aServer);
		sendMessageToAll(new MessageServer("closed", aServer.getName()));

		return new MessageCommand("ok", c);
	}

	private HashMap<String, String> serverReady(AServer aServer,
			HashMap<String, String> c) {
		if (players.size() < 2)
			return new MessageCommand("not_enough_players", c);
		if (playersReady.size() < players.size())
			return new MessageCommand("players_not_ready", c);

		return new MessageCommand("ok", c);
	}

	// PUBLIC STATIC access methods //
	public static HashMap<String, String> sendCommand(APlayer aPlayer,
			HashMap<String, String> command) {

		AServer serv = getServerByPlayer(aPlayer);
		Control game = null;
		if (serv != null)
			game = servers.get(serv);

		String commandName = command.get("command");

		if (commandName == null) {
			HashMap<String, String> retour = new HashMap<>(command);
			retour.put("state", "wrong_command_use");
			return retour;
		}

		try {
			switch (commandName) {
			case "player_leave":
				return game.playerLeave(aPlayer, command);
			case "player_join":
				return playerJoin(aPlayer, command);
			case "player_ready":
				return game.playerReady(aPlayer, command);
			}
		} catch (NullPointerException npe) {
			HashMap<String, String> retour = new HashMap<>();
			retour.put("command", commandName);
			retour.put("state", "not_in_game");
			return retour;
		}

		HashMap<String, String> retour = new HashMap<>(command);
		retour.put("state", "command_unknown");
		return retour;
	}

	public static HashMap<String, String> sendCommand(AServer aServer,
			HashMap<String, String> c) {

		Control game = servers.get(aServer);
		String commandName = c.get("command");

		if (commandName != null)
			try {
				switch (commandName) {
				case "close":
					return game.serverClose(aServer, c);
				case "open":
					return newServer(aServer, c);
				case "ready":
					return game.serverReady(aServer, c);
				}
			} catch (NullPointerException npe) {
				HashMap<String, String> retour = new HashMap<>();
				retour.put("command", commandName);
				retour.put("state", "not_open");
				return retour;
			}

		HashMap<String, String> retour = new HashMap<>();
		retour.put("command", commandName);
		retour.put("state", "command_unknown");
		return retour;
	}
}
