package me.Superior_Slime.bedhome;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class listener
  implements Listener
{
  public static main plugin;
  //TESTTEST

  public void setBed(Player player){
	Player p = (Player) player;
  	String dn = p.getDisplayName();
    dn = ChatColor.stripColor(dn);
    p.setBedSpawnLocation(p.getLocation());
	double x = p.getLocation().getX();
	double z = p.getLocation().getZ();
	double y = p.getLocation().getY();
    World w = p.getLocation().getWorld();
    String wn = w.getName();
	plugin.yml.set(dn + ".x", x);
	plugin.yml.set(dn + ".y", y);
	plugin.yml.set(dn + ".z", z);
	plugin.yml.set(dn + ".world", wn);
	try {
		plugin.yml.save(plugin.file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	p.sendMessage(ChatColor.DARK_GREEN + (plugin.getConfig().getString("bedset")));
  }
  public listener(main instance)
  {
    plugin = instance;
  }
  @EventHandler
  public void bedLeave(PlayerBedLeaveEvent event) throws IOException{
  	Player p = event.getPlayer();
        if ((p.hasPermission("bedhome.bed")) && plugin.getConfig().getString("permissions") == "true"){
        	setBed(p);
        }else if(plugin.getConfig().getString("permissions") == "false"){
        	setBed(p);
        }else{
        	p.sendMessage(ChatColor.DARK_RED + "Couldn't set bed home, plugin was not configured correctly. Please contact your server admin");
        }
    }
  @EventHandler
  public void playerJoin(PlayerJoinEvent event){
	  Player p = event.getPlayer();
	  if ((p.hasPermission("bedhome.getupdates") || p.isOp()) && plugin.getConfig().getString("updatecheck") == "true"){
		  p.sendMessage(ChatColor.BLUE + "[BH] A new version of BedHome is availible: " + UpdateChecker.v);
		  p.sendMessage(ChatColor.BLUE + "[BH] Get it here: " + ChatColor.RED + UpdateChecker.l);
	  }
  }


}
