package core;

/**
 * 
 * @author Nathaël Noguès
 * 
 */
public abstract class Element {
	private Coord c;
	private double size;

	public Element(Coord c, double size) {
		this.c = c;
		this.size = size;
	}

	public boolean tooNearFrom(Element e) {
		return c.getDistance(e.c) > (size + e.size) * (size + e.size);
	}

}
