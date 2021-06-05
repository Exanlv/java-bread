package nl.landviz.handlers;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import nl.landviz.Bread;

public class ReactionHandler {
    private Bread bread = Bread.getInstance();

    public ReactionHandler() {
        this.register();
    }

    private void register() {
        this.bread.gateway.on(ReactionAddEvent.class).subscribe(event -> {
            ReactionEmoji reaction = event.getEmoji();

            
        });
    }
}
