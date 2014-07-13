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

public abstract class AbstractConfig {

	/**
	 * Charset used to write configuration.
	 */
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	/**
	 * The plugin instance
	 */
	protected final CyberCraft plugin;

	/**
	 * Configuration file path.
	 */
	protected final Path configPath;

	/**
	 * Builds a CyberConfig.
	 *
	 * @param instance the plugin instance
	 * @param fileName the file name
	 */
	protected AbstractConfig(final CyberCraft instance, final String fileName) {
		this.plugin = instance;
		this.configPath = Paths.get(this.plugin.getDataFolder().getAbsolutePath(), fileName);
	}

	/**
	 * Loads the configuration.
	 */
	public void load() {
		this.checkFile(false);

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
	protected abstract void read(final YamlConfiguration config);

	/**
	 * Saves the configuration.
	 */
	public void save() {
		this.checkFile(true);

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
	protected abstract String write();

	/**
	 * Checks that the configuration file and all parent folders exists.
	 * Creates them if they don't.
	 */
	private void checkFile(final boolean whileSaving) {
		if (!Files.exists(this.configPath)) {
			try {
				if (!Files.exists(this.configPath.toFile().getParentFile().toPath())) {
					if (!this.configPath.toFile().getParentFile().mkdirs()) {
						throw new IllegalStateException("Unable to create configuration file!");
					}
				}
				Files.createFile(this.configPath);
				if (!whileSaving) {
					save();
				}
			} catch (final Throwable e) {
				throw new RuntimeException("Problem in configuration handling", e);
			}
		}
	}
}
