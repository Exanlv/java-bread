package nl.landviz.commands;

import java.util.ArrayList;

import discord4j.core.object.entity.Message;

public class HelpCommand extends BaseCommand {
    public HelpCommand(Message message) { super(message); }

    public void run(ArrayList<String> args) {
        String message = "`🍞 help` - Shows this menu\n"
                       + "`🍞 invite` - Invite Bread to a different server\n" 
                       + "`🍞 top` - Display the bread leaderboard\n" 
                       + "`🍞 me` - Display the amount of bread you've collected\n" 
                       + "`🍞 gamble %amount%` - Waste away your life savings\n" 
                       + "`🍞 privacy` - Privacy policy";

        this.message.getChannel().subscribe(channel -> {
            channel.createMessage(message).subscribe();
        });
    }
}
