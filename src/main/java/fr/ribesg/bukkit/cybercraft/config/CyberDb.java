package fr.ribesg.bukkit.cybercraft.config;
import fr.ribesg.bukkit.cybercraft.CyberCraft;
import fr.ribesg.bukkit.cybercraft.cyber.ChargingStation;
import fr.ribesg.bukkit.cybercraft.cyber.CyberPlayer;
import fr.ribesg.bukkit.cybercraft.util.BlockLocation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The plugin's configuration.
 */
public class CyberDb extends AbstractConfig {

	private static final Logger LOG = Logger.getLogger(CyberConfig.class.getName());

	/**
	 * Builds a CyberDb.
	 *
	 * @param instance the plugin instance
	 */
	public CyberDb(final CyberCraft instance) {
		super(instance, "db.yml");
	}

	/**
	 * Reads the loaded configuration.
	 *
	 * @param config the loaded configuration
	 */
	@Override
	protected void read(final YamlConfiguration config) {
		if (config.isConfigurationSection("chargingStations")) {
			final ConfigurationSection stationsSection = config.getConfigurationSection("chargingStations");
			for (final String key : stationsSection.getKeys(false)) {
				if (!stationsSection.isConfigurationSection(key)) {
					LOG.warning("Ignored invalid node '" + key + "' in db.yml");
				} else {
					final ConfigurationSection stationSection = stationsSection.getConfigurationSection(key);
					final BlockLocation loc = BlockLocation.fromString(stationSection.getString("location"));
					final ChargingStation station = new ChargingStation(this.plugin, loc);
					if (stationSection.isList("signs")) {
						for (final String signLoc : stationSection.getStringList("signs")) {
							station.getAttachedSigns().add(BlockLocation.fromString(signLoc));
						}
					}
					this.plugin.getStations().put(station.getBaseLocation(), station);
					this.plugin.getStations().put(station.getTopLocation(), station);
				}
			}
		}
		if (config.isConfigurationSection("players")) {
			final ConfigurationSection playersSection = config.getConfigurationSection("players");
			for (final String uuidString : playersSection.getKeys(false)) {
				if (!playersSection.isConfigurationSection(uuidString)) {
					LOG.warning("Ignored invalid node '" + uuidString + "' in db.yml");
				} else {
					final UUID id = UUID.fromString(uuidString);
					final ConfigurationSection playerSection = playersSection.getConfigurationSection(uuidString);
					final String name = playerSection.getString("name");
					final double power = playerSection.getDouble("power");
					final CyberPlayer player = new CyberPlayer(id, name, power);
					this.plugin.getPlayers().put(id, player);
				}
			}
		}
	}

	/**
	 * Writes the configuration content to a String.
	 *
	 * @return the configuration content as a String
	 */
	@Override
	protected String write() {
		final StringBuilder builder = new StringBuilder();

		builder.append("# Please don't touch this file, or very carefully!\n\n");

		builder.append("chargingStations:\n");
		int i = 0;
		for (final ChargingStation station : new HashSet<>(this.plugin.getStations().values())) {
			builder.append("  station").append(i++).append(":\n");
			builder.append("    location: ").append(station.getBaseLocation().toString()).append('\n');
			if (station.getAttachedSigns().size() > 0) {
				builder.append("    signs:\n");
				for (final BlockLocation loc : station.getAttachedSigns()) {
					builder.append("    - ").append(loc.toString()).append('\n');
				}
			}
		}
		builder.append('\n');

		builder.append("players:\n");
		for (final CyberPlayer player : this.plugin.getPlayers().values()) {
			builder.append("  ").append(player.getPlayerId().toString()).append(":\n");
			builder.append("    name: ").append(player.getPlayerName()).append('\n');
			builder.append("    power: ").append(player.getPower()).append('\n');
		}

		return builder.toString();
	}
}
