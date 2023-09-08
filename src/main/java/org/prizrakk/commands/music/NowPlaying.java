package org.prizrakk.commands.music;

import org.prizrakk.ICommand;
import org.prizrakk.lavaplayer.GuildMusicManager;
import org.prizrakk.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.prizrakk.util.Values;

import java.util.List;

public class NowPlaying implements ICommand {
    @Override
    public String getName() {
        return "nowplaying";
    }

    @Override
    public String getDescription() {
        return "Показывает текущую музыку";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()) {
            event.reply("Тебя нет в голосовом канале я не могу запустить без тебя музыку :(").queue();
            return;
        }

        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()) {
            event.reply("Упс подождите меня я забыл зайти").queue();
            return;
        }

        if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            event.reply("Тебя нет в конале со мной приди ко мне").queue();
            return;
        }

        GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
        if(guildMusicManager.getTrackScheduler().getPlayer().getPlayingTrack() == null) {
            event.reply("Я уже за стойкой диджея!").queue();
            return;
        }
        AudioTrackInfo info = guildMusicManager.getTrackScheduler().getPlayer().getPlayingTrack().getInfo();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Я играю щас:");
        embedBuilder.setDescription("**Имя:** `" + info.title + "`");
        embedBuilder.appendDescription("\n**Автор:** `" + info.author + "`");
        embedBuilder.appendDescription("\n**Ссылка:** `" + info.uri + "`");
        embedBuilder.appendDescription("\n**Репит:** `" + Values.isRepeat + "`");
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
