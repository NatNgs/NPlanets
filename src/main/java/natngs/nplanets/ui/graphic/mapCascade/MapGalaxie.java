package natngs.nplanets.ui.graphic.mapCascade;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import natngs.nplanets.ui.plateauGeneral.*;

public class MapGalaxie extends JPanel {
	private static final long serialVersionUID = 8243341363124299036L;
	private Plateau pp;
	private ArrayList<Vaisseau> v;
	double pixSize;
	double[] r;

	public MapGalaxie(Plateau p, ArrayList<Vaisseau> v) {
		this.v = v;
		setBackground(Color.BLACK);
		pp = p;
		refresh();
	}

	private double[] getCoordOnScreen(double xOnMap, double yOnMap) {
		return new double[]{ (xOnMap-r[0])*pixSize, (yOnMap-r[1])*pixSize };
	}

	private void refresh() {
		r = pp.getRect();	//	r[0]x	r[1]y	r[2]width	r[3]height
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if(pp==null)
			return;

		Graphics2D g2d = (Graphics2D)g;
		RenderingHints rhb = g2d.getRenderingHints();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		//	recalculer le coef (size dans le jeu d'un pixel à l'écran)
		pixSize = 1/Math.max(r[3]/getHeight(), r[2]/getWidth());

		//	et maintenant on affiche les planet une a une
		for(Star e : pp.getEtoiles()) {
			double[] cs = getCoordOnScreen(e.getCoord().getX(), e.getCoord().getY());
			double taille = e.getSize()*pixSize<5?5:e.getSize()*pixSize;
			g2d.setColor(Color.YELLOW);
			fillCircle(g2d, cs[0], cs[1], taille);


			for(Planet p : e.getPlanets()) {
				cs = getCoordOnScreen(p.getCoord().getX(), p.getCoord().getY());
				taille = p.getTaille()*pixSize<1?1:p.getTaille()*pixSize;
				g2d.setColor(Color.LIGHT_GRAY);
				fillCircle(g2d, cs[0], cs[1], taille);
			}

			double zo = e.getOrbitRadius();
			cs = getCoordOnScreen(e.getCoord().getX()-zo, e.getCoord().getY()-zo);
			g2d.setColor(new Color(255, 255, 255, 25));
			g2d.drawOval((int)cs[0], (int)cs[1], (int)(2*zo*pixSize), (int)(2*zo*pixSize));
		}

		//	affichage des vaisseaux
		for(Vaisseau v : this.v) {
			if(v.getDistanceRestante() < 2*pixSize)
				continue;
			g2d.setColor(new Color(255, 0, 0));
			g2d.drawLine((int)v.getCoords()[0].getX(), (int)v.getCoords()[0].getY(),
					(int)v.getCoords()[1].getX(), (int)v.getCoords()[1].getY());
		}

		//	tracer une échelle
		g2d.setColor(Color.WHITE);
		g2d.drawLine(getWidth()-110, getHeight()-10, getWidth()-10, getHeight()-10);
		g2d.drawLine(getWidth()-110, getHeight()-13, getWidth()-110, getHeight()-7);
		g2d.drawLine(getWidth()-10, getHeight()-13, getWidth()-10, getHeight()-7);

		//	écrire la valeur de l'échelle (size de 100px de l'écran dans le jeu)
		String s = String.format("%,.0f u.", 100/pixSize);
		FontMetrics fm   = g.getFontMetrics(g.getFont());
		Rectangle2D rect = fm.getStringBounds(s, g2d);
		g2d.drawString(s, (float)(getWidth()-60-rect.getWidth()/2), getHeight()-12);


		g2d.setRenderingHints(rhb);
	}

	private void fillCircle(Graphics2D g2d, double cx, double cy, double r) {
		g2d.fillOval((int)(cx-r/2), (int)(cy-r/2), (int)r, (int)r);
	}


}
