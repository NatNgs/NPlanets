package natngs.nplanets.ui.graphic.mapZoom;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import natngs.nplanets.ui.plateauGeneral.Plateau;

public class FMapZoom extends JFrame {
	private static final long serialVersionUID = 4837381005071760790L;
	private JPanel contentPane;

	public static void main(String[] args) {
		FMapZoom frame = new FMapZoom();
		frame.setVisible(true);

		Plateau p = new Plateau();
		for(int i=0; i<(int)(Math.random()*9+1); i++)
			p.addEtoile();

		frame.setPlateau(p);

		try {
			while(true) {
				frame.repaint();
				p.avancer(0.01);
				Thread.sleep(30);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public FMapZoom(Plateau p) {
		this();
		setPlateau(p);
	}
	public FMapZoom() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Map Zomm");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
	}

	public void setPlateau(Plateau p) {
		unsetPlateau();	//	défaire le plateau actuellement affiché

		contentPane.add(new MapGalaxieZ(p));
		validate();
		repaint();
	}
	public void unsetPlateau() {
		contentPane.removeAll();
	}
}
