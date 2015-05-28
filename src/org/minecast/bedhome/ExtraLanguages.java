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
  public static String getSimplifiedChinese(LocaleStrings l) {
    switch (l) {
      case ERR_NO_BED:
        return  ChatColor.DARK_RED + "你在这个世界中并没有床, 或者床已经被摧毁了。";
     

      case ERR_NO_PERMS:
        return  ChatColor.DARK_RED + "你没有权限";
      case ERR_PLAYER_NO_BED:
        return  ChatColor.DARK_RED + "你指定的玩家 ($player) 在世界 $world 中没有床。";
      case ERR_BAD_PLAYER:
        return  ChatColor.DARK_RED + "你指定的玩家并不存在(区分大小写)!";
      case ERR_SYNTAX:
        return  ChatColor.DARK_RED + "语法错误! 用法: /bedroom [reload/help] 或者 /bedroom <lookup/teleport> <玩家名> <世界名>";
      case ERR_CONSOLE_TELE:
        return  ChatColor.DARK_RED + "控制台并不能被传送!";
      case CONSOLE_PLAYER_TELE:
        return "$player 已被传送到他的床前。";
      case CONSOLE_PLAYER_SET:
        return "$player 已经设置了他的床的位置。";
      case BED_TELE:
        return  ChatColor.DARK_GREEN + "你已经被传送到你的床前。";
      case BED_SET:
        return ChatColor.DARK_GREEN +  "你的床已被设置。";
      case TELE_OTHER_PLAYER:
        return ChatColor.DARK_GREEN +  "你已被传送到玩家 $player 在世界 $world 中的床上。";
      case HELP_LOOKUP:
        return  ChatColor.DARK_GRAY + "查找某人的床";
      case HELP_TELE:
        return  ChatColor.DARK_GRAY + "传送到某人的床边";
      case HELP_BEDHOME:
        return  ChatColor.DARK_GRAY + "刷新插件或获取帮助";
      case HELP_BED:
        return  ChatColor.DARK_GRAY +"传送到你床边";
      case NAME:
        return ChatColor.DARK_AQUA + "<玩家名>";
      case WORLD:
        return ChatColor.DARK_AQUA + "<世界名>";
      case LOOKUP_RESULT:
        return ChatColor.GOLD + "$player 的床位于:";
      case BED_COORDS:
        return ChatColor.RED + "你的床被毁了, 但是它的坐标还有:";
      case BH_BAD_WORLD:
        return ChatColor.DARK_RED + "那个世界并不存在!";
      case BH_VERSION:
        return ChatColor.BLUE + "BedHome 版本 $version 作者: Superior_Slime";
      case BH_RELOADED:
        return ChatColor.BLUE + "设置和语言已刷新!";
      case BH_CONSOLE_CMD:
        return "只有玩家才可使用这个命令!";
      case ERR_NO_BED_OTHER:
        return  ChatColor.DARK_RED + "你在世界 $world 中没有床, 或者已经毁掉了。";
      default:
        return "error fetching locale string";
    }
  }
  public static String getKorean(LocaleStrings l) {
    switch (l) {
      case ERR_NO_BED:
        return  ChatColor.DARK_RED + "이 월드에 침대가 없거나 파괴되었습니다.";
     

      case ERR_NO_PERMS:
        return  ChatColor.DARK_RED + "권한이 없으십니다.";
      case ERR_PLAYER_NO_BED:
        return  ChatColor.DARK_RED + "당신이 원한 플레이어 ($player)는 침대가 $world에 없습니다.";
      case ERR_BAD_PLAYER:
        return  ChatColor.DARK_RED + "당신이 원한 플레이어는 존재하지 않습니다.";
      case ERR_SYNTAX:
        return  ChatColor.DARK_RED + "문법이 틀렸습니다! /bedhome [reload/help] 나 /bedhome <lookup/teleport> <이름> <월드> 를 사용하십시오.";
      case ERR_CONSOLE_TELE:
        return  ChatColor.DARK_RED + "CONSOLE 침대로 이동할 수 없습니다!";
      case CONSOLE_PLAYER_TELE:
        return "$player가 침대로 순간이동하였습니다.。";
      case CONSOLE_PLAYER_SET:
        return "$player가 침대를 정하였습니다.";
      case BED_TELE:
        return  ChatColor.DARK_GREEN + "침대로 순간이동하였습니다.";
      case BED_SET:
        return ChatColor.DARK_GREEN +  "침대가 정해졌습니다.";
      case TELE_OTHER_PLAYER:
        return ChatColor.DARK_GREEN +  "$world 월드의 $player''s 의 침대로 이동하였습니다.";
      case HELP_LOOKUP:
        return  ChatColor.DARK_GRAY + "어떤 사람의 침대를 검색";
      case HELP_TELE:
        return  ChatColor.DARK_GRAY + "어떤 사람의 침대로 이동";
      case HELP_BEDHOME:
        return  ChatColor.DARK_GRAY + "Reload the plugin or get help.";
      case HELP_BED:
        return  ChatColor.DARK_GRAY +"자신의 침대로 이동한다.";
      case NAME:
        return ChatColor.DARK_AQUA + "<이름>";
      case WORLD:
        return ChatColor.DARK_AQUA + "<월드>";
      case LOOKUP_RESULT:
        return ChatColor.GOLD + "$player 의 침대 위치:";
      case BED_COORDS:
        return ChatColor.RED + "당신의 침대는 파괴되었습니다 아무튼 그곳의 위치는:";
      case BH_BAD_WORLD:
        return ChatColor.DARK_RED + "월드가 존재하지 않습니다!";
      case BH_VERSION:
        return ChatColor.BLUE + "BedHome $version에 의해Superior_Slime";
      case BH_RELOADED:
        return ChatColor.BLUE + "Reloaded!";
      case BH_CONSOLE_CMD:
        return "플레이어만이 이 커맨드를 사용할 수 있습니다!";
      case ERR_NO_BED_OTHER:
        return  ChatColor.DARK_RED + "$world 월드에 당신의 침대가 없거나 파괴되었습니다.。";
      default:
        return "error fetching locale string";
    }
  }
  public static String getJapanese(LocaleStrings l) {
    switch (l) {
      case ERR_NO_BED:
        return  ChatColor.DARK_RED + "あなたはこの世界にベッドを所有していません。または、破壊されています。";
      case ERR_NO_BED_OTHER:
        return  ChatColor.DARK_RED + "あなたは $world にベッドを所有していません。または、破壊されています。";

      case ERR_NO_PERMS:
        return  ChatColor.DARK_RED + "許可がありません。";
      case ERR_PLAYER_NO_BED:
        return  ChatColor.DARK_RED + "そのプレイヤー ($player) は $world にベッドを所有していません。";
      case ERR_BAD_PLAYER:
        return  ChatColor.DARK_RED + "そのプレイヤーは存在しません。 (大文字・小文字まで正確に入力してください。)";
      case ERR_SYNTAX:
        return  ChatColor.DARK_RED + "シンタックスが正しくありません。　このコマンドを使ってください: /bedhome [reload/help] または /bedhome <lookup/teleport> <名前> <ワールド>";
      case ERR_CONSOLE_TELE:
        return  ChatColor.DARK_RED + "コンソールはベッドにテレポートできません!";
      case CONSOLE_PLAYER_TELE:
        return "$player がベッドにテレポートしました。";
      case CONSOLE_PLAYER_SET:
        return "$player がベッドを設定しました。";
      case BED_TELE:
        return  ChatColor.DARK_GREEN + "あなたはベッドにテレポートされました。";
      case BED_SET:
        return ChatColor.DARK_GREEN +  "あなたはベッドを設定しました。";
      case TELE_OTHER_PLAYER:
        return ChatColor.DARK_GREEN +  "あなたは $world にある $player のベッドにテレポートされました。";
      case HELP_LOOKUP:
        return  ChatColor.DARK_GRAY + "だれかのベッドを検索・参照します。";
      case HELP_TELE:
        return  ChatColor.DARK_GRAY + "だれかのベッドにテレポートします。";
      case HELP_BEDHOME:
        return  ChatColor.DARK_GRAY + "プラグインを再読み込みします。またはヘルプを表示します。";
      case HELP_BED:
        return  ChatColor.DARK_GRAY +"あなたのベッドにテレポートします。";
      case NAME:
        return ChatColor.DARK_AQUA + "<名前>";
      case WORLD:
        return ChatColor.DARK_AQUA + "<ワールド>";
      case LOOKUP_RESULT:
        return ChatColor.GOLD + "$player のベッドはここにあります:";
      case BED_COORDS:
        return ChatColor.RED + "あなたのベッドは破壊されました。座標:";
      case BH_BAD_WORLD:
        return ChatColor.DARK_RED + "そのワールドは存在しません!";
      case BH_VERSION:
        return ChatColor.BLUE + "BedHomeの v$version Superior_Slimeの";
      case BH_RELOADED:
        return ChatColor.BLUE + "コンフィグとロケールを再読み込みしました。";
      case BH_CONSOLE_CMD:
        return "そのコマンドはプレイヤーにしか使えません!";
      default:
        return "error fetching locale string";
    }
  }
}
