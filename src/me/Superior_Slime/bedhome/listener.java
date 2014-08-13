package me.Superior_Slime.bedhome;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;



public class listener
  implements Listener
{
  public static main plugin;

  Updater updater;
  
  public listener(main instance)
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
  					+ "Your bed has been set.");
  			if(plugin.getConfig().getBoolean("console_messages")){
  				plugin.log.info("[BH]" + p.getDisplayName() + " has set their bed.");
  			}
  		
}
  }

}