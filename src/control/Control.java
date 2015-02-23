package control;

import java.util.HashMap;
import java.util.HashSet;

import vue.messages.*;
import vue.players.APlayer;
import core.Player;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Control {
	private static final Control instance = new Control();
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

	// Command Methods //
	private Message playerQuit(APlayer aPlayer, CommandQuit c) {
		if (!players.containsKey(aPlayer))
			return new MessageError("Player not in game", c);
		sendMessageToAll(new MessagePlayer("quit", aPlayer.getName()));
		players.remove(aPlayer);

		return new MessageOk(c);
	}

	private Message playerJoin(APlayer aPlayer, CommandJoin c) {
		if (players.containsKey(aPlayer))
			return new MessageError("Player already in game", c);

		players.put(aPlayer, new Player());
		sendMessageToAll(new MessagePlayer("join", aPlayer.getName()));

		return new MessageOk(c);
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

		return new MessageOk(c);
	}

	// PUBLIC STATIC access methods //
	public static Message sendCommand(APlayer aPlayer, Command c) {
		try {
			switch (c.getName()) {
			case "quit":
				return instance.playerQuit(aPlayer, (CommandQuit) c);
			case "join":
				return instance.playerJoin(aPlayer, (CommandJoin) c);
			case "ready":
				return instance.playerReady(aPlayer, (CommandReady) c);
			}
		} catch (ClassCastException cce) {
			return new MessageError("Command '" + c.getName() + "' can't be a "
					+ c.getClass(), c);
		}
		return new MessageError("Command '" + c.getName() + "' is unknown", c);
	}

}
