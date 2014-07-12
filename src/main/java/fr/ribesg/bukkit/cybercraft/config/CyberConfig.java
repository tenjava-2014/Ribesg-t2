package fr.ribesg.bukkit.cybercraft.config;
import fr.ribesg.bukkit.cybercraft.CyberCraft;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The plugin's configuration.
 */
public class CyberConfig extends AbstractConfig {

	private static final Logger LOG = Logger.getLogger(CyberConfig.class.getName());

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
	 * Default value for {@link #attackPerkCost}
	 */
	private static final double DEFAULT_ATTACK_PERK_COST = 15D;

	/**
	 * Cost of the Attach perk
	 */
	private double attackPerkCost;

	/**
	 * Default value for {@link #attackPerkMultiplier}
	 */
	private static final double DEFAULT_ATTACK_PERK_MULITPLIER = 2D;

	/**
	 * Damage multiplier of the Attack perk
	 */
	private double attackPerkMultiplier;

	/**
	 * Default value for {@link #defensePerkCost}
	 */
	private static final double DEFAULT_DEFENSE_PERK_COST = 10D;

	/**
	 * Cost of the Defense perk
	 */
	private double defensePerkCost;

	/**
	 * Default value for {@link #defensePerkMultiplier}
	 */
	private static final double DEFAULT_DEFENSE_PERK_MULTIPLIER = 0.5D;

	/**
	 * Damage multiplier of the Defense perk
	 */
	private double defensePerkMultiplier;

	/**
	 * Default value for {@link #flyPerkCost}
	 */
	private static final double DEFAULT_FLY_PERK_COST = 5D;

	/**
	 * Cost of the Fly perk
	 */
	private double flyPerkCost;

	/**
	 * Default value for {@link #flyPerkInterval}
	 */
	private static final long DEFAULT_FLY_PERK_INTERVAL = 20L;

	/**
	 * Interval between ech discharge caused by the Fly perk
	 */
	private long flyPerkInterval;

	/**
	 * Default value for {@link #fuelPower}
	 */
	private static final EnumMap<Material, Double> DEFAULT_FUEL_POWER;

	static {
		DEFAULT_FUEL_POWER = new EnumMap<>(Material.class);
		DEFAULT_FUEL_POWER.put(Material.COAL, 100D);
		DEFAULT_FUEL_POWER.put(Material.COAL_BLOCK, 1_000D);
		DEFAULT_FUEL_POWER.put(Material.DIAMOND, 10_000D);
		DEFAULT_FUEL_POWER.put(Material.DIAMOND_BLOCK, 100_000D);
	}

	/**
	 * Map of fuel power provided by fuel materials
	 */
	private EnumMap<Material, Double> fuelPower;

	/**
	 * Builds a CyberConfig.
	 *
	 * @param instance the plugin
	 */
	public CyberConfig(final CyberCraft instance) {
		super(instance, "config.yml");
		this.initialPower = CyberConfig.DEFAULT_INITIAL_POWER;
		this.naturalDecay = CyberConfig.DEFAULT_NATURAL_DECAY;
		this.naturalDecayInterval = CyberConfig.DEFAULT_NATURAL_DECAY_INTERVAL;
		this.chargingInterval = CyberConfig.DEFAULT_CHARGING_INTERVAL;
		this.attackPerkCost = CyberConfig.DEFAULT_ATTACK_PERK_COST;
		this.attackPerkMultiplier = CyberConfig.DEFAULT_ATTACK_PERK_MULITPLIER;
		this.defensePerkCost = CyberConfig.DEFAULT_DEFENSE_PERK_COST;
		this.defensePerkMultiplier = CyberConfig.DEFAULT_DEFENSE_PERK_MULTIPLIER;
		this.flyPerkCost = CyberConfig.DEFAULT_FLY_PERK_COST;
		this.flyPerkInterval = CyberConfig.DEFAULT_FLY_PERK_INTERVAL;
		this.fuelPower = new EnumMap<>(CyberConfig.DEFAULT_FUEL_POWER);
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
	 * Gets the cost of the Attach perk.
	 *
	 * @return the cost of the Attach perk
	 */
	public double getAttackPerkCost() {
		return attackPerkCost;
	}

	/**
	 * Gets the damage multiplier of the Attack perk.
	 *
	 * @return the damage multiplier of the Attack perk
	 */
	public double getAttackPerkMultiplier() {
		return attackPerkMultiplier;
	}

	/**
	 * Gets the cost of the Defense perk.
	 *
	 * @return the cost of the Defense perk
	 */
	public double getDefensePerkCost() {
		return defensePerkCost;
	}

	/**
	 * Gets the damage multiplier of the Defense perk.
	 *
	 * @return the damage multiplier of the Defense perk
	 */
	public double getDefensePerkMultiplier() {
		return defensePerkMultiplier;
	}

	/**
	 * Gets the cost of the Fly perk.
	 *
	 * @return the cost of the Fly perk
	 */
	public double getFlyPerkCost() {
		return flyPerkCost;
	}

	/**
	 * Gets the interval between ech discharge caused by the Fly perk.
	 *
	 * @return the interval between ech discharge caused by the Fly perk
	 */
	public long getFlyPerkInterval() {
		return flyPerkInterval;
	}

	/**
	 * Gets map of fuel power provided by fuel materials.
	 *
	 * @return the map of fuel power provided by fuel materials
	 */
	public Map<Material, Double> getFuelPower() {
		return fuelPower;
	}

	/**
	 * Reads the loaded configuration.
	 *
	 * @param config the loaded configuration
	 */
	@Override
	protected void read(final YamlConfiguration config) {
		this.initialPower = config.getDouble("initialPower", CyberConfig.DEFAULT_INITIAL_POWER);
		this.naturalDecay = config.getDouble("naturalDecay", CyberConfig.DEFAULT_NATURAL_DECAY);
		this.naturalDecayInterval = config.getLong("naturalDecayInterval", CyberConfig.DEFAULT_NATURAL_DECAY_INTERVAL);
		this.chargingInterval = config.getLong("chargingInterval", CyberConfig.DEFAULT_CHARGING_INTERVAL);
		this.attackPerkCost = config.getDouble("attackPerkCost", CyberConfig.DEFAULT_ATTACK_PERK_COST);
		this.attackPerkMultiplier = config.getDouble("attackPerkMultiplier", CyberConfig.DEFAULT_ATTACK_PERK_MULITPLIER);
		this.defensePerkCost = config.getDouble("defensePerkCost", CyberConfig.DEFAULT_DEFENSE_PERK_COST);
		this.defensePerkMultiplier = config.getDouble("defensePerkMultiplier", CyberConfig.DEFAULT_DEFENSE_PERK_MULTIPLIER);
		this.flyPerkCost = config.getDouble("flyPerkCost", CyberConfig.DEFAULT_FLY_PERK_COST);
		this.flyPerkInterval = config.getLong("flyPerkInterval", CyberConfig.DEFAULT_FLY_PERK_INTERVAL);
		if (config.isConfigurationSection("fuelPower")) {
			this.fuelPower.clear();
			final ConfigurationSection fuelPowerSection = config.getConfigurationSection("fuelPower");
			for (final String key : fuelPowerSection.getKeys(false)) {
				final Material m = Material.matchMaterial(key);
				if (m == null) {
					LOG.warning("Unknown materiel '" + key + "', ignored!");
				} else {
					this.fuelPower.put(m, fuelPowerSection.getDouble(key, 1D));
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
		builder.append("# Initial power given to first joining players and respawning players\n");
		builder.append("initialPower: ").append(this.initialPower).append("\n\n");

		builder.append("# Amount of power naturally loss and interval between losses\n");
		builder.append("naturalDecay: ").append(this.naturalDecay).append('\n');
		builder.append("naturalDecayInterval: ").append(this.naturalDecayInterval).append("\n\n");

		builder.append("# Interval between charges when on a Charging Station\n");
		builder.append("chargingInterval: ").append(this.chargingInterval).append("\n\n");

		builder.append("# Cost and damage multiplier of the Attack perk\n");
		builder.append("attackPerkCost: ").append(this.attackPerkCost).append('\n');
		builder.append("attackPerkMultiplier: ").append(this.attackPerkMultiplier).append("\n\n");

		builder.append("# Cost and damage multiplier of the Defense perk\n");
		builder.append("defensePerkCost: ").append(this.defensePerkCost).append('\n');
		builder.append("defensePerkMultiplier: ").append(this.defensePerkMultiplier).append("\n\n");

		builder.append("# Cost and discharge interval of the Fly perk\n");
		builder.append("flyPerkCost: ").append(this.flyPerkCost).append('\n');
		builder.append("flyPerkInterval: ").append(this.flyPerkInterval).append("\n\n");

		builder.append("# Fuel definition. You need to use Bukkit Material enum values here.\n");
		builder.append("fuelPower:\n");
		for (final Map.Entry<Material, Double> e : this.fuelPower.entrySet()) {
			builder.append("  ").append(e.getKey().toString()).append(": ").append(e.getValue().toString()).append('\n');
		}
		builder.append('\n');
		return builder.toString();
	}
}
