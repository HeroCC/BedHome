package me.Superior_Slime.bedhome;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
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
	YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
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
	} // Ends onEnable()

	private boolean bedInConfig(Player player) {
		String id = player.getUniqueId().toString();
		World w = player.getLocation().getWorld();
		String wn = w.getName();
		return (yml.contains(id + "." + wn + ".x")
				&& yml.contains(id + "." + wn + ".y") && yml.contains(id + "."
				+ wn + ".z"));
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
		p.sendMessage(ChatColor.DARK_GREEN + "You have been teleported to your bed.");
	}
	private void cfgCheck(Player player){
		Player p = (Player) player;
		String id = player.getUniqueId().toString();
		if ((getConfig().getString("nobedmode").equals("a"))) {
			if (bedInConfig(p)) {
				tp(p);
			} else {
				p.sendMessage(ChatColor.DARK_RED
						+ "You do not have a bed in this world, or it has been destroyed.");
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
				p.sendMessage(ChatColor.RED
						+ "Your bed has been destroyed, however, here are its co-ordinates:"
						+ ChatColor.RED + " X: " + ChatColor.GOLD + xInt
						+ ChatColor.RED + ", Y: " + ChatColor.GOLD + yInt
						+ ChatColor.RED + ", Z: " + ChatColor.GOLD + zInt);
			} else {
				p.sendMessage(ChatColor.DARK_RED
						+ "You do not have a bed in this world, or it has been destroyed.");
			}
		} else if ((getConfig().getString("nobedmode").equals("b"))) {
			p.sendMessage(ChatColor.DARK_RED
					+ "You do not have a bed in this world, or it has been destroyed.");
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
						reloadConfig();
						p.sendMessage(ChatColor.BLUE + "[BH] Config reloaded!");
				}else if(args[0].equals("help")){
					p.sendMessage(ChatColor.GREEN + "BedHome version " + pdf.getVersion() + " by Superior_Slime - help");
					p.sendMessage(ChatColor.DARK_AQUA + "/bed   " + ChatColor.DARK_GRAY + "Teleport to your bed");
					p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "[reload/help]    "
					+ ChatColor.DARK_GRAY + "Reload the plugin config or get help");
					p.sendMessage(ChatColor.DARK_AQUA + "/bedhome " + ChatColor.AQUA + "lookup "  + ChatColor.DARK_AQUA +  "<name>    "
							+ ChatColor.DARK_GRAY + "Lookup someone's bed");
				}else if(args[0].equals("lookup")){
					p.sendMessage(ChatColor.DARK_RED + "We need a player to lookup! /bedhome lookup <name>");
				}
				}else{
					p.sendMessage(ChatColor.DARK_RED + "Incorrect syntax! Use: /bedhome [reload/help] or /bedhome lookup <name>");
				}
			}else if(args.length > 1){
				if(args[0].equals("lookup")){
					if((p.hasPermission("bedhome.lookup")) || p.isOp()){
						try {
							if (this.yml.contains((UUIDFetcher.getUUIDOf(args[1])).toString())){
								p.sendMessage(ChatColor.BLUE + "[BH] " + ChatColor.DARK_AQUA + args[1] + ChatColor.BLUE + "'s bed is at:");
								double x = (Double) yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + p.getWorld().getName() + ".x");
								double y = (Double) yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + p.getWorld().getName() + ".y");
								double z = (Double) yml.get(((UUIDFetcher.getUUIDOf(args[1])).toString()) + "." + p.getWorld().getName() + ".z");
								int xInt = (int) Math.round(x);
								int yInt = (int) Math.round(y);
								int zInt = (int) Math.round(z);
								p.sendMessage(ChatColor.RED + "X: " + ChatColor.GOLD + Integer.toString(xInt));
								p.sendMessage(ChatColor.RED + "Y: " + ChatColor.GOLD + Integer.toString(yInt));
								p.sendMessage(ChatColor.RED + "Z: " + ChatColor.GOLD + Integer.toString(zInt));
							}else{
								p.sendMessage(ChatColor.DARK_RED + "That player does not have a bed in this world.");
							}
						} catch (Exception e) {
							p.sendMessage(ChatColor.DARK_RED + "An error occured while attempting to perform this command (see console stack trace).");
							e.printStackTrace();
						}
					}else{
						p.sendMessage(ChatColor.DARK_RED + "You don't have permission.");
					}
				}else{
					p.sendMessage(ChatColor.DARK_RED + "Incorrect syntax! Use: /bedhome [reload/help] or /bedhome lookup <name>");
				}
		}
		}else if (commandLabel.equalsIgnoreCase("bed")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage("CONSOLE can't teleport to a bed!");
			} else if (((Player) sender).getBedSpawnLocation() != null) {
				Player p = (Player) sender;
				String dn = p.getDisplayName();
				dn = ChatColor.stripColor(dn);
				if (p.getBedSpawnLocation().getWorld() == p.getWorld()) {
					if (!p.hasPermission("bedhome.bed")
							&& (getConfig().getString("permissions") == "true")) {
						p.sendMessage(ChatColor.DARK_RED
								+ "You do not have permission to do that.");
					} else if (p.isOp()
							|| ((p.hasPermission("bedhome.bed")) & (getConfig()
									.getString("permissions") == "true"))
							|| ((getConfig().getString("permissions") != "false") & (getConfig()
									.getString("permissions") != "true"))
							|| (getConfig().getString("permissions") == "false")) {
						if (bedInConfig(p)) {
							tp(p);
						} else {
							p.sendMessage(ChatColor.DARK_RED
									+ "You do not have a bed in this world, or it has been destroyed.");
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
