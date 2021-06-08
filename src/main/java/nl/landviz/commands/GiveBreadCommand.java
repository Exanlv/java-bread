package nl.landviz.commands;

import java.util.ArrayList;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import nl.landviz.Bread;
import nl.landviz.storage.BreadStorage;

public class GiveBreadCommand extends BaseCommand {
    private BreadStorage breadStorage = BreadStorage.getInstance();
    private Bread bread = Bread.getInstance();
    private String guildId;
    private String userId;

    public GiveBreadCommand(Message message) {
        super(message);
        this.guildId = this.message.getGuildId().get().asString();
        this.userId = this.message.getUserData().id().asString();
    }

    public void run(ArrayList<String> args) {
        if (args.size() == 0) {
            this.message.getChannel().subscribe(channel -> {
                channel.createMessage("Enter an amount").subscribe();
            });

            return;
        }

        try {
            int breadAmount = Integer.parseInt(args.get(0));

            for (Snowflake userId :  this.message.getUserMentionIds()) {
                this.breadStorage.modifyBread(this.guildId, userId.asString(), breadAmount);
            }

            this.message.getChannel().subscribe(channel -> {
                channel.createMessage("Bread added").subscribe();
            });
        } catch (NumberFormatException exception) {
            this.message.getChannel().subscribe(channel -> {
                channel.createMessage("Invalid amount").subscribe();
            });
        }
    }

    public boolean hasPermission() {
        for (String adminId : bread.adminIds) {
            if (adminId.equals(this.userId)) {
                return true;
            }
        }

        return false;
    }
}
