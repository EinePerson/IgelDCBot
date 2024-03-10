package de.leafstudios;

import de.leafstudios.events.EventManager;
import de.leafstudios.events.SlashEventHandler;
import de.leafstudios.listeners.Listener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class IgelBot {
    private static IgelBot instance;

    public static IgelBot getInstance() {
        return instance;
    }


    private EventManager eventManager;
    private final JDA jda;

    private IgelBot(){
        eventManager = new EventManager(null);
        try(InputStream stream = this.getClass().getClassLoader().getResourceAsStream("token.txt")) {
            jda = JDABuilder.createDefault(new String(Objects.requireNonNull(stream).readAllBytes())).addEventListeners(new Listener()).build();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        registerEvents();
        eventManager.getCmds().add(Commands.slash("sus","test"));
        jda.updateCommands().addCommands(eventManager.getCmds()).queue();
    }

    private void registerEvents(){
        eventManager.addEvent("gaming","Are you really gaming?", new SlashEventHandler() {
            @Override
            public void handle(SlashCommandInteraction interaction) {
                if(interaction.getMember() == null || (interaction.getMember() != null && interaction.getMember().getActivities().isEmpty())){
                    interaction.reply("Are you really gaming?").queue();
                    return;
                }
                interaction.reply("You are gaming:").queue();
                interaction.getMember().getActivities().forEach(activity ->  interaction.getChannel().sendMessage(activity.getName()).queue());
            }
        });
        eventManager.addEvent("projects","Get a list of all projects", new SlashEventHandler() {
            @Override
            public void handle(SlashCommandInteraction interaction) {
                try{
                    if(!Files.exists(Path.of("./conf/projects.txt"))){
                        interaction.reply("No projects").queue();
                        return;
                    }
                    File f = new File("./conf/projects.txt");
                    BufferedReader reader = new BufferedReader(new FileReader(f));
                    while (reader.ready())interaction.getChannel().sendMessage(reader.readLine()).queue();
                    interaction.reply("List of all current projects:").queue();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        eventManager.addEvent("roulette", "timeout a random person", new SlashEventHandler() {
            @Override
            public void handle(SlashCommandInteraction interaction) {
                System.out.println(interaction.getGuild().getMembers());
            }
        });
    }

    public void handle(SlashCommandInteraction e){
        SlashEventHandler handler = eventManager.getHandler(e.getName());
        if(handler != null){
            handler.handle(e);
            return;
        }
        e.reply("Unknown command " + e.getName()).queue();;
    }

    public static void init(){
        if(instance == null)instance = new IgelBot();
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public JDA getJda() {
        return jda;
    }
}
