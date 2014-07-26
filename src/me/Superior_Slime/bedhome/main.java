package me.Superior_Slime.bedhome;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	public static main plugin;
	public final listener l = new listener(this);
	File file = new File(this.getDataFolder(), "beds.yml");
	YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
	protected Logger log;
	
	public void onDisable() {
	}

	public void onEnable() {
		Updater updater = new Updater(this, 81407, this.getFile(), getConfig().getBoolean("auto-update")? Updater.UpdateType.DEFAULT : Updater.UpdateType.NO_DOWNLOAD, false);
		this.log = this.getLogger();
		this.yml.options().copyDefaults(true);
		saveDefaultConfig();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.l, this);
		pm.addPermission(new Permission("bedhome.getupdates"));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} //Ends onEnable()

	private boolean bedExists(Player player) {
		String dn = player.getDisplayName();
		dn = ChatColor.stripColor(dn);
		return (yml.contains(dn + ".x") && yml.contains(dn + ".y") && yml
				.contains(dn + ".world"));
	}

	private void tp(Player player){
		Player p = (Player) player;
		String dn = p.getDisplayName();
		dn = ChatColor.stripColor(dn);
		double x = (Double) yml.get(dn + ".x");
		double y = (Double) yml.get(dn + ".y");
		double z = (Double) yml.get(dn + ".z");
		World bw = Bukkit.getWorld((yml.get(dn + ".world").toString()));
		 
		p.teleport(new Location(bw, x, y, z));
		p.sendMessage(ChatColor.DARK_GREEN + getConfig().getString("tpmessage"));
		}
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (commandLabel.equalsIgnoreCase("bed")) {
			Player p = (Player) sender;
			String dn = p.getDisplayName();
			dn = ChatColor.stripColor(dn);
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED
						+ "CONSOLE can't teleport to a bed!");
			} else if (p.getBedSpawnLocation() != null) {
				if (!p.hasPermission("bedhome.bed")
						&& (getConfig().getString("permissions") == "true")) {
					p.sendMessage(ChatColor.DARK_GREEN
							+ getConfig().getString("noperms"));
				} else if (p.isOp()
						|| ((p.hasPermission("bedhome.bed")) & (getConfig()
								.getString("permissions") == "true"))
						|| ((getConfig().getString("permissions") != "false") & (getConfig()
								.getString("permissions") != "true"))
						|| (getConfig().getString("permissions") == "false")) {
					if (bedExists(p)) {
						tp(p);
					} else {
						p.sendMessage(ChatColor.DARK_RED
								+ getConfig().getString("nobed"));
					}

				}
			} else if ((getConfig().getString("nobedmode") == "a")) {
				if (bedExists(p)) {
					tp(p);
				} else {
					p.sendMessage(ChatColor.DARK_RED
							+ getConfig().getString("nobed"));
				}
			} else if ((getConfig().getString("nobedmode") == "c")) {
				if (bedExists(p)) {
					double x = (Double) yml.get(dn + ".x");
					double y = (Double) yml.get(dn + ".y");
					double z = (Double) yml.get(dn + ".z");
					int xInt = (int) Math.round(x);
					int yInt = (int) Math.round(y);
					int zInt = (int) Math.round(z);
					p.sendMessage(ChatColor.RED
							+ getConfig().getString("bedcoordmessage")
							+ ChatColor.GOLD + " X: " + xInt + ", Y: " + yInt
							+ ", Z: " + zInt);
				} else {
					p.sendMessage(ChatColor.DARK_RED
							+ getConfig().getString("nobed"));
				}
			} else if ((getConfig().getString("nobedmode") == "b")) {
				p.sendMessage(ChatColor.DARK_RED
						+ getConfig().getString("nobed"));
			} else {
				p.sendMessage(ChatColor.DARK_RED
						+ "The plugin config was not set up correctly. Please contact your server administrator.");
			}
		}
		return false;

	}


}