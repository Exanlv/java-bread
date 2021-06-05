package nl.landviz.commands;

import discord4j.core.object.entity.Message;

public class ErrorCommand extends BaseCommand {
    public ErrorCommand(Message message) {
        super(message);
    }

    public void run() {
        this.message.getChannel().subscribe(channel -> {
            channel.createMessage("Something went wrong, try again later.").subscribe();
        });
    }
}
