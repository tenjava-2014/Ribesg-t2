package fr.ribesg.bukkit.cybercraft;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Dispenser;

/**
 * Represents a Charging Station.
 */
public class ChargingStation {

	/**
	 * Checks if a Charging Station is valid, based on its base Location.
	 *
	 * @param baseLocation the base Location of the Charging Station
	 *
	 * @return true if the Charging Station is valid, false otherwise
	 */
	public static boolean isValid(final Location baseLocation) {
		final Block baseBlock = baseLocation.getBlock();
		final Block topBlock = baseLocation.add(0, 2, 0).getBlock();
		return baseBlock.getType() == Material.DISPENSER &&
		       ((Dispenser) baseBlock.getState().getData()).getFacing() == BlockFace.UP &&
		       topBlock.getType() == Material.DISPENSER &&
		       ((Dispenser) topBlock.getState().getData()).getFacing() == BlockFace.DOWN &&
		       baseBlock.getRelative(BlockFace.UP).getType() == Material.AIR &&
		       topBlock.getRelative(BlockFace.DOWN).getType() == Material.AIR;
	}

	/**
	 * The Charging Station's base Location
	 */
	private final Location baseLocation;

	/**
	 * The Charging Station's top Location
	 */
	private final Location topLocation;

	/**
	 * This Charging Station's power level
	 */
	private long powerLevel;

	/**
	 * Builds a Charging Station from its base Location.
	 *
	 * @param baseLocation the base Location of the Charging Station
	 */
	public ChargingStation(final Location baseLocation) {
		if (!ChargingStation.isValid(baseLocation)) {
			throw new IllegalArgumentException("Invalid Charging Station at " + baseLocation.toString());
		}
		this.baseLocation = baseLocation;
		this.topLocation = baseLocation.add(0, 2, 0);
		this.powerLevel = 0;
	}

	/**
	 * Gets the power level available in this Charging Station.
	 *
	 * @return the power level available in this Charging Station
	 */
	public long getPowerLevel() {
		return this.powerLevel;
	}

	/**
	 * Updates the power level of this Charging Station base on its dispensers content.
	 */
	public void updatePowerLevel() {
		// TODO
	}

	/**
	 * Charges a CyberPlayer with this Charging Station.
	 *
	 * @param player the CyberPlayer to charge
	 */
	public void charge(final CyberPlayer player) {
		// TODO
	}

	/**
	 * Gets the Charging Station's base Location.
	 *
	 * @return the Charging Station's base Location
	 */
	public Location getBaseLocation() {
		return baseLocation;
	}

	/**
	 * Gets the Charging Station's top Location.
	 *
	 * @return the Charging Station's top Location
	 */
	public Location getTopLocation() {
		return topLocation;
	}
}
