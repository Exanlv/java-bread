package nl.landviz;

import nl.landviz.handlers.MessageHandler;

public class App {
    public static void main(String[] args)
    {
        Bread bread = Bread.getInstance();
        bread.initialize();

        new MessageHandler();
    }
}
