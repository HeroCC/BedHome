package org.minecast.bedhome;

import java.io.IOException;

import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;



public class BedHomeListener implements Listener {
  public static Main plugin;
  Updater updater;
  
  public BedHomeListener(Main instance) {
    plugin = instance;
  }

  public boolean day(Player p) {
    long time = p.getWorld().getTime();
    return time < 12300 || time > 23850;
  }

  @EventHandler
  public void playerJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (p.getName().equals("Superior_Slime")) {
      p.sendMessage(ChatColor.GREEN + "This server uses BedHome! :)");
    }


  }

  @EventHandler
  public void playerInteract(PlayerInteractEvent e) {
    if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType()
        .equals(Material.BED_BLOCK))) {
      Player p = e.getPlayer();
      if (!day(p) || plugin.getConfig().getBoolean("day_beds")) {
        if ((p.hasPermission("bedhome.bed") && plugin.getConfig().getString("permissions") == "true")
            || p.isOp() || (plugin.getConfig().getString("permissions") == "false")) {

          String id = p.getUniqueId().toString();
          p.teleport(e.getClickedBlock().getLocation());
          p.setBedSpawnLocation(p.getLocation(), true);
          double x = p.getLocation().getX();
          double z = p.getLocation().getZ();
          double y = p.getLocation().getY();
          World w = p.getLocation().getWorld();
          String wn = w.getName();
          plugin.yml.set(id + "." + wn + ".x", x);
          plugin.yml.set(id + "." + wn + ".y", y);
          plugin.yml.set(id + "." + wn + ".z", z);
          try {
            plugin.yml.save(plugin.file);
          } catch (IOException ex) {
            ex.printStackTrace();
          }
          p.setBedSpawnLocation(p.getLocation());
          p.sendMessage(plugin.getLocaleString("BED_SET"));
          if (plugin.getConfig().getBoolean("console_messages")) {
            plugin.log.info((plugin.getLocaleString("CONSOLE_PLAYER_SET")).replace("$player",
                ChatColor.stripColor(p.getDisplayName())));
          }
        }
      }
    }
  }


}
