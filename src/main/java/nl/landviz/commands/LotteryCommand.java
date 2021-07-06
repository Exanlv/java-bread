package nl.landviz.commands;

import java.util.ArrayList;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.TextChannel;
import nl.landviz.storage.BreadStorage;
import nl.landviz.storage.LotteryStorage;

public class LotteryCommand extends BaseCommand {
    private LotteryStorage lotteryStorage = LotteryStorage.getInstance();
    private BreadStorage breadStorage = BreadStorage.getInstance();

    public LotteryCommand(Message message) {
        super(message);
    }

    public void run(ArrayList<String> args) {
        boolean hasOnGoingLottery = this.lotteryStorage.hasLottery(this.message.getChannelId().asString());
        
        if (args.size() == 0) {
            this.message.getChannel().subscribe(channel -> {
                if (hasOnGoingLottery) {
                    channel.createMessage("There is already an ongoing bread lottery. Buy tickets using `🍞 lottery buy %amount%`").subscribe();
                } else {
                    channel.createMessage("There is currently no ongoing bread lottery. Start one using `🍞 lottery start %?amount%`").subscribe();
                }
            });

            return;
        }

        switch (args.get(0)) {
            case "start":
                if (hasOnGoingLottery) {
                    this.message.getChannel().subscribe(channel -> {
                        channel.createMessage("There is already an ongoing bread lottery. Buy tickets using `🍞 lottery:buy %amount%`").subscribe();
                    });
        
                    return;
                }

                this.lotteryStorage.startLottery(
                    (TextChannel) this.message.getChannel().block()
                );

                this.message.getChannel().subscribe(channel -> {
                    channel.createMessage("Lottery started. Buy tickets using `🍞 lottery:buy %amount%`").subscribe();
                });

                if (args.size() > 1) {
                    this.buyBread(args.get(1));
                }
            break;

            case "buy":
                if (args.size() > 1) {
                    this.buyBread(args.get(1));
                } else {
                    this.message.getChannel().subscribe(channel -> {
                        channel.createMessage("Please enter how many tickets you want to buy").subscribe();
                    });
                }
            break;
        }
    }

    private void buyBread(String amount) {
        try {
            int breadAmount = Integer.parseInt(amount);
            String userId = this.message.getUserData().id().asString();

            if (breadAmount < 1) {
                throw new NumberFormatException();
            }

            this.lotteryStorage.addTickets(
                this.message.getChannelId().asString(),
                userId,
                breadAmount
            );

            this.breadStorage.modifyBread(
                this.message.getGuildId().get().asString(),
                userId,
                breadAmount
            );

            this.message.getChannel().subscribe(channel -> {
                channel.createMessage("<@" + userId + "> you bought " + String.valueOf(breadAmount) + " tickets!").subscribe();
            });
        } catch (NumberFormatException exception) {
            this.message.getChannel().subscribe(channel -> {
                channel.createMessage("Invalid amount").subscribe();
            });
        }
    }
}
