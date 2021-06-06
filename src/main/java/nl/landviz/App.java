package nl.landviz;

import java.util.Timer;
import java.util.TimerTask;

import nl.landviz.daemons.CleanMessageCache;
import nl.landviz.daemons.SaveBread;
import nl.landviz.handlers.ChannelUpdateHandler;
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

        timer.schedule(saveBread, 1000, 3000);
        timer.schedule(cleanCache, 1000, 120000);
    }

    private static void registerHandlers() {
        new MessageHandler();
        new MemberUpdateHandler();
        new ChannelUpdateHandler();
        new ReactionHandler();
    }
}
