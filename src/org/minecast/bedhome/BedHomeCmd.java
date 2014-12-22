package org.minecast.bedhome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedHomeCmd implements CommandExecutor{
	 public static Main plugin;
	 public BedHomeCmd(Main instance)
	  {
	    plugin = instance;
	  }
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
	if (commandLabel.equalsIgnoreCase("bedhome")){
		Player p = (Player) sender;
		if(args.length == 0){
			p.sendMessage(ChatColor.BLUE + "[BH] BedHome version " + plugin.pdf.getVersion() + " by Superior_Slime");
		} 
		else if(args.length == 1){
			if((args[0].equals("reload"))){
				if((p.hasPermission("bedhome.config")) || p.isOp()){
					plugin.reloadConfig();
					p.sendMessage(ChatColor.BLUE + "[BH] Config reloaded!");
				}else{
					p.sendMessage(ChatColor.DARK_RED + (plugin.locale.getString(plugin.locale() + "." + "ERR_NO_PERMS")));
				}
			}else if(args[0].equals("help")){
				p.sendMessage(ChatColor.GREEN + "-----==================================-----");
				p.sendMessage(ChatColor.DARK_AQUA + "/bed                           " + ChatColor.DARK_GRAY + (plugin.locale.getString(plugin.locale() + "." + "HELP_BED")));
				p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "[reload/help]    "
				+ ChatColor.DARK_GRAY + (plugin.locale.getString(plugin.locale() + "." + "HELP_BEDHOME")));
				p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "lookup "  + ChatColor.DARK_AQUA +  (plugin.locale.getString(plugin.locale() + "." + "NAME")) + " " + (plugin.locale.getString(plugin.locale() + "." + "WORLD")) + "    " + ChatColor.DARK_GRAY + (plugin.locale.getString(plugin.locale() + "." + "HELP_LOOKUP")));
				p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "teleport "  + ChatColor.DARK_AQUA +  (plugin.locale.getString(plugin.locale() + "." + "NAME")) + " " + (plugin.locale.getString(plugin.locale() + "." + "WORLD")) + "    " + ChatColor.DARK_GRAY + (plugin.locale.getString(plugin.locale() + "." + "HELP_TELE")));
			}else{
				p.sendMessage(ChatColor.DARK_RED + (plugin.locale.getString(plugin.locale() + "." + "ERR_SYNTAX")));
			}
		
		}else if(args.length > 1){
			if(args.length == 3){
				if(args[0].equals("lookup")){
					if((p.hasPermission("bedhome.lookup")) || p.isOp()){
						try {
							if (plugin.yml.contains(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2])){
								p.sendMessage(ChatColor.BLUE + (plugin.locale.getString(plugin.locale() + "." + "LOOKUP_RESULT")).replace("$player", args[1]));
								double x = (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".x");
								double y = (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".y");
								double z = (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".z");
								int xInt = (int) Math.round(x);
								int yInt = (int) Math.round(y);
								int zInt = (int) Math.round(z);
								p.sendMessage(ChatColor.RED + "X: " + ChatColor.GOLD + Integer.toString(xInt));
								p.sendMessage(ChatColor.RED + "Y: " + ChatColor.GOLD + Integer.toString(yInt));
								p.sendMessage(ChatColor.RED + "Z: " + ChatColor.GOLD + Integer.toString(zInt));
							}else{
								p.sendMessage(ChatColor.DARK_RED + ((plugin.locale.getString(plugin.locale() + "." + "ERR_PLAYER_NO_BED")).replace("$player", ChatColor.stripColor(args[1]))).replace("$world", args[2]));
							}
						} catch (Exception e) {
							p.sendMessage(ChatColor.DARK_RED + (plugin.locale.getString(plugin.locale() + "." + "ERR_BAD_PLAYER")));
						}
					}else{
						p.sendMessage(ChatColor.DARK_RED + (plugin.locale.getString(plugin.locale() + "." + "ERR_NO_PERMS")));
					}
				}else if (((args[0].equals("teleport")) || (args[0].equals("tele"))) && (p.hasPermission("bedhome.lookup")) || p.isOp()){
					try {
						if (plugin.yml.contains(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2])){
							double x = (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".x");
							double y = (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".y");
							double z = (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".z");
							World w = Bukkit.getWorld(args[2]);
							Location l = new Location(w, x, y, z);
							p.teleport(l);
							p.sendMessage(ChatColor.DARK_GREEN + ((plugin.locale.getString(plugin.locale() + "." + "TELE_OTHER_PLAYER")).replace("$player", args[1])).replace("$world", args[2]));
						}else{
							p.sendMessage(ChatColor.DARK_RED + ((plugin.locale.getString(plugin.locale() + "." + "ERR_PLAYER_NO_BED")).replace("$player", args[1])).replace("$world", args[2]));
						}
					} catch (Exception e) {
						p.sendMessage(ChatColor.DARK_RED + (plugin.locale.getString(plugin.locale() + "." + "ERR_BAD_PLAYER")));
					}
				}else{
					p.sendMessage(ChatColor.DARK_RED + (plugin.locale.getString(plugin.locale() + "." + "ERR_SYNTAX")));
				}
		}else{
			p.sendMessage(ChatColor.DARK_RED + (plugin.locale.getString(plugin.locale() + "." + "ERR_SYNTAX")));
		}
		}else{
			p.sendMessage(ChatColor.DARK_RED + (plugin.locale.getString(plugin.locale() + "." + "ERR_SYNTAX")));
		}
		return true;
	}
	return false;
}
}
