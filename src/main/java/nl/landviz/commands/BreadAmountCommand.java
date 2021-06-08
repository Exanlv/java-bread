package nl.landviz.commands;

import java.util.ArrayList;

import discord4j.core.object.entity.Message;
import nl.landviz.storage.BreadStorage;

public class BreadAmountCommand extends BaseCommand {
    private BreadStorage breadStorage = BreadStorage.getInstance();
    private String userId;
    private String guildId;

    public BreadAmountCommand(Message message) {
        super(message);
        this.guildId = this.message.getGuildId().get().asString();
        this.userId = this.message.getUserData().id().asString();
    }

    public void run(ArrayList<String> args) {
        int breadAmount = this.breadStorage.getBread(
            this.guildId,
            this.userId
        );

        this.message.getChannel().subscribe(channel -> {
            channel.createMessage("<@" + this.userId + ">, you currently have " + breadAmount + " bread!").subscribe();
        });
    }
}
