package nl.landviz.cache;

import java.util.HashMap;
import java.util.Map;

import nl.landviz.entity.CachedMember;

public class MemberCache {
    private static MemberCache memberCache = new MemberCache();

    private Map<String, CachedMember> cache = new HashMap<>();

    private MemberCache() {

    }

    public static MemberCache getInstance() {
        return memberCache;
    }

    public void setFrench(String userGuid, String displayName) {
        this.cache.put(userGuid, new CachedMember(displayName));
    }

    public boolean isFrench(String userGuid) {
        return this.cache.get(userGuid).isFrench;
    }

    public boolean isCached(String userGuid) {
        return this.cache.containsKey(userGuid);
    }

    public String getUsername(String userGuid) {
        return this.cache.get(userGuid).displayName;
    }

    public void remove(String userGuid) {
        this.cache.remove(userGuid);
    }
}
