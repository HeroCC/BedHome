package org.minecast.bedhome;

import org.bukkit.ChatColor;

public class ExtraLanguages {
  enum LocaleStrings {
    ERR_NO_BED, ERR_NO_PERMS, ERR_PLAYER_NO_BED, ERR_SYNTAX, ERR_CONSOLE_TELE, CONSOLE_PLAYER_TELE, CONSOLE_PLAYER_SET, BED_TELE, BED_SET, TELE_OTHER_PLAYER, HELP_LOOKUP, HELP_TELE, HELP_BEDHOME, HELP_BED, NAME, WORLD, LOOKUP_RESULT, BED_COORDS, BH_BAD_WORLD, BH_VERSION, BH_RELOADED, BH_CONSOLE_CMD
  }

  public static String getRussian(LocaleStrings l) {
    switch (l) {
      case ERR_NO_BED:
        return ChatColor.DARK_RED + "У вас нет кровати в этом мире, либо она была уничтожена.";
      case ERR_NO_PERMS:
        return ChatColor.DARK_RED + "У вас нет прав для данного действия.";
      case ERR_PLAYER_NO_BED:
        return ChatColor.DARK_RED + "$player не имеет кровати в $world.";
      case ERR_SYNTAX:
        return ChatColor.DARK_RED + "Игрок с таким именем не найден (учитывайте регистр!).";
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
        return null;
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
