package fr.ribesg.bukkit.cybercraft;
import fr.ribesg.bukkit.cybercraft.cyber.ChargingStation;
import fr.ribesg.bukkit.cybercraft.util.BlockLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
			// TODO Send some message explaining wth is happening
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
			final Location loc = block.getLocation();
			if (ChargingStation.isValid(loc)) {
				this.plugin.createChargingStation(loc);
			} else if (ChargingStation.isValid(loc.subtract(0, 2, 0))) {
				this.plugin.createChargingStation(loc.subtract(0, 2, 0));
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
}
