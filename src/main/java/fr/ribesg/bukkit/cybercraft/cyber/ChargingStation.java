package fr.ribesg.bukkit.cybercraft.cyber;
import fr.ribesg.bukkit.cybercraft.util.BlockLocation;
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
		final Block topBlock = baseLocation.add(0, 3, 0).getBlock();
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
	private final BlockLocation baseLocation;

	/**
	 * The Charging Station's top Location
	 */
	private final BlockLocation topLocation;

	/**
	 * This Charging Station's power level
	 */
	private double powerLevel;

	/**
	 * Builds a Charging Station from its base Location.
	 *
	 * @param baseLocation the base Location of the Charging Station
	 */
	public ChargingStation(final Location baseLocation) {
		if (!ChargingStation.isValid(baseLocation)) {
			throw new IllegalArgumentException("Invalid Charging Station at " + baseLocation.toString());
		}
		this.baseLocation = new BlockLocation(baseLocation);
		this.topLocation = new BlockLocation(baseLocation.add(0, 3, 0));
		this.powerLevel = 0d;
	}

	/**
	 * Gets the power level available in this Charging Station.
	 *
	 * @return the power level available in this Charging Station
	 */
	public double getPowerLevel() {
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
	 *
	 * @return true if this Charging Station charged the CyberPlayer, false
	 * otherwise
	 */
	public boolean charge(final CyberPlayer player) {
		return false; // TODO
	}

	/**
	 * Gets the Charging Station's base Location.
	 *
	 * @return the Charging Station's base Location
	 */
	public BlockLocation getBaseLocation() {
		return this.baseLocation;
	}

	/**
	 * Gets the Charging Station's top Location.
	 *
	 * @return the Charging Station's top Location
	 */
	public BlockLocation getTopLocation() {
		return this.topLocation;
	}
}
