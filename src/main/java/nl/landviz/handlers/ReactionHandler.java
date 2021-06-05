package nl.landviz.handlers;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.event.domain.message.ReactionRemoveEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import nl.landviz.Bread;
import nl.landviz.cache.MessageCache;
import nl.landviz.helpers.ReactionHelper;

public class ReactionHandler {
    private Bread bread = Bread.getInstance();

    private MessageCache messageCache = MessageCache.getInstance();

    public ReactionHandler() {
        this.register();
    }

    private void register() {
        this.bread.gateway.on(ReactionAddEvent.class).subscribe(event -> {
            ReactionEmoji genericReaction = event.getEmoji();

            String reaction = ReactionHelper.getReactId(genericReaction);

            if (this.messageCache.shouldAlterBreadScore(event.getMessageId().asString(), reaction)) {
                // Bread should be altered...
            }
        });

        this.bread.gateway.on(ReactionRemoveEvent.class).subscribe(event -> {
            ReactionEmoji genericReaction = event.getEmoji();

            String reaction = ReactionHelper.getReactId(genericReaction);

            if (this.messageCache.shouldAlterBreadScore(event.getMessageId().asString(), reaction)) {
                // Bread should be altered...
            }
        });
    }
}
