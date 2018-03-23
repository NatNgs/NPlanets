package natngs.nplanets.ui.plateauGeneral;

import java.util.ArrayList;

public class Plateau {

	private ArrayList<Star> étoiles;
	private ArrayList<Vaisseau> vaisseaux;

	public Plateau()
	{
		étoiles = new ArrayList<>();
		vaisseaux = new ArrayList<>();
	}

	public Star addEtoile() {

		if(étoiles.isEmpty())
			étoiles.add(new Star("Α", new Coord(0, 0)));	//	ATTENTION, ceci n'est pas un a, mais un α !
		else {
			boolean ok = false;
			Star ne = null;
			double r = étoiles.get(étoiles.size()-1).coord.getRayon();

			while(!ok) {
				ne = new Star(""+(char)('Α'+étoiles.size()), new Coord(r, Math.random()*Math.PI*2));
				r+=ne.size;

				ok = true;
				for(Star e : étoiles)	//	si les zones des étoiles sont en contact
					if(e.coord.getDistance(ne.coord)<e.getOrbitRadius()+ne.getOrbitRadius()) {
						ok = false;
						break;
					}
			}
			étoiles.add(ne);
		}
		return étoiles.get(étoiles.size()-1);
	}

	public void avancer(double nb) {
		if(étoiles.size()<1)
			return;

		for(Star e : étoiles)
			e.move(nb);
	}

	@Override
	public String toString()
	{
		String s = "";
		for(Star e : étoiles) {
			s+=e+"\n";
			for(int i = 0; i<e.planet.size(); i++) {
				s+="\t"+e.planet.get(i)+"\n";

				for(int j = 0; j<e.planet.get(i).satellites.size(); j++)
					s+="\t\t"+e.planet.get(i).satellites.get(j)+"\n";
			}
		}

		return s;
	}

	public ArrayList<Star> getEtoiles() {
		return new ArrayList<>(étoiles);
	}
	public ArrayList<Vaisseau> getVaisseaux() {
		return new ArrayList<>(vaisseaux);
	}

	public double[] getRect() {
		if(étoiles.size()<1)
			return new double[]{-1, -1, 2 , 2};

		//	Obtenir les coordonnées min et max de la map
		Star e0 = getEtoiles().get(0);
		double hgx = e0.getCoord().getX()-e0.getSize();
		double hgy = e0.getCoord().getY()-e0.getSize();
		double bdx = e0.getCoord().getX()+e0.getSize();
		double bdy = e0.getCoord().getY()+e0.getSize();

		for(Star e : getEtoiles()) {
			double taille = e.getOrbitRadius();

			double hgx2 = e.getCoord().getX()-taille;
			double hgy2 = e.getCoord().getY()-taille;
			double bdx2 = e.getCoord().getX()+taille;
			double bdy2 = e.getCoord().getY()+taille;
			hgx = hgx>hgx2?hgx2:hgx;
			hgy = hgy>hgy2?hgy2:hgy;
			bdx = bdx<bdx2?bdx2:bdx;
			bdy = bdy<bdy2?bdy2:bdy;
		}
		//System.out.println("hgx:"+hgx+", hgy:"+hgy+", bdx:"+bdx+", bdy:"+bdy+"\n"+toString());

		return new double[]{hgx, hgy, bdx-hgx, bdy-hgy};
	}
}
