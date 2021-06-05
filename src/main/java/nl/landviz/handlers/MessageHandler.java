package nl.landviz.handlers;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;

import nl.landviz.Bread;
import nl.landviz.cache.ChannelCache;
import nl.landviz.cache.MemberCache;
import nl.landviz.cache.MessageCache;
import nl.landviz.helpers.BreadChannelHelper;
import nl.landviz.helpers.IsFrenchHelper;

public class MessageHandler {
    private Bread bread = Bread.getInstance();

    private String prefix = "🍞";
    
    private CommandHandler commandHandler = CommandHandler.getInstance();

    public static ChannelCache channelCache = ChannelCache.getInstance();
    public MemberCache memberCache = MemberCache.getInstance();
    public MessageCache messageCache = MessageCache.getInstance(); 

    public MessageHandler() {
        this.register();
    }

    private void register() {
        this.bread.gateway.on(MessageCreateEvent.class).subscribe(event -> {
            Message message = event.getMessage();

            String channelId = message.getChannelId().asString();

            boolean isBread = false;

            /**
             * Determine whether to do anything with the message
             */
            if (MessageHandler.channelCache.isCached(channelId)) {
                isBread = MessageHandler.channelCache.isBread(channelId);
            } else {
                if (message.getGuildId().isPresent()) {
                    MessageChannel channel = message.getChannel().block();

                    if (channel instanceof TextChannel textChannel) {
                        isBread = BreadChannelHelper.isBread(textChannel.getName());
                    }
                }

                MessageHandler.channelCache.setChannel(channelId, isBread);
            }

            if (!isBread) {
                return;
            }
            
            handleMessage(message);
        });
    }

    private void handleMessage(Message message) {
        boolean isFrench = false;

        /**
         * Guild ID will always be present at this point
         */
        String userGuid = IsFrenchHelper.getUserGuid(
            message.getGuildId().get().asLong(),
            message.getUserData().id().asLong()
        );

        if (memberCache.isCached(userGuid)) {
            isFrench = memberCache.isFrench(userGuid);
        } else {
            isFrench = IsFrenchHelper.isFrench(message.getAuthorAsMember().block().getDisplayName());

            memberCache.setFrench(userGuid, isFrench);
        }

        String reaction = isFrench ? "🥖" : "🍞";

        this.messageCache.cacheMessage(message.getId().asString(), reaction, message.getTimestamp());

        message.addReaction(ReactionEmoji.unicode(reaction)).subscribe();

        String messageContent = message.getContent().toLowerCase();

        if (!messageContent.startsWith(this.prefix)) {
            return;
        }

        this.commandHandler.handleCommand(messageContent.substring(this.prefix.length()), message);
    }
}
