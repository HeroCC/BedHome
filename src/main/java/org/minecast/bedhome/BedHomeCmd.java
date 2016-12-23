package org.minecast.bedhome;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedHomeCmd implements CommandExecutor {
  private final Main plugin = Main.getPlugin();

  @Override
  public boolean onCommand(CommandSender p, Command cmd, String commandLabel, String[] args) {
    if (cmd.getName().equalsIgnoreCase("bedhome") || cmd.getName().equalsIgnoreCase("bh")) {
      if (args.length == 0) {
        plugin.sendUTF8Message(plugin.getLocaleString("BH_VERSION").replace("$version", plugin.pdf.getVersion()), (p));
      } else {
        if (args[0].equals("reload")) {
          doReload(p);
        } else if (args[0].equals("help")) {
          doHelp(p);
        }else if (args[0].equals("lookup")) {
          doLookup(p, args);
        } else if (args[0].equals("teleport") || args[0].equals("tele")) {
          doTeleport(p, args);
        } else {
          p.sendMessage(plugin.getLocaleString("ERR_SYNTAX"));
        }
      }
    }
    return true;
  }

  private void doReload(CommandSender p){
    if (p.hasPermission("bedhome.admin") || p.isOp() || p.hasPermission("bedhome.config")) {
      plugin.reloadConfig();
      plugin.reloadLocale();
      plugin.reloadEconomy();
      plugin.sendUTF8Message(plugin.getLocaleString("BH_RELOADED"), p);
    } else {
      p.sendMessage((plugin.getLocaleString("ERR_NO_PERMS")));
    }
  }

  private void doHelp(CommandSender p){
    p.sendMessage(ChatColor.GREEN + "-----==================================-----");
    p.sendMessage(ChatColor.DARK_AQUA + "/bed [world] - " + (plugin.getLocaleString("HELP_BED")));
    p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "[reload/help] - " + (plugin.getLocaleString("HELP_BEDHOME")));
    p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "lookup " + plugin.getLocaleString("NAME") + " "
        + (plugin.getLocaleString("WORLD")) + " - "
        + (plugin.getLocaleString("HELP_LOOKUP")));
    p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "teleport " + (plugin.getLocaleString("NAME")) + " "
        + (plugin.getLocaleString("WORLD")) + " - "
        + (plugin.getLocaleString("HELP_TELE")));
    p.sendMessage(ChatColor.GREEN + "-----==================================-----");
  }

  private void doLookup(CommandSender p, String[] args){
    if(plugin.isPlayerAuthorized(p, "bedhome.admin") || p.hasPermission("bedhome.lookup")){
      if(args.length == 3){
        String uuid = plugin.getServer().getOfflinePlayer(args[1]).getUniqueId().toString().toLowerCase();
        if (uuid != null) {
          if (plugin.yml.contains(uuid + "." + args[2])) {
            p.sendMessage(plugin.getLocaleString("LOOKUP_RESULT").replace("$player", args[1]));
            double x = plugin.yml.getDouble((uuid) + "." + args[2] + ".x");
            double y = plugin.yml.getDouble((uuid) + "." + args[2] + ".y");
            double z = plugin.yml.getDouble((uuid) + "." + args[2] + ".z");
            int xInt = (int) Math.round(x);
            int yInt = (int) Math.round(y);
            int zInt = (int) Math.round(z);
            p.sendMessage(ChatColor.RED + "X: " + ChatColor.GOLD + Integer.toString(xInt));
            p.sendMessage(ChatColor.RED + "Y: " + ChatColor.GOLD + Integer.toString(yInt));
            p.sendMessage(ChatColor.RED + "Z: " + ChatColor.GOLD + Integer.toString(zInt));
          } else {
            p.sendMessage(((plugin.getLocaleString("ERR_PLAYER_NO_BED")).replace("$player", ChatColor.stripColor(args[1]))).replace("$world", args[2]));
          }
        } else {
          p.sendMessage((plugin.getLocaleString("ERR_BAD_PLAYER")));
        }
      }else{
        p.sendMessage(plugin.getLocaleString("ERR_SYNTAX"));
      }
    }else{
      p.sendMessage(plugin.getLocaleString("ERR_NO_PERMS"));
    }
  }

  private void doTeleport(CommandSender p, String[] args){
    if (!(p instanceof Player)) {
      plugin.sendUTF8Message(plugin.getLocaleString("BH_CONSOLE_CMD"), p);
    } else {
      Player player = (Player) p;
      OfflinePlayer targetPlayer = plugin.getServer().getOfflinePlayer(args[1]);
      if (plugin.isPlayerAuthorized(player, "bedhome.admin") || player.hasPermission("bedhome.lookup")) {
        if (args.length == 3) {
          if (targetPlayer.hasPlayedBefore()) {
            String uuid = targetPlayer.getUniqueId().toString().toLowerCase();
            if (plugin.yml.contains(uuid + "." + args[2])) {
              double x = plugin.yml.getDouble(uuid + "." + args[2] + ".x");
              double y = plugin.yml.getDouble(uuid + "." + args[2] + ".y");
              double z = plugin.yml.getDouble(uuid + "." + args[2] + ".z");
              World w = Bukkit.getWorld(args[2]);
              Location l = new Location(w, x, y, z);
              player.teleport(l);
              player.sendMessage(plugin.getLocaleString("TELE_OTHER_PLAYER").replace("$player", args[1]).replace("$world", args[2]));
            } else {
              player.sendMessage(plugin.getLocaleString("ERR_PLAYER_NO_BED").replace("$player", args[1]).replace("$world", args[2]));
            }
          } else {
            player.sendMessage(plugin.getLocaleString("ERR_BAD_PLAYER"));
          }
        } else {
          player.sendMessage(plugin.getLocaleString("ERR_SYNTAX"));
        }
      } else {
        player.sendMessage(plugin.getLocaleString("ERR_NO_PERMS"));
      }
    }
  }
}
