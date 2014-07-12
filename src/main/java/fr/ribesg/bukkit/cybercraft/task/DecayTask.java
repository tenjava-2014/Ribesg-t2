package fr.ribesg.bukkit.cybercraft.task;
import fr.ribesg.bukkit.cybercraft.CyberCraft;
import org.bukkit.scheduler.BukkitRunnable;

public class DecayTask extends BukkitRunnable {

	private final CyberCraft plugin;

	public DecayTask(final CyberCraft instance) {
		this.plugin = instance;
		this.start();
	}

	public void start() {
		this.runTaskTimer(this.plugin, 20L, this.plugin.getPluginConfig().getNaturalDecayInterval());
	}

	@Override
	public void run() {
		final double naturalDecay = this.plugin.getPluginConfig().getNaturalDecay();
		this.plugin.getPlayers().values().forEach((player) -> player.discharge(naturalDecay));
	}
}
