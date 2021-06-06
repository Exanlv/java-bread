package nl.landviz.daemons;

import java.util.TimerTask;

import nl.landviz.cache.MessageCache;

public class CleanMessageCache extends TimerTask {
    private MessageCache messageCache = MessageCache.getInstance();

    public CleanMessageCache() {
        
    }

    public void run() {
        this.messageCache.cleanCache();
    }
}
