package fr.ribesg.bukkit.cybercraft;
import fr.ribesg.bukkit.cybercraft.cyber.ChargingStation;
import fr.ribesg.bukkit.cybercraft.util.BlockLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * CyberCraft's Listener.
 */
public class CyberListener implements Listener {

	private final CyberCraft plugin;

	public CyberListener(final CyberCraft instance) {
		this.plugin = instance;
		Bukkit.getPluginManager().registerEvents(this, this.plugin);
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if (!this.plugin.isRegistered(player)) {
			this.plugin.register(player);
		} else {
			this.plugin.link(player);
		}
	}

	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {
		this.plugin.unlink(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerPlaceBlock(final BlockPlaceEvent event) {
		final Block block = event.getBlockPlaced();
		if (block.getType() == Material.DISPENSER) {
			final Location l = block.getLocation();
			for (int i = -3; i <= 3; i += 3) {
				final Location loc = l.clone().add(0, i, 0);
				if (ChargingStation.isValid(loc)) {
					CyberListener.this.plugin.createChargingStation(loc);
					break;
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerBreakBlock(final BlockBreakEvent event) {
		final Block block = event.getBlock();
		if (block.getType() == Material.DISPENSER) {
			final BlockLocation loc = new BlockLocation(block.getLocation());
			if (this.plugin.isChargingStationLocation(loc)) {
				this.plugin.destroyChargingStation(this.plugin.getChargingStation(loc));
			}
		}
	}

	@EventHandler
	public void onPlayerCloseInventory(final InventoryCloseEvent event) {
		final Inventory inv = event.getInventory();
		final InventoryHolder holder = inv.getHolder();
		if (holder instanceof Dispenser) {
			final Dispenser dispenser = (Dispenser) holder;
			final BlockLocation blockLocation = new BlockLocation(dispenser.getLocation());
			final ChargingStation station = this.plugin.getChargingStation(blockLocation);
			if (station != null) {
				station.updatePowerLevel();
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onSignChange(final SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("[Power]")) {
			final Block sign = event.getBlock();
			for (final BlockFace bf : new BlockFace[] {
					BlockFace.NORTH,
					BlockFace.SOUTH,
					BlockFace.EAST,
					BlockFace.WEST
			}) {
				final BlockLocation loc = new BlockLocation(sign.getRelative(bf).getLocation());
				final ChargingStation station = this.plugin.getChargingStation(loc);
				if (station != null) {
					System.out.println("Attaching a sign");
					station.attachSign(new BlockLocation(sign.getLocation()));
					break;
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onFoodLevelChange(final FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerRegainHealth(final EntityRegainHealthEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent event) {
		this.plugin.getPlayers().get(event.getPlayer().getUniqueId()).setPower(this.plugin.getPluginConfig().getInitialPower());
	}
}
