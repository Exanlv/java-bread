package nl.landviz;

import nl.landviz.handlers.CommandHandler;
import nl.landviz.handlers.MessageHandler;

public class App {
    public static void main(String[] args)
    {
        Bread bread = Bread.getInstance();
        bread.initialize();

        CommandHandler commandHandler = CommandHandler.getInstance();
        new MessageHandler();

        bread.block();
    }
}
