package org.minecast.bedhome;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;


public class BedHomeListener implements Listener {
  public static Main plugin;
  
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
    if (p.getUniqueId().toString().equalsIgnoreCase("ccf73101-c88c-461a-90be-c3a6c70da1f9") || p.getUniqueId().toString().equalsIgnoreCase("e6505ed0-eacb-4bbc-a6f6-adc2b753f02a")) {
      // If Superior_Slime or HeroCC, let them know BedHome is in use
      p.sendMessage(ChatColor.GREEN + "This server uses BedHome! :)");
    }
  }

  @EventHandler
  public void playerInteract(PlayerInteractEvent e) {
    if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && Main.blockIsBed(e.getClickedBlock())) {
      Player p = e.getPlayer();
      World w = p.getLocation().getWorld();
      String wn = w.getName();
      if (!day(p) || plugin.getConfig().getBoolean("day_beds")) {
        if (plugin.isPlayerAuthorized(p, "bedhome.bed")) {
          if (plugin.bedInConfig(p, w)) { // ogBed is null if they have bed
            Location ogBed = plugin.getSavedBedLocation(p, w); // The player's current saved bed
            if (ogBed.equals(e.getClickedBlock().getLocation()) || ogBed.equals(plugin.getAltBedBlock(e.getClickedBlock()).getLocation())) {
              return; // If the clicked block is the same as the saved block, return
            }
          }
  
          if (!plugin.chargePlayerAccount(p, plugin.bedSetCost)) {
            p.sendMessage(plugin.getLocaleString("ERR_NO_MONEY"));
            return;
          }
          
          String id = p.getUniqueId().toString();
          p.teleport(e.getClickedBlock().getLocation()); // This doesn't need to be Paper Async as it's in an already generated area (close enough for the player to touch)
          p.setBedSpawnLocation(p.getLocation(), true);
          double x = p.getLocation().getX();
          double z = p.getLocation().getZ();
          double y = p.getLocation().getY();
          plugin.yml.set(id + "." + wn + ".x", x);
          plugin.yml.set(id + "." + wn + ".y", y);
          plugin.yml.set(id + "." + wn + ".z", z);
          try {
            plugin.yml.save(plugin.beds);
          } catch (IOException ex) {
            ex.printStackTrace();
          }
          p.sendMessage(plugin.getLocaleString("BED_SET"));
          if (plugin.getConfig().getBoolean("console_messages")) {
            plugin.log.info((plugin.getLocaleString("CONSOLE_PLAYER_SET")).replace("$player", ChatColor.stripColor(p.getDisplayName())));
          }
        }
      }
    }
  }

  @EventHandler
  public void playerBreakBlock(BlockBreakEvent e){
    if (!plugin.getConfig().getString("nobedmode").equals("b")){ return; } // Return if nobedmode isn't b
    Player p = e.getPlayer();
    if (Main.blockIsBed(e.getBlock()) && plugin.getSavedBedLocation(p, p.getWorld()).distance(e.getBlock().getLocation()) <= 1) {
      String id = p.getUniqueId().toString().toLowerCase();
      if (plugin.yml.contains(id)){
        plugin.yml.set(id + "." + p.getWorld().getName(), null); // Remove the bed (in this world) from the config
        try {
          plugin.yml.save(plugin.beds);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    }
  }
}
