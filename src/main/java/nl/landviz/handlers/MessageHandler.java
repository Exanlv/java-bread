package nl.landviz.handlers;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

import nl.landviz.Bread;

public class MessageHandler {
    private Bread bread = Bread.getInstance();

    private String prefix = "🍞";
    
    private String[] breadChannelIdentifiers =  {"🍞", "bread"};

    public MessageHandler() {
        this.register();
    }

    private void register() {
        bread.api.addMessageCreateListener(messageCreateEvent -> {
            TextChannel genericChannel = messageCreateEvent.getChannel();

            if (!(genericChannel instanceof ServerTextChannel channel)) {
                return;
            }
    
            String channelName = channel.getName().toLowerCase();
    
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
            
            handleMessage(channel, messageCreateEvent.getMessage());
        });
    }

    private void handleMessage(ServerTextChannel channel, Message message) {
        message.addReaction(message.getAuthor().getDisplayName().contains("🇫🇷") ? "🥖" : "🍞");

        String messageContent = message.getContent().toLowerCase();

        if (!messageContent.startsWith(this.prefix)) {
            return;
        }

        CommandHandler.handleCommand(messageContent.substring(this.prefix.length()), channel, message);
    }
}
