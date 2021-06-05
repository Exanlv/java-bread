package nl.landviz;

import nl.landviz.handlers.ChannelUpdateHandler;
import nl.landviz.handlers.MemberUpdateHandler;
import nl.landviz.handlers.MessageHandler;

public class App {
    public static void main(String[] args)
    {
        Bread bread = Bread.getInstance();
        bread.initialize();

        new MessageHandler();
        new MemberUpdateHandler();
        new ChannelUpdateHandler();

        bread.block();
    }
}
