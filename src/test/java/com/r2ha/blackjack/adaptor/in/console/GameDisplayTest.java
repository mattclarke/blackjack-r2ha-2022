package com.r2ha.blackjack.adaptor.in.console;

import com.r2ha.blackjack.Blackjack;
import com.r2ha.blackjack.domain.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

public class GameDisplayTest {
    private final InputStream originalSystemIn = System.in;

    private void provideInput(String input) {
        byte[] inputBytes = input.getBytes();
        ByteArrayInputStream testIn = new ByteArrayInputStream(inputBytes);
        System.setIn(testIn); // reading from System.in will consume our input
    }

    @AfterEach
    public void restoreSystemInput() {
        System.setIn(originalSystemIn);
    }


    @Test
    void gamePlays() {
        provideInput("\nS\n");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        ConsoleGame.directOutputTo(printStream);
        // Starts the game with an empty String array for the arguments.
        Blackjack.main(new String[0]);
        String output = baos.toString().replaceAll("\u001B\\[[\\d;]*[^\\d;]",
                                                   "\n");

        assertThat(output)
                .containsIgnoringWhitespaces(
                        "Hit [ENTER]",
                        "[H]it or [S]tand?");

        assertThat(output)
                .containsIgnoringWhitespaces(
                        "Hit [ENTER] to start...");

        assertThat(output)
                .containsIgnoringWhitespaces(
                        "Welcome to JitterTed's Blackjack game");

        assertThat(output)
                .containsIgnoringWhitespaces(
                        "Dealer has");
        assertThat(output)
                .containsIgnoringWhitespaces("┌─────────┐",
                                             "│░░░░░░░░░│",
                                             "│░ J I T ░│",
                                             "│░ T E R ░│",
                                             "│░ T E D ░│",
                                             "│░░░░░░░░░│",
                                             "└─────────┘");

        String cardTop = "┌─────────┐";
        String cardBottom = "└─────────┘";

        int numTops = 0;
        int numBottoms = 0;

        for (String line : output.split("\\r?\\n")) {
            if (cardTop.equals(line.strip())) {
                numTops += 1;
            }
            if (cardBottom.equals(line.strip())) {
                numBottoms += 1;
            }
        }

        assertThat(numTops).isEqualTo(numBottoms);

        assertThat(output).containsAnyOf(
                "You Busted, so you lose.  💸",
                "Dealer went BUST, Player wins! Yay for you!! 💵",
                "You beat the Dealer! 💵",
                "Push: Nobody wins, we'll call it even.",
                "You lost to the Dealer. 💸");
    }

}

