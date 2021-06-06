package nl.landviz.commands;

import java.util.ArrayList;

import discord4j.core.object.entity.Message;

public class UnknownCommand extends BaseCommand {
    public UnknownCommand(Message message) {
        super(message);
    }

    public void run(ArrayList<String> args) {
        this.message.getChannel().subscribe(channel -> {
            channel.createMessage("Unknown command. Use `🍞 help` for a list of commands.").subscribe();
        });
    }
}
