package nl.landviz.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

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

    public String[] getTopList() {
        ArrayList<String> topList = new ArrayList<String>();

        for (Map.Entry<String, Integer> entry : this.data.entrySet()) {
            try {
                for (int i = 0; i < topList.size() + 1; i++) {
                    String userId = topList.get(i);

                    int breadAmount = this.data.get(userId);

                    if (entry.getValue() > breadAmount) {
                        topList.add(i, entry.getKey());

                        break;
                    }
                }
            } catch (IndexOutOfBoundsException exception) {
                topList.add(entry.getKey());
            }
        }

        return topList.toArray(new String[topList.size()]);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        for (Map.Entry<String, Integer> entry : this.data.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }

        return json;
    }
}
