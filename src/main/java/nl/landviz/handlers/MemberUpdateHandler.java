package nl.landviz.handlers;

import discord4j.core.event.domain.guild.MemberUpdateEvent;
import nl.landviz.Bread;
import nl.landviz.cache.MemberCache;
import nl.landviz.helpers.IsFrenchHelper;

public class MemberUpdateHandler {
    private Bread bread = Bread.getInstance();;
    
    private static MemberCache memberCache = MemberCache.getInstance();

    public MemberUpdateHandler() {
        this.register();
    }

    public void register() {
        this.bread.gateway.on(MemberUpdateEvent.class).subscribe(event -> {
            memberCache.setFrench(
                IsFrenchHelper.getUserGuid(
                    event.getGuildId().asLong(),
                    event.getMemberId().asLong()
                ),
                event.getMember().block().getDisplayName()
            );
        });
    }
}
