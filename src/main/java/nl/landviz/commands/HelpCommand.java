package nl.landviz.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.TextChannel;

public class HelpCommand extends BaseCommand {
    public HelpCommand() { super(); }

    public void run() {
        TextChannel channel = (TextChannel) this.message.getChannel().block();

        channel.createMessage("HELP");
    }
}
