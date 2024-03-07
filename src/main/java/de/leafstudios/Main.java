package de.leafstudios;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;

public class Main {
    private static final String token;

    static {
        try {
            token = new String(Main.class.getClassLoader().getResourceAsStream("token.txt").readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(new Listener());
        jda.updateCommands().addCommands(Commands.slash("gaming","Go gaming")).queue();
    }
}
