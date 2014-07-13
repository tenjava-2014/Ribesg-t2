package fr.ribesg.bukkit.cybercraft;
import fr.ribesg.bukkit.cybercraft.cyber.CyberPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Handles various perks.
 */
public class CyberPerks implements Listener {

	/**
	 * The plugin's instance
	 */
	private final CyberCraft plugin;

	/**
	 * Set of players which activated the Attack perk
	 */
	private final Set<UUID> attackPerkUsers;

	/**
	 * Set of players which activated the Defense perk
	 */
	private final Set<UUID> defensePerkUsers;

	/**
	 * Set of players which activated the Fly perk
	 */
	private final Set<UUID> flyPerkUsers;

	/**
	 * Set of players which are actually flying
	 */
	private final Set<UUID> flyingUsers;

	/**
	 * Builds the CyberPerks handler.
	 *
	 * @param instance the plugin instance
	 */
	public CyberPerks(final CyberCraft instance) {
		this.plugin = instance;
		this.attackPerkUsers = new HashSet<>();
		this.defensePerkUsers = new HashSet<>();
		this.flyPerkUsers = new HashSet<>();
		this.flyingUsers = new HashSet<>();
		Bukkit.getScheduler().runTaskTimer(this.plugin, () -> this.flyingUsers.forEach(id -> {
			final CyberPlayer player = this.plugin.getPlayers().get(id);
			player.discharge(this.plugin.getPluginConfig().getFlyPerkCost());
		}), 15L, this.plugin.getPluginConfig().getFlyPerkInterval());
		Bukkit.getPluginManager().registerEvents(this, this.plugin);
	}

	// ################# //
	// ## Attack Perk ## //
	// ################# //

	/**
	 * Gets the set of players which activated the Attack perk.
	 *
	 * @return the set of players which activated the Attack perk
	 */
	public Set<UUID> getAttackPerkUsers() {
		return this.attackPerkUsers;
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerAttack(final EntityDamageByEntityEvent event) {
		if (event.getDamager().getType() == EntityType.PLAYER) {
			final Player player = (Player) event.getDamager();
			if (this.attackPerkUsers.contains(player.getUniqueId())) {
				final CyberPlayer cyberPlayer = this.plugin.getPlayers().get(player.getUniqueId());
				if (cyberPlayer.getPower() > this.plugin.getPluginConfig().getAttackPerkCost()) {
					cyberPlayer.discharge(this.plugin.getPluginConfig().getAttackPerkCost());
					event.setDamage(event.getDamage() * this.plugin.getPluginConfig().getAttackPerkMultiplier());
				}
			}
		}
	}

	// ################## //
	// ## Defense Perk ## //
	// ################## //

	/**
	 * Gets the set of players which activated the Defense perk.
	 *
	 * @return the set of players which activated the Defense perk
	 */
	public Set<UUID> getDefensePerkUsers() {
		return this.defensePerkUsers;
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerDamageByEntity(final EntityDamageByEntityEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			final Player player = (Player) event.getEntity();
			if (this.defensePerkUsers.contains(player.getUniqueId())) {
				final CyberPlayer cyberPlayer = this.plugin.getPlayers().get(player.getUniqueId());
				if (cyberPlayer.getPower() > this.plugin.getPluginConfig().getDefensePerkCost()) {
					cyberPlayer.discharge(this.plugin.getPluginConfig().getDefensePerkCost());
					event.setDamage(event.getDamage() * this.plugin.getPluginConfig().getDefensePerkMultiplier());
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerDamage(final EntityDamageEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			final Player player = (Player) event.getEntity();
			if (this.defensePerkUsers.contains(player.getUniqueId())) {
				final CyberPlayer cyberPlayer = this.plugin.getPlayers().get(player.getUniqueId());
				if (cyberPlayer.getPower() > this.plugin.getPluginConfig().getDefensePerkCost()) {
					cyberPlayer.discharge(this.plugin.getPluginConfig().getDefensePerkCost());
					event.setDamage(event.getDamage() * this.plugin.getPluginConfig().getDefensePerkMultiplier());
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerDamageByBlock(final EntityDamageByBlockEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			final Player player = (Player) event.getEntity();
			if (this.defensePerkUsers.contains(player.getUniqueId())) {
				final CyberPlayer cyberPlayer = this.plugin.getPlayers().get(player.getUniqueId());
				if (cyberPlayer.getPower() > this.plugin.getPluginConfig().getDefensePerkCost()) {
					cyberPlayer.discharge(this.plugin.getPluginConfig().getDefensePerkCost());
					event.setDamage(event.getDamage() * this.plugin.getPluginConfig().getDefensePerkMultiplier());
				}
			}
		}
	}

	// ############## //
	// ## Fly Perk ## //
	// ############## //

	/**
	 * Gets the set of players which activated the Fly perk.
	 *
	 * @return the set of players which activated the Fly perk
	 */
	public Set<UUID> getFlyPerkUsers() {
		return this.flyPerkUsers;
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerToggleFlight(final PlayerToggleFlightEvent event) {
		final UUID id = event.getPlayer().getUniqueId();
		if (this.flyPerkUsers.contains(id)) {
			if (event.isFlying()) {
				this.flyingUsers.add(id);
			} else {
				this.flyingUsers.remove(id);
			}
		}
	}
}
