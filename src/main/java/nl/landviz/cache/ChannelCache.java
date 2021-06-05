package nl.landviz.cache;

import java.util.HashMap;
import java.util.Map;

public class ChannelCache {
    private static ChannelCache channelCache = new ChannelCache();

    private Map<String, Boolean> cache = new HashMap<>();

    private ChannelCache() {

    }

    public static ChannelCache getInstance() {
        return channelCache;
    }

    public void setChannel(String channelId, boolean isBread) {
        this.cache.put(channelId, isBread);
    }

    public boolean isBread(String channelId) {
        return this.cache.get(channelId);
    }

    public boolean isCached(String channelId) {
        return this.cache.get(channelId) != null;
    }
}
