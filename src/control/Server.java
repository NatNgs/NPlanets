package control;

import java.util.HashMap;
import java.util.HashSet;

import vue.players.APlayer;
import vue.server.AServer;
import core.Player;

public class Server {
	private boolean ready;
	public HashMap<APlayer, Player> players;
	public HashSet<APlayer> playersReady;

	public Server() {
		players = new HashMap<>();
		playersReady = new HashSet<>();
		ready = false;
	}

	// // // Utility Methods // // //
	public void sendMessageToPlayers(HashMap<String, String> m) {
		for (APlayer ap : players.keySet())
			ap.recieveInfos(m);
	}

	// // // Game Methods // // //
	private void update(int nbTurnToSpend) {
		while (nbTurnToSpend > 0) {
			for (Player p : players.values())
				p.update(1);
			nbTurnToSpend--;
		}
	}

	// // // Player Command Methods // // //
	public HashMap<String, String> playerLeave(APlayer aPlayer,
			HashMap<String, String> c) {

		HashMap<String, String> toOthers = new HashMap<>();
		toOthers.put("command", "player_leave");
		toOthers.put("player", aPlayer.getName());
		sendMessageToPlayers(toOthers);
		players.remove(aPlayer);

		HashMap<String, String> toSender = new HashMap<>(c);
		toSender.put("state", "ok");
		return toSender;
	}

	public HashMap<String, String> playerReady(APlayer aPlayer,
			HashMap<String, String> c) {

		HashMap<String, String> toSender = new HashMap<>(c);
		if (playersReady.contains(aPlayer)) {
			toSender.put("state", "already");
			return toSender;
		}

		playersReady.add(aPlayer);

		HashMap<String, String> toOthers = new HashMap<>();
		toOthers.put("command", "player_ready");
		toOthers.put("player", aPlayer.getName());
		sendMessageToPlayers(toOthers);

		toSender.put("state", "ok");
		return toSender;
	}

	public HashMap<String, String> playerNotReady(APlayer aPlayer,
			HashMap<String, String> c) {

		HashMap<String, String> toSender = new HashMap<>(c);
		if (!playersReady.contains(aPlayer)) {
			toSender.put("state", "already");
			return toSender;
		}

		playersReady.add(aPlayer);

		HashMap<String, String> toOthers = new HashMap<>();
		toOthers.put("command", "player_not_ready");
		toOthers.put("player", aPlayer.getName());
		sendMessageToPlayers(toOthers);

		toSender.put("state", "ok");
		return toSender;
	}


	// // // Server Command Methods // // //
	public HashMap<String, String> serverReady(AServer aServer,
			HashMap<String, String> c) {
		HashMap<String, String> toSender = new HashMap<>(c);

		if (ready) {
			toSender.put("state", "already");
			return toSender;
		}
		if (players.size() < 2) {
			toSender.put("state", "not_enough_players");
			return toSender;
		}

		ready = true;

		HashMap<String, String> toPlayers = new HashMap<>();
		toPlayers.put("command", "server_ready");
		sendMessageToPlayers(toPlayers);

		toSender.put("state", "ok");
		return toSender;
	}

	public HashMap<String, String> serverNotReady(AServer aServer,
			HashMap<String, String> c) {
		HashMap<String, String> toSender = new HashMap<>(c);

		if (!ready) {
			toSender.put("state", "already");
			return toSender;
		}

		ready = false;

		HashMap<String, String> toPlayers = new HashMap<>();
		toPlayers.put("command", "server_not_ready");
		sendMessageToPlayers(toPlayers);

		toSender.put("state", "ok");
		return toSender;
	}

	public HashMap<String, String> serverStart(AServer aServer,
			HashMap<String, String> c) {
		HashMap<String, String> toSender = new HashMap<>(c);
		toSender.put("nbTotal", "" + players.size());
		toSender.put("nbReady", "" + playersReady.size());

		if (!ready) {
			toSender.put("state", "server_not_ready");
			return toSender;
		}

		if (playersReady.size() < players.size()) {
			toSender.put("state", "players_not_ready");
			return toSender;
		}

		// TODO start game

		toSender.put("state", "ok");
		return toSender;
	}

}
