package de.leafstudios.events;

import de.leafstudios.IgelBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
    private final HashMap<String,SlashEventHandler> slashEvents;
    private final ArrayList<CommandData> cmds;

    public EventManager(@Nullable HashMap<String,SlashEventHandler> slashEvents){
        if(slashEvents != null)this.slashEvents = slashEvents;
        else this.slashEvents = new HashMap<>();
        cmds = new ArrayList<>();
    }

    public void addEvent(String name,String desc,SlashEventHandler handler){
        slashEvents.put(name,handler);
        cmds.add(Commands.slash(name,desc));
        //IgelBot.getInstance().getJda().updateCommands().addCommands(Commands.slash(name,desc)).queue();
    }

    public void addEvent(CommandData cmd,SlashEventHandler handler){
        slashEvents.put(cmd.getName(),handler);
        cmds.add(cmd);
    }

    public @Nullable SlashEventHandler getHandler(String name){
        return slashEvents.get(name);
    }

    public ArrayList<CommandData> getCmds() {
        return cmds;
    }
}
