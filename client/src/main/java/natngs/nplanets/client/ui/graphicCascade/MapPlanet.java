package natngs.nplanets.client.ui.graphicCascade;

import natngs.nplanets.client.model.Coord;
import natngs.nplanets.client.model.Planet;
import natngs.nplanets.client.model.Vaisseau;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MapPlanet extends JPanel {
	private static final long serialVersionUID = 8243341363124299036L;
	private ArrayList<Vaisseau> v;
	private Planet pl;
	double coef;
	double[] r;

	public MapPlanet(ArrayList<Vaisseau> v, Planet pl) {
		this.v = v;
		this.pl = pl;
		setBackground(Color.BLACK);
	}

	private double[] getCoordOnScreen(double xOnMap, double yOnMap) {
		return new double[]{(xOnMap - r[0]) * coef, (yOnMap - r[1]) * coef};
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (pl == null)
			return;

		Graphics2D g2d = (Graphics2D)g;
		RenderingHints rhb = g2d.getRenderingHints();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		//	si r1>re, on choisis le coef de size par rapport a la hauteur; sinon par rapport a la largeur
		r = pl.getRect();
		coef = 1 / Math.max(r[3] / getHeight(), r[2] / getWidth());

		//	Affichage de la planète
		double[] cs = getCoordOnScreen(pl.getCoord().getX(), pl.getCoord().getY());
		double taille = pl.getTaille() * coef < 9 ? 9 : pl.getTaille() * coef;
		//g2d.setColor(Color.LIGHT_GRAY);

		Paint p = g2d.getPaint();
		Coord c1 = new Coord(pl.getTaille() / 2, pl.getCoord().getAngle(), pl);
		Coord c2 = new Coord(pl.getTaille() / 2, pl.getCoord().getAngle() + Math.PI, pl);
		double[] d1 = getCoordOnScreen(c1.getX(), c1.getY());
		double[] d2 = getCoordOnScreen(c2.getX(), c2.getY());
		g2d.setPaint(new GradientPaint((float)d2[0], (float)d2[1], Color.WHITE, (float)d1[0], (float)d1[1], Color.DARK_GRAY));
		fillCircle(g2d, cs[0], cs[1], taille);
		g2d.setPaint(p);

		double zo = pl.getCoord().getRayon();
		g2d.setColor(new Color(255, 255, 255, 10));
		cs = getCoordOnScreen(pl.getCoord().getReference().getCoord().getX() - zo, pl.getCoord().getReference().getCoord().getY() - zo);
		drawCircle(g2d, cs[0], cs[1], 2 * zo * coef);


		//	et maintenant on affiche les satellites un a un
		for (Planet s : pl.getSatellites()) {
			cs = getCoordOnScreen(s.getCoord().getX(), s.getCoord().getY());
			taille = s.getTaille() * coef < 5 ? 5 : s.getTaille() * coef;

			p = g2d.getPaint();
			c1 = new Coord(s.getTaille() / 2, pl.getCoord().getAngle(), s);
			c2 = new Coord(s.getTaille() / 2, pl.getCoord().getAngle() + Math.PI, s);
			d1 = getCoordOnScreen(c1.getX(), c1.getY());
			d2 = getCoordOnScreen(c2.getX(), c2.getY());
			g2d.setPaint(new GradientPaint((float)d2[0], (float)d2[1], Color.WHITE, (float)d1[0], (float)d1[1], Color.DARK_GRAY));
			fillCircle(g2d, cs[0], cs[1], taille);
			g2d.setPaint(p);

			zo = s.getCoord().getRayon();
			g2d.setColor(new Color(255, 255, 255, 25));
			g2d.drawOval((int)((pl.getCoord().getX() - zo - r[0]) * coef), (int)((pl.getCoord().getY() - zo - r[1]) * coef), (int)(2 * zo * coef), (int)(2 * zo * coef));
		}

		//	tracer une échelle
		g2d.setColor(Color.WHITE);
		g2d.drawLine(getWidth() - 110, getHeight() - 10, getWidth() - 10, getHeight() - 10);
		g2d.drawLine(getWidth() - 110, getHeight() - 13, getWidth() - 110, getHeight() - 7);
		g2d.drawLine(getWidth() - 10, getHeight() - 13, getWidth() - 10, getHeight() - 7);

		//	écrire la valeur de l'échelle (size de 100px de l'écran dans le jeu)
		String s = String.format("%,.1f u.", 100 / coef);
		FontMetrics fm = g.getFontMetrics(g.getFont());
		Rectangle2D rect = fm.getStringBounds(s, g2d);
		g2d.drawString(s, (float)(getWidth() - 60 - rect.getWidth() / 2), getHeight() - 12);


		g2d.setRenderingHints(rhb);
	}

	private void fillCircle(Graphics2D g2d, double cx, double cy, double r) {
		g2d.fillOval((int)(cx - r / 2), (int)(cy - r / 2), (int)r, (int)r);
	}

	private void drawCircle(Graphics2D g2d, double cx, double cy, double r) {
		g2d.drawOval((int)cx, (int)cy, (int)r, (int)r);
	}
}
