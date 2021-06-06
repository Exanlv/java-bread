package nl.landviz.handlers;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.event.domain.message.ReactionRemoveEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import nl.landviz.Bread;
import nl.landviz.cache.MessageCache;
import nl.landviz.helpers.ReactionHelper;
import nl.landviz.storage.BreadStorage;

public class ReactionHandler {
    private Bread bread = Bread.getInstance();

    private BreadStorage breadStorage = BreadStorage.getInstance();

    private MessageCache messageCache = MessageCache.getInstance();

    public ReactionHandler() {
        this.register();
    }

    private void register() {
        this.bread.gateway.on(ReactionAddEvent.class).subscribe(event -> {
            if (!event.getGuildId().isPresent()) {
                return;
            }

            String userId = event.getUserId().asString();

            if (userId.equals(this.bread.ownId)) {
                return;
            }

            ReactionEmoji genericReaction = event.getEmoji();

            String reaction = ReactionHelper.getReactId(genericReaction);

            if (this.messageCache.shouldAlterBreadScore(event.getMessageId().asString(), reaction)) {
                this.breadStorage.modifyBread(
                    event.getGuildId().get().asString(),
                    userId,
                    1
                );
            }
        });

        this.bread.gateway.on(ReactionRemoveEvent.class).subscribe(event -> {
            if (!event.getGuildId().isPresent()) {
                return;
            }

            String userId = event.getUserId().asString();

            if (userId.equals(this.bread.ownId)) {
                return;
            }

            ReactionEmoji genericReaction = event.getEmoji();

            String reaction = ReactionHelper.getReactId(genericReaction);

            if (this.messageCache.shouldAlterBreadScore(event.getMessageId().asString(), reaction)) {
                this.breadStorage.modifyBread(
                    event.getGuildId().get().asString(),
                    userId,
                    -1
                );
            }
        });
    }
}
