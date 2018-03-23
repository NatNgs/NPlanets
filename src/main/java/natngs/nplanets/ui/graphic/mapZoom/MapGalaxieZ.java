package natngs.nplanets.ui.graphic.mapZoom;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

import natngs.nplanets.ui.plateauGeneral.Plateau;

public class MapGalaxieZ extends JPanel {
	private static final long serialVersionUID = 8243341363124299036L;
	private Plateau pp;

	public MapGalaxieZ(Plateau p) {
		super();
		setBackground(Color.BLACK);
		pp = p;

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if(!arg0.isMetaDown())	//	si clic gauche
					return;

				//	Au drag&drop clic droit

			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {

				//	MOUVEMENT DE ROULETTE	//
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//	AU DECLIC	//
			}
		});

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if(pp==null)
			return;

		Graphics2D g2d = (Graphics2D)g;
		RenderingHints rhb = g2d.getRenderingHints();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// GROS BORDEL //

		g2d.setRenderingHints(rhb);
	}

	private double[] getCoordOnScreen(int xOnMap, int yOnMap) {


		return null;
	}
}
