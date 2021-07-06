package nl.landviz.games;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import org.json.JSONObject;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.TextChannel;
import nl.landviz.App;
import nl.landviz.Bread;
import nl.landviz.storage.BreadStorage;
import nl.landviz.storage.LotteryStorage;

public class Lottery {
    private Date endTime;
    private Bread bread = Bread.getInstance();
    private BreadStorage breadStorage = BreadStorage.getInstance();
    private TextChannel channel;
    private String guildId;
    private LotteryStorage lotteryStorage = LotteryStorage.getInstance();

    private Map<String, Integer> buyers = new HashMap<>();

    public Lottery(String guildId, TextChannel channel) {
        this.channel = channel;
        this.guildId = guildId;

        int hours = Integer.parseInt(this.bread.dotenv.get("LOTTERY_HOURS_DURATION"));

        int timeInSeconds = hours * 60; // * 60;

        this.endTime = new Date(System.currentTimeMillis() + (timeInSeconds * 1000));

        TimerTask finishLottery = new FinishLottery(this);

        App.timer.schedule(finishLottery, this.endTime);
    }

    public Lottery(JSONObject recoveryData) {
        this.channel = (TextChannel) this.bread.gateway.getChannelById(Snowflake.of(recoveryData.getString("cid"))).block();
        this.guildId = recoveryData.getString("gid");

        // timer...
    }

    public void addTickets(String userId, int amount) {
        this.buyers.put(userId, amount);
    }

    public void finish() {
        int totalBread = this.getTotalBread();
        ArrayList<PotentialWinner> potentialWinners = this.getOdds(totalBread);

        double won = Math.random() * 100;

        for (PotentialWinner potentialWinner : potentialWinners) {
            won -= potentialWinner.winChance;

            if (won <= 0) {
                this.breadStorage.modifyBread(this.guildId, potentialWinner.userId, totalBread);

                this.channel.createMessage(
                    "Congratulations <@" + potentialWinner.userId + ">! You won the lottery, you now have " + this.breadStorage.getBread(this.guildId, potentialWinner.userId) + " bread! (+ " + totalBread + ")"
                ).subscribe();

                break;
            }
        }

        this.lotteryStorage.remove(this.channel.getId().asString());
    }

    private int getTotalBread() {
        int total = 0;

        for (Map.Entry<String, Integer> entry : this.buyers.entrySet()) {
            total += entry.getValue();
        }

        return total;
    }

    private ArrayList<PotentialWinner> getOdds(int totalBread) {
        ArrayList<PotentialWinner> potentialWinners = new ArrayList<>();

        float fixedChance = 50 / this.buyers.size();

        for (Map.Entry<String, Integer> entry : this.buyers.entrySet()) {
            potentialWinners.add(
                new PotentialWinner(
                    fixedChance + (100 / totalBread * entry.getValue()) / 2,
                    entry.getKey()
                )
            );
        }

        return potentialWinners;
    }
}

class FinishLottery extends TimerTask {
    private Lottery lottery;

    public FinishLottery(Lottery lottery) {
        super();

        this.lottery = lottery;
    }

    public void run() {
        this.lottery.finish();
    }
}

class PotentialWinner {
    public float winChance;
    public String userId;

    public PotentialWinner(float winChance, String userId) {
        this.winChance = winChance;
        this.userId = userId;
    }
}
