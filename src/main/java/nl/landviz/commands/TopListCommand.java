package nl.landviz.commands;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import nl.landviz.cache.MemberCache;
import nl.landviz.entity.TopListMember;
import nl.landviz.entity.TopListUserInfo;
import nl.landviz.helpers.IsFrenchHelper;
import nl.landviz.storage.BreadStorage;

public class TopListCommand extends BaseCommand {
    private BreadStorage breadStorage = BreadStorage.getInstance();
    private MemberCache memberCache = MemberCache.getInstance();
    private Map<String, String> userGuids = new HashMap<>();
    private ArrayList<TopListMember> topList;
    private Consumer<EmbedCreateSpec> embed = this.getEmbedSpec();
    private long guildIdLong;
    private String guildIdString;

    public TopListCommand(Message message) {
        super(message);
        this.guildIdLong = this.message.getGuildId().get().asLong();
        this.guildIdString = this.message.getGuildId().get().asString();
    }

    public void run(ArrayList<String> args) {
        this.topList = this.getTopMembers();

        if (topList.size() == 0) {
            this.message.getChannel().subscribe(channel -> {
                channel.createMessage("Noone in this guild has bread yet :(").subscribe();
            });

            return;
        }

        this.message.getChannel().subscribe(channel -> {
            channel.createMessage(messageSpec -> {
                messageSpec.setEmbed(this.embed);
            }).subscribe();
        });
    }

    private ArrayList<TopListMember> getTopMembers() {
        return this.getTopMembers(5);
    }

    private ArrayList<TopListMember> getTopMembers(int amount) {
        ArrayList<TopListMember> topList = new ArrayList<>();

        TopListUserInfo[] totalTop = breadStorage.getTopList(this.guildIdString);

        for (int i = 0; i < amount; i++) {
            if (i >= totalTop.length) {
                break;
            }

            if (this.isCached(totalTop[i].userId)) {
                topList.add(
                    new TopListMember(
                        this.getUsername(totalTop[i].userId),
                        totalTop[i].bread
                    )
                );
            } else {
                amount++;
            }
        }

        return topList;
    }

    private void ensureGuidExists(String memberId) {
        if (!this.userGuids.containsKey(memberId)) {
            this.userGuids.put(
                memberId,
                IsFrenchHelper.getUserGuid(
                    this.guildIdLong,
                    Long.parseLong(memberId)
                )
            );
        }
    }

    private boolean isCached(String memberId) {
        this.ensureGuidExists(memberId);

        return this.memberCache.isCached(
            this.userGuids.get(memberId)
        );
    }

    private String getUsername(String memberId) {
        this.ensureGuidExists(memberId);

        return memberCache.getUsername(
            this.userGuids.get(memberId)
        );
    }

    private Consumer<EmbedCreateSpec> getEmbedSpec() {
        Consumer<EmbedCreateSpec> embed = spec -> {
            String[] prefixes = {"🥇", "🥈", "🥉", "4th place", "5th place"};
    
            spec.setAuthor("The breadest of them all!", "https://cdn.discordapp.com/attachments/651081524954923028/651890139173224488/bread.png", "https://cdn.discordapp.com/attachments/651081524954923028/651890139173224488/bread.png");
            spec.setTimestamp(Instant.now());
            spec.setColor(Color.of(192,132,238));
            spec.setThumbnail("https://cdn.discordapp.com/attachments/651081524954923028/651890139173224488/bread.png");
    
            for (int i = 0; i < this.topList.size(); i++) {
                TopListMember member = this.topList.get(i);
    
                spec.addField(prefixes[i], member.name, true);
                spec.addField("​", "​", true); // Zero width spaces
                spec.addField("With:", member.bread + " bread", true);
            }
        };

        return embed;
    }
}
