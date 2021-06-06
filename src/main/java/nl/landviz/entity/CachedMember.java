package nl.landviz.entity;

import nl.landviz.helpers.IsFrenchHelper;

public class CachedMember {
    public boolean isFrench;

    public String displayName;

    public CachedMember(String displayName) {
        this.update(displayName);
    }

    public void update(String displayName) {
        this.isFrench = IsFrenchHelper.isFrench(displayName);
        this.displayName = displayName;
    }
}
