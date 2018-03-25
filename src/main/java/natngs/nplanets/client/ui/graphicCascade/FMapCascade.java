package natngs.nplanets.client.ui.graphicCascade;

import natngs.nplanets.client.model.Planet;
import natngs.nplanets.client.model.Plateau;
import natngs.nplanets.client.model.Star;
import natngs.nplanets.client.model.Vaisseau;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class FMapCascade extends JFrame {
	private static final long serialVersionUID = 4837381005071760790L;
	private static double t = 0;
	private JPanel contentPane;
	private JTabbedPane tpEtoiles;
	private JTabbedPane tpPlanetes;
	private JTabbedPane tabbedPane;

	private ArrayList<Vaisseau> v;

	public static void main(String[] args) {
		final ArrayList<Vaisseau> vaisseaux = new ArrayList<>();

		FMapCascade frame = new FMapCascade(vaisseaux);
		frame.setVisible(true);

		final Plateau p = new Plateau();

		for (int i = 0; i < (int)(Math.random() * 9 + 1); i++)
			p.addEtoile();

		frame.setPlateau(p);

		new Thread(()->{
			try {
				while (true) {
					Thread.sleep(500);

					if (p.getEtoiles().size() > 0) {
						Vaisseau v = null;
						//	Choix de la source
						Star et = p.getEtoiles().get((int)(Math.random() * p.getEtoiles().size() - 1));

						//	Chercher parmi les étoiles
						int cp = (int)(Math.random() * et.getPlanets().size());
						if (cp == 0) {
							//	renvoyer un vaisseau au hasard parmi ceux déjà envoyés
							if (vaisseaux.size() == 0)
								continue;    //	abandonner

							v = vaisseaux.remove(0);
						} else {
							//	Chercher parmi les planet de l'étoile choisie
							int cs = (int)(Math.random() * et.getPlanets().get(cp).getSatellites().size());
							if (cs == 0)
								//	envoyer un vaisseau de la planète
								v = new Vaisseau(et.getPlanets().get(cp));
							else
								//	envoyer un vaisseau du satélite choisi
								v = new Vaisseau(et.getPlanets().get(cp).getSatellites().get(cs));
						}

						vaisseaux.add(v);
						//	Choix de la destination
						et = p.getEtoiles().get((int)(Math.random() * p.getEtoiles().size() - 1));

						//	Chercher parmi les étoiles
						cp = (int)(Math.random() * et.getPlanets().size());
						if (cp == 0)
							//	cibler l'étoile trouvée
							v.sendTo(et);
						else {
							//	Chercher parmi les planet de l'étoile choisie
							int cs = (int)(Math.random() * et.getPlanets().get(cp).getSatellites().size());
							if (cs == 0)
								//	envoyer un vaisseau de la planète
								v.sendTo(et.getPlanets().get(cp));
							else
								//	envoyer un vaisseau du satélite choisi
								v.sendTo(et.getPlanets().get(cp).getSatellites().get(cs));
						}
						//System.out.println("["+t+"] "+v+" "+v.getTempsRestant());
					}
				}
			} catch (Exception e) {
				System.err.println("STOPED !");
			}
		}).start();

		try {
			while (true) {
				go(p, vaisseaux, frame);
				Thread.sleep(30);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private synchronized static void go(Plateau p, ArrayList<Vaisseau> vaisseaux, JFrame frame) {
		p.avancer(0.01);
		for (Vaisseau v : vaisseaux)
			v.avancer(0.01);
		t += 0.01;
		frame.repaint();
	}

	public FMapCascade(Plateau p, ArrayList<Vaisseau> v) {
		this(v);
		setPlateau(p);
	}

	public FMapCascade(ArrayList<Vaisseau> v) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Map Cascade");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		tabbedPane = new JTabbedPane();
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		this.v = v;
	}

	public void setPlateau(Plateau p) {
		unsetPlateau();    //	défaire le plateau actuellement affiché

		tabbedPane.addTab("Galaxie", new MapGalaxie(p, v));

		for (Star e : p.getEtoiles()) {
			if (e.getPlanets().size() < 1)
				continue;


			if (tpEtoiles == null) {
				tpEtoiles = new JTabbedPane();
				tabbedPane.addTab("Étoiles", tpEtoiles);
			}

			MapEtoile mapEtoile = new MapEtoile(v, e);
			tpEtoiles.addTab("Star " + e.getId(), mapEtoile);

			JTabbedPane tpPlanetesEtoile = null;

			for (Planet pl : e.getPlanets()) {
				//if(pl.getSatellites().size()<1)
				//	continue;

				if (tpPlanetes == null) {
					tpPlanetes = new JTabbedPane();
					tabbedPane.addTab("Planètes", tpPlanetes);
				}
				if (tpPlanetesEtoile == null) {
					tpPlanetesEtoile = new JTabbedPane();
					tpPlanetes.addTab("Star " + e.getId(), tpPlanetesEtoile);
				}

				MapPlanète mapPlanete = new MapPlanète(v, pl);
				tpPlanetesEtoile.addTab("Planète " + pl.getId(), mapPlanete);
			}
		}
	}

	public void unsetPlateau() {
		tabbedPane.removeAll();
		tpEtoiles = null;
		tpPlanetes = null;
	}
}
