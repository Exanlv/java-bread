package nl.landviz.entity;

import java.time.Instant;

public class CachedMessage {
    public Instant createTime;
    public String react;

    public CachedMessage(Instant createTime, String react) {
        this.createTime = createTime;
        this.react = react;
    }
}
