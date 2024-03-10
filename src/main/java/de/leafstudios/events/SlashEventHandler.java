package de.leafstudios.events;

import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

@FunctionalInterface
public interface SlashEventHandler {
    void handle(SlashCommandInteraction interaction);
}
