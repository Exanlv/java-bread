package nl.landviz.commands;

import java.util.ArrayList;

import discord4j.core.object.entity.Message;

public class NoPermissionsCommand extends BaseCommand {
    public NoPermissionsCommand(Message message) {
        super(message);
    }

    public void run(ArrayList<String> args) {
        this.message.getChannel().subscribe(channel -> {
            channel.createMessage("You dont have the required permissions to use this command").subscribe();
        });
    }
}
