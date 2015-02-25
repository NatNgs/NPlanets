package control;

import java.util.HashMap;

import vue.players.APlayer;
import vue.server.AServer;
import core.Player;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public abstract class Control {
	private static final HashMap<AServer, Server> servers = new HashMap<>();

	// PRIVATE access methods //
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

		Server game = servers.get(server);

		game.players.put(aPlayer, new Player());

		HashMap<String, String> toOthers = new HashMap<>();
		toOthers.put("command", "player_join");
		toOthers.put("player", aPlayer.getName());
		game.sendMessageToPlayers(toOthers);

		toSender.put("state", "ok");
		return toSender;
	}


	// Server Command Methods //
	private static HashMap<String, String> serverOpen(AServer aServer,
			HashMap<String, String> c) {
		HashMap<String, String> toSender = new HashMap<>(c);

		if (servers.containsKey(aServer)) {
			toSender.put("state", "already");
			return toSender;
		}

		servers.put(aServer, new Server());

		toSender.put("state", "ok");
		return toSender;
	}

	private static HashMap<String, String> serverClose(AServer aServer,
			HashMap<String, String> c) {
		HashMap<String, String> toSender = new HashMap<>(c);

		Server s = servers.get(aServer);
		if (s == null) {
			toSender.put("state", "already");
			return toSender;
		}

		servers.remove(aServer);

		HashMap<String, String> toPlayers = new HashMap<>();
		toPlayers.put("command", "server_close");
		s.sendMessageToPlayers(toPlayers);

		toSender.put("state", "ok");
		return toSender;
	}


	// PUBLIC STATIC access methods //
	public static HashMap<String, String> sendCommand(APlayer aPlayer,
			HashMap<String, String> command) {

		AServer serv = getServerByPlayer(aPlayer);
		Server game = null;
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
			case "join":
				return playerJoin(aPlayer, command);
			case "leave":
				return game.playerLeave(aPlayer, command);
			case "ready":
				return game.playerReady(aPlayer, command);
			case "not_ready":
				return game.playerNotReady(aPlayer, command);
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

		Server game = servers.get(aServer);
		String commandName = c.get("command");

		if (commandName != null)
			try {
				switch (commandName) {
				case "close":
					return serverClose(aServer, c);
				case "open":
					return serverOpen(aServer, c);
				case "ready":
					return game.serverReady(aServer, c);
				case "not_ready":
					return game.serverNotReady(aServer, c);
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
