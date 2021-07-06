package nl.landviz.handlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import discord4j.core.object.entity.Message;

import nl.landviz.commands.BaseCommand;
import nl.landviz.commands.BreadAmountCommand;
import nl.landviz.commands.ErrorCommand;
import nl.landviz.commands.GambleCommand;
import nl.landviz.commands.GiveBreadCommand;
import nl.landviz.commands.HelpCommand;
import nl.landviz.commands.InviteCommand;
import nl.landviz.commands.LotteryCommand;
import nl.landviz.commands.NoPermissionsCommand;
import nl.landviz.commands.PrivacyCommand;
import nl.landviz.commands.TopListCommand;
import nl.landviz.commands.UnknownCommand;

public class CommandHandler {
    private static CommandHandler commandHandler = new CommandHandler();

    private Map<String, Class<? extends BaseCommand>> commands = new HashMap<>();

    private CommandHandler() {
        commands.put("help", HelpCommand.class);
        commands.put("privacy", PrivacyCommand.class);
        commands.put("me", BreadAmountCommand.class);
        commands.put("gamble", GambleCommand.class);
        commands.put("top", TopListCommand.class);
        commands.put("invite", InviteCommand.class);
        commands.put("give", GiveBreadCommand.class);
        commands.put("lottery", LotteryCommand.class);
    }

    public static CommandHandler getInstance() {
        return commandHandler;
    }

    public void handleCommand(String commandString, Message message) {
        ArrayList<String> args = getArgs(commandString);

        if (args.size() == 0) {
            args.add("top");
        }

        Class<? extends BaseCommand> command = this.commands.get(args.get(0));

        args.remove(0);

        if (command == null) {
            UnknownCommand unknownCommand = new UnknownCommand(message);
            unknownCommand.run(args);

            return;
        }


        try {
            Constructor<? extends BaseCommand> constructor = command.getConstructor(Message.class);
            BaseCommand commandInstance = (BaseCommand) constructor.newInstance(message);

            if (commandInstance.hasPermission()) {
                commandInstance.run(args);
            } else {
                NoPermissionsCommand noPermissionsCommand = new NoPermissionsCommand(message);
                noPermissionsCommand.run(args);
            }
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            ErrorCommand errorCommand = new ErrorCommand(message);
            errorCommand.run(args);
        }
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