package fr.ribesg.bukkit.cybercraft.cyber;
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
	 * Last saved power state. Used for Time Left entry.
	 */
	private double previousPower;

	/**
	 * Last saved power state timestamp. Used for Time Left entry.
	 */
	private long previousPowerTimestamp;

	/**
	 * Time left, in seconds, minutes or hours. Used for Time Left entry.
	 */
	private long timeLeft;

	/**
	 * Time left entry current name. Used for Time Left entry.
	 */
	private String timeLeftName;

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
		this.previousPower = power;
		this.previousPowerTimestamp = System.currentTimeMillis();
		this.timeLeft = 0;
		this.timeLeftName = "Time Left (s):";

		final Objective powerObj = scoreboard.registerNewObjective(name, "dummy");
		powerObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		powerObj.getScore("Power Left:").setScore((int) power);
		powerObj.getScore(this.timeLeftName).setScore((int) this.timeLeft);
	}

	/**
	 * Recharges this CyberPlayer.
	 *
	 * @param amount the amount of power to add
	 */
	public void recharge(final double amount) {
		this.power += amount;
		this.updateScoreboard(true);
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
			}
			this.updateScoreboard(false);
		} else {
			final Player player = Bukkit.getPlayer(this.playerId);
			if (player.getFoodLevel() > 0) {
				player.setFoodLevel(player.getFoodLevel() - 1);
			} else {
				player.setHealth(player.getHealth() - (player.getMaxHealth() / 20D));
			}
		}
	}

	private void updateScoreboard(final boolean forceUpdate) {
		// Handle power left
		this.scoreboard.getObjective(this.playerName).getScore("Power Left:").setScore((int) this.power);

		// Update time left
		final long timeDiff = System.currentTimeMillis() - this.previousPowerTimestamp;
		final double diff = this.previousPower - this.power;
		if (forceUpdate || timeDiff > 2500) {
			final String oldTimeLeftName = this.timeLeftName;
			if (diff > 0) {

				// Compute new time left
				this.timeLeft = (long) (this.power / diff * timeDiff / 1000D);

				// Get new time left value
				final int value;
				if (this.timeLeft < 300) {
					this.timeLeftName = "Time Left (s):";
					value = (int) this.timeLeft;
				} else if (this.timeLeft < 60 * 120) {
					this.timeLeftName = "Time left (m):";
					value = (int) (this.timeLeft / 60);
				} else {
					this.timeLeftName = "Time Left (h):";
					value = (int) (this.timeLeft / (60 * 60));
				}

				// Apply new time left value
				if (!oldTimeLeftName.equals(this.timeLeftName)) {
					this.scoreboard.resetScores(oldTimeLeftName);
				}
				this.scoreboard.getObjective(this.playerName).getScore(this.timeLeftName).setScore(value);
			}
			this.previousPower = this.power;
			this.previousPowerTimestamp = System.currentTimeMillis();
		}
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
	 * Sets the Player's power level.
	 *
	 * @param power the new Player power level
	 */
	public void setPower(double power) {
		this.power = power;
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
