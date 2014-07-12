package fr.ribesg.bukkit.cybercraft;
import org.bukkit.entity.Player;

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
	private long power;

	/**
	 * Builds a CyberPlayer from a Player and an initial Power.
	 *
	 * @param player       the player
	 * @param initialPower the initial power
	 */
	public CyberPlayer(final Player player, final long initialPower) {
		this(player.getUniqueId(), player.getName(), initialPower);
	}

	/**
	 * Builds a CyberPlayer from its base data, usually from saved state.
	 *
	 * @param id    the player's UUID
	 * @param name  the player's name
	 * @param power the player's power level
	 */
	public CyberPlayer(final UUID id, final String name, final long power) {
		this.playerId = id;
		this.playerName = name;
		this.power = power;
	}

	/**
	 * Recharges this CyberPlayer.
	 *
	 * @param amount the amount of power to add
	 */
	public void recharge(final int amount) {
		this.power += amount;
	}

	/**
	 * Discharges this CyberPlayer.
	 *
	 * @param amount the amount of power to remove
	 */
	public void discharge(final int amount) {
		this.power -= amount;
		if (this.power < 0) {
			this.power = 0;
			noMorePower();
		}
	}

	/**
	 * Handles mostly-inevitable death. Called when the Player no longer has power.
	 */
	public void noMorePower() {
		// TODO Handle Player slow death
	}

	/**
	 * Gets the Player's UUID.
	 *
	 * @return the Player's UUID
	 */
	public UUID getPlayerId() {
		return playerId;
	}

	/**
	 * Gets the last known Player's name.
	 *
	 * @return the last known Player's name
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Gets the Player's power level.
	 *
	 * @return the Player's power level
	 */
	public long getPower() {
		return power;
	}
}
