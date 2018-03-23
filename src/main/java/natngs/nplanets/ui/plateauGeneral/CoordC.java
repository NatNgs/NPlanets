package natngs.nplanets.ui.plateauGeneral;

public class CoordC extends Coord {

	public CoordC(double x, double y) {
		super(x, y);
	}

	@Override
	public double getX() {
		return getRayon();
	}
	@Override
	public double getY() {
		return getAngle();
	}
}
