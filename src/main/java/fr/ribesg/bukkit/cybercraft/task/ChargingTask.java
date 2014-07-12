package fr.ribesg.bukkit.cybercraft.task;
import fr.ribesg.bukkit.cybercraft.CyberCraft;
import fr.ribesg.bukkit.cybercraft.cyber.ChargingStation;
import fr.ribesg.bukkit.cybercraft.util.BlockLocation;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class ChargingTask extends BukkitRunnable {

	private final CyberCraft plugin;

	public ChargingTask(final CyberCraft instance) {
		this.plugin = instance;
		this.start();
	}

	public void start() {
		this.runTaskTimer(this.plugin, 20L, this.plugin.getPluginConfig().getChargingInterval());
	}

	@Override
	public void run() {
		final Set<ChargingStation> stations = new HashSet<>();
		for (final Player player : Bukkit.getOnlinePlayers()) {
			final BlockLocation loc = new BlockLocation(player.getLocation().subtract(0, 1, 0));
			final ChargingStation station = this.plugin.getChargingStation(loc);
			if (station != null) {
				if (station.charge(this.plugin.getPlayers().get(player.getUniqueId()))) {
					stations.add(station);
				}
			}
		}
		for (final ChargingStation station : this.plugin.getStations().values()) {
			final Location a = station.getBaseLocation().toBukkit().add(0.5, 1.5, 0.5);
			final World w = a.getWorld();
			final Location b = a.clone().add(0, 0.5, 0);
			final Location c = b.clone().add(0, 0.5, 0);
			if (stations.contains(station)) {
				w.playEffect(a, Effect.MOBSPAWNER_FLAMES, 4);
				w.playEffect(b, Effect.MOBSPAWNER_FLAMES, 4);
				w.playEffect(c, Effect.MOBSPAWNER_FLAMES, 4);
			} else {
				w.playEffect(a, Effect.SMOKE, 4);
				w.playEffect(b, Effect.SMOKE, 4);
				w.playEffect(c, Effect.SMOKE, 4);
			}
		}
	}

}
