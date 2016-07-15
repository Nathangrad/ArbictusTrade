package com.arbictus.trade;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Trade extends JavaPlugin {

	public static String prefix = "§7[§aArbictus§7] ";

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager()
				.registerEvents(new TradeListener(), this);
		getLogger().info("Enabled");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("trade")) {
			String usage = "§c/trade <Player>";
			if (!(sender instanceof Player)) {
				sender.sendMessage("Console can't use this command");
				return true;
			}
			Player player = (Player) sender;
			if (args.length == 0) {
				player.sendMessage(prefix + usage);
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0].toString());
			if (target == null) {
				player.sendMessage(prefix + usage);
				return true;
			}
			if (target == player) {
				player.sendMessage(prefix + "§cYou may not trade with yourself");
				return true;
			}
			new TradeRequest(player, target);
			return true;
		}
		return false;
	}
}
