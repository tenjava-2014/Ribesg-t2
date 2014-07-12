package fr.ribesg.bukkit.cybercraft.config;
import fr.ribesg.bukkit.cybercraft.CyberCraft;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * The plugin's configuration.
 */
public class CyberConfig {

	/**
	 * Charset used to write configuration.
	 */
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	/**
	 * Configuration file path.
	 */
	private final Path configPath;

	/**
	 * Default value for {@link #initialPower}.
	 */
	private static final double DEFAULT_INITIAL_POWER = 1_000D;

	/**
	 * Initial power for new CyberPlayers and for respawning CyberPlayers.
	 */
	private double initialPower;

	/**
	 * Default value for {@link #naturalDecay}
	 */
	private static final double DEFAULT_NATURAL_DECAY = 1D;

	/**
	 * Amount of power naturally lost with time
	 */
	private double naturalDecay;

	/**
	 * Default value for {@link #naturalDecayInterval}
	 */
	private static final long DEFAULT_NATURAL_DECAY_INTERVAL = 5L;

	/**
	 * Amount of ticks between each natural power loss
	 */
	private long naturalDecayInterval;

	/**
	 * Default value for {@link #chargingInterval}
	 */
	private static final long DEFAULT_CHARGING_INTERVAL = 20L;

	/**
	 * Amount of ticks between each charging action
	 */
	private long chargingInterval;

	/**
	 * Builds a CyberConfig.
	 *
	 * @param plugin the plugin
	 */
	public CyberConfig(final CyberCraft plugin) {
		this.configPath = Paths.get(plugin.getDataFolder().getAbsolutePath(), "config.yml");
		this.initialPower = CyberConfig.DEFAULT_INITIAL_POWER;
		this.naturalDecay = CyberConfig.DEFAULT_NATURAL_DECAY;
		this.naturalDecayInterval = CyberConfig.DEFAULT_NATURAL_DECAY_INTERVAL;
		this.chargingInterval = CyberConfig.DEFAULT_CHARGING_INTERVAL;
	}

	/**
	 * Gets the initial power for new CyberPlayers and for respawning
	 * CyberPlayers.
	 *
	 * @return the initial power for new CyberPlayers and for respawning
	 * CyberPlayers
	 */
	public double getInitialPower() {
		return this.initialPower;
	}

	/**
	 * Gets the amount of power naturally lost with time.
	 *
	 * @return the amount of power naturally lost with time
	 */
	public double getNaturalDecay() {
		return this.naturalDecay;
	}

	/**
	 * Gets the amount of ticks between each natural power loss.
	 *
	 * @return the amount of ticks between each natural power loss
	 */
	public long getNaturalDecayInterval() {
		return this.naturalDecayInterval;
	}

	/**
	 * Gets the amount of ticks between each charging action.
	 *
	 * @return the amount of ticks between each charging action
	 */
	public long getChargingInterval() {
		return chargingInterval;
	}

	/**
	 * Loads the configuration.
	 */
	public void load() {
		this.checkFile();

		final YamlConfiguration config = new YamlConfiguration();
		try {
			try (BufferedReader reader = Files.newBufferedReader(this.configPath, CHARSET)) {
				final StringBuilder s = new StringBuilder();
				while (reader.ready()) {
					s.append(reader.readLine()).append('\n');
				}
				config.loadFromString(s.toString());
			}
		} catch (final IOException | InvalidConfigurationException e) {
			throw new RuntimeException("Problem in configuration handling", e);
		}

		this.read(config);
	}

	/**
	 * Reads the loaded configuration.
	 *
	 * @param config the loaded configuration
	 */
	private void read(final YamlConfiguration config) {
		this.initialPower = config.getDouble("initialPower", CyberConfig.DEFAULT_INITIAL_POWER);
		this.naturalDecay = config.getDouble("naturalDecay", CyberConfig.DEFAULT_NATURAL_DECAY);
		this.naturalDecayInterval = config.getLong("naturalDecayInterval", CyberConfig.DEFAULT_NATURAL_DECAY_INTERVAL);
		this.chargingInterval = config.getLong("chargingInterval", CyberConfig.DEFAULT_CHARGING_INTERVAL);
	}

	/**
	 * Saves the configuration.
	 */
	public void save() {
		this.checkFile();

		final String configurationContent = this.write();

		try {
			try (BufferedWriter writer = Files.newBufferedWriter(this.configPath, CHARSET, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
				writer.write(configurationContent);
			}
		} catch (final IOException e) {
			throw new RuntimeException("Problem in configuration handling", e);
		}
	}

	/**
	 * Writes the configuration content to a String.
	 *
	 * @return the configuration content as a String
	 */
	private String write() {
		// TODO Documented configuration
		final StringBuilder builder = new StringBuilder();
		builder.append("initialPower: ").append(this.initialPower).append("\n\n");
		builder.append("naturalDecay: ").append(this.naturalDecay).append("\n");
		builder.append("naturalDecayInterval: ").append(this.naturalDecayInterval).append("\n\n");
		builder.append("chargingInterval: ").append(this.chargingInterval).append("\n\n");
		return builder.toString();
	}

	/**
	 * Checks that the configuration file and all parent folders exists.
	 * Creates them if they don't.
	 */
	private void checkFile() {
		if (!Files.exists(this.configPath)) {
			try {
				if (!this.configPath.toFile().getParentFile().mkdirs()) {
					throw new IllegalStateException("Unable to create configuration file!");
				}
				Files.createFile(this.configPath);
			} catch (final Throwable e) {
				throw new RuntimeException("Problem in configuration handling", e);
			}
		}
	}
}
