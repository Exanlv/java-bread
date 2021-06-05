package nl.landviz.commands;

import discord4j.core.object.entity.Message;

public class PrivacyCommand extends BaseCommand {
    public PrivacyCommand(Message message) { super(message); }

    public void run() {
        String message = "**Data stored**\n"
                       + "The only data Bread (this bot) collects is how much bread (points) you have collected. This data is not shared with a third party.\n\n"
        
                       + "**Data removal**\n"
                       + "This data is not removed when you leave the guild you collected the bread on. If your points ever show up in the leaderboard, the username displayed will be Member has left the server.\n\n"
        
                       + "If you have any concerns and/or further questions, or want your data removed, contact me (bot owner/creator) on Github. <https://github.com/Exanlv/java-bread/>\n\n"
        
                       + "**Third party involvement**\n"
                       + "This bot is hosted on a Scaleway VPS. Their privacy policy may apply.\n\n"
        
                       + "If requested by law enforcement, your bread scores will not be given up voluntarily.";

        this.message.getChannel().subscribe(channel -> {
            channel.createMessage(message).subscribe();
        });
    }
}
