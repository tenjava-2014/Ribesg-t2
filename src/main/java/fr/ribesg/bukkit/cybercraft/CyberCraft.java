package fr.ribesg.bukkit.cybercraft;

import fr.ribesg.bukkit.cybercraft.config.CyberConfig;
import fr.ribesg.bukkit.cybercraft.cyber.ChargingStation;
import fr.ribesg.bukkit.cybercraft.cyber.CyberPlayer;
import fr.ribesg.bukkit.cybercraft.task.ChargingTask;
import fr.ribesg.bukkit.cybercraft.task.DecayTask;
import fr.ribesg.bukkit.cybercraft.util.BlockLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CyberCraft extends JavaPlugin {

	public static final String POWER_OBJECTIVE = "Power";

	private CyberConfig config;

	private CyberListener listener;
	private DecayTask     decayTask;
	private ChargingTask  chargingTask;

	private Map<UUID, CyberPlayer>              players;
	private Map<BlockLocation, ChargingStation> stations;

	private Scoreboard adminScoreboard;

	// ################## //
	// ## Main methods ## //
	// ################## //

	@Override
	public void onDisable() {
		// TODO Something, maybe. I don't know.
	}

	@Override
	public void onEnable() {
		this.config = new CyberConfig(this);
		this.config.load();

		this.listener = new CyberListener(this);
		this.decayTask = new DecayTask(this);
		this.chargingTask = new ChargingTask(this);

		this.players = new HashMap<>();
		this.stations = new HashMap<>();

		this.adminScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

		final Objective powerObj = this.adminScoreboard.registerNewObjective(POWER_OBJECTIVE, "dummy");
		powerObj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	// ########################### //
	// ## CyberPlayers handling ## //
	// ########################### //

	public void register(final Player player) {
		if (this.isRegistered(player)) {
			throw new IllegalStateException("Registering a player twice!");
		}
		this.players.put(player.getUniqueId(), new CyberPlayer(player, this.config.getInitialPower()));
		this.link(player);
	}

	public void link(final Player player) {
		final CyberPlayer cyberPlayer = this.players.get(player.getUniqueId());
		final int playerScore = (int) cyberPlayer.getPower();
		final Objective adminPowerObj = this.adminScoreboard.getObjective(POWER_OBJECTIVE);
		adminPowerObj.getScore(player.getName()).setScore(playerScore);
		if (player.isOp() || player.hasPermission("cybercraft.admin")) {
			player.setScoreboard(this.adminScoreboard);
		} else {
			player.setScoreboard(cyberPlayer.getScoreboard());
		}
	}

	public boolean isRegistered(final Player player) {
		return this.players.containsKey(player.getUniqueId());
	}

	public void unlink(final Player player) {
		this.adminScoreboard.resetScores(player.getName());
	}

	// ############################### //
	// ## ChargingStations handling ## //
	// ############################### //

	public void createChargingStation(final Location baseLocation) {
		final ChargingStation station = new ChargingStation(this, baseLocation);
		this.stations.put(station.getBaseLocation(), station);
		this.stations.put(station.getTopLocation(), station);
	}

	public boolean isChargingStationLocation(final BlockLocation anyLocation) {
		return this.stations.containsKey(anyLocation);
	}

	public ChargingStation getChargingStation(final BlockLocation anyLocation) {
		return this.stations.get(anyLocation);
	}

	public void destroyChargingStation(final ChargingStation station) {
		this.stations.remove(station.getBaseLocation());
		this.stations.remove(station.getTopLocation());
	}

	// ############# //
	// ## Getters ## //
	// ############# //

	public CyberConfig getPluginConfig() {
		return this.config;
	}

	public Map<UUID, CyberPlayer> getPlayers() {
		return players;
	}

	public Map<BlockLocation, ChargingStation> getStations() {
		return stations;
	}

	public Scoreboard getAdminScoreboard() {
		return this.adminScoreboard;
	}
}
