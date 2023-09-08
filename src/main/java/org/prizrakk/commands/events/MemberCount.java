package org.prizrakk.commands.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.prizrakk.manager.LoggerManager;
import org.prizrakk.util.Values;

import java.util.Collection;

public class MemberCount extends ListenerAdapter {
    public LoggerManager log;
    JDA jda;
    private Collection guilds (JDA event) {
        Collection<Guild> guilds = event.getGuilds();
        return guilds;
    }


    public long globalMemberCount() {

        for (Guild guild : Values.guilds) {
            Values.globalUserCount += guild.getMemberCount();
        }
        return Values.globalUserCount;
    }

}
