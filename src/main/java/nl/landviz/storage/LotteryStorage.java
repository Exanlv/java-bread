package nl.landviz.storage;

import java.util.HashMap;
import java.util.Map;

import discord4j.core.object.entity.channel.TextChannel;
import nl.landviz.games.Lottery;

public class LotteryStorage {
    private static LotteryStorage instance = new LotteryStorage();

    private Map<String, Lottery> data = new HashMap<>();

    private LotteryStorage() {

    }

    public static LotteryStorage getInstance() {
        return instance;
    }

    public void loadLotteries(String storageDir) {

    }

    public void startLottery(TextChannel channel) {
        this.data.put(channel.getId().asString(), new Lottery(channel.getGuildId().asString(), channel));
    }

    public boolean hasLottery(String channelId) {
        return this.data.containsKey(channelId);
    }

    public void addTickets(String channelId, String userId, int amount) {
        this.data.get(channelId).addTickets(userId, amount);
    }

    public void remove(String channelId) {
        this.data.remove(channelId);
    }
}
