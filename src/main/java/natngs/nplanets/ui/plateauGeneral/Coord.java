package natngs.nplanets.ui.plateauGeneral;

public class Coord {
	private double r;
	private double a;
	private Astre ref;

	public Coord(double r, double a) {
		this.r = r;
		this.a = a;
		ref = null;
	}

	public Coord(double rayon, double angle, Astre ref) {
		r = rayon;
		a = angle%(2*Math.PI);
		this.ref = ref;
	}

	public double getX() {
		if(ref==null)
			return r*Math.cos(a);

		return ref.getCoord().getX()+r*Math.cos(a);
	}

	public double getY() {
		if(ref==null)
			return r*Math.sin(a);

		return ref.getCoord().getY()+r*Math.sin(a);
	}

	public double getRayon() {
		return r;
	}
	public double getAngle() {
		return a;
	}
	public Astre getReference() {
		return ref;
	}

	public double getDistance(Coord c) {
		return Math.sqrt(Math.pow(getX()-c.getX(), 2)+Math.pow(getY()-c.getY(), 2));
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Coord))
			return false;

		return ((Coord)o).getDistance(this)==0;
	}

	@Override
	public String toString() {
		return "x:"+(int)getX()+", y:"+(int)getY()+" (r:"+r+", a:"+a+", ref:"+ref.getId()+")";
	}
}
