package org.prizrakk.commands.info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.prizrakk.ICommand;
import org.prizrakk.commands.events.MemberCount;
import org.prizrakk.util.Values;

import java.util.List;

public class About implements ICommand {
    @Override
    public String getName() {
        return "about";
    }

    @Override
    public String getDescription() {
        return "О боте";
    }
    public JDA jda;

    @Override
    public List<OptionData> getOptions() {
        return null;
    }


    @Override
    public void execute(SlashCommandInteractionEvent event) {

        //SelfUser selfUser = jda.getSelfUser();
        //String avatarUrl = selfUser.getAvatarUrl();
          // your JDA instance
        MemberCount member = new MemberCount();
        EmbedBuilder embed = new EmbedBuilder();
        SelfUser selfUser = event.getJDA().getSelfUser();
        String avatarUrl = selfUser.getAvatarUrl();
        embed.setTitle("О боте");
        embed.setThumbnail(avatarUrl);
        embed.addField("О боте","Описание: разработал его пользователь под ником prizrakk просто так :) "
                + "\n" + "*Версия JDA:* `5.0.0-beta.12`"
                + "\n" + "*Версия LavaPlayer:* `1.3.77`"
                + "\n" + "*Версия java:* `1.8_372 by BellSoft`"
                + "\n" + "*Версия бота:* " + Values.versionBot,true);
        embed.addField("Статистика","Кол-во сообществ: " + Values.guildCount
                + "\n" + "*Общее кол-во юзеров с сообществ:* " + member.globalMemberCount(), true);
        Button offDiscord = Button.link("https://http.cat/404", "Скоро.....");
        event.replyEmbeds(embed.build())
                .setActionRow(offDiscord)
                .queue();
    }
}
