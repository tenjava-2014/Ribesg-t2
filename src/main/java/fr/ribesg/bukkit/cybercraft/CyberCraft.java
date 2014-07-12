package fr.ribesg.bukkit.cybercraft;

import org.bukkit.plugin.java.JavaPlugin;

public class CyberCraft extends JavaPlugin {

	private Config config;

	@Override
	public void onDisable() {
		// TODO Something, maybe. I don't know.
	}

	@Override
	public void onEnable() {
		this.config = new Config(this);
		this.config.load();
	}

	public Config getPluginConfig() {
		return config;
	}
}
