package org.prizrakk.commands.info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.prizrakk.ICommand;
import org.prizrakk.Main;
import org.prizrakk.db.Database;
import org.prizrakk.manager.GDBV;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class UserInfo implements ICommand {
    /**
     * getName() - обработчик имен команд нужен чтобы зарегестрировать команду
     * getDescription() - обработчик описания нужен для обработки описания команд
     * getOptions() нужен для парсинга опций если они имееются
     * <p>
     * void execute(SlashCommandInteractionEvent event) автономное создание метода в котором будет весь сок :)
     *
     * @return
     */
    @Override
    public String getName() {
        return "userinfo";
    }

    @Override
    public String getDescription() {
        return "Показывает статистику пользователя";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }
    private Main main;
    /*
    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByNICK(player.getName());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getName(), 0, "default", 0,0, 0, 0,0.0, new Date(), new Date());
            database.createPlayerStats(playerStats);
        }

        return playerStats;
    }

    */
    @Override
    public void execute(SlashCommandInteractionEvent event) throws SQLException {
        Database database = new Database(main);
        GDBV gdbv = database.findPlayerStatsByNICK(event.getMember().getId());
        if (gdbv == null) {
            gdbv = new GDBV(event.getMember().getNickname(), 0, 0,0,0,0, event.getMember().getId());
            database.createUserStats(gdbv);
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.GREEN);
        embed.setTitle("Информация о пользователе");
        embed.addField("Информация", "баланс: " + gdbv.getBalance(), true);
    }
}
