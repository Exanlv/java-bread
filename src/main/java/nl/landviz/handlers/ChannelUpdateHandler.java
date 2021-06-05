package nl.landviz.handlers;


import discord4j.core.event.domain.channel.TextChannelUpdateEvent;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.object.entity.channel.TextChannel;
import nl.landviz.Bread;
import nl.landviz.cache.ChannelCache;
import nl.landviz.helpers.BreadChannelHelper;

public class ChannelUpdateHandler {
    private Bread bread = Bread.getInstance();

    private static ChannelCache channelCache = ChannelCache.getInstance();

    public ChannelUpdateHandler() {
        this.register();
    }

    private void register() {
        this.bread.gateway.on(TextChannelUpdateEvent.class).subscribe(event -> {
            GuildMessageChannel newChannel = event.getCurrent();

            if (!(newChannel instanceof TextChannel newTextChannel)) {
                return;
            }

            channelCache.setChannel(
                newTextChannel.getId().asString(),
                BreadChannelHelper.isBread(
                    newTextChannel.getName()
                )
            );
        });
    }
}
