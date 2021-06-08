package nl.landviz.entity;

public class TopListMember {
    public String name;
    public int bread;

    public TopListMember(String name, int bread) {
        this.name = name;
        this.bread = bread;
    }

    public String toString() {
        return this.name + "|" + this.bread;
    }
}
