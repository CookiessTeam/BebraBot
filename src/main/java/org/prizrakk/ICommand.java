package org.prizrakk;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.prizrakk.util.Values;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ICommand {


    /**
     * getName() - обработчик имен команд нужен чтобы зарегестрировать команду
     * getDescription() - обработчик описания нужен для обработки описания команд
     * getOptions() нужен для парсинга опций если они имееются
     *
     * void execute(SlashCommandInteractionEvent event) автономное создание метода в котором будет весь сок :)
     * @return
     */
    String getName();

    String getDescription();

    List<OptionData> getOptions();

    void execute(SlashCommandInteractionEvent event) throws SQLException;
}
