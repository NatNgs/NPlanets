package natngs.nplanets.ui.graphic.mapCascade;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import natngs.nplanets.ui.plateauGeneral.*;

public class MapEtoile extends JPanel {
	private static final long serialVersionUID = 8243341363124299036L;
	private ArrayList<Vaisseau> v;
	private Star e;
	double pixSize;
	double[] r;

	public MapEtoile(ArrayList<Vaisseau> v, Star e) {
		this.v = v;
		this.e = e;
		setBackground(Color.BLACK);
		refresh();
	}

	private double[] getCoordOnScreen(double xOnMap, double yOnMap) {
		return new double[]{ (xOnMap-r[0])*pixSize, (yOnMap-r[1])*pixSize };
	}

	private void refresh() {
		r = e.getBounds();	//	r[0]x	r[1]y	r[2]width	r[3]height
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if(e==null)
			return;

		Graphics2D g2d = (Graphics2D)g;
		RenderingHints rhb = g2d.getRenderingHints();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		//	si r1>re, on choisis le coef de size par rapport a la hauteur; sinon par rapport a la largeur
		pixSize = 1/Math.max(r[3]/getHeight(), r[2]/getWidth());

		//	et maintenant on affiche les planet une a une
		double[] cs = getCoordOnScreen(e.getCoord().getX(), e.getCoord().getY());
		double taille = e.getSize()*pixSize<11?11:e.getSize()*pixSize;
		g2d.setColor(Color.YELLOW);
		fillCircle(g2d, cs[0], cs[1], taille);

		for(Planet p : e.getPlanets()) {
			cs = getCoordOnScreen(p.getCoord().getX(), p.getCoord().getY());
			taille = (int) (p.getTaille()*pixSize<5?5:p.getTaille()*pixSize);
			Paint pt =g2d.getPaint();
			Coord c1 = new Coord(p.getTaille()/2, p.getCoord().getAngle(), p);
			Coord c2 = new Coord(p.getTaille()/2, p.getCoord().getAngle()+Math.PI, p);
			double[] d1 = getCoordOnScreen(c1.getX(), c1.getY());
			double[] d2 = getCoordOnScreen(c2.getX(), c2.getY());
			g2d.setPaint(new GradientPaint((float)d2[0], (float)d2[1], Color.WHITE, (float)d1[0], (float)d1[1], Color.DARK_GRAY));
			fillCircle(g2d, cs[0], cs[1], taille);
			g2d.setPaint(pt);

			for(Planet s : p.getSatellites()) {
				cs = getCoordOnScreen(s.getCoord().getX(), s.getCoord().getY());
				taille = (int) (s.getTaille()*pixSize<1?1:s.getTaille()*pixSize);

				g2d.setColor(Color.LIGHT_GRAY);
				fillCircle(g2d, cs[0], cs[1], taille);
			}

			double zo = p.getRayonZoneOrbite();
			g2d.setColor(new Color(255, 255, 255, 25));
			cs = getCoordOnScreen(p.getCoord().getX()-zo, p.getCoord().getY()-zo);
			drawCircle(g2d, cs[0], cs[1], 2*zo*pixSize);

			zo = p.getCoord().getRayon();
			g2d.setColor(new Color(255, 255, 255, 10));
			cs = getCoordOnScreen(e.getCoord().getX()-zo, e.getCoord().getY()-zo);
			drawCircle(g2d, cs[0], cs[1], 2*zo*pixSize);
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
		String s = String.format("%,.2f u.", 100/pixSize);
		FontMetrics fm   = g.getFontMetrics(g.getFont());
		Rectangle2D rect = fm.getStringBounds(s, g2d);
		g2d.drawString(s, (float)(getWidth()-60-rect.getWidth()/2), getHeight()-12);

		g2d.setRenderingHints(rhb);
	}

	private void fillCircle(Graphics2D g2d, double cx, double cy, double r) {
		g2d.fillOval((int)(cx-r/2), (int)(cy-r/2), (int)r, (int)r);
	}
	private void drawCircle(Graphics2D g2d, double cx, double cy, double r) {
		g2d.drawOval((int)cx, (int)cy, (int)r, (int)r);
	}

}
