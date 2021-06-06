package nl.landviz.commands;

import java.util.ArrayList;

import discord4j.core.object.entity.Message;

public class ErrorCommand extends BaseCommand {
    public ErrorCommand(Message message) {
        super(message);
    }

    public void run(ArrayList<String> args) {
        this.message.getChannel().subscribe(channel -> {
            channel.createMessage("Something went wrong, try again later.").subscribe();
        });
    }
}
