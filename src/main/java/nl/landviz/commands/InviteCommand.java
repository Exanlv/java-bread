package nl.landviz.commands;

import java.util.ArrayList;

import discord4j.core.object.entity.Message;

public class InviteCommand extends BaseCommand {
    public InviteCommand(Message message) {
        super(message);
    }

    public void run(ArrayList<String> args) {
        this.message.getChannel().subscribe(channel -> {
            channel.createMessage("https://discord.com/oauth2/authorize?client_id=634812523685609532&scope=bot&permissions=3136").subscribe();
        });
    }
}
