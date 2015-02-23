package control;

import java.util.HashMap;
import java.util.HashSet;

import vue.messages.*;
import vue.messages.commandPlayer.*;
import vue.messages.commandServer.*;
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

	private void sendMessageToAll(Message m) {
		for (APlayer ap : players.keySet())
			ap.recieveInfos(m);
	}

	private static Control getControlByPlayer(APlayer aPlayer) {
		for (Control c : servers.values())
			if (c.players.containsKey(aPlayer))
				return c;
		return null;
	}

	private static AServer getServerByName(String name) {
		for (AServer as : servers.keySet())
			if (as.getName().equalsIgnoreCase(name))
				return as;
		return null;
	}

	// Player Command Methods //
	private static Message playerJoin(APlayer aPlayer, CommandJoin c) {
		if (getControlByPlayer(aPlayer) != null)
			return new MessageError("Player '" + aPlayer.getName()
					+ "' is already in game", c);

		AServer server = getServerByName(c.getServerName());
		if (server == null)
			return new MessageError("No server found with name '"
					+ c.getServerName() + "'", c);

		Control game = servers.get(server);

		game.players.put(aPlayer, new Player());
		game.sendMessageToAll(new MessagePlayer("join", aPlayer.getName()));

		return new MessageCommand("ok", c);
	}

	private Message playerQuit(APlayer aPlayer, CommandQuit c) {
		sendMessageToAll(new MessagePlayer("quit", aPlayer.getName()));
		players.remove(aPlayer);

		return new MessageCommand("ok", c);
	}

	private Message playerReady(APlayer aPlayer, CommandReady c) {
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
	private static Message newServer(AServer aServer, CommandServerOpen c) {

		return new MessageError("Server Command '" + c.getName()
				+ "' is unknown", c);
	}

	private Message serverClose(AServer aServer, CommandServerClose c) {

		return new MessageError("Server Command '" + c.getName()
				+ "' is unknown", c);
	}

	private Message serverReady(AServer aServer, CommandServerReady c) {

		return new MessageError("Server Command '" + c.getName()
				+ "' is unknown", c);
	}

	// PUBLIC STATIC access methods //
	public static Message sendCommand(APlayer aPlayer, CommandPlayer c) {
		try {
			Control game = getControlByPlayer(aPlayer);

			switch (c.getName()) {
			case "quit":
				return game.playerQuit(aPlayer, (CommandQuit) c);
			case "join":
				return playerJoin(aPlayer, (CommandJoin) c);
			case "ready":
				return game.playerReady(aPlayer, (CommandReady) c);
			}
		} catch (ClassCastException cce) {
			return new MessageError("Command '" + c.getName() + "' can't be a "
					+ c.getClass(), c);
		} catch (NullPointerException npe) {
			return new MessageError("Player '" + aPlayer.getName()
					+ "' isn't in a game", c);
		}
		return new MessageError("Command '" + c.getName() + "' is unknown", c);
	}

	public static Message sendCommand(AServer aServer, CommandServer c) {
		try {
			Control game = servers.get(aServer);

			switch (c.getName()) {
			case "close":
				return game.serverClose(aServer, (CommandServerClose) c);
			case "open":
				return newServer(aServer, (CommandServerOpen) c);
			case "ready":
				return game.serverReady(aServer, (CommandServerReady) c);
			}
		} catch (ClassCastException cce) {
			return new MessageError("Server Command '" + c.getName()
					+ "' can't be a "
					+ c.getClass(), c);
		} catch (NullPointerException npe) {
			return new MessageError("Server '" + aServer.getName()
					+ "' isn't open", c);
		}
		return new MessageError("Server Command '" + c.getName()
				+ "' is unknown", c);
	}
}
