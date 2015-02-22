package core;

import java.util.HashMap;

import map.Element;
import map.Planet;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public class Universe {
	private HashMap<Coord, Element> elements;
	private double size; // Distance max avec le (0,0,0)

	public void setMap(int width, int size, int nbP) {
		elements = new HashMap<>();
		this.size = size;
		generateMap(nbP);
	}

	private void generateMap(int nbP) {
		elements.clear();

		for(int i=0; i<nbP; i++) {
			Element e = null;
			do
				e = new Planet(new Coord(new double[] { getRandomPosition(),
						getRandomPosition(), getRandomPosition() }),
						Math.random() + 1);
			while (canPlace(e));
		}
	}
	private double getRandomPosition() {
		return (Math.random() * 2 - 1) * size;
	}

	private boolean canPlace(Element elem) {
		for (Element e : elements.values())
			if(elem.tooNearFrom(e))
				return false;
		return true;
	}

}
