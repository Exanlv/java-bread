package nl.landviz;

import java.util.Timer;
import java.util.TimerTask;

import nl.landviz.daemons.CleanMessageCache;
import nl.landviz.daemons.SaveBread;
import nl.landviz.handlers.ChannelUpdateHandler;
import nl.landviz.handlers.GuildCreateHandler;
import nl.landviz.handlers.MemberUpdateHandler;
import nl.landviz.handlers.MessageHandler;
import nl.landviz.handlers.ReactionHandler;
import nl.landviz.storage.BreadStorage;

public class App {
    private static Timer timer = new Timer(true);
    private static Bread bread = Bread.getInstance();
    private static BreadStorage breadStorage = BreadStorage.getInstance();

    public static void main(String[] args) {
        bread.initialize();
        breadStorage.loadBread(bread.dotenv.get("FILE_STORAGE"));

        startDaemons();
        registerHandlers();

        bread.block();
    }

    private static void startDaemons() {
        TimerTask saveBread = new SaveBread(bread.dotenv.get("FILE_STORAGE"));
        TimerTask cleanCache = new CleanMessageCache();

        int saveInterval = Integer.parseInt(bread.dotenv.get("SAVE_INTERVAL")) * 1000;
        int cacheInterval = Integer.parseInt(bread.dotenv.get("CACHE_INTERVAL")) * 1000;

        long unixTimeStamp = System.currentTimeMillis();

        timer.schedule(
            saveBread,
            saveInterval - (unixTimeStamp % saveInterval),
            saveInterval
        );

        timer.schedule(
            cleanCache,
            // Add 30 seconds to make sure the tasks dont run at once
            cacheInterval - (unixTimeStamp % cacheInterval) + 30000,
            cacheInterval
        );
    }

    private static void registerHandlers() {
        new MessageHandler();
        new MemberUpdateHandler();
        new ChannelUpdateHandler();
        new ReactionHandler();
        new GuildCreateHandler();
    }
}
