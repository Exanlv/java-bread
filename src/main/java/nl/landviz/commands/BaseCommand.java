package nl.landviz.commands;

import discord4j.core.object.entity.Message;

public class BaseCommand {
    protected Message message;

    public BaseCommand(Message message) {
        this.message = message;
    }

    public void run() {

        
    }
}
