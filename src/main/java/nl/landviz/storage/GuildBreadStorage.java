package nl.landviz.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import nl.landviz.entity.TopListUserInfo;

public class GuildBreadStorage {
    private Map<String, Integer> data = new HashMap<>();

    public GuildBreadStorage() {
        
    }

    public GuildBreadStorage(JSONObject jsonObject) {
        Iterator<String> iterator = jsonObject.keys();

        while (iterator.hasNext()) {
            String memberId = iterator.next();

            this.data.put(memberId, jsonObject.getInt(memberId));
        }
    }

    public void loadBread() {
        
    }

    public void modifyBread(String userId, int amount) {
        if (this.data.containsKey(userId)) {
            this.data.put(userId, this.data.get(userId) + amount);
        } else {
            this.data.put(userId, amount);
        }
    }

    public int getBreadAmount(String userId) {
        if (!this.data.containsKey(userId)) {
            return 0;
        }

        return this.data.get(userId);
    }

    public TopListUserInfo[] getTopList() {
        ArrayList<TopListUserInfo> topList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : this.data.entrySet()) {
            try {
                for (int i = 0; i < topList.size() + 1; i++) {
                    int breadAmount = topList.get(i).bread;

                    if (entry.getValue() > breadAmount) {
                        topList.add(i, new TopListUserInfo(entry.getKey(), entry.getValue()));

                        break;
                    }
                }
            } catch (IndexOutOfBoundsException exception) {
                topList.add(
                    new TopListUserInfo(
                        entry.getKey(),
                        entry.getValue()
                    )
                );
            }
        }

        return topList.toArray(new TopListUserInfo[topList.size()]);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        for (Map.Entry<String, Integer> entry : this.data.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }

        return json;
    }
}
