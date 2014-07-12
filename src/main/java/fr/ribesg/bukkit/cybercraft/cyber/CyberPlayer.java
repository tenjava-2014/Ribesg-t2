package fr.ribesg.bukkit.cybercraft.cyber;
import fr.ribesg.bukkit.cybercraft.CyberCraft;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

/**
 * Represents a CyberPlayer
 */
public class CyberPlayer {

	/**
	 * CyberPlayer's UUID
	 */
	private final UUID playerId;

	/**
	 * CyberPlayer's last known name
	 */
	private final String playerName;

	/**
	 * CyberPlayer's power
	 */
	private double power;

	/**
	 * CyberPlayer's scoreboard
	 */
	private final Scoreboard scoreboard;

	/**
	 * Builds a CyberPlayer from a Player and an initial Power.
	 *
	 * @param player       the player
	 * @param initialPower the initial power
	 */
	public CyberPlayer(final Player player, final double initialPower) {
		this(player.getUniqueId(), player.getName(), initialPower);
	}

	/**
	 * Builds a CyberPlayer from its base data, usually from saved state.
	 *
	 * @param id    the player's UUID
	 * @param name  the player's name
	 * @param power the player's power level
	 */
	public CyberPlayer(final UUID id, final String name, final double power) {
		this.playerId = id;
		this.playerName = name;
		this.power = power;
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		final Objective powerObj = scoreboard.registerNewObjective(CyberCraft.POWER_OBJECTIVE, "dummy");
		powerObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		powerObj.getScore(name).setScore((int) power);
	}

	/**
	 * Recharges this CyberPlayer.
	 *
	 * @param amount the amount of power to add
	 */
	public void recharge(final double amount) {
		this.power += amount;
		this.updateScoreboard();
	}

	/**
	 * Discharges this CyberPlayer.
	 *
	 * @param amount the amount of power to remove
	 */
	public void discharge(final double amount) {
		if (this.power > 0) {
			this.power -= amount;
			if (this.power < 0) {
				this.power = 0;
				noMorePower();
			}
			this.updateScoreboard();
		}
	}

	/**
	 * Handles mostly-inevitable death. Called when the Player no longer has power.
	 */
	public void noMorePower() {
		// TODO Handle Player slow death
	}

	private void updateScoreboard() {
		this.scoreboard.getObjective(CyberCraft.POWER_OBJECTIVE).getScore(this.playerName).setScore((int) this.power);
	}

	/**
	 * Gets the Player's UUID.
	 *
	 * @return the Player's UUID
	 */
	public UUID getPlayerId() {
		return this.playerId;
	}

	/**
	 * Gets the last known Player's name.
	 *
	 * @return the last known Player's name
	 */
	public String getPlayerName() {
		return this.playerName;
	}

	/**
	 * Gets the Player's power level.
	 *
	 * @return the Player's power level
	 */
	public double getPower() {
		return this.power;
	}

	/**
	 * Gets the Player's scoreboard.
	 *
	 * @return the Player's scoreboard
	 */
	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}
}
