
package org.minecast.bedhome;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

import net.gravitydevelopment.updater.Updater;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.evilmidget38.UUIDFetcher;

public class Main extends JavaPlugin implements Listener{
	public static Main plugin;
	public final BedHomeListener l = new BedHomeListener(this);
	File file = new File(this.getDataFolder(), "beds.yml");
	File localeFile = new File(this.getDataFolder(), "locale.yml");
	YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
	YamlConfiguration locale = YamlConfiguration.loadConfiguration(localeFile);
	protected Logger log;
	PluginDescriptionFile pdf = this.getDescription();
	public boolean autoDL(){
		if((getConfig().getBoolean("auto-update"))){
			return true;
		}else{
			return false;
		}
	}
		@SuppressWarnings("unused")
    	public void reloadLocale(){
    	    if (localeFile == null) {
    	        File localeFile = new File(this.getDataFolder(), "locale.yml");
    	    }
    	    locale = YamlConfiguration.loadConfiguration(localeFile);
    	 
    	    // Look for defaults in the jar
    	    Reader localeStream = new InputStreamReader(this.getResource("locale.yml"));
    	    if (localeStream != null) {
    	        YamlConfiguration loc = YamlConfiguration.loadConfiguration(localeStream);
    	        locale.setDefaults(loc);
    	    }
    	}
    	public FileConfiguration getLocale(){
    	    if (locale == null) {
    	        reloadLocale();
    	    }
    	    return locale;
    	}
    	  public void checkConfig(String section, Object value){
    	    if (!getConfig().isSet(section)) {
    	      getConfig().set(section, value);
    	    }
    	  }
	@Override
	@SuppressWarnings("unused")
	public void onEnable() {
		Updater updater = new Updater(this, 81407, this.getFile(), autoDL() ? Updater.UpdateType.DEFAULT : Updater.UpdateType.DISABLED //Custom Updater type which does nothing
						, false);
		
		this.getCommand("bedhome").setExecutor(new BedHomeCmd(this));
		this.log = this.getLogger();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.l, this);

