package listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class Voting extends ListenerAdapter {
    Map<String, String> userReactions = new HashMap<>();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("vote-begin")) {
            boolean SBPlaying = Objects.requireNonNull(event.getOption("skybattle")).getAsBoolean();
            boolean BBPlaying = Objects.requireNonNull(event.getOption("battlebox")).getAsBoolean();
            boolean HITWPlaying = Objects.requireNonNull(event.getOption("holeinthewall")).getAsBoolean();
            boolean TGTTOSPlaying = Objects.requireNonNull(event.getOption("togettotheotherside")).getAsBoolean();
            EmbedBuilder votingEmbed = new EmbedBuilder();
            votingEmbed.setFooter("Made by SuitSnap", "https://static-cdn.jtvnw.net/jtv_user_pictures/6df3a537-0cc0-41f0-b074-04eb81c7589f-profile_image-70x70.png").setColor(new Color(0x24A9E1)).setTitle("<:island:1053681032894890044> Voting <:island:1053681032894890044>");
            int SBPercent, BBPercent, HITWPercent, TGTTOSPercent;
            SBPercent = BBPercent = HITWPercent = TGTTOSPercent = 0;
            String MainString = "Vote for the game you want to play next:\n\n";
            String SBText = "<:sky_battle:1053681030759977042> Sky Battle: %d%%\n".formatted(SBPercent);
            String BBText = "<:battle_box:1053681035386306591> Battle Box: %d%%\n".formatted(BBPercent);
            String HITWText = "<:hitw:1053681034144796682> Hole In The Wall: %d%%\n".formatted(HITWPercent);
            String TGTTOSText = "<:tgttos:1053681029694623884> To Get To The Other Side: %d%%\n".formatted(TGTTOSPercent);
            List<String> reactions = new ArrayList<>();
            if (SBPlaying) {
                MainString += SBText;
                reactions.add("1053681030759977042");
            }
            if (BBPlaying) {
                MainString += BBText;
                reactions.add("1053681035386306591");
            }
            if (HITWPlaying) {
                MainString += HITWText;
                reactions.add("1053681034144796682");
            }
            if (TGTTOSPlaying) {
                MainString += TGTTOSText;
                reactions.add("1053681029694623884");
            }
            votingEmbed.setDescription(MainString);
            event.getChannel().sendMessageEmbeds(votingEmbed.build()).queue(message -> {
                for (String emoji : reactions) {
                    message.addReaction(Objects.requireNonNull(event.getJDA().getEmojiById(emoji))).queue();
                    try {
                        FileWriter myObj = new FileWriter("C:\\Users\\xndrw\\IdeaProjects\\MCCICBot\\src\\main\\resources\\votemessages.txt", false);
                        myObj.write(message.getId());
                        myObj.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            event.reply("Done").setEphemeral(true).queue();

        }
        if (command.equals("vote-end")) {
            try {
                new FileWriter("C:\\Users\\xndrw\\IdeaProjects\\MCCICBot\\src\\main\\resources\\votes.txt", false).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            event.reply("Done :)").setEphemeral(true).queue();
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        String messageReactedToID = event.getMessageId();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\xndrw\\IdeaProjects\\MCCICBot\\src\\main\\resources\\votemessages.txt"));
            String currentVoteMessage = reader.readLine();
            if (messageReactedToID.equals(currentVoteMessage)) {
                // Retrieve the message that the reactions were added to
                Message message = event.getChannel().retrieveMessageById(event.getMessageId()).complete();

                // Get the user who added the reaction
                User user = event.retrieveUser().complete();
                //System.out.println(user);

                // Check if the user has already reacted
                if (userReactions.containsKey(Objects.requireNonNull(user).getId()) && !user.isBot()) {
                    // User has already reacted, remove their old reaction
                    String oldEmojiId = userReactions.get(user.getId());
                    Emoji oldEmoji = event.getJDA().getEmojiById(oldEmojiId);
                    message.removeReaction(Objects.requireNonNull(oldEmoji), user).complete();
                    // Remove the user's old reaction from the map
                    userReactions.remove(user.getId());
                }
                // Get the emoji of the reaction
                String[] emojiIdSplit = event.getReaction().getEmoji().getAsReactionCode().split(":");
                String emojiId = emojiIdSplit[1];
                userReactions.put(user.getId(), emojiId);

                // Initialize vote counts for each game
                int SBVotes = 0;
                int BBVotes = 0;
                int HITWVotes = 0;
                int TGTTOSVotes = 0;

                // Iterate through the list of reactions on the message
                for (MessageReaction reaction : message.getReactions()) {
                    // Check the emoji of the reaction
                    String emoji = reaction.getEmoji().getAsReactionCode();
                    //System.out.println(emoji);
                    switch (emoji) {
                        case "sky_battle:1053681030759977042" -> // Sky Battle emoji, increment vote count
                                SBVotes += reaction.getCount() - 1;

                        //System.out.println("AHA");
                        case "battle_box:1053681035386306591" -> // Battle Box emoji, increment vote count
                                BBVotes += reaction.getCount() - 1;

                        //System.out.println("AHA");
                        case "hitw:1053681034144796682" -> // Hole In The Wall emoji, increment vote count
                                HITWVotes += reaction.getCount() - 1;

                        //System.out.println("AHA");
                        case "tgttos:1053681029694623884" -> // To Get To The Other Side emoji, increment vote count
                                TGTTOSVotes += reaction.getCount() - 1;

                        //System.out.println("AHA");
                    }
                }

                // Calculate the total number of votes
                int totalVotes = SBVotes + BBVotes + HITWVotes + TGTTOSVotes;

                // Calculate the percentages for each game
                int SBPercent = (int) ((double) SBVotes / totalVotes * 100);
                int BBPercent = (int) ((double) BBVotes / totalVotes * 100);
                int HITWPercent = (int) ((double) HITWVotes / totalVotes * 100);
                int TGTTOSPercent = (int) ((double) TGTTOSVotes / totalVotes * 100);

                // Update the text for each game with the new percentages
                String SBText = "<:sky_battle:1053681030759977042> Sky Battle: %d%%\n".formatted(SBPercent);
                String BBText = "<:battle_box:1053681035386306591> Battle Box: %d%%\n".formatted(BBPercent);
                String HITWText = "<:hitw:1053681034144796682> Hole In The Wall: %d%%\n".formatted(HITWPercent);
                String TGTTOSText = "<:tgttos:1053681029694623884> To Get To The Other Side: %d%%\n".formatted(TGTTOSPercent);

                //System.out.println(SBText + BBText + HITWText + TGTTOSText);

                // Update the embed message with the new percentages
                EmbedBuilder votingEmbed = new EmbedBuilder();
                votingEmbed.setFooter("Made by SuitSnap", "https://static-cdn.jtvnw.net/jtv_user_pictures/6df3a537-0cc0-41f0-b074-04eb81c7589f-profile_image-70x70.png").setColor(new Color(0x24A9E1)).setTitle("<:island:1053681032894890044> Voting <:island:1053681032894890044>");
                votingEmbed.setDescription("Vote for the game you want to play next:\n\n"+SBText + BBText + HITWText + TGTTOSText);

                // Edit the original message to show the updated results
                message.editMessageEmbeds(votingEmbed.build()).complete();


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        String messageReactedToID = event.getMessageId();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\xndrw\\IdeaProjects\\MCCICBot\\src\\main\\resources\\votemessages.txt"));
            String currentVoteMessage = reader.readLine();
            System.out.println(userReactions);
            if (messageReactedToID.equals(currentVoteMessage)) {
                // Retrieve the message that the reactions were added to
                Message message = event.getChannel().retrieveMessageById(event.getMessageId()).complete();


                // Get the user who added the reaction
                User user = event.retrieveUser().complete();
                //System.out.println(user);


                // Check if the user has already reacted
                if (userReactions.containsKey(Objects.requireNonNull(user).getId()) && !user.isBot()) {
                    // Remove the user's old reaction from the map
                    userReactions.remove(user.getId());
                }

                // Initialize vote counts for each game
                int SBVotes = 0;
                int BBVotes = 0;
                int HITWVotes = 0;
                int TGTTOSVotes = 0;

                // Iterate through the list of reactions on the message
                for (MessageReaction reaction : message.getReactions()) {
                    // Check the emoji of the reaction
                    String emoji = reaction.getEmoji().getAsReactionCode();
                    //System.out.println(emoji);
                    switch (emoji) {
                        case "sky_battle:1053681030759977042" -> // Sky Battle emoji, increment vote count
                                SBVotes += reaction.getCount() - 1;

                        // System.out.println("AHA");
                        case "battle_box:1053681035386306591" -> // Battle Box emoji, increment vote count
                                BBVotes += reaction.getCount() - 1;

                        //System.out.println("AHA");
                        case "hitw:1053681034144796682" -> // Hole In The Wall emoji, increment vote count
                                HITWVotes += reaction.getCount() - 1;

                        //System.out.println("AHA");
                        case "tgttos:1053681029694623884" -> // To Get To The Other Side emoji, increment vote count
                                TGTTOSVotes += reaction.getCount() - 1;

                        //System.out.println("AHA");
                    }
                }

                // Calculate the total number of votes
                int totalVotes = SBVotes + BBVotes + HITWVotes + TGTTOSVotes;

                // Calculate the percentages for each game
                int SBPercent = (int) ((double) SBVotes / totalVotes * 100);
                int BBPercent = (int) ((double) BBVotes / totalVotes * 100);
                int HITWPercent = (int) ((double) HITWVotes / totalVotes * 100);
                int TGTTOSPercent = (int) ((double) TGTTOSVotes / totalVotes * 100);

                // Update the text for each game with the new percentages
                String SBText = "<:sky_battle:1053681030759977042> Sky Battle: %d%%\n".formatted(SBPercent);
                String BBText = "<:battle_box:1053681035386306591> Battle Box: %d%%\n".formatted(BBPercent);
                String HITWText = "<:hitw:1053681034144796682> Hole In The Wall: %d%%\n".formatted(HITWPercent);
                String TGTTOSText = "<:tgttos:1053681029694623884> To Get To The Other Side: %d%%\n".formatted(TGTTOSPercent);

                //System.out.println(SBText + BBText + HITWText + TGTTOSText);

                // Update the embed message with the new percentages
                EmbedBuilder votingEmbed = new EmbedBuilder();
                votingEmbed.setFooter("Made by SuitSnap", "https://static-cdn.jtvnw.net/jtv_user_pictures/6df3a537-0cc0-41f0-b074-04eb81c7589f-profile_image-70x70.png").setColor(new Color(0x24A9E1)).setTitle("<:island:1053681032894890044> Voting <:island:1053681032894890044>");
                votingEmbed.setDescription("Vote for the game you want to play next:\n\n"+SBText + BBText + HITWText + TGTTOSText);

                // Edit the original message to show the updated results
                message.editMessageEmbeds(votingEmbed.build()).complete();


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
