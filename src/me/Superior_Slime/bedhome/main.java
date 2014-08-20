package me.Superior_Slime.bedhome;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

public class main extends JavaPlugin implements Listener {
	FileConfiguration config;
	File cfile;
	public static main plugin;
	public final listener l = new listener(this);
	File file = new File(this.getDataFolder(), "beds.yml");
	File localeFile = new File(this.getDataFolder(), "locale.yml");
	YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
	YamlConfiguration locale = YamlConfiguration.loadConfiguration(localeFile);
	protected Logger log;
	PluginDescriptionFile pdf = this.getDescription();
	@Override
	public void onDisable() {
	}


		
	public boolean autoDL(){
		if((getConfig().getBoolean("auto-update"))){
			return true;
		}else{
			return false;
		}
	}
	@SuppressWarnings("unused")
	@Override
	public void onEnable() {
		String Note_to_the_BukkitDev_mods = 
		"PLEASE read line 205 of the Updater class as this DISABLES auto-updating according to the config!";
		Updater updater = new Updater(this, 81407, this.getFile(), autoDL() ? Updater.UpdateType.DEFAULT : Updater.UpdateType.DISABLED //Custom Updater type which does nothing
						, false);
		this.getCommand("bedhome").setExecutor(new BedHomeCmd(this));
		this.log = this.getLogger();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.l, this);
		this.yml.options().copyDefaults(true);
		this.locale.options().copyDefaults(true);
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		try {
			Metrics metrics = new Metrics(this);
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
		if (!localeFile.exists()) {
			this.saveResource("locale.yml", false);
			InputStream locStream = this.getResource("locale.yml");
			if(locStream != null){
				locale.setDefaults(locale);
			}
		}
	} // Ends onEnable()
	private boolean bedInConfig(Player player) {
		String id = player.getUniqueId().toString();
		World w = player.getLocation().getWorld();
		String wn = w.getName();
		return (yml.contains(id + "." + wn + ".x")
				&& yml.contains(id + "." + wn + ".y") && yml.contains(id + "."
				+ wn + ".z"));
	}
	public String locale(){
		if (this.getConfig().getString("locale").equals("en") ||
			this.getConfig().getString("locale").equals("fr") ||
			this.getConfig().getString("locale").equals("es") ||
			this.getConfig().getString("locale").equals("dn") ||
			this.getConfig().getString("locale").equals("pt")){
			return this.getConfig().getString("locale");
		}else{
			return "en";
		}
	}
	String ERR_NO_BED = locale.getString(locale() + "." + "ERR_NO_BED");
	String ERR_PLAYER_NO_BED = locale.getString(locale() + "." + "ERR_PLAYER_NO_BED");
	String ERR_BAD_PLAYER = locale.getString(locale() + "." + "ERR_BAD_PLAYER");
	String ERR_SYNTAX = locale.getString(locale() + "." + "ERR_SYNTAX");
	String CONSOLE_PLAYER_TELE = locale.getString(locale() + "." + "CONSOLE_PLAYER_TELE");
	String CONSOLE_PLAYER_SET = locale.getString(locale() + "." + "CONSOLE_PLAYER_SET");
	String HELP_BEDHOME = locale.getString(locale() + "." + "HELP_BEDHOME");
	String BED_TELE = locale.getString(locale() + "." + "BED_TELE");
	String BED_SET = locale.getString(locale() + "." + "BED_SET");
	String LOOKUP_RESULT = locale.getString(locale() + "." + "LOOKUP_RESULT");
	String BED_COORDS = locale.getString(locale() + "." + "BED_COORDS");
	String TELE_OTHER_PLAYER = locale.getString(locale() + "." + "TELE_OTHER_PLAYER");
	String HELP_BED = locale.getString(locale() + "." + "HELP_BED");
	String HELP_LOOKUP = locale.getString(locale() + "." + "HELP_LOOKUP");
	String HELP_TELE = locale.getString(locale() + "." + "HELP_TELE");
	String ERR_NO_PERMS = locale.getString(locale() + "." + "ERR_NO_PERMS");
	String ERR_CONSOLE_TELE = locale.getString(locale() + "." + "ERR_CONSOLE_TELE");
	String NAME = locale.getString(locale() + "." + "NAME");
	String WORLD = locale.getString(locale() + "." + "WORLD");

	private void tp(Player player) {
		Player p = player;
		String id = player.getUniqueId().toString();
		World w = player.getLocation().getWorld();
		String wn = w.getName();
		double x = (Double) yml.get(id + "." + wn + ".x");
		double y = (Double) yml.get(id + "." + wn + ".y");
		double z = (Double) yml.get(id + "." + wn + ".z");
		p.teleport(new Location(w, x, y, z));
		p.sendMessage(ChatColor.DARK_GREEN + BED_TELE);
	}
	private void cfgCheck(Player player){
		Player p = (Player) player;
		String id = player.getUniqueId().toString();
		if ((getConfig().getString("nobedmode").equals("a"))) {
			if (bedInConfig(p)) {
				tp(p);
				if(getConfig().getBoolean("console_messages")){
	  				log.info(CONSOLE_PLAYER_TELE.replace("$player", ChatColor.stripColor(p.getDisplayName())));
	  			}
			} else {
				p.sendMessage(ChatColor.DARK_RED
						+ ERR_NO_BED);
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
				p.sendMessage(ChatColor.RED + BED_COORDS);
				p.sendMessage(ChatColor.RED + "X: " + ChatColor.GOLD + xInt);
				p.sendMessage(ChatColor.RED + "Y: " + ChatColor.GOLD + yInt);
				p.sendMessage(ChatColor.RED + "Z: " + ChatColor.GOLD + zInt);
			} else {
				p.sendMessage(ChatColor.DARK_RED
						+ ERR_NO_BED);
			}
		} else if ((getConfig().getString("nobedmode").equals("b"))) {
			p.sendMessage(ChatColor.DARK_RED
					+ ERR_NO_BED);
		} else {
			p.sendMessage(ChatColor.DARK_RED
					+ "Plugin was not set up correctly. Please contact your server administrator.");
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("bed")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(ERR_CONSOLE_TELE);
			} else if (((Player) sender).getBedSpawnLocation() != null) {
				Player p = (Player) sender;
				String dn = p.getDisplayName();
				dn = ChatColor.stripColor(dn);
				if (p.getBedSpawnLocation().getWorld() == p.getWorld()) {
					if (!p.hasPermission("bedhome.bed")
							&& (getConfig().getString("permissions") == "true")) {
						p.sendMessage(ChatColor.DARK_RED
								+ ERR_NO_PERMS);
					} else if (p.isOp()
							|| ((p.hasPermission("bedhome.bed")) & (getConfig()
									.getString("permissions") == "true"))
							|| ((getConfig().getString("permissions") != "false") & (getConfig()
									.getString("permissions") != "true"))
							|| (getConfig().getString("permissions") == "false")) {
						if (bedInConfig(p)) {
							tp(p);
							if(getConfig().getBoolean("console_messages")){
								log.info(CONSOLE_PLAYER_TELE.replace("$player", ChatColor.stripColor(p.getDisplayName())));
				  			}
						} else {
							p.sendMessage(ChatColor.DARK_RED
									+ ERR_NO_BED);
						}
					}
				} else if (bedInConfig(p)) {
					cfgCheck(p);
				}
			}else{
				cfgCheck((Player) sender);
			}
		}
		return false;
}
}