		this.yml.options().copyDefaults(true);
		
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		try {
			Metrics metrics = new Metrics(this);
			getConfig().options();
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		
		
		reloadLocale();
		
		if (!localeFile.exists()) {
	           this.saveResource("locale.yml", false);
	    }
		locale.setDefaults(locale);
		reloadLocale();
		try{
	        OfflinePlayer.class.getMethod("getUniqueId", new Class[0]);
	      }
	      catch (NoSuchMethodException e){
	        getLogger().severe("!!!====================================WARNING====================================!!!");
	        getLogger().severe("Since version 2.15, BedHome requires a server with UUID support.");
	        getLogger().severe("Please update your Bukkit version or downgrade the plugin to version 2.0 or below.");
	        getLogger().severe("Plugin disabling.");
	        getLogger().severe("!!!====================================WARNING====================================!!!");
	        getPluginLoader().disablePlugin(this);
	        return;
	      }
			getConfig().options().header("Configuration for BedHome 2.18 by Superior_Slime"
					+ "\npermissions - true/false. Whether to use permissions or allow all players to do /bed"
					+ "\nauto-update - true/false. Should the plugin automatically download and install new updates?"
					+ "\nconsole_messages - true/false. Should player actions (such as teleporting to a bed or setting one) be logged to the console?"
					+ "\nday_beds - true/false. Should players be able to set beds at day? Or only allow beds at night?"
					+ "\nnobedmode - a/b/c."
					+ "\na: Allow players to teleport to their previous bed if destroyed."
					+ "\nb: Players will not be able to teleport to their past bed."
					+ "\nc: Players will not be able to teleport to their past bed, but can see its co-ordinates."
					+ "\nLocale - What language to use. Availible: en (English), es (Spanish), German (de), fr (French), pt (Portuguese) and dn (Danish)."
					+ "\n If you specify a language that doesn't exist, the plugin will just use English.");
			checkConfig("day_beds", false);
			saveConfig();
		
		
	} // Ends onEnable()
	private boolean bedInConfig(Player player) {
		if(yml != null){
			String id = player.getUniqueId().toString();
			World w = player.getLocation().getWorld();
			String wn = w.getName();
			return (yml.contains(id + "." + wn + ".x")
					&& yml.contains(id + "." + wn + ".y") && yml.contains(id + "."
					+ wn + ".z"));
		}else{
			return false;
		}
	}
	public String getLanguage(){
		if ((this.locale.isConfigurationSection(getConfig().getString("locale")))){
			return this.getConfig().getString("locale");
		}else{
			return "en";
		}
	}
	public boolean authorized(CommandSender s, String perm){
		if(s instanceof Player){
			Player p = (Player) s;
			if(p.hasPermission(perm)){
				return true;
			}else if(p.isOp()){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	private void tp(Player player) {
		Player p = player;
		String id = player.getUniqueId().toString();
		World w = player.getLocation().getWorld();
		String wn = w.getName();
		double x = (Double) yml.get(id + "." + wn + ".x");
		double y = (Double) yml.get(id + "." + wn + ".y");
		double z = (Double) yml.get(id + "." + wn + ".z");
		p.teleport(new Location(w, x, y, z));
		p.sendMessage(ChatColor.DARK_GREEN + locale.getString(getLanguage() + "." + "BED_TELE"));
	}
	private void cfgCheck(Player player){
		if(getConfig() != null && file != null){
			Player p = (Player) player;
			String id = player.getUniqueId().toString();
			if ((getConfig().getString("nobedmode").equals("a"))) {
				if (bedInConfig(p)) {
					tp(p);
					if(getConfig().getBoolean("console_messages")){
		  				log.info(locale.getString(getLanguage() + "." + "CONSOLE_PLAYER_TELE").replace("$player", ChatColor.stripColor(p.getDisplayName())));
		  			}
				} else {
					p.sendMessage(ChatColor.DARK_RED
							+ locale.getString(getLanguage() + "." + "ERR_NO_BED"));
				}
			} else if (getConfig().getString("nobedmode").equals("c")) {
				if (bedInConfig(p)) {
					World w = p.getLocation().getWorld();
					String wn = w.getName();
					double x = (Double) yml.get(id + "." + wn + ".x");
					double y = (Double) yml.get(id + "." + wn + ".y");
					double z = (Double) yml.get(id + "." + wn + ".z");
					int xInt = (int) Math.round(x);
					int yInt = (int) Math.round(y);
					int zInt = (int) Math.round(z);
					p.sendMessage(ChatColor.RED + (locale.getString(getLanguage() + "." + "BED_COORDS")));
					p.sendMessage(ChatColor.RED + "X: " + ChatColor.GOLD + xInt);
					p.sendMessage(ChatColor.RED + "Y: " + ChatColor.GOLD + yInt);
					p.sendMessage(ChatColor.RED + "Z: " + ChatColor.GOLD + zInt);
				} else {
					p.sendMessage(ChatColor.DARK_RED
							+ locale.getString(getLanguage() + "." + "ERR_NO_BED"));
				}
			} else if ((getConfig().getString("nobedmode").equals("b"))) {
				p.sendMessage(ChatColor.DARK_RED
						+ locale.getString(getLanguage() + "." + "ERR_NO_BED"));
			} else {
				p.sendMessage(ChatColor.DARK_RED
						+ "Plugin was not set up correctly. Please contact your server administrator.");
			}
		}else{
			
			player.sendMessage(ChatColor.DARK_RED
					+ locale.getString(getLanguage() + "." + "ERR_NO_BED"));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("bed")) {
            if(file != null){
            	if (!(sender instanceof Player)) {
    				sender.sendMessage(locale.getString(getLanguage() + "." + "ERR_CONSOLE_TELE"));
    			} else if (((Player) sender).getBedSpawnLocation() != null) {
    				Player p = (Player) sender;
    				String dn = p.getDisplayName();
    				dn = ChatColor.stripColor(dn);
    				if (p.getBedSpawnLocation().getWorld() == p.getWorld()) {
    					if (!p.hasPermission("bedhome.bed")
    							&& (getConfig().getBoolean("permissions"))) {
    						p.sendMessage(ChatColor.DARK_RED
    								+ locale.getString(getLanguage() + "." + "ERR_NO_PERMS"));
    					} else if (p.isOp()
    							|| ((p.hasPermission("bedhome.bed")) & (getConfig()
    									.getString("permissions") == "true"))
    							|| ((getConfig().getString("permissions") != "false") & (getConfig()
    									.getString("permissions") != "true"))
    							|| (!getConfig().getBoolean("permissions"))) {
    						if (bedInConfig(p)) {
    							tp(p);
    							if(getConfig().getBoolean("console_messages")){
    								log.info(locale.getString(getLanguage() + "." + "CONSOLE_PLAYER_TELE").replace("$player", ChatColor.stripColor(p.getDisplayName())));
    				  			}
    						} else {
    							p.sendMessage(ChatColor.DARK_RED
    									+ locale.getString(getLanguage() + "." + "ERR_NO_BED"));
    						}
    					}
    				} else if (bedInConfig(p)) {
    					cfgCheck(p);
    				}
    			}else{
    				cfgCheck((Player) sender);
    			}
    			
            }else{
            	if(sender instanceof Player){
            		sender.sendMessage(ChatColor.DARK_RED
    						+ locale.getString(getLanguage() + "." + "ERR_NO_BED"));
            	}else{
            		sender.sendMessage(locale.getString(getLanguage() + "." + "ERR_CONSOLE_TELE"));
            	}
            }
            return true;
		}else if(commandLabel.equalsIgnoreCase("bhdebug")){
				sender.sendMessage("Command Registered");
				sender.sendMessage("=======DEBUG BEGIN=======");
				if(authorized(sender, "bedhome.debug")){
					sender.sendMessage("Name: " + pdf.getName());
					sender.sendMessage("Main class: " + pdf.getMain());
					sender.sendMessage("Locale: " + getLanguage());
					sender.sendMessage("Version: " + pdf.getVersion());
					if(sender instanceof Player){
						Player p = (Player) sender;
						if(p.getBedSpawnLocation() != null){
							p.sendMessage("Bed spawn location not null");
							p.sendMessage(Double.toString(p.getBedSpawnLocation().getX()));
							p.sendMessage(Double.toString(p.getBedSpawnLocation().getY()));
							p.sendMessage(Double.toString(p.getBedSpawnLocation().getZ()));
						}else{
							p.sendMessage("Bed spawn location is null");
						}
					}
					if(Bukkit.getServer().getOnlineMode()){
						sender.sendMessage("Mode: Online");
					}else{
						sender.sendMessage("Mode: Offline");
					}
					try{
						sender.sendMessage("Day bed mode: " + getConfig().getString("day_beds"));
					}catch(Exception e2){
						sender.sendMessage(ChatColor.DARK_RED + "Config error!!");
					}
					try{
						sender.sendMessage("Locale string: " + ChatColor.DARK_RED + (locale.getString("en.ERR_NO_PERMS") + locale.getString("fr.ERR_NO_PERMS") + locale.getString("es.ERR_NO_PERMS")
								+ locale.getString("pt.ERR_NO_PERMS") + locale.getString("de.ERR_NO_PERMS") + locale.getString("dn.ERR_NO_PERMS")));
						sender.sendMessage("Locale should be OK");
					}catch(Exception e1){
						sender.sendMessage(ChatColor.DARK_RED + "Locale error!!");
					}
					try {
						sender.sendMessage(((UUIDFetcher.getUUIDOf("Superior_Slime")).toString()));
						sender.sendMessage("UUID Fetching OK");
					} catch (Exception e) {
						sender.sendMessage("UUID Fetching NOT OK");
						try {
							sender.sendMessage("Stack trace incoming in 3 seconds. INCLUDE THIS IN BUG REPORT");
							Thread.sleep(3000);
						} catch (InterruptedException e1) {
							sender.sendMessage("Incoming Stack Trace. INCLUDE THIS IN BUG REPORT");
						}
						sender.sendMessage(e.getStackTrace().toString());
					}
				}else{
					sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to perform this command!");
				}
				sender.sendMessage("=======DEBUG END=======");
				return true;
		}
		return false;
		}
}

