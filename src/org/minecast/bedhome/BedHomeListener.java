package org.minecast.bedhome;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;



public class BedHomeListener
  implements Listener
{
  public static Main plugin;

  Updater updater;
  
  public BedHomeListener(Main instance)
  {
    plugin = instance;
  }
  @EventHandler
  public void bedLeave(PlayerBedLeaveEvent event){
	  Player p = event.getPlayer();
  	if ((p.hasPermission("bedhome.bed") && plugin.getConfig().getString("permissions") == "true") || p.isOp()
    		|| (plugin.getConfig().getString("permissions") == "false")){
  		
  	  	String id = p.getUniqueId().toString();
  	    p.setBedSpawnLocation(p.getLocation());
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
  			p.sendMessage(ChatColor.DARK_GREEN
  					+ plugin.BED_SET);
  			if(plugin.getConfig().getBoolean("console_messages")){
  				plugin.log.info(plugin.CONSOLE_PLAYER_SET.replace("$player", ChatColor.stripColor(p.getDisplayName())));
  			}
  		
}
  }

}