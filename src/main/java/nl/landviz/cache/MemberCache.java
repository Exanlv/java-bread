package nl.landviz.cache;

import java.util.HashMap;
import java.util.Map;

public class MemberCache {
    private static MemberCache memberCache = new MemberCache();

    private Map<String, Boolean> cache = new HashMap<>();

    private MemberCache() {

    }

    public static MemberCache getInstance() {
        return memberCache;
    }

    public void setFrench(String userGuid, boolean isFrench) {
        this.cache.put(userGuid, isFrench);
    }

    public boolean isFrench(String userGuid) {
        return this.cache.get(userGuid);
    }

    public boolean isCached(String userGuid) {
        return this.cache.get(userGuid) != null;
    }
}
