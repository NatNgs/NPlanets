package core.model;

import java.util.Iterator;

import core.enums.CoefType;
import core.map.Planet;
import core.map.SpaceShip;

public class BattleReport {
	private Player attPlayer;
	private Player defPlayer;
	private int nbTroopsAtt;
	private int nbTroopsDef;
	private int nbTroopsWon; // if 0, it means equality
	private boolean attWon; // if false and nbTroopsWin > 0, it means defWon

	public BattleReport(SpaceShip att, Planet def) {
		if (att.getOwner() == def.getOwner())
			throw new IllegalArgumentException(this.getClass()
					+ "::spaceshipAttack: Impossible to attack allies.");

		attPlayer = att.getOwner();
		defPlayer = def.getOwner();
		nbTroopsAtt = att.getNbSoldiers();
		nbTroopsDef = def.getNbSoldiers();

		int nbTroopsAtt = this.nbTroopsAtt;
		int nbTroopsDef = this.nbTroopsDef;

		// Calculating battle bonus depending on armes's sizes
		// This equation can be changed to equalise game parameters
		double bonusAtt = Math.sqrt(att.getNbSoldiers() / def.getNbSoldiers()) - 1;

		Iterator<Troop> attI = att.getTroops().iterator();
		Iterator<Troop> defI = att.getTroops().iterator();

		Troop attT = null, defT = null;

		while (attI.hasNext() && defI.hasNext()) {
			if (attT == null || attT.getNbSoldiers() == 0)
				attT = attI.next();
			if (defT == null || defT.getNbSoldiers() == 0)
				defT = defI.next();

			double coefAtt = Math.pow(2, attT.getCoef(CoefType.Attack)
					+ bonusAtt);
			double coefDef = Math.pow(2, defT.getCoef(CoefType.Defense));
			double powerAtt = attT.getNbSoldiers() * coefAtt;
			double powerDef = defT.getNbSoldiers() * coefDef;

			if (powerAtt >= powerDef) {
				// Kill all defenser from def troop
				nbTroopsDef-= defT.getNbSoldiers();
				defT.kill(defT.getNbSoldiers());
				// Kill some attackers from att troop
				nbTroopsAtt-= (int) (powerDef / coefAtt);
				attT.kill((int) (powerDef / coefAtt));
			} else { // if powerDef > powerAtt
				// Kill all defenser from def troop
				nbTroopsAtt-= attT.getNbSoldiers();
				attT.kill(attT.getNbSoldiers());
				// Kill some attackers from att troop
				nbTroopsDef-= (int) (powerAtt / coefDef);
				defT.kill((int) (powerAtt / coefDef));
			}
		}

		if (nbTroopsAtt == nbTroopsDef) {
			nbTroopsWon = 0;
			attWon = false;
			// Planet is abandonned
			def.setOwner(null);
		} else if (nbTroopsAtt > nbTroopsDef) {
			nbTroopsWon = nbTroopsAtt;
			attWon = true;
			// Planet is conquered
			def.setOwner(attPlayer);
			def.addTroops(att.getTroops());
		} else { // if(nbTroopsAtt < nbTroopsDef)
			nbTroopsWon = nbTroopsDef;
			attWon = false;
			// Planet remain unchanged
		}
	}


	// GETTERS //
	public Player getAttPlayer() {
		return attPlayer;
	}

	public Player getDefPlayer() {
		return defPlayer;
	}

	public int getNbTroopsAtt() {
		return nbTroopsAtt;
	}

	public int getNbTroopsDef() {
		return nbTroopsDef;
	}

	public int getNbTroopsWon() {
		return nbTroopsWon;
	}

	public boolean isAttWon() {
		return attWon;
	}

}
