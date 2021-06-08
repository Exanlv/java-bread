package nl.landviz.entity;

public class TopListUserInfo {
    public String userId;
    public int bread;

    public TopListUserInfo(String userId, int bread) {
        this.userId = userId;
        this.bread = bread;
    }

    public String toString() {
        return this.userId + "|" + this.bread;
    }
}
