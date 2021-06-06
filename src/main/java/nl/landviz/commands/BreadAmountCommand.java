package nl.landviz.commands;

import discord4j.core.object.entity.Message;
import nl.landviz.storage.BreadStorage;

public class BreadAmountCommand extends BaseCommand {
    private BreadStorage breadStorage = BreadStorage.getInstance();

    public BreadAmountCommand(Message message) {
        super(message);
    }

    public void run() {
        int breadAmount = this.breadStorage.getBread(
            this.message.getGuildId().get().asString(),
            this.message.getUserData().id().asString()
        );

        this.message.getChannel().subscribe(channel -> {
            channel.createMessage("You currently have " + breadAmount + " bread!").subscribe();
        });
    }
}
