package nl.landviz.commands;

import java.util.ArrayList;

import discord4j.core.object.entity.Message;
import nl.landviz.storage.BreadStorage;

public class GambleCommand extends BaseCommand {
    private BreadStorage breadStorage = BreadStorage.getInstance();

    private String userId;
    private String guildId;
    private String userTag;

    public GambleCommand(Message message) {
        super(message);
        this.userId = this.message.getUserData().id().asString();
        this.guildId = this.message.getGuildId().get().asString();
        this.userTag = "<@" + this.userId + ">";
    }

    public void run(ArrayList<String> args) {
        if (args.size() == 0) {
            this.message.getChannel().subscribe(channel -> {
                channel.createMessage(this.userTag + " specify how much bread you want to gamble").subscribe();
            });

            return;
        }

        int amount;
        String userEnteredAmount = args.get(0);

        if (userEnteredAmount.equals("all")) {
            amount = this.getBread();
        } else if (userEnteredAmount.equals("half")) {
            amount = this.getBread() / 2;
        } else {
            try {
                amount = Integer.parseInt(userEnteredAmount);

                if (amount > this.getBread()) {
                    this.message.getChannel().subscribe(channel -> {
                        channel.createMessage(this.userTag + " you dont have this much bread").subscribe();
                    });
        
                    return;
                }
            } catch (NumberFormatException exception) {
                this.message.getChannel().subscribe(channel -> {
                    channel.createMessage("Invalid amount").subscribe();
                });
    
                return;
            }
        }

        /**
         * Gamble amount has to be a final in order to be used in the lambda scope
         */
        final int gambleAmount = amount;
        boolean win = Math.random() > 0.45; // 55% win chance

        if (win) {
            this.modifyBread(gambleAmount);

            this.message.getChannel().subscribe(channel -> {
                channel.createMessage(
                    this.userTag + " you won! You now have " + this.getBread() + " bread! (+" + gambleAmount + ")"
                ).subscribe();
            });
        } else {
            this.modifyBread(0 - gambleAmount);

            this.message.getChannel().subscribe(channel -> {
                channel.createMessage(
                    this.userTag + " you lost! You now have " + this.getBread() + " bread! (-" + gambleAmount + ")"
                ).subscribe();
            });
        }
    }

    private int getBread() {
        return breadStorage.getBread(
            this.guildId,
            this.userId
        );
    }

    private void modifyBread(int amount) {
        this.breadStorage.modifyBread(
            this.guildId,
            this.userId,
            amount
        );
    }
}
