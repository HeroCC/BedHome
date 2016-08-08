package org.minecast.bedhome;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.util.logging.Logger;

import net.gravitydevelopment.updater.Updater;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.mcstats.Metrics;

import org.minecast.bedhome.ExtraLanguages.LocaleStrings;


public class Main extends JavaPlugin implements Listener {
  public static Main plugin;
  public final BedHomeListener l = new BedHomeListener(this);
  File file = new File(this.getDataFolder(), "beds.yml");
  YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);

  File localeFile = new File(this.getDataFolder(), "locale.yml");
  YamlConfiguration locale = YamlConfiguration.loadConfiguration(localeFile);

  protected Logger log;
  PluginDescriptionFile pdf = this.getDescription();
 
  private boolean autoDL() { return (getConfig().getBoolean("auto-update")); }
  
  @SuppressWarnings("unused")
  public void reloadLocale() {
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

  public FileConfiguration getLocale() {
    if (locale == null) {
      reloadLocale();
    }
    return locale;
  }

  public void checkConfig(String section, Object value) {
    if (!getConfig().isSet(section)) {
      getConfig().set(section, value);
    }
  }

  public void checkLocale(String lang, String section, Object value) {
    if (!getLocale().isSet(lang + "." + section)) {
      getLocale().set(lang + "." + section, value);
    }
  }

  public String getLocaleString(String item) {
    if(getConfig().getString("locale").equals("ru")){
      return ExtraLanguages.getRussian(LocaleStrings.valueOf(item));
    }else if(getConfig().getString("locale").equals("zh_tw")){
        return ExtraLanguages.getTraditionalChinese(LocaleStrings.valueOf(item));
    }else if(getConfig().getString("locale").equals("jp")){
        return ExtraLanguages.getJapanese(LocaleStrings.valueOf(item));
    }else if(getConfig().getString("locale").equals("zh_cn")){
      return ExtraLanguages.getSimplifiedChinese(LocaleStrings.valueOf(item));
    }else if(getConfig().getString("locale").equals("kr")){
      return ExtraLanguages.getKorean(LocaleStrings.valueOf(item));
    }else{
      return locale.getString(getLanguage() + "." + item).replace('&', '§');
    }
  }

  public void sendUTF8Message(String text, CommandSender p) {
    try {
      byte[] bytes = text.getBytes("UTF-8");
      String value = new String(bytes, "UTF-8");
      p.sendMessage(value);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void copyFile(File sourceFile, File destFile) throws IOException {
    if (!destFile.exists()) {
      destFile.createNewFile();
    }

    FileChannel source = null;
    FileChannel destination = null;

    try {
      source = new FileInputStream(sourceFile).getChannel();
      destination = new FileOutputStream(destFile).getChannel();
      destination.transferFrom(source, 0, source.size());
    } finally {
      if (source != null) {
        source.close();
      }
      if (destination != null) {
        destination.close();
      }
    }
  }

  public void verifyLocale() {
    if (((!getLocale().isSet("version") || getLocale().getDouble("version") == 2.23)) && new File(this.getDataFolder(), "locale.yml").exists()) {
      getLogger().warning("/!\\======================NOTICE======================/!\\");
      getLogger().warning(
          "Since the last version of the plugin, the locale has had vital items added to it.");
      getLogger()
          .warning(
              "Thanks to Bukkit's crappy encoding handling, we'll have to delete your locale to regenerate it.");
      getLogger().warning("But, we will back up your old file so you can migrate any changes.");
      getLogger().warning("The backup file is called \"locale.yml.old\"");
      getLogger().warning("/!\\======================NOTICE======================/!\\");

      try {
        File bak = new File(this.getDataFolder(), "locale.yml.old");
        copyFile(localeFile, bak);

      } catch (IOException e) {
        // TODO TODO TODO TODO DOOO
        e.printStackTrace();
      }
      localeFile.delete();
      reloadLocale();

      if (!localeFile.exists()) {
        this.saveResource("locale.yml", false);
      }
      locale.setDefaults(locale);
      reloadLocale();
    }
  }

  public void setConfigOpts() {
    getConfig()
        .options()
        .header(
            "Configuration for BedHome 2.26 by Superior_Slime"
                + "\npermissions - true/false. Whether to use permissions or allow all players to do /bed"
                + "\nauto-update - true/false. Should the plugin automatically download and install new updates?"
                + "\nconsole_messages - true/false. Should player actions (such as teleporting to a bed or setting one) be logged to the console?"
                + "\nday_beds - true/false. Should players be able to set beds at day? Or only allow beds at night?"
                + "\nrelaxed_checking - true/false. If you have problems using /bed, set this to true. However, this can cause bugs."
                + "\nnobedmode - a/b/c."
                + "\na: Allow players to teleport to their previous bed if destroyed."
                + "\nb: Players will not be able to teleport to their past bed."
                + "\nc: Players will not be able to teleport to their past bed, but can see its co-ordinates."
                + "\nLocale - What language to use. Availible: ru (English), es (Spanish), German (de), fr (Frruch), pt (Portuguese) and dn (Danish)."
                + "\n If you specify a language that doesn't exist, the plugin will just use English.");
    checkConfig("permissions", true);
    checkConfig("auto-update", true);
    checkConfig("console_messages", false);
    checkConfig("day_beds", false);
    checkConfig("relaxed_checking", false);
    checkConfig("nobedmode", 'c');
    checkConfig("locale", "en");
    this.getConfig().options().copyDefaults(true);
  }

  public boolean checkForUUID() {
    try {
      OfflinePlayer.class.getMethod("getUniqueId", new Class[0]);
      return true;
    } catch (NoSuchMethodException e) {
      getLogger().severe("!!!======================WARNING======================!!!");
      getLogger().severe("Since version 2.15, BedHome requires a server with UUID support.");
      getLogger().severe(
          "Please update your Bukkit version or downgrade the plugin to version 2.0 or below.");
      getLogger().severe("Plugin disabling.");
      getLogger().severe("!!!======================WARNING======================!!!");
      getPluginLoader().disablePlugin(this);
      return false;
    }
  }

  private void setupMetrics() {
    try {
      Metrics metrics = new Metrics(this);
      getConfig().options();
      metrics.start();
    } catch (IOException e) {
      // Failed to submit the stats :-(
    }
  }

  @Override
  @SuppressWarnings("unused")
  public void onEnable() {
    verifyLocale();

    setConfigOpts();
    saveConfig();
    saveDefaultConfig();

    checkForUUID();

    Updater updater = new Updater(this, 81407, this.getFile(), autoDL() ? Updater.UpdateType.DEFAULT : Updater.UpdateType.NO_DOWNLOAD, false);

    this.getCommand("bedhome").setExecutor(new BedHomeCmd(this));
    this.log = this.getLogger();
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(this.l, this);

    this.yml.options().copyDefaults(true);

    setupMetrics();
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
    

    pm.addPermission(new Permission("bedhome.bed"));
    pm.addPermission(new Permission("bedhome.admin"));
    pm.addPermission(new Permission("bedhome.world"));
    pm.addPermission(new Permission("bedhome.lookup"));
    pm.addPermission(new Permission("bedhome.config"));

  }

  private boolean bedInConfig(Player player, World w) {
    if (yml != null) {
      String id = player.getUniqueId().toString();
      String wn = w.getName();
      return (yml.contains(id + "." + wn + ".x") && yml.contains(id + "." + wn + ".y") && yml
          .contains(id + "." + wn + ".z"));
    } else {
      return false;
    }
  }

  public String getLanguage() {
    if (this.locale.isConfigurationSection(getConfig().getString("locale"))) {
      return this.getConfig().getString("locale");
    } else {
      return "en";
    }
  }

  public boolean isPlayerAuthorized(CommandSender s, String perm) {
    if (s instanceof Player) {
      Player p = (Player) s;
      if (p.hasPermission(perm) || p.isOp()) {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }

  public void teleToBed(Player player, World w) {
    String id = player.getUniqueId().toString();
    String wn = w.getName();
    double x = (Double) yml.get(id + "." + wn + ".x");
    double y = (Double) yml.get(id + "." + wn + ".y");
    double z = (Double) yml.get(id + "." + wn + ".z");
    player.teleport(new Location(w, x, y, z));
    sendUTF8Message(getLocaleString("BED_TELE"), player);
  }

  public void sendCoords(Player p, World w) {
    String wn = w.getName();
    String id = p.getUniqueId().toString();
    double x = (Double) yml.get(id + "." + wn + ".x");
    double y = (Double) yml.get(id + "." + wn + ".y");
    double z = (Double) yml.get(id + "." + wn + ".z");
    int xInt = (int) Math.round(x);
    int yInt = (int) Math.round(y);
    int zInt = (int) Math.round(z);
    p.sendMessage((getLocaleString("BED_COORDS")));
    p.sendMessage(ChatColor.RED + "X: " + ChatColor.GOLD + xInt);
    p.sendMessage(ChatColor.RED + "Y: " + ChatColor.GOLD + yInt);
    p.sendMessage(ChatColor.RED + "Z: " + ChatColor.GOLD + zInt);
  }

  private void noBedCheck(Player p, World w, boolean isOtherWorld) {
    if (getConfig() != null && yml != null) {
      if ((getConfig().getString("nobedmode").equals("a"))) {
        if (bedInConfig(p, w)) {
          teleToBed(p, w);
          if (getConfig().getBoolean("console_messages")) {
            log.info(getLocaleString("CONSOLE_PLAYER_TELE").replace("$player", ChatColor.stripColor(p.getDisplayName())));
          }
        } else {
          if (!isOtherWorld) { sendUTF8Message(getLocaleString("ERR_NO_BED"), p); } else { sendUTF8Message(getLocaleString("ERR_NO_BED_OTHER").replace("$world", w.getName()), p); }
        }
      } else if ((getConfig().getString("nobedmode").equals("b"))) {
        if (!isOtherWorld) { sendUTF8Message(getLocaleString("ERR_NO_BED"), p); } else { sendUTF8Message(getLocaleString("ERR_NO_BED_OTHER").replace("$world", w.getName()), p); }
      } else if (getConfig().getString("nobedmode").equals("c")) {
        if (bedInConfig(p, w)) {
          sendCoords(p, w);
        } else {
          if (!isOtherWorld){ sendUTF8Message(getLocaleString("ERR_NO_BED"), p); } else { sendUTF8Message(getLocaleString("ERR_NO_BED_OTHER").replace("$world", w.getName()), p); }
        }
      } else {
        p.sendMessage(ChatColor.DARK_RED
            + "Plugin was not set up correctly. Please contact your server administrator.");
      }
    } else {
      if (!isOtherWorld){ sendUTF8Message(getLocaleString("ERR_NO_BED"), p); } else { sendUTF8Message(getLocaleString("ERR_NO_BED_OTHER").replace("$world", w.getName()), p); }
    }
  }

  private boolean bedAtPos(Player p, World w){
        if(!getConfig().getBoolean("relaxed_checking")){
          String id = p.getUniqueId().toString();
          String wn = w.getName();
          double x = (Double) yml.get(id + "." + wn + ".x");
          double y = (Double) yml.get(id + "." + wn + ".y");
          double z = (Double) yml.get(id + "." + wn + ".z");
          Location l = new Location(w, x, y, z);
          return l.getBlock().getType() == Material.BED_BLOCK || l.getBlock().getType() == Material.BED
              || l.add(0,1,0).getBlock().getType() == Material.BED_BLOCK || l.add(0,1,0).getBlock().getType() == Material.BED;
        }else{
          return true;
        }
  }

  private void doDebugChecking(CommandSender sender) {
    sendUTF8Message("Command Registered", sender);
    sendUTF8Message("=======DEBUG BEGIN=======", sender);
    if (isPlayerAuthorized(sender, "bedhome.debug")) {
      sender.sendMessage("Name: " + pdf.getName());
      sender.sendMessage("Main class: " + pdf.getMain());
      sender.sendMessage("Locale: " + getLanguage());
      sender.sendMessage("Version: " + pdf.getVersion());
      sendUTF8Message("Телепортироваться в свою кровать.", sender);

      if (sender instanceof Player) {
        Player p = (Player) sender;
        if (p.getBedSpawnLocation() != null) {
          sendUTF8Message("Bed spawn location not null", p);
          p.sendMessage(Double.toString(p.getBedSpawnLocation().getX()));
          p.sendMessage(Double.toString(p.getBedSpawnLocation().getY()));
          p.sendMessage(Double.toString(p.getBedSpawnLocation().getZ()));
        } else {
          sendUTF8Message("Bed spawn location is null", p);
        }

        if (p.hasPermission("bedhome.bed")) {
          sendUTF8Message("You have /bed permission", p);
        } else {
          sendUTF8Message("You do not have /bed permissions", p);
        }
      }

      if (Bukkit.getServer().getOnlineMode()) {
        sendUTF8Message("Mode: Online", sender);
      } else {
        sendUTF8Message("Mode: Offline", sender);
      }

      try {
        sender.sendMessage("Day bed mode: " + getConfig().getString("day_beds"));
        sender.sendMessage("Permissions: " + getConfig().getString("permissions"));
      } catch (Exception e2) {
        sender.sendMessage(ChatColor.DARK_RED + "Config error!!");
      }
      try {
        sender.sendMessage("Locale string: "
            + ChatColor.DARK_RED
            + (locale.getString("en.ERR_NO_PERMS") + locale.getString("fr.ERR_NO_PERMS")
            + locale.getString("es.ERR_NO_PERMS") + locale.getString("pt.ERR_NO_PERMS")
            + locale.getString("de.ERR_NO_PERMS") + locale.getString("dn.ERR_NO_PERMS")));
        sendUTF8Message("Locale should be OK", sender);
      } catch (Exception e1) {
        sender.sendMessage(ChatColor.DARK_RED + "Locale error!!");
      }

      if (checkForUUID()){ sendUTF8Message("UUID Fetching OK", sender); } else { sendUTF8Message("UUID Fetching NOT OK", sender); }

      try {
        sendUTF8Message("Stack trace incoming in 3 seconds. INCLUDE THIS IN BUG REPORT", sender);
        Thread.sleep(3000);
      } catch (InterruptedException e1) {
        sendUTF8Message("Incoming Stack Trace. INCLUDE THIS IN BUG REPORT", sender);
        sender.sendMessage(e1.getStackTrace().toString());
      }

    } else {
      sender.sendMessage(ChatColor.DARK_RED + "You don't have permission.");
    }
    sendUTF8Message("=======DEBUG END=======", sender);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (commandLabel.equalsIgnoreCase("bed")) {
      if (sender instanceof Player) {
        Player p = (Player) sender;
        if (args.length == 1) {
          if (isPlayerAuthorized(sender, "bedhome.world") || !getConfig().getBoolean("permissions")) {
            if (Bukkit.getWorld(args[0]) != null) {
              World w = Bukkit.getWorld(args[0]);
              if (bedInConfig(p, w) && bedAtPos(p, w)) {
                teleToBed(p, w);
              } else {
                noBedCheck(p, w, true);
              }
            } else {
              sendUTF8Message(getLocaleString("BH_BAD_WORLD"), p);
            }
          } else {
            sendUTF8Message(getLocaleString("ERR_NO_PERMS"), p);
          }
        } else if (args.length == 0) {
          if ((isPlayerAuthorized(sender, "bedhome.bed")) || !getConfig().getBoolean("permissions")) {
            if (((Player) sender).getBedSpawnLocation() != null) {
              if (p.getBedSpawnLocation().getWorld() == p.getWorld()) {
                if (bedInConfig(p, p.getWorld())) {
                  teleToBed(p, p.getWorld());
                  if (getConfig().getBoolean("console_messages")) {
                    log.info(getLocaleString("CONSOLE_PLAYER_TELE").replace("$player", ChatColor.stripColor(p.getDisplayName())));
                  }
                } else {
                  sendUTF8Message(getLocaleString("ERR_NO_BED"), p);
                }
              } else {
                noBedCheck(p, p.getWorld(), false);
              }
            } else {
              noBedCheck(p, p.getWorld(), false);
            }
          } else {
            sendUTF8Message(getLocaleString("ERR_NO_PERMS"), p);
          }
        } else {
          sender.sendMessage(getLocaleString("ERR_SYNTAX"));
        }
      } else {
        sender.sendMessage(getLocaleString("ERR_CONSOLE_TELE"));
      }
      return true;
    } else if (commandLabel.equalsIgnoreCase("bhdebug")) {
      doDebugChecking(sender);
      return true;
    }
    return false;
  }
}
