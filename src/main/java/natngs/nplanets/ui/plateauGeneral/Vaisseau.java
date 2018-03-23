package natngs.nplanets.ui.plateauGeneral;

public class Vaisseau {

	private static long idnum = 0;
	private String id;
	protected Astre position;	// destination si enMouvement
	protected Coord coord;
	protected Coord destCoord;	//	coordonnées de la destination
	private double vitesseOrigine, vitesse;
	private boolean enMouvement;

	public Vaisseau(Planet position) {
		this.position = position;
		coord = position.coord;
		vitesseOrigine = position.vitesse;
		vitesse = 0;
		enMouvement = false;
		idnum++;
		id = position.getId()+String.format("v%X", idnum);
	}

	public boolean avancer(double nb) {
		if(!enMouvement)
			return false;

		//	temps restant
		double tmps = coord.getDistance(destCoord)/vitesse;

		if(tmps < nb) {
			coord = destCoord;
			enMouvement = false;
			System.err.println("VAISSEAU ARRIVÉ sur "+position);
			return true;
		}

		double coef = nb/tmps;

		double x = coord.getX() + coef*(destCoord.getX()-coord.getX());
		double y = coord.getY() + coef*(destCoord.getY()-coord.getY());
		coord = new CoordC(x, y);

		return false;
	}

	public void sendTo(Astre destination) {
		if(position == destination || enMouvement)
			return;

		//	Vitesse de base
		vitesse = vitesseOrigine;

		//	Vitesse de catapultage (le cas échéant)
		//if(position instanceof Planet)
		//	vitesse = calcVitesse(position.getCoord().getReference().getCoord(), position.getCoord(), destination.getCoord(), vitesseOrigine, position.getVS());

		if(destination instanceof Star)
			destCoord = destination.getCoord();
		else {
			Planet d = (Planet)destination;

			destCoord = d.getCoord();
			double dist, duree, dist2 = destCoord.getDistance(coord);
			do{
				dist = dist2;
				duree = dist/vitesse;
				//System.out.print("duree:"+duree+", distance:"+dist+", vitesse:"+vitesse+", ");

				//	Vitesse de catapultage
				if(position instanceof Planet)
					vitesse = calcVitesse(position.getCoord().getReference().getCoord(), position.getCoord(), d.getFutureCoord(duree), vitesseOrigine, position.getVS());

				destCoord = d.getFutureCoord(duree);
				dist2 = destCoord.getDistance(coord);

				//System.out.println("newVitesse:"+vitesse+", newDistance:"+dist2);
				if(Math.pow(dist-dist2, 2)>1_000_000 || Double.isInfinite(duree) || Double.isNaN(duree) || Double.isNaN(vitesse)) {
					//	Il semble impossible d'arriver à destination
					vitesse = 0;	//	Remise en place des paramètres et annulation du vol
					return;
				}
				//System.out.println("distance:"+Math.pow(dist-dist2, 2));
			}while(Math.pow(dist-dist2, 2)>0.001);

			duree = dist/vitesse;
			System.err.println("\nVAISSEAU ENVOYÉ "+id+", vers "+position.getId()+", durée:"+duree+", vitesse:"+vitesse+", distance:"+coord.getDistance(destCoord));

			position = destination;
			enMouvement = true;
		}
	}

	/**
	 * Calcule la vitesse d'un vaisseau lancé d'une planète (prise en compte de l'inertie)
	 *
	 * @param ref	Coordonnées de l'astre autour duquel tourne la planète de départ
	 * @param depart	Coordonnées de la planète de départ 	/!\ PLANETE OBLIGATOIREMENT /!\
	 * @param destination	Coordonnées de la destination
	 * @param vs	Vitesse de base du vaisseau
	 * @param vit 	vitesse_angulaire_rotation_source*rayon_orbite_source (garder le signe de var)
	 * @return	Vitesse du vaisseau
	 */
	public static double calcVitesse(Coord ref, Coord depart, Coord destination, double vs, double vit) {
		return vit;
		/*double cx = destination.getX(), cy = destination.getY();
		if(Double.isNaN(cy) || Double.isNaN(cy))
			System.err.println("!!! "+destination+" !!!");
		double bx = depart.getX(), by = depart.getY();
		double ax = ref.getX(), ay = ref.getY();

		double d = depart.getDistance(destination);

		double ix = bx, iy = by;	//	I est la coordonnée où nous serions après un temps t=1 si on partait droit en direction de la destination
		if(vs > 0) {
			ix = d*(cx-bx)/vs;
			iy = d*(cy-by)/vs;
		}

		double ex = 0, ey = 0;
		double aide1 = bx*(bx-2*ax), aide2 = by*(by-2*ay), aide3 = aide1+aide2+ay*ay+ax*ax;
		double aide0 = vit*Math.sqrt(aide3);
		if(vit < 0) {
			vit *=-1;
			ex =-((by-ay)*aide0-bx*(aide2+ay*ay+bx*(bx+2*ax)+ax*ax))/aide3;
			ey = ((bx-ax)*aide0+by*aide2+(ay*ay+aide1+ax*ax)*by)/aide3;
		}
		else {
			ex = ((by-ay)*aide0+bx*aide3)/aide3;
			ey =-((bx-ax)*aide0+by*by*(2*ay-by)-(aide1+ay*ay+ax*ax)*by)/aide3;
		}

		System.out.println("vs:"+vs+", vit:"+vit+", A["+ax+", "+ay+"], B["+bx+", "+by+"], C["+cx+", "+cy+"]");
		System.out.println("d:"+d+", I["+ix+", "+iy+"], E["+ex+", "+ey+"], racine:"+String.format("%.2f, end:%.2f", cy*(cy-2*by)+by*by+cx*cx+bx*(bx-2*bx*cx), 2*(cx-bx)*(cy-by)));
		double res = Math.sqrt(cy*(cy-2*by)+by*by+cx*cx+bx*(bx-2*bx*cx)) * ((cx-bx)*(iy+ey)+(ix+ex-2*bx)*cy+(-ix-ex-2*cx+4*bx)*by) / (2*(cx-bx)*(cy-by));
		System.out.println("\t\tRetour: "+res+"\n");
		return res;
		*/
	}

	@Override
	public String toString() {
		return id;
	}

	public double getTempsRestant() {
		if(!enMouvement || vitesse==0)
			return 0;
		return coord.getDistance(destCoord)/vitesse;
	}

	public double getDistanceRestante() {
		if(!enMouvement)
			return 0;
		return coord.getDistance(destCoord);
	}

	public Coord[] getCoords() {
		return new Coord[]{coord, destCoord};
	}
}
