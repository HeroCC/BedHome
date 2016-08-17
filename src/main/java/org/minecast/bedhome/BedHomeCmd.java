package org.minecast.bedhome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilmidget38.UUIDFetcher;

public class BedHomeCmd implements CommandExecutor {
  public static Main plugin;

  public BedHomeCmd(Main instance) {
    plugin = instance;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (commandLabel.equalsIgnoreCase("bedhome") || commandLabel.equalsIgnoreCase("bh")) {
      if (!(sender instanceof Player)) {
        plugin.sendUTF8Message(plugin.getLocaleString("BH_CONSOLE_CMD"), (sender));
      } else {
        Player p = (Player) sender;
        if (args.length == 0) {
          plugin.sendUTF8Message(plugin.getLocaleString("BH_VERSION").replace("$version", plugin.pdf.getVersion()), (p));
        } else {
          if ((args[0].equals("reload"))) {
            if ((p.hasPermission("bedhome.admin")) || p.isOp() || p.hasPermission("bedhome.config")) {
              plugin.reloadConfig();
              plugin.reloadLocale();
              plugin.sendUTF8Message(plugin.getLocaleString("BH_RELOADED"), (p));
            } else {
              p.sendMessage((plugin.getLocaleString("ERR_NO_PERMS")));
            }
          } else if (args[0].equals("help")) {
            p.sendMessage(ChatColor.GREEN + "-----==================================-----");
            p.sendMessage(ChatColor.DARK_AQUA + "/bed [world] - "
                + (plugin.getLocaleString("HELP_BED")));
            p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "[reload/help] - "
                + (plugin.getLocaleString("HELP_BEDHOME")));
            p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "lookup "
                + (plugin.getLocaleString("NAME")) + " "
                + (plugin.getLocaleString("WORLD")) + " - "
                + (plugin.getLocaleString("HELP_LOOKUP")));
            p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "teleport "
                + (plugin.getLocaleString("NAME")) + " "
                + (plugin.getLocaleString("WORLD")) + " - "
                + (plugin.getLocaleString("HELP_TELE")));
          }else if (args[0].equals("lookup")) {
                if(plugin.isPlayerAuthorized(p, "bedhome.admin") || p.hasPermission("bedhome.lookup")){
                  if(args.length == 3){
                    try {
                      if (plugin.yml.contains(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "."
                          + args[2])) {
                        p.sendMessage((plugin.getLocaleString("LOOKUP_RESULT"))
                            .replace("$player", args[1]));
                        double x =
                            (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "."
                                + args[2] + ".x");
                        double y =
                            (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "."
                                + args[2] + ".y");
                        double z =
                            (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "."
                                + args[2] + ".z");
                        int xInt = (int) Math.round(x);
                        int yInt = (int) Math.round(y);
                        int zInt = (int) Math.round(z);
                        p.sendMessage(ChatColor.RED + "X: " + ChatColor.GOLD + Integer.toString(xInt));
                        p.sendMessage(ChatColor.RED + "Y: " + ChatColor.GOLD + Integer.toString(yInt));
                        p.sendMessage(ChatColor.RED + "Z: " + ChatColor.GOLD + Integer.toString(zInt));
                      } else {
                        p.sendMessage(((plugin.getLocaleString("ERR_PLAYER_NO_BED"))
                            .replace("$player", ChatColor.stripColor(args[1]))).replace("$world",
                            args[2]));
                      }
                    } catch (Exception e) {
                      p.sendMessage((plugin.getLocaleString("ERR_BAD_PLAYER")));
                    }
                  }else{
                    p.sendMessage(plugin.getLocaleString("ERR_SYNTAX"));
                  }
                }else{
                  p.sendMessage(plugin.getLocaleString("ERR_NO_PERMS"));
                }
                  
              } else if (((args[0].equals("teleport")) || (args[0].equals("tele")))) {
                    if(plugin.isPlayerAuthorized(p, "bedhome.admin") || p.hasPermission("bedhome.lookup")){
                      if(args.length == 3){
                        try {
                          if (plugin.yml.contains(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "."
                              + args[2])) {
                            double x =
                                (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "."
                                    + args[2] + ".x");
                            double y =
                                (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "."
                                    + args[2] + ".y");
                            double z =
                                (Double) plugin.yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "."
                                    + args[2] + ".z");
                            World w = Bukkit.getWorld(args[2]);
                            Location l = new Location(w, x, y, z);
                            p.teleport(l);
                            p.sendMessage(((plugin.getLocaleString("TELE_OTHER_PLAYER"))
                                .replace("$player", args[1])).replace("$world", args[2]));
                          } else {
                            p.sendMessage(((plugin.getLocaleString("ERR_PLAYER_NO_BED"))
                                .replace("$player", args[1])).replace("$world", args[2]));
                          }
                        } catch (Exception e) {
                          p.sendMessage((plugin.getLocaleString("ERR_BAD_PLAYER")));
                        }
                      }else{
                        p.sendMessage(plugin.getLocaleString("ERR_SYNTAX"));
                      }
                    }else{
                      p.sendMessage(plugin.getLocaleString("ERR_NO_PERMS"));
                    }
              } else {
                p.sendMessage(plugin.getLocaleString("ERR_SYNTAX"));
              }
        
        
      }
        
    }
      return true;
  }
    return false;
}
}
