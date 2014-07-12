package fr.ribesg.bukkit.cybercraft.task;
import fr.ribesg.bukkit.cybercraft.CyberCraft;
import fr.ribesg.bukkit.cybercraft.cyber.CyberPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class AdminScoreboardUpdateTask extends BukkitRunnable {

	private final CyberCraft plugin;

	public AdminScoreboardUpdateTask(final CyberCraft instance) {
		this.plugin = instance;
		this.start();
	}

	public void start() {
		this.runTaskTimer(this.plugin, 20L, 20L);
	}

	@Override
	public void run() {
		final Scoreboard scoreboard = this.plugin.getAdminScoreboard();

		// Unregister old Objective
		final Objective oldPowerObj = scoreboard.getObjective(CyberCraft.POWER_OBJECTIVE);
		oldPowerObj.unregister();

		// Create new updated Objective
		final Objective newPowerObj = scoreboard.registerNewObjective(CyberCraft.POWER_OBJECTIVE, "dummy");
		newPowerObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		for (final CyberPlayer cyberPlayer : this.plugin.getPlayers().values()) {
			newPowerObj.getScore(cyberPlayer.getPlayerName()).setScore((int) cyberPlayer.getPower());
		}

		// Update scoreboard for everybody, in case somebody get/lose op or the permission
		for (final Player player : Bukkit.getOnlinePlayers()) {
			if (player.getScoreboard() != scoreboard && (player.isOp() || player.hasPermission("cybercraft.admin"))) {
				player.setScoreboard(scoreboard);
			} else if (player.getScoreboard() == scoreboard && !player.isOp() && !player.hasPermission("cybercraft.admin")) {
				final CyberPlayer cyberPlayer = this.plugin.getPlayers().get(player.getUniqueId());
				player.setScoreboard(cyberPlayer.getScoreboard());
			}
		}
	}
}
