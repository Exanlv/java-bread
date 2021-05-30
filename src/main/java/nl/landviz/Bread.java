package nl.landviz;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import io.github.cdimascio.dotenv.Dotenv;

public class Bread {
    private static Bread bread = new Bread();
    
    public DiscordApi api;
    public static Dotenv dotenv = Dotenv.load();
    
    private Bread() { }

    public static Bread getInstance() {
        return bread;
    }

    public void initialize() {
        api = new DiscordApiBuilder()
            .setToken(dotenv.get("DISCORD_TOKEN"))
            .login()
            .join()
        ;
    }
}
