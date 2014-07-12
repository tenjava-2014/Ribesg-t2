package fr.ribesg.bukkit.cybercraft;
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
public class Config {

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
	private static final long DEFAULT_INITIAL_POWER = 1_000;

	/**
	 * Initial power for new CyberPlayers and for CyberPlayer respawn.
	 */
	private long initialPower;

	/**
	 * Builds a Config.
	 *
	 * @param plugin the plugin
	 */
	public Config(final CyberCraft plugin) {
		this.configPath = Paths.get(plugin.getDataFolder().getAbsolutePath(), "config.yml");
		this.initialPower = Config.DEFAULT_INITIAL_POWER;
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
		this.initialPower = config.getLong("initialPower", Config.DEFAULT_INITIAL_POWER);
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
		return "initialPower: " + this.initialPower;
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
