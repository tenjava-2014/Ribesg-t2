package fr.ribesg.bukkit.cybercraft.cyber;
import fr.ribesg.bukkit.cybercraft.CyberCraft;
import fr.ribesg.bukkit.cybercraft.util.BlockLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Represents a Charging Station.
 */
public class ChargingStation {

	private static final DecimalFormat FORMAT;

	static {
		final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		FORMAT = new DecimalFormat("###,###", symbols);
	}

	/**
	 * Checks if a Charging Station is valid, based on its base Location.
	 *
	 * @param baseLocation the base Location of the Charging Station
	 *
	 * @return true if the Charging Station is valid, false otherwise
	 */
	public static boolean isValid(final Location baseLocation) {
		final Block baseBlock = baseLocation.getBlock();
		final Block topBlock = baseLocation.clone().add(0, 3, 0).getBlock();
		return baseBlock.getType() == Material.DISPENSER &&
		       ((org.bukkit.material.Dispenser) baseBlock.getState().getData()).getFacing() == BlockFace.UP &&
		       topBlock.getType() == Material.DISPENSER &&
		       ((org.bukkit.material.Dispenser) topBlock.getState().getData()).getFacing() == BlockFace.DOWN &&
		       baseBlock.getRelative(BlockFace.UP).getType() == Material.AIR &&
		       topBlock.getRelative(BlockFace.DOWN).getType() == Material.AIR;
	}

	/**
	 * The plugin instance
	 */
	private final CyberCraft plugin;

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
	 * Signs attached to this Charging Station
	 */
	private final Set<BlockLocation> attachedSigns;

	/**
	 * Builds a Charging Station from its base Location.
	 *
	 * @param baseLocation the base Location of the Charging Station
	 */
	public ChargingStation(final CyberCraft instance, final Location baseLocation) {
		if (!ChargingStation.isValid(baseLocation)) {
			throw new IllegalArgumentException("Invalid Charging Station at " + baseLocation.toString());
		}
		this.plugin = instance;
		this.baseLocation = new BlockLocation(baseLocation);
		this.topLocation = new BlockLocation(baseLocation.add(0, 3, 0));
		this.attachedSigns = new HashSet<>();
		this.updatePowerLevel();
	}

	/**
	 * Builds a Charging Station from its base BlockLocation.
	 *
	 * @param baseLocation the base BlockLocation of the Charging Station
	 */
	public ChargingStation(final CyberCraft instance, final BlockLocation baseLocation) {
		this(instance, baseLocation.toBukkit());
	}

	/**
	 * Updates the power level of this Charging Station base on its dispensers content.
	 */
	public void updatePowerLevel() {
		final Map<Material, Double> fuelPower = this.plugin.getPluginConfig().getFuelPower();

		double power = 0D;

		final Inventory inv1 = ((Dispenser) this.topLocation.toBukkit().getBlock().getState()).getInventory();
		for (final ItemStack is : inv1) {
			if (is != null && fuelPower.containsKey(is.getType())) {
				power += is.getAmount() * fuelPower.get(is.getType());
			}
		}

		final Inventory inv2 = ((Dispenser) this.baseLocation.toBukkit().getBlock().getState()).getInventory();
		for (final ItemStack is : inv2) {
			if (is != null && fuelPower.containsKey(is.getType())) {
				power += is.getAmount() * fuelPower.get(is.getType());
			}
		}

		this.powerLevel = power;

		this.updateSigns();
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
		if (this.powerLevel > 0) {
			final Inventory inv = ((Dispenser) this.topLocation.toBukkit().getBlock().getState()).getInventory();
			final Map<Material, Double> fuelPower = this.plugin.getPluginConfig().getFuelPower();
			Material m = null;
			for (final ItemStack is : inv) {
				if (is != null && fuelPower.containsKey(is.getType())) {
					m = is.getType();
					break;
				}
			}
			if (m == null) {
				return false;
			} else {
				final int index = inv.first(m);
				ItemStack is = inv.getItem(index);
				is.setAmount(is.getAmount() - 1);
				if (is.getAmount() == 0) {
					is = null;
				}
				inv.setItem(index, is);
				final double power = fuelPower.get(m);
				player.recharge(power);
				this.powerLevel -= power;
				this.updateSigns();
				return true;
			}
		}
		return false;
	}

	/**
	 * Attaches a new Sign to this Charging Station.
	 *
	 * @param loc the new Sign location
	 */
	public void attachSign(final BlockLocation loc) {
		this.attachedSigns.add(loc);
		Bukkit.getScheduler().runTaskLater(this.plugin, this::updateSigns, 5L);
	}

	/**
	 * Updates the signs attached to this Charging Station.
	 */
	public void updateSigns() {
		final Iterator<BlockLocation> it = this.attachedSigns.iterator();
		while (it.hasNext()) {
			final BlockLocation loc = it.next();
			final Block b = Bukkit.getWorld(loc.getWorldName()).getBlockAt(loc.getX(), loc.getY(), loc.getZ());
			if (b.getType() != Material.SIGN_POST && b.getType() != Material.WALL_SIGN) {
				it.remove();
			} else {
				final Sign sign = (Sign) b.getState();
				sign.setLine(0, "" + ChatColor.BLACK + '[' + ChatColor.GREEN + "Power" + ChatColor.BLACK + ']');
				sign.setLine(2, FORMAT.format((int) this.powerLevel));
				sign.update(true);
			}
		}
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

	/**
	 * Gets the power level available in this Charging Station.
	 *
	 * @return the power level available in this Charging Station
	 */
	public double getPowerLevel() {
		return this.powerLevel;
	}

	/**
	 * Gets all signs attached to this Charging Station.
	 *
	 * @return all signs attached to this Charging Station
	 */
	public Set<BlockLocation> getAttachedSigns() {
		return attachedSigns;
	}
}
