package nl.landviz.handlers;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.TextChannel;
import nl.landviz.Bread;
import nl.landviz.commands.BaseCommand;
import nl.landviz.commands.HelpCommand;

public class CommandHandler {
    private static CommandHandler commandHandler = new CommandHandler();

    private static Bread bread = Bread.getInstance();

    private Map<String, Class<?>> commands = new HashMap<>();

    private CommandHandler() {
        commands.put("help", HelpCommand.class);
    }

    public static CommandHandler getInstance() {
        return commandHandler;
    }

    public void handleCommand(String commandString, TextChannel channel, Message message) {
        ArrayList<String> args = getArgs(commandString);

        System.out.println("asdf");

        if (args.size() == 0) {
            args.add("list");
        }

        Class<?> command = this.commands.get(args.get(0));

        if (command == null) {
            return;
        }
        
        Constructor<?> constructor = command.getConstructor();
        BaseCommand commandInstance = (BaseCommand) constructor.newInstance();
        commandInstance.run();
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