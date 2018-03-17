package natngs.nplanets.server;

import com.sun.istack.internal.NotNull;
import natngs.nplanets.common.ILocated;
import natngs.nplanets.common.Location;

public abstract class ARelativeLocated implements ILocated {
	private final ILocated ref;

	protected ARelativeLocated(@NotNull ILocated ref) {
		this.ref = ref;
	}

	@Override
	@NotNull
	public final Location getLocation(double when) {
		return new Location(ref.getLocation(when), getRelativeLocation(when));
	}

	protected abstract Location getRelativeLocation(double when);
}
