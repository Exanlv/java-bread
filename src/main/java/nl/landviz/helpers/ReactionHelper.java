package nl.landviz.helpers;

import discord4j.core.object.reaction.ReactionEmoji;

public class ReactionHelper {
    public static String getReactId(ReactionEmoji genericReaction) {
        String reaction;

        if (genericReaction instanceof ReactionEmoji.Unicode reactionEmoji) {
            reaction = reactionEmoji.getRaw();
        } else if (genericReaction instanceof ReactionEmoji.Custom reactionEmoji) {
            reaction = reactionEmoji.getId().asString();
        } else {
            /**
             * Shouldnt ever come here
             */
            reaction = "";
        }

        return reaction;
    }
}
