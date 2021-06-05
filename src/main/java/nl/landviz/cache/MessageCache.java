package nl.landviz.cache;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import nl.landviz.entity.CachedMessage;

public class MessageCache {
    private static MessageCache messageCache = new MessageCache();

    private static int breadTimeLimit = 8;

    private Map<String, CachedMessage> cache = new HashMap<>();

    private MessageCache() {

    }

    public static MessageCache getInstance() {
        return messageCache;
    }

    public void cacheMessage(String messageId, String emoji, Instant createdTime) {
        this.cache.put(
            messageId,
            new CachedMessage(
                createdTime,
                emoji
            )
        );
    }

    public CachedMessage getMessage(String messageId) {
        return this.cache.get(messageId);
    }

    public boolean shouldAlterBreadScore(String messageId, String emoji) {
        CachedMessage message = this.getMessage(messageId);

        System.out.println(message);

        if (message == null || !message.react.equals(emoji)) {
            return false;
        }

        Instant now = Instant.now();

        Duration timeAgo = Duration.between(message.createTime, now);

        if (timeAgo.toHours() > breadTimeLimit) {
            this.cache.remove(messageId);

            return false;
        }

        return true;
    }
}
