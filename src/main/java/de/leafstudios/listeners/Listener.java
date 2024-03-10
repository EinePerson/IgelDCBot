package de.leafstudios.listeners;

import de.leafstudios.IgelBot;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Listener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        IgelBot.getInstance().handle(event);
    }

    @Override
    public void onReady(ReadyEvent event) {
        //event.getJDA().deleteCommandById("1216415293941743756").queue();
        event.getJDA().updateCommands().addCommands(IgelBot.getInstance().getEventManager().getCmds()).queue();
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        //event.getGuild().deleteCommandById("1216415293941743756").queue();
    }
}
