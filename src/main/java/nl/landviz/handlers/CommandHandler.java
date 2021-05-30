package nl.landviz.handlers;

import java.util.ArrayList;
import java.util.Arrays;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;

import nl.landviz.Bread;

public class CommandHandler {
    private static Bread bread = Bread.getInstance();

    private CommandHandler() { }

    public static void handleCommand(String commandString, ServerTextChannel channel, Message message) {
        ArrayList<String> args = getArgs(commandString);

        System.out.print(args);
    }

    private static ArrayList<String> getArgs(String commandString) {
        ArrayList<String> args = new ArrayList<String>(Arrays.asList(commandString.split(" ")));
        
        for (int i = args.size() - 1; i > -1; i--) {
            String arg = args.get(i);
            if (arg == " " || arg == "") {
                args.remove(i);
            }
        }

        return args;
    }
}