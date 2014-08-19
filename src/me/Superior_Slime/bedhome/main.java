package me.Superior_Slime.bedhome;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
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

	@Override
	public void onEnable() {
		@SuppressWarnings("unused")
		Updater updater = new Updater(this, 81407, this.getFile(), getConfig()
				.getBoolean("auto-update") ? Updater.UpdateType.DEFAULT
				: Updater.UpdateType.NO_DOWNLOAD, false);
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
	public String configLoc(){
		if (this.getConfig().getString("locale").equals("en") ||
			this.getConfig().getString("locale").equals("fr") ||
			this.getConfig().getString("locale").equals("es") ||
			this.getConfig().getString("locale").equals("pt")){
			return this.getConfig().getString("locale");
		}else{
			return "en";
		}
	}
	String ERR_NO_BED = locale.getString(configLoc() + "." + "ERR_NO_BED");
	String ERR_PLAYER_NO_BED = locale.getString(configLoc() + "." + "ERR_PLAYER_NO_BED");
	String ERR_BAD_PLAYER = locale.getString(configLoc() + "." + "ERR_BAD_PLAYER");
	String ERR_SYNTAX = locale.getString(configLoc() + "." + "ERR_SYNTAX");
	String CONSOLE_PLAYER_TELE = locale.getString(configLoc() + "." + "CONSOLE_PLAYER_TELE");
	String CONSOLE_PLAYER_SET = locale.getString(configLoc() + "." + "CONSOLE_PLAYER_SET");
	String HELP_BEDHOME = locale.getString(configLoc() + "." + "HELP_BEDHOME");
	String BED_TELE = locale.getString(configLoc() + "." + "BED_TELE");
	String BED_SET = locale.getString(configLoc() + "." + "BED_SET");
	String LOOKUP_RESULT = locale.getString(configLoc() + "." + "LOOKUP_RESULT");
	String BED_COORDS = locale.getString(configLoc() + "." + "BED_COORDS");
	String TELE_OTHER_PLAYER = locale.getString(configLoc() + "." + "TELE_OTHER_PLAYER");
	String HELP_BED = locale.getString(configLoc() + "." + "HELP_BED");
	String HELP_LOOKUP = locale.getString(configLoc() + "." + "HELP_LOOKUP");
	String HELP_TELE = locale.getString(configLoc() + "." + "HELP_TELE");
	String ERR_NO_PERMS = locale.getString(configLoc() + "." + "ERR_NO_PERMS");
	String ERR_CONSOLE_TELE = locale.getString(configLoc() + "." + "ERR_CONSOLE_TELE");
	String NAME = locale.getString(configLoc() + "." + "NAME");
	String WORLD = locale.getString(configLoc() + "." + "WORLD");

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
		if (commandLabel.equalsIgnoreCase("bedhome")){
			Player p = (Player) sender;
			if(args.length == 0){
				p.sendMessage(ChatColor.BLUE + "[BH] BedHome version " + pdf.getVersion() + " by Superior_Slime");
			} 
			else if(args.length == 1){
				if((args[0].equals("reload"))){
					if((p.hasPermission("bedhome.config")) || p.isOp()){
						InputStream defConfigStream = getResource("config.yml");
						if (defConfigStream != null) {
							YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
							this.getConfig().setDefaults(defConfig);
						}
						p.sendMessage(ChatColor.BLUE + "[BH] Config reloaded!");
					}else{
						p.sendMessage(ChatColor.DARK_RED + ERR_NO_PERMS);
					}
				}else if(args[0].equals("help")){
					p.sendMessage(ChatColor.GREEN + "BedHome version " + pdf.getVersion() + " by Superior_Slime - help");
					p.sendMessage(ChatColor.DARK_AQUA + "/bed                           " + ChatColor.DARK_GRAY + HELP_BED);
					p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "[reload/help]    "
					+ ChatColor.DARK_GRAY + HELP_BEDHOME);
					p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "lookup "  + ChatColor.DARK_AQUA +  NAME + " " + WORLD + "    " + ChatColor.DARK_GRAY + HELP_LOOKUP);
					p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "teleport "  + ChatColor.DARK_AQUA +  NAME + " " + WORLD + "    " + ChatColor.DARK_GRAY + HELP_TELE);
				}else{
					p.sendMessage(ChatColor.DARK_RED + ERR_SYNTAX);
				}
			
			}else if(args.length > 1){
				if(args.length == 3){
					if(args[0].equals("lookup")){
						if((p.hasPermission("bedhome.lookup")) || p.isOp()){
							try {
								if (this.yml.contains(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2])){
									p.sendMessage(ChatColor.BLUE + LOOKUP_RESULT);
									double x = (Double) yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".x");
									double y = (Double) yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".y");
									double z = (Double) yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".z");
									int xInt = (int) Math.round(x);
									int yInt = (int) Math.round(y);
									int zInt = (int) Math.round(z);
									p.sendMessage(ChatColor.RED + "X: " + ChatColor.GOLD + Integer.toString(xInt));
									p.sendMessage(ChatColor.RED + "Y: " + ChatColor.GOLD + Integer.toString(yInt));
									p.sendMessage(ChatColor.RED + "Z: " + ChatColor.GOLD + Integer.toString(zInt));
								}else{
									p.sendMessage(ChatColor.DARK_RED + (ERR_PLAYER_NO_BED.replace("$player", ChatColor.stripColor(p.getDisplayName()))).replace("$world", args[2]));
								}
							} catch (Exception e) {
								p.sendMessage(ChatColor.DARK_RED + ERR_BAD_PLAYER);
							}
						}else{
							p.sendMessage(ChatColor.DARK_RED + ERR_NO_PERMS);
						}
					}else if (((args[0].equals("teleport")) || (args[0].equals("tele"))) && (p.hasPermission("bedhome.lookup")) || p.isOp()){
						try {
							if (this.yml.contains(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2])){
								double x = (Double) yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".x");
								double y = (Double) yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".y");
								double z = (Double) yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + args[2] + ".z");
								World w = Bukkit.getWorld(args[2]);
								Location l = new Location(w, x, y, z);
								p.teleport(l);
								p.sendMessage(ChatColor.DARK_GREEN + (TELE_OTHER_PLAYER.replace("$player", args[1])).replace("$world", args[2]));
							}else{
								p.sendMessage(ChatColor.DARK_RED + (ERR_PLAYER_NO_BED.replace("$player", ChatColor.stripColor(p.getDisplayName()))).replace("$world", args[2]));
							}
						} catch (Exception e) {
							p.sendMessage(ChatColor.DARK_RED + ERR_BAD_PLAYER);
						}
					}else{
						p.sendMessage(ChatColor.DARK_RED + ERR_SYNTAX);
					}
			}else{
				p.sendMessage(ChatColor.DARK_RED + ERR_SYNTAX);
			}
			}else{
				p.sendMessage(ChatColor.DARK_RED + ERR_SYNTAX);
			}
		}else if (commandLabel.equalsIgnoreCase("bed")) {

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