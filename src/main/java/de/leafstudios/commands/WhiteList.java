package de.leafstudios.commands;

import de.leafstudios.events.SlashEventHandler;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.io.IOException;

public class WhiteList implements SlashEventHandler {

    @Override
    public void handle(SlashCommandInteraction interaction) {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            pb.command("./whitelist.sh","whitelist add " + interaction.getOption("name").getAsString());
            Process p = pb.start();
            p.waitFor();

            String err = new String(p.getErrorStream().readAllBytes());
            if(!err.isEmpty()) interaction.getChannel().sendMessage(err).queue();
            String out2 = new String(p.getInputStream().readAllBytes());
            String out = out2.substring(0,out2.length() - 5);
            if(!out.isEmpty()) interaction.getChannel().sendMessage(out).queue();

            if(p.exitValue() == 0 && err.isEmpty() && out.isEmpty()) interaction.reply("Added " + interaction.getOption("name").getAsString() + " to the whitelist").queue();
            else interaction.reply("An error occurred:").queue();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
