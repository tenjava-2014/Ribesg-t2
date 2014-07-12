package fr.ribesg.bukkit.cybercraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CyberExecutor implements CommandExecutor {

	private final CyberCraft plugin;

	public CyberExecutor(final CyberCraft instance) {
		this.plugin = instance;
		this.plugin.getCommand("setpower").setExecutor(this);
		this.plugin.getCommand("perk").setExecutor(this);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		switch (cmd.getName()) {
			case "setpower":
				if (sender.isOp() || sender.hasPermission("cybercraft.admin")) {
					return cmdSetPower(sender, args);
				} else {
					sender.sendMessage(ChatColor.RED + "You're not allowed to do that.");
					return true;
				}
			case "perk":
				return cmdPerk(sender, args);
			default:
				return false;
		}
	}

	private boolean cmdSetPower(final CommandSender sender, final String[] args) {
		if (args.length < 1) {
			return false;
		} else {
			final double value;
			try {
				value = Double.parseDouble(args[args.length - 1]);
			} catch (final NumberFormatException e) {
				return false;
			}
			if (args.length == 1) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "Cannot change power level of non-Player.");
					return true;
				} else {
					final Player player = (Player) sender;
					this.plugin.getPlayers().get(player.getUniqueId()).setPower(value);
					sender.sendMessage(ChatColor.GREEN + "You set your power level to " + value);
					return true;
				}
			} else {
				for (int i = 1; i < args.length - 1; i++) {
					for (final String name : args[i].split(",")) {
						final Player player = Bukkit.getPlayer(name);
						if (player == null) {
							sender.sendMessage(ChatColor.RED + "Player '" + name + "' not found.");
						} else {
							this.plugin.getPlayers().get(player.getUniqueId()).setPower(value);
							sender.sendMessage(ChatColor.GREEN + "Set power level of " + name + " to " + value);
						}
					}
				}
				return true;
			}
		}
	}

	private boolean cmdPerk(final CommandSender sender, final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "A non-Player can't have perks.");
			return true;
		} else if (args.length != 2) {
			return false;
		}
		final Player player = (Player) sender;

		// Parse Perk name
		final String perkName;
		switch (args[0].toLowerCase()) {
			case "attack":
			case "att":
				perkName = "attack";
				break;
			case "defense":
			case "def":
				perkName = "defense";
				break;
			case "fly":
				perkName = "fly";
				break;
			default:
				return false;
		}

		// Parse value
		final boolean value;
		switch (args[1].toLowerCase()) {
			case "enable":
			case "1":
			case "true":
			case "activate":
			case "on":
				value = true;
				break;
			case "disable":
			case "0":
			case "false":
			case "deactivate":
			case "off":
				value = false;
				break;
			default:
				return false;
		}

		// Apply
		switch (perkName) {
			case "attack":
				if (value) {
					if (this.plugin.getPerks().getAttackPerkUsers().add(player.getUniqueId())) {
						player.sendMessage(ChatColor.GREEN + "Attack Perk enabled!");
					} else {
						player.sendMessage(ChatColor.GOLD + "Attack Perk already enabled");
					}
				} else {
					if (this.plugin.getPerks().getAttackPerkUsers().remove(player.getUniqueId())) {
						player.sendMessage(ChatColor.GREEN + "Attack Perk disabled!");
					} else {
						player.sendMessage(ChatColor.GOLD + "Attack Perk already disabled");
					}
				}
				return true;
			case "defense":
				if (value) {
					if (this.plugin.getPerks().getDefensePerkUsers().add(player.getUniqueId())) {
						player.sendMessage(ChatColor.GREEN + "Defense Perk enabled!");
					} else {
						player.sendMessage(ChatColor.GOLD + "Defense Perk already enabled");
					}
				} else {
					if (this.plugin.getPerks().getDefensePerkUsers().remove(player.getUniqueId())) {
						player.sendMessage(ChatColor.GREEN + "Defense Perk disabled!");
					} else {
						player.sendMessage(ChatColor.GOLD + "Defense Perk already disabled");
					}
				}
				return true;
			case "fly":
				if (value) {
					if (this.plugin.getPerks().getFlyPerkUsers().add(player.getUniqueId())) {
						player.sendMessage(ChatColor.GREEN + "Fly Perk enabled!");
						player.setAllowFlight(true);
					} else {
						player.sendMessage(ChatColor.GOLD + "Fly Perk already enabled");
					}
				} else {
					if (this.plugin.getPerks().getFlyPerkUsers().remove(player.getUniqueId())) {
						player.sendMessage(ChatColor.GREEN + "Fly Perk disabled!");
						player.setAllowFlight(false);
					} else {
						player.sendMessage(ChatColor.GOLD + "Fly Perk already disabled");
					}
				}
				return true;
			default:
				return false;
		}
	}
}
