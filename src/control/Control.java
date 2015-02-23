package control;

import java.util.HashMap;

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
	private HashMap<APlayer, Boolean> playersReady;

	private Control() {
		players = new HashMap<>();
		playersReady = new HashMap<>();
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
	private Message playerQuit(APlayer aPlayer, Command c) {
		if (!players.containsKey(aPlayer))
			return new MessageError("Player not in game", c);
		sendMessageToAll(new MessagePlayerLeave(aPlayer.getName()));
		players.remove(aPlayer);

		return new MessageOk(c);
	}

	private Message playerJoin(APlayer aPlayer, Command c) {
		if (players.containsKey(aPlayer))
			return new MessageError("Player already in game", c);

		players.put(aPlayer, new Player());
		sendMessageToAll(new MessagePlayerJoin(aPlayer.getName()));

		return new MessageOk(c);
	}

	// PUBLIC STATIC access methods //
	public static Message sendCommand(APlayer aPlayer, Command c) {
		switch (c.getName()) {
		case "quit":
			return instance.playerQuit(aPlayer, c);
		case "join":
			return instance.playerJoin(aPlayer, c);
		}
		return new MessageError("Command untreatable", c);
	}

}
