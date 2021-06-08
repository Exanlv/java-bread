package nl.landviz.handlers;

import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.retriever.RestEntityRetriever;
import nl.landviz.Bread;
import nl.landviz.cache.MemberCache;
import nl.landviz.helpers.IsFrenchHelper;

public class GuildCreateHandler {
    private Bread bread = Bread.getInstance();
    private static MemberCache memberCache = MemberCache.getInstance();

    public GuildCreateHandler() {
        this.register();
    }

    private void register() {
        this.bread.gateway.on(GuildCreateEvent.class).subscribe(event -> {
            event.getGuild().getMembers(RestEntityRetriever::new).subscribe(member -> {
                memberCache.setFrench(
                    IsFrenchHelper.getUserGuid(
                        member.getGuildId().asLong(), 
                        member.getUserData().id().asLong()
                    ),
                    member.getDisplayName()
                );
            });
        });
    }
}
