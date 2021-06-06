package nl.landviz;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import io.github.cdimascio.dotenv.Dotenv;

public class Bread {
    private static Bread bread = new Bread();
    
    public Dotenv dotenv = Dotenv.load();

    public DiscordClient client;
    public GatewayDiscordClient gateway;

    public String ownId;

    private Bread() {
        this.client = DiscordClient.create(this.dotenv.get("DISCORD_TOKEN"));
    }

    public static Bread getInstance() {
        return bread;
    }

    public void initialize() {
        this.gateway = this.client.login().block();
        this.ownId = this.gateway.getSelfId().asString();
    }

    public void block() {
        this.gateway.onDisconnect().block();
    }
}
