package org.prizrakk;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.prizrakk.commands.fun.*;
import org.prizrakk.commands.info.*;
import org.prizrakk.commands.modals.EmbedModal;
import org.prizrakk.commands.modals.SuggestModals;
import org.prizrakk.commands.music.*;
import org.prizrakk.db.Database;
import org.prizrakk.manager.CommandManager;
import org.prizrakk.manager.ConsoleManager;
import org.prizrakk.manager.LoggerManager;
import org.prizrakk.util.Values;
import org.prizrakk.manager.ConfigManager;

import java.io.File;
import java.sql.SQLException;


public class Main extends ListenerAdapter {
    private static final String TOKEN = "";

    public LoggerManager log = new LoggerManager();
    private static File configFile;

    public static void main(String[] args) {
        ConfigManager configManager = new ConfigManager();
        configFile = new File("config.properties");
        if (!configFile.exists()) {
            configManager.setProperty("token", "token replace here");
            configManager.setProperty("jdbc", "jdbc:");
            configManager.setProperty("password", "password");
            configManager.setProperty("login", "user");
            configManager.saveConfig();
        }
        Main nonStatic = new Main();
        jdaLoad();
        nonStatic.log.info("Запуск системы логирования");
        nonStatic.JDBCConnect();
        ConsoleManager console = new ConsoleManager();
        console.console();

    }
    @Override
    public void onReady(ReadyEvent event) {
        log.info("Бот ");
        log.info("Приглашение " + event.getJDA().getInviteUrl(Permission.ADMINISTRATOR));
        Values.guildCount = event.getGuildTotalCount();
        JDA jda = event.getJDA();
        Values.guilds = jda.getGuilds();
        for (Guild guild : Values.guilds) {
            Values.globalUserCount += guild.getMemberCount();
        }
    }
    public static void jdaLoad() {
        ConfigManager config = new ConfigManager();
        JDA jda = JDABuilder.createDefault(config.getProperty("token"))
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("BebraBotTV"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableCache(CacheFlag.ONLINE_STATUS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .build();
        jda.addEventListener(new Main());


        CommandManager manager = new CommandManager();
        /* music command */
        manager.add(new Play());
        manager.add(new Skip());
        manager.add(new Stop());
        manager.add(new NowPlaying());
        manager.add(new Queue());
        manager.add(new Repeat());

        /*Fun Command **/
        manager.add(new Calc());
        manager.add(new Dogs());
        manager.add(new HttpCat());
        manager.add(new Tyan());

        /*Предложения**/
        manager.add(new Suggest());
        jda.addEventListener(new SuggestModals());
        /* Ембеды **/
        manager.add(new Embed());
        jda.addEventListener(new EmbedModal());

        /* Информационые **/
        manager.add(new Help());
        manager.add(new UserInfo());
        manager.add(new ServerInfo());
        manager.add(new About());
        jda.addEventListener(manager);
    }

    public void JDBCConnect() {
        try {
            Database database = new Database(this);
            database.initializeDatabase();
        } catch (SQLException ex) {
            log.error("Error Database!");
            ex.printStackTrace();
        }
    }
}