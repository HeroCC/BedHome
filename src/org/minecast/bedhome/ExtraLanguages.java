package org.minecast.bedhome;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ExtraLanguages {
  enum LocaleStrings {
    ERR_NO_BED, ERR_NO_PERMS, ERR_PLAYER_NO_BED, ERR_SYNTAX,
    ERR_CONSOLE_TELE, CONSOLE_PLAYER_TELE, CONSOLE_PLAYER_SET, BED_TELE, 
    BED_SET, TELE_OTHER_PLAYER, HELP_LOOKUP, HELP_TELE, 
    HELP_BEDHOME, HELP_BED, NAME, WORLD, 
    LOOKUP_RESULT, BED_COORDS, BH_BAD_WORLD, BH_VERSION, 
    BH_RELOADED, BH_CONSOLE_CMD, ERR_NO_BED_OTHER, ERR_BAD_PLAYER
  }
  
  



  public static String getRussian(LocaleStrings l) {
    switch (l) {
      case ERR_NO_BED:
        return ChatColor.DARK_RED + "У вас нет кровати в этом мире, либо она была уничтожена.";
      case ERR_NO_BED_OTHER:
        return ChatColor.DARK_RED + "У вас нет кровати в мире $world или она была разрушена.";
      case ERR_NO_PERMS:
        return ChatColor.DARK_RED + "У вас нет прав для данного действия.";
      case ERR_PLAYER_NO_BED:
        return ChatColor.DARK_RED + "$player не имеет кровати в $world.";
      case ERR_BAD_PLAYER:
        return ChatColor.DARK_RED + "Игрок с таким именем не найден (учитывайте регистр!).";
      case ERR_SYNTAX:
        return ChatColor.DARK_RED + "Ошибка синтаксиса! Использование: /bedhome [reload/help] или /bedhome <просмотр/телепорт> <имя> <мир>";
      case ERR_CONSOLE_TELE:
        return ChatColor.DARK_RED
            + "Ошибка синтаксиса! Использование: /bedhome [reload/help] или /bedhome <lookup/teleport> <имя> <мир>";
      case CONSOLE_PLAYER_TELE:
        return "КОНСОЛЬ невозможно телепортировать в кровать!";
      case CONSOLE_PLAYER_SET:
        return "$player обозначил свою кровать.";
      case BED_TELE:
        return ChatColor.DARK_GREEN + "Вы были телепортированы в свою кровать.";
      case BED_SET:
        return ChatColor.DARK_GREEN + "Вы обозначили свою кровать.";
      case TELE_OTHER_PLAYER:
        return ChatColor.DARK_GREEN + "Вы телепортированы в кровать игрока $player в мир $world";
      case HELP_LOOKUP:
        return ChatColor.DARK_GRAY + "Просмотр чьей-нибудь кровати.";
      case HELP_TELE:
        return ChatColor.DARK_GRAY + "Телепортироваться в чью-нибудь кровать.";
      case HELP_BEDHOME:
        return ChatColor.DARK_GRAY + "Перезагрузить плагин или получить помощь.";
      case HELP_BED:
        return ChatColor.DARK_GRAY + "Телепортироваться в свою кровать.";
      case NAME:
        return ChatColor.DARK_AQUA + "<имя>";
      case WORLD:
        return ChatColor.DARK_AQUA + "<мир>";
      case LOOKUP_RESULT:
        return ChatColor.GOLD + "Кровать игрока $player расположена в:";
      case BED_COORDS:
        return ChatColor.RED + "Ваша кровать была уничтожена, однако, вот ее координаты:";
      case BH_BAD_WORLD:
        return ChatColor.DARK_RED + "Что мир не существует!";
      case BH_VERSION:
        return ChatColor.BLUE + "BedHome версия $version по Superior_Slime";
      case BH_RELOADED:
        return ChatColor.BLUE + "Конфигурация и локализация перезагружается!";
      case BH_CONSOLE_CMD:
        return "Только игроки могут использовать эту команду!";
      default:
        return "error fetching locale string";
    }
  }

  
  public static String getTraditionalChinese(LocaleStrings l) {
    switch (l) {
      case ERR_NO_BED:
        return  ChatColor.DARK_RED + "你在這世界上沒有床唷，或者已經被毀掉了呢。";
      case ERR_NO_BED_OTHER:
        return  ChatColor.DARK_RED + "你在 $world 沒有床唷，或者已經被毀掉了呢。";

      case ERR_NO_PERMS:
        return  ChatColor.DARK_RED + "你沒有權限使用";
      case ERR_PLAYER_NO_BED:
        return  ChatColor.DARK_RED + "你指定的玩家 ($player) 在 $world 沒有床唷";
      case ERR_BAD_PLAYER:
        return  ChatColor.DARK_RED + "你指定的玩家不存在(或者是大小寫有誤唷)";
      case ERR_SYNTAX:
        return  ChatColor.DARK_RED + "錯誤語法！ 使用 /bedhome [reload/help] 或是 /bedhome <lookup/teleport> <名字> <世界>";
      case ERR_CONSOLE_TELE:
        return  ChatColor.DARK_RED + "無法傳送至床位";
      case CONSOLE_PLAYER_TELE:
        return "$player 傳送到他的床位了";
      case CONSOLE_PLAYER_SET:
        return "$player 設置了床位";
      case BED_TELE:
        return  ChatColor.DARK_GREEN + "你傳送到你的床位了";
      case BED_SET:
        return ChatColor.DARK_GREEN +  "床位已設置";
      case TELE_OTHER_PLAYER:
        return ChatColor.DARK_GREEN +  "在 $world 傳送到 $player 的床位";
      case HELP_LOOKUP:
        return  ChatColor.DARK_GRAY + "撿查某人的床位";
      case HELP_TELE:
        return  ChatColor.DARK_GRAY + "傳送至某人的床位";
      case HELP_BEDHOME:
        return  ChatColor.DARK_GRAY + "重新載入插件 或是查詢幫助";
      case HELP_BED:
        return  ChatColor.DARK_GRAY +"傳送到你的床位";
      case NAME:
        return ChatColor.DARK_AQUA + "<名字>";
      case WORLD:
        return ChatColor.DARK_AQUA + "<世界>";
      case LOOKUP_RESULT:
        return ChatColor.GOLD + "$player 的床位位在:";
      case BED_COORDS:
        return ChatColor.RED + "你的床位已遭毀壞，以下是此床位的座標:";
      case BH_BAD_WORLD:
        return ChatColor.DARK_RED + "不存在的世界";
      case BH_VERSION:
        return ChatColor.BLUE + "此為 Superior_Slime 製作的 BedHome v$version";
      case BH_RELOADED:
        return ChatColor.BLUE + "設定與定位已重新載入";
      case BH_CONSOLE_CMD:
        return "只有玩家可以使用這項指令";
      default:
        return "error fetching locale string";
    }
  }
  public String getJapanese(LocaleStrings l) {
    switch (l) {
      case ERR_NO_BED:
        return null;
      case ERR_NO_PERMS:
        return null;
      case ERR_PLAYER_NO_BED:
        return null;
      case ERR_SYNTAX:
        return null;
      case ERR_CONSOLE_TELE:
        return null;
      case CONSOLE_PLAYER_TELE:
        return null;
      case CONSOLE_PLAYER_SET:
        return null;
      case BED_TELE:
        return null;
      case BED_SET:
        return null;
      case TELE_OTHER_PLAYER:
        return null;
      case HELP_LOOKUP:
        return null;
      case HELP_TELE:
        return null;
      case HELP_BEDHOME:
        return null;
      case HELP_BED:
        return null;
      case NAME:
        return null;
      case WORLD:
        return null;
      case LOOKUP_RESULT:
        return null;
      case BED_COORDS:
        return null;
      case BH_BAD_WORLD:
        return null;
      case BH_VERSION:
        return null;
      case BH_RELOADED:
        return null;
      case BH_CONSOLE_CMD:
        return null;
      default:
        return null;
    }
  }
}
