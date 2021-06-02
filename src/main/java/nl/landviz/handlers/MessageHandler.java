package nl.landviz.handlers;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import nl.landviz.Bread;

public class MessageHandler {
    private Bread bread = Bread.getInstance();

    private String prefix = "🍞";
    
    private String[] breadChannelIdentifiers =  {"🍞", "bread"};

    public MessageHandler() {
        this.register();
    }

    private void register() {
        this.bread.gateway.on(MessageCreateEvent.class).subscribe(event -> {
            Message message = event.getMessage();

            message.getChannel().doOnNext(channel -> {
                if (!(channel instanceof TextChannel textChannel)) {
                    return;
                }

                String channelName = textChannel.getName().toLowerCase();

                boolean foundChannelIdentifier = false;

                for (int i = 0; i < this.breadChannelIdentifiers.length; i++) {
                    if (channelName.contains(this.breadChannelIdentifiers[i])) {
                        foundChannelIdentifier = true;
                        break;
                    }
                }

                if (!foundChannelIdentifier) {
                    return;
                }

                handleMessage(textChannel, message);
            }).subscribe();
        });
    }

    private void handleMessage(TextChannel channel, Message message) {
        message.getAuthorAsMember().doOnNext(member -> {
            message.addReaction(
                ReactionEmoji.unicode(
                    member.getDisplayName().contains("🇫🇷") ? "🥖" : "🍞"
                )
            ).subscribe();
        }).subscribe();

        String messageContent = message.getContent().toLowerCase();

        if (!messageContent.startsWith(this.prefix)) {
            return;
        }

        CommandHandler commandHandler = CommandHandler.getInstance();

        commandHandler.handleCommand(messageContent.substring(this.prefix.length()), channel, message);
    }
}
