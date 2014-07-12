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
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (cmd.getName().equals("setpower")) {
			if (sender.isOp() || sender.hasPermission("cybercraft.admin")) {
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
			} else {
				sender.sendMessage(ChatColor.RED + "You're not allowed to do that.");
				return true;
			}
		}
		return false;
	}
}
