package listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ButtonRoles extends ListenerAdapter {


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("role-enable")) {
            // get category
            // create buttons
            Button roleButtonBB = Button.primary("role-button-bb", "Battle Box").withEmoji(Emoji.fromFormatted("<:battle_box:1053681035386306591>"));
            Button roleButtonSB = Button.primary("role-button-sb", "Sky Battle").withEmoji(Emoji.fromFormatted("<:sky_battle:1053681030759977042>"));
            Button roleButtonHITW = Button.primary("role-button-hitw", "Hole In The Wall").withEmoji(Emoji.fromFormatted("<:hitw:1053681034144796682>"));
            Button roleButtonTGTTOS = Button.primary("role-button-tgttos", "To Get To The Other Side").withEmoji(Emoji.fromFormatted("<:tgttos:1053681029694623884>"));
            Button roleButtonHH = Button.primary("role-button-hh", "He/Him");
            Button roleButtonHT = Button.primary("role-button-ht", "He/They");
            Button roleButtonSH = Button.primary("role-button-sh", "She/Her");
            Button roleButtonST = Button.primary("role-button-st", "She/They");
            Button roleButtonTT = Button.primary("role-button-tt", "They/Them");
            Button roleButtonAskP = Button.primary("role-button-askP", "Ask Pronouns");
            Button roleButtonAnyP = Button.primary("role-button-anyP", "Any Pronouns");
            Button roleButtonUN = Button.primary("role-button-un", "Use Name");
            // create embed
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("React Roles");
            embed.setColor(new Color(0x24A9E1));
            embed.setDescription("This is the way to gain roles! Below are buttons that you can press and it will add or remove the role relating to the button you press." + "\nThe roles for the MCCI games will give you a role that can be pinged when someone wants to look for a party for that specific game.");
            embed.setFooter("Made by SuitSnap", "https://static-cdn.jtvnw.net/jtv_user_pictures/6df3a537-0cc0-41f0-b074-04eb81c7589f-profile_image-70x70.png");
            // send message
            event.reply("Message sent, king!").setEphemeral(true).queue();
            event.getChannel().sendMessageEmbeds(embed.build()).addActionRow(roleButtonBB, roleButtonSB, roleButtonHITW, roleButtonTGTTOS).addActionRow(roleButtonHH, roleButtonHT, roleButtonSH, roleButtonST, roleButtonTT).addActionRow(roleButtonAskP, roleButtonAnyP, roleButtonUN).queue();

        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String buttonName = event.getButton().getId();
        List<Role> memberByRoles = Objects.requireNonNull(event.getMember()).getRoles();
        switch (Objects.requireNonNull(buttonName)) {
            case "role-button-bb" -> {
                Role role = event.getJDA().getRoleById("1055218599331115088");
                if (!memberByRoles.contains(role)) {
                    Objects.requireNonNull(event.getGuild()).addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added Battle Box Role!").setEphemeral(true).queue();
                } else {
                    Objects.requireNonNull(event.getGuild()).removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed Battle Box Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-sb" -> {
                Role role = event.getJDA().getRoleById("1055218607338045530");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added Sky Battle Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed Sky Battle Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-hitw" -> {
                Role role = event.getJDA().getRoleById("1055218613382037554");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added Hole In The Wall Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed Hole In The Wall Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-tgttos" -> {
                Role role = event.getJDA().getRoleById("1055218616154464298");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added To Get To The Other Side Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed To Get To The Other Side Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-hh" -> {
                Role role = event.getJDA().getRoleById("1055217419154632725");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added He/Him Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed He/Him Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-ht" -> {
                Role role = event.getJDA().getRoleById("1055217556035735592");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added He/They Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed He/They Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-sh" -> {
                Role role = event.getJDA().getRoleById("1055217425966170133");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added She/Her Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed She/Her Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-st" -> {
                Role role = event.getJDA().getRoleById("1055217477824557138");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added She/They Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed She/They Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-tt" -> {
                Role role = event.getJDA().getRoleById("1055217386254508165");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added They/Them Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed They/Them Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-askP" -> {
                Role role = event.getJDA().getRoleById("1055217649656803368");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added Ask Pronouns Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed Ask Pronouns Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-anyP" -> {
                Role role = event.getJDA().getRoleById("1055218190373900298");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added Any Pronouns Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed Any Pronouns Role!").setEphemeral(true).queue();
                }
            }
            case "role-button-un" -> {
                Role role = event.getJDA().getRoleById("1055217658611646524");
                if (!memberByRoles.contains(role)) {
                    event.getGuild().addRoleToMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Added Use Name Role!").setEphemeral(true).queue();
                } else {
                    event.getGuild().removeRoleFromMember(UserSnowflake.fromId(event.getUser().getId()), Objects.requireNonNull(role)).queue();
                    event.reply("Removed Use Name Role!").setEphemeral(true).queue();
                }
            }
        }
    }
}
