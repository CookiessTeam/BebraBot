package org.prizrakk.manager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.prizrakk.util.Values;

import java.util.Scanner;
public class ConsoleManager extends ListenerAdapter {
    public JDA jda;
    LoggerManager log = new LoggerManager();
    public void console() {

        log.info("Запуск консоли!");

        while (true) {
            Scanner scanner = new Scanner(System.in);

            String in = scanner.nextLine();
            switch (in) {
                case "help" :
                    log.command("help","");
                    log.command("help","============Список доступных комнад!============");
                    log.command("help","help - выводит список команд!");
                    log.command("help","stop - останавливает бота");
                    log.command("help","stats - показывает статистику бота");
                    log.command("help","guildlist - показывает список сообществ где находится бот");
                    log.command("help","============powered by prizrakk!================");
                    log.command("help"," ");
                    break;
                case "stop" :
                    log.command("stop","Остановка бота!");
                    System.exit(0);
                    break;
                case "stats" :
                    Runtime runtime = Runtime.getRuntime();
                    long memoryUsed = (runtime.totalMemory() - runtime.freeMemory()) / 1048576;
                    long memoryAvailable = (runtime.maxMemory() - memoryUsed) / 1048576;
                    log.command("stats","Оперативная память: " + memoryUsed + "/" + memoryAvailable);
                    log.command("stats","Количество сообществ: " + Values.guildCount);
                    log.command("stats","Общее количество пользователей: " + Values.globalUserCount);
                    break;
                case "guildlist" :
                    for (Guild guild : Values.guilds) {
                        log.command("GuildList", "--------------------------------------------");
                        log.command("GuildList"," | Guild Name: " + guild.getName());
                        log.command("GuildList"," | Guild MemberCount: " + guild.getMemberCount());
                        log.command("GuildList"," | Guild BoostCount: " + guild.getBoostCount());
                        log.command("GuildList"," | Guild ID: " + guild.getId());
                        log.command("GuildList", "--------------------------------------------");
                    }
                    break;
                case "test_log" :
                    log.info("INFO");
                    log.command("COMMAND", "logline");
                    log.error("errorline");
                    log.warn("warnline");
                    break;
                default:
                    log.error("ERROR 404: Command not found! Usage 'help'");
            }
        }
    }
}